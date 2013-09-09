 package com.prime.infra;
 
 public class BusinessException extends Exception
 {
   public BusinessException(String message)
   {
/* 15 */     super(message);
   }
 
   public BusinessException(String message, Throwable cause) {
/* 19 */     super(message, cause);
   }
 }