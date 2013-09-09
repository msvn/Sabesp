package com.prime.app.agvirtual.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.prime.app.agvirtual.entity.AutoAtendimentoAcessado;
import com.prime.app.agvirtual.entity.SituacaoAtendimento;
import com.prime.app.agvirtual.entity.Atendimento;
import com.prime.app.agvirtual.entity.AgvTabAutoatendimento;
import com.prime.app.agvirtual.entity.MotivoInsucesso;
import com.prime.app.agvirtual.entity.JPASupportTest;

public class AgvTabAutoatendimentoAcessDaoImplTest extends JPASupportTest{

	private AutoAtendimentoAcessadoDaoImpl aaAcessadoDao;
	
	Long id = 1L;	
	
	@Before
	public void setUp() throws Exception {
		aaAcessadoDao = new AutoAtendimentoAcessadoDaoImpl();
		aaAcessadoDao.setEntityManager(getEntityManager());
	}

	@Test
	public void testFindAll() {
		List<AutoAtendimentoAcessado> result = aaAcessadoDao.findAll();
		assertEquals(1, result.size());
		
	}
	
	@Test
	@Ignore
	public void testFindById() {
		AutoAtendimentoAcessado aaServico = aaAcessadoDao.findById(id, Boolean.FALSE);
		assertNotNull(aaServico);
	}

	// other
	@Test
	public void testQuery(){
		try{
//			String queryString = "select o.cdRgi, o.socilicitante from AutoAtendimentoAcessado o WHERE o.atendimento.cdAtendimento=1 GROUP BY o.cdRgi,o.socilicitante";
			String queryString = "select o.cdRgi, o.socilicitante from AutoAtendimentoAcessado o WHERE o.atendimento.cdAtendimento= ?1 GROUP BY o.cdRgi, o.socilicitante";
			
			EntityManager em = getEntityManager();
			
//			String queryString = "select o.cdRgi, o.socilicitante from AutoAtendimentoAcessado o WHERE o.atendimento= ?1 GROUP BY o.cdRgi,o.socilicitante"; //where o.atendimento = " + 1 + " GROUP BY o.cdRgi";
			Query query = em.createQuery(queryString);
			query.setParameter(1, 1L);
			
			List result = query.getResultList();
			
//			RelatorioAutoAtendimentoAcessadoTO2 to = null;
//			for(Iterator i = result.iterator();i.hasNext();){
//				to = (RelatorioAutoAtendimentoAcessadoTO2)i.next();
//			}
						
			
			System.out.println(result);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	// end
	
	
	
	@Test
	@Ignore
	public void testRemoveAll(){
		try{
			EntityManager em = getEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.createQuery("DELETE from AutoAtendimentoAcessado").executeUpdate();
			em.flush();
			tx.commit();
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	@Test
	public void testPersist() {
		AutoAtendimentoAcessado aaAcessado = createAAAcessado();
		
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(aaAcessado);
		em.flush();
		tx.commit();
		
		assertTrue(verifyPersistence(aaAcessado.getCdAutoAtendimentoAcess()));
	}
	
	private boolean verifyPersistence(Long cdAutoAtendimentoAcess){
		AutoAtendimentoAcessado o = aaAcessadoDao.findById(cdAutoAtendimentoAcess, Boolean.FALSE);
		return (o!=null);
	}
	
	private AutoAtendimentoAcessado createAAAcessado(){
		AgvTabAutoatendimento autoAtendimento = getEntityManager().find(AgvTabAutoatendimento.class, 1L);
		Atendimento atendimento = getEntityManager().find(Atendimento.class, 1L);
		
		AutoAtendimentoAcessado aaAcessado = new AutoAtendimentoAcessado();
		
		aaAcessado.setAtendimento(atendimento);
		aaAcessado.setAutoAtendimento(autoAtendimento);
		aaAcessado.setCdAtendComercial(1L);
		aaAcessado.setMotivoInsucesso(new MotivoInsucesso(1L, "xpto"));
		aaAcessado.setCdMunicipio(1);
		aaAcessado.setSituacaoAtendimento(new SituacaoAtendimento(1L,"xpto"));
		aaAcessado.setCdRgi(999999L);
		aaAcessado.setSocilicitante("Rodrigo");
		
		return aaAcessado;
	}

}
