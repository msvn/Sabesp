 package com.prime.infra.ejb;
 
 import com.prime.infra.config.RunningMode;
 import com.prime.infra.config.Version;
 import com.prime.infra.logging.LoggingConfigurator;
 import java.io.PrintStream;
 import javax.annotation.PostConstruct;
 import javax.annotation.PreDestroy;
 import javax.ejb.PostActivate;
 import javax.ejb.PrePassivate;
 import javax.interceptor.InvocationContext;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
 
 public class EjbAutowiringInterceptor
 {
   private SpringBeanAutowiringInterceptor interceptor;
 
   public EjbAutowiringInterceptor()
   {
/* 50 */     this.interceptor = new SpringBeanAutowiringInterceptor();
   }
 
   @PostConstruct
   @PostActivate
   public void autowireBean(InvocationContext invocationContext) {
     try {
/* 57 */       this.interceptor.autowireBean(invocationContext);
     }
     catch (Exception e) {
/* 60 */       throw new IllegalStateException(e);
     }
   }
 
   @PreDestroy
   @PrePassivate
   public void releaseBean(InvocationContext invocationContext)
   {
     try
     {
/* 72 */       this.interceptor.releaseBean(invocationContext);
     }
     catch (Exception e) {
/* 75 */       throw new IllegalStateException(e);
     }
   }
 
   static
   {
/* 26 */     System.out.println("static EjbAutowiringInterceptor");
 
/* 28 */     String displayName = Version.getApplicationVersion().getName();
/* 29 */     System.out.println("[[ Starting up " + displayName + " ]]");
 
/* 31 */     if (!(RunningMode.isConfigured())) {
/* 32 */       RunningMode.configure();
     }
 
/* 35 */     LoggingConfigurator loggingConfigurator = new LoggingConfigurator();
/* 36 */     loggingConfigurator.configure();
 
/* 38 */     Logger startup = LoggerFactory.getLogger("STARTUP");
/* 39 */     startup.info("================================================================");
/* 40 */     startup.info("  Starting system configuration...");
/* 41 */     startup.info("  Application Name    [" + Version.getApplicationVersion().getName() + "]");
/* 42 */     startup.info("  Application Version [" + Version.getApplicationVersion() + "]");
/* 43 */     startup.info("  Framework Version   [" + Version.getFrameworkVersion() + "]");
/* 44 */     startup.info("  Running mode        " + RunningMode.get());
/* 45 */     startup.info("  Application context configured");
/* 46 */     startup.info("================================================================");
   }
 }