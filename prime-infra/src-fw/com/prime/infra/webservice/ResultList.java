 package com.prime.infra.webservice;
 
 import org.apache.commons.lang.builder.ToStringBuilder;
 
 public abstract class ResultList
 {
   private String OESC_001;
   private Paginator paginator;
 
   public final Paginator getPaginator()
   {
/* 14 */     return this.paginator;
   }
 
   public final void setPaginator(Paginator paginator) {
/* 18 */     this.paginator = paginator;
   }
 
   public final String getOESC_001() {
/* 22 */     return this.OESC_001;
   }
 
   public final void setOESC_001(String oesc_001) {
/* 26 */     this.OESC_001 = oesc_001;
   }
 
   public String toString()
   {
/* 31 */     return ToStringBuilder.reflectionToString(this);
   }
 }