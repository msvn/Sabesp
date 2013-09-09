 package com.prime.infra.dao.jpa;
 
 import java.io.Serializable;
 import java.util.List;
 import javax.persistence.EntityManager;
 import javax.persistence.LockModeType;
 import javax.persistence.Query;
 import org.hibernate.CacheMode;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.Example;
 import org.hibernate.ejb.HibernateEntityManager;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public final class CrudJpaDaoImpl
   implements CrudJpaDao
 {
/*  19 */   private final Logger logger = LoggerFactory.getLogger(CrudJpaDaoImpl.class);
   private EntityManager em;
 
   public CrudJpaDaoImpl(EntityManager em)
   {
/*  24 */     if ((true) && (em == null)) throw new AssertionError("EntityManager cannot be null");
/*  25 */     this.em = em;
   }
 
   protected EntityManager getEntityManager() {
/*  29 */     if ((true) && (this.em == null)) throw new AssertionError("EntityManager has not been set on DAO before usage");
/*  30 */     return this.em;
   }
 
   public <T> T findById(Class<T> entityType, Serializable id, boolean lock)
   {
     Object entity;
/*  35 */     if (this.logger.isDebugEnabled()) {
/*  36 */       this.logger.debug("Finding entity using " + id);
     }
 
/*  40 */     if (lock) {
/*  41 */       entity = getEntityManager().find(entityType, id);
/*  42 */       getEntityManager().lock(entity, LockModeType.WRITE);
     }
     else {
/*  45 */       entity = getEntityManager().find(entityType, id);
     }
 
/*  48 */     if (this.logger.isDebugEnabled()) {
/*  49 */       this.logger.debug("Entity Found = " + entity);
     }
 
/*  52 */     return (T)entity;
   }
 
   public <T> List<T> findAll(Class<?> entityType, int maxResults)
   {
/*  57 */     if (this.logger.isDebugEnabled()) {
/*  58 */       this.logger.debug("Finding all entities of type " + entityType.getName());
     }
 
/*  61 */     List results = getEntityManager().createQuery("from " + entityType.getName()).setMaxResults(maxResults).getResultList();
 
/*  64 */     if (this.logger.isDebugEnabled()) {
/*  65 */       this.logger.debug("Entities found using no criteria for the entity " + entityType.getName());
     }
 
/*  68 */     return results;
   }
 
   protected <T> List<T> findByExample(Class<?> entityType, T exampleInstance, String[] excludeProperty)
   {
/*  73 */     if (this.logger.isDebugEnabled()) {
/*  74 */       this.logger.debug("Finding by example for entity" + entityType.getName() + " using the following criteria " + exampleInstance + " and excluding " + excludeProperty);
     }
 
/*  79 */     Criteria crit = ((HibernateEntityManager)getEntityManager()).getSession().createCriteria(entityType);
 
/*  81 */     Example example = Example.create(exampleInstance);
 
/*  83 */     for (String exclude : excludeProperty) {
/*  84 */       example.excludeProperty(exclude);
     }
/*  86 */     crit.add(example);
/*  87 */     crit.setCacheable(true);
/*  88 */     crit.setCacheMode(CacheMode.NORMAL);
/*  89 */     List results = crit.list();
 
/*  91 */     if (this.logger.isDebugEnabled()) {
/*  92 */       this.logger.debug("Entities found using the following type " + entityType.getName() + " with result = " + results);
     }
 
/*  96 */     return results;
   }
 
   protected <T> List<T> findByCriteria(Class<?> entityType, Criterion[] criterion)
   {
/* 101 */     if (this.logger.isDebugEnabled()) {
/* 102 */       this.logger.debug("Finding entities by criteria for entity " + entityType.getName() + " using the following criteria " + criterion);
     }
 
/* 107 */     Session session = ((HibernateEntityManager)getEntityManager()).getSession();
/* 108 */     Criteria crit = session.createCriteria(entityType);
/* 109 */     for (Criterion c : criterion) {
/* 110 */       crit.add(c);
     }
 
/* 113 */     List results = crit.list();
 
/* 115 */     if (this.logger.isDebugEnabled()) {
/* 116 */       this.logger.debug("Finding entities by criteria for entity " + entityType.getName() + " with results = " + results);
     }
 
/* 119 */     return results;
   }
 
   protected <T> List<T> findByQuery(Class<?> entityType, String queryString, Object[] params)
   {
/* 124 */     if (this.logger.isDebugEnabled()) {
/* 125 */       this.logger.debug("Finding all entities of type " + entityType.getName() + " using the following query " + queryString + " and the following parameters " + params);
     }
 
/* 129 */     Query query = this.em.createQuery(queryString);
/* 130 */     if (params != null) {
/* 131 */       for (int i = 0; i < params.length; ++i) {
/* 132 */         query.setParameter(i + 1, params[i]);
       }
     }
 
/* 136 */     return query.getResultList();
   }
 
   protected <T> List<T> findByQueryWraper(QueryWrapper queryWrapper, Object[] params)
   {
/* 141 */     Query query = getEntityManager().createQuery(queryWrapper.getQueryString());
/* 142 */     if (params != null) {
/* 143 */       for (int i = 0; i < params.length; ++i) {
/* 144 */         queryWrapper.setParam(i + 1, query, params[i]);
       }
     }
/* 147 */     return query.getResultList();
   }
 
   public <T> T persist(T entity)
   {
/* 156 */     if (this.logger.isDebugEnabled()) {
/* 157 */       this.logger.debug("Saving instance " + entity);
     }
 
/* 160 */     Object entityUpdated = getEntityManager().merge(entity);
 
/* 162 */     if (this.logger.isDebugEnabled()) {
/* 163 */       this.logger.debug("Entity saved " + entityUpdated);
     }
 
/* 166 */     return (T)entityUpdated;
   }
 
   public <T> void remove(Class<?> entityType, Serializable id)
   {
/* 176 */     if (this.logger.isDebugEnabled()) {
/* 177 */       this.logger.debug("Removing instance with primary key " + id);
     }
 
/* 180 */     Object stored = getEntityManager().getReference(entityType, id);
 
/* 182 */     if (this.logger.isDebugEnabled()) {
/* 183 */       this.logger.debug("Removing instance " + stored);
     }
 
/* 186 */     getEntityManager().remove(stored);
 
/* 188 */     if (this.logger.isDebugEnabled())
/* 189 */       this.logger.debug("Entity " + stored + " removed");
   }
 
   protected static abstract interface QueryWrapper<T>
   {
     public abstract String getQueryString();
 
     public abstract void setParam(int paramInt, Query paramQuery, Object paramObject);
   }
 }