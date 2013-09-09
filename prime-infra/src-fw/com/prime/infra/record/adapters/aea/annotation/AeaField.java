package com.prime.infra.record.adapters.aea.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AeaField
{
  public abstract String name();

  public abstract String id();

  public abstract Class<?> converter();

  public abstract Class<?> validator();
}