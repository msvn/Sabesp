 package com.prime.infra.record.adapters.mapper.impl;
 
 import com.prime.infra.converter.Converter;
 import com.prime.infra.record.exception.DataException;
 import com.prime.infra.validator.Validator;
 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.Reader;
 import java.util.Map;
 
 public class FixedLenghtFieldMapper extends AbstractFixedLenghtFieldMapper
 {
/*  18 */   private Reader reader = null;
 
   public FixedLenghtFieldMapper(Class<?> clazz) throws DataException {
/*  21 */     super(clazz);
   }
 
   public int getFieldAmount() {
/*  25 */     return this.fieldSizes.length;
   }
 
   public int getRecordSize() {
/*  29 */     int size = 0;
/*  30 */     int[] sizes = this.fieldSizes;
/*  31 */     for (int s : sizes) {
/*  32 */       size += s;
     }
/*  34 */     return size;
   }
 
   public boolean skipLineFeed() {
/*  38 */     return true;
   }
 
   public Class<?> getPojoClass() {
/*  42 */     return this.clazz;
   }
 
   public String[] getFieldNames() {
/*  46 */     return this.fieldNames;
   }
 
   public Map<String, Class<?>> getConverters() {
/*  50 */     return this.conveters;
   }
 
   public Map<String, Class<?>> getValidators() {
/*  54 */     return this.validators;
   }
 
   public String getFieldName(Integer fieldId) {
/*  58 */     return this.fieldNames[fieldId.intValue()];
   }
 
   public Object getFieldValue(Integer fieldId)
     throws DataException
   {
     Object value;
/*  62 */     char[] buffer = new char[this.fieldSizes[fieldId.intValue()]];
     try
     {
/*  65 */       for (int j = 0; j < this.fieldSizes[fieldId.intValue()]; ++j) {
/*  66 */         char read = (char)readChar(this.reader);
/*  67 */         if (read != '\uFFFF') {
/*  68 */           buffer[j] = read;
         }
       }
 
/*  72 */       Validator validator = null;
/*  73 */       Class validatorClass = (Class)getValidators().get(getFieldName(fieldId));
/*  74 */       if (null != validatorClass) {
/*  75 */         validator = (Validator)validatorClass.newInstance();
       }
 
/*  78 */       Class converterClass = (Class)getConverters().get(getFieldName(fieldId));
/*  79 */       Converter converter = null;
/*  80 */       if (null != converterClass) {
/*  81 */         converter = (Converter)converterClass.newInstance();
       }
 
/*  84 */       value = new String(buffer).trim();
 
/*  86 */       if (null != validator) {
/*  87 */         validator.validate(value);
       }
 
/*  90 */       if (null != converter)
/*  91 */         value = converter.parseValue((String)value);
     }
     catch (Exception e)
     {
/*  95 */       throw new DataException(e);
     }
/*  97 */     return value;
   }
 
   private int readChar(Reader reader)
     throws IOException
   {
     int read;
/* 103 */     if (!(skipLineFeed())) {
/* 104 */       read = reader.read();
/* 105 */       return read;
     }
/* 107 */     while (!(isValid(read = reader.read())));
/* 109 */     return read;
   }
 
   private boolean isValid(int read) {
/* 113 */     return ((read != 13) && (read != 10));
   }
 
   public boolean ready() {
     try {
/* 118 */       return this.reader.ready();
     } catch (IOException e) {
     }
/* 121 */     return false;
   }
 
   public void close() throws DataException
   {
     try {
/* 127 */       this.reader.close();
     }
     catch (IOException e) {
/* 130 */       throw new DataException(e);
     }
   }
 
   public void setReader(Reader reader) {
/* 135 */     this.reader = reader;
   }
 
   public void setBufferSize(Integer size)
   {
/* 144 */     this.reader = new BufferedReader(this.reader, size.intValue());
   }
 }