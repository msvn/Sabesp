 package com.prime.infra.validator;
 
 import com.prime.infra.record.exception.ValidateException;
 
 public class StringLengthValidator extends BaseValidator
 {
   private Integer min;
   private Integer max;
 
   public StringLengthValidator(Integer min, Integer max)
   {
/* 16 */     this.min = min;
/* 17 */     this.max = max;
   }
 
   public void validate(Object target) throws ValidateException {
/* 21 */     if (target == null) {
/* 22 */       reject("stringlength.null.error", null);
/* 23 */       return;
     }
/* 25 */     if (!(String.class.equals(target.getClass()))) {
/* 26 */       reject("stringlength.class.error", target);
/* 27 */       return;
     }
 
/* 30 */     String string = (String)target;
/* 31 */     if ((this.min != null) && (string.length() < this.min.intValue())) {
/* 32 */       reject("stringlength.min.error", string);
     }
/* 34 */     if ((this.max != null) && (string.length() > this.max.intValue()))
/* 35 */       reject("stringlength.max.error", string);
   }
 }