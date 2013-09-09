package com.prime.app.agvirtual.eae;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EAEConnectionPool extends GenericObjectPool {
	
    private static final Logger logger = LoggerFactory.getLogger(EAEConnectionPool.class);

	public void setConexoes(int conexoes) {

		logger.info("setMaxActive = " + conexoes);
		setMaxActive(conexoes);

	}

	public void setValidaConexao(boolean validaConexao) {

		logger.info("setTestOnBorrow = " + validaConexao);
		setTestOnBorrow(validaConexao);

	}

	@Override
	public synchronized void setMaxIdle(int maxIdle) {

		logger.info("setMaxIdle = " + maxIdle);
		super.setMaxIdle(maxIdle);
	}
	
	
}
