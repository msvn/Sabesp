 package com.prime.infra.aop;
 
 import java.util.Date;
 
 public class FaultException extends RuntimeException
 {
   private Date timeStamp;
   private String occurrenceId;
   private String methodName;
   private transient Object current;
   private transient Object[] args;
 
   public FaultException(String message, Throwable cause)
   {
/* 20 */     super(message, cause);
   }
 
   public FaultException(Throwable cause) {
/* 24 */     super(cause);
   }
 
   public Date getTimeStamp() {
/* 28 */     return this.timeStamp;
   }
 
   public void setTimeStamp(Date timeStamp) {
/* 32 */     this.timeStamp = timeStamp;
   }
 
   public void setOccurrenceId(String occurrenceId) {
/* 36 */     this.occurrenceId = occurrenceId;
   }
 
   public String getOccurrenceId() {
/* 40 */     return this.occurrenceId;
   }
 
   public String getMethodName() {
/* 44 */     return this.methodName;
   }
 
   public void setMethodName(String methodName) {
/* 48 */     this.methodName = methodName;
   }
 
   public Object getCurrent() {
/* 52 */     return this.current;
   }
 
   public void setCurrent(Object current) {
/* 56 */     this.current = current;
   }
 
   public Object[] getArgs() {
/* 60 */     return this.args;
   }
 
   public void setArgs(Object[] args) {
/* 64 */     this.args = args;
   }
 }