package com.prime.infra.record.adapters;

import com.prime.infra.record.adapters.flatfile.FlatfileContextDataMarshaller;
import com.prime.infra.record.exception.DataException;

public abstract interface ContextDataOutput<T> extends DataOutput<T>
{
  public abstract void save(T paramT, MarshallContext paramMarshallContext)
    throws DataException;

  public abstract void setFlatfileDataMarshaller(FlatfileContextDataMarshaller<T> paramFlatfileContextDataMarshaller);
}