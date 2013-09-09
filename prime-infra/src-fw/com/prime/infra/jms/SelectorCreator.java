package com.prime.infra.jms;

import javax.jms.Message;

public abstract interface SelectorCreator
{
  public abstract String createSelector(Message paramMessage);
}