 package com.prime.infra.util;
 
 import java.io.PrintStream;
 import java.lang.reflect.Field;
 import java.lang.reflect.Method;
 import java.lang.reflect.Modifier;
 
 public class ErrorMessagePrinter
 {
   public static void main(String[] args)
     throws Exception
   {
/* 14 */     Class clazz = Class.forName(args[0]);
/* 15 */     printFields(clazz);
   }
 
   protected static void printFields(Class c) throws Exception {
/* 19 */     Field[] fields = c.getFields();
/* 20 */     for (Field field : fields)
/* 21 */       if ((Modifier.isStatic(field.getModifiers())) && (c.isAssignableFrom(field.getType()))) {
/* 22 */         Object obj = c.getField(field.getName()).get(null);
/* 23 */         Method getCode = obj.getClass().getMethod("getCode", new Class[0]);
/* 24 */         Method getMesssage = obj.getClass().getMethod("getMesssage", new Class[0]);
 
/* 26 */         String name = field.getName();
/* 27 */         Object code = getCode.invoke(obj, new Object[0]);
/* 28 */         Object message = getMesssage.invoke(obj, new Object[0]);
 
/* 30 */         System.out.printf("%d: %s - \"%s\"%n", new Object[] { code, name, message });
       }
   }
 }