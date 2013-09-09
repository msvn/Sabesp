 package com.prime.infra.aop;
 
 import org.aspectj.lang.ProceedingJoinPoint;
 import org.aspectj.lang.annotation.Around;
 import org.aspectj.lang.annotation.Aspect;
 import org.slf4j.Logger;
 import org.springframework.core.Ordered;
 
 @Aspect
 public class TimerAspect
   implements Ordered
 {
   public int getOrder()
   {
/* 17 */     return 400;
   }
 
   @Around("com.prime.infra.aop.FrameworkPointcuts.backingBean()")
   public Object timer(ProceedingJoinPoint jp)
     throws Throwable
   {
     Object retVal;
/* 23 */     Logger logger = Util.getLogger(jp);
 
/* 26 */     if (logger.isDebugEnabled()) {
/* 27 */       long t0 = System.currentTimeMillis();
       try {
/* 29 */         retVal = jp.proceed();
       }
       finally {
/* 32 */         long t1 = System.currentTimeMillis();
/* 33 */         long dt = t1 - t0;
 
/* 35 */         logger.debug("-- " + Util.methodName(jp) + ": " + dt + " ms");
       }
     }
     else {
/* 39 */       retVal = jp.proceed();
     }
/* 41 */     return retVal;
   }
 }