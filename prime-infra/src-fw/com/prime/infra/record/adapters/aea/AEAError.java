 package com.prime.infra.record.adapters.aea;
 
 public class AEAError
 {
   private String id;
   private String description;
 
   public String getId()
   {
/* 24 */     return this.id;
   }
 
   public void setId(String id) {
/* 28 */     this.id = id;
   }
 
   public String getDescription()
   {
/* 35 */     return this.description;
   }
 
   public void setDescription(String description) {
/* 39 */     this.description = description;
   }
 }