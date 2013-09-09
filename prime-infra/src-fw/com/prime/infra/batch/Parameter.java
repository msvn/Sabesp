 package com.prime.infra.batch;
 
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class Parameter
 {
/* 13 */   public static final Integer INPUT = Integer.valueOf(0);
/* 14 */   public static final Integer OUTPUT = Integer.valueOf(1);
   private Integer parameterType;
   private String parameter;
 
   public Integer getParameterType()
   {
/* 24 */     return this.parameterType;
   }
 
   public void setParameterType(Integer parameterType) {
/* 28 */     this.parameterType = parameterType;
   }
 
   public String getParameter()
   {
/* 36 */     return this.parameter;
   }
 
   public void setParameter(String parameter) {
/* 40 */     this.parameter = parameter;
   }
 
   public Collection<Parameter> getInputParameters(Collection<Parameter> parameters)
   {
/* 50 */     Collection result = new ArrayList();
/* 51 */     for (Parameter parameter : parameters) {
/* 52 */       if (parameter.getParameterType().equals(INPUT)) {
/* 53 */         result.add(parameter);
       }
     }
/* 56 */     return result;
   }
 
   public Collection<Parameter> getOutputParameters(Collection<Parameter> parameters)
   {
/* 65 */     Collection result = new ArrayList();
/* 66 */     for (Parameter parameter : parameters) {
/* 67 */       if (parameter.getParameterType().equals(OUTPUT)) {
/* 68 */         result.add(parameter);
       }
     }
/* 71 */     return result;
   }
 }