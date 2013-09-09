 package com.prime.infra.batch;
 
 public class BatchWarningException extends RuntimeException
 {
   private int code;
 
   public BatchWarningException(int code, String message)
   {
/* 14 */     super(message);
/* 15 */     this.code = code;
   }
 
   public BatchWarningException(int code, String message, Throwable cause) {
/* 19 */     super(message, cause);
/* 20 */     this.code = code;
   }
 
   public int getCode() {
/* 24 */     return this.code;
   }
 }