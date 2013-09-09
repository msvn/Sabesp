 package com.prime.infra.config;
 
 import com.prime.infra.logging.LoggingConfigurator;
 import java.io.PrintStream;
 import javax.servlet.ServletContext;
 import javax.servlet.ServletContextEvent;
 import javax.servlet.ServletContextListener;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.web.context.ConfigurableWebApplicationContext;
 import org.springframework.web.context.ContextLoader;
 
 public class ContextLoaderListener
   implements ServletContextListener
 {
   private ContextLoader contextLoader;
 
   public void contextInitialized(ServletContextEvent event)
   {
/* 33 */     String displayName = event.getServletContext().getServletContextName();
/* 34 */     System.out.println("[[ Starting up " + displayName + " ]]");
 
/* 36 */     RunningMode.configure();
 
/* 38 */     LoggingConfigurator loggingConfigurator = new LoggingConfigurator();
/* 39 */     loggingConfigurator.configure();
 
/* 41 */     Logger startup = LoggerFactory.getLogger("STARTUP");
/* 42 */     startup.info("================================================================");
/* 43 */     startup.info("  Starting system configuration...");
/* 44 */     startup.info("  Application Name    [" + displayName + "]");
/* 45 */     startup.info("  Application Version [" + Version.getApplicationVersion() + "]");
/* 46 */     startup.info("  Framework Version   [" + Version.getFrameworkVersion() + "]");
/* 47 */     startup.info("  Running mode        " + RunningMode.get());
 
/* 49 */     this.contextLoader = new CustomContextLoader(null);
/* 50 */     this.contextLoader.initWebApplicationContext(event.getServletContext());
 
/* 52 */     startup.info("  Application context configured");
/* 53 */     startup.info("================================================================");
   }
 
   public void contextDestroyed(ServletContextEvent event)
   {
/* 60 */     String displayName = event.getServletContext().getServletContextName();
/* 61 */     Logger shutdown = LoggerFactory.getLogger("SHUTDOWN");
/* 62 */     shutdown.info("================================================================");
/* 63 */     shutdown.info("  Stopping application:  [" + displayName + "]");
/* 64 */     if (this.contextLoader != null) {
/* 65 */       this.contextLoader.closeWebApplicationContext(event.getServletContext());
     }
/* 67 */     shutdown.info("  Application stopped");
/* 68 */     shutdown.info("================================================================");
   }
 
   private static final class CustomContextLoader extends ContextLoader
   {
     protected static final String DEFAULT_CONFIG_LOCATION = "classpath:META-INF/root-context.xml";
public CustomContextLoader(Object object) {
	// TODO Auto-generated constructor stub
}
 
     protected void customizeContext(ServletContext servletContext, ConfigurableWebApplicationContext applicationContext)
     {
/* 80 */       String configLocation = "classpath:META-INF/root-context.xml";
/* 81 */       if (configLocation != null)
/* 82 */         applicationContext.setConfigLocation(configLocation);
     }
   }
 }