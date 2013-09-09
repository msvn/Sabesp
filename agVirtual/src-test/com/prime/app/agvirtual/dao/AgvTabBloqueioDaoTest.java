package com.prime.app.agvirtual.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.entity.JPASupportTest;

 
public class AgvTabBloqueioDaoTest extends JPASupportTest{

	@Test
	public void testFindById() {
//		List result = getEntityManager().createQuery("select e from AgvTabBloqueio e where e.excluido = false " +
//				"and e.agvTabBloqueioDetalheList.municipio.id = '1'  and e.agvTabItemMenuAcesso.cdItemMenu = '3' ").getResultList();
		List result = getEntityManager().createNativeQuery("select * from agv_tab_bloqueio b , agv_tab_bloqueio_detalhe d " +
					" where b.fl_excluido = 0 and d.cd_bloqueio = b.cd_bloqueio "+ 
					" and d.cd_item_menu_acesso = '13' and d.cd_municipio= '1'").getResultList();
		System.err.println(result.size());
		Assert.assertNotNull(result);
	}
}
