package com.prime.infra.record.adapters;

import com.prime.infra.record.exception.WriterException;
import java.io.Writer;

public abstract interface ContextSupportRecordWriter<T> extends GenericRecordWriter<T>
{
  public abstract void write(T paramT, Writer paramWriter, MarshallContext paramMarshallContext)
    throws WriterException;
}