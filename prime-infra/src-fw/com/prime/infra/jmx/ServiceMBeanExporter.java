 package com.prime.infra.jmx;
 
 import java.lang.reflect.Method;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import javax.management.JMException;
 import javax.management.ObjectName;
 import javax.management.modelmbean.ModelMBean;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.aop.support.AopUtils;
 import org.springframework.beans.BeansException;
 import org.springframework.beans.factory.BeanFactory;
 import org.springframework.beans.factory.BeanFactoryAware;
 import org.springframework.beans.factory.DisposableBean;
 import org.springframework.beans.factory.InitializingBean;
 import org.springframework.beans.factory.ListableBeanFactory;
 import org.springframework.beans.factory.support.DefaultListableBeanFactory;
 import org.springframework.jmx.export.MBeanExportException;
 import org.springframework.jmx.export.SpringModelMBean;
 import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
 import org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler;
 import org.springframework.jmx.export.naming.ObjectNamingStrategy;
 import org.springframework.jmx.support.JmxUtils;
 import org.springframework.jmx.support.MBeanRegistrationSupport;
 import org.springframework.util.Assert;
 import org.springframework.util.CollectionUtils;
 
 public class ServiceMBeanExporter extends MBeanRegistrationSupport
   implements BeanFactoryAware, InitializingBean, DisposableBean
 {
   private Logger logger;
   private static final String SERVICE_SUFFIX = "ServiceImpl";
   private DefaultListableBeanFactory beanFactory;
   private final AnnotationJmxAttributeSource annotationSource;
   private final MetadataMBeanInfoAssembler metadataAssembler;
   private ObjectNamingStrategy namingStrategy;
 
   public ServiceMBeanExporter()
   {
/*  32 */     this.logger = LoggerFactory.getLogger(ServiceMBeanExporter.class);
 
/*  38 */     this.annotationSource = new AnnotationJmxAttributeSource();
/*  39 */     this.metadataAssembler = new MetadataMBeanInfoAssembler(this.annotationSource);
   }
 
   public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
/*  43 */     if (beanFactory instanceof ListableBeanFactory) {
/*  44 */       this.beanFactory = ((DefaultListableBeanFactory)beanFactory);
     }
     else
/*  47 */       this.logger.info("MBeanExporter not running in a ListableBeanFactory: Autodetection of MBeans not available.");
   }
 
   public ObjectNamingStrategy getNamingStrategy()
   {
/*  52 */     return this.namingStrategy;
   }
 
   public void setNamingStrategy(ObjectNamingStrategy namingStrategy) {
/*  56 */     this.namingStrategy = namingStrategy;
   }
 
   public void afterPropertiesSet()
   {
     Iterator i$;
/*  60 */     if (this.server == null) {
/*  61 */       this.server = JmxUtils.locateMBeanServer();
     }
 
/*  64 */     Map beans = autodetectServices();
 
/*  66 */     if (!(beans.isEmpty()))
/*  67 */       for (i$ = beans.entrySet().iterator(); i$.hasNext(); ) { Object o = i$.next();
/*  68 */         Map.Entry entry = (Map.Entry)o;
/*  69 */         Assert.notNull(entry.getKey(), "Beans key must not be null");
 
/*  71 */         registerService(entry.getValue(), (String)entry.getKey());
       }
   }
 
   public void destroy()
   {
/*  77 */     this.logger.info("Unregistering JMX-exposed beans on shutdown");
/*  78 */     unregisterBeans();
   }
 
   private Map autodetectServices() {
/*  82 */     Map beans = new HashMap();
 
/*  84 */     String[] beanNames = this.beanFactory.getBeanNamesForType(Object.class, true, false);
/*  85 */     for (String beanName : beanNames) {
/*  86 */       Class beanClass = this.beanFactory.getType(beanName);
/*  87 */       if ((beanClass != null) && (isServiceImpl(beanClass, beanName))) {
/*  88 */         Object beanInstance = this.beanFactory.getBean(beanName);
/*  89 */         if ((beans.containsValue(beanName)) || ((beanInstance != null) && (CollectionUtils.containsInstance(beans.values(), beanInstance))))
           continue;
/*  91 */         beans.put(beanName, (beanInstance != null) ? beanInstance : beanName);
       }
 
     }
 
/*  96 */     return beans;
   }
 
   private boolean isServiceImpl(Class beanClass, String beanName) {
/* 100 */     return beanName.endsWith("ServiceImpl");
   }
 
   private Class findServiceInterface(Class targetClass)
   {
/* 108 */     String className = targetClass.getName();
/* 109 */     if (className.endsWith("ServiceImpl"))
     {
/* 111 */       String interfaceName = className.substring(0, className.length() - 4);
/* 112 */       Class[] interfaces = targetClass.getInterfaces();
/* 113 */       for (Class i : interfaces) {
/* 114 */         if (i.getName().equals(interfaceName)) {
/* 115 */           return i;
         }
 
       }
 
/* 120 */       int x = interfaceName.lastIndexOf(46);
/* 121 */       int y = interfaceName.substring(0, x).lastIndexOf(46);
/* 122 */       interfaceName = interfaceName.substring(0, y) + interfaceName.substring(x);
/* 123 */       interfaces = targetClass.getInterfaces();
/* 124 */       for (Class i : interfaces) {
/* 125 */         if (i.getName().equals(interfaceName)) {
/* 126 */           return i;
         }
       }
     }
/* 130 */     return null;
   }
 
   private void registerService(Object bean, String beanKey)
   {
/* 137 */     Class targetClass = AopUtils.getTargetClass(bean);
/* 138 */     Class serviceInterface = findServiceInterface(targetClass);
/* 139 */     if (serviceInterface != null) {
/* 140 */       this.logger.info("Service '" + beanKey + "' has been autodetected for JMX interface monitoring");
/* 141 */       Method[] methods = serviceInterface.getDeclaredMethods();
/* 142 */       for (Method method : methods) {
/* 143 */         String fullMethodName = targetClass.getName() + "." + method.getName();
 
/* 145 */         Object monitor = new ServiceStats(fullMethodName);
         try {
/* 147 */           registerMBean(monitor, fullMethodName);
         }
         catch (JMException e) {
/* 150 */           this.logger.error("Error registering MBean ", e);
         }
       }
     }
   }
 
   private void registerMBean(Object bean, String beanKey) throws JMException {
/* 157 */     ObjectName objectName = this.namingStrategy.getObjectName(bean, beanKey);
/* 158 */     ModelMBean mbean = createAndConfigureMBean(bean, beanKey);
/* 159 */     doRegister(mbean, objectName);
/* 160 */     if (this.logger.isInfoEnabled())
/* 161 */       this.logger.debug("Created managed bean '" + beanKey + "': registering with JMX server as MBean [" + objectName + "]");
   }
 
   protected ModelMBean createAndConfigureMBean(Object managedResource, String beanKey) throws MBeanExportException
   {
     try
     {
/* 168 */       ModelMBean mbean = new SpringModelMBean();
/* 169 */       mbean.setModelMBeanInfo(this.metadataAssembler.getMBeanInfo(managedResource, beanKey));
/* 170 */       mbean.setManagedResource(managedResource, "ObjectReference");
/* 171 */       return mbean;
     }
     catch (Exception ex) {
/* 174 */       throw new MBeanExportException("Could not create ModelMBean for managed resource [" + managedResource + "] with key '" + beanKey + "'", ex);
     }
   }
 }