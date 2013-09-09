package com.prime.app.agvirtual.dao.impl.test;

import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.prime.app.agvirtual.dao.impl.CronogramaLeituraDaoImpl;
import com.prime.app.agvirtual.to.CronogramaLeituraTO;
import com.prime.app.test.util.PropsUtil;

public class ConsultarCronogramaLeituraTest extends CronogramaLeituraDaoImpl {
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
	
	@Override
	protected Session getHibernateSession() {
		return this.session;
	}
	
	@Test
	public void testBuscaCronogramaLeituraByRGI() {
		Assert.assertNotNull(findByRGI(props.getProperty("rgi.com.cronograma.leitura")));
	}
	
	@Test
	public void testBuscaCronogramaLeituraByRGIInvalido() {		
		Assert.assertNull(findByRGI(props.getProperty("rgi.invalido")));				
	}
	
	private List<CronogramaLeituraTO> findByRGI(String dsRGI) {
		List<CronogramaLeituraTO> lista = null;
		try {
			lista = findByRgi(dsRGI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (lista!=null && lista.size()==0) lista=null;
		return lista;
	}
	
	public static void main(String[] args) throws Exception{
		ConsultarCronogramaLeituraTest t = new ConsultarCronogramaLeituraTest();
		t.session = t.sf.openSession();
		List<CronogramaLeituraTO> lista = t.findByRgi(t.props.getProperty("rgi.invalido"));
		System.out.println();
		t.session.close();
	}

}
