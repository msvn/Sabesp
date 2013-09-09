 package com.prime.infra.webservice;
 
 public class Paginator
 {
   private String maxResults;
   private String index;
   private String nextIndex;
 
   public String getMaxResults()
   {
/* 13 */     return this.maxResults;
   }
 
   public void setMaxResults(String maxResults)
   {
/* 18 */     this.maxResults = maxResults;
   }
 
   public String getIndex()
   {
/* 23 */     return this.index;
   }
 
   public void setIndex(String index)
   {
/* 28 */     this.index = index;
   }
 
   public String getNextIndex()
   {
/* 33 */     return this.nextIndex;
   }
 
   public void setNextIndex(String nextIndex)
   {
/* 38 */     this.nextIndex = nextIndex;
   }
 
   public String toString()
   {
/* 44 */     return super.toString() + "Paginator: index=" + getIndex() + ", maxResults=" + getMaxResults() + ", nextIndex=" + getNextIndex();
   }
 }