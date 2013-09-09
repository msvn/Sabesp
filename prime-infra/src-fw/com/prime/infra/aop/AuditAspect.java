 package com.prime.infra.aop;
 
 import com.prime.infra.audit.AuditInfo;
 import com.prime.infra.audit.AuditTrail;
 import com.prime.infra.security.SecurityInfo;
 import org.aspectj.lang.ProceedingJoinPoint;
 import org.aspectj.lang.annotation.Around;
 import org.aspectj.lang.annotation.Aspect;
 import org.joda.time.DateTime;
 import org.slf4j.Logger;
 import org.springframework.core.Ordered;
 
 @Aspect
 public class AuditAspect
   implements Ordered
 {
   public int getOrder()
   {
/* 22 */     return 200;
   }
 
   @Around("com.prime.infra.aop.FrameworkPointcuts.businessServiceImpl() && @annotation(com.prime.infra.annotation.Audit)")
   public Object audit(ProceedingJoinPoint jp)
     throws Throwable
   {
     Object retVal;
/* 28 */     Logger logger = Util.getLogger(jp);
 
/* 31 */     boolean done = false;
 
/* 33 */     AuditInfo auditInfo = null;
     try {
/* 35 */       logger.debug("About to get auditInfo...");
/* 36 */       auditInfo = (AuditInfo)Util.getArgOfType(jp, AuditInfo.class);
/* 37 */       logger.debug("auditInfo = " + auditInfo);
 
/* 39 */       retVal = jp.proceed();
/* 40 */       done = true;
     }
     finally {
/* 43 */       if (logger.isDebugEnabled()) {
/* 44 */         logger.debug("-- About to audit " + Util.methodName(jp));
       }
/* 46 */       if (auditInfo == null) {
/* 47 */         auditInfo = new AuditInfo();
       }
 
/* 50 */       auditInfo.put("fw-servico_ok", Boolean.valueOf(done));
/* 51 */       auditInfo.put("fw-servico", Util.className(jp));
/* 52 */       auditInfo.put("fw-metodo", Util.methodName(jp));
/* 53 */       auditInfo.put("fw-data_hora", new DateTime());
 
/* 55 */       if (auditInfo.getUsuario() == null) {
/* 56 */         SecurityInfo securityInfo = SecurityInfo.getCurrent();
/* 57 */         if (securityInfo != null) {
/* 58 */           String userName = securityInfo.getUserName();
/* 59 */           if (userName != null) {
/* 60 */             auditInfo.setUsuario(userName);
/* 61 */             logger.debug("userName = " + userName);
           }
         }
       }
 
/* 66 */       AuditTrail audit = AuditTrail.getInstance();
/* 67 */       logger.debug("AuditTrail.getInstance()");
 
/* 69 */       audit.log(auditInfo);
/* 70 */       logger.debug("audit.log()");
 
/* 72 */       if (logger.isDebugEnabled()) {
/* 73 */         logger.debug("-- Audit " + Util.methodName(jp) + " OK");
       }
     }
 
/* 77 */     return retVal;
   }
 }