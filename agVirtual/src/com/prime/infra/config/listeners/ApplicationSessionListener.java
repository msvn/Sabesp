package com.prime.infra.config.listeners;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.prime.app.agvirtual.web.jsf.AtendimentoBBean;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
public class ApplicationSessionListener implements HttpSessionListener,HttpSessionActivationListener{
	private static final Logger logger = LoggerFactory.getLogger(ApplicationSessionListener.class);
	
	private int totalActiveSessions = 0;
	
	public ApplicationSessionListener() {}
	
    public void sessionCreated(HttpSessionEvent arg0) {
    	totalActiveSessions++;
    	logger.debug("Numero de sessoes ativas: " + totalActiveSessions);
    }
    
	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent e) {
    	logger.info("Inicio ApplicationSessionListener->sessionDestroyed");
    	totalActiveSessions--;
    	
    	HttpSession session = e.getSession();
    	
    	WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
    	
    	if(ctx==null){
    		logger.error("Atendimento nao finalizado por TIME-OUT. Spring Context nao encontrado!");
    		return;
    	}
    	
    	AtendimentoBBean atendimentoBBean = null;
    	try{
	    	atendimentoBBean = (AtendimentoBBean)ctx.getBean("atendimentoBBean");
    	}catch(Exception e1){
    		e1.printStackTrace();
    		logger.error("Erro ao obter atendimentoBBean." + e1);
    	} 
    	
    	if(atendimentoBBean!=null){
    		atendimentoBBean.concluirAtendimentoTimeOut();
    		logger.info("Atendimento concluido por motivo time-out.");
    	}else{
    		logger.error("Atendimento nao finalizado por TIME-OUT. ["+ AtendimentoBBean.BEAN_NAME +"] nao encontrado!");
    	}
    	
    	logger.debug("Numero de sessoes ativas = [" + totalActiveSessions + "]");
    }

	public void sessionDidActivate(HttpSessionEvent arg0) {}

	public void sessionWillPassivate(HttpSessionEvent arg0) {}	
	
}
