 package com.prime.infra.converter;
 
 import com.prime.infra.record.exception.ConverterException;
 
 public class BooleanConverter extends BaseConverter<Boolean>
 {
   private String trueValue;
   private String falseValue;
 
   public void setFalseValue(String falseValue)
   {
/* 15 */     this.falseValue = falseValue;
   }
 
   public void setTrueValue(String trueValue) {
/* 19 */     this.trueValue = trueValue;
   }
 
   public String formatValue(Object value)
     throws ConverterException
   {
/* 28 */     if (!(value instanceof Boolean)) {
/* 29 */       throw new ConverterException("O objeto não é uma instância de java.lang.Boolean", (value == null) ? "null" : value.getClass().getName());
     }
 
/* 32 */     if (null == this.trueValue) {
/* 33 */       throw new ConverterException("Use o método setTrueValue()", null);
     }
/* 35 */     if (null == this.falseValue) {
/* 36 */       throw new ConverterException("Use o método setFalseValue()", null);
     }
 
/* 39 */     if (((Boolean)value).booleanValue()) {
/* 40 */       return this.trueValue;
     }
 
/* 43 */     return this.falseValue;
   }
 
   public Boolean parseValue(String value)
     throws ConverterException
   {
/* 53 */     if (isNull(value)) {
/* 54 */       return null;
     }
/* 56 */     if (null == this.trueValue) {
/* 57 */       throw new ConverterException("Use o método setTrueValue()", null);
     }
/* 59 */     if (value.equalsIgnoreCase(this.trueValue)) {
/* 60 */       return Boolean.TRUE;
     }
/* 62 */     return Boolean.FALSE;
   }
 }