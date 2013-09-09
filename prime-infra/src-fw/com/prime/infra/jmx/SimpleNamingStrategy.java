 package com.prime.infra.jmx;
 
 import javax.management.MalformedObjectNameException;
 import javax.management.ObjectName;
 import org.springframework.jmx.export.naming.ObjectNamingStrategy;
 import org.springframework.jmx.support.ObjectNameManager;
 import org.springframework.util.ClassUtils;
 
 public class SimpleNamingStrategy
   implements ObjectNamingStrategy
 {
   private String domainName;
 
   public ObjectName getObjectName(Object object, String name)
     throws MalformedObjectNameException
   {
/* 19 */     StringBuffer objectName = new StringBuffer();
/* 20 */     objectName.append(this.domainName);
/* 21 */     objectName.append(":type=");
/* 22 */     objectName.append(ClassUtils.getShortName(object.getClass()));
/* 23 */     objectName.append(",name=");
/* 24 */     objectName.append(name);
 
/* 26 */     return ObjectNameManager.getInstance(objectName.toString());
   }
 
   public String getDomainName() {
/* 30 */     return this.domainName;
   }
 
   public void setDomainName(String domainName) {
/* 34 */     this.domainName = domainName;
   }
 }