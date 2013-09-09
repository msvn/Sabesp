 package com.prime.infra.jms.aea;
 
 import com.prime.infra.jms.AbstractMessagingGateway;
 import com.prime.infra.record.adapters.RecordWriter;
 import com.prime.infra.record.adapters.aea.AEAError;
 import com.prime.infra.record.adapters.aea.AEAReplyParser;
 import com.prime.infra.record.adapters.aea.AEARequestMarshaller;
 import com.prime.infra.record.adapters.data.impl.DefaultRecordWriter;
 import com.prime.infra.record.exception.MarshallException;
 import com.prime.infra.record.exception.MessageErrorException;
 import com.prime.infra.record.exception.ParseException;
 import com.prime.infra.record.exception.ValidateException;
 import java.io.StringReader;
 import java.io.StringWriter;
 import java.util.Collection;
 import javax.jms.Queue;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;
 
 public abstract class AbstractAeaMessagingGatewayImpl<T> extends AbstractMessagingGateway
   implements AeaMessagingGateway<T>
 {
   private final Logger logger;
 
   @Autowired
   private AeaUser aeaUser;
 
   public AbstractAeaMessagingGatewayImpl()
   {
/*  33 */     this.logger = LoggerFactory.getLogger(AbstractAeaMessagingGatewayImpl.class);
   }
 
   public abstract Queue getRequestQueue();
 
   public abstract Queue getResponseQueue();
 
   @Transactional(readOnly=false, propagation=Propagation.NOT_SUPPORTED)
   public AeaResult send(T request)
     throws AeaException
   {
     String messageReply;
/*  53 */     if (request == null) {
/*  54 */       throw new NullPointerException("Request object cannot be null.");
     }
 
/*  57 */     this.logger.debug("Sending AEA message... ");
     try
     {
/*  60 */       String messageRequest = prepareRequest(request);
/*  61 */       this.logger.trace("AEA request = {}", messageRequest);
/*  62 */       messageReply = sendAndReceiveTextMessage(messageRequest);
/*  63 */       this.logger.trace("AEA reply = {}", messageReply);
     }
     catch (Exception e) {
/*  66 */       this.logger.error("Error sending AEA message");
/*  67 */       throw new AeaException(e);
     }
 
/*  70 */     this.logger.debug("Parsing AEA response... ");
/*  71 */     return prepareReply(messageReply);
   }
 
   protected String prepareRequest(T request) throws MarshallException {
/*  75 */     RecordWriter writer = new DefaultRecordWriter();
 
/*  77 */     AEARequestMarshaller dataMarshaller = new AEARequestMarshaller();
/*  78 */     dataMarshaller.setUserName(this.aeaUser.getUserName());
/*  79 */     dataMarshaller.setOperationName(getOperation());
/*  80 */     writer.setMarshaller(dataMarshaller);
 
/*  82 */     StringWriter output = new StringWriter();
/*  83 */     writer.setOutput(output);
 
/*  85 */     writer.marshall(request);
/*  86 */     writer.flush();
 
/*  88 */     return output.toString();
   }
 
   protected AeaResult prepareReply(String messageReply) throws AeaException {
/*  92 */     if (messageReply == null) {
/*  93 */       this.logger.debug("null messageReply");
/*  94 */       throw new AeaException("There is no response message.");
     }
 
/*  97 */     AEAReplyParser parser = new AEAReplyParser();
/*  98 */     parser.setReplyFormats(getReplyFormats());
     try {
/* 100 */       parser.parse(new StringReader(messageReply));
     }
     catch (ValidateException e)
     {
/* 104 */       this.logger.error("A validation restriction occurred.");
/* 105 */       throw new AeaException(e);
     }
     catch (MessageErrorException e)
     {
/* 109 */       Collection<AEAError> errors = e.getErrors();
/* 110 */       if ((errors != null) && (errors.size() > 0)) {
/* 111 */         for (AEAError error : errors) {
/* 112 */           this.logger.error("AEA error [" + error.getId() + "]: " + error.getDescription());
         }
       }
/* 115 */       throw new AeaException(e);
     }
     catch (ParseException e)
     {
/* 119 */       this.logger.error("Cannot parse the given message.");
/* 120 */       throw new AeaException(e);
     }
 
/* 124 */     if (this.logger.isErrorEnabled()) {
/* 125 */       Collection<String> errors = parser.getParsingErrors();
/* 126 */       if ((errors != null) && (errors.size() > 0)) {
/* 127 */         for (String e : errors) {
/* 128 */           this.logger.error("Parsing error: " + e);
         }
       }
     }
/* 132 */     return new AeaResult(parser);
   }
 
   private String errorsToString(Collection<AEAError> errors)
   {
/* 140 */     StringBuilder result = new StringBuilder();
/* 141 */     for (AEAError error : errors) {
/* 142 */       if (result.length() != 0) {
/* 143 */         result.append("; ");
       }
/* 145 */       result.append(error.getDescription());
     }
/* 147 */     return result.toString();
   }
 
   public AeaUser getAeaUser()
   {
/* 154 */     return this.aeaUser;
   }
 
   public void setAeaUser(AeaUser aeaUser)
   {
/* 161 */     this.aeaUser = aeaUser;
   }
 }