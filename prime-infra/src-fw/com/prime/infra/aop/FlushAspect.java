 package com.prime.infra.aop;
 
 import javax.persistence.EntityManager;
 import javax.persistence.PersistenceContext;
 import org.aspectj.lang.JoinPoint;
 import org.aspectj.lang.annotation.AfterReturning;
 import org.aspectj.lang.annotation.Aspect;
 import org.hibernate.impl.SessionImpl;
 import org.slf4j.Logger;
 import org.springframework.core.Ordered;
 
 @Aspect
 public class FlushAspect
   implements Ordered
 {
 
   @PersistenceContext
   private EntityManager em;
 
   public int getOrder()
   {
/* 21 */     return 1000000400;
   }
 
   @AfterReturning("com.prime.infra.aop.FrameworkPointcuts.businessServiceImpl()")
   public void flush(JoinPoint jp)
     throws Throwable
   {
/* 29 */     if (isTransactionInProgress()) {
/* 30 */       Logger logger = Util.getLogger(jp);
 
/* 32 */       if (logger.isTraceEnabled()) {
/* 33 */         logger.trace("-- " + Util.methodName(jp) + ": flushing");
/* 34 */         this.em.flush();
/* 35 */         logger.trace("-- " + Util.methodName(jp) + ": flushed");
       }
       else {
/* 38 */         this.em.flush();
       }
     }
   }
 
   private boolean isTransactionInProgress() {
/* 44 */     SessionImpl session = (SessionImpl)this.em.getDelegate();
/* 45 */     return session.isTransactionInProgress();
   }
 }