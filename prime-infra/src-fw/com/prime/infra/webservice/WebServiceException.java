 package com.prime.infra.webservice;
 
 import com.prime.infra.BusinessException;
 import com.prime.infra.SystemException;
 import javax.xml.ws.WebFault;
 
 @WebFault
 public class WebServiceException extends Exception
 {
   protected FaultInfo faultInfo;
 
   public WebServiceException(String message, int code)
   {
/* 18 */     super(message);
/* 19 */     this.faultInfo = new FaultInfo(message, Integer.valueOf(code));
   }
 
   public WebServiceException(SystemException se) {
/* 23 */     super(se.getMessage());
/* 24 */     this.faultInfo = new FaultInfo(se.getMessage(), Integer.valueOf(se.getErrorCode()));
   }
 
   public WebServiceException(BusinessException se, int code) {
/* 28 */     super(se.getMessage());
/* 29 */     this.faultInfo = new FaultInfo(se.getMessage(), Integer.valueOf(code));
   }
 
   public FaultInfo getFaultInfo() {
/* 33 */     return this.faultInfo;
   }
 }