 package com.prime.infra.audit;
 
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public class DefaultAuditHandler
   implements AuditHandler
 {
   private static final String AUDIT_LOGGER_NAME = "AUDIT";
 
   public void logEvent(AuditInfo auditInfo)
   {
/* 17 */     StringBuilder sb = new StringBuilder();
/* 18 */     for (Object o : auditInfo.keySet()) {
/* 19 */       String key = (String)o;
/* 20 */       String value = auditInfo.get(key).toString();
 
/* 22 */       sb.append(key);
/* 23 */       sb.append('=');
/* 24 */       sb.append(value);
/* 25 */       sb.append(' ');
     }
 
/* 28 */     Logger logger = LoggerFactory.getLogger("AUDIT");
/* 29 */     logger.info("- " + sb);
   }
 }