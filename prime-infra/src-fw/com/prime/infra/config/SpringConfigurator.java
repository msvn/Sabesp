 package com.prime.infra.config;
 
 import java.io.IOException;
 import java.lang.reflect.Constructor;
 import java.net.URL;
 import java.util.Arrays;
 import java.util.Comparator;
 import java.util.Set;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.BeansException;
 import org.springframework.beans.factory.BeanInitializationException;
 import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
 import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
 import org.springframework.beans.factory.support.BeanDefinitionReader;
 import org.springframework.beans.factory.support.DefaultListableBeanFactory;
 import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
 import org.springframework.context.ResourceLoaderAware;
 import org.springframework.core.io.Resource;
 import org.springframework.core.io.ResourceLoader;
 import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
 import org.springframework.core.io.support.ResourcePatternResolver;
 import org.springframework.web.context.support.XmlWebApplicationContext;
 
 public class SpringConfigurator extends PropertyPlaceholderConfigurer
   implements ResourceLoaderAware
 {
   protected Logger logger;
   private static final String STANDARD_LOCATION_PREFIX = "classpath*:META-INF/config";
   private static final String STANDARD_WEB_LOCATION_PREFIX = "/WEB-INF/config";
   protected boolean inWebConteiner;
   protected ResourceLoader resourceLoader;
 
   public SpringConfigurator()
   {
/*  53 */     this.logger = LoggerFactory.getLogger(SpringConfigurator.class);
   }
 
   public void setResourceLoader(ResourceLoader resourceLoader)
   {
/*  63 */     this.resourceLoader = resourceLoader;
 
/*  65 */     if (resourceLoader instanceof XmlWebApplicationContext) {
/*  66 */       Resource r = resourceLoader.getResource("/WEB-INF/config");
/*  67 */       if (r.exists())
/*  68 */         this.inWebConteiner = true;
     }
   }
 
   public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException
   {
/*  74 */     setSystemPropertiesMode(2);
 
/*  77 */     setLocations(findProperties());
 
/*  80 */     BeanDefinitionReader reader = new XmlBeanDefinitionReader((DefaultListableBeanFactory)factory);
/*  81 */     reader.loadBeanDefinitions(findContexts());
 
/*  83 */     super.postProcessBeanFactory(factory);
   }
 
   protected Resource[] findProperties() {
/*  87 */     Resource[] resources = findStandardResources("properties");
/*  88 */     for (Resource r : resources) {
/*  89 */       this.logger.debug("Properties -> " + r);
     }
/*  91 */     return resources;
   }
 
   protected Resource[] findContexts() {
/*  95 */     Resource[] resources = findStandardResources("xml");
/*  96 */     for (Resource r : resources) {
/*  97 */       this.logger.debug("Context -> " + r);
     }
/*  99 */     return resources;
   }
 
   protected Resource[] findStandardResources(String extension) {
/* 103 */     Resource[] resources = findModeResources("classpath*:META-INF/config", extension);
/* 104 */     if (this.inWebConteiner) {
/* 105 */       Resource[] webResources = findModeResources("/WEB-INF/config", extension);
/* 106 */       resources = concat(resources, webResources);
     }
/* 108 */     return resources;
   }
 
   protected Resource[] findModeResources(String prefix, String extension) {
/* 112 */     Resource[] resources = findResources(prefix + "/*." + extension);
/* 113 */     Set<String> runningModes = RunningMode.get();
/* 114 */     for (String runningMode : runningModes) {
/* 115 */       Resource[] modeResources = findResources(prefix + "/" + runningMode + "/*." + extension);
/* 116 */       resources = concat(resources, modeResources);
     }
/* 118 */     return resources;
   }
 
   protected Resource[] findResources(String locationPattern) {
/* 122 */     ResourcePatternResolver resolver = getResolver();
     try {
/* 124 */       Resource[] resources = resolver.getResources(locationPattern);
/* 125 */       Arrays.sort(resources, new ResourceComparator());
/* 126 */       return resources;
     }
     catch (IOException e) {
/* 129 */       throw new BeanInitializationException("Error reading resources", e);
     }
   }
 
   protected ResourcePatternResolver getResolver()
   {
/* 135 */     if (this.inWebConteiner) {
       try {
/* 137 */         Constructor ctor = Class.forName("ServletContextResourcePatternResolver").getConstructor(new Class[0]);
/* 138 */         return ((ResourcePatternResolver)ctor.newInstance(new Object[] { this.resourceLoader }));
       }
       catch (Exception e)
       {
       }
     }
/* 144 */     return new PathMatchingResourcePatternResolver(this.resourceLoader);
   }
 
   protected Resource[] concat(Resource[] a1, Resource[] a2) {
/* 148 */     int l1 = a1.length;
/* 149 */     int l2 = a2.length;
 
/* 151 */     Resource[] r = new Resource[l1 + l2];
 
/* 153 */     System.arraycopy(a1, 0, r, 0, l1);
/* 154 */     System.arraycopy(a2, 0, r, l1, l2);
 
/* 156 */     return r;
   }
 
   protected static final class ResourceComparator implements Comparator<Resource>
   {
     protected static final String WEB_INF_CLASSES_META_INF_CONFIG = "/WEB-INF/classes/META-INF/config/";
 
     public int compare(Resource r1, Resource r2) {
       try {
/* 165 */         String name1 = r1.getURL().toExternalForm().replace('\\', '/');
/* 166 */         String name2 = r2.getURL().toExternalForm().replace('\\', '/');
/* 167 */         boolean webInfClasses1 = name1.indexOf("/WEB-INF/classes/META-INF/config/") != -1;
/* 168 */         boolean webInfClasses2 = name2.indexOf("/WEB-INF/classes/META-INF/config/") != -1;
/* 169 */         if ((!(webInfClasses1)) && (webInfClasses2)) {
/* 170 */           return -1;
         }
/* 172 */         if ((webInfClasses1) && (!(webInfClasses2))) {
/* 173 */           return 1;
         }
       }
       catch (IOException io)
       {
       }
 
/* 180 */       return r1.getFilename().compareTo(r2.getFilename());
     }
   }
 }