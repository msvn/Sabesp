 package com.prime.infra.logging;
 
 import com.prime.infra.ErrorMessage;
 import java.util.Formatter;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public class MyLogger
 {
   private Logger logger;
/* 72 */   public static ThreadLocal<Formatter> formatterCache = new FormatterCache(null);
 
   public static MyLogger getLogger(String name)
   {
/* 16 */     return new MyLogger(LoggerFactory.getLogger(name));
   }
 
   public static MyLogger getLogger(Class clazz) {
/* 20 */     return new MyLogger(LoggerFactory.getLogger(clazz));
   }
 
   public static MyLogger getLogger() {
/* 24 */     String name = new java.lang.Exception().getStackTrace()[1].getClassName();
/* 25 */     return new MyLogger(LoggerFactory.getLogger(name));
   }
 
   MyLogger(Logger logger) {
/* 29 */     this.logger = logger;
   }
 
   public boolean isErrorEnabled()
   {
/* 35 */     return this.logger.isErrorEnabled();
   }
 
   public void error(String format, Object[] args) {
/* 39 */     if (!(this.logger.isErrorEnabled())) {
/* 40 */       return;
     }
/* 42 */     this.logger.error(sprintf(format, args));
   }
 
   public void error(String format, Throwable t, Object[] args) {
/* 46 */     if (!(this.logger.isErrorEnabled())) {
/* 47 */       return;
     }
/* 49 */     this.logger.error(sprintf(format, args), t);
   }
 
   public void error(ErrorMessage errorMessage, Object[] args) {
/* 53 */     if (!(this.logger.isErrorEnabled())) {
/* 54 */       return;
     }
/* 56 */     this.logger.error(errorMessage.formatMesssage(args));
   }
 
   public static String sprintf(String format, Object[] args)
   {
/* 62 */     Formatter f = (Formatter)formatterCache.get();
/* 63 */     f.format(format, args);
 
/* 65 */     StringBuilder sb = (StringBuilder)f.out();
/* 66 */     String message = sb.toString();
/* 67 */     sb.setLength(0);
 
/* 69 */     return message;
   }
 
   public static void main(String[] args)
   {
/* 84 */     MyLogger log = getLogger();
 
/* 86 */     log.error("xxx", new Object[0]);
/* 87 */     log.error("x = %s y = %d", new Object[] { "xxx", Integer.valueOf(17) });
   }
 
   private static class FormatterCache extends ThreadLocal<Formatter>
   {
public FormatterCache(Object object) {
		// TODO Auto-generated constructor stub
	}

     protected synchronized Formatter initialValue()
     {
/* 76 */       return new Formatter();
     }
   }
 }