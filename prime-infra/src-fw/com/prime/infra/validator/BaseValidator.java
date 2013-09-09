 package com.prime.infra.validator;
 
 import com.prime.infra.record.exception.ValidateException;
 
 public abstract class BaseValidator
   implements Validator
 {
   public boolean isValid(Object value)
   {
     try
     {
/* 16 */       validate(value);
/* 17 */       return true;
     } catch (Exception e) {
     }
/* 20 */     return false;
   }
 
   protected void reject(String errorCode, Object value)
     throws ValidateException
   {
/* 33 */     throw new ValidateException(errorCode, value);
   }
 }