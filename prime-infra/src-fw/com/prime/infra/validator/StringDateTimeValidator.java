 package com.prime.infra.validator;
 
 import com.prime.infra.record.exception.ValidateException;
 import org.joda.time.format.DateTimeFormat;
 import org.joda.time.format.DateTimeFormatter;
 
 public class StringDateTimeValidator extends BaseValidator
 {
   private DateTimeFormatter fmt;
 
   public StringDateTimeValidator(DateTimeFormatter fmt)
   {
/* 18 */     this.fmt = fmt;
   }
 
   public StringDateTimeValidator(String pattern) {
/* 22 */     this.fmt = DateTimeFormat.forPattern(pattern);
   }
 
   public void validate(Object target) throws ValidateException {
/* 26 */     if (target == null) {
/* 27 */       reject("stringdatetime.null.error", null);
/* 28 */       return;
     }
/* 30 */     if (!(String.class.equals(target.getClass()))) {
/* 31 */       reject("stringdatetime.class.error", target);
/* 32 */       return;
     }
 
/* 35 */     String string = (String)target;
     try {
/* 37 */       if (this.fmt.parseDateTime(string) == null)
/* 38 */         reject("stringdatetime.invalid.error", string);
     }
     catch (IllegalArgumentException e)
     {
/* 42 */       reject("stringdatetime.parse.error", string);
     }
   }
 }