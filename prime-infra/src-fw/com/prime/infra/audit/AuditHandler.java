package com.prime.infra.audit;

public abstract interface AuditHandler
{
  public abstract void logEvent(AuditInfo paramAuditInfo);
}