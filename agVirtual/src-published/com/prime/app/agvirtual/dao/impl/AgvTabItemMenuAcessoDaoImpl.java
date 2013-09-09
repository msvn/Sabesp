package com.prime.app.agvirtual.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabItemMenuAcessoDao;
import com.prime.app.agvirtual.entity.AgvTabBloqueio;
import com.prime.app.agvirtual.entity.AgvTabBloqueioDetalhe;
import com.prime.app.agvirtual.entity.AgvTabItemMenuAcesso;
import com.prime.app.agvirtual.entity.AgvTabItemMenuGrupo;
import com.prime.app.agvirtual.enums.TipoMenu;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AgvTabItemMenuAcessoDaoImpl extends GenericHibernateJpaDao<AgvTabItemMenuAcesso, Long> implements AgvTabItemMenuAcessoDao {

	private static final Logger logger = LoggerFactory.getLogger(AgvTabItemMenuAcessoDaoImpl.class);
	 
	public List<AgvTabItemMenuAcesso> findAllBloqueio() {
		List<AgvTabItemMenuAcesso> listaResultado = findByQuery("select e from AgvTabItemMenuAcesso e");
    	return listaResultado;
	}

	public void save(ArrayList<AgvTabBloqueioDetalhe> lista,AgvTabBloqueio tabBloq) {
		for (Iterator<AgvTabBloqueioDetalhe> iterator = lista.iterator(); iterator.hasNext();) {
			AgvTabBloqueioDetalhe type = (AgvTabBloqueioDetalhe) iterator.next();
			type.setAgvTabBloqueio(tabBloq);
			getEntityManager().merge(type);
		}		
	}

	public List<AgvTabItemMenuAcesso> findByGrupo(AgvTabItemMenuGrupo item) {
		
		List<AgvTabItemMenuAcesso> listaResultado = new ArrayList<AgvTabItemMenuAcesso>();
		try {
			listaResultado = findByQuery("select e from AgvTabItemMenuAcesso e where e.cdItemMenu = " + item.getCdItemMenu());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    	return listaResultado;
	}
	
	public List<AgvTabItemMenuAcesso> pesquisaTodosItensMenu() {
		
		List<AgvTabItemMenuAcesso> listaResultado = new ArrayList<AgvTabItemMenuAcesso>();
		try {
			listaResultado = findByQuery("select distinct e from AgvTabItemMenuAcesso e order by e.dsItemMenu");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    	return listaResultado;
	}

	/**
	 * Pesquisa em qual item de menu um item de subMenu esta.
	 */
	public TipoMenu pesquisaMenuItemPertence(String idItemMenuSelected) {
		
		BigDecimal result =  null;
		TipoMenu tpMenu = null;
		try{
		String sql = "select g.cd_item_pai from agv_tab_menu_acesso_grupo t , agv_tab_item_menu_grupo g "+ 
		 "where g.cd_item_menu_grupo = t.cd_item_menu_grupo and  t.cd_item_menu_acesso = ? ";
		
		Query query = getEntityManager().createNativeQuery(sql);
		
		query.setParameter(1, idItemMenuSelected);
		result = (BigDecimal) query.getSingleResult();
		
		tpMenu = TipoMenu.byValue(Integer.valueOf(result.intValue()));
		
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ERRO na busca de menu -  pesquisaMenuItemPertence" );
		}
		
		return tpMenu;
	}
}
