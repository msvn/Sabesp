package com.prime.app.agvirtual.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.prime.app.agvirtual.dao.impl.AgvTabItemMenuAcessoDaoImpl;
import com.prime.app.agvirtual.dao.impl.AtendimentoDaoImpl;
import com.prime.app.agvirtual.dao.impl.LogAcessoDaoImpl;
import com.prime.app.agvirtual.entity.AgvTabItemMenu;
import com.prime.app.agvirtual.entity.Atendimento;
import com.prime.app.agvirtual.entity.JPASupportTest;
import com.prime.app.agvirtual.entity.LogAcesso;

public class AtendimentoDaoTest extends JPASupportTest{

	private LogAcessoDaoImpl logAcessoDao;
	private AgvTabItemMenuAcessoDaoImpl itemMenuAcessoDao;
	private AtendimentoDaoImpl atendimentoDao;
	
	String cdSessao = "4D6A8C3AA6C93F1E8BCAC17EF124AFF3";
	
	@Before
	public void setUp() throws Exception {
		logAcessoDao = new LogAcessoDaoImpl();
		logAcessoDao.setEntityManager(getEntityManager());
		
		itemMenuAcessoDao = new AgvTabItemMenuAcessoDaoImpl();
		itemMenuAcessoDao.setEntityManager(getEntityManager());
		
		atendimentoDao = new AtendimentoDaoImpl();
		atendimentoDao.setEntityManager(getEntityManager());	
	}

	@Test
	public void testFindById() {
		Atendimento a = atendimentoDao.findById(1L);
		Assert.assertNotNull(a);
	}

	@Test
	public void testFindAll() {
		List<Atendimento> atendimentos = atendimentoDao.findAll();
		Assert.assertNotNull(atendimentos);
	}

	@Test
	public void testPersistAtendimentoELogAcesso() {
		Atendimento atendimento = criarAtendimentoBase();
		LogAcesso logAcesso = criaLogAcessoBase(atendimento);
		atendimento.setListaLog(criaListaLogAcesso(logAcesso));
		
		int qdeAtendimentosNaBase = atendimentoDao.findAll().size();
		
		atendimento = atendimentoDao.persist(atendimento);
		flush(getEntityManager());
		
		// valida persistencia apenas do atendimento
		int expected = qdeAtendimentosNaBase+1;
		int actual = atendimentoDao.findAll().size();
		Assert.assertEquals(expected, actual);
		
	}

	@Test
	public void testFindByCodSessao() {
		Atendimento at = atendimentoDao.findByCodSessao(cdSessao);
		Assert.assertNotNull(at);
	}
	
	@Test
	public void testQuery(){
		List result = getEntityManager().createQuery("from Atendimento").getResultList();
		Assert.assertNotNull(result);
		
	}
	
	// carrega dados 	
	private Atendimento criarAtendimentoBase(){
		Atendimento atendimento = new Atendimento();
		atendimento.setCdSessao("4D6A8C3AA6C93F1E8BCAC17EF124AFF3");
		atendimento.setCdStaAtendimento(1L);
		atendimento.setDtInclusao(new Date());
		atendimento.setNrIp("123.123.123.123");
		
		return atendimento;
	}
	
	private List<LogAcesso> criaListaLogAcesso(Object... objs){
		ArrayList retorno = new ArrayList();
		
		for(int i=0;i<objs.length;i++){
			retorno.add(objs[i]);
		}
		
		return retorno;
	}
	
	private LogAcesso criaLogAcessoBase(Atendimento atendimento){
		LogAcesso log = new LogAcesso();
		log.setDataAcesso(new Date());
		log.setDescricaoUsuario("Cambra");
		log.setItemMenu(itemMenuAcessoDao.findById(14L, Boolean.FALSE));
		log.setAtendimento(atendimento);
		
		return log;
	}

}
