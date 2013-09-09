package com.prime.app.agvirtual.dao.impl.test;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.prime.app.agvirtual.dao.impl.AgvTabSubservicoDaoImpl;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.to.SubServicoTO;
import com.prime.app.test.util.PropsUtil;
import com.prime.infra.util.WrapperUtils;

public class ConsultarSubservicoTest extends AgvTabSubservicoDaoImpl {
	public Properties props = new PropsUtil().load("/testcase.properties");
	public SessionFactory sf = new AnnotationConfiguration().configure("/hibernate.cfg.xml").buildSessionFactory();
	public Session session = null;
	private EntityManagerFactory emf = null;	
		
	@Before
	public void setUp() throws Exception {		
		this.session = sf.openSession();
		emf = Persistence.createEntityManagerFactory("manager1");
	}
	
	@After
	public void tearDown() throws Exception {
		this.session.close();
	}
	
	@Override
	protected EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	@Override
	protected Session getHibernateSession() {
		return this.session;
	}
	
	@Test
	public void testBuscaTarifasByMunicipo(){
		Assert.assertNotNull(findTarifasByMunicipo())	;
	}

	public List<SubServicoTO> findTarifasByMunicipo() {
		List<SubServicoTO> lista = findAllWithTarifasByMunicipo(new MunicipioTO(WrapperUtils.toLong("40")));
		if (lista!=null && lista.size()==0){
			lista=null;	
		}		
		return lista;
	}
}
