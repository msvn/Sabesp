 package com.prime.infra.config;
 
 import java.io.IOException;
 import java.lang.reflect.Constructor;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Comparator;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Properties;
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
 
 public class SpringConfiguratorEjb extends PropertyPlaceholderConfigurer
   implements ResourceLoaderAware
 {
   protected Logger logger;
   protected static final String STANDARD_LOCATION_PREFIX = "classpath*:META-INF/config";
   protected static final String WEBSPHERE_LOCATION_PREFIX = "META-INF/config";
   protected static final String STANDARD_WEB_LOCATION_PREFIX = "/WEB-INF/config";
   protected static final String FW_RESOURCE_PATH = "META-INF/config/fw-resources.properties";
   protected boolean inWebConteiner;
   protected ResourceLoader resourceLoader;
   protected Properties frameworkProperties;
 
   public SpringConfiguratorEjb()
   {
/*  52 */     this.logger = LoggerFactory.getLogger(SpringConfigurator.class);
   }
 
   public void setResourceLoader(ResourceLoader resourceLoader)
   {
/*  65 */     this.resourceLoader = resourceLoader;
 
/*  67 */     if (resourceLoader instanceof XmlWebApplicationContext) {
/*  68 */       Resource r = resourceLoader.getResource("/WEB-INF/config");
/*  69 */       if (r.exists())
/*  70 */         this.inWebConteiner = true;
     }
   }
 
   public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
/*  75 */     setSystemPropertiesMode(2);
 
/*  78 */     setLocations(findProperties());
 
/*  81 */     BeanDefinitionReader reader = new XmlBeanDefinitionReader((DefaultListableBeanFactory)factory);
/*  82 */     reader.loadBeanDefinitions(findContexts());
 
/*  84 */     super.postProcessBeanFactory(factory);
   }
 
   protected Resource[] findProperties() {
/*  88 */     Resource[] resources = findStandardResources("properties");
/*  89 */     for (Resource r : resources) {
/*  90 */       this.logger.debug("Properties -> " + r);
     }
/*  92 */     if ((!(this.inWebConteiner)) && (((resources == null) || (resources.length == 0)))) {
/*  93 */       List webspherePropsResources = parseResourceNames("fw.properties");
/*  94 */       List runningModePropsResources = parseResourceNames("running.mode.properties");
 
/*  97 */       List resourceList = getRequiredResources(webspherePropsResources);
/*  98 */       resourceList.addAll(getRunningModeResources(runningModePropsResources));
/*  99 */       resources = (Resource[])resourceList.toArray(resources);
     }
/* 101 */     return resources;
   }
 
   protected Resource[] findContexts() {
/* 105 */     Resource[] resources = findStandardResources("xml");
/* 106 */     for (Resource r : resources) {
/* 107 */       this.logger.debug("Context -> " + r);
     }
/* 109 */     if ((!(this.inWebConteiner)) && (((resources == null) || (resources.length == 0)))) {
/* 110 */       List websphereReqXmlResources = parseResourceNames("fw.required.xml");
/* 111 */       List websphereXmlResources = parseResourceNames("fw.xml");
/* 112 */       List runningModeXmlResources = parseResourceNames("running.mode.xml");
 
/* 115 */       List resourceList = getRequiredResources(websphereReqXmlResources);
/* 116 */       resourceList.addAll(getOptionalResources(websphereXmlResources));
/* 117 */       resourceList.addAll(getRunningModeResources(runningModeXmlResources));
/* 118 */       resources = (Resource[])resourceList.toArray(resources);
     }
/* 120 */     return resources;
   }
 
   protected Resource[] findStandardResources(String extension) {
/* 124 */     Resource[] resources = findModeResources("classpath*:META-INF/config", extension);
/* 125 */     if (this.inWebConteiner) {
/* 126 */       Resource[] webResources = findModeResources("/WEB-INF/config", extension);
/* 127 */       resources = concat(resources, webResources);
     }
/* 129 */     return resources;
   }
 
   protected Resource[] findModeResources(String prefix, String extension) {
/* 133 */     Resource[] resources = findResources(prefix + "/*." + extension);
/* 134 */     Set<String> runningModes = RunningMode.get();
/* 135 */     for (String runningMode : runningModes) {
/* 136 */       Resource[] modeResources = findResources(prefix + "/" + runningMode + "/*." + extension);
/* 137 */       resources = concat(resources, modeResources);
     }
/* 139 */     return resources;
   }
 
   protected Resource[] findResources(String locationPattern) {
/* 143 */     ResourcePatternResolver resolver = getResolver();
     try {
/* 145 */       Resource[] resources = resolver.getResources(locationPattern);
/* 146 */       Arrays.sort(resources, new ResourceComparator());
/* 147 */       return resources;
     }
     catch (IOException e) {
/* 150 */       throw new BeanInitializationException("Error reading resources", e);
     }
   }
 
   protected ResourcePatternResolver getResolver() {
/* 155 */     if (this.inWebConteiner) {
       try {
/* 157 */         Constructor ctor = Class.forName("ServletContextResourcePatternResolver").getConstructor(new Class[0]);
/* 158 */         return ((ResourcePatternResolver)ctor.newInstance(new Object[] { this.resourceLoader }));
       }
       catch (Exception e)
       {
       }
     }
/* 164 */     return new PathMatchingResourcePatternResolver(this.resourceLoader);
   }
 
   protected Resource[] concat(Resource[] a1, Resource[] a2) {
/* 168 */     int l1 = a1.length;
/* 169 */     int l2 = a2.length;
 
/* 171 */     Resource[] r = new Resource[l1 + l2];
 
/* 173 */     System.arraycopy(a1, 0, r, 0, l1);
/* 174 */     System.arraycopy(a2, 0, r, l1, l2);
 
/* 176 */     return r;
   }
 
   protected Properties getFrameworkProperties() throws IOException {
/* 180 */     if (this.frameworkProperties == null) {
/* 181 */       this.frameworkProperties = new Properties();
/* 182 */       Resource resource = getResolver().getResource("META-INF/config/fw-resources.properties");
/* 183 */       if ((resource != null) && (resource.exists()))
/* 184 */         this.frameworkProperties.load(resource.getInputStream());
       else
/* 186 */         throw new IllegalStateException("META-INF/config/fw-resources.properties doesnot exists.");
     }
/* 188 */     return this.frameworkProperties;
   }
 
   protected List<String> parseResourceNames(String keyname) {
     try {
/* 193 */       List resourceList = new ArrayList();
/* 194 */       for (String fileName : getFrameworkProperties().getProperty(keyname).split(",")) {
/* 195 */         resourceList.add(fileName.trim());
       }
/* 197 */       return resourceList;
     }
     catch (IOException e) {
/* 200 */       throw new IllegalStateException(e);
     }
   }
 
   protected List<Resource> getRequiredResources(List<String> resourceNames) {
/* 205 */     List resourceList = new ArrayList();
/* 206 */     for (String resourceName : resourceNames) {
/* 207 */       resourceList.add(getResolver().getResource("META-INF/config/" + resourceName));
     }
/* 209 */     return resourceList;
   }
 
   protected List<Resource> getOptionalResources(List<String> resourceNames) {
/* 213 */     List resourceList = new ArrayList();
/* 214 */     for (String resourceName : resourceNames) {
/* 215 */       Resource resource = getResolver().getResource("META-INF/config/" + resourceName);
/* 216 */       if ((resource != null) && (resource.exists()))
/* 217 */         resourceList.add(resource);
     }
/* 219 */     return resourceList;
   }
 
   protected List<Resource> getRunningModeResources(List<String> resourceNames)
   {
     String runningMode;
/* 223 */     List resourceList = new ArrayList();
/* 224 */     Set runningModes = RunningMode.get();
/* 225 */     for (Iterator i$ = runningModes.iterator(); i$.hasNext(); ) { runningMode = (String)i$.next();
/* 226 */       for (String resourceName : resourceNames) {
/* 227 */         Resource resource = getResolver().getResource("META-INF/config/" + runningMode + "/" + resourceName);
 
/* 229 */         if ((resource != null) && (resource.exists()))
/* 230 */           resourceList.add(resource);
       }
     }
/* 233 */     return resourceList;
   }
 
   protected static final class ResourceComparator implements Comparator<Resource>
   {
     protected static final String WEB_INF_CLASSES_META_INF_CONFIG = "/WEB-INF/classes/META-INF/config/";
 
     public int compare(Resource r1, Resource r2) {
       try {
/* 242 */         String name1 = r1.getURL().toExternalForm().replace('\\', '/');
/* 243 */         String name2 = r2.getURL().toExternalForm().replace('\\', '/');
/* 244 */         boolean webInfClasses1 = name1.indexOf("/WEB-INF/classes/META-INF/config/") != -1;
/* 245 */         boolean webInfClasses2 = name2.indexOf("/WEB-INF/classes/META-INF/config/") != -1;
/* 246 */         if ((!(webInfClasses1)) && (webInfClasses2))
/* 247 */           return -1;
/* 248 */         if ((webInfClasses1) && (!(webInfClasses2))) {
/* 249 */           return 1;
         }
       }
       catch (IOException io)
       {
       }
/* 255 */       return r1.getFilename().compareTo(r2.getFilename());
     }
   }
 }