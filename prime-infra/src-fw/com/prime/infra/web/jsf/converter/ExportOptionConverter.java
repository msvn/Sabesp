 package com.prime.infra.web.jsf.converter;
 
 import com.prime.infra.report.ExportOption;
 import javax.faces.component.UIComponent;
 import javax.faces.context.FacesContext;
 import javax.faces.convert.Converter;
 
 public class ExportOptionConverter
   implements Converter
 {
   public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
   {
/* 15 */     if (arg2 != null) {
/* 16 */       return ExportOption.valueOf(arg2);
     }
/* 18 */     return null;
   }
 
   public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
/* 22 */     return arg2.toString();
   }
 }