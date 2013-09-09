package com.prime.infra.security.authentication;

public abstract interface Authenticator
{
  public abstract void login()
    throws AuthenticationException;
}