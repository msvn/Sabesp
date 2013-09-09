 package com.prime.infra.dao.jpa;
 
 import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.prime.infra.config.RunningMode;
 
 public abstract class GenericHibernateJpaDao<T, ID extends Serializable> extends GenericJpaDao<T, ID>
 {
   protected List<T> findByExample(T exampleInstance, String[] excludeProperty)
   {
/*  33 */     Criteria crit = getHibernateSession().createCriteria(getEntityBeanType());
/*  34 */     Example example = Example.create(exampleInstance);
/*  35 */     return fillInExclusions(crit, example, excludeProperty).list();
   }
 
   protected List<T> findByCriteria(Criterion[] criterion)
   {
/*  47 */     Session session = getHibernateSession();
/*  48 */     Criteria crit = session.createCriteria(getEntityBeanType());
/*  49 */     return fillInCriterions(crit, criterion).list();
   }
 
   protected T findOneByCriteria(Criterion[] criterion)
   {
/*  61 */     Session session = getHibernateSession();
/*  62 */     Criteria crit = session.createCriteria(getEntityBeanType());
/*  63 */     return (T)fillInCriterions(crit, criterion).uniqueResult();
   }
 
   protected T findOneByExample(T exampleInstance, String[] excludeProperty)
   {
/*  76 */     Criteria crit = getHibernateSession().createCriteria(getEntityBeanType());
/*  77 */     Example example = Example.create(exampleInstance);
/*  78 */     return (T)fillInExclusions(crit, example, excludeProperty).uniqueResult();
   }
 
   protected Criteria fillInCriterions(Criteria criteria, Criterion[] _criteria)
   {
/*  89 */     for (Criterion c : _criteria) {
/*  90 */       criteria.add(c);
     }
/*  92 */     return criteria;
   }
 
   protected Criteria fillInExclusions(Criteria criteria, Example example, String[] excludedProperties)
   {
/* 103 */     for (String exclude : excludedProperties) {
/* 104 */       example.excludeProperty(exclude);
     }
/* 106 */     criteria.add(example);
/* 107 */     return criteria;
   }
 
   protected <R> List<R> findUsingCallback(HibernateCallback callback)
   {
     try
     {
    	 return ((List)callback.doInHibernate(getHibernateSession()));
     }
     catch (SQLException ex) {
    	 throw new RuntimeException(ex);
     }
   }
 
   protected <R> R findOneUsingCallback(HibernateCallback callback)
   {
     try
     {
    	 return (R)callback.doInHibernate(getHibernateSession());
     }
     catch (SQLException ex) {
    	 throw new RuntimeException(ex);
     }
   }
 
   protected Session getHibernateSession()
   {
//	   if (RunningMode.get().contains("junit")){
//		   return null;
//	   }   	
       return ((Session)getEntityManager().getDelegate());
   }
 }