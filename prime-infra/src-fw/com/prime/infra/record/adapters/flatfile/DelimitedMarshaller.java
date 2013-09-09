 package com.prime.infra.record.adapters.flatfile;
 
 import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;

import com.prime.infra.converter.Converter;
import com.prime.infra.record.adapters.DataMarshaller;
import com.prime.infra.record.adapters.ObjectUtils;
import com.prime.infra.record.adapters.flatfile.annotation.DelimitedField;
import com.prime.infra.record.adapters.flatfile.annotation.DelimitedFieldDetailId;
import com.prime.infra.record.adapters.flatfile.annotation.DelimitedFieldFile;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtFieldFile;
import com.prime.infra.record.exception.ConverterException;
import com.prime.infra.record.exception.MarshallException;
 
 public class DelimitedMarshaller<T>
   implements DataMarshaller<T>
 {
/*  27 */   private static final String NEW_LINE = System.getProperty("line.separator");
   private static final String MISSING_MANDATORY_METHOD = "O objeto de entrada [{0}], nao possui o metodo [ get{1}()] definido.";
   private static final String MISSING_MANDATORY_ANNOTATION = "A classe do objeto fornecido[{0}] nao contem a anotacao obrigatoria: {1}.";
   private DelimitedFieldFile annDelimitedFieldFile;
   private Class<T> clazz;
   private String id;
 
   public StringBuilder buildDetail(T obj)
     throws MarshallException
   {
/*  37 */     StringBuilder detailBuilder = new StringBuilder();
/*  38 */     if (this.id != null) {
/*  39 */       detailBuilder.append(this.id);
/*  40 */       detailBuilder.append(this.annDelimitedFieldFile.delimiter());
     }
/*  42 */     Field[] fields = this.clazz.getDeclaredFields();
/*  43 */     String[] values = getValues(obj, detailBuilder, fields);
/*  44 */     for (String value : values) {
/*  45 */       if (value != null) {
/*  46 */         detailBuilder.append(value);
/*  47 */         detailBuilder.append(this.annDelimitedFieldFile.delimiter());
       }
     }
/*  50 */     detailBuilder.deleteCharAt(detailBuilder.length() - 1);
/*  51 */     return detailBuilder;
   }
 
   public StringBuilder buildField(T obj, Field field) throws MarshallException {
/*  55 */     throw new MarshallException(new UnsupportedOperationException("Esse metodo nao esta disponi­vel para essa classe."));
   }
 
   public StringBuilder buildHeader() throws MarshallException
   {
/*  60 */     throw new MarshallException(new UnsupportedOperationException("Esse metodo nao esta disponi­vel para essa classe."));
   }
 
   public StringBuilder buildTrail() throws MarshallException
   {
/*  65 */     throw new MarshallException(new UnsupportedOperationException("Esse metodo nao esta disponi­vel para essa classe."));
   }
 
   public String marshall(T obj) throws MarshallException
   {
/*  70 */     StringBuilder result = new StringBuilder();
/*  71 */     result.append(buildDetail(obj));
/*  72 */     result.append(NEW_LINE);
/*  73 */     return result.toString();
   }
 
   public String marshall(Collection<T> collection)
     throws MarshallException
   {
/*  79 */     if ((collection == null) || (collection.isEmpty())) {
/*  80 */       throw new MarshallException();
     }
/*  82 */     Object check = collection.toArray()[0];
/*  83 */     this.clazz = checkMandatoryAnnotations(check);
/*  84 */     if (this.clazz.isAnnotationPresent(DelimitedFieldDetailId.class)) {
/*  85 */       this.id = ((DelimitedFieldDetailId)this.clazz.getAnnotation(DelimitedFieldDetailId.class)).id();
     }
 
/*  88 */     StringBuilder result = new StringBuilder();
/*  89 */     for (Iterator i$ = collection.iterator(); i$.hasNext(); ) { T obj = (T)i$.next();
/*  90 */       result.append(buildDetail(obj));
/*  91 */       result.append(NEW_LINE);
     }
/*  93 */     return result.toString();
   }
 
   private String[] getValues(T obj, StringBuilder detailBuilder, Field[] fields) throws MarshallException {
/*  97 */     String[] values = new String[fields.length];
/*  98 */     for (Field field : fields) {
/*  99 */       if (field.getType().isPrimitive()) {
/* 100 */         throw new MarshallException("O tipo do atributo deve ser um objeto. Use uma wrapper class. Ex.: int -> Integer");
       }
 
/* 103 */       DelimitedField annDelimitedField = null;
       try {
/* 105 */         if (field.isAnnotationPresent(DelimitedField.class)) {
/* 106 */           Converter converter = null;
/* 107 */           annDelimitedField = (DelimitedField)field.getAnnotation(DelimitedField.class);
 
/* 109 */           if (!(annDelimitedField.converter().equals(Class.class))) {
/* 110 */             converter = (Converter)annDelimitedField.converter().newInstance();
           }
/* 112 */           if (values[(annDelimitedField.position() - 1)] == null) {
/* 113 */             values[(annDelimitedField.position() - 1)] = value(obj, field, annDelimitedField, converter);
           }
           else {
/* 116 */             throw new MarshallException("Existe mais de um campo informando a mesma posicao [" + annDelimitedField.position() + "] no regitro. Dado atual: [" + values[(annDelimitedField.position() - 1)] + "], campo duplicado: [" + obj.getClass().getSimpleName() + "." + field.getName() + "]");
           }
 
         }
 
       }
       catch (IndexOutOfBoundsException e)
       {
/* 124 */         throw new MarshallException("A posicao [" + annDelimitedField.position() + "] informada para o campo no regitro nao e valida. Total de campos no registro: " + fields.length, e);
       }
       catch (ConverterException e)
       {
/* 130 */         throw new MarshallException("Erro convertendo o valor do atributo[" + field.getName() + "] da classe [" + this.clazz.getSimpleName() + "].", e);
       }
       catch (RuntimeException e)
       {
/* 134 */         throw new MarshallException("Erro inesperado tentando gerar os dados de saida.", e);
       }
       catch (NoSuchMethodException e) {
/* 137 */         throw new MarshallException(MessageFormat.format("O objeto de entrada [{0}], nao possui o metodo [ get{1}()] definido.", new Object[] { obj.getClass().getSimpleName(), field.getName() }), e);
       }
       catch (IllegalAccessException e)
       {
/* 141 */         throw new MarshallException("O metodo [get" + field.getName() + "()] deve ser public.", e);
       }
       catch (InvocationTargetException e) {
/* 144 */         throw new MarshallException("A chamada ao metodo [get" + field.getName() + "()] provocou uma excecao.", e);
       }
       catch (InstantiationException e)
       {
/* 148 */         throw new MarshallException("Erro criando instancia de um objeto a partir da classe [" + annDelimitedField.converter().getSimpleName() + "].", e);
       }
     }
 
/* 152 */     return values;
   }
 
   private String value(T obj, Field field, DelimitedField annDelimitedField, Converter<?> converter)
     throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ConverterException
   {
/* 158 */     String result = "";
/* 159 */     Object value = ObjectUtils.invokeGetterMethod(field, obj);
/* 160 */     if (value != null)
     {
/* 162 */       if (converter != null) {
/* 163 */         value = converter.formatValue(value);
       }
/* 165 */       result = value.toString();
     }
 
/* 168 */     return result;
   }
 
   private Class<T> checkMandatoryAnnotations(Object obj) throws MarshallException
   {
/* 173 */     Class clazz = obj.getClass();
 
/* 175 */     this.annDelimitedFieldFile = ((DelimitedFieldFile)clazz.getAnnotation(DelimitedFieldFile.class));
/* 176 */     if (this.annDelimitedFieldFile == null) {
/* 177 */       throw new MarshallException(MessageFormat.format("A classe do objeto fornecido[{0}] nao contem a anotacao obrigatoria: {1}.", new Object[] { obj.getClass().getSimpleName(), FixedLenghtFieldFile.class.getSimpleName() }));
     }
 
/* 180 */     return clazz;
   }
 }