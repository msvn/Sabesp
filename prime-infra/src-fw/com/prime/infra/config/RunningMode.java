 package com.prime.infra.config;
 
 import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import org.springframework.util.ClassUtils;
 
 public class RunningMode
 {
   private static Set<String> modes;
   private static String environment;
   public static final String WEBSPHERE = "websphere";
   public static final String TOMCAT = "tomcat";
   public static final String GERONIMO = "geronimo";
   public static final String BATCH = "batch";
   public static final String UNKNOWN = "unknown";
   public static boolean configured;
 
   public static Set<String> get()
   {
/* 27 */     return Collections.unmodifiableSet(modes);
   }
 
   public static void configure(String defaultEnvironment) {
/* 31 */     environment = defaultEnvironment;
/* 32 */     configure();
   }
 
   public static void configure() {
/* 36 */     if (modes != null) {
/* 37 */       System.out.println("WARNING: Running modes already defined");
/* 38 */       return;
     }
 
/* 41 */     modes = new LinkedHashSet();
 
/* 44 */     modes.add(detectServerFamily());
     try
     {
/* 48 */       String systemRunningModes = System.getProperty("runningMode", null);
/* 49 */       parse(systemRunningModes, modes);
     }
     catch (SecurityException e) {
/* 52 */       System.out.println("WARNING: Can't read system property \"runningMode\": " + e);
     }
 
/* 55 */     configured = true;
   }
 
   private static void parse(String string, Set<String> list) {
/* 59 */     if (string == null) {
/* 60 */       return;
     }
 
/* 63 */     Scanner sc = new Scanner(string).useDelimiter(",");
/* 64 */     while (sc.hasNext()) {
/* 65 */       String s = sc.next().trim();
/* 66 */       if (!(s.equals("")))
/* 67 */         list.add(s);
     }
   }
 
   private static String detectServerFamily()
   {
/* 73 */     if (ClassUtils.isPresent("com.ibm.wsspi.uow.UOWManager", ClassUtils.getDefaultClassLoader())) {
/* 74 */       return "websphere";
     }
/* 76 */     if (ClassUtils.isPresent("org.apache.catalina.startup.Bootstrap", ClassUtils.getDefaultClassLoader())) {
/* 77 */       return "tomcat";
     }
/* 79 */     if (ClassUtils.isPresent("org.apache.geronimo.kernel.Kernel", ClassUtils.getDefaultClassLoader())) {
/* 80 */       return "geronimo";
     }
/* 82 */     if ((environment != null) && 
/* 83 */       (environment.equalsIgnoreCase("batch"))) {
/* 84 */       return "batch";
     }
 
/* 87 */     return "unknown";
   }
 
   public static boolean isConfigured() {
/* 91 */     return configured;
   }
 }