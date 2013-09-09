package com.prime.app.agvirtual.eae;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.LINCEnvironment;
import com.unisys.jellybeans.LINCStatus;


public class EAEDaoSupport {

	@Autowired
	private EAEConnectionPool connectionPool;
	protected LINCEnvironment objLINC = null;
	
    private static final Logger logger = LoggerFactory.getLogger(EAEDaoSupport.class);

	protected void openConnection() throws Exception {

		logger.debug("Requisitando conexao EAE ao pool...");
		objLINC = (LINCEnvironment) connectionPool.borrowObject();

	}

	protected void loadIspec(String ispec) {
		
		IspecModel objTempIspec = objLINC.getIspec(ispec);
		objLINC.loadIspec(objTempIspec);
	}
	
	protected void closeConnection() {
		
		logger.debug("Devolvendo conexao EAE ao pool...");
		try {
			connectionPool.returnObject(objLINC);
		} catch (Exception e) {
			//------------------------------------------
			// Registra o erro, mas nao provoca rollback
			logger.error(e.getMessage());
			//------------------------------------------
		}

	}

	protected void trataStatus(LINCStatus lincStatus, int iResult) throws EAEException {
		
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
