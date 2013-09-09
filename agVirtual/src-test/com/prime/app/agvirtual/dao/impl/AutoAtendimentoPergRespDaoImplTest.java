package com.prime.app.agvirtual.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.prime.app.agvirtual.entity.AutoAtendimentoPergResp;
import com.prime.app.agvirtual.entity.JPASupportTest;
import com.prime.app.agvirtual.entity.PerguntaAutoAtendimento;
import com.prime.app.agvirtual.entity.RespostaAutoAtendimento;

public class AutoAtendimentoPergRespDaoImplTest extends JPASupportTest{

	AutoAtendimentoPergRespDaoImpl dao;
	
	@Before
	public void before(){
		dao = new AutoAtendimentoPergRespDaoImpl();
		dao.setEntityManager(getEntityManager());
	}
	
	@Test
	public void testFindById() {
		AutoAtendimentoPergResp aa = dao.findById(1L,false);
		
		System.out.println(aa.getCdAutoatendimento());
		System.out.println(aa.getDsAutoatendimento());
		
		assertNotNull(aa);
	}
	
	@Test
	@Ignore
	public void testFindByIdPerguntasRespostas(){
		Long cdAAtendimento = 1L;
		int expNumPerguntas = 2;
		int expNumRespostas = 2;
		
		AutoAtendimentoPergResp aa = dao.findById(cdAAtendimento,false);
		
		assertNotNull(aa);
		
		List<PerguntaAutoAtendimento> perguntas = aa.getListaPergAutoAtendimentoTodasDiretorias();
		assertNotNull(perguntas);
		assertEquals(expNumPerguntas, perguntas.size());
	
		// test resp perg 1
		List<RespostaAutoAtendimento> respostas = perguntas.get(0).getListaRespostaAutoAtendimento();
		assertNotNull(respostas);
		assertEquals(expNumRespostas, respostas.size());
	}

}
