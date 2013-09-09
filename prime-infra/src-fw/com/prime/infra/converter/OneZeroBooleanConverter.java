 package com.prime.infra.converter;
 
 public class OneZeroBooleanConverter extends BooleanConverter
 {
   public OneZeroBooleanConverter()
   {
/* 10 */     setTrueValue("1");
/* 11 */     setFalseValue("0");
   }
 }