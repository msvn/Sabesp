 package com.prime.infra.record.adapters.mapper.impl;
 
 import com.prime.infra.record.adapters.Mapper;
 import com.prime.infra.record.adapters.flatfile.annotation.DelimitedField;
 import com.prime.infra.record.adapters.flatfile.annotation.DelimitedFieldFile;
 import com.prime.infra.record.exception.DataException;
 import java.lang.reflect.Field;
 import java.text.MessageFormat;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 
 public abstract class AbstractDelimiterMapper
   implements Mapper
 {
   protected static final String MISSING_MANDATORY_ANNOTATION = "The class provided [{0}] is missing the mandadotory annotation: {1}.";
   protected Class<?> clazz;
   protected int[] fieldSizes;
   protected String[] fieldNames;
   protected Map<String, Class<?>> conveters;
   protected Map<String, Class<?>> validators;
   protected String delimiter;
 
   public AbstractDelimiterMapper(Class<?> clazz)
     throws DataException
   {
/* 30 */     this.clazz = clazz;
/* 31 */     checkMandatoryAnnotations(clazz);
/* 32 */     Field[] fields = this.clazz.getDeclaredFields();
/* 33 */     List fieldNamesTemp = new ArrayList();
/* 34 */     this.conveters = new HashMap();
/* 35 */     this.validators = new HashMap();
/* 36 */     int count = 0;
/* 37 */     for (Field field : fields) {
/* 38 */       if (field.isAnnotationPresent(DelimitedField.class)) {
/* 39 */         DelimitedField delimitedField = (DelimitedField)field.getAnnotation(DelimitedField.class);
/* 40 */         fieldNamesTemp.add(field.getName());
/* 41 */         if (!(delimitedField.converter().equals(Class.class))) {
/* 42 */           this.conveters.put(field.getName(), delimitedField.converter());
         }
/* 44 */         if (!(delimitedField.validator().equals(Class.class))) {
/* 45 */           this.validators.put(field.getName(), delimitedField.validator());
         }
/* 47 */         ++count;
       }
     }
 
/* 51 */     adjustArraySizes(count, fieldNamesTemp);
   }
 
   private void adjustArraySizes(int count, List<String> fieldNames) {
/* 55 */     this.fieldSizes = new int[count];
/* 56 */     this.fieldNames = new String[this.fieldSizes.length];
/* 57 */     this.fieldNames = ((String[])fieldNames.toArray(this.fieldNames));
   }
 
   public void checkMandatoryAnnotations(Class<?> clazz) throws DataException
   {
/* 62 */     DelimitedFieldFile annAEDelimitedFieldFile = (DelimitedFieldFile)clazz.getAnnotation(DelimitedFieldFile.class);
/* 63 */     if (annAEDelimitedFieldFile == null) {
/* 64 */       throw new DataException(MessageFormat.format("The class provided [{0}] is missing the mandadotory annotation: {1}.", new Object[] { clazz.getSimpleName(), DelimitedFieldFile.class.getSimpleName() }));
     }
 
/* 67 */     this.delimiter = annAEDelimitedFieldFile.delimiter();
   }
 }