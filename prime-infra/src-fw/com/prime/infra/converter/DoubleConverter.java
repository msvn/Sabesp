 package com.prime.infra.converter;
 
 public class DoubleConverter extends NumberConverter
 {
   public DoubleConverter()
   {
/* 12 */     super.setPattern("0.00");
   }
 
   public Double parseValue(String value) {
/* 16 */     if (isNull(value)) {
/* 17 */       return null;
     }
/* 19 */     return Double.valueOf(super.parseValue(value).doubleValue());
   }
 }