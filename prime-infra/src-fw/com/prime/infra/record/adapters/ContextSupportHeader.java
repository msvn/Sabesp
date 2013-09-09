package com.prime.infra.record.adapters;

import com.prime.infra.record.exception.MarshallException;

public abstract interface ContextSupportHeader
{
  public abstract String processHeader(MarshallContext paramMarshallContext)
    throws MarshallException;
}