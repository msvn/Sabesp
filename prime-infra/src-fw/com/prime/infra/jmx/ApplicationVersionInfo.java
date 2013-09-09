 package com.prime.infra.jmx;
 
 import com.prime.infra.config.Version;
 import org.springframework.jmx.export.annotation.ManagedAttribute;
 import org.springframework.jmx.export.annotation.ManagedResource;
 
 @ManagedResource
 public class ApplicationVersionInfo
 {
   private String title;
   private String name;
   private String pkg;
   private String version;
   private String vendor;
 
   public ApplicationVersionInfo()
   {
/* 15 */     this.title = Version.getApplicationVersion().getTitle();
/* 16 */     this.name = Version.getApplicationVersion().getName();
/* 17 */     this.pkg = Version.getApplicationVersion().getPackage();
/* 18 */     this.version = Version.getApplicationVersion().getVersion();
/* 19 */     this.vendor = Version.getApplicationVersion().getVendor(); }
 
   @ManagedAttribute
   public String getTitle() {
/* 23 */     return this.title;
   }
 
   @ManagedAttribute
   public String getName() {
/* 28 */     return this.name;
   }
 
   @ManagedAttribute
   public String getPkg() {
/* 33 */     return this.pkg;
   }
 
   @ManagedAttribute
   public String getVersion() {
/* 38 */     return this.version;
   }
 
   @ManagedAttribute
   public String getVendor() {
/* 43 */     return this.vendor;
   }
 }