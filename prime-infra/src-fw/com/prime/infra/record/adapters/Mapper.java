package com.prime.infra.record.adapters;

import com.prime.infra.record.exception.DataException;
import java.io.Reader;

public abstract interface Mapper
{
  public abstract Class<?> getPojoClass();

  public abstract int getFieldAmount();

  public abstract int getRecordSize();

  public abstract String getFieldName(Integer paramInteger);

  public abstract Object getFieldValue(Integer paramInteger)
    throws DataException;

  public abstract void setReader(Reader paramReader);

  public abstract boolean ready();

  public abstract void close()
    throws DataException;

  public abstract void setBufferSize(Integer paramInteger);
}