 package com.prime.infra.validator;
 
 import com.prime.infra.record.exception.ValidateException;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 
 public class StringPatternValidator extends BaseValidator
 {
   private Pattern pattern;
 
   public StringPatternValidator(Pattern pattern)
   {
/* 17 */     this.pattern = pattern;
   }
 
   public StringPatternValidator(String s) {
/* 21 */     this.pattern = Pattern.compile(s);
   }
 
   public void validate(Object target) throws ValidateException {
/* 25 */     if (target == null) {
/* 26 */       reject("stringpattern.null.error", null);
/* 27 */       return;
     }
/* 29 */     if (!(String.class.equals(target.getClass()))) {
/* 30 */       reject("stringpattern.class.error", target);
/* 31 */       return;
     }
 
/* 34 */     String string = (String)target;
/* 35 */     Matcher matcher = this.pattern.matcher(string);
 
/* 37 */     if (!(matcher.matches()))
/* 38 */       reject("stringpattern.match.error", string);
   }
 }