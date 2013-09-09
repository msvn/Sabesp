 package com.prime.infra.converter;
 
 import com.prime.infra.record.exception.ConverterException;
 import java.text.DecimalFormat;
 import java.text.ParseException;
 
 public class NumberConverter extends BaseConverter<Number>
 {
   private DecimalFormat decimalFormat;
 
   public void setPattern(String pattern)
   {
/* 20 */     this.decimalFormat = new DecimalFormat(pattern);
   }
 
   public String formatValue(Object value)
     throws ConverterException
   {
/* 29 */     if (!(value instanceof Number)) {
/* 30 */       throw new ConverterException("O objeto não é uma instância de java.lang.Number", (value == null) ? "null" : value.getClass().getName());
     }
 
/* 33 */     if (null == this.decimalFormat) {
/* 34 */       throw new ConverterException("Use o método setPattern()", null);
     }
/* 36 */     return this.decimalFormat.format(value);
   }
 
   public Number parseValue(String value)
     throws ConverterException
   {
/* 45 */     if (isNull(value))
/* 46 */       return null;
     try
     {
/* 49 */       return this.decimalFormat.parse(value);
     }
     catch (ParseException e) {
/* 52 */       reject("invalid.number.format", value); }
/* 53 */     return null;
   }
 }