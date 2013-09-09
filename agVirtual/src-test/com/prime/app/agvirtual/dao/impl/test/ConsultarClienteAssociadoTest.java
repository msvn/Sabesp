package com.prime.app.agvirtual.dao.impl.test;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.prime.app.agvirtual.dao.impl.ClienteDaoImpl;
import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.test.util.PropsUtil;

public class ConsultarClienteAssociadoTest extends ClienteDaoImpl {
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
	
	@Test
	public void testConsultarClienteAssociadoPorRGI(){
		Assert.assertNotNull(findClienteByRGI(props.getProperty("rgi.valido")))	;
	}

	@Test
	public void testConsultarClienteAssociadoPorRGIInvalido(){
		Assert.assertNull(findClienteByRGI(props.getProperty("rgi.invalido")));		
	}
	
	private Cliente findClienteByRGI(String dsRGI){
		Imovel imovel = imovelDAO.findImovelByRGI(new Imovel(dsRGI));		
		return this.findByCliente(new Cliente(imovel.getCdCliente(),imovel.getCdMunicipio()));
	}
	
	@Override
	protected Session getHibernateSession() {
		return this.session;
	}
	
	public static void main(String[] args) {
		ConsultarClienteAssociadoTest t = new ConsultarClienteAssociadoTest();
		t.session = t.sf.openSession();
		t.imovelDAO = new ImovelDaoImplCase();
		Cliente c = t.findClienteByRGI(t.props.getProperty("rgi.valido"));
		System.out.println(c);		
		t.session.close();
	}
}
