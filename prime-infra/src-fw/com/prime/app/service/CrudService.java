package com.prime.app.service;

import java.io.Serializable;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public abstract interface CrudService
{
  @Transactional(readOnly=true)
  public abstract <T> List<T> findAll(Class<T> paramClass, int paramInt);

  @Transactional(readOnly=false)
  public abstract <T, ID extends Serializable> T get(Class<T> paramClass, ID paramID, boolean paramBoolean);

  @Transactional(readOnly=false)
  public abstract <T, ID extends Serializable> void remove(Class<T> paramClass, ID paramID);

  @Transactional(readOnly=false)
  public abstract <T> T save(T paramT);
}