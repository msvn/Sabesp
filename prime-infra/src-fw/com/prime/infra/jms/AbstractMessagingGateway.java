 package com.prime.infra.jms;
 
 import java.util.ArrayList;
 import java.util.List;
 import javax.jms.ConnectionFactory;
 import javax.jms.JMSException;
 import javax.jms.Message;
 import javax.jms.Queue;
 import javax.jms.TextMessage;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.InitializingBean;
 import org.springframework.jms.core.JmsTemplate;
 
 public abstract class AbstractMessagingGateway
   implements MessagingGateway, InitializingBean
 {
   private final Logger logger;
   protected JmsTemplate jmsTemplate;
 
   public AbstractMessagingGateway()
   {
/*  23 */     this.logger = LoggerFactory.getLogger(AbstractMessagingGateway.class);
   }
 
   public void setTimeOut(long timeOut)
   {
/*  33 */     this.jmsTemplate.setReceiveTimeout(timeOut);
   }
 
   public abstract ConnectionFactory getConnectionFactory();
 
   public abstract Queue getRequestQueue();
 
   public abstract Queue getResponseQueue();
 
   public void afterPropertiesSet()
     throws Exception
   {
/*  63 */     this.logger.debug("ConnectionFactory = {}", getConnectionFactory());
 
/*  65 */     this.jmsTemplate = new JmsTemplate(getConnectionFactory());
/*  66 */     this.logger.debug("JMS Template created: {}", this.jmsTemplate);
   }
 
   public Message sendMessage(ExtendedMessageCreator<?> creator)
     throws JMSException
   {
/*  74 */     if (getRequestQueue() == null) {
/*  75 */       throw new NullPointerException("Request queue can not be null!");
     }
 
/*  80 */     this.jmsTemplate.send(getRequestQueue().getQueueName(), creator);
/*  81 */     this.logger.debug("Message Sent!");
 
/*  83 */     if (this.logger.isTraceEnabled()) {
/*  84 */       this.logger.trace("Message = {}", creator.getMessage());
     }
 
/*  87 */     return creator.getMessage();
   }
 
   public Message sendTextMessage(String msg)
     throws JMSException
   {
/*  94 */     ExtendedMessageCreator creator = new ExtendedMessageCreator(msg) {
       public void setParams(TextMessage message) throws JMSException {
/*  96 */         message.setText(message.toString());
       }
     };
/*  99 */     return sendMessage(creator);
   }
 
   public Message receiveMessage(String selector)
     throws JMSException
   {
/* 106 */     if (selector == null) {
/* 107 */       throw new NullPointerException("Selector can not be null!");
     }
 
/* 110 */     if (getResponseQueue() == null) {
/* 111 */       throw new NullPointerException("Response queue can not be null!");
     }
 
/* 114 */     this.logger.debug("Selecting message using: {}", selector);
/* 115 */     Message resMessage = this.jmsTemplate.receiveSelected(getResponseQueue().getQueueName(), selector);
 
/* 117 */     if (this.logger.isTraceEnabled()) {
/* 118 */       this.logger.trace("Response = {}", resMessage);
     }
 
/* 121 */     return resMessage;
   }
 
   public String receiveTextMessage(String selector)
     throws JMSException
   {
/* 129 */     TextMessage resMessage = (TextMessage)receiveMessage(selector);
/* 130 */     return ((resMessage != null) ? resMessage.getText() : null);
   }
 
   public List<Message> receiveMultipleMessages(String selector, int maxMsgCount, long totalTimeOut)
     throws JMSException
   {
/* 138 */     if (selector == null) {
/* 139 */       throw new NullPointerException("Selector can not be null!");
     }
 
/* 142 */     if (getResponseQueue() == null) {
/* 143 */       throw new NullPointerException("Response queue can not be null!");
     }
 
/* 146 */     if (this.logger.isDebugEnabled()) {
/* 147 */       this.logger.debug("Selecting message using: {}", selector);
     }
 
/* 150 */     List responses = new ArrayList();
 
/* 152 */     long finishTime = System.currentTimeMillis() + totalTimeOut;
 
/* 154 */     while ((responses.size() < maxMsgCount) && (System.currentTimeMillis() < finishTime)) {
/* 155 */       Message message = receiveMessage(selector);
 
/* 157 */       if (message == null) {
         continue;
       }
 
/* 161 */       responses.add(message);
     }
 
/* 164 */     if (this.logger.isDebugEnabled()) {
/* 165 */       this.logger.debug(responses.size() + " messages found: " + responses);
     }
 
/* 168 */     return responses;
   }
 
   public List<String> receiveMultipleTextMessages(String selector, int maxMsgCount, long totalTimeOut)
     throws JMSException
   {
/* 177 */     List<Message> responses = receiveMultipleMessages(selector, maxMsgCount, totalTimeOut);
 
/* 179 */     List textResponses = new ArrayList(responses.size());
/* 180 */     for (Message response : responses) {
/* 181 */       TextMessage txtMsg = (TextMessage)response;
/* 182 */       textResponses.add(txtMsg.getText());
     }
 
/* 185 */     return textResponses;
   }
 
   public Message sendAndReceiveMessage(ExtendedMessageCreator<?> messageCreator, SelectorCreator selectorCreator)
     throws JMSException
   {
/* 196 */     this.logger.debug("Sending message...");
/* 197 */     Message message = sendMessage(messageCreator);
 
/* 199 */     this.logger.debug("Receiving response...");
/* 200 */     return receiveMessage(selectorCreator.createSelector(message));
   }
 
   public List<Message> sendAndReceiveMessages(ExtendedMessageCreator<?> messageCreator, SelectorCreator selectorCreator, int maxMsgCount, long timeOut)
     throws JMSException
   {
/* 211 */     this.logger.debug("Sending message...");
/* 212 */     Message message = sendMessage(messageCreator);
 
/* 214 */     this.logger.debug("Receiving responses...");
/* 215 */     return receiveMultipleMessages(selectorCreator.createSelector(message), maxMsgCount, timeOut);
   }
 
   public String sendAndReceiveTextMessage(String msg)
     throws JMSException
   {
/* 223 */     this.logger.debug("Sending message...");
/* 224 */     Message requestMessage = sendTextMessage(msg);
 
/* 226 */     this.logger.debug("Receiving response...");
/* 227 */     String selector = "JMSCorrelationID = '" + requestMessage.getJMSMessageID() + "'";
/* 228 */     return receiveTextMessage(selector);
   }
 }