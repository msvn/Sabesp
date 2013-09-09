 package com.prime.infra.record.adapters.flatfile;
 
 import com.prime.infra.record.adapters.ContextDataOutput;
 import com.prime.infra.record.adapters.ContextSupportRecordWriter;
 import com.prime.infra.record.adapters.MarshallContext;
 import com.prime.infra.record.exception.DataException;
 import com.prime.infra.record.exception.WriterException;
 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.io.PrintWriter;
 
 public class FlatfileContextDataOutput<T>
   implements ContextDataOutput<T>
 {
   public ContextSupportRecordWriter<T> defaultRecordWriter;
   private String fullFilenamePath;
 
   public FlatfileContextDataOutput(String fullFilenamePath)
   {
/* 21 */     this.fullFilenamePath = fullFilenamePath;
   }
 
   public void setFlatfileDataMarshaller(FlatfileContextDataMarshaller<T> dataMarshaller) {
/* 25 */     this.defaultRecordWriter = new FlatfileContextRecordWriter(dataMarshaller);
   }
 
   public void save(T object, MarshallContext marshallContext) throws DataException
   {
     try {
       try {
         try {
/* 33 */           File file = new File(this.fullFilenamePath);
/* 34 */           if (!(file.exists())) {
/* 35 */             file.createNewFile();
           }
/* 37 */           BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(file));
 
/* 39 */           this.defaultRecordWriter.write(object, bufferedWriter, marshallContext);
/* 40 */           this.defaultRecordWriter.flush();
         } finally {
/* 42 */           this.defaultRecordWriter.close();
         }
       }
       catch (FileNotFoundException e) {
/* 46 */         throw new DataException("File [" + this.fullFilenamePath + "] not found.", e);
       }
     } catch (IOException e) {
/* 49 */       throw new DataException(e);
     }
     catch (WriterException e) {
/* 52 */       throw new DataException(e);
     }
   }
 
   public void close() throws DataException
   {
     try {
/* 59 */       this.defaultRecordWriter.close();
     } catch (WriterException e) {
/* 61 */       throw new DataException(e);
     }
   }
 
   public void flush() throws DataException
   {
     try {
/* 68 */       this.defaultRecordWriter.flush();
     } catch (WriterException e) {
/* 70 */       throw new DataException(e);
     }
   }
 
   public void save(T object) throws DataException {
     try {
       try {
         try {
/* 78 */           BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(new File(this.fullFilenamePath)));
 
/* 80 */           this.defaultRecordWriter.write(object, bufferedWriter);
/* 81 */           this.defaultRecordWriter.flush();
         } finally {
/* 83 */           this.defaultRecordWriter.close();
         }
       }
       catch (FileNotFoundException e) {
/* 87 */         throw new DataException("File [" + this.fullFilenamePath + "] not found.", e);
       }
     } catch (WriterException e) {
/* 90 */       throw new DataException(e);
     }
   }
 }