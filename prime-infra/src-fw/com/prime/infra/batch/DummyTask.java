 package com.prime.infra.batch;
 
 public class DummyTask extends Task
 {
   public Object execute(Object data)
     throws Exception
   {
/* 12 */     return data;
   }
 }