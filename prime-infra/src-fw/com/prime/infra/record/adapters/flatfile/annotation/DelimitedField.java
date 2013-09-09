package com.prime.infra.record.adapters.flatfile.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DelimitedField
{
  public abstract String name();

  public abstract int position();

  public abstract Class<?> converter();

  public abstract Class<?> validator();
}