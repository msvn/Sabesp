 package com.prime.infra.web.jsf.component;
 
 import com.prime.infra.report.ExportOption;
 import java.io.Serializable;
 
 public class ExportOptionSelectItem extends CustomSelectItem
   implements Serializable
 {
   public ExportOptionSelectItem(ExportOption exportOption)
   {
/* 14 */     super(exportOption.name(), exportOption);
   }
 }