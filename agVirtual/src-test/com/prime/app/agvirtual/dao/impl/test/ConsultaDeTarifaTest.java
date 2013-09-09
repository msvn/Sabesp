package com.prime.app.agvirtual.dao.impl.test;

import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.prime.app.agvirtual.dao.impl.TarifaDaoImpl;
import com.prime.app.agvirtual.to.TarifaTO;
import com.prime.app.test.util.PropsUtil;

public class ConsultaDeTarifaTest extends TarifaDaoImpl {
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
	public void testTodasTarifasVirgentesPorCategoriaEMunicipio(){
		Assert.assertNotNull(findTarifaByParams(props.getProperty("municipio.valido")))	;
	}

	@Test
	public void testTodasTarifasVirgentesPorCategoriaEMunicipioInvalido(){
		Assert.assertNull(findTarifaByParams(props.getProperty("municipio.invalido")))	;
	}

	public List<List<TarifaTO>> findTarifaByParams(String cdMunicipio) {
		List<List<TarifaTO>> lista = findTarifaByParams(cdMunicipio, "0", "1");
		if (lista!=null){
			if (lista.get(0)!=null && lista.get(0).size()==0){
				lista=null;	
			}
		}			
		return lista;
	}
	
//	public static void main(String[] args) {
//		ConsultaDeTarifaTest t = new ConsultaDeTarifaTest();
//		t.session = t.sf.openSession();
//		List<List<TarifaTO>> lista = t.findTarifaByParams("100", "0", "1"); 
//		System.out.println("");		
//		t.session.close();
//	}
}
