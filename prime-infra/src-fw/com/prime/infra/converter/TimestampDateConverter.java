 package com.prime.infra.converter;
 
 import com.prime.infra.record.exception.ConverterException;
 import java.util.Date;
 
 public class TimestampDateConverter extends DateConverter
 {
   public TimestampDateConverter()
   {
/* 14 */     setPattern("yyyy-MM-dd-hh.mm.ss.S");
   }
 
   public Date parseValue(String valueToConvert)
     throws ConverterException
   {
/* 24 */     return super.parseValue(valueToConvert);
   }
 }