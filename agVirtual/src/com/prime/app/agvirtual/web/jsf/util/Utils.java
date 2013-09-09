package com.prime.app.agvirtual.web.jsf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	
	public static Double parseDouble(String param){
		Double retorno = new Double(0.0);
		
		if((param == null) && ("".equals(param)))
			return retorno;
		
		try{
			retorno = Double.valueOf(param);
		}catch(Exception e){
			logger.warn("Erro ao converter para Double o valor = " + param);
		}

		return retorno;
	}
}
