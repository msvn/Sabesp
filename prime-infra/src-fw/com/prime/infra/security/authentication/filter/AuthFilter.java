 package com.prime.infra.security.authentication.filter;
 
 import com.prime.infra.security.SecurityInfo;
 import com.prime.infra.security.authentication.PortalAuthenticator;
 import java.io.IOException;
 import java.util.Date;
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
 
 public class AuthFilter
   implements Filter
 {
/*  29 */   private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);
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
/*  77 */     HttpServletRequest httpRequest = (HttpServletRequest)req;
/*  78 */     LOGGER.debug("URI:" + httpRequest.getRequestURI());
 
/*  80 */     if (!(isAuthenticated(httpRequest.getSession(true))))
     {
/*  83 */       String userName = getUserName(httpRequest);
/*  84 */       String systemId = getServico(httpRequest);
/*  85 */       String timestamp = getTimeStamp(httpRequest);
/*  86 */       String ticket = getTicket(httpRequest);
 
/*  88 */       if ((userName == null) || (systemId == null) || (timestamp == null) || (ticket == null)) {
/*  89 */         String message = "Requisição mal formada. Problemas com Ticket de Seguranca. Recurso [" + httpRequest.getRequestURI() + "].";
 
/*  91 */         handleException(resp, message);
/*  92 */         return;
       }
 
/*  96 */       PortalAuthenticator authenticator = new PortalAuthenticator();
/*  97 */       authenticator.setTimeOutAutenticacao(this.timeout);
/*  98 */       authenticator.setUrlAutenticacao(this.autenticationURL);
/*  99 */       authenticator.setHdnIdServico(systemId);
/* 100 */       authenticator.setHdnTicket(ticket);
/* 101 */       authenticator.setHdnTimeStamp(timestamp);
/* 102 */       authenticator.setHdnUsuario(userName);
       try
       {
/* 105 */         authenticator.login();
/* 106 */         LOGGER.debug("Autenticacao OK");
 
/* 108 */         SecurityInfo securityInfo = new SecurityInfo(userName, httpRequest.getRemoteAddr(), systemId, new Date());
 
/* 111 */         HttpSession session = httpRequest.getSession();
/* 112 */         session.setAttribute("com.prime.infra.authentication.security-info", securityInfo);
/* 113 */         LOGGER.debug("Sessao criada!");
       }
       catch (Throwable e) {
/* 116 */         LOGGER.debug("Usuario NAO autenticado!");
/* 117 */         handleException(resp, e.getMessage());
/* 118 */         return;
       }
     }
 
/* 122 */     if ((httpRequest.getMethod().equalsIgnoreCase("post")) && (httpRequest.getRequestURI().endsWith(this.firstPage)) && (null != getTicket(httpRequest)))
     {
/* 124 */       LOGGER.debug("Chamando a primeira pagina a partir do portal");
/* 125 */       ((HttpServletResponse)resp).sendRedirect(this.firstPage);
/* 126 */       return;
     }
 
/* 129 */     MDC.put("user", SecurityInfo.getCurrent().getUserName());
     try {
/* 131 */       filterChain.doFilter(req, resp);
     }
     finally {
/* 134 */       MDC.remove("user");
     }
   }
 
   private void handleException(ServletResponse resp, String message) throws ServletException, IOException {
/* 139 */     LOGGER.error(message);
/* 140 */     if (this.redirectURL == null) {
/* 141 */       throw new ServletException(message);
     }
 
/* 144 */     LOGGER.info("Redirecting to " + this.redirectURL);
/* 145 */     HttpServletResponse response = (HttpServletResponse)resp;
/* 146 */     response.sendRedirect(this.redirectURL);
   }
 
   private String getUserName(HttpServletRequest httpServletRequest)
   {
/* 151 */     String userName = httpServletRequest.getParameter("hdnUsuario");
/* 152 */     if (userName == null) {
/* 153 */       LOGGER.debug("Parametro nao encontrado [hdnUsuario]");
     }
/* 155 */     return userName;
   }
 
   private String getServico(HttpServletRequest httpServletRequest) {
/* 159 */     String servico = httpServletRequest.getParameter("hdnIdServico");
/* 160 */     if (servico == null) {
/* 161 */       LOGGER.debug("Parametro nao encontrado [hdnIdServico]");
     }
/* 163 */     return servico;
   }
 
   private String getTimeStamp(HttpServletRequest httpServletRequest) {
/* 167 */     String timeStamp = httpServletRequest.getParameter("hdnTimeStamp");
/* 168 */     if (timeStamp == null) {
/* 169 */       LOGGER.debug("Parametro nao encontrado [hdnTimeStamp]");
     }
/* 171 */     return timeStamp;
   }
 
   private String getTicket(HttpServletRequest httpServletRequest) {
/* 175 */     String ticket = httpServletRequest.getParameter("hdnTicket");
/* 176 */     if (ticket == null) {
/* 177 */       LOGGER.debug("Parametro nao encontrado [hdnTicket]");
     }
/* 179 */     return ticket;
   }
 
   public boolean isAuthenticated(HttpSession httpSession) {
/* 183 */     SecurityInfo securityInfo = (SecurityInfo)httpSession.getAttribute("com.prime.infra.authentication.security-info");
 
/* 185 */     if (securityInfo == null) {
/* 186 */       LOGGER.debug("SecurityInfo not found");
/* 187 */       return false;
     }
 
/* 190 */     return (securityInfo.getLoginTimestamp() != null);
   }
 
   public void destroy()
   {
   }
 
   public void init(FilterConfig config)
     throws ServletException
   {
/* 201 */     this.redirectURL = config.getInitParameter("com.prime.infra.authentication.redirect.url");
 
/* 204 */     this.autenticationURL = getInitParameter("com.prime.infra.authentication.url", config);
/* 205 */     this.timeout = getIntegerInitParameter("com.prime.infra.authentication.timeout", config);
/* 206 */     this.firstPage = getInitParameter("com.prime.infra.authentication.firstPage", config);
   }
 
   private int getIntegerInitParameter(String key, FilterConfig config) {
/* 210 */     String param = getInitParameter(key, config).trim();
     try {
/* 212 */       return Integer.parseInt(param);
     }
     catch (NumberFormatException e) {
/* 215 */       String message = "Valor[" + param + "] inválido para key[" + key + "]. Favor verificar o web.xml";
/* 216 */       LOGGER.error(message);
/* 217 */       throw new RuntimeException(message);
     }
   }
 
   private String getInitParameter(String key, FilterConfig config) {
/* 222 */     LOGGER.debug("Recuperando propriedade[" + key + "]");
/* 223 */     String result = config.getInitParameter(key);
/* 224 */     if ((null == result) || ("".equals(result))) {
/* 225 */       String message = "Erro nas configurações do filtro para a key[" + key + "]. Favor verificar o web.xml";
/* 226 */       LOGGER.error(message);
/* 227 */       throw new RuntimeException(message);
     }
/* 229 */     LOGGER.debug("Valor[" + result + "] recuperado com sucesso.");
/* 230 */     return result;
   }
 }