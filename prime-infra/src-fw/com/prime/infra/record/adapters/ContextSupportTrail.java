package com.prime.infra.record.adapters;

import com.prime.infra.record.exception.MarshallException;

public abstract interface ContextSupportTrail
{
  public abstract String processTrail(MarshallContext paramMarshallContext)
    throws MarshallException;
}