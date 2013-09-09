 package com.prime.infra.record.exception;
 
 import com.prime.infra.ErrorMessage;
 import com.prime.infra.SystemException;
 
 public class ValidateException extends SystemException
 {
   private static final long serialVersionUID = -9056394439140308611L;
 
   public ValidateException(String arg, Object value)
   {
/* 14 */     super(ErrorMessage.VALIDATION_ERROR, new Object[] { arg, value });
   }
 }