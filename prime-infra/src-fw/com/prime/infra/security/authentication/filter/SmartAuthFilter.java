 package com.prime.infra.security.authentication.filter;
 
 import com.prime.infra.security.SecurityInfo;
 import java.io.IOException;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import org.apache.commons.httpclient.HttpClient;
 import org.apache.commons.httpclient.HttpConnectionManager;
 import org.apache.commons.httpclient.NameValuePair;
 import org.apache.commons.httpclient.methods.PostMethod;
 import org.apache.commons.httpclient.params.HttpClientParams;
 import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public class SmartAuthFilter
   implements Filter
 {
/*  36 */   private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);
   private static final String LOGIN_PAGE_URI = "/pages/login.iceface";
   private static final String SINAL_PARAMETRO = "\\|";
   private static final String SINAL_ATRIBUICAO = "=";
   public static final String RETORNO = "retorno";
   public static final String COD_ERRO = "codErro";
   public static final String MSG_ERRO = "msgErro";
   public static final String ATT_STATUS_AUTENTICACAO = "ATT_STATUS_AUTENTICACAO";
   public static final String ST_AUTENTICADO = "ST_AUTENTICADO";
/*  77 */   public static String SIGLA = "SIGLA";
 
/*  82 */   public static String URL_AUTENTICACAO_TICKET = "http://localhost:8080/authWeb/CheckAuth";
 
/*  90 */   public static String MODO_AUTENTICACAO = "LOCAL";
   protected final Logger logger;
   private HttpServletRequest req_http;
   private HttpServletResponse resp_http;
 
   public SmartAuthFilter()
   {
/*  93 */     this.logger = LoggerFactory.getLogger(super.getClass());
   }
 
   public void init(FilterConfig filterConfig)
     throws ServletException
   {
/* 103 */     URL_AUTENTICACAO_TICKET = filterConfig.getInitParameter("com.prime.infra.authentication.url");
/* 104 */     MODO_AUTENTICACAO = filterConfig.getInitParameter("com.prime.infra.authentication.modo");
/* 105 */     SIGLA = filterConfig.getInitParameter("com.prime.infra.authentication.sigla");
 
/* 107 */     this.logger.info("URL de autenticação TICKET => " + URL_AUTENTICACAO_TICKET);
/* 108 */     this.logger.info("Modo de autenticação => " + MODO_AUTENTICACAO);
/* 109 */     this.logger.info("SIGLA => " + SIGLA);
   }
 
   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
     throws IOException, ServletException
   {
/* 118 */     this.logger.debug("doFilter");
     try
     {
/* 122 */       this.req_http = ((HttpServletRequest)req);
/* 123 */       this.resp_http = ((HttpServletResponse)res);
 
/* 125 */       if (!(isAuthenticated(this.req_http)))
       {
/* 127 */         if (MODO_AUTENTICACAO.equalsIgnoreCase("REMOTE"))
         {
           String msgLog;
           ServletException ex;
/* 129 */           boolean loginValido = false;
 
/* 131 */           int timeOutAutenticacao = 30;
 
/* 133 */           HttpClientParams httpClientParams = new HttpClientParams();
/* 134 */           httpClientParams.setConnectionManagerTimeout(timeOutAutenticacao);
/* 135 */           HttpClient client = new HttpClient(httpClientParams);
 
/* 137 */           client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOutAutenticacao);
 
/* 140 */           PostMethod postMethod = new PostMethod(URL_AUTENTICACAO_TICKET);
 
/* 142 */           NameValuePair[] data = { new NameValuePair("hdnIdServico", this.req_http.getParameter("hdnIdServico")), new NameValuePair("hdnUsuario", this.req_http.getParameter("hdnUsuario")), new NameValuePair("hdnTimeStamp", this.req_http.getParameter("hdnTimeStamp")), new NameValuePair("hdnTicket", this.req_http.getParameter("hdnTicket")) };
 
/* 149 */           postMethod.setRequestBody(data);
/* 150 */           int statusCode = client.executeMethod(postMethod);
 
/* 152 */           byte[] response = postMethod.getResponseBody();
 
/* 154 */           Map responseValues = getResponseValues(response);
/* 155 */           int codErro = Integer.parseInt((String)responseValues.get("codErro"));
/* 156 */           loginValido = new Boolean((String)responseValues.get("retorno")).booleanValue();
/* 157 */           String msgErro = (String)responseValues.get("msgErro");
 
/* 160 */           if (codErro != 0) {
/* 161 */             msgLog = "Erro na chamada de autenticação junto ao portal corporativo com os parâmetros\n \t\t\t\tcódigo de erro: " + codErro + "\n \t\t\t\tmensagem de erro: " + msgErro;
 
/* 164 */             ex = new ServletException(msgLog);
/* 165 */             req.setAttribute("exception", ex);
/* 166 */             throw ex;
           }
 
/* 170 */           if (!(loginValido)) {
/* 171 */             msgLog = "Autenticação negada pelo portal corporativo Prime \n \t\t\t\tcódigo de erro: " + codErro + "\n \t\t\t\tmensagem de erro: " + msgErro;
 
/* 174 */             ex = new ServletException(msgLog);
/* 175 */             req.setAttribute("exception", ex);
/* 176 */             throw ex;
           }
 
/* 179 */           setStatusAtenticacao(this.req_http.getParameter("hdnUsuario"), this.req_http.getParameter("hdnIdServico"), this.req_http);
 
/* 181 */           chain.doFilter(req, res);
         }
         else
         {
/* 186 */           String logged_user = this.req_http.getRemoteUser();
 
/* 188 */           if (logged_user != null)
           {
/* 190 */             logged_user = logged_user.substring(logged_user.indexOf("\\") + 1);
 
/* 192 */             setStatusAtenticacao(logged_user, SIGLA, this.req_http);
 
/* 194 */             chain.doFilter(req, res);
           }
           else
           {
/* 198 */             throw new ServletException("Usuário não encontrado, header REMOTE_USER = null.");
           }
 
         }
 
       }
       else
       {
/* 206 */         chain.doFilter(req, res);
       }
 
     }
     catch (Exception e)
     {
/* 213 */       throw new ServletException("Security Filter error, " + e.getMessage());
     }
   }
 
   public void destroy()
   {
   }
 
   private Map<String, String> getResponseValues(byte[] response)
   {
/* 237 */     Map valuesMap = new HashMap();
     try {
/* 239 */       String resp = new String(response);
 
/* 241 */       String[] nvps = resp.split("\\|");
/* 242 */       int length = nvps.length;
/* 243 */       for (int i = 0; i < length; ++i) {
/* 244 */         String[] nvp = nvps[i].split("=");
/* 245 */         if (nvp.length > 1)
/* 246 */           valuesMap.put(nvp[0], nvp[1]);
         else
/* 248 */           valuesMap.put(nvp[0], null);
       }
     }
     catch (RuntimeException e)
     {
/* 253 */       return null;
     }
 
/* 256 */     return valuesMap;
   }
 
   private void setStatusAtenticacao(String userName, String sigla, HttpServletRequest httpRequest)
   {
/* 264 */     HttpSession session = this.req_http.getSession();
 
/* 266 */     SecurityInfo securityInfo = new SecurityInfo(userName, httpRequest.getRemoteAddr(), sigla, new Date());
 
/* 268 */     this.logger.debug("Setting security info " + securityInfo + " on session...");
/* 269 */     session.setAttribute("com.prime.infra.authentication.security-info", securityInfo);
/* 270 */     this.logger.debug("Security info set!");
   }
 
   public boolean isAuthenticated(HttpServletRequest req)
   {
/* 281 */     SecurityInfo securityInfo = (SecurityInfo)req.getSession(true).getAttribute("com.prime.infra.authentication.security-info");
 
/* 283 */     if (securityInfo == null) {
/* 284 */       LOGGER.debug("SecurityInfo not found");
/* 285 */       return false;
     }
 
/* 288 */     return (securityInfo.getLoginTimestamp() != null);
   }
 }