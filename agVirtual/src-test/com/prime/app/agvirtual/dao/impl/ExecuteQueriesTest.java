package com.prime.app.agvirtual.dao.impl;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.prime.app.agvirtual.entity.JPASupportTest;

public class ExecuteQueriesTest extends JPASupportTest{
	
	private EntityManager localEm;
	
	@Before
	public void setUp() throws Exception {
		localEm = getEntityManager();
	}
	
	@Test public void executeQuery(){
//		select
//		pri.cd_conjunto_resposta
//		from 
//		agv_tab_integ_perg_resp pri
//		where
//		pri.cd_pergunta_resposta IN (1 ,3)
//		group by
//		pri.cd_conjunto_resposta
//		having 
//		count(pri.cd_conjunto_resposta) > 1	

		String queryString = "select cd_conjunto_resposta from agv_tab_integ_perg_resp where " +
				" cd_pergunta_resposta IN (1 ,3) group by cd_conjunto_resposta having " +
				" count(cd_conjunto_resposta) > 1";
	
		Object r = localEm.createNativeQuery(queryString).getSingleResult();
//		Object r = localEm.createQuery(queryString).getSingleResult();
		
		System.out.println(r);
	}
}
