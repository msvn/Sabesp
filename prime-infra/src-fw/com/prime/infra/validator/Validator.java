package com.prime.infra.validator;

import com.prime.infra.record.exception.ValidateException;

public abstract interface Validator
{
  public abstract void validate(Object paramObject)
    throws ValidateException;

  public abstract boolean isValid(Object paramObject);
}