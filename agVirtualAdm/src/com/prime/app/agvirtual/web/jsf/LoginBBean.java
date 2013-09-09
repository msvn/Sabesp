package com.prime.app.agvirtual.web.jsf;


import java.io.Serializable;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.to.LoginTO;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.BasicBBean;

/**
 * BackBean com controles da classe Notícia
 */
@Component
@Scope("session")
public class LoginBBean extends BasicBBean implements Serializable {

	private static final long serialVersionUID = -2924497964816710919L;
	
	private static final Logger agvlogger = LoggerFactory.getLogger(LoginBBean.class);
	
	static final String USER = "userSession"; 
	
	private String user;
	
	private String senha;

    public String logar() {
    	agvlogger.info("==== Logando usuario Mr Freeze no sistema AgVirtual Administração ====");
    	LoginTO loginTO =  new LoginTO();
    	loginTO.setCdUser(WrapperUtils.toLong("1"));
    	loginTO.setUser("Mr.Freeze");
    	loginTO.setCodSessao("123");
    	loginTO.setDataInicioSessao(new Date());
    	loginTO.setEmail("mrFreeze@zera.com.br");
    	
    	setSessionAttribute(USER, loginTO);
    	getHttpRequest().setAttribute("user", "MR.Freeze");
    	
    	agvlogger.info("==== Usuário logado com sucesso AgVirtual Administração ====");
		return "NOTICIA";
    }

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
	public void logOff(ActionEvent event){
		//TODO ao invalidar a sessão verificar como não invalidar os bean no scopo.
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.invalidate();
		HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
		try {
			response.sendRedirect("/agvirtualadm/index.html");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}
