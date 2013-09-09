 package com.prime.infra.record.exception;
 
 import com.prime.infra.ErrorMessage;
 import com.prime.infra.SystemException;
 
 public class ConverterException extends SystemException
 {
   public ConverterException(String arg, Object value)
   {
/* 13 */     super(ErrorMessage.VALIDATION_ERROR, new Object[] { arg, value });
   }
 }