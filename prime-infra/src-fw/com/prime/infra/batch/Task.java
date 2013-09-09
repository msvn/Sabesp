 package com.prime.infra.batch;
 
 import java.util.concurrent.Callable;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public abstract class Task<PARAM, RET>
   implements Callable<RET>
 {
/* 19 */   private static Logger logger = LoggerFactory.getLogger(Task.class);
   private int id;
   private RecordConsumer<RET> output;
   private PARAM param;
 
   public int getId()
   {
/* 26 */     return this.id;
   }
 
   public void setId(int id) {
/* 30 */     this.id = id;
   }
 
   public RecordConsumer<RET> getOutput() {
/* 34 */     return this.output;
   }
 
   public void setOutput(RecordConsumer<RET> output) {
/* 38 */     this.output = output;
   }
 
   public PARAM getParam() {
/* 42 */     return this.param;
   }
 
   public void setParam(PARAM param) {
/* 46 */     this.param = param;
   }
 
   public String toString() {
/* 50 */     return "Task[" + this.id + "]";
   }
 
   public RET call() throws Exception {
/* 54 */     Object result = execute(this.param);
/* 55 */     logger.trace("Task finished: " + this);
/* 56 */     if (this.output != null) {
/* 57 */       logger.trace("Writing result = " + result);
/* 58 */       this.output.consume((RET)result);
     }
/* 60 */     return (RET)result;
   }
 
   public abstract RET execute(PARAM paramPARAM)
     throws Exception;
 }