 package com.prime.infra.config;
 
 import java.io.IOException;
 import java.io.InputStream;
 import java.util.Properties;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.core.io.DefaultResourceLoader;
 import org.springframework.core.io.Resource;
 
 public class Version
 {
/* 18 */   private static Logger logger = LoggerFactory.getLogger(Version.class);
   private static final String TITLE_UNDEFINED = "TITLE_UNDEFINED";
   private static final String NAME_UNDEFINED = "NAME_UNDEFINED";
   private static final String PACKAGE_UNDEFINED = "PACKAGE_UNDEFINED";
   private static final String VERSION_UNDEFINED = "VERSION_UNDEFINED";
   private static final String VENDOR_UNDEFINED = "VENDOR_UNDEFINED";
/* 30 */   private static Version frameworkVersion = new Version("framework");
/* 31 */   private static Version applicationVersion = new Version("application");
 
/* 44 */   private String title = "TITLE_UNDEFINED";
/* 45 */   private String name = "NAME_UNDEFINED";
/* 46 */   private String pkg = "PACKAGE_UNDEFINED";
/* 47 */   private String version = "VERSION_UNDEFINED";
/* 48 */   private String vendor = "VENDOR_UNDEFINED";
 
   public static Version getFrameworkVersion()
   {
/* 35 */     return frameworkVersion;
   }
 
   public static Version getApplicationVersion() {
/* 39 */     return applicationVersion;
   }
 
   public String getTitle()
   {
/* 51 */     return this.title;
   }
 
   public String getName() {
/* 55 */     return this.name;
   }
 
   public String getPackage() {
/* 59 */     return this.pkg;
   }
 
   public String getVersion() {
/* 63 */     return this.version;
   }
 
   public String getVendor() {
/* 67 */     return this.vendor;
   }
 
   public String toString() {
/* 71 */     return this.name + ": " + this.version;
   }
 
   public Version(String baseName) {
/* 75 */     readProperties(baseName);
   }
 
   protected void readProperties(String baseName) {
/* 79 */     String fileName = "classpath:META-INF/config/" + baseName + ".properties";
/* 80 */     Resource res = new DefaultResourceLoader().getResource(fileName);
     try
     {
/* 84 */       InputStream inputStream = res.getInputStream();
/* 85 */       if (inputStream != null) {
/* 86 */         Properties p = new Properties();
/* 87 */         p.load(inputStream);
 
/* 89 */         this.title = p.getProperty(baseName + ".title", "TITLE_UNDEFINED");
/* 90 */         this.name = p.getProperty(baseName + ".name", "NAME_UNDEFINED");
/* 91 */         this.pkg = p.getProperty(baseName + ".package", "PACKAGE_UNDEFINED");
/* 92 */         this.version = p.getProperty(baseName + ".version", "VERSION_UNDEFINED");
/* 93 */         this.vendor = p.getProperty(baseName + ".vendor", "VENDOR_UNDEFINED");
 
/* 95 */         inputStream.close();
       }
     }
     catch (IOException e) {
/* 99 */       logger.error("Can't read configuration file: " + fileName, e);
     }
   }
 }