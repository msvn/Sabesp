 package com.prime.infra.web.jsf.component;
 
 import java.io.Serializable;
 import javax.faces.model.SelectItem;
 
 public class CustomSelectItem extends SelectItem
   implements Serializable
 {
   public CustomSelectItem(String label, Object value)
   {
/* 13 */     super(value, label);
   }
 }