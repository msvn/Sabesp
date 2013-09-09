 package com.prime.infra.batch;
 
 import java.io.PrintStream;
 import java.util.List;
 import org.apache.commons.lang.builder.ToStringBuilder;
 
 public class ToStdoutConsumer
   implements RecordConsumer
 {
   public void consume(Object object)
   {
/* 15 */     System.out.println(ToStringBuilder.reflectionToString(object));
   }
 
   public void flush() {
   }
 
   public List getParameters() {
/* 22 */     return null;
   }
 
   public void setParameters(List parameters)
   {
   }
 }