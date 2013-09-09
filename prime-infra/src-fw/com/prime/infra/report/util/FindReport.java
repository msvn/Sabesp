 package com.prime.infra.report.util;
 
 import java.io.InputStream;
 
 public class FindReport
 {
   public static InputStream getReport(String path)
   {
/* 18 */     if ((path == null)) throw new AssertionError("Path cannot be null");
/* 19 */     return FindReport.class.getResourceAsStream(path);
   }
 }