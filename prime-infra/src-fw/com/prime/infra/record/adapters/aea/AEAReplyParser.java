 package com.prime.infra.record.adapters.aea;
 
 import com.prime.infra.converter.Converter;
 import com.prime.infra.record.adapters.aea.annotation.AeaField;
 import com.prime.infra.record.adapters.aea.annotation.AeaReplyMessage;
 import com.prime.infra.record.exception.DataException;
 import com.prime.infra.record.exception.MessageErrorException;
 import com.prime.infra.record.exception.ParseException;
 import com.prime.infra.record.exception.ValidateException;
 import com.prime.infra.validator.Validator;
 import java.io.IOException;
 import java.io.Reader;
 import java.lang.reflect.Field;
 import java.text.MessageFormat;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.xml.parsers.ParserConfigurationException;
 import javax.xml.parsers.SAXParser;
 import javax.xml.parsers.SAXParserFactory;
 import org.apache.commons.beanutils.BeanUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.xml.sax.Attributes;
 import org.xml.sax.InputSource;
 import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
 public class AEAReplyParser
 {
/*  41 */   private static final Logger LOGGER = LoggerFactory.getLogger(AEAReplyParser.class);
   private static final String MISSING_MANDATORY_ANNOTATION = "The class provided [{0}] is missing the mandadotory annotation: {1}.";
   private Map<String, Class<?>> replyFormats;
   private Collection<String> parsingErrors;
   private Map<String, List<Object>> result;
   private List<Object> orderedObjects;
 
   public Map<String, List<Object>> parse(Reader data)
     throws MessageErrorException, ParseException, ValidateException
   {
/*  62 */     SAXParserFactory parserFactory = SAXParserFactory.newInstance();
/*  63 */     parserFactory.setNamespaceAware(true);
/*  64 */     InputSource inputSource = new InputSource(data);
/*  65 */     AEAXmlHandler handler = new AEAXmlHandler(this.replyFormats);
     try {
/*  67 */       SAXParser parser = parserFactory.newSAXParser();
/*  68 */       parser.parse(inputSource, handler);
     }
     catch (SAXException e) {
/*  71 */       if (null != e.getCause()) {
/*  72 */         if (e.getCause() instanceof MessageErrorException) {
/*  73 */           throw ((MessageErrorException)e.getCause());
         }
/*  75 */         if (e.getCause() instanceof ValidateException)
/*  76 */           throw ((ValidateException)e.getCause());
       }
     }
     catch (IOException e)
     {
/*  81 */       throw new ParseException("Invalid stream [" + data + "]", e);
     }
     catch (ParserConfigurationException e) {
/*  84 */       throw new ParseException(e);
     }
     finally {
/*  87 */       if (null != data) {
         try {
/*  89 */           data.close();
         }
         catch (IOException e) {
/*  92 */           LOGGER.error("Error trying to close stream.", e);
         }
       }
     }
/*  96 */     this.parsingErrors = handler.getErrors();
/*  97 */     this.result = handler.getResult();
/*  98 */     this.orderedObjects = handler.getOrderedRecords();
/*  99 */     return this.result;
   }
 
   public void setReplyFormats(Map<String, Class<?>> replyFormats)
   {
/* 108 */     this.replyFormats = replyFormats;
   }
 
   public Collection<String> getParsingErrors()
   {
/* 115 */     return this.parsingErrors;
   }
 
   public Collection<AEAWarning> getWarnings()
   {
/* 124 */     return (Collection<AEAWarning>) getList("WARNINGS");
   }
 
   public List<?> getList(String format)
   {
/* 428 */     if (null == this.result) {
/* 429 */       throw new IllegalStateException("No data loaded.");
     }
/* 431 */     return ((List)this.result.get(format));
   }
 
   public List<Object> getOrderedRecords()
   {
/* 438 */     return this.orderedObjects;
   }
 
   public <T> List<T> getList(Class<T> clazz)
     throws ParseException
   {
/* 452 */     if (null == this.result) {
/* 453 */       throw new IllegalStateException("No data loaded.");
     }
/* 455 */     String key = findKey(clazz);
/* 456 */     if (null == key) {
/* 457 */       throw new ParseException("Requested type not found. Please check the mapping configuration.");
     }
/* 459 */     return ((List)this.result.get(key));
   }
 
   private String findKey(Class<?> clazz) {
/* 463 */     if (this.replyFormats.containsValue(clazz)) {
/* 464 */       for (String key : this.replyFormats.keySet()) {
/* 465 */         if (((Class)this.replyFormats.get(key)).equals(clazz)) {
/* 466 */           return key;
         }
       }
     }
/* 470 */     return null;
   }
 
   private static class FieldProperties
   {
			  public FieldProperties(Object o){
				  super();
			  }
			  
			  public FieldProperties(){
				  super();
			  }
	
     private String name;
     private Class<?> converter;
     private Class<?> validator;
 
     public Class<?> getValidator()
     {
/* 396 */       return this.validator;
     }
 
     public void setValidator(Class<?> validator) {
/* 400 */       this.validator = validator;
     }
 
     public String getName() {
/* 404 */       return this.name;
     }
 
     public void setName(String name) {
/* 408 */       this.name = name;
     }
 
     public Class<?> getConverter() {
/* 412 */       return this.converter;
     }
 
     public void setConverter(Class<?> converter) {
/* 416 */       this.converter = converter;
     }
   }
 
   private static class AEAXmlHandler extends DefaultHandler
   {
     private static final String NOK = "NOK";
     private static final String ERRORS = "errores";
     private static final String WARNINGS = "avisos";
     private StringBuilder actualValue;
     private boolean isRecord;
     private boolean errors;
     private boolean errorsTag;
     private boolean warningsTag;
     private String actualFormat;
     private Object actualRecord;
     private List<Object> warnings;
     private Class<?> actualClass;
     private Map<String, List<Object>> result;
     private Collection<String> errorMessages;
     private MessageErrorException errorException;
     private Map<String, Class<?>> replyFormats;
     private List<Object> orderedRecords;
 
     public AEAXmlHandler(Map<String, Class<?>> replyFormats)
     {
/* 149 */       this.replyFormats = replyFormats;
     }
 
     public void startDocument() throws SAXException {
/* 153 */       this.errorMessages = new ArrayList();
/* 154 */       this.actualValue = new StringBuilder();
/* 155 */       this.warnings = new ArrayList();
/* 156 */       this.orderedRecords = new ArrayList();
     }
 
     public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
/* 160 */       this.actualValue.delete(0, this.actualValue.length());
/* 161 */       String attributeId = attributes.getValue("id");
/* 162 */       if (this.errors) {
/* 163 */         handleErrors(attributes, attributeId);
 
/* 165 */         return;
       }
/* 167 */       handleWarnings(attributes, attributeId);
/* 168 */       handleAttributes(attributes, attributeId);
     }
 
     public void characters(char[] ch, int start, int length) {
/* 172 */       this.actualValue.append(ch, start, length);
     }
 
     public void endElement(String uri, String localName, String name) throws SAXException {
/* 176 */       if ((name.equals("dse_status")) && 
/* 177 */         (this.actualValue.toString().equalsIgnoreCase("NOK")))
       {
/* 179 */         this.errors = true;
/* 180 */         this.errorException = new MessageErrorException();
       }
 
/* 184 */       if ((this.errorsTag) && (name.equalsIgnoreCase("kcoll"))) {
/* 185 */         this.errorsTag = false;
/* 186 */         SAXException exception = new SAXException();
/* 187 */         exception.initCause(this.errorException);
/* 188 */         throw exception;
       }
 
/* 191 */       if ((this.warningsTag) && (name.equalsIgnoreCase("kcoll"))) {
/* 192 */         this.warningsTag = false;
       }
 
/* 195 */       if ((this.isRecord) && (name.equalsIgnoreCase("kcoll"))) {
/* 196 */         this.isRecord = Boolean.FALSE.booleanValue();
/* 197 */         this.orderedRecords.add(this.actualRecord);
/* 198 */         if (!(this.result.containsKey(this.actualFormat))) {
/* 199 */           List objects = new ArrayList();
/* 200 */           this.result.put(this.actualFormat, objects);
         }
/* 202 */         ((List)this.result.get(this.actualFormat)).add(this.actualRecord);
       }
/* 204 */       this.actualValue.delete(0, this.actualValue.length());
     }
 
     public void endDocument() throws SAXException {
/* 208 */       if (this.warnings.size() > 0)
/* 209 */         this.result.put("WARNINGS", this.warnings);
     }
 
     private void handleWarnings(Attributes attributes, String attributeId)
     {
/* 215 */       if (this.warningsTag) {
/* 216 */         AEAWarning actualWarning = new AEAWarning();
/* 217 */         actualWarning.setId(attributeId);
/* 218 */         actualWarning.setDescription(attributes.getValue("value"));
/* 219 */         this.warnings.add(actualWarning);
       }
/* 221 */       if ((null != attributeId) && (attributeId.equalsIgnoreCase("avisos")))
/* 222 */         this.warningsTag = true;
     }
 
     private void handleErrors(Attributes attributes, String attributeId)
     {
/* 227 */       if (this.errorsTag) {
/* 228 */         AEAError actualError = new AEAError();
/* 229 */         actualError.setId(attributeId);
/* 230 */         actualError.setDescription(attributes.getValue("value"));
/* 231 */         this.errorException.addError(actualError);
       }
/* 233 */       if ((null != attributeId) && (attributeId.equalsIgnoreCase("errores")))
/* 234 */         this.errorsTag = true;
     }
 
     private void checkMandatoryAnnotations(Class<?> clazz)
       throws DataException
     {
/* 240 */       AeaReplyMessage annAeaReplyMessage = (AeaReplyMessage)clazz.getAnnotation(AeaReplyMessage.class);
/* 241 */       if (annAeaReplyMessage == null)
/* 242 */         throw new DataException(MessageFormat.format("The class provided [{0}] is missing the mandadotory annotation: {1}.", new Object[] { clazz.getSimpleName(), AeaReplyMessage.class.getSimpleName() }));
     }
 
     private void handleAttributes(Attributes attributes, String attributeId)
       throws SAXException
     {
/* 248 */       if (null == attributes)
         return;
/* 250 */       if (this.isRecord)
       {
         try
         {
/* 254 */           AEAReplyParser.FieldProperties fieldProperties = handleAttributeNames(this.actualRecord.getClass(), attributeId);
 
/* 257 */           if (null != fieldProperties)
           {
/* 259 */             Object value = attributes.getValue("value");
 
/* 261 */             Class validatorClass = fieldProperties.getValidator();
 
/* 264 */             if ((null != validatorClass) && (!(validatorClass.equals(Class.class)))) {
/* 265 */               Validator validator = (Validator)validatorClass.newInstance();
/* 266 */               validator.validate(value);
             }
 
/* 269 */             Class converterClass = fieldProperties.getConverter();
 
/* 272 */             if ((null != converterClass) && (!(converterClass.equals(Class.class)))) {
/* 273 */               Converter converter = (Converter)converterClass.newInstance();
/* 274 */               value = converter.parseValue((String)value);
             }
 
/* 277 */             BeanUtils.copyProperty(this.actualRecord, fieldProperties.getName(), value);
           }
           else {
/* 280 */             AEAReplyParser.LOGGER.debug("There isn't such field [" + attributeId + "] in class [" + this.actualClass.getSimpleName() + "].");
           }
         }
         catch (IllegalArgumentException e)
         {
/* 285 */           this.errorMessages.add("Invalid type for field [" + attributeId + "] from record [" + this.actualFormat + "]. Please set a converter.");
         }
         catch (ValidateException e)
         {
/* 289 */           SAXException exception = new SAXException();
/* 290 */           exception.initCause(e);
/* 291 */           throw exception;
         }
         catch (Throwable t) {
/* 294 */           handleError(t);
         }
       }
 
/* 298 */       if ((null != attributeId) && (attributeId.equalsIgnoreCase("dc_formato"))) {
/* 299 */         String format = attributes.getValue("value");
/* 300 */         if (null != this.actualFormat) {
/* 301 */           if (this.actualFormat.equalsIgnoreCase(format))
           {
/* 303 */             this.isRecord = createNewRecord(format);
           }
           else
           {
/* 307 */             this.isRecord = createNewRecord(format);
           }
         }
         else
         {
/* 312 */           this.isRecord = createNewRecord(format);
/* 313 */           if (this.isRecord)
/* 314 */             this.result = new HashMap();
         }
       }
     }
 
     private void handleError(Throwable e)
     {
/* 322 */       this.errorMessages.add(e.getMessage());
/* 323 */       AEAReplyParser.LOGGER.debug("Parse error: " + e.getMessage(), e);
     }
 
     private AEAReplyParser.FieldProperties handleAttributeNames(Class<?> class1, String attributeId) throws DataException {
/* 327 */       checkMandatoryAnnotations(class1);
/* 328 */       AEAReplyParser.FieldProperties result = null;
       try {
/* 330 */         Field field = this.actualRecord.getClass().getDeclaredField(attributeId);
/* 331 */         if (field.isAnnotationPresent(AeaField.class)) {
/* 332 */           result = new AEAReplyParser.FieldProperties(null);
/* 333 */           AeaField aeaField = (AeaField)field.getAnnotation(AeaField.class);
/* 334 */           result.setName(attributeId);
/* 335 */           result.setConverter(aeaField.converter());
/* 336 */           result.setValidator(aeaField.validator());
         }
       }
       catch (NoSuchFieldException e) {
/* 340 */         Field[] fields = this.actualRecord.getClass().getDeclaredFields();
/* 341 */         for (Field field : fields) {
/* 342 */           if (field.isAnnotationPresent(AeaField.class)) {
/* 343 */             String attributeName = ((AeaField)field.getAnnotation(AeaField.class)).id();
/* 344 */             if (attributeName.equalsIgnoreCase(attributeId)) {
/* 345 */               result = new AEAReplyParser.FieldProperties(null);
/* 346 */               AeaField aeaField = (AeaField)field.getAnnotation(AeaField.class);
/* 347 */               result.setName(field.getName());
/* 348 */               result.setConverter(aeaField.converter());
/* 349 */               result.setValidator(aeaField.validator());
/* 350 */               break;
             }
           }
         }
       }
/* 355 */       return result;
     }
 
     private boolean createNewRecord(String format) {
/* 359 */       this.actualFormat = format;
/* 360 */       this.actualClass = ((Class)this.replyFormats.get(this.actualFormat));
/* 361 */       if (null == this.actualClass)
/* 362 */         return false;
       try
       {
/* 365 */         this.actualRecord = this.actualClass.newInstance();
       }
       catch (InstantiationException e) {
/* 368 */         handleError(new ParseException("Unable to instantiate class[" + this.actualClass.getName() + "]. Please check your configuration files.", e));
       }
       catch (IllegalAccessException e)
       {
/* 372 */         handleError(e);
       }
/* 374 */       return true;
     }
 
     public Map<String, List<Object>> getResult() {
/* 378 */       return this.result;
     }
 
     public List<Object> getOrderedRecords() {
/* 382 */       return this.orderedRecords;
     }
 
     public Collection<String> getErrors() {
/* 386 */       return this.errorMessages;
     }
   }
 }