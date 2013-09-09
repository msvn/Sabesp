 package com.prime.infra.aop;
 
 import org.aspectj.lang.JoinPoint;
 import org.aspectj.lang.annotation.AfterReturning;
 import org.aspectj.lang.annotation.Aspect;
 import org.aspectj.lang.annotation.Before;
 import org.slf4j.Logger;
 import org.springframework.core.Ordered;
 
 @Aspect
 public class LogAspect
   implements Ordered
 {
   public int getOrder()
   {
/* 18 */     return 100;
   }
 
   @Before("com.prime.infra.aop.FrameworkPointcuts.businessServiceImpl()")
   public void logEntry(JoinPoint jp) throws Throwable {
/* 23 */     Logger logger = Util.getLogger(jp);
 
/* 25 */     if (logger.isDebugEnabled()) {
/* 26 */       StringBuilder sb = new StringBuilder(128);
/* 27 */       sb.append(">> ");
/* 28 */       sb.append(Util.methodName(jp));
/* 29 */       sb.append('(');
/* 30 */       Util.appendArguments(jp.getArgs(), sb);
/* 31 */       sb.append(')');
/* 32 */       logger.debug(sb.toString());
     }
   }
 
   @AfterReturning(pointcut="com.prime.infra.aop.FrameworkPointcuts.businessServiceImpl() && (execution(void *..*(..)))")
   public void logExit(JoinPoint jp) throws Throwable
   {
/* 39 */     Logger logger = Util.getLogger(jp);
 
/* 41 */     if (logger.isDebugEnabled())
/* 42 */       logger.debug("<< " + Util.methodName(jp) + " -> void");
   }
 
   @AfterReturning(pointcut="com.prime.infra.aop.FrameworkPointcuts.businessServiceImpl() && !(execution(void *..*(..)))", returning="returningValue", argNames="jp,returningValue")
   public void logExit(JoinPoint jp, Object returningValue)
     throws Throwable
   {
/* 49 */     Logger logger = Util.getLogger(jp);
 
/* 51 */     if (logger.isDebugEnabled())
/* 52 */       logger.debug("<< " + Util.methodName(jp) + " -> " + Util.displayObject(returningValue));
   }
 }