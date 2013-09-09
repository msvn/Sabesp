 package com.prime.infra;
 
 import java.text.MessageFormat;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public class ErrorMessage
 {
/* 14 */   public static final ErrorMessage UNEXPECTED_ERROR = new ErrorMessage(-1, "Unexpected error: {0}");
/* 15 */   public static final ErrorMessage NULL_ARGUMENT = new ErrorMessage(-2, "Null argument");
/* 16 */   public static final ErrorMessage VALIDATION_ERROR = new ErrorMessage(-3, "Validation error: {0} value: {1}");
/* 17 */   public static final ErrorMessage CONVERTER_ERROR = new ErrorMessage(-4, "Converter error: {0} value: {1}");
 
/* 21 */   private static final Logger LOGGER = LoggerFactory.getLogger(ErrorMessage.class);
   private int code;
   private String message;
 
   protected ErrorMessage(int code, String message)
   {
/* 27 */     this.code = code;
/* 28 */     this.message = message;
   }
 
   public int getCode() {
/* 32 */     return this.code;
   }
 
   public String getMesssage() {
/* 36 */     return this.message;
   }
 
   public String formatMesssage(Object[] args) {
/* 40 */     String formatedMessage = this.message;
     try {
/* 42 */       formatedMessage = MessageFormat.format(this.message, args);
     }
     catch (IllegalArgumentException e) {
/* 45 */       LOGGER.warn("\"" + this.message + "\" " + e, e);
     }
/* 47 */     return addCode(formatedMessage);
   }
 
   private String addCode(String s) {
/* 51 */     return "[" + this.code + ": " + s + "]";
   }
 }