 package com.prime.infra.aop;
 
 import com.prime.infra.BusinessException;
 import com.prime.infra.ErrorMessage;
 import com.prime.infra.SystemException;
 import java.io.PrintWriter;
 import java.io.StringWriter;
 import java.util.Date;
 import org.aspectj.lang.JoinPoint;
 import org.aspectj.lang.annotation.AfterThrowing;
 import org.aspectj.lang.annotation.Aspect;
 import org.slf4j.Logger;
 import org.springframework.core.Ordered;
 
 @Aspect
 public class ExceptionBarrierAspect
   implements Ordered
 {
/*  95 */   private static int occurrenceSeq = 1;
 
   public int getOrder()
   {
/*  25 */     return 500;
   }
 
   @AfterThrowing(pointcut="com.prime.infra.aop.FrameworkPointcuts.businessServiceImpl()", throwing="ex", argNames="jp,ex")
   public void catchException(JoinPoint jp, Throwable ex)
     throws Throwable
   {
/*  32 */     String occurrenceId = nextOccurrenceId();
/*  33 */     String methodName = Util.methodName(jp);
 
/*  35 */     Logger logger = Util.getLogger(jp);
/*  36 */     logger.debug("<< " + methodName + " -> EXCEPTION [" + occurrenceId + "] " + ex.getMessage());
 
/*  39 */     if (ex instanceof BusinessException) {
/*  40 */       throw ex;
     }
 
/*  44 */     SystemException sex = new SystemException(ex, ErrorMessage.UNEXPECTED_ERROR, new Object[] { ex.getMessage() });
 
/*  48 */     sex.setTimeStamp(new Date());
/*  49 */     sex.setOccurrenceId(occurrenceId);
/*  50 */     sex.setCurrent(jp.getThis());
/*  51 */     sex.setMethodName(methodName);
/*  52 */     sex.setArgs(jp.getArgs());
 
/*  54 */     if (logger.isErrorEnabled()) {
/*  55 */       dumpFault(sex, logger);
     }
 
/*  58 */     throw sex;
   }
 
   private void dumpFault(SystemException sex, Logger logger) {
/*  62 */     StringBuilder args = new StringBuilder();
/*  63 */     Util.appendArguments(sex.getArgs(), args);
 
/*  65 */     logger.error("# Error code: " + sex.getErrorCode());
/*  66 */     logger.error("# Error message: " + sex.getMessage());
/*  67 */     logger.error("# Ocurrence: " + sex.getOccurrenceId());
/*  68 */     logger.error("# Timestamp: " + sex.getTimeStamp());
/*  69 */     logger.error("# Service: " + sex.getCurrent());
/*  70 */     logger.error("# Method: " + sex.getMethodName());
/*  71 */     logger.error("# Arguments: (" + args + ")");
 
/*  73 */     Throwable rootCause = sex;
/*  74 */     while (rootCause.getCause() != null) {
/*  75 */       rootCause = rootCause.getCause();
     }
 
/*  78 */     StringWriter rootCauseStack = new StringWriter();
/*  79 */     PrintWriter writer = new PrintWriter(rootCauseStack);
/*  80 */     writer.println();
/*  81 */     rootCause.printStackTrace(writer);
 
/*  83 */     logger.error("# Cause: " + rootCauseStack);
   }
 
   private static synchronized String nextOccurrenceId()
   {
/*  98 */     int id = occurrenceSeq;
/*  99 */     occurrenceSeq += 1;
/* 100 */     return String.valueOf(id);
   }
 }