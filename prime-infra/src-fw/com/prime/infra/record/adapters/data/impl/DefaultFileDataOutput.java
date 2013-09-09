 package com.prime.infra.record.adapters.data.impl;
 
 import com.prime.infra.record.adapters.DataMarshaller;
 import com.prime.infra.record.adapters.DataOutput;
 import com.prime.infra.record.exception.DataException;
 import com.prime.infra.record.exception.MarshallException;
 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.PrintWriter;
 
 public class DefaultFileDataOutput<T>
   implements DataOutput<T>
 {
/* 24 */   private DefaultRecordWriter<T> defaultRecordWriter = new DefaultRecordWriter();
 
   public DefaultFileDataOutput(String fullFilenamePath, DataMarshaller<T> dataMarshaller) throws DataException {
     try {
/* 28 */       BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(new File(fullFilenamePath)));
/* 29 */       this.defaultRecordWriter.setOutput(bufferedWriter);
/* 30 */       this.defaultRecordWriter.setMarshaller(dataMarshaller);
     }
     catch (FileNotFoundException e) {
/* 33 */       throw new DataException("File [" + fullFilenamePath + "] not found.", e);
     }
   }
 
   public void flush()
     throws DataException
   {
     try
     {
/* 42 */       this.defaultRecordWriter.flush();
     }
     catch (MarshallException e) {
/* 45 */       throw new DataException(e);
     }
   }
 
   public void close()
     throws DataException
   {
     try
     {
/* 55 */       this.defaultRecordWriter.close();
     }
     catch (MarshallException e) {
/* 58 */       throw new DataException(e);
     }
   }
 
   public void save(T object)
     throws DataException
   {
     try
     {
/* 67 */       this.defaultRecordWriter.marshall(object);
     }
     catch (MarshallException e) {
/* 70 */       throw new DataException(e);
     }
   }
 }