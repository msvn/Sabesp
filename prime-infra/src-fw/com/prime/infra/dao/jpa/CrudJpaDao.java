package com.prime.infra.dao.jpa;

import java.io.Serializable;
import java.util.List;

public abstract interface CrudJpaDao
{
  public abstract <T> T findById(Class<T> paramClass, Serializable paramSerializable, boolean paramBoolean);

  public abstract <T> List<T> findAll(Class<?> paramClass, int paramInt);

  public abstract <T> T persist(T paramT);

  public abstract <T> void remove(Class<?> paramClass, Serializable paramSerializable);
}