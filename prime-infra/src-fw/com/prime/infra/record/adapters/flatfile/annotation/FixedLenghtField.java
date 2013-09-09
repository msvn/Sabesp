package com.prime.infra.record.adapters.flatfile.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedLenghtField

//public interface FixedLenghtField extends Annotation
{
  public abstract int position(); // default 0;

  public abstract int lenght(); // default 0;

  public abstract char paddingChar() default ' ';

  public abstract int paddingAlign() default 0;
   
  public abstract Class<?> converter() default Class.class;
//  public abstract Class<?> converter() default void.class;  

  public abstract Class<?> validator() default Class.class; 
}
