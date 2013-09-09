 package com.prime.infra.jmx;
 
 import com.jamonapi.Monitor;
 import com.jamonapi.MonitorFactory;
 import java.util.Date;
 import org.springframework.jmx.export.annotation.ManagedAttribute;
 import org.springframework.jmx.export.annotation.ManagedResource;
 
 @ManagedResource
 public class ServiceStats
 {
   private Monitor monitor;
 
   public ServiceStats(String name)
   {
/* 21 */     this.monitor = MonitorFactory.getMonitor(name, "ms.");
   }
 
   @ManagedAttribute
   public double getHits() {
/* 26 */     return this.monitor.getHits();
   }
 
   @ManagedAttribute
   public double getLastValue() {
/* 31 */     return this.monitor.getLastValue();
   }
 
   @ManagedAttribute
   public double getMin() {
/* 36 */     return this.monitor.getMin();
   }
 
   @ManagedAttribute
   public double getMax() {
/* 41 */     return this.monitor.getMax();
   }
 
   @ManagedAttribute
   public double getAvg() {
/* 46 */     return this.monitor.getAvg();
   }
 
   @ManagedAttribute
   public double getTotal() {
/* 51 */     return this.monitor.getTotal();
   }
 
   @ManagedAttribute
   public double getStdDev() {
/* 56 */     return this.monitor.getStdDev();
   }
 
   @ManagedAttribute
   public Date getFirstAccess() {
/* 61 */     return this.monitor.getFirstAccess();
   }
 
   @ManagedAttribute
   public Date getLastAccess() {
/* 66 */     return this.monitor.getLastAccess();
   }
 }