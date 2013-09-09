package com.prime.app.agvirtual.dao.impl.test;

import java.util.ArrayList;
import java.util.Properties;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.prime.app.agvirtual.dao.impl.BancoConveniadoPagEletronicoDaoImpl;
import com.prime.app.test.util.PropsUtil;

public class ConsultarBancosConveniadosParaPgtoEletronicoTest extends BancoConveniadoPagEletronicoDaoImpl {
	public Properties props = new PropsUtil().load("/testcase.properties");
	private EntityManagerFactory emf = null;	
		
	@Before
	public void setUp() throws Exception {		
		emf = Persistence.createEntityManagerFactory("manager1");
	}
	
	@Override
	protected EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	@Test
	public void testBuscaBancoConveniado(){
		Assert.assertNotNull(findBancoConveniado())	;
	}

	public ArrayList<SelectItem> findBancoConveniado() {
		ArrayList<SelectItem> lista = buscaBancoConveniado();
		if (lista!=null && lista.size()==0){
			lista=null;	
		}		
		return lista;
	}
	
//	public static void main(String[] args) {
//		ConsultarBancosConveniadosParaPgtoEletronico c = new ConsultarBancosConveniadosParaPgtoEletronico();
//		c.emf = Persistence.createEntityManagerFactory("manager1");
//		ArrayList<SelectItem> a = c.buscaBancoConveniado();
//		System.out.println("--->"+a.size());
//	}

}
