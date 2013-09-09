package com.prime.app.agvirtual.dao.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.prime.app.agvirtual.entity.ConjuntoRespostaAutoAtendimento;
import com.prime.app.agvirtual.entity.JPASupportTest;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;

public class ConjuntoRespotaAutoAtendimentoDaoImplTest extends JPASupportTest{

	private ConjuntoRespotaAutoAtendimentoDaoImpl conjuntoDao;
	
	private List<Integer> codigos;
	
	@Before
	public void setUp() throws Exception {
		conjuntoDao = new ConjuntoRespotaAutoAtendimentoDaoImpl();
		conjuntoDao.setEntityManager(getEntityManager());
		codigos = new ArrayList<Integer>();
	}

	@Test
	@Ignore
	public void testFindByIdLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByCodPerguntasRespostas() throws Exception {
		codigos.add(new Integer(1));  // codigo RerguntaResposta = 1
		codigos.add(new Integer(3));  // codigo RerguntaResposta = 3
		
		List<Object> r = null;
		try {
			r = conjuntoDao.findByCodPerguntasRespostas(codigos,"T");
		} catch (NotFoundBusinessException e) {
			fail(e.getMessage());
		}
		
		assertNotNull(r);
//		assertEquals("1", Long.toString(r.getAcao().getCdAcao())); //FIXME CAMBRA
	}
	
	@Test(expected = NotFoundBusinessException.class)
	public void testFindByCodPerguntasRespostasNotFound() throws Exception{
		codigos.add(new Integer(1));  // codigo RerguntaResposta = 1
		codigos.add(new Integer(2));  // codigo RerguntaResposta = 3	
		
		List<Object> r2 = conjuntoDao.findByCodPerguntasRespostas(codigos,"T");
	}
	
	@Test
	@Ignore
	public void testQuery(){
		String queryString = "select cd_conjunto_resposta from agv_tab_integ_perg_resp where " +
		" cd_pergunta_resposta IN (1,3) group by cd_conjunto_resposta having " +
		" count(cd_conjunto_resposta) > 1";
		
		Query query = getEntityManager().createNativeQuery(queryString);
		
//		String splitedCodPerguntasRespostas = "1,2";
//		query.setParameter(1,splitedCodPerguntasRespostas);
		
		List result = query.getResultList();
//		Object conjuntoRespostaId = query.getSingleResult();	
		
		assertNotNull(result);
	}

}
