 package com.prime.infra.record.adapters.data.impl;
 
 import com.prime.infra.record.adapters.DataInput;
 import com.prime.infra.record.adapters.Mapper;
 import com.prime.infra.record.exception.DataException;
 import java.io.IOException;
 import java.io.Reader;
 import java.util.ArrayList;
 import java.util.Collection;
 import org.apache.commons.beanutils.BeanUtils;
 
 public class DefaultDataInput
   implements DataInput
 {
   private static final int READ_AHEAD_MARK = 1;
   private Reader reader;
   private Mapper mapper;
   private Collection<String> errorMessages;
 
   public DefaultDataInput()
   {
/*  27 */     this.errorMessages = new ArrayList();
   }
 
   public boolean hasNext()
     throws DataException
   {
/*  34 */     return this.mapper.ready();
   }
 
   public Object read()
     throws DataException
   {
/*  42 */     Object result = null;
/*  43 */     String field = null;
     try
     {
/*  46 */       int fieldAmount = this.mapper.getFieldAmount();
 
/*  48 */       result = this.mapper.getPojoClass().newInstance();
 
/*  50 */       for (int i = 0; i < fieldAmount; ++i) {
/*  51 */         field = this.mapper.getFieldName(Integer.valueOf(i));
/*  52 */         BeanUtils.copyProperty(result, field, this.mapper.getFieldValue(Integer.valueOf(i)));
       }
     }
     catch (IllegalArgumentException e) {
/*  56 */       this.errorMessages.add("Wrong type for field [" + ((null != field) ? field : "none") + "]. Converter needed.");
     }
     catch (Exception e)
     {
/*  60 */       throw new DataException(e);
     }
/*  62 */     return result;
   }
 
   public Reader getReader()
   {
/*  67 */     return this.reader;
   }
 
   public void setReader(Reader reader)
     throws DataException
   {
/*  75 */     this.reader = reader;
     try {
/*  77 */       if (reader.markSupported())
/*  78 */         reader.mark(1);
     }
     catch (IOException e)
     {
/*  82 */       throw new DataException(e);
     }
/*  84 */     if (null != this.mapper)
/*  85 */       this.mapper.setReader(this.reader);
   }
 
   public Mapper getMapper()
   {
/*  90 */     return this.mapper;
   }
 
   public void setMapper(Mapper mapper)
   {
/*  97 */     this.mapper = mapper;
/*  98 */     if (null != this.reader)
/*  99 */       this.mapper.setReader(this.reader);
   }
 
   public Collection<String> getErrors()
   {
/* 107 */     return this.errorMessages;
   }
 
   public void close()
     throws DataException
   {
     try
     {
/* 115 */       this.reader.close();
     }
     catch (IOException e) {
/* 118 */       throw new DataException(e);
     }
   }
 
   public void reset()
     throws DataException
   {
     try
     {
/* 127 */       this.reader.reset();
     }
     catch (IOException e) {
/* 130 */       throw new DataException(e);
     }
   }
 }