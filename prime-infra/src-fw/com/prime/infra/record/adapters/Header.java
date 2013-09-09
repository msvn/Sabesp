package com.prime.infra.record.adapters;

import com.prime.infra.record.exception.MarshallException;

public abstract interface Header
{
  public abstract void setPattern(String paramString);

  public abstract String changeValues(Object[] paramArrayOfObject)
    throws MarshallException;
}