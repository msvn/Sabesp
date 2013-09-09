 package com.prime.infra.jmx;
 
 import javax.management.MalformedObjectNameException;
 import javax.management.ObjectName;
 import org.springframework.jmx.export.naming.ObjectNamingStrategy;
 import org.springframework.jmx.support.ObjectNameManager;
 import org.springframework.util.ClassUtils;
 
 public class WebsphereNamingStrategy
   implements ObjectNamingStrategy
 {
   private String domainName;
   private String cellName;
   private String nodeName;
   private String processName;
 
   public ObjectName getObjectName(Object object, String name)
     throws MalformedObjectNameException
   {
/* 22 */     StringBuffer objectName = new StringBuffer();
/* 23 */     objectName.append(this.domainName);
/* 24 */     objectName.append(":type=");
/* 25 */     objectName.append(ClassUtils.getShortName(object.getClass()));
/* 26 */     objectName.append(",name=");
/* 27 */     objectName.append(name);
/* 28 */     objectName.append(",cell=");
/* 29 */     objectName.append(this.cellName);
/* 30 */     objectName.append(",node=");
/* 31 */     objectName.append(this.nodeName);
/* 32 */     objectName.append(",process=");
/* 33 */     objectName.append(this.processName);
 
/* 35 */     return ObjectNameManager.getInstance(objectName.toString());
   }
 
   public String getDomainName() {
/* 39 */     return this.domainName;
   }
 
   public void setDomainName(String domainName) {
/* 43 */     this.domainName = domainName;
   }
 
   public String getCellName() {
/* 47 */     return this.cellName;
   }
 
   public void setCellName(String cellName) {
/* 51 */     this.cellName = cellName;
   }
 
   public String getNodeName() {
/* 55 */     return this.nodeName;
   }
 
   public void setNodeName(String nodeName) {
/* 59 */     this.nodeName = nodeName;
   }
 
   public String getProcessName() {
/* 63 */     return this.processName;
   }
 
   public void setProcessName(String processName) {
/* 67 */     this.processName = processName;
   }
 }