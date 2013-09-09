 package com.prime.infra.web.jsf;
 
 import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;

 public abstract class BasicBBean implements BeanNameAware, Serializable
 {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Logger logger;
    protected static final String SUCCESS = "Success";
    protected static final String AGVINICIAL = "PAGINAINICIAL";
    protected static final String RELATORIO_ATENDIMENTO = "RELATORIOATENDIMENTO";
    protected static final String TABELA_PRECOS_SERVICOS = "TABELAPRECOSSERVICOS";
    
	//Constantes utilizadas para obter os dados da navegação(Migalha de Pão)
    protected static final String PARAMETRO_CODIGO_MENU = "cdMenu";
	protected static final String PARAMETRO_DESCRICAO_MENU = "dsMenu";
	protected static final String PARAMETRO_OUTCOME_MENU = "outcomeMenu";
	protected static final String PARAMETRO_CODIGO_SUB_MENU = "cdSubMenu";
	protected static final String PARAMETRO_DESCRICAO_SUB_MENU = "dsSubMenu";
	protected static final String PARAMETRO_OUTCOME_SUB_MENU = "outcomeSubMenu";
	protected static final String PARAMETRO_CODIGO_AUTO_ATENDIMENTO = "cdAutoAtendimento";

	protected static final String EMPTY = "";
    
    
    protected static final String ERROR = "Error";
    public transient boolean fromSerialization;
    private String beanName;
    
    
    public TimeZone getTimeZone() {
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");  
        TimeZone.setDefault(tz);  
		return tz;
	}
 
   public BasicBBean()
   {
/*  39 */     this.logger = LoggerFactory.getLogger(super.getClass());
   }
 
   protected <T> T getBind(String managedBeanName)
   {
/*  54 */     return (T) getFacesContext().getApplication().createValueBinding(managedBeanName).getValue(getFacesContext());
   }
 
   protected void addMessage(FacesMessage.Severity severity, String summary, String details)
   {
/*  65 */     getFacesContext().addMessage(summary, new FacesMessage(severity, summary, details));
   }
 
   protected FacesContext getFacesContext()
   {
/*  74 */     return FacesContext.getCurrentInstance();
   }
 
   protected HttpServletRequest getHttpRequest()
   {
/*  83 */     return ((HttpServletRequest)getFacesContext().getExternalContext().getRequest());
   }
 
   protected HttpServletResponse getHttpResponse()
   {
/*  92 */     return ((HttpServletResponse)getFacesContext().getExternalContext().getResponse());
   }
 
   protected HttpSession getHttpSession(boolean create)
   {
/* 103 */     return ((HttpSession)getFacesContext().getExternalContext().getSession(create));
   }
 
   protected <T> T getRequestAttribute(String attributeName)
   {
/* 115 */     return (T)getHttpRequest().getAttribute(attributeName);
   }
 
   protected String getRequestParameter(String paramName)
   {
/* 125 */     return getHttpRequest().getParameter(paramName);
   }
 
   protected <T> T getSessionAttribute(String attributeName)
   {
/* 137 */     return (T)getHttpSession(true).getAttribute(attributeName);
   }
 
   protected void setSessionAttribute(String attributeName, Object attributeValue)
   {
/* 147 */     getHttpSession(true).setAttribute(attributeName, attributeValue);
   }
 
   protected <T> T getAttribute(ActionEvent event, String attName)
   {
/* 160 */     return (T)event.getComponent().getAttributes().get(attName);
   }
 
   public void setBeanName(String name) {
/* 164 */     this.beanName = name;
   }
 
   public String getBeanName() {
/* 168 */     return this.beanName;
   }
 
   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 172 */     in.defaultReadObject();
/* 173 */     this.fromSerialization = true;
   }
   
   protected String getRequestParameterMap(String nomeAtributo) {
	   if(nomeAtributo == null){
		   return null;
	   }
	    Map map = getFacesContext().getExternalContext().getRequestParameterMap();
	    String atributo = (String) map.get(nomeAtributo);
	    return atributo;
	}

 }