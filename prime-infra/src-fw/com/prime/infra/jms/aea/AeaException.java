 package com.prime.infra.jms.aea;
 
 public class AeaException extends Exception
 {
   public AeaException(String message, Throwable cause)
   {
/* 11 */     super(message, cause);
   }
 
   public AeaException(String message) {
/* 15 */     super(message);
   }
 
   public AeaException(Throwable cause) {
/* 19 */     super(cause);
   }
 }