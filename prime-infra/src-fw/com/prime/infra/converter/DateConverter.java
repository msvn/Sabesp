 package com.prime.infra.converter;
 
 import com.prime.infra.record.exception.ConverterException;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 
 public class DateConverter extends BaseConverter<Date>
 {
   private SimpleDateFormat sdf;
 
   public void setPattern(String pattern)
   {
/* 23 */     this.sdf = new SimpleDateFormat(pattern);
   }
 
   public String formatValue(Object value)
     throws ConverterException
   {
/* 30 */     if (!(value instanceof Date)) {
/* 31 */       throw new ConverterException("O objeto nao a uma instancia de java.util.Date", (value == null) ? "null" : value.getClass().getName());
     }
 
/* 34 */     return this.sdf.format((Date)value);
   }
 
   public Date parseValue(String value)
     throws ConverterException
   {
/* 41 */     if (isNull(value)) {
/* 42 */       return null;
     }
 
/* 45 */     if (null == this.sdf)
/* 46 */       throw new ConverterException("Use o metodo setPattern()", null);
     try
     {
/* 49 */       return this.sdf.parse(value);
     }
     catch (ParseException e) {
/* 52 */       throw new ConverterException("Impossivel converter o valor: [" + value + "]", e);
     }
   }
 }