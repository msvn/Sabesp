 package com.prime.infra.validator;
 
 public class EmailValidator extends StringPatternValidator
 {
   private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
 
   public EmailValidator()
   {
/* 13 */     super("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
   }
 }