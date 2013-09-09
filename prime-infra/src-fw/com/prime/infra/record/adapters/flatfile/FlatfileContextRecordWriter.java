 package com.prime.infra.record.adapters.flatfile;
 
 import com.prime.infra.record.adapters.ContextSupportRecordWriter;
 import com.prime.infra.record.adapters.MarshallContext;
 import com.prime.infra.record.exception.MarshallException;
 import com.prime.infra.record.exception.WriterException;
 import java.io.IOException;
 import java.io.Writer;
 
 public class FlatfileContextRecordWriter<T>
   implements ContextSupportRecordWriter<T>
 {
   private FlatfileContextDataMarshaller<T> dataMarshaller;
   private Writer writer;
 
   public FlatfileContextRecordWriter()
   {
   }
 
   public FlatfileContextRecordWriter(FlatfileContextDataMarshaller<T> dataMarshaller)
   {
/* 20 */     this.dataMarshaller = dataMarshaller;
   }
 
   public void close() throws WriterException
   {
     try {
/* 26 */       this.writer.close();
     } catch (IOException e) {
/* 28 */       throw new WriterException(new MarshallException("Erro durante o flush", e));
     }
   }
 
   public void flush() throws WriterException
   {
     try {
/* 35 */       this.writer.flush();
     } catch (IOException e) {
/* 37 */       throw new WriterException(new MarshallException("Erro durante o flush", e));
     }
   }
 
   public void write(T objToWrite, Writer writer) throws WriterException
   {
/* 43 */     this.writer = writer;
/* 44 */     if (this.dataMarshaller == null) {
/* 45 */       throw new WriterException(new MarshallException("Use o metodo setMarshaller() para determinar qual adaptador deve ser usado para gerar os dados."));
     }
     try
     {
/* 49 */       writer.append(this.dataMarshaller.marshall(objToWrite));
     }
     catch (Exception e) {
/* 52 */       throw new WriterException("Erro", e);
     }
   }
 
   public void setDataMarshaller(FlatfileContextDataMarshaller<T> dataMarshaller) {
/* 57 */     this.dataMarshaller = dataMarshaller;
   }
 
   public void write(T objToWrite, Writer writer, MarshallContext marshallContext)
     throws WriterException
   {
/* 63 */     this.writer = writer;
/* 64 */     if (this.dataMarshaller == null) {
/* 65 */       throw new WriterException(new MarshallException("Use o metodo setMarshaller() para determinar qual adaptador deve ser usado para gerar os dados."));
     }
     try
     {
/* 69 */       writer.append(this.dataMarshaller.marshall(objToWrite, marshallContext));
     }
     catch (Exception e)
     {
/* 73 */       throw new WriterException("Erro", e);
     }
   }
 }