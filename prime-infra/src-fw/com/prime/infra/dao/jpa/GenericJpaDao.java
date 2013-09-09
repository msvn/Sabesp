 package com.prime.infra.dao.jpa;
 
 import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
 
 public abstract class GenericJpaDao<T, ID extends Serializable>
 {
   private Class<T> entityBeanType;
   protected EntityManager em;
 
   public GenericJpaDao()
   {
	   try{
/*  29 */     Type genericSuperclass = super.getClass().getGenericSuperclass();
/*  30 */     ParameterizedType type = (ParameterizedType)genericSuperclass;
/*  31 */     this.entityBeanType = ((Class)type.getActualTypeArguments()[0]);
   	   }catch (Exception e) {
             e.printStackTrace();
   	   }
   }
 
   @PersistenceContext
   public void setEntityManager(EntityManager em)
   {
/*  42 */     this.em = em;
   }
 
   protected EntityManager getEntityManager() {
/*  46 */     if (this.em == null) {
/*  47 */       throw new IllegalStateException("EntityManager has not been set on DAO before usage");
     }
 
/*  50 */     return this.em;
   }
 
   protected Class<T> getEntityBeanType() {
/*  54 */     return this.entityBeanType;
   }
 
   public T findById(ID id, boolean lock)
   {
     Object entity;
/*  67 */     if (lock) {
/*  68 */       entity = getEntityManager().find(getEntityBeanType(), id);
/*  69 */       getEntityManager().lock(entity, LockModeType.WRITE);
     } else {
/*  71 */       entity = getEntityManager().find(getEntityBeanType(), id);
     }
/*  73 */     return (T)entity;
   }
 
   public List<T> findAll(int maxResults)
   {
/*  85 */     return getEntityManager().createQuery("from " + getEntityBeanType().getName()).setMaxResults(maxResults).getResultList();
   }
 
   protected List<T> findByQuery(String queryString, Object... params)
   {
/* 101 */     Query query = getEntityManager().createQuery(queryString);
/* 102 */     return fillInQueryParameters(query, params).getResultList();
   }
   protected List<T> findByQuery(String queryString)
   {
/* 101 */     Query query = getEntityManager().createQuery(queryString);
              Object param = null;
/* 102 */     return fillInQueryParameters(query, param).getResultList();
   }

   protected T findOneByQuery(String queryString, Object... params)
   {
/* 116 */     Query query = getEntityManager().createQuery(queryString);
/* 117 */     return (T)fillInQueryParameters(query, params).getSingleResult();
   }
 
   protected List<T> findByQueryWraper(QueryWrapper queryWrapper, Object[] params)
   {
/* 133 */     Query query = getEntityManager().createQuery(queryWrapper.getQueryString());
/* 134 */     if (params != null) {
/* 135 */       for (int i = 0; i < params.length; ++i) {
/* 136 */         queryWrapper.setParam(i + 1, query, params[i]);
       }
     }
/* 139 */     return query.getResultList();
   }
 
   public T persist(T entity)
   {
/* 150 */     return getEntityManager().merge(entity);
   }
 
   public void remove(T entity)
   {
/* 160 */     getEntityManager().remove(entity);
   }
 
   protected Query fillInQueryParameters(Query query, Object... params)
   {
/* 173 */     if (params != null) {
/* 174 */       for (int i = 0; i < params.length; ++i) {
				  if (params[i] != null) {
/* 175 */              query.setParameter(i + 1, params[i]);
                  }
       }
     }
/* 178 */     return query;
   }
 
   public static abstract interface QueryWrapper
   {
     public abstract String getQueryString();
 
     public abstract void setParam(int paramInt, Query paramQuery, Object paramObject);
   }
 }