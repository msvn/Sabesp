package com.prime.app.agvirtual.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.prime.app.agvirtual.entity.AutoAtendimentoPergResp;
import com.prime.app.agvirtual.entity.PerguntaAutoAtendimento;
import com.prime.app.agvirtual.entity.JPASupportTest;

public class PerguntaAutoAtendimentoDaoImplTest extends JPASupportTest{
	
	private PerguntaAutoAtendimentoDaoImpl dao; 
	
	@Before
	public void before(){
		dao = new PerguntaAutoAtendimentoDaoImpl();
		dao.setEntityManager(getEntityManager());
	}
	
	@Test
	@Ignore
	public void testFindAll() {
		List<PerguntaAutoAtendimento> perguntas = dao.findAll(100);
		
		print(perguntas);
		
		// test PerguntaAutoAtendimento
		int expected = 2;		
		assertEquals(expected,perguntas.size());
		
	}
	
	@Test
	public void findById(){
		PerguntaAutoAtendimento pergunta = dao.findById(1);
		print(pergunta, 1);
		
		assertNotNull(pergunta);
	}
	
	@Test
	@Ignore
	public void test(){
		String sql = "select o from AgvTabPergAutoAtendimento o";
		
		List result = getEntityManager().createQuery(sql).setMaxResults(20).getResultList();
		int expected = 2;
		
		assertEquals(expected, result.size());
		
		
	}
	
	private void print(List<PerguntaAutoAtendimento> items){
		System.out.println("####### Items ########");
		int i = 1;
		for(PerguntaAutoAtendimento o : items){
			print(o, i);
		}
		System.out.println("####### fim items ########");
	}
	private void print(PerguntaAutoAtendimento item, int position){
		System.out.println("Item: " + position + " - Content: " + ToStringBuilder.reflectionToString(item));
	}	
	
	

}
