package com.prime.infra.record.adapters.flatfile.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedLenghtFieldDetailId
{
  public abstract String id();
}