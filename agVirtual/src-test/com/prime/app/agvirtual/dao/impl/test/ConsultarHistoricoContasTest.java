package com.prime.app.agvirtual.dao.impl.test;

import java.util.ArrayList;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.prime.app.agvirtual.dao.impl.ContaDaoImpl;
import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.Conta;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.app.test.util.PropsUtil;

public class ConsultarHistoricoContasTest extends ContaDaoImpl {
	public Properties props = new PropsUtil().load("/testcase.properties");
	public SessionFactory sf = new AnnotationConfiguration().configure("/hibernate.cfg.xml").buildSessionFactory();
	public Session session = null;
	ImovelDaoImplCase imovelDAO=null;
	
	@Before
	public void setUp() throws Exception {		
		this.session = sf.openSession();
		imovelDAO = new ImovelDaoImplCase();
	}
	
	@After
	public void tearDown() throws Exception {
		this.session.close();
	}
	
	@Override
	protected Session getHibernateSession() {
		return this.session;
	}
	
	@Test
	public void testPesquisaHistoricoContasRolEspecial() {
		Assert.assertNotNull(findBy(props.getProperty("rgi.com.contas.em.aberto.rol.especial")));
	}
	
	@Test
	public void testPesquisaHistoricoContasRolEspecialInvalido() {		
		Assert.assertNull(findBy(props.getProperty("rgi.invalido")));				
	}
	
	@Test
	public void testPesquisaHistoricoContasEmAberto() {		
		Assert.assertNotNull(findContasEmAbertoBy(props.getProperty("rgi.com.contas.em.aberto")));				
	}
	
	@Test
	public void testPesquisaHistoricoContasEmAbertoInvalido() {		
		Assert.assertNull(findContasEmAbertoBy(props.getProperty("rgi.invalido")));				
	}

	private ArrayList<Conta> findBy(String dsRGI) {
		Imovel imovel = imovelDAO.findImovelByRGI(new Imovel(dsRGI));		
		DadosImoveisTO dados = new DadosImoveisTO();
		dados.setImovel(imovel);
		ArrayList<Conta> lista = null;
		try {
			lista = pesquisaHistoricoContasRolEspecial(dados);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (lista!=null && lista.size()==0) lista=null;
		return lista;
	}
	
	private ArrayList<Conta> findContasEmAbertoBy(String dsRGI) {
		Imovel imovel = imovelDAO.findImovelByRGI(new Imovel(dsRGI));		
		DadosImoveisTO dados = new DadosImoveisTO();
		dados.setImovel(imovel);
		ArrayList<Conta> lista = null;
		try {
			lista = pesquisaContasEmAberto(dados);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (lista!=null && lista.size()==0) lista=null;
		return lista;
	}
	

}
