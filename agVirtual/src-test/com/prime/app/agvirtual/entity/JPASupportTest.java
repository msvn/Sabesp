package com.prime.app.agvirtual.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPASupportTest {
	private static EntityManagerFactory emf;
	private EntityManager localEntityManager;
	
	static{
		emf = Persistence.createEntityManagerFactory("manager1");
	}
	
	protected EntityManager getEntityManager(){
		if(localEntityManager==null) localEntityManager = emf.createEntityManager();
		return localEntityManager;
	}
	
	protected void persist(Object entity){
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(entity);
		em.flush();
		tx.commit();
	}
	
	protected void flush(EntityManager em){
		localEntityManager.getTransaction().begin();
		localEntityManager.flush();
		localEntityManager.getTransaction().commit();
	}
	
	
}
