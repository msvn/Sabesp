 package com.prime.infra.record.adapters.aea;
 
 import com.prime.infra.converter.Converter;
 import com.prime.infra.record.adapters.DataMarshaller;
 import com.prime.infra.record.adapters.ObjectUtils;
 import com.prime.infra.record.adapters.aea.annotation.AeaField;
 import com.prime.infra.record.adapters.aea.annotation.AeaRequestMessage;
 import com.prime.infra.record.exception.ConverterException;
 import com.prime.infra.record.exception.MarshallException;
 import java.lang.annotation.Annotation;
 import java.lang.reflect.Field;
 import java.lang.reflect.InvocationTargetException;
 import java.text.MessageFormat;
 import java.util.Collection;
 
 public class AEARequestMarshaller<T>
   implements DataMarshaller<T>
 {
/*  25 */   private static final String NEW_LINE = System.getProperty("line.separator");
   private static final String MISSING_MANDATORY_ANNOTATION = "The provided class [{0}] are missing the mandatory annotation: {1}.";
   private static final String TAB = "\t";
   private static final String LEVEL1 = "\t";
   private static final String LEVEL2 = "\t\t";
   private static final String LEVEL3 = "\t\t\t";
   private static final String LEVEL4 = "\t\t\t\t";
   private static final String ROOT = "requestMsg";
   private static final String OPERATION_NAME_TAG = "dse_operationName";
   private static final String FORMATTED_DATA_TAG = "dse_formattedData";
   private static final String DATA_TYPE_TAG = "kColl";
   private static final String DATA_TYPE_ID = "entryData";
   private static final String PROCESS_PARAMETER_TAG = "dse_processParameters";
   private static final String TRANSACTION_USER_NAME_ID = "Usuario";
   private AeaRequestMessage annAEARequestMessage;
   private String userName;
   private String operationName;
 
   public String getUserName()
   {
/*  51 */     return this.userName;
   }
 
   public void setUserName(String userName) {
/*  55 */     this.userName = userName;
   }
 
   public String getOperationName() {
/*  59 */     return this.operationName;
   }
 
   public void setOperationName(String operationName) {
/*  63 */     this.operationName = operationName;
   }
 
   public String marshall(Collection<T> collection) throws MarshallException {
/*  67 */     throw new MarshallException(new UnsupportedOperationException("This method is unavailable for this class."));
   }
 
   public String marshall(T obj) throws MarshallException {
/*  71 */     Class clazz = checkMandatoryAnnotations(obj);
/*  72 */     StringBuilder requestBuilder = new StringBuilder();
 
/*  74 */     requestBuilder.append(buildHeader());
 
/*  77 */     Field[] fields = clazz.getDeclaredFields();
/*  78 */     for (Field field : fields) {
/*  79 */       if (field.isAnnotationPresent(AeaField.class)) {
/*  80 */         requestBuilder.append(buildField(obj, field));
       }
     }
 
/*  84 */     requestBuilder.append(buildTrail());
/*  85 */     return requestBuilder.toString();
   }
 
   public StringBuilder buildHeader() throws MarshallException
   {
/*  90 */     StringBuilder headerBuilder = new StringBuilder("<?xml version=\"1.0\"?>" + NEW_LINE);
 
/*  92 */     headerBuilder.append("<");
/*  93 */     headerBuilder.append("requestMsg");
/*  94 */     headerBuilder.append(">");
/*  95 */     headerBuilder.append(NEW_LINE);
 
/*  97 */     headerBuilder.append("\t");
/*  98 */     headerBuilder.append("<");
/*  99 */     headerBuilder.append("dse_operationName");
/* 100 */     headerBuilder.append(">");
 
/* 102 */     if ((null != this.annAEARequestMessage) && 
/* 103 */       (!(this.annAEARequestMessage.operationName().equals("")))) {
/* 104 */       this.operationName = this.annAEARequestMessage.operationName();
     }
 
/* 108 */     if (this.operationName == null) {
/* 109 */       throw new MarshallException("This transaction needs a operation name. Please provide one using AEARequestMessage annotation or set the name using the setOperationName() method.");
     }
 
/* 114 */     headerBuilder.append(this.operationName);
 
/* 116 */     headerBuilder.append("</");
/* 117 */     headerBuilder.append("dse_operationName");
/* 118 */     headerBuilder.append(">");
/* 119 */     headerBuilder.append(NEW_LINE);
 
/* 121 */     headerBuilder.append("\t\t");
/* 122 */     headerBuilder.append("<");
/* 123 */     headerBuilder.append("dse_formattedData");
/* 124 */     headerBuilder.append(">");
/* 125 */     headerBuilder.append(NEW_LINE);
 
/* 127 */     headerBuilder.append("\t\t\t");
/* 128 */     headerBuilder.append("<");
/* 129 */     headerBuilder.append("kColl");
/* 130 */     headerBuilder.append(" id=\"");
/* 131 */     headerBuilder.append("entryData");
/* 132 */     headerBuilder.append("\">");
/* 133 */     headerBuilder.append(NEW_LINE);
 
/* 136 */     headerBuilder.append(requestUserAssembly());
 
/* 138 */     return headerBuilder;
   }
 
   public StringBuilder buildField(T obj, Field field) throws MarshallException {
/* 142 */     Annotation annotationField = field.getAnnotation(AeaField.class);
/* 143 */     AeaField fieldAnnotation = (AeaField)annotationField;
     try {
/* 145 */       Object value = ObjectUtils.invokeGetterMethod(field, obj);
/* 146 */       if (value != null) {
/* 147 */         StringBuilder fieldBuilder = new StringBuilder();
/* 148 */         fieldBuilder.append("\t\t\t\t");
/* 149 */         fieldBuilder.append("<");
/* 150 */         fieldBuilder.append(fieldAnnotation.name());
/* 151 */         fieldBuilder.append(" id=\"");
/* 152 */         fieldBuilder.append((fieldAnnotation.id().equals("")) ? field.getName() : fieldAnnotation.id());
/* 153 */         fieldBuilder.append("\" value=\"");
/* 154 */         if (fieldAnnotation.converter().equals(Class.class)) {
/* 155 */           fieldBuilder.append(value);
         }
         else {
/* 158 */           Converter converter = (Converter)fieldAnnotation.converter().newInstance();
/* 159 */           fieldBuilder.append(converter.formatValue(value));
         }
/* 161 */         fieldBuilder.append("\"/>");
/* 162 */         fieldBuilder.append(NEW_LINE);
 
/* 164 */         return fieldBuilder;
       }
/* 166 */       return new StringBuilder("");
     }
     catch (ConverterException e) {
/* 169 */       throw new MarshallException("The converter [" + fieldAnnotation.converter() + "] failed converting the valeu in field " + "[" + field.getName() + "].", e);
     }
     catch (RuntimeException e)
     {
/* 173 */       throw new MarshallException("Unexpected error trying to assembly the output data.", e);
     }
     catch (NoSuchMethodException e) {
/* 176 */       throw new MarshallException("There is no such method [ get" + field.getName() + "()], in the given object [" + obj.getClass().getSimpleName() + "].", e);
     }
     catch (IllegalAccessException e)
     {
/* 180 */       throw new MarshallException("The method [get" + field.getName() + "()] must be public.", e);
     }
     catch (InvocationTargetException e) {
/* 183 */       throw new MarshallException("The invocation of method [get" + field.getName() + "()] throwed an exception.", e);
     }
     catch (InstantiationException e)
     {
/* 187 */       throw new MarshallException("Failed to instantiate the given converter class [" + field.getName() + "].", e);
     }
   }
 
   public StringBuilder buildTrail()
   {
/* 193 */     StringBuilder trailBuilder = new StringBuilder();
 
/* 195 */     trailBuilder.append("\t\t\t");
/* 196 */     trailBuilder.append("</");
/* 197 */     trailBuilder.append("kColl");
/* 198 */     trailBuilder.append(">");
/* 199 */     trailBuilder.append(NEW_LINE);
 
/* 201 */     trailBuilder.append("\t\t");
/* 202 */     trailBuilder.append("</");
/* 203 */     trailBuilder.append("dse_formattedData");
/* 204 */     trailBuilder.append(">");
/* 205 */     trailBuilder.append(NEW_LINE);
 
/* 207 */     trailBuilder.append("\t");
/* 208 */     trailBuilder.append("<");
/* 209 */     trailBuilder.append("dse_processParameters");
/* 210 */     trailBuilder.append(">");
/* 211 */     trailBuilder.append("dse_operationName");
/* 212 */     trailBuilder.append("</");
/* 213 */     trailBuilder.append("dse_processParameters");
/* 214 */     trailBuilder.append(">");
/* 215 */     trailBuilder.append(NEW_LINE);
 
/* 217 */     trailBuilder.append("</");
/* 218 */     trailBuilder.append("requestMsg");
/* 219 */     trailBuilder.append(">");
/* 220 */     trailBuilder.append(NEW_LINE);
/* 221 */     return trailBuilder;
   }
 
   private Class<T> checkMandatoryAnnotations(Object obj) throws MarshallException {
/* 225 */     Class clazz = obj.getClass();
 
/* 227 */     this.annAEARequestMessage = ((AeaRequestMessage)clazz.getAnnotation(AeaRequestMessage.class));
/* 228 */     if (this.annAEARequestMessage == null) {
/* 229 */       throw new MarshallException(MessageFormat.format("The provided class [{0}] are missing the mandatory annotation: {1}.", new Object[] { obj.getClass().getSimpleName(), AeaRequestMessage.class.getSimpleName() }));
     }
 
/* 232 */     return clazz;
   }
 
   private Object requestUserAssembly() throws MarshallException {
/* 236 */     StringBuilder userBuilder = new StringBuilder();
 
/* 238 */     userBuilder.append("\t\t\t\t");
/* 239 */     userBuilder.append("<field id=\"");
/* 240 */     userBuilder.append("Usuario");
/* 241 */     userBuilder.append("\"");
/* 242 */     userBuilder.append(" value=\"");
 
/* 244 */     if (this.userName == null) {
/* 245 */       throw new MarshallException("This transaction needs a user. Please provide one setting the user name using the setUserName() method.");
     }
 
/* 249 */     userBuilder.append(this.userName);
 
/* 251 */     userBuilder.append("\" ");
/* 252 */     userBuilder.append("/>");
/* 253 */     userBuilder.append(NEW_LINE);
/* 254 */     return userBuilder;
   }
 
   public StringBuilder buildDetail(T obj) throws MarshallException {
/* 258 */     throw new MarshallException(new UnsupportedOperationException("This method is unavailable for this class."));
   }
 }