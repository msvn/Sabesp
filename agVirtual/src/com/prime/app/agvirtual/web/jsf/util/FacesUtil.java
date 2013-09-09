package com.prime.app.agvirtual.web.jsf.util;

import java.util.Map;

import javax.faces.context.FacesContext;

public class FacesUtil {	
	
	public static Object getParameter(Object paramName, Map parameterMap){
		return parameterMap.get(paramName);
	}
	
	public static String getStringParameter(Object paramName, Map parameterMap){
		try{
			return (String)getParameter(paramName, parameterMap);
		}catch(Exception e){
			return null;
		}
	}

	
	
	public static String getRequestParameterRetornarOutcome() {
		
		String outcome = null;
		
		if( FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("retornoparam") != null ) {
				
				outcome = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap().get("retornoparam").toString();
				
		}
		
		return outcome;

    }
	
	public static String getRequestParameterProsseguirOutcome() {
		
		String outcome = null;
		
		if( FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("outcomeparam") != null ) {
				
				outcome = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap().get("outcomeparam").toString();
				
		}
		
		return outcome;

    }


}
