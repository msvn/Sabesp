 package com.prime.infra.record.exception;
 
 public class WriterException extends Exception
 {
   public WriterException()
   {
   }
 
   public WriterException(String arg0, Throwable arg1)
   {
/* 10 */     super(arg0, arg1);
   }
 
   public WriterException(String arg0) {
/* 14 */     super(arg0);
   }
 
   public WriterException(Throwable arg0) {
/* 18 */     super(arg0);
   }
 }