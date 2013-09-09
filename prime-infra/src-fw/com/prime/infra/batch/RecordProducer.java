package com.prime.infra.batch;

import java.util.List;

public abstract interface RecordProducer<T>
{
  public abstract boolean hasNext();

  public abstract T next();

  public abstract List<Parameter> getParameters();

  public abstract void setParameters(List<Parameter> paramList);
}