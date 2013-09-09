package com.prime.app.agvirtual.dao;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.prime.app.agvirtual.dao.impl.AgvTabItemMenuAcessoDaoImpl;
import com.prime.app.agvirtual.dao.impl.AtendimentoDaoImpl;
import com.prime.app.agvirtual.dao.impl.LogAcessoDaoImpl;
import com.prime.app.agvirtual.entity.AgvTabItemMenu;
import com.prime.app.agvirtual.entity.AgvTabItemMenuAcesso;
import com.prime.app.agvirtual.entity.JPASupportTest;
import com.prime.app.agvirtual.entity.LogAcesso;

public class LogAcessoDaoTest extends JPASupportTest{

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
	public void testPersist() {
		int expected = logAcessoDao.findAll(99).size() + 1;
		
		LogAcesso logAcesso = new LogAcesso();
		logAcesso.setDataAcesso(new Date());
		logAcesso.setDescricaoUsuario("Cambra");
		logAcesso.setItemMenu(itemMenuAcessoDao.findById(13L, Boolean.FALSE));
		logAcesso.setAtendimento(atendimentoDao.findByCodSessao(cdSessao));
		
		logAcesso = logAcessoDao.persist(logAcesso);				
		flush(getEntityManager());
		
		int actual = logAcessoDao.findAll(99).size();
		Assert.assertEquals(expected, actual);
	}
	

}
