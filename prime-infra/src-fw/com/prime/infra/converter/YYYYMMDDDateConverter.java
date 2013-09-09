 package com.prime.infra.converter;
 
 import com.prime.infra.record.exception.ConverterException;
 import java.util.Date;
 
 public class YYYYMMDDDateConverter extends DateConverter
 {
   public YYYYMMDDDateConverter()
   {
/* 14 */     setPattern("yyyy-MM-dd");
   }
 
   public Date parseValue(String valueToConvert)
     throws ConverterException
   {
/* 24 */     return super.parseValue(valueToConvert);
   }
 }