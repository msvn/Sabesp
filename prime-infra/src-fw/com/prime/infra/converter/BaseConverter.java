 package com.prime.infra.converter;
 
 import com.prime.infra.record.exception.ConverterException;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 
 public abstract class BaseConverter<T>
   implements Converter<T>
 {
   protected Pattern nullPattern;
 
   public BaseConverter()
   {
/* 17 */     this.nullPattern = Pattern.compile("^[ ]*$");
   }
 
   protected void reject(String errorCode, Object value)
     throws ConverterException
   {
/* 27 */     throw new ConverterException(errorCode, value);
   }
 
   protected boolean isNull(String value) {
/* 31 */     if (value.equals("")) {
/* 32 */       return true;
     }
/* 34 */     return (!(this.nullPattern.matcher(value).matches()));
   }
 }