package com.prime.infra.jms;

import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;

public abstract interface MessagingGateway
{
  public abstract Message sendMessage(ExtendedMessageCreator<?> paramExtendedMessageCreator)
    throws JMSException;

  public abstract Message sendTextMessage(String paramString)
    throws JMSException;

  public abstract Message receiveMessage(String paramString)
    throws JMSException;

  public abstract String receiveTextMessage(String paramString)
    throws JMSException;

  public abstract List<Message> receiveMultipleMessages(String paramString, int paramInt, long paramLong)
    throws JMSException;

  public abstract List<String> receiveMultipleTextMessages(String paramString, int paramInt, long paramLong)
    throws JMSException;

  public abstract Message sendAndReceiveMessage(ExtendedMessageCreator<?> paramExtendedMessageCreator, SelectorCreator paramSelectorCreator)
    throws JMSException;

  public abstract List<Message> sendAndReceiveMessages(ExtendedMessageCreator<?> paramExtendedMessageCreator, SelectorCreator paramSelectorCreator, int paramInt, long paramLong)
    throws JMSException;

  public abstract String sendAndReceiveTextMessage(String paramString)
    throws JMSException;
}