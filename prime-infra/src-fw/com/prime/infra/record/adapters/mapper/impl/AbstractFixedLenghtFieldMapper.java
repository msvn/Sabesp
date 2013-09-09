 package com.prime.infra.record.adapters.mapper.impl;
 
 import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.prime.infra.record.adapters.Mapper;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtField;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtFieldFile;
import com.prime.infra.record.exception.DataException;
 
 public abstract class AbstractFixedLenghtFieldMapper
   implements Mapper
 {
   protected static final String MISSING_MANDATORY_ANNOTATION = "The class provided [{0}] is missing the mandadotory annotation: {1}.";
   protected Class<?> clazz;
   protected int[] fieldSizes;
   protected String[] fieldNames;
   protected Map<String, Class<?>> conveters;
   protected Map<String, Class<?>> validators;
 
   public AbstractFixedLenghtFieldMapper(Class<?> clazz)
     throws DataException
   {
/* 28 */     this.clazz = clazz;
/* 29 */     checkMandatoryAnnotations(clazz);
/* 30 */     Field[] fields = this.clazz.getDeclaredFields();
/* 31 */     this.fieldSizes = new int[fields.length];
/* 32 */     this.fieldNames = new String[this.fieldSizes.length];
/* 33 */     this.conveters = new HashMap();
/* 34 */     this.validators = new HashMap();
/* 35 */     int count = 0;
/* 36 */     for (Field field : fields)
/* 37 */       if (field.isAnnotationPresent(FixedLenghtField.class)) {
/* 38 */         FixedLenghtField fixedLenghtField = (FixedLenghtField)field.getAnnotation(FixedLenghtField.class);
/* 39 */         this.fieldNames[count] = field.getName();
/* 40 */         this.fieldSizes[count] = fixedLenghtField.lenght();
/* 41 */         if (!(fixedLenghtField.converter().equals(Class.class))) {
/* 42 */           this.conveters.put(field.getName(), fixedLenghtField.converter());
         }
/* 44 */         if (!(fixedLenghtField.validator().equals(Class.class))) {
/* 45 */           this.validators.put(field.getName(), fixedLenghtField.validator());
         }
/* 47 */         ++count;
       }
   }
 
   public void checkMandatoryAnnotations(Class<?> clazz)
     throws DataException
   {
/* 54 */     FixedLenghtFieldFile annAEFixedLenghtFieldFile = (FixedLenghtFieldFile)clazz.getAnnotation(FixedLenghtFieldFile.class);
/* 55 */     if (annAEFixedLenghtFieldFile == null)
/* 56 */       throw new DataException(MessageFormat.format("The class provided [{0}] is missing the mandadotory annotation: {1}.", new Object[] { clazz.getSimpleName(), FixedLenghtFieldFile.class.getSimpleName() }));
   }
 }