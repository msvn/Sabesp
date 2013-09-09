 package com.prime.infra.converter;
 
 import java.math.BigDecimal;
 
 public class BigDecimalConverter extends NumberConverter
 {
/* 15 */   private int scale = 0;
 
   public void setScale(int scale) {
/* 18 */     this.scale = scale;
   }
 
   public BigDecimalConverter() {
/* 22 */     super.setPattern("0.00");
   }
 
   public BigDecimal parseValue(String value) {
/* 26 */     if (isNull(value)) {
/* 27 */       return null;
     }
/* 29 */     return new BigDecimal(super.parseValue(value).doubleValue()).setScale(this.scale, 6);
   }
 }