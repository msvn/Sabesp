 package com.prime.infra.aop;
 
 import com.jamonapi.Monitor;
 import com.jamonapi.MonitorFactory;
 import org.aspectj.lang.ProceedingJoinPoint;
 import org.aspectj.lang.annotation.Around;
 import org.aspectj.lang.annotation.Aspect;
 import org.slf4j.Logger;
 import org.springframework.core.Ordered;
 
 @Aspect
 public class MonitorAspect
   implements Ordered
 {
   public int getOrder()
   {
/* 20 */     return 600;
   }
 
   @Around("com.prime.infra.aop.FrameworkPointcuts.businessServiceImpl()")
   public Object monitor(ProceedingJoinPoint jp)
     throws Throwable
   {
     Object retVal;
/* 25 */     Logger logger = Util.getLogger(jp);
 
/* 29 */     Monitor monitor = MonitorFactory.start(Util.fullMethodName(jp));
/* 30 */     if (monitor.isEnabled()) {
       try {
/* 32 */         retVal = jp.proceed();
       }
       finally {
/* 35 */         monitor.stop();
/* 36 */         if (logger.isDebugEnabled()) {
/* 37 */           logger.debug("-- " + Util.methodName(jp) + ": " + displayData(monitor));
         }
       }
     }
     else {
/* 42 */       retVal = jp.proceed();
     }
/* 44 */     return retVal;
   }
 
   private StringBuffer displayData(Monitor monitor) {
/* 48 */     StringBuffer b = new StringBuffer(400);
/* 49 */     b.append("Hits=");
/* 50 */     b.append(monitor.getHits());
/* 51 */     b.append(", LastValue=");
/* 52 */     b.append(monitor.getLastValue());
/* 53 */     b.append(", Min=");
/* 54 */     b.append(monitor.getMin());
/* 55 */     b.append(", Max=");
/* 56 */     b.append(monitor.getMax());
/* 57 */     b.append(", Avg=");
/* 58 */     b.append(monitor.getAvg());
/* 59 */     b.append(", Total=");
/* 60 */     b.append(monitor.getTotal());
/* 61 */     b.append(", StdDev=");
/* 62 */     b.append(monitor.getStdDev());
/* 63 */     b.append(", FirstAccess=");
/* 64 */     b.append(monitor.getFirstAccess());
/* 65 */     b.append(", LastAccess=");
/* 66 */     b.append(monitor.getLastAccess());
/* 67 */     return b;
   }
 }