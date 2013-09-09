 package com.prime.infra.validator;
 
 import com.prime.infra.record.exception.ValidateException;
 
 public class NumberRangeValidator extends BaseValidator
 {
   private Number min;
   private Number max;
 
   public NumberRangeValidator(Number min, Number max)
   {
/* 17 */     this.min = min;
/* 18 */     this.max = max;
   }
 
   public void validate(Object target) throws ValidateException {
/* 22 */     if (target == null) {
/* 23 */       reject("numberrange.null.error", null);
/* 24 */       return;
     }
/* 26 */     if (!(Number.class.isAssignableFrom(target.getClass()))) {
/* 27 */       reject("numberrange.class.error", target);
/* 28 */       return;
     }
/* 30 */     if (!(target instanceof Comparable)) {
/* 31 */       reject("numberrange.uncomparable.error", target);
/* 32 */       return;
     }
 
/* 35 */     Comparable number = (Comparable)target;
/* 36 */     if ((this.min != null) && (number.compareTo(this.min) < 0)) {
/* 37 */       reject("numberrange.min.error", number);
     }
/* 39 */     if ((this.max != null) && (number.compareTo(this.max) > 0))
/* 40 */       reject("numberrange.max.error", number);
   }
 }