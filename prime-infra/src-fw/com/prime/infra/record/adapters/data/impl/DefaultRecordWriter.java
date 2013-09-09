 package com.prime.infra.record.adapters.data.impl;
 
 import com.prime.infra.record.adapters.DataMarshaller;
 import com.prime.infra.record.adapters.RecordWriter;
 import com.prime.infra.record.exception.MarshallException;
 import java.io.IOException;
 import java.io.Writer;
 import java.util.Collection;
 
 public class DefaultRecordWriter<T>
   implements RecordWriter<T>
 {
   private DataMarshaller<T> dataMarshaller;
   private Writer output;
 
   public void setOutput(Writer output)
   {
/* 24 */     this.output = output;
   }
 
   public void setMarshaller(DataMarshaller<T> dataMarshaller)
   {
/* 31 */     this.dataMarshaller = dataMarshaller;
   }
 
   public void marshall(T toMarshall)
     throws MarshallException
   {
     String data;
/* 39 */     if (this.dataMarshaller == null) {
/* 40 */       throw new MarshallException("Use o metodo setMarshaller() para determinar qual adaptador deve ser usado para gerar os dados.");
     }
 
/* 44 */     if (toMarshall instanceof Collection) {
/* 45 */       data = this.dataMarshaller.marshall((Collection)toMarshall);
     }
     else
/* 48 */       data = this.dataMarshaller.marshall(toMarshall);
     try
     {
/* 51 */       this.output.append(data);
     }
     catch (IOException e) {
/* 54 */       throw new MarshallException("Erro", e);
     }
   }
 
   public void flush()
     throws MarshallException
   {
     try
     {
/* 63 */       this.output.flush();
     }
     catch (IOException e) {
/* 66 */       throw new MarshallException("Erro durante o flush", e);
     }
   }
 
   public void close()
     throws MarshallException
   {
     try
     {
/* 75 */       this.output.close();
     }
     catch (IOException e) {
/* 78 */       throw new MarshallException(e);
     }
   }
 }