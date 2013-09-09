 package com.prime.infra.aop;
 
 import java.io.PrintStream;
 import java.lang.reflect.Array;
 import java.lang.reflect.Method;
 import java.util.Arrays;
 import org.apache.commons.lang.builder.ToStringBuilder;
 import org.apache.commons.lang.builder.ToStringStyle;
 import org.aspectj.lang.JoinPoint;
 import org.aspectj.lang.ProceedingJoinPoint;
 import org.aspectj.lang.Signature;
 import org.hibernate.Hibernate;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public class Util
 {
   private static final int MAX_ARRAY_DISPLAY = 10;
/* 117 */   private static LazyToStringStyle lazyToStringStyle = new LazyToStringStyle();
 
   public static Logger getLogger(JoinPoint jp)
   {
/*  24 */     String fqcn = jp.getTarget().getClass().getName();
/*  25 */     return LoggerFactory.getLogger(fqcn);
   }
 
   public static String serviceName(ProceedingJoinPoint jp) {
/*  29 */     return jp.getTarget().getClass().getSimpleName();
   }
 
   public static String methodName(JoinPoint jp) {
/*  33 */     return jp.getSignature().getName();
   }
 
   public static String fullMethodName(JoinPoint jp) {
/*  37 */     return jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
   }
 
   public static String className(JoinPoint jp) {
/*  41 */     return jp.getTarget().getClass().getName();
   }
 
   public static Object getArgOfType(ProceedingJoinPoint jp, Class type) {
/*  45 */     Object[] args = jp.getArgs();
 
/*  47 */     for (Object arg : args) {
/*  48 */       if ((arg != null) && (arg.getClass().isAssignableFrom(type))) {
/*  49 */         return arg;
       }
     }
 
/*  53 */     throw new IllegalArgumentException(type.getName() + " not found in service call");
   }
 
   public static void appendArguments(Object[] args, StringBuilder sb) {
/*  57 */     for (int i = 0; i < args.length; ++i) {
/*  58 */       Object arg = args[i];
/*  59 */       if (i > 0) {
/*  60 */         sb.append(',');
       }
/*  62 */       sb.append(displayObject(arg));
     }
   }
 
   public static String displayObject(Object arg) {
/*  67 */     if (arg == null) {
/*  68 */       return "<null>";
     }
/*  70 */     if ((arg instanceof String) || (arg instanceof Character) || (arg instanceof Boolean) || (arg instanceof Number)) {
/*  71 */       return arg.toString();
     }
/*  73 */     if (arg.getClass().isArray()) {
/*  74 */       int length = Array.getLength(arg);
/*  75 */       if (length > 10) {
/*  76 */         return "LargeArray[size=" + length + "]";
       }
     }
/*  79 */     if (hasToString(arg)) {
/*  80 */       return arg.toString();
     }
/*  82 */     return ToStringBuilder.reflectionToString(arg, lazyToStringStyle);
   }
 
   public static boolean hasToString(Object o) {
     try {
/*  87 */       Method m = o.getClass().getDeclaredMethod("toString", new Class[0]);
/*  88 */       return (m != null);
     }
     catch (NoSuchMethodException e) {
     }
/*  92 */     return false;
   }
 
   public static void main(String[] args)
   {
/* 121 */     System.out.println("" + String.class.getName());
/* 122 */     System.out.println("" + String.class.getSimpleName());
 
/* 124 */     int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
/* 125 */     System.out.println("x = " + x.toString());
/* 126 */     System.out.println("x = " + Arrays.asList(new int[][] { x }).toString());
 
/* 128 */     System.out.println("x = " + ToStringBuilder.reflectionToString(x));
/* 129 */     System.out.println("x = " + ToStringBuilder.reflectionToString(x, lazyToStringStyle));
 
/* 131 */     int[] y = new int[10];
/* 132 */     System.arraycopy(x, 0, y, 0, 10);
/* 133 */     System.out.println("y = " + ToStringBuilder.reflectionToString(y, lazyToStringStyle));
   }
 
   private static class LazyToStringStyle extends ToStringStyle
   {
     private LazyToStringStyle()
     {
/* 101 */       setUseShortClassName(true);
/* 102 */       setUseIdentityHashCode(false);
     }
 
     public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
/* 106 */       if (Hibernate.isInitialized(value)) {
/* 107 */         super.append(buffer, fieldName, value, fullDetail);
       }
       else {
/* 110 */         appendFieldStart(buffer, fieldName);
/* 111 */         buffer.append("<lazy>");
/* 112 */         appendFieldEnd(buffer, fieldName);
       }
     }
   }
 }