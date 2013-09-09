package com.prime.app.agvirtual.web.jsf;

import com.prime.infra.web.jsf.BasicBBean;

public class MenuBBean extends BasicBBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4027915656468337196L;
	
	public static String NOTICIA = "noticia";
	public static String SERVICO = "servico";
	public static String SUBSERVICO = "subservico";
	public static String CANAL = "canal";
	public static String DOCUMENTO = "documento";
	

	public String listar(){
		String link = getRequestParameter("tela");
		if(link.equals(NOTICIA)){
			return NOTICIA;
		}else 
		if(link.equals(SERVICO)){
			return SERVICO;
		}else
		if(link.equals(SUBSERVICO)){
			return SUBSERVICO;
		}else 
		if(link.equals(CANAL)){
			return CANAL;
		}else 
		if(link.equals(DOCUMENTO)){
			return DOCUMENTO;
		}
		return DOCUMENTO;
	}
}
