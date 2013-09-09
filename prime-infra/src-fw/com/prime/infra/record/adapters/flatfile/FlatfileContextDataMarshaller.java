package com.prime.infra.record.adapters.flatfile;

import com.prime.infra.record.adapters.MarshallContext;
import com.prime.infra.record.exception.MarshallException;

public abstract interface FlatfileContextDataMarshaller<T>
{
  public abstract String marshall(T paramT)
    throws MarshallException;

  public abstract String marshall(T paramT, MarshallContext paramMarshallContext)
    throws MarshallException;
}