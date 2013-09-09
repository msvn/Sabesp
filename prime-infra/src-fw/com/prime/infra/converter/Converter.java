package com.prime.infra.converter;

import com.prime.infra.record.exception.ConverterException;

public abstract interface Converter<T>
{
  public abstract T parseValue(String paramString)
    throws ConverterException;

  public abstract String formatValue(Object paramObject)
    throws ConverterException;
}