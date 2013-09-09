package com.prime.infra.jms.aea;

import java.util.Map;

public abstract interface AeaMessagingGateway<T>
{
  public abstract void setTimeOut(long paramLong);

  public abstract AeaResult send(T paramT)
    throws AeaException;

  public abstract Map<String, Class<?>> getReplyFormats();

  public abstract String getOperation();

  public abstract void setUser(AeaUser paramAeaUser);
}