 package com.prime.infra.record.adapters;
 
 import java.util.Collection;
 import java.util.Collections;
 
 public class DataHolderMarshallContext<T>
   implements MarshallContext
 {
   private T data;
 
   public DataHolderMarshallContext(T data)
   {
/* 14 */     this.data = data;
   }
 
   public T getData()
   {
/* 19 */     if (Collection.class.isAssignableFrom(this.data.getClass())) {
/* 20 */       return (T) Collections.unmodifiableCollection((Collection)this.data);
     }
/* 22 */     return this.data;
   }
 }