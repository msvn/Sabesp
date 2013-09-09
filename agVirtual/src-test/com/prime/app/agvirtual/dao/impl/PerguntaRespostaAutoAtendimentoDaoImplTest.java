package com.prime.app.agvirtual.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.Query;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.prime.app.agvirtual.entity.ConjuntoRespostaAutoAtendimento;
import com.prime.app.agvirtual.entity.JPASupportTest;
import com.prime.app.agvirtual.entity.PerguntaRespostaAutoAtendimento;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;

public class PerguntaRespostaAutoAtendimentoDaoImplTest extends JPASupportTest{

	private PerguntaRespostaAutoAtendimentoDaoImpl dao;
	
	@Before
	public void before(){
		dao = new PerguntaRespostaAutoAtendimentoDaoImpl();
		dao.setEntityManager(getEntityManager());
	}	
	
	
	@Test
	public void testFindByPerguntaIdRespostaId() {
		int cdPergunta = 1;
		int cdResposta = 1;
		int numConjuntos = 3;
		
		PerguntaRespostaAutoAtendimento pergResp = null;
		try {
			pergResp = dao.findByPerguntaIdRespostaId(cdPergunta, cdResposta);
		} catch (NotFoundBusinessException e) {
			fail(e.getMessage());
		}
		assertNotNull(pergResp);
		
		List<ConjuntoRespostaAutoAtendimento> listConjResp = pergResp.getListaConjuntoResposta();
		assertNotNull(listConjResp);
		
		assertEquals(numConjuntos, listConjResp.size());
		
		System.out.println(pergResp.getCdPerguntaResposta());
	}

	@Test
	public void testFindByIdInt() {
		int codPergResp  = 1;
		int numConjuntos = 3; 
		
		PerguntaRespostaAutoAtendimento pergResp = dao.findById(codPergResp);
		assertNotNull(pergResp);
		
		List<ConjuntoRespostaAutoAtendimento> listConjResp = pergResp.getListaConjuntoResposta();
		assertNotNull(listConjResp);
		
		assertEquals(numConjuntos, listConjResp.size());		
	}
	
	@Test
	@Ignore
	public void myTest(){
		try{
		Query query = getEntityManager().createQuery("select o from PerguntaRespostaAutoAtendimento o where o.pergunta=?1 and o.resposta=?2");
		query.setParameter(1, 1);
		query.setParameter(2, 1);
		
		List r = query.getResultList();
		
		System.out.println(r.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
