package com.prime.app.agvirtual.dao.impl.test;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.prime.app.agvirtual.dao.impl.CsiDaoImpl;
import com.prime.app.agvirtual.to.TarifaTO;
import com.prime.app.test.util.PropsUtil;

public class ConsultarTabelaPrecos extends CsiDaoImpl{
	public Properties props = new PropsUtil().load("/testcase.properties");
	public SessionFactory sf = new AnnotationConfiguration().configure("/hibernate.cfg.xml").buildSessionFactory();
	public Session session = null;
	ImovelDaoImplCase imovelDAO=null;
	
	@Before
	public void setUp() throws Exception {		
		this.session = sf.openSession();
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
	public void testFindTarifaByParams() {	
		TarifaTO tarifaTO = findTarifaByParams("40","1","10","1");		
		Assert.assertNotNull(tarifaTO);				
	}
}
