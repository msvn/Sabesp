 package com.prime.infra.record.adapters;
 
 import java.lang.reflect.Field;
 import java.lang.reflect.InvocationTargetException;
 import java.lang.reflect.Method;
 
 public class ObjectUtils
 {
   public static Object invokeGetterMethod(Field field, Object object)
     throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
   {
/* 15 */     String methodName = "get" + String.valueOf(field.getName().charAt(0)).toUpperCase() + field.getName().substring(1);
 
/* 17 */     Class[] parameterTypes = new Class[0];
/* 18 */     Method method = object.getClass().getMethod(methodName, parameterTypes);
/* 19 */     return method.invoke(object, new Object[0]);
   }
 }