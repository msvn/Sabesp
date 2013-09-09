 package com.prime.infra.ejb;
 
 import com.prime.infra.config.RunningMode;
 import com.prime.infra.config.Version;
 import com.prime.infra.logging.LoggingConfigurator;
 import java.io.PrintStream;
 import javax.ejb.MessageDrivenContext;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
 import org.springframework.ejb.support.AbstractJmsMessageDrivenBean;
 
 public abstract class MDBSupport extends AbstractJmsMessageDrivenBean
 {
   public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext)
   {
/* 41 */     super.setMessageDrivenContext(messageDrivenContext);
/* 42 */     setBeanFactoryLocator(ContextSingletonBeanFactoryLocator.getInstance());
/* 43 */     setBeanFactoryLocatorKey("defaultFactory");
   }
 
   static
   {
/* 16 */     System.out.println("static MDB");
 
/* 18 */     String displayName = Version.getApplicationVersion().getName();
/* 19 */     System.out.println("[[ Starting up " + displayName + " ]]");
 
/* 21 */     if (!(RunningMode.isConfigured())) {
/* 22 */       RunningMode.configure();
     }
 
/* 25 */     LoggingConfigurator loggingConfigurator = new LoggingConfigurator();
/* 26 */     loggingConfigurator.configure();
 
/* 28 */     Logger startup = LoggerFactory.getLogger("STARTUP");
/* 29 */     startup.info("================================================================");
/* 30 */     startup.info("  Starting system configuration...");
/* 31 */     startup.info("  Application Name    [" + Version.getApplicationVersion().getName() + "]");
/* 32 */     startup.info("  Application Version [" + Version.getApplicationVersion() + "]");
/* 33 */     startup.info("  Framework Version   [" + Version.getFrameworkVersion() + "]");
/* 34 */     startup.info("  Running mode        " + RunningMode.get());
/* 35 */     startup.info("  Application context configured");
/* 36 */     startup.info("================================================================");
   }
 }