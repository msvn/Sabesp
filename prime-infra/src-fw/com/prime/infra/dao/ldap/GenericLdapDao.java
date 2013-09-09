 package com.prime.infra.dao.ldap;
 
 import java.util.ArrayList;
 import java.util.List;
 import javax.naming.directory.SearchControls;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.ldap.control.PagedResult;
 import org.springframework.ldap.control.PagedResultsCookie;
 import org.springframework.ldap.control.PagedResultsRequestControl;
 import org.springframework.ldap.core.simple.ParameterizedContextMapper;
 import org.springframework.ldap.core.simple.SimpleLdapTemplate;
 
 public abstract class GenericLdapDao
 {
   private SimpleLdapTemplate simpleLdapTemplate;
   private Logger logger;
 
   public GenericLdapDao()
   {
/*  24 */     this.logger = LoggerFactory.getLogger(GenericLdapDao.class);
   }
 
   @Autowired
   public void setSimpleLdapTemplate(SimpleLdapTemplate simpleLdapTemplate)
   {
/*  46 */     this.simpleLdapTemplate = simpleLdapTemplate;
   }
 
   protected SimpleLdapTemplate getSimpleLdapTemplate()
   {
/*  53 */     return this.simpleLdapTemplate;
   }
 
   protected <T> List<T> pagedSearch(String filter, ParameterizedContextMapper<?> mapper, int pageSize)
   {
/*  73 */     List resultList = new ArrayList();
 
/*  76 */     this.logger.debug("Perform the first search...");
/*  77 */     PagedResult result = search(filter, mapper, null, pageSize);
 
/*  80 */     this.logger.trace("If there the result.getCookie().getCookie() is not null perform the search again...");
/*  81 */     while ((result.getCookie() != null) && (result.getCookie().getCookie() != null)) {
/*  82 */       result = search(filter, mapper, result.getCookie(), pageSize);
/*  83 */       resultList.addAll(result.getResultList());
/*  84 */       if ((this.logger.isTraceEnabled()) && (resultList.size() % pageSize * 10 == 0));
/*  85 */       this.logger.trace("Search paged reached: " + resultList.size() + " results.");
     }
 
/*  88 */     return resultList;
   }
 
   protected PagedResult search(String filter, ParameterizedContextMapper<?> mapper, PagedResultsCookie cookie, int pageSize)
   {
/* 108 */     PagedResultsRequestControl control = new PagedResultsRequestControl(pageSize, cookie);
 
/* 110 */     SearchControls searchControls = new SearchControls();
/* 111 */     searchControls.setSearchScope(2);
/* 112 */     searchControls.setCountLimit(pageSize);
 
/* 114 */     this.logger.trace("Performing ldap search...");
/* 115 */     List resultList = getSimpleLdapTemplate().search("", filter, searchControls, mapper, control);
 
/* 119 */     return new PagedResult(resultList, control.getCookie());
   }
 }