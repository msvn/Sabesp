 package com.prime.infra.batch;
 
 public class BatchErrorException extends RuntimeException
 {
   private int code;
 
   public BatchErrorException(int code, String message)
   {
/* 14 */     super(message);
/* 15 */     this.code = code;
   }
 
   public BatchErrorException(int code, String message, Throwable cause) {
/* 19 */     super(message, cause);
/* 20 */     this.code = code;
   }
 
   public int getCode() {
/* 24 */     return this.code;
   }
 }