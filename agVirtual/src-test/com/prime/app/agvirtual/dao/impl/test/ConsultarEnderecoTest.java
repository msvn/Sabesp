package com.prime.app.agvirtual.dao.impl.test;

import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.prime.app.agvirtual.dao.impl.EnderecoDaoImpl;
import com.prime.app.test.util.PropsUtil;

public class ConsultarEnderecoTest extends EnderecoDaoImpl {
	public Properties props = new PropsUtil().load("/testcase.properties");
	public SessionFactory sf = new AnnotationConfiguration().configure("/hibernate.cfg.xml").buildSessionFactory();
	public Session session = null;
	
	@Before
	public void setUp() throws Exception {		
		this.session = sf.openSession();
	}
	
	@Override
	protected Session getHibernateSession() {
		return this.session;
	}
	
	@Test
	public void testMunicipio(){
		Assert.assertNotNull(findByMunicipio());
	}
	
	private boolean findByMunicipio() {
		List lista = findQBE(" SELECT " +
				" H.NMMUNICIP " +
				" FROM CTB17 H " +
				" WHERE H.CDMUNICIP=?","40");
		return true;

	}

}
