 package com.prime.infra.record.adapters.flatfile;
 
 import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import com.prime.infra.converter.Converter;
import com.prime.infra.record.adapters.ContextSupportHeader;
import com.prime.infra.record.adapters.ContextSupportTrail;
import com.prime.infra.record.adapters.DataHolderMarshallContext;
import com.prime.infra.record.adapters.Header;
import com.prime.infra.record.adapters.MarshallContext;
import com.prime.infra.record.adapters.ObjectUtils;
import com.prime.infra.record.adapters.Trail;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtField;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtFieldDetailId;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtFieldFile;
import com.prime.infra.record.exception.ConverterException;
import com.prime.infra.record.exception.MarshallException;
 
 public class FixedLengthFlatfileDataMarshaller<T>
   implements FlatfileContextDataMarshaller<T>
 {
/*  33 */   private static final String NEW_LINE = System.getProperty("line.separator");
   private static final String MISSING_MANDATORY_METHOD = "The given object [{0}], are missing the [ get{1}()] method.";
   private static final String MISSING_MANDATORY_ANNOTATION = "The given object type [{0}] are missing the mandatory annotation: {1}.";
   private FixedLenghtFieldFile annFixedLenghtFieldFile;
   private Class<T> clazz;
   private String id;
 
   public String marshall(T obj)
     throws MarshallException
   {
/*  44 */     return marshall(obj, new DataHolderMarshallContext(obj));
   }
 
   public String marshall(T obj, MarshallContext context) throws MarshallException {
/*  48 */     if (!(Collection.class.isAssignableFrom(obj.getClass()))) {
/*  49 */       this.clazz = checkMandatoryAnnotations(obj);
/*  50 */       if (this.clazz.isAnnotationPresent(FixedLenghtFieldDetailId.class)) {
/*  51 */         this.id = ((FixedLenghtFieldDetailId)this.clazz.getAnnotation(FixedLenghtFieldDetailId.class)).id();
       }
/*  53 */       StringBuilder result = new StringBuilder();
/*  54 */       result.append(buildDetail(obj));
/*  55 */       return result.toString();
     }
 
/*  58 */     Collection collection = (Collection)obj;
/*  59 */     if ((collection == null) || (collection.isEmpty())) {
/*  60 */       throw new MarshallException();
     }
/*  62 */     Object check = collection.toArray()[0];
/*  63 */     this.clazz = checkMandatoryAnnotations(check);
/*  64 */     if (this.clazz.isAnnotationPresent(FixedLenghtFieldDetailId.class)) {
/*  65 */       this.id = ((FixedLenghtFieldDetailId)this.clazz.getAnnotation(FixedLenghtFieldDetailId.class)).id();
     }
 
/*  68 */     StringBuilder result = new StringBuilder();
/*  69 */     StringBuilder header = buildHeader(context);
/*  70 */     result.append((header.length() > 0) ? header.append(NEW_LINE) : "");
/*  71 */     for (Iterator i$ = collection.iterator(); i$.hasNext(); ) { Object singleObj = i$.next();
/*  72 */       result.append(buildDetail(singleObj));
     }
/*  74 */     if (!(result.toString().equals(""))) {
/*  75 */       StringBuilder trail = buildTrail(context);
/*  76 */       if (trail.length() > 0) {
/*  77 */         result.append(trail);
       }
     }
/*  80 */     return result.toString();
   }
 
   public StringBuilder buildField(Object obj, Field field) throws MarshallException
   {
/*  85 */     throw new MarshallException(new UnsupportedOperationException("This method are not available in this class."));
   }
 
   public StringBuilder buildDetail(Object obj) throws MarshallException {
/*  89 */     StringBuilder detailBuilder = new StringBuilder();
/*  90 */     if (this.id != null) {
/*  91 */       detailBuilder.append(this.id);
     }
/*  93 */     Field[] fields = this.clazz.getDeclaredFields();
/*  94 */     String[] values = getValues(obj, detailBuilder, fields);
/*  95 */     for (String value : values) {
/*  96 */       if (value != null) {
/*  97 */         detailBuilder.append(value);
       }
     }
/* 100 */     detailBuilder.append(NEW_LINE);
/* 101 */     return detailBuilder;
   }
 
   private String[] getValues(Object obj, StringBuilder detailBuilder, Field[] fields) throws MarshallException {
/* 105 */     String[] values = new String[fields.length];
/* 106 */     for (Field field : fields) {
/* 107 */       if (field.getType().isPrimitive()) {
/* 108 */         throw new MarshallException("The attribute must be an object. Use a wrapper class: int -> Integer");
       }
/* 110 */       FixedLenghtField annAEFixedLenghtField = null;
       try {
/* 112 */         if (field.isAnnotationPresent(FixedLenghtField.class)) {
/* 113 */           Converter converter = null;
/* 114 */           annAEFixedLenghtField = (FixedLenghtField)field.getAnnotation(FixedLenghtField.class);
 
/* 116 */           if (!(annAEFixedLenghtField.converter().equals(Class.class))) {
/* 117 */             converter = (Converter)annAEFixedLenghtField.converter().newInstance();
           }
/* 119 */           if (values[(annAEFixedLenghtField.position() - 1)] == null) {
/* 120 */             if (annAEFixedLenghtField.paddingAlign() == 0) {
/* 121 */               values[(annAEFixedLenghtField.position() - 1)] = formatFieldLeft(obj, field, annAEFixedLenghtField, converter);
             }
             else
             {
/* 125 */               values[(annAEFixedLenghtField.position() - 1)] = formatFieldRight(obj, field, annAEFixedLenghtField, converter);
             }
 
           }
           else {
/* 130 */             throw new MarshallException("The field index [" + annAEFixedLenghtField.position() + "] are not unique in this record." + " Data: [" + values[(annAEFixedLenghtField.position() - 1)] + "], cloned index field: [" + obj.getClass().getSimpleName() + "." + field.getName() + "]");
           }
 
         }
 
       }
       catch (ConverterException e)
       {
/* 138 */         throw new MarshallException("The value from [" + field.getName() + "] field cannot be converted. Owner class: [" + this.clazz.getSimpleName() + "].", e);
       }
       catch (IndexOutOfBoundsException e)
       {
/* 142 */         throw new MarshallException("The index [" + annAEFixedLenghtField.position() + "] is invalid. Total fields in record: " + fields.length, e);
       }
       catch (RuntimeException e)
       {
/* 146 */         throw new MarshallException("Unexpected error creating output data.", e);
       }
       catch (NoSuchMethodException e) {
/* 149 */         throw new MarshallException(MessageFormat.format("The given object [{0}], are missing the [ get{1}()] method.", new Object[] { obj.getClass().getSimpleName(), field.getName() }), e);
       }
       catch (IllegalAccessException e)
       {
/* 153 */         throw new MarshallException("The [get" + field.getName() + "()] method must be public.", e);
       }
       catch (InvocationTargetException e) {
/* 156 */         throw new MarshallException("The [get" + field.getName() + "()] method call raises an exception.", e);
       }
       catch (InstantiationException e) {
/* 159 */         throw new MarshallException("Cannot instantiate an object of [" + annAEFixedLenghtField.converter().getSimpleName() + "] class.", e);
       }
     }
 
/* 163 */     return values;
   }
 
   private String formatFieldRight(Object obj, Field field, FixedLenghtField annAEFixedLenghtField, Converter<?> converter)
     throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ConverterException
   {
/* 169 */     String result = "";
/* 170 */     Object value = ObjectUtils.invokeGetterMethod(field, obj);
/* 171 */     if (value != null)
     {
/* 173 */       if (converter != null) {
/* 174 */         value = converter.parseValue(value.toString());
       }
/* 176 */       result = StringUtils.rightPad(value.toString(), annAEFixedLenghtField.lenght(), annAEFixedLenghtField.paddingChar());
     }
 
/* 179 */     return result;
   }
 
   private String formatFieldLeft(Object obj, Field field, FixedLenghtField annAEFixedLenghtField, Converter<?> converter)
     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SecurityException, IllegalArgumentException, ConverterException
   {
/* 185 */     String result = "";
/* 186 */     Object value = ObjectUtils.invokeGetterMethod(field, obj);
/* 187 */     if (value != null)
     {
/* 189 */       if (converter != null) {
/* 190 */         value = converter.parseValue(value.toString());
       }
/* 192 */       result = StringUtils.leftPad(value.toString(), annAEFixedLenghtField.lenght(), annAEFixedLenghtField.paddingChar());
     }
 
/* 195 */     return result;
   }
 
   public StringBuilder buildHeader(MarshallContext marshallContext) throws MarshallException {
/* 199 */     StringBuilder headerBuilder = new StringBuilder();
/* 200 */     Class headerClass = this.annFixedLenghtFieldFile.header();
/* 201 */     if (headerClass.equals(Class.class)) {
/* 202 */       return headerBuilder;
     }
     try
     {
/* 206 */       headerBuilder = new StringBuilder();
/* 207 */       Object header = headerClass.newInstance();
/* 208 */       if (ContextSupportHeader.class.isAssignableFrom(header.getClass()))
/* 209 */         headerBuilder.append(((ContextSupportHeader)header).processHeader(marshallContext));
       else {
/* 211 */         headerBuilder.append(((Header)header).changeValues(new Object[] { new Integer(1), new Integer(2), new Integer(3) }));
       }
/* 213 */       return headerBuilder;
     }
     catch (InstantiationException e) {
/* 216 */       throw new MarshallException("Cannot instantiate an object of [" + headerClass.getSimpleName() + "] class.", e);
     }
     catch (IllegalAccessException e)
     {
/* 220 */       throw new MarshallException("The [" + headerClass.getSimpleName() + "()] method must be public.", e);
     }
   }
 
   public StringBuilder buildTrail(MarshallContext marshallContext) throws MarshallException
   {
/* 226 */     StringBuilder trailBuilder = new StringBuilder();
/* 227 */     Class trailClass = this.annFixedLenghtFieldFile.trail();
/* 228 */     if (trailClass.equals(Class.class)) {
/* 229 */       return trailBuilder;
     }
     try
     {
/* 233 */       trailBuilder = new StringBuilder();
/* 234 */       Object trail = trailClass.newInstance();
/* 235 */       if (ContextSupportTrail.class.isAssignableFrom(trail.getClass()))
/* 236 */         trailBuilder.append(((ContextSupportTrail)trail).processTrail(marshallContext));
       else {
/* 238 */         trailBuilder.append(((Trail)trail).changeValues(new Object[] { new Integer(1), new Integer(2), new Integer(3) }));
       }
/* 240 */       return trailBuilder;
     }
     catch (InstantiationException e) {
/* 243 */       throw new MarshallException("Cannot instantiate an object of [" + trailClass.getSimpleName() + "] class.", e);
     }
     catch (IllegalAccessException e)
     {
/* 247 */       throw new MarshallException("The [" + trailClass.getSimpleName() + "()] method must be public.", e);
     }
   }
 
   private Class<T> checkMandatoryAnnotations(Object obj)
     throws MarshallException
   {
/* 255 */     Class clazz = obj.getClass();
 
/* 257 */     this.annFixedLenghtFieldFile = ((FixedLenghtFieldFile)clazz.getAnnotation(FixedLenghtFieldFile.class));
/* 258 */     if (this.annFixedLenghtFieldFile == null) {
/* 259 */       throw new MarshallException(MessageFormat.format("The given object type [{0}] are missing the mandatory annotation: {1}.", new Object[] { obj.getClass().getSimpleName(), FixedLenghtFieldFile.class.getSimpleName() }));
     }
 
/* 262 */     return clazz;
   }
 }