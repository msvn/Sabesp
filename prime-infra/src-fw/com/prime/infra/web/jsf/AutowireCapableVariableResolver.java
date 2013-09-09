 package com.prime.infra.web.jsf;
 
 import javax.faces.context.FacesContext;
 import javax.faces.el.EvaluationException;
 import javax.faces.el.VariableResolver;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
 import org.springframework.web.context.WebApplicationContext;
 import org.springframework.web.jsf.FacesContextUtils;
 import org.springframework.web.jsf.SpringBeanVariableResolver;
 
 public class AutowireCapableVariableResolver extends SpringBeanVariableResolver
 {
/* 19 */   Logger logger = LoggerFactory.getLogger(AutowireCapableVariableResolver.class);
 
   public AutowireCapableVariableResolver(VariableResolver originalVariableResolver) {
/* 22 */     super(originalVariableResolver);
   }
 
   public Object resolveVariable(FacesContext facesContext, String name) throws EvaluationException
   {
/* 27 */     Object bean = super.resolveVariable(facesContext, name);
 
/* 29 */     if (bean instanceof BasicBBean) {
/* 30 */       BasicBBean bb = (BasicBBean)bean;
/* 31 */       if (bb.fromSerialization) {
/* 32 */         AutowireCapableBeanFactory factory = FacesContextUtils.getWebApplicationContext(facesContext).getAutowireCapableBeanFactory();
 
/* 34 */         factory.autowireBean(bean);
/* 35 */         factory.initializeBean(bean, bb.getBeanName());
/* 36 */         bb.fromSerialization = false;
       }
     }
 
/* 40 */     return bean;
   }
 }