 package com.prime.infra.security.authentication;
 
 import java.io.IOException;
 import java.util.HashMap;
 import java.util.Map;
 import org.apache.commons.httpclient.ConnectTimeoutException;
 import org.apache.commons.httpclient.HttpClient;
 import org.apache.commons.httpclient.HttpConnectionManager;
 import org.apache.commons.httpclient.NameValuePair;
 import org.apache.commons.httpclient.methods.PostMethod;
 import org.apache.commons.httpclient.params.HttpClientParams;
 import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public class PortalAuthenticator
   implements Authenticator
 {
/*  35 */   private static Logger logger = LoggerFactory.getLogger(PortalAuthenticator.class);
   public static final String PARAMETER_ID_SERVICO = "hdnIdServico";
   public static final String PARAMETER_USUARIO = "hdnUsuario";
   public static final String PARAMETER_TIMESTAMP = "hdnTimeStamp";
   public static final String PARAMETER_TICKET_SEGURANCAO = "hdnTicket";
   private static final String RETORNO = "retorno";
   private static final String COD_ERRO = "codErro";
   private static final String MSG_ERRO = "msgErro";
   private String hdnIdServico;
   private String hdnUsuario;
   private String hdnTimeStamp;
   private String hdnTicket;
   private String urlAutenticacao;
   private int timeOutAutenticacao;
 
   public void login()
     throws AuthenticationException
   {
     try
     {
/* 105 */       HttpClientParams httpClientParams = new HttpClientParams();
/* 106 */       httpClientParams.setConnectionManagerTimeout(this.timeOutAutenticacao);
/* 107 */       HttpClient client = new HttpClient(httpClientParams);
 
/* 109 */       client.getHttpConnectionManager().getParams().setConnectionTimeout(this.timeOutAutenticacao);
/* 110 */       client.getHttpConnectionManager().getParams().setSoTimeout(this.timeOutAutenticacao);
 
/* 112 */       PostMethod postMethod = new PostMethod(this.urlAutenticacao);
 
/* 114 */       NameValuePair[] data = { new NameValuePair("hdnIdServico", getHdnIdServico()), new NameValuePair("hdnUsuario", getHdnUsuario()), new NameValuePair("hdnTimeStamp", getHdnTimeStamp()), new NameValuePair("hdnTicket", getHdnTicket()) };
 
/* 121 */       postMethod.setRequestBody(data);
/* 122 */       logger.debug("Fazendo POST para [" + this.urlAutenticacao + "]");
/* 123 */       int statusCode = client.executeMethod(postMethod);
 
/* 126 */       checkStatusHTTP(statusCode);
 
/* 129 */       byte[] response = postMethod.getResponseBody();
/* 130 */       Map responseValues = getResponseValues(response);
 
/* 132 */       boolean loginValido = Boolean.valueOf((String)responseValues.get("retorno")).booleanValue();
/* 133 */       if (loginValido) {
/* 134 */         logger.debug("Portal respondeu OK!");
/* 135 */         return;
       }
/* 137 */       logger.debug("Portal respondeu NAO OK!");
 
/* 139 */       String codErro = (String)responseValues.get("codErro");
/* 140 */       String msgErro = (String)responseValues.get("msgErro");
/* 141 */       logger.debug("Codigo erro: {} - {}", codErro, msgErro);
 
/* 143 */       String msgLog = "autenticação negada pelo portal corporativo Prime " + getDadosLogin() + "\n\t\t\t\t\tcódigo de erro: " + codErro + "\n\t\t\t\t\t\tmensagem de erro: " + msgErro + " uma exceção AutenticacaoInvalidaException foi lançada";
 
/* 146 */       logger.error(msgLog);
     }
     catch (ConnectTimeoutException e) {
/* 149 */       logger.error("ConnectTimeoutException executando o método login()");
/* 150 */       throw new AuthenticationException(e);
     }
     catch (IOException e) {
/* 153 */       logger.error("IOException executando o método login()", e);
/* 154 */       throw new AuthenticationException(e);
     }
     catch (RuntimeException e) {
/* 157 */       logger.error("RuntimeExeception executando o método login()", e);
/* 158 */       throw new AuthenticationException(e);
     }
 
/* 161 */     throw new AuthenticationException("Erro na autenticação junto ao portal corporativo, usuário: " + getHdnUsuario() + "idServico:" + getHdnIdServico());
   }
 
   private void checkStatusHTTP(int statusCode)
     throws AuthenticationException
   {
     String msgLog;
/* 173 */     if (statusCode == 404) {
/* 174 */       msgLog = "O recurso para autenticação corporativa não foi localizado" + getDadosLogin();
/* 175 */       logger.error(msgLog);
/* 176 */       throw new AuthenticationException(msgLog);
     }
 
/* 180 */     if (statusCode != 200) {
/* 181 */       msgLog = "Erro HTTP chamando o endereço de autenticação " + getDadosLogin();
/* 182 */       logger.error(msgLog);
/* 183 */       throw new AuthenticationException(msgLog);
     }
   }
 
   private Map<String, String> getResponseValues(byte[] response)
     throws AuthenticationException
   {
/* 197 */     logger.debug("iniciando método getResponseValues");
 
/* 199 */     Map valuesMap = new HashMap();
     try {
/* 201 */       String resp = new String(response);
 
/* 203 */       String[] nvps = resp.split("\\|");
/* 204 */       int length = nvps.length;
/* 205 */       for (int i = 0; i < length; ++i) {
/* 206 */         String[] nvp = nvps[i].split("=");
/* 207 */         if (nvp.length > 1) {
/* 208 */           valuesMap.put(nvp[0], nvp[1]);
         }
         else
/* 211 */           valuesMap.put(nvp[0], null);
       }
     }
     catch (RuntimeException e)
     {
/* 216 */       logger.error("RuntimeException executando o método getResponseValues " + getDadosLogin());
/* 217 */       throw new AuthenticationException(e);
     }
     finally {
/* 220 */       logger.debug("finalizando método getResponseValues");
     }
/* 222 */     return valuesMap;
   }
 
   private String getDadosLogin()
   {
/* 231 */     return "\n\t\t\t\t\tusuário: " + getHdnUsuario() + "\n\t\t\t\t\tisServico: " + getHdnIdServico() + "\n\t\t\t\t\ttimestamp: " + getHdnTimeStamp() + "\n\t\t\t\t\turlAutenticação: " + this.urlAutenticacao;
   }
 
   public String getHdnIdServico()
   {
/* 240 */     return this.hdnIdServico;
   }
 
   public void setHdnIdServico(String hdnIdServico)
   {
/* 247 */     this.hdnIdServico = hdnIdServico;
   }
 
   public String getHdnTicket()
   {
/* 254 */     return this.hdnTicket;
   }
 
   public void setHdnTicket(String hdnTicket)
   {
/* 261 */     this.hdnTicket = hdnTicket;
   }
 
   public String getHdnTimeStamp()
   {
/* 268 */     return this.hdnTimeStamp;
   }
 
   public void setHdnTimeStamp(String hdnTimeStamp)
   {
/* 275 */     this.hdnTimeStamp = hdnTimeStamp;
   }
 
   public String getHdnUsuario()
   {
/* 282 */     return this.hdnUsuario;
   }
 
   public void setHdnUsuario(String hdnUsuario)
   {
/* 289 */     this.hdnUsuario = hdnUsuario;
   }
 
   public String getUrlAutenticacao() {
/* 293 */     return this.urlAutenticacao;
   }
 
   public void setUrlAutenticacao(String urlAutenticacao) {
/* 297 */     this.urlAutenticacao = urlAutenticacao;
   }
 
   public int getTimeOutAutenticacao() {
/* 301 */     return this.timeOutAutenticacao;
   }
 
   public void setTimeOutAutenticacao(int timeOutAutenticacao) {
/* 305 */     this.timeOutAutenticacao = timeOutAutenticacao;
   }
 }