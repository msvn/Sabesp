 package com.prime.infra.record.adapters.flatfile;
 
 import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import com.prime.infra.converter.Converter;
import com.prime.infra.record.adapters.DataMarshaller;
import com.prime.infra.record.adapters.Header;
import com.prime.infra.record.adapters.ObjectUtils;
import com.prime.infra.record.adapters.Trail;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtField;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtFieldDetailId;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtFieldFile;
import com.prime.infra.record.exception.ConverterException;
import com.prime.infra.record.exception.MarshallException;
 
 @Deprecated
 public class FixedLenghtMarshaller<T>
   implements DataMarshaller<T>
 {
/*  28 */   private static final String NEW_LINE = System.getProperty("line.separator");
   private static final String MISSING_MANDATORY_METHOD = "The given object [{0}], are missing the [ get{1}()] method.";
   private static final String MISSING_MANDATORY_ANNOTATION = "The given object type [{0}] are missing the mandatory annotation: {1}.";
   private FixedLenghtFieldFile annFixedLenghtFieldFile;
   private Class<T> clazz;
   private String id;
 
   public StringBuilder buildField(T obj, Field field)
     throws MarshallException
   {
/*  38 */     throw new MarshallException(new UnsupportedOperationException("This method are not available in this class."));
   }
 
   public StringBuilder buildDetail(T obj) throws MarshallException {
/*  42 */     StringBuilder detailBuilder = new StringBuilder();
/*  43 */     if (this.id != null) {
/*  44 */       detailBuilder.append(this.id);
     }
/*  46 */     Field[] fields = this.clazz.getDeclaredFields();
/*  47 */     String[] values = getValues(obj, detailBuilder, fields);
/*  48 */     for (String value : values) {
/*  49 */       if (value != null) {
/*  50 */         detailBuilder.append(value);
       }
     }
/*  53 */     return detailBuilder;
   }
 
   private String[] getValues(T obj, StringBuilder detailBuilder, Field[] fields) throws MarshallException {
/*  57 */     String[] values = new String[fields.length];
/*  58 */     for (Field field : fields) {
/*  59 */       if (field.getType().isPrimitive()) {
/*  60 */         throw new MarshallException("The attribute must be an object. Use a wrapper class: int -> Integer");
       }
/*  62 */       FixedLenghtField annAEFixedLenghtField = null;
       try {
/*  64 */         if (field.isAnnotationPresent(FixedLenghtField.class)) {
/*  65 */           Converter converter = null;
/*  66 */           annAEFixedLenghtField = (FixedLenghtField)field.getAnnotation(FixedLenghtField.class);
 
/*  68 */           if (!(annAEFixedLenghtField.converter().equals(Class.class))) {
/*  69 */             converter = (Converter)annAEFixedLenghtField.converter().newInstance();
           }
/*  71 */           if (values[(annAEFixedLenghtField.position() - 1)] == null) {
/*  72 */             if (annAEFixedLenghtField.paddingAlign() == 0) {
/*  73 */               values[(annAEFixedLenghtField.position() - 1)] = formatFieldLeft(obj, field, annAEFixedLenghtField, converter);
             }
             else
             {
/*  77 */               values[(annAEFixedLenghtField.position() - 1)] = formatFieldRight(obj, field, annAEFixedLenghtField, converter);
             }
 
           }
           else {
/*  82 */             throw new MarshallException("The field index [" + annAEFixedLenghtField.position() + "] are not unique in this record." + " Data: [" + values[(annAEFixedLenghtField.position() - 1)] + "], cloned index field: [" + obj.getClass().getSimpleName() + "." + field.getName() + "]");
           }
 
         }
 
       }
       catch (ConverterException e)
       {
/*  90 */         throw new MarshallException("The value from [" + field.getName() + "] field cannot be converted. Owner class: [" + this.clazz.getSimpleName() + "].", e);
       }
       catch (IndexOutOfBoundsException e)
       {
/*  94 */         throw new MarshallException("The index [" + annAEFixedLenghtField.position() + "] is invalid. Total fields in record: " + fields.length, e);
       }
       catch (RuntimeException e)
       {
/*  98 */         throw new MarshallException("Unexpected error creating output data.", e);
       }
       catch (NoSuchMethodException e) {
/* 101 */         throw new MarshallException(MessageFormat.format("The given object [{0}], are missing the [ get{1}()] method.", new Object[] { obj.getClass().getSimpleName(), field.getName() }), e);
       }
       catch (IllegalAccessException e)
       {
/* 105 */         throw new MarshallException("The [get" + field.getName() + "()] method must be public.", e);
       }
       catch (InvocationTargetException e) {
/* 108 */         throw new MarshallException("The [get" + field.getName() + "()] method call raises an exception.", e);
       }
       catch (InstantiationException e) {
/* 111 */         throw new MarshallException("Cannot instantiate an object of [" + annAEFixedLenghtField.converter().getSimpleName() + "] class.", e);
       }
     }
 
/* 115 */     return values;
   }
 
   private String formatFieldRight(T obj, Field field, FixedLenghtField annAEFixedLenghtField, Converter<?> converter)
     throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ConverterException
   {
/* 121 */     String result = "";
/* 122 */     Object value = ObjectUtils.invokeGetterMethod(field, obj);
/* 123 */     if (value != null)
     {
/* 125 */       if (converter != null) {
/* 126 */         value = converter.parseValue(value.toString());
       }
/* 128 */       result = StringUtils.rightPad(value.toString(), annAEFixedLenghtField.lenght(), annAEFixedLenghtField.paddingChar());
     }
 
/* 131 */     return result;
   }
 
   private String formatFieldLeft(T obj, Field field, FixedLenghtField annAEFixedLenghtField, Converter<?> converter)
     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SecurityException, IllegalArgumentException, ConverterException
   {
/* 137 */     String result = "";
/* 138 */     Object value = ObjectUtils.invokeGetterMethod(field, obj);
/* 139 */     if (value != null)
     {
/* 141 */       if (converter != null) {
/* 142 */         value = converter.parseValue(value.toString());
       }
/* 144 */       result = StringUtils.leftPad(value.toString(), annAEFixedLenghtField.lenght(), annAEFixedLenghtField.paddingChar());
     }
 
/* 147 */     return result;
   }
 
   public String marshall(Collection<T> collection)
     throws MarshallException
   {
/* 153 */     if ((collection == null) || (collection.isEmpty())) {
/* 154 */       throw new MarshallException();
     }
/* 156 */     Object check = collection.toArray()[0];
/* 157 */     this.clazz = checkMandatoryAnnotations(check);
/* 158 */     if (this.clazz.isAnnotationPresent(FixedLenghtFieldDetailId.class)) {
/* 159 */       this.id = ((FixedLenghtFieldDetailId)this.clazz.getAnnotation(FixedLenghtFieldDetailId.class)).id();
     }
 
/* 162 */     StringBuilder result = new StringBuilder();
/* 163 */     StringBuilder header = buildHeader();
/* 164 */     result.append((header.length() > 0) ? header.append(NEW_LINE) : "");
/* 165 */     for (Iterator i$ = collection.iterator(); i$.hasNext(); ) { T obj = (T)i$.next();
/* 166 */       result.append(buildDetail(obj));
     }
/* 168 */     if (!(result.toString().equals(""))) {
/* 169 */       StringBuilder trail = buildTrail();
/* 170 */       if (trail.length() > 0) {
/* 171 */         result.append(NEW_LINE);
/* 172 */         result.append(trail);
       }
     }
/* 175 */     return result.toString();
   }
 
   public String marshall(T obj) throws MarshallException {
/* 179 */     this.clazz = checkMandatoryAnnotations(obj);
/* 180 */     if (this.clazz.isAnnotationPresent(FixedLenghtFieldDetailId.class)) {
/* 181 */       this.id = ((FixedLenghtFieldDetailId)this.clazz.getAnnotation(FixedLenghtFieldDetailId.class)).id();
     }
/* 183 */     StringBuilder result = new StringBuilder();
/* 184 */     result.append(buildDetail(obj));
/* 185 */     return result.toString();
   }
 
   public StringBuilder buildHeader() throws MarshallException {
/* 189 */     StringBuilder headerBuilder = new StringBuilder();
/* 190 */     Class headerClass = this.annFixedLenghtFieldFile.header();
/* 191 */     if (headerClass.equals(Class.class)) {
/* 192 */       return headerBuilder;
     }
     try
     {
/* 196 */       Header header = (Header)headerClass.newInstance();
/* 197 */       headerBuilder = new StringBuilder();
/* 198 */       headerBuilder.append(header.changeValues(new Object[] { new Integer(1), new Integer(2), new Integer(3) }));
/* 199 */       return headerBuilder;
     }
     catch (InstantiationException e) {
/* 202 */       throw new MarshallException("Cannot instantiate an object of [" + headerClass.getSimpleName() + "] class.", e);
     }
     catch (IllegalAccessException e)
     {
/* 206 */       throw new MarshallException("The [" + headerClass.getSimpleName() + "()] method must be public.", e);
     }
   }
 
   public StringBuilder buildTrail() throws MarshallException
   {
/* 212 */     StringBuilder trailBuilder = new StringBuilder();
/* 213 */     Class trailClass = this.annFixedLenghtFieldFile.trail();
/* 214 */     if (trailClass.equals(Class.class)) {
/* 215 */       return trailBuilder;
     }
     try
     {
/* 219 */       Trail trail = (Trail)trailClass.newInstance();
/* 220 */       trailBuilder = new StringBuilder();
/* 221 */       trailBuilder.append(trail.changeValues(new Object[] { new Integer(1), new Integer(2), new Integer(3) }));
/* 222 */       return trailBuilder;
     }
     catch (InstantiationException e) {
/* 225 */       throw new MarshallException("Cannot instantiate an object of [" + trailClass.getSimpleName() + "] class.", e);
     }
     catch (IllegalAccessException e)
     {
/* 229 */       throw new MarshallException("The [" + trailClass.getSimpleName() + "()] method must be public.", e);
     }
   }
 
   private Class<T> checkMandatoryAnnotations(Object obj)
     throws MarshallException
   {
/* 237 */     Class clazz = obj.getClass();
 
/* 239 */     this.annFixedLenghtFieldFile = ((FixedLenghtFieldFile)clazz.getAnnotation(FixedLenghtFieldFile.class));
/* 240 */     if (this.annFixedLenghtFieldFile == null) {
/* 241 */       throw new MarshallException(MessageFormat.format("The given object type [{0}] are missing the mandatory annotation: {1}.", new Object[] { obj.getClass().getSimpleName(), FixedLenghtFieldFile.class.getSimpleName() }));
     }
 
/* 244 */     return clazz;
   }
 }