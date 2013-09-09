package com.prime.infra.record.adapters.flatfile.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedLenghtFieldFile
{
  
  public abstract Class<?> header() default Class.class;

  public abstract Class<?> trail() default Class.class;
  
  public abstract String nameFile() default "";
  
}