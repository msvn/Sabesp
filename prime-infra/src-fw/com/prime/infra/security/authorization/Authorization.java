package com.prime.infra.security.authorization;

import com.prime.infra.security.SecurityInfo;
import javax.swing.tree.TreeModel;

public abstract interface Authorization
{
  public abstract boolean isUserAuthorized(SecurityInfo paramSecurityInfo, String paramString);

  public abstract Object getAuthorizationProvider();

  public abstract TreeModel getMenuTree(String paramString1, String paramString2);
}