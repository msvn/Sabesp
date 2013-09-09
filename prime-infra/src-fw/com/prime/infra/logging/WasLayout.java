 package com.prime.infra.logging;
 
 import ch.qos.logback.classic.Level;
 import ch.qos.logback.classic.spi.CallerData;
 import ch.qos.logback.classic.spi.LoggerRemoteView;
 import ch.qos.logback.classic.spi.LoggingEvent;
 import ch.qos.logback.core.LayoutBase;
 import java.io.PrintStream;
 import java.io.PrintWriter;
 import java.io.StringWriter;
 import org.joda.time.format.DateTimeFormat;
 import org.joda.time.format.DateTimeFormatter;
 import org.slf4j.MDC;
 
 public class WasLayout extends LayoutBase<LoggingEvent>
 {
   private DateTimeFormatter dateFmt;
   private DateTimeFormatter timeFmt;
 
   public WasLayout()
   {
/* 21 */     this.dateFmt = DateTimeFormat.shortDate();
/* 22 */     this.timeFmt = DateTimeFormat.forPattern("kk:mm:ss:SSS zzz"); }
 
   public String doLayout(LoggingEvent event) {
/* 25 */     long time = event.getTimeStamp();
 
/* 27 */     long threadId = Thread.currentThread().getId();
 
/* 29 */     String loggerName = event.getLoggerRemoteView().getName();
/* 30 */     int i = loggerName.lastIndexOf(".");
/* 31 */     if (i != -1) {
/* 32 */       loggerName = loggerName.substring(i + 1);
     }
/* 34 */     if (loggerName.length() > 13) {
/* 35 */       loggerName = loggerName.substring(0, 13);
     }
 
/* 38 */     CallerData callerData = event.getCallerData()[0];
 
/* 40 */     String userName = MDC.get("user");
/* 41 */     if (userName == null) {
/* 42 */       userName = "";
     }
 
/* 45 */     StringWriter stringWriter = new StringWriter();
/* 46 */     PrintWriter writer = new PrintWriter(stringWriter);
 
/* 48 */     writer.printf("[%s %s] %08x %-13s %c %s %s {%s} %s%n", new Object[] { this.dateFmt.print(time), this.timeFmt.print(time), Long.valueOf(threadId), loggerName, Character.valueOf(displayLevel(event.getLevel())), checkNull(callerData.getClassName()), checkNull(callerData.getMethodName()), userName, event.getMessage() });
 
/* 59 */     String formattedRecord = stringWriter.toString();
/* 60 */     writer.close();
 
/* 62 */     return formattedRecord;
   }
 
   private char displayLevel(Level level) {
/* 66 */     switch (level.levelInt)
     {
     case 40000:
/* 68 */       return 'S';
     case 30000:
/* 70 */       return 'W';
     case 20000:
/* 72 */       return 'I';
     case 10000:
/* 74 */       return '1';
     case 5000:
/* 76 */       return '3';
     }
/* 78 */     System.out.println("Unknown Log Level: " + level + " " + level.levelInt);
/* 79 */     return '?';
   }
 
   private String checkNull(String str)
   {
/* 84 */     return ((str == null) ? "" : str);
   }
 }