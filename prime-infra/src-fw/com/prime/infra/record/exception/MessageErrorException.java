 package com.prime.infra.record.exception;
 
 import com.prime.infra.record.adapters.aea.AEAError;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class MessageErrorException extends Exception
 {
   private Collection<AEAError> errors;
 
   public MessageErrorException()
   {
/* 20 */     init();
   }
 
   public MessageErrorException(String arg0, Throwable arg1) {
/* 24 */     super(arg0, arg1);
/* 25 */     init();
   }
 
   public MessageErrorException(String arg0) {
/* 29 */     super(arg0);
/* 30 */     init();
   }
 
   public MessageErrorException(Throwable arg0) {
/* 34 */     super(arg0);
/* 35 */     init();
   }
 
   public void addError(AEAError error)
   {
/* 44 */     this.errors.add(error);
   }
 
   public void addAllErrors(Collection<AEAError> errors) {
/* 48 */     this.errors.addAll(errors);
   }
 
   public Collection<AEAError> getErrors()
   {
/* 55 */     return this.errors;
   }
 
   private void init() {
/* 59 */     this.errors = new ArrayList();
   }
 }