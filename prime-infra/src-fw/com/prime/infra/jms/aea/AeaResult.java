 package com.prime.infra.jms.aea;
 
 import com.prime.infra.aop.FaultException;
 import com.prime.infra.record.adapters.aea.AEAReplyParser;
 import com.prime.infra.record.adapters.aea.AEAWarning;
 import com.prime.infra.record.exception.ParseException;
 import java.util.List;
 
 public class AeaResult
 {
   private AEAReplyParser parser;
 
   AeaResult(AEAReplyParser parser)
   {
/* 22 */     this.parser = parser;
   }
 
   public List<Object> getOrderedRecords()
   {
/* 29 */     return this.parser.getOrderedRecords();
   }
 
   public <R> List<R> getList(Class<R> clazz)
   {
     try
     {
/* 37 */       return this.parser.getList(clazz);
     }
     catch (ParseException e) {
/* 40 */       throw new FaultException(e);
     }
   }
 
   public List<AEAWarning> getWarnings()
   {
/* 48 */     return ((List)this.parser.getWarnings());
   }
 
   public List<String> getParsingErrors()
   {
/* 55 */     return ((List)this.parser.getParsingErrors());
   }
 }