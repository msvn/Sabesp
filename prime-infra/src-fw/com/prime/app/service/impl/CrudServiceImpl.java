 package com.prime.app.service.impl;
 
 import com.prime.app.service.CrudService;
 import com.prime.infra.dao.jpa.CrudJpaDao;
 import com.prime.infra.dao.jpa.CrudJpaDaoImpl;
 import java.io.Serializable;
 import java.lang.reflect.Constructor;
 import java.util.List;
 import javax.persistence.EntityManager;
 import javax.persistence.PersistenceContext;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
 @Service
 public class CrudServiceImpl
   implements CrudService
 {
/* 24 */   protected final Logger logger = LoggerFactory.getLogger(CrudServiceImpl.class);
   private CrudJpaDao dao;
 
   @PersistenceContext
   public void setEntityManager(EntityManager em)
   {
/* 33 */     if ((true) && (em == null)) throw new AssertionError("EntityManager cannot be null");
     try
     {
/* 36 */       this.dao = ((CrudJpaDao)CrudJpaDaoImpl.class.getConstructor(new Class[] { EntityManager.class }).newInstance(new Object[] { em }));
     }
     catch (Exception ex) {
/* 39 */       this.logger.error("Could not create an instance of " + CrudJpaDaoImpl.class.getName() + " verify if EntityManager is ok.");
 
/* 41 */       throw new IllegalStateException(ex);
     }
   }
 
   @Transactional(readOnly=true)
   public <T> List<T> findAll(Class<T> entityType, int maxResults)
   {
/* 48 */     return this.dao.findAll(entityType, maxResults);
   }
 
   @Transactional(readOnly=false)
   public <T, ID extends Serializable> T get(Class<T> entityType, ID id, boolean lock)
   {
/* 54 */     if ((true) && (id == null)) throw new AssertionError();
 
/* 56 */     return this.dao.findById(entityType, id, lock);
   }
 
   @Transactional(readOnly=false)
   public <T, ID extends Serializable> void remove(Class<T> entityType, ID primaryKey) {
/* 61 */     if ((true) && (primaryKey == null)) throw new AssertionError();
 
/* 63 */     this.dao.remove(entityType, primaryKey);
   }
 
   @Transactional(readOnly=false)
   public <T> T save(T entity) {
/* 68 */     if ((true) && (entity == null)) throw new AssertionError();
 
/* 70 */     return this.dao.persist(entity);
   }
 }