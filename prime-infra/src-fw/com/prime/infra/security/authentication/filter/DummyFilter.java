 package com.prime.infra.security.authentication.filter;
 
 import com.prime.infra.security.SecurityInfo;
 import java.io.IOException;
 import java.util.Date;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.slf4j.MDC;
 
 public class DummyFilter
   implements Filter
 {
   private String userName;
   private String systemId;
 
   public DummyFilter()
   {
/* 25 */     this.userName = "USER";
/* 26 */     this.systemId = "SYSTEM"; }
 
   public void destroy() {
   }
 
   public String getUserName() {
/* 32 */     return this.userName;
   }
 
   public void setUserName(String userName) {
/* 36 */     this.userName = userName;
   }
 
   public String getSystemId() {
/* 40 */     return this.systemId;
   }
 
   public void setSystemId(String systemId) {
/* 44 */     this.systemId = systemId;
   }
 
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
     throws IOException, ServletException
   {
/* 50 */     SecurityInfo info = new SecurityInfo(getUserName(), request.getRemoteAddr(), getSystemId(), new Date());
 
/* 52 */     HttpSession session = ((HttpServletRequest)request).getSession();
/* 53 */     session.setAttribute("com.prime.infra.authentication.security-info", info);
 
/* 55 */     MDC.put("user", getUserName());
     try {
/* 57 */       filterChain.doFilter(request, response);
     }
     finally {
/* 60 */       MDC.remove("user");
     }
   }
 
   public void init(FilterConfig arg0)
     throws ServletException
   {
   }
 }