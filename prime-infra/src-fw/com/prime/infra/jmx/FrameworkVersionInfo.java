 package com.prime.infra.jmx;
 
 import com.prime.infra.config.Version;
 import org.springframework.jmx.export.annotation.ManagedAttribute;
 import org.springframework.jmx.export.annotation.ManagedResource;
 
 @ManagedResource
 public class FrameworkVersionInfo
 {
   private String title;
   private String name;
   private String pkg;
   private String version;
   private String vendor;
 
   public FrameworkVersionInfo()
   {
/* 15 */     this.title = Version.getFrameworkVersion().getTitle();
/* 16 */     this.name = Version.getFrameworkVersion().getName();
/* 17 */     this.pkg = Version.getFrameworkVersion().getPackage();
/* 18 */     this.version = Version.getFrameworkVersion().getVersion();
/* 19 */     this.vendor = Version.getFrameworkVersion().getVendor(); }
 
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