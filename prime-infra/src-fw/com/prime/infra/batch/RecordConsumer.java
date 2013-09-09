package com.prime.infra.batch;

import java.util.List;

public abstract interface RecordConsumer<V>
{
  public abstract void consume(V paramV);

  public abstract void flush();

  public abstract List<Parameter> getParameters();

  public abstract void setParameters(List<Parameter> paramList);
}