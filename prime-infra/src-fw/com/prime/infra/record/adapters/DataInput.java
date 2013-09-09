package com.prime.infra.record.adapters;

import com.prime.infra.record.exception.DataException;
import java.io.Reader;
import java.util.Collection;

public abstract interface DataInput
{
  public abstract boolean hasNext()
    throws DataException;

  public abstract Object read()
    throws DataException;

  public abstract void setReader(Reader paramReader)
    throws DataException;

  public abstract void setMapper(Mapper paramMapper);

  public abstract Collection<String> getErrors();

  public abstract void reset()
    throws DataException;

  public abstract void close()
    throws DataException;
}