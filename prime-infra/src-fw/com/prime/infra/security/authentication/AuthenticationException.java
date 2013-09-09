 package com.prime.infra.security.authentication;
 
 import com.prime.infra.aop.FaultException;
 
 public class AuthenticationException extends FaultException
 {
   public AuthenticationException(Throwable cause)
   {
/* 12 */     super(cause);
   }
 
   public AuthenticationException(String message) {
/* 16 */     super(message, null);
   }
 }