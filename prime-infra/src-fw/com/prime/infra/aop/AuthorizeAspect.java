 package com.prime.infra.aop;
 
 import com.prime.infra.security.SecurityInfo;
 import com.prime.infra.security.authorization.Authorization;
 import com.prime.infra.security.authorization.AuthorizationException;
 import org.aspectj.lang.ProceedingJoinPoint;
 import org.aspectj.lang.annotation.Around;
 import org.aspectj.lang.annotation.Aspect;
 import org.slf4j.Logger;
 import org.slf4j.MDC;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.Ordered;
 
 @Aspect
 public class AuthorizeAspect
   implements Ordered
 {
 
   @Autowired
   private Authorization authorizaton;
 
   public int getOrder()
   {
/* 26 */     return 300;
   }
 
   @Around("com.prime.infra.aop.FrameworkPointcuts.businessServiceImpl() && @annotation(com.prime.infra.annotation.Authorize)")
   public Object authorize(ProceedingJoinPoint jp)
     throws Throwable
   {
     Object retVal;
/* 33 */     Logger logger = Util.getLogger(jp);
 
/* 35 */     String serviceName = Util.serviceName(jp);
/* 36 */     String methodName = Util.methodName(jp);
/* 37 */     String object = serviceName + "." + methodName;
 
/* 39 */     SecurityInfo securityInfo = getSecurityInfo(jp);
/* 40 */     if ((securityInfo == null) || (securityInfo.getUserName() == null)) {
/* 41 */       logger.debug("SecurityInfo not available");
/* 42 */       throw new AuthorizationException("Nao autorizado");
     }
 
/* 45 */     String userName = securityInfo.getUserName();
/* 46 */     if (logger.isDebugEnabled()) {
/* 47 */       logger.debug("-- Authorize " + userName + "? " + object);
     }
 
/* 51 */     if ((this.authorizaton == null) || (!(this.authorizaton.isUserAuthorized(securityInfo, object)))) {
/* 52 */       logger.debug("-- Not authorized: " + userName + "? " + object);
/* 53 */       throw new AuthorizationException("Not authorized");
     }
 
/* 56 */     MDC.put("user", userName);
     try
     {
/* 60 */       retVal = jp.proceed();
     }
     finally {
/* 63 */       MDC.remove("user");
     }
 
/* 66 */     if (logger.isDebugEnabled()) {
/* 67 */       logger.debug("-- Authorized " + Util.methodName(jp));
     }
 
/* 70 */     return retVal;
   }
 
   private SecurityInfo getSecurityInfo(ProceedingJoinPoint jp) {
/* 74 */     SecurityInfo info = (SecurityInfo)Util.getArgOfType(jp, SecurityInfo.class);
/* 75 */     if (info == null) {
/* 76 */       info = SecurityInfo.getCurrent();
     }
/* 78 */     return info;
   }
 }