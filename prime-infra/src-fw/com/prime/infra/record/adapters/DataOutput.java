package com.prime.infra.record.adapters;

import com.prime.infra.record.exception.DataException;

public abstract interface DataOutput<T>
{
  public abstract void save(T paramT)
    throws DataException;

  public abstract void flush()
    throws DataException;

  public abstract void close()
    throws DataException;
}