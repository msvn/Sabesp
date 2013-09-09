package com.sabesp.pool;

import org.apache.commons.pool.BasePoolableObjectFactory;

import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.IspecModelRef;
import com.unisys.jellybeans.LINCEnvironment;
import com.unisys.jellybeans.LINCStatus;

public class ConnectionFactory extends BasePoolableObjectFactory {
	
	private String host;
	private String viewName;
	private String user;
	private String pwd;

	public ConnectionFactory(String host, String viewName, String user, String pwd) {
		this.host = host;
		this.viewName = viewName;
		this.user = user;
		this.pwd = pwd;
	}

	public Object makeObject() { 
		LINCEnvironment objLINC = new LINCEnvironment();
		
		inicializa(objLINC);

		if (objLINC.isConnected()) {
			System.out.println("Já conectado!");
		} else {
			conecta(objLINC);
		}
		
		if (objLINC.isLoggedIn()) {
			System.out.println("Já logado!");
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

	@Override
	public void destroyObject(Object obj) throws Exception {
		
		System.out.println("Fechando conexao...");
		((LINCEnvironment) obj).close();

		super.destroyObject(obj);
	}

	private void conecta(LINCEnvironment objLINC) {
		int iResult;
		
        try {
			LINCStatus objStatusLine = objLINC.makeLINCStatus();
			com.unisys.util.ObjectRef objLoginAttrsRef = objLINC.makeObjectRef();
			
			//iResult = objLINC.connect("x-ratl:ratldm1.ti.sabesp.com.br:1030", viewName, objLoginAttrsRef, objStatusLine, 0);
			iResult = objLINC.connect(host, viewName, objLoginAttrsRef, objStatusLine, 0);
			
			if(iResult != 100) {
				throw new Exception("Conexao falhou!");
			}
		} catch (Exception e) {
			e.printStackTrace();
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

			System.out.println("Sending Login...");
			
			iResult = objLINC.simpleTransaction(objCurrentIspec, objStatusLine);
			
			if(!objLINC.isLoggedIn()) {
			    System.out.println((new StringBuilder("Not Logged ... - Result = ")).append(iResult).toString());
			    System.out.println((new StringBuilder("Status line = ")).append(objStatusLine.getError(0)).toString());
			    System.out.println((new StringBuilder("Error 2 =")).append(objStatusLine.getStatus()).toString());
				throw new Exception("Login falhou!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		
		System.out.println("Finalize...");
	}

	@Override
	public boolean validateObject(Object obj) {
		
		boolean valid = ((LINCEnvironment) obj).isLoggedIn();
		
		if (!valid) {
			// --------------------------------------
			// Controle aqui a perda de 1 conexao
			// --------------------------------------
			//System.out.println("1 conexao EAE perdida...");
		}
		
		return valid;
	}

}
