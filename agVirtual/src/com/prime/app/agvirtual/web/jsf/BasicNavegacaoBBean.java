package com.prime.app.agvirtual.web.jsf;


import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.prime.app.agvirtual.web.jsf.util.ReflectionFacade;
import com.prime.infra.web.jsf.BasicBBean;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * BackBean responsavel pela navegacao das telas de AutoAtendimento
 *
 */
public class BasicNavegacaoBBean extends BasicBBean {

	
	public static final String OUTCOME_PROSSEGUIR		= "PROSSEGUIR";
	public static final String OUTCOME_ERROR 			= "ERRO";
	public static final String OUTCOME_ERRO_SISTEMICO 	= "ERRO_SISTEMICO";
	
	@Autowired protected ErroBBean erroBBean;

	private static final long serialVersionUID = 2497280032953868297L;

	public static final String prosseguirOutcomeParamName = "outcomeparam";
	
	public static final String retornarOutcomeParamName = "retornoparam";
	
	public BasicNavegacaoBBean(){}

	private String prosseguirOutcome;
	
	private String retornarOutcome;
	
	protected boolean existeMsgErroValidacao;
	
	public boolean isExisteMsgErroValidacao() {
		Iterator iter = FacesContext.getCurrentInstance().getMessages();
		existeMsgErroValidacao = iter.hasNext();
		return existeMsgErroValidacao;
	}

	public void setExisteMsgErroValidacao(boolean existeMsgErroValidacao) {
		this.existeMsgErroValidacao = existeMsgErroValidacao;
	}

	/**
	 * Obtem proxima pagina
	 */
	public String prosseguir(){		
		prosseguirOutcome = getRequestParameter(prosseguirOutcomeParamName);
		
		// verifica se destino da navegacao deve ser alterado
		if(getRequestAttribute(prosseguirOutcomeParamName) != null){
			prosseguirOutcome = getRequestAttribute(prosseguirOutcomeParamName);
		}
		
		if( isExisteMsgErroValidacao() )
			prosseguirOutcome = null;
		
		return prosseguirOutcome;	
	}
	
	/**
	 * Obtem pagina anterior
	 */	
	public String retornar(){
		retornarOutcome =  getRequestParameter(retornarOutcomeParamName);
		if(getRequestAttribute(prosseguirOutcomeParamName) != null){
			retornarOutcome = getRequestAttribute(retornarOutcomeParamName);
		}
		return retornarOutcome;
	}
	
	/**
	 * Altera destino da navegacao
	 */
	protected void modificarOutcome(String prosseguirOutcome){
		getHttpRequest().setAttribute(prosseguirOutcomeParamName, prosseguirOutcome);
	}
	
	/**
	 * Dispara a��o do bot�o prosseguir
	 * @return
	 */
	public String getProsseguirOutcome() {
		return prosseguir();
	}

	public void setProsseguirOutcome(String prosseguirOutcome) {
		this.prosseguirOutcome = prosseguirOutcome;
	}

	/**
	 * Dispara a��o do bot�o retornar
	 * @param action
	 */
	public String getRetornarOutcome() {
		return retornar();
	}

	public void setRetornarOutcome(String retornarOutcome) {
		this.retornarOutcome = retornarOutcome;
	}
	
	@Override
	protected void addMessage(Severity severity, String summary, String details) {
		getFacesContext().addMessage(null, new FacesMessage(severity, summary, details));
	}
	
	/**
	 * Preenche Bean contendo dados do erro sistemico
	 * 
	 * 1 - Retorna outcome padrao para erro sistemico
	 */
	protected String tratarErroSistemico(Exception e) {
		String msg = FacesBundleUtil.getInstance().getString("erro.sistemico");
		addMessage(FacesMessage.SEVERITY_ERROR, msg, e.getMessage());
		erroBBean.setDsErro(e.getMessage()); //FIXME transformar o printStackTrace em lista de erro
		erroBBean.setException(e);
		return OUTCOME_ERRO_SISTEMICO;
	}
	
	/**
	 * Retorna outcome definido ou, se houver ocorrido erro, retorna outcome de erro sistemico
	 */
	protected String forwardOutcome(String outcome){
		if(erroBBean.isShowError()){
			return OUTCOME_ERRO_SISTEMICO;
		}
		return outcome;
	}
	
	/**
	 * M�todo respons�vel por executar um m�todo espec�fico de acordo com os par�metros.
	 * 
	 * @param beanName Nome do Bean � ser executado
	 * @param methodName Nome do m�todo � ser executado dentro do Bean
	 * @param parameters Par�metros que devem ser passados para o m�todo � ser executado dentro do Bean
	 * 
	 * Obs.: Quando o m�todo que ser� executado n�o tiver par�metros de entrada passar um new Object[] {}
	 * 		 para o par�metro parameters.
	 */
	protected void executeMethodByClass(String beanName, String methodName, Object[] parameters) {
	   try{
			Object bean = getFacesContext().getApplication().createValueBinding("#{" + beanName + "}").getValue(getFacesContext());
		
			ReflectionFacade instance = ReflectionFacade.instance(bean);
			instance.chamaMetodo(instance.getMetodo(methodName), parameters);
		} catch(Exception e) {
			this.logger.error("BasicNavegacaoBBean executado com erro.", e);
		}
	}
}
