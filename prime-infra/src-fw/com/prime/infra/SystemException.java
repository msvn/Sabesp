 package com.prime.infra;
 
 import com.prime.infra.aop.FaultException;
 
 public class SystemException extends FaultException
 {
   private int errorCode;
 
   public SystemException(ErrorMessage message, Object[] messageArgs)
   {
/* 24 */     super(createMessage(message, messageArgs), null);
/* 25 */     this.errorCode = message.getCode();
   }
 
   public SystemException(Throwable cause, ErrorMessage message, Object[] messageArgs) {
/* 29 */     super(createMessage(message, messageArgs), cause);
/* 30 */     this.errorCode = message.getCode();
   }
 
   public int getErrorCode() {
/* 34 */     return this.errorCode;
   }
 
   private static String createMessage(ErrorMessage message, Object[] messageArgs) {
/* 38 */     return message.formatMesssage(messageArgs);
   }
 }