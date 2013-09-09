 package com.prime.infra.webservice;
 
 public class FaultInfo
 {
   protected String message;
   protected Integer code;
 
   public FaultInfo()
   {
   }
 
   public FaultInfo(String message, Integer code)
   {
/* 16 */     this.message = message;
/* 17 */     this.code = code;
   }
 
   public String getMessage() {
/* 21 */     return this.message;
   }
 
   public void setMessage(String message) {
/* 25 */     this.message = message;
   }
 
   public Integer getCode() {
/* 29 */     return this.code;
   }
 
   public void setCode(Integer code) {
/* 33 */     this.code = code;
   }
 
   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append("FaultInfo");
/* 39 */     sb.append("[message=").append(this.message);
/* 40 */     sb.append(", code=").append(this.code);
/* 41 */     sb.append(']');
/* 42 */     return sb.toString();
   }
 }