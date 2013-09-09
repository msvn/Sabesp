package com.prime.infra.record.adapters;

import com.prime.infra.record.exception.MarshallException;
import java.lang.reflect.Field;
import java.util.Collection;

public abstract interface DataMarshaller<T>
{
  public abstract String marshall(Collection<T> paramCollection)
    throws MarshallException;

  public abstract String marshall(T paramT)
    throws MarshallException;

  public abstract StringBuilder buildHeader()
    throws MarshallException;

  public abstract StringBuilder buildField(T paramT, Field paramField)
    throws MarshallException;

  public abstract StringBuilder buildDetail(T paramT)
    throws MarshallException;

  public abstract StringBuilder buildTrail()
    throws MarshallException;
}