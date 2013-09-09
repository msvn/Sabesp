package com.prime.infra.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class FrameworkPointcuts
{
  public static final int HIGHEST_PRECEDENCE = -2147483648;
  public static final int LOG_ASPECT_ORDER = 100;
  public static final int AUDIT_ASPECT_ORDER = 200;
  public static final int AUTHORIZE_ASPECT_ORDER = 300;
  public static final int TIMER_ASPECT_ORDER = 400;
  public static final int EXCEPTION_BARRIER_ASPECT_ORDER = 500;
  public static final int MONITOR_ASPECT_ORDER = 600;
  public static final int TRANSACTION_ASPECT_ORDER = 1000000000;
  public static final int CACHE_ASPECT_ORDER = 1000000300;
  public static final int FLUSH_ASPECT_ORDER = 1000000400;
  public static final int LOWEST_PRECEDENCE = 2147483647;

  @Pointcut("execution(* com.prime.app..service..*ServiceImpl.*(..))")
  public void businessServiceImpl()
  {
  }

  @Pointcut("execution(* com.prime.app..web.jsf.*BBean.*(..))")
  public void backingBean()
  {
  }
}