package com.prime.infra.record.adapters;

import com.prime.infra.record.exception.MarshallException;
import java.io.Writer;

public abstract interface RecordWriter<T>
{
  public abstract void setOutput(Writer paramWriter);

  public abstract void setMarshaller(DataMarshaller<T> paramDataMarshaller);

  public abstract void marshall(T paramT)
    throws MarshallException;

  public abstract void flush()
    throws MarshallException;

  public abstract void close()
    throws MarshallException;
}