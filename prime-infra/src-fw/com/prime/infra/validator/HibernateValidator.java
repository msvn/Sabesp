 package com.prime.infra.validator;
 
 import com.prime.infra.record.exception.ValidateException;
 import org.hibernate.validator.ClassValidator;
 import org.hibernate.validator.InvalidValue;
 
 public class HibernateValidator extends BaseValidator
 {
   private ClassValidator validator;
 
   public HibernateValidator(Class clazz)
   {
/* 18 */     this.validator = new ClassValidator(clazz);
   }
 
   public void validate(Object target) throws ValidateException {
/* 22 */     if (target == null) {
/* 23 */       reject("hibernate.null.error", null);
/* 24 */       return;
     }
 
/* 27 */     InvalidValue[] invalidValues = this.validator.getInvalidValues(target);
 
/* 29 */     if ((invalidValues != null) && (invalidValues.length > 0))
/* 30 */       reject("hibernate.validator.error", null);
   }
 }