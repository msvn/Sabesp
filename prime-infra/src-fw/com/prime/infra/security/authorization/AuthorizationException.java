 package com.prime.infra.security.authorization;
 
 import com.prime.infra.aop.FaultException;
 
 public class AuthorizationException extends FaultException
 {
   public AuthorizationException(String message, Throwable cause)
   {
/* 12 */     super(message, cause);
   }
 
   public AuthorizationException(Throwable cause) {
/* 16 */     super(cause);
   }
 
   public AuthorizationException(String message) {
/* 20 */     super(message, null);
   }
 }