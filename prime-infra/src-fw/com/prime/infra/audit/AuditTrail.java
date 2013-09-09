 package com.prime.infra.audit;
 
 import java.util.concurrent.BlockingQueue;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public class AuditTrail
 {
/* 13 */   private static Logger logger = LoggerFactory.getLogger(AuditTrail.class);
   private BlockingQueue<AuditInfo> queue;
   private static AuditTrail instance;
 
   public static AuditTrail getInstance()
   {
/* 20 */     return instance;
   }
 
   public AuditTrail(BlockingQueue<AuditInfo> queue) {
/* 24 */     this.queue = queue;
   }
 
   public void setupLogger() {
/* 28 */     if (instance == null)
/* 29 */       instance = this;
   }
 
   public void log(AuditInfo auditInfo)
   {
     try {
/* 35 */       this.queue.put(auditInfo);
     }
     catch (InterruptedException e) {
/* 38 */       logger.error("Error addind AuditInfo to queue", e);
     }
   }
 }