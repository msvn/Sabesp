package com.prime.app.agvirtual.eae;


import org.apache.commons.pool.BasePoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.IspecModelRef;
import com.unisys.jellybeans.LINCEnvironment;
import com.unisys.jellybeans.LINCStatus;

public class EAEConnectionFactory extends BasePoolableObjectFactory {
	
    private static final Logger logger = LoggerFactory.getLogger(EAEConnectionFactory.class);
	
	private String host;
	private String viewName;
	private String user;
	private String pwd;
	
	public EAEConnectionFactory() {

	}

	public EAEConnectionFactory(String host, String viewName, String user, String pwd) {
		this.host = host;
		this.viewName = viewName;
		this.user = user;
		this.pwd = pwd;
	}

	public Object makeObject() {
		
		LINCEnvironment objLINC = new LINCEnvironment();
		
		inicializa(objLINC);

		if (objLINC.isConnected()) {
			logger.debug("Já conectado!");
		} else {
			conecta(objLINC);
		}
		
		if (objLINC.isLoggedIn()) {
			logger.debug("Já logado!");
		} else {
			login(objLINC);
		}
		 

		return objLINC; 
	}

	private void inicializa(LINCEnvironment objLINC) {
		
        objLINC.setName(viewName);
        objLINC.setPackagePrefix("com.sabesp");
        objLINC.setApplicationName("csit");
        objLINC.setBundleName("full");
        
        objLINC.setObjectPooled(true);
	}

	public void destroyObject(Object obj) throws Exception {
		
		logger.info("Fechando conexao EAE...");
		((LINCEnvironment) obj).close();

		super.destroyObject(obj);
	}

	private void conecta(LINCEnvironment objLINC) {
		
		int iResult;
		
        try {
			LINCStatus objStatusLine = objLINC.makeLINCStatus();
			com.unisys.util.ObjectRef objLoginAttrsRef = objLINC.makeObjectRef();
			
			iResult = objLINC.connect(host, viewName, objLoginAttrsRef, objStatusLine, 0);
			
			if(iResult != 100) {
				throw new EAEException("Conexao falhou!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}
	
	private void login(LINCEnvironment objLINC) {
		
		int iResult;

        try {
			LINCStatus objStatusLine = objLINC.makeLINCStatus();

			IspecModelRef objHelloIspecRef = objLINC.makeIspecModelRef();
			iResult = objLINC.hello(objHelloIspecRef, objStatusLine);
			Object objHelloIspec = objHelloIspecRef.getObject();
			IspecModel objCurrentIspec = (IspecModel)objHelloIspec;
			objCurrentIspec.setFieldValue("NMUSERID", user);
			objCurrentIspec.setFieldValue("CDPASSWOR", pwd);

			logger.info("Sending Login...");
			
			iResult = objLINC.simpleTransaction(objCurrentIspec, objStatusLine);
			
			if(!objLINC.isLoggedIn()) {
				trataStatus(objStatusLine, iResult);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public boolean validateObject(Object obj) {
		
		boolean valid = ((LINCEnvironment) obj).isLoggedIn();
		
		if (!valid) {
			logger.info("1 conexao EAE perdida...");
		}
		
		return valid;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	private void trataStatus(LINCStatus lincStatus, int iResult) throws EAEException {
		
		int errorCount = lincStatus.getErrorCount();
		StringBuilder errorBuffer = new StringBuilder();
		String message = "EAE Transaction Return Code = " + iResult;
		
		if (errorCount > 0) {
			for (int i = 0; i < errorCount; i++) {
				errorBuffer.append(lincStatus.getError(i) + "\n");
			}
		}

		if (iResult >= 900) {
			if (errorCount > 0) {
				message = message + "\n" + errorBuffer.toString();
			} else if (lincStatus.getStatus() != null) {
				message = message + "\n" + lincStatus.getStatus();
			}

			throw new EAEException(message);
		}

		logger.debug(message);
	}

}
