 package com.prime.infra.audit;
 
 import java.util.concurrent.BlockingQueue;
 import java.util.concurrent.TimeUnit;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public class AuditEventManager
 {
/* 14 */   private static Logger logger = LoggerFactory.getLogger(AuditEventManager.class);
   private AuditHandler auditHandler;
   private BlockingQueue<AuditInfo> queue;
   private boolean running;
 
   public void setAuditHandler(AuditHandler auditHandler)
   {
/* 21 */     this.auditHandler = auditHandler;
   }
 
   public void setAuditEventQueue(BlockingQueue<AuditInfo> queue) {
/* 25 */     this.queue = queue;
   }
 
   public void start() {
/* 29 */     if (this.running) {
/* 30 */       return;
     }
 
/* 33 */     this.running = true;
 
/* 35 */     Thread t = new Thread(new Runnable() {
       public void run() {
/* 37 */         AuditEventManager.logger.info("AuditEventManager started");
/* 38 */         AuditEventManager.this.processEvents();
       }
     });
/* 41 */     t.setDaemon(true);
/* 42 */     t.start();
   }
 
   public void stop() {
/* 46 */     logger.debug("Stopping AuditEventManager");
/* 47 */     this.running = false;
   }
 
   private void processEvents() {
/* 51 */     while (this.running)
       try {
/* 53 */         AuditInfo props = (AuditInfo)this.queue.poll(30L, TimeUnit.SECONDS);
/* 54 */         if (props != null)
/* 55 */           logEvent(props);
       }
       catch (InterruptedException e)
       {
       }
   }
 
   private void logEvent(AuditInfo props)
   {
     try
     {
/* 66 */       this.auditHandler.logEvent(props);
     }
     catch (Exception e) {
/* 69 */       logger.error("Error in AuditHandler: " + e.getMessage(), e);
     }
   }
 }