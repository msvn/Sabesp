 package com.prime.infra.record.exception;
 
 public class ParseException extends Exception
 {
   public ParseException()
   {
   }
 
   public ParseException(String msg, Throwable throwable)
   {
/* 14 */     super(msg, throwable);
   }
 
   public ParseException(String msg) {
/* 18 */     super(msg);
   }
 
   public ParseException(Throwable throwable) {
/* 22 */     super(throwable);
   }
 }