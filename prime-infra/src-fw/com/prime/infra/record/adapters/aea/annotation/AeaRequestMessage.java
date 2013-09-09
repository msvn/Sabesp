package com.prime.infra.record.adapters.aea.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AeaRequestMessage
{
  public abstract String operationName();

  public abstract String processParameters();
}