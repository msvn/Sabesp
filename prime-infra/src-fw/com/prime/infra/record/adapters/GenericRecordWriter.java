package com.prime.infra.record.adapters;

import com.prime.infra.record.exception.WriterException;
import java.io.Writer;

public abstract interface GenericRecordWriter<T>
{
  public abstract void write(T paramT, Writer paramWriter)
    throws WriterException;

  public abstract void flush()
    throws WriterException;

  public abstract void close()
    throws WriterException;
}