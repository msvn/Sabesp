 package com.prime.infra.record.exception;
 
 public class MarshallException extends Exception
 {
   public MarshallException()
   {
   }
 
   public MarshallException(String arg0)
   {
/* 14 */     super(arg0);
   }
 
   public MarshallException(Throwable arg0) {
/* 18 */     super(arg0);
   }
 
   public MarshallException(String arg0, Throwable arg1) {
/* 22 */     super(arg0, arg1);
   }
 }