 package com.prime.infra.security.authentication.filter;
 
 import com.prime.infra.security.SecurityInfo;
 import com.prime.infra.security.authentication.PortalAuthenticator;
import com.prime.infra.util.WrapperUtils;

 import java.io.IOException;
 import java.util.Date;
import java.util.HashMap;

 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
 
 public class LDAPAuthFilter
   implements Filter
 {
   private static final Logger LOGGER = LoggerFactory.getLogger(LDAPAuthFilter.class);
   private static final String PARAMETER_REDIRECT_URL = "com.prime.infra.authentication.redirect.url";
   private static final String PARAMETER_AUTHENTICATION_URL = "com.prime.infra.authentication.url";
   private static final String PARAMETER_TIMEOUT = "com.prime.infra.authentication.timeout";
   private static final String PARAMETER_FIRST_PAGE = "com.prime.infra.authentication.firstPage";
   private String redirectURL;
   private String autenticationURL;
   private int timeout;
   private String firstPage;
 
   public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
     throws IOException, ServletException
   {
     HttpServletRequest httpRequest = (HttpServletRequest)req;
     LOGGER.debug("URI:" + httpRequest.getRequestURI());

     if (!(isAuthenticated(httpRequest.getSession(true))))
     {
       String userName = getUserName(httpRequest);
       String userPass = getUserPass(httpRequest);
       String systemId = getServico(httpRequest);

       if (validateLogin(userName,userPass)) {
         String message = "Requisicao mal formada. Problemas com Ticket de Seguranca. Recurso [" + httpRequest.getRequestURI() + "].";
         handleException(resp, message);
         return;
       }
 
//       PortalAuthenticator authenticator = new PortalAuthenticator();
//       authenticator.setTimeOutAutenticacao(this.timeout);
//       authenticator.setUrlAutenticacao(this.autenticationURL);
//       authenticator.setHdnIdServico(systemId);
//       authenticator.setHdnTicket(ticket);
//       authenticator.setHdnTimeStamp(timestamp);
//       authenticator.setHdnUsuario(userName);
       try
       {
//         authenticator.login();
         LOGGER.debug("Autenticacao OK");

         SecurityInfo securityInfo = new SecurityInfo(userName, httpRequest.getRemoteAddr(), systemId, new Date());

         HttpSession session = httpRequest.getSession();
         session.setAttribute("com.prime.infra.authentication.security-info", securityInfo);
         LOGGER.debug("Sessao criada!");
       }
       catch (Throwable e) {
         LOGGER.debug("Usuario NAO autenticado!");
         handleException(resp, e.getMessage());
         return;
       }
     }
 
//     if ((httpRequest.getMethod().equalsIgnoreCase("post")))// && (httpRequest.getRequestURI().endsWith(this.firstPage)) && (null != getTicket(httpRequest)))
//     {
//       LOGGER.debug("Chamando a primeira pagina a partir do portal");
//       ((HttpServletResponse)resp).sendRedirect(this.firstPage);
//       return;
//     }
 
     MDC.put("user", SecurityInfo.getCurrent().getUserName());
     try {
       filterChain.doFilter(req, resp);
     }
     finally {
       MDC.remove("user");
     }
   }
 
   
   private  boolean validateLogin(String userName,String userPass) {
	HashMap<String, String> lista = new HashMap<String, String>();
	lista.put("anogueira.prime", "sabesp");
	lista.put("camilaaraujo", "sabesp");
	lista.put("celestefernandes", "sabesp");
	lista.put("dtonon", "sabesp");
	lista.put("hdias.prime", "sabesp");
	lista.put("mvilaca.prime", "sabesp");
	lista.put("rcorrea", "sabesp");
	lista.put("rosanapinheiro", "sabesp");
	lista.put("vraphael", "sabesp");
	lista.put("wmartins.prime", "sabesp");
	lista.put("admin", "sabesp");
	
	if(WrapperUtils.isNotNull(userName) && WrapperUtils.isNotNull(userPass)){
		String aux = lista.get(userName.toLowerCase());
		if(WrapperUtils.isNotNull(aux)){
			if(aux.equalsIgnoreCase(userPass))
			return false;
		}
	}else {
		return true;	
	}
	return true;
}

private void handleException(ServletResponse resp, String message) throws ServletException, IOException {
     LOGGER.error(message);
     if (this.redirectURL == null) {
       throw new ServletException(message);
     }
 
     LOGGER.info("Redirecting to " + this.redirectURL);
     HttpServletResponse response = (HttpServletResponse)resp;
     response.sendRedirect(this.redirectURL);
   }
 
   private String getUserName(HttpServletRequest httpServletRequest)
   {
     String userName = httpServletRequest.getParameter("login");
     if (userName == null) {
       LOGGER.debug("Parametro nao encontrado [login]");
     }
     return userName;
   }
   private String getUserPass(HttpServletRequest httpServletRequest)
   {
     String userName = httpServletRequest.getParameter("senha");
     if (userName == null) {
       LOGGER.debug("Parametro nao encontrado [senha]");
     }
     return userName;
   }
   private String getServico(HttpServletRequest httpServletRequest) {
     String servico = httpServletRequest.getParameter("hdnIdServico");
     if (servico == null) {
       LOGGER.debug("Parametro nao encontrado [hdnIdServico]");
     }
     return servico;
   }
 
   private String getTimeStamp(HttpServletRequest httpServletRequest) {
     String timeStamp = httpServletRequest.getParameter("hdnTimeStamp");
     if (timeStamp == null) {
       LOGGER.debug("Parametro nao encontrado [hdnTimeStamp]");
     }
     return timeStamp;
   }
 
   private String getTicket(HttpServletRequest httpServletRequest) {
     String ticket = httpServletRequest.getParameter("hdnTicket");
     if (ticket == null) {
       LOGGER.debug("Parametro nao encontrado [hdnTicket]");
     }
    return ticket;
   }
 
   public boolean isAuthenticated(HttpSession httpSession) {
     SecurityInfo securityInfo = (SecurityInfo)httpSession.getAttribute("com.prime.infra.authentication.security-info");

     if (securityInfo == null) {
       LOGGER.debug("SecurityInfo not found");
       return false;
     }
 
     return (securityInfo.getLoginTimestamp() != null);
   }
 
   public void destroy()
   {
   }
 
   public void init(FilterConfig config)
     throws ServletException
   {
     this.redirectURL = "/agvirtualadm/index.html";//config.getInitParameter("com.prime.infra.authentication.redirect.url");

//     this.autenticationURL = getInitParameter("com.prime.infra.authentication.url", config);
//     this.timeout = getIntegerInitParameter("com.prime.infra.authentication.timeout", config);
//     this.firstPage = getInitParameter("com.prime.infra.authentication.firstPage", config);
   }
 
   private int getIntegerInitParameter(String key, FilterConfig config) {
     String param = getInitParameter(key, config).trim();
     try {
       return Integer.parseInt(param);
     }
     catch (NumberFormatException e) {
       String message = "Valor[" + param + "] inválido para key[" + key + "]. Favor verificar o web.xml";
       LOGGER.error(message);
       throw new RuntimeException(message);
     }
   }
 
   private String getInitParameter(String key, FilterConfig config) {
     LOGGER.debug("Recuperando propriedade[" + key + "]");
     String result = config.getInitParameter(key);
     if ((null == result) || ("".equals(result))) {
       String message = "Erro nas configurações do filtro para a key[" + key + "]. Favor verificar o web.xml";
       LOGGER.error(message);
       throw new RuntimeException(message);
     }
     LOGGER.debug("Valor[" + result + "] recuperado com sucesso.");
     return result;
   }
 }