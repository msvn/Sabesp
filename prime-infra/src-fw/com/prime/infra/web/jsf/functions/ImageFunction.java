 package com.prime.infra.web.jsf.functions;
 
 import java.io.Serializable;
 import javax.faces.context.ExternalContext;
 import javax.faces.context.FacesContext;
 
 public class ImageFunction
   implements Serializable
 {
   public static String geImagetUrl(String compName, Serializable id)
   {
/* 15 */     if (compName == null) {
/* 16 */       throw new NullPointerException("ComponentName cannot be null.");
     }
/* 18 */     if (id == null) {
/* 19 */       throw new NullPointerException("Id cannot be null");
     }
/* 21 */     return appendBuilder(new String[] { getContextUrl(), "/img.jpg?", "im", "=", compName, "&", "uniqueId", "=", id.toString() });
   }
 
   protected static String appendBuilder(String[] params)
   {
/* 26 */     StringBuilder builder = new StringBuilder();
/* 27 */     for (String param : params) {
/* 28 */       builder.append(param);
     }
/* 30 */     return builder.toString();
   }
 
   protected static String getContextUrl() {
/* 34 */     return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
   }
 }