package com.prime.app.agvirtual.web.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Funcionalidades de navegacao especificas para os casos de uso 
 * 
 * que fazem uso da funcionalidade de identificacao do Imovel APOS TELA INICIAL. 
 */
public class BasicNavegacaoPesquisaBBean extends BasicPesquisaBBean{
	private static final Logger logger = LoggerFactory.getLogger(BasicNavegacaoPesquisaBBean.class);
	
	/**
	 *  Navega para identificacao do imovel APENAS se o imovel nao tiver sido identificado.
	 *  
	 *  Chamado em TELA pelo Faces.  Ver tela: consertohidrometroperguntas
	 */
	public String prosseguirIdentificacao(){
		if(isImovelIdentificado()){																			// imovel nao identificado
			logger.info("Imovel identificado, retornando outcome=" + obterOutcomeImovelIdentificado());  	// log
			modificarOutcome(obterOutcomeImovelIdentificado());   											// altera navegacao
		}else{
			logger.info("Imovel [NAO] identificado, retornando outcome=" + super.prosseguir());
		}
		
		return super.prosseguir();						    // imovel ja indentificado, segue caso de uso
	}
	
	/**
	 * Retorna outcome para pagina de idenficacao do imovel, especifica para cada caso de uso.
	 */
	protected String obterOutcomeImovelIdentificado(){return "";};
}
