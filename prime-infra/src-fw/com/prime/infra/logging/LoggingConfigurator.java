 package com.prime.infra.logging;
 
 import ch.qos.logback.classic.BasicConfigurator;
 import ch.qos.logback.classic.Logger;
 import ch.qos.logback.classic.LoggerContext;
 import ch.qos.logback.classic.joran.JoranConfigurator;
 import ch.qos.logback.core.FileAppender;
 import ch.qos.logback.core.joran.spi.JoranException;
 import ch.qos.logback.core.util.StatusPrinter;
 import com.prime.infra.config.RunningMode;
 import java.io.PrintStream;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Set;
 import org.slf4j.LoggerFactory;
 
 public class LoggingConfigurator
 {
   private static final String LOG_CONFIG_PREFIX = "/META-INF/log/";
   private static final String LOG_CONFIG_NAME = "logback";
   private static final String STANDARD_LOGGER_NAME = "STANDARD_APPLICATION_LOG_FILE";
   private LoggerContext loggerContext;
 
   public LoggingConfigurator()
   {
/* 38 */     this.loggerContext = ((LoggerContext)LoggerFactory.getILoggerFactory());
/* 39 */     this.loggerContext.shutdownAndReset();
   }
 
   public void configure()
   {
/* 45 */     String baseLogPath = System.getProperty("BASE_LOG_PATH");
/* 46 */     if (baseLogPath == null) {
/* 47 */       System.setProperty("BASE_LOG_PATH", ".");
     }
 
/* 51 */     List<URL> resources = new ArrayList();
/* 52 */     findResource("logback", resources);
/* 53 */     Set<String> runningModes = RunningMode.get();
/* 54 */     for (String rm : runningModes) {
/* 55 */       findResource("logback-" + rm, resources);
     }
 
/* 59 */     if (resources.size() > 0) {
/* 60 */       for (URL r : resources) {
/* 61 */         System.out.println("Logging configuration: [" + r.getFile() + "]");
 
/* 63 */         configure(r);
       }
/* 65 */       reportStandardAppender();
     }
     else {
/* 68 */       System.out.println("Logging configuration: [BasicConfigurator]");
/* 69 */       BasicConfigurator.configureDefaultContext();
     }
   }
 
   protected void reportStandardAppender()
   {
/* 75 */     Logger root = this.loggerContext.getLogger("root");
     try {
/* 77 */       FileAppender appender = (FileAppender)root.getAppender("STANDARD_APPLICATION_LOG_FILE");
/* 78 */       System.out.println("Standard log output:   [" + appender.getFile() + "]");
     }
     catch (Exception e) {
/* 81 */       System.out.println("WARNING: Can't get standard log Appender");
     }
   }
 
   protected void findResource(String fileName, List<URL> resources) {
/* 86 */     String file = "/META-INF/log/" + fileName + ".xml";
/* 87 */     URL url = super.getClass().getResource(file);
/* 88 */     if (url != null)
/* 89 */       resources.add(url);
   }
 
   protected void configure(URL url)
   {
/* 94 */     JoranConfigurator configurator = new JoranConfigurator();
/* 95 */     configurator.setContext(this.loggerContext);
     try {
/* 97 */       configurator.doConfigure(url);
     }
     catch (JoranException e) {
/* 100 */       System.out.println("ERROR: Can't configure logging");
/* 101 */       StatusPrinter.print(this.loggerContext);
     }
   }
 }