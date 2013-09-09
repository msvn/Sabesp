package com.prime.app.agvirtual.web.jsf.util;

import javax.servlet.http.HttpSession;

import com.prime.app.agvirtual.entity.Atendimento;
import com.prime.app.agvirtual.to.DadosImoveisTO;

public class SessionUtil {
	public static final String ATENDIMENTOSESSION = "AtendimentoSession";
	public static final String DADOSIMOVELSESSION = "dadosImovelSession";

	/**
	 * Coloca qualquer objeto na sessao
	 * 
	 * @param <E>
	 * @param attributeName
	 * @param attributeValue
	 * @param session
	 * @return
	 */
	public static <E> boolean adicionarAtributo(String attributeName, E attributeValue, HttpSession session) {
		if (session == null) return Boolean.FALSE;

		session.setAttribute(attributeName, attributeValue);
		return Boolean.TRUE;
	}
	
	/**
	 * Obter objeto da sessao
	 * 
	 * @param <E>
	 * @param attributeName
	 * @param session
	 * @return
	 */
	public static <E> E obterAtributo(String attributeName, HttpSession session, E e){
		Object o = session.getAttribute(attributeName);
		
		if (o == null) return null;
		
		return (E)o;
	}

	/**
	 * Remove objeto da sessao
	 * 
	 * @param attributeName
	 * @param session
	 * @return
	 */
	public static boolean removerAtributo(String attributeName, HttpSession session) {
		if (session == null)
			return Boolean.FALSE;

		session.removeAttribute(attributeName);
		return Boolean.TRUE;
	}


	/**
	 * Obtem dados do imovel da sessao 
	 * 
	 * Chamado em tela
	 */
	public static DadosImoveisTO obterImovelIdentificado(HttpSession session) {
		return SessionUtil.obterAtributo(SessionUtil.DADOSIMOVELSESSION, session, new DadosImoveisTO());
	}		


}
