 package com.prime.infra.record.adapters.mapper.impl;
 
 import com.prime.infra.converter.Converter;
 import com.prime.infra.record.exception.DataException;
 import com.prime.infra.validator.Validator;
 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.Reader;
import java.util.Map;
 
 public class DelimiterMapper extends AbstractDelimiterMapper
 {
/*  18 */   private String[] fields = null;
/*  19 */   private Integer recordController = null;
/*  20 */   BufferedReader bufferedReader = null;
/*  21 */   private Integer bufferSize = null;
 
   public DelimiterMapper(Class<?> clazz) throws DataException
   {
/*  25 */     super(clazz);
/*  26 */     this.fields = new String[this.fieldNames.length];
   }
 
   public int getFieldAmount() {
/*  30 */     return this.fields.length;
   }
 
   public String getFieldName(Integer fieldId) {
/*  34 */     return this.fieldNames[fieldId.intValue()];
   }
 
   public Map<String, Class<?>> getConverters() {
/*  38 */     return this.conveters;
   }
 
   public Map<String, Class<?>> getValidators() {
/*  42 */     return this.validators;
   }
 
   public Object getFieldValue(Integer fieldId) throws DataException {
     Object value;
     try {
/*  48 */       Converter converter = null;
/*  49 */       Validator validator = null;
/*  50 */       if (null == this.recordController) {
/*  51 */         readLine();
       }
/*  53 */       else if (this.recordController.equals(Integer.valueOf(this.fields.length - 1))) {
/*  54 */         readLine();
       }
/*  56 */       Class converterClass = (Class)getConverters().get(getFieldName(fieldId));
/*  57 */       if (null != converterClass) {
/*  58 */         converter = (Converter)converterClass.newInstance();
       }
/*  60 */       Class validatorClass = (Class)getValidators().get(getFieldName(fieldId));
/*  61 */       if (null != validatorClass) {
/*  62 */         validator = (Validator)validatorClass.newInstance();
       }
/*  64 */       this.recordController = fieldId;
/*  65 */       value = this.fields[fieldId.intValue()].trim();
/*  66 */       if (null != validator) {
/*  67 */         validator.validate(value);
       }
 
/*  70 */       if (null != converter) {
/*  71 */         value = converter.parseValue((String)value);
       }
     }
     catch (Exception e)
     {
/*  76 */       throw new DataException(e);
     }
/*  78 */     return value;
   }
 
   private void readLine() throws IOException {
/*  82 */     this.fields = this.bufferedReader.readLine().split(this.delimiter);
   }
 
   public Class<?> getPojoClass() {
/*  86 */     return this.clazz;
   }
 
   /** @deprecated */
   public int getRecordSize()
   {
/*  93 */     return -1;
   }
 
   public boolean ready() {
     try {
/*  98 */       return this.bufferedReader.ready();
     } catch (IOException e) {
     }
/* 101 */     return false;
   }
 
   public void close() throws DataException
   {
     try {
/* 107 */       this.bufferedReader.close();
     }
     catch (IOException e) {
/* 110 */       throw new DataException(e);
     }
   }
 
   public void setReader(Reader reader) {
/* 115 */     if (null == this.bufferSize) {
/* 116 */       this.bufferSize = Integer.valueOf(1024);
     }
/* 118 */     this.bufferedReader = new BufferedReader(reader, this.bufferSize.intValue());
   }
 
   public void setBufferSize(Integer size) {
/* 122 */     this.bufferSize = size;
   }
 }