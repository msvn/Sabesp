 package com.prime.infra.validator;
 
 import com.prime.infra.record.exception.ValidateException;
 import java.math.BigDecimal;
 
 public class NumberDigitsValidator extends BaseValidator
 {
   private Integer integerDigits;
   private Integer fractionalDigits;
 
   public NumberDigitsValidator(Integer integerDigits, Integer fractionalDigits)
   {
/* 17 */     this.integerDigits = integerDigits;
/* 18 */     this.fractionalDigits = fractionalDigits;
   }
 
   public NumberDigitsValidator(Integer integerDigits) {
/* 22 */     this.integerDigits = integerDigits;
/* 23 */     this.fractionalDigits = Integer.valueOf(0);
   }
 
   public void validate(Object target) throws ValidateException {
/* 27 */     if (target == null) {
/* 28 */       reject("numberdigits.null.error", null);
/* 29 */       return;
     }
/* 31 */     if (!(BigDecimal.class.equals(target.getClass()))) {
/* 32 */       reject("numberdigits.class.error", target);
/* 33 */       return;
     }
 
/* 36 */     BigDecimal bd = (BigDecimal)target;
/* 37 */     int scale = bd.scale();
/* 38 */     if ((this.fractionalDigits != null) && 
/* 39 */       (scale >= 0) && (scale > this.fractionalDigits.intValue())) {
/* 40 */       reject("numberdigits.fractional.error", bd);
     }
 
/* 44 */     if ((this.integerDigits == null) || 
/* 45 */       (bd.precision() - scale <= this.integerDigits.intValue())) return;
/* 46 */     reject("numberdigits.integer.error", bd);
   }
 }