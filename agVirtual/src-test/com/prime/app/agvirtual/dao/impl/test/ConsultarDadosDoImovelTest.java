package com.prime.app.agvirtual.dao.impl.test;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.prime.app.agvirtual.dao.impl.EnderecoDaoImpl;
import com.prime.app.agvirtual.entity.Endereco;
import com.prime.app.test.util.PropsUtil;

public class ConsultarDadosDoImovelTest extends EnderecoDaoImpl{
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
	public void testConsultarDadosDoImovelPorRGI(){
		Assert.assertNotNull( findEnderecoImovelByRGI(props.getProperty("rgi.valido")))	;
	}

	@Test
	public void testConsultarDadosDoImovelPorRGIInvalido(){
		Assert.assertNull( findEnderecoImovelByRGI(props.getProperty("rgi.invalido")));		
	}
	
	private Endereco findEnderecoImovelByRGI(String dsRGI){
//		Imovel imovel = imovelDAO.findImovelByRGI(new Imovel(dsRGI));		
		Endereco endereco = new Endereco();
//		endereco.setImovel(imovel);
		endereco = getEnderecoByRGI(dsRGI);
		if(endereco.getDsEndereco()==null) endereco=null;
		return endereco;
	}
	
	@Override
	protected Session getHibernateSession() {
		return this.session;
	}
	
//	public static void main(String[] args) {
//		ConsultarDadosDoImovelTest t = new ConsultarDadosDoImovelTest();
//		t.session = t.sf.openSession();
//		t.imovelDAO = new ImovelDaoImplCase();
//		Endereco endereco = t.findEnderecoImovelByRGI("9999999999");
//		System.out.println();
//		t.session.close();
//	}
	
}
