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
import com.prime.app.agvirtual.entity.Conta;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.test.util.PropsUtil;

public class ConsultarContasEmAbertoTest  extends ContaDaoImpl  {
	public Properties props = new PropsUtil().load("/testcase.properties");
	public SessionFactory sf = new AnnotationConfiguration().configure("/hibernate.cfg.xml").buildSessionFactory();
	public Session session = null;
	
	@Before
	public void setUp() throws Exception {		
		this.session = sf.openSession();
	}
	
	@After
	public void tearDown() throws Exception {
		this.session.close();
	}
	
	@Test
	public void testBuscaContasEmAbertoByRGI() {
		Assert.assertNotNull(findContasEmAbertoByRGI(props.getProperty("rgi.com.contas.em.aberto")));
	}
	
	@Test
	public void testBuscaContasEmAbertoByRGIInvalido() {		
		Assert.assertNull(findContasEmAbertoByRGI(props.getProperty("rgi.invalido")));				
	}
	
	private ArrayList<Conta> findContasEmAbertoByRGI(String dsRGI) {
		ArrayList<Conta> lista = findContasEmAberto(new Imovel(dsRGI));
		if (lista!=null && lista.size()==0) lista=null;
		return lista;
	}

	@Override
	protected Session getHibernateSession() {
		return this.session;
	}
	
	public static void main(String[] args) {
		ConsultarContasEmAbertoTest t = new ConsultarContasEmAbertoTest();
		t.session = t.sf.openSession();
		ArrayList<Conta> lista = t.findContasEmAberto(new Imovel("2720"));
		System.out.println();
		t.session.close();
	}

}
