 package com.prime.infra.logging;
 
 import ch.qos.logback.classic.spi.CallerData;
 import ch.qos.logback.classic.spi.LoggerRemoteView;
 import ch.qos.logback.classic.spi.LoggingEvent;
 import ch.qos.logback.classic.spi.ThrowableInformation;
 import ch.qos.logback.core.AppenderBase;
 import java.io.PrintStream;
 import java.util.logging.LogRecord;
 import java.util.logging.Logger;
 
 public class JULAppender extends AppenderBase
 {
   private boolean exposeCaller;
   private String messagePrefix;
 
   public JULAppender()
   {
/* 15 */     this.exposeCaller = true;
/* 16 */     this.messagePrefix = null; }
 
   public boolean getExposeCaller() {
/* 19 */     return this.exposeCaller;
   }
 
   public void setExposeCaller(boolean exposeCaller) {
/* 23 */     this.exposeCaller = exposeCaller;
   }
 
   public String getMessagePrefix() {
/* 27 */     return this.messagePrefix;
   }
 
   public void setMessagePrefix(String messagePrefix) {
/* 31 */     this.messagePrefix = messagePrefix;
   }
 
   protected void append(Object eventObject) {
/* 35 */     LoggingEvent event = (LoggingEvent)eventObject;
 
/* 37 */     String message = this.messagePrefix + ": " + event.getMessage();
 
/* 39 */     LogRecord julRecord = new LogRecord(convertLevel(event.getLevel()), message);
 
/* 42 */     String loggerName = event.getLoggerRemoteView().getName();
 
/* 44 */     julRecord.setLoggerName(loggerName);
/* 45 */     julRecord.setMillis(event.getTimeStamp());
/* 46 */     julRecord.setThreadID((int)Thread.currentThread().getId());
/* 47 */     ThrowableInformation ti = event.getThrowableInformation();
/* 48 */     if (ti != null) {
/* 49 */       julRecord.setThrown(ti.getThrowable());
     }
 
/* 52 */     if (this.exposeCaller) {
/* 53 */       CallerData callerData = event.getCallerData()[0];
/* 54 */       julRecord.setSourceClassName(callerData.getClassName());
/* 55 */       julRecord.setSourceMethodName(callerData.getMethodName());
     }
     else {
/* 58 */       julRecord.setSourceClassName("");
/* 59 */       julRecord.setSourceMethodName("");
     }
 
/* 62 */     Logger julLogger = Logger.getLogger(loggerName);
/* 63 */     julLogger.log(julRecord);
   }
 
   private java.util.logging.Level convertLevel(ch.qos.logback.classic.Level level) {
/* 67 */     switch (level.levelInt)
     {
     case 40000:
/* 69 */       return java.util.logging.Level.SEVERE;
     case 30000:
/* 71 */       return java.util.logging.Level.WARNING;
     case 20000:
/* 73 */       return java.util.logging.Level.INFO;
     case 10000:
/* 75 */       return java.util.logging.Level.FINE;
     case 5000:
/* 77 */       return java.util.logging.Level.FINEST;
     }
/* 79 */     System.out.println("WARNING: Unknown Level " + level + " " + level.levelInt);
/* 80 */     return java.util.logging.Level.OFF;
   }
 }