 package com.prime.infra.security;
 
 import java.util.Date;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.web.context.request.RequestAttributes;
 import org.springframework.web.context.request.RequestContextHolder;
 
 public class SecurityInfo
 {
   public static final String SECURITY_INFO_ATTRIBUTE = "com.prime.infra.authentication.security-info";
   private String userName;
   private String remoteAddr;
   private String systemId;
   private Date loginTimestamp;
/* 22 */   private static final Logger logger = LoggerFactory.getLogger(SecurityInfo.class);
 
   public SecurityInfo() {
   }
 
   public SecurityInfo(String userName, String remoteAddr, String systemId, Date loginTimestamp) {
/* 28 */     this.userName = userName;
/* 29 */     this.remoteAddr = remoteAddr;
/* 30 */     this.systemId = systemId;
/* 31 */     this.loginTimestamp = loginTimestamp;
   }
 
   public String getUserName() {
/* 35 */     return this.userName;
   }
 
   public void setUserName(String userName) {
/* 39 */     this.userName = userName;
   }
 
   public String getRemoteAddr() {
/* 43 */     return this.remoteAddr;
   }
 
   public void setRemoteAddr(String remoteAddr) {
/* 47 */     this.remoteAddr = remoteAddr;
   }
 
   public String getSystemId() {
/* 51 */     return this.systemId;
   }
 
   public void setSystemId(String systemId) {
/* 55 */     this.systemId = systemId;
   }
 
   public Date getLoginTimestamp() {
/* 59 */     return this.loginTimestamp;
   }
 
   public void setLoginTimestamp(Date loginTimestamp) {
/* 63 */     this.loginTimestamp = loginTimestamp;
   }
 
   public static SecurityInfo getCurrent() {
/* 67 */     RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
 
/* 69 */     if (attributes == null) {
/* 70 */       logger.trace("Could not read RequestAttributes...");
/* 71 */       logger.trace("Trying to reach through RequestContextHolder.currentRequestAttributes()...");
/* 72 */       attributes = RequestContextHolder.currentRequestAttributes();
     }
 
/* 75 */     if (attributes == null) {
/* 76 */       logger.debug("Attributes cannot be null, check your web.xml file.");
/* 77 */       logger.warn("Could not retrieve the current securityInfo.");
/* 78 */       return null;
     }
 
/* 81 */     return ((SecurityInfo)attributes.getAttribute("com.prime.infra.authentication.security-info", 1));
   }
 }