 package com.prime.infra.jms;
 
 import java.lang.reflect.ParameterizedType;
 import java.lang.reflect.Type;
 import javax.jms.BytesMessage;
 import javax.jms.JMSException;
 import javax.jms.MapMessage;
 import javax.jms.Message;
 import javax.jms.ObjectMessage;
 import javax.jms.Session;
 import javax.jms.StreamMessage;
 import javax.jms.TextMessage;
import org.springframework.jms.core.MessageCreator;
 
 public class ExtendedMessageCreator<T extends Message>
   implements MessageCreator
 {
   private Message message;
   private Class<T> messageType;
 
   public ExtendedMessageCreator()
   {
/* 33 */     Type genericSuperclass = super.getClass().getGenericSuperclass();
/* 34 */     ParameterizedType type = (ParameterizedType)genericSuperclass;
/* 35 */     this.messageType = ((Class)type.getActualTypeArguments()[0]);
   }
public ExtendedMessageCreator(String msg) {
	// TODO Auto-generated constructor stub
}
 
   public Message createMessage(Session session)
     throws JMSException
   {
/* 42 */     if (this.messageType.equals(TextMessage.class)) {
/* 43 */       this.message = session.createTextMessage();
     }
/* 45 */     else if (this.messageType.equals(MapMessage.class)) {
/* 46 */       this.message = session.createMapMessage();
     }
/* 48 */     else if (this.messageType.equals(BytesMessage.class)) {
/* 49 */       this.message = session.createBytesMessage();
     }
/* 51 */     else if (this.messageType.equals(ObjectMessage.class)) {
/* 52 */       this.message = session.createObjectMessage();
     }
/* 54 */     else if (this.messageType.equals(StreamMessage.class)) {
/* 55 */       this.message = session.createStreamMessage();
     }
/* 57 */     else if (this.messageType.equals(Message.class)) {
/* 58 */       this.message = session.createMessage();
     }
/* 60 */     setParams((T)this.message);
/* 61 */     return this.message;
   }
 
   public void setParams(T message)
     throws JMSException
   {
   }
 
   public Message getMessage()
   {
/* 76 */     return this.message;
   }
 
   public String toString()
   {
/* 81 */     return super.getClass().getName() + " - message: \n" + ((this.message != null) ? this.message.toString() : "message not yet created!");
   }
 }