 package com.prime.infra.record.exception;
 
 public class DataException extends Exception
 {
   public DataException()
   {
   }
 
   public DataException(String arg0, Throwable arg1)
   {
/* 14 */     super(arg0, arg1);
   }
 
   public DataException(String arg0) {
/* 18 */     super(arg0);
   }
 
   public DataException(Throwable arg0) {
/* 22 */     super(arg0);
   }
 }