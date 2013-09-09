package com.prime.app.agvirtual.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabItemPaginaInicialDao;
import com.prime.app.agvirtual.entity.AgvTabItemPagIni;
import com.prime.app.agvirtual.to.ItemPaginaInicialTO;
import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

/**
 * @author felipepm
 */
@Repository
public class AgvTabItemPaginaInicialDaoImpl
	extends GenericHibernateJpaDao<AgvTabItemPagIni, Long>
		implements AgvTabItemPaginaInicialDao {

	/**
	 * Log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(AgvTabItemPaginaInicialDaoImpl.class);
	
	/**
	 * {@inheritDoc}
	 */
	public List<ItemPaginaInicialTO> findByParams(SecaoPaginaIncialTO secao) {  
		
		List<AgvTabItemPagIni> listaResultado = findByQuery("select e from AgvTabItemPagIni e where e.agvTabSecaoPagIni.cdSecao = " + secao.toEntity().getCdSecao());
    	return parseTO(listaResultado);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ItemPaginaInicialTO> findAll() {
		List<AgvTabItemPagIni> listaResultado = findByQuery("select e from AgvTabItemPagIni e");
    	return parseTO(listaResultado);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void save(List<ItemPaginaInicialTO> lItens){
		try {
			for (ItemPaginaInicialTO item : lItens) {
				Session session = getHibernateSession();
				getEntityManager().merge(item.toEntity());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Faz o Parse do Entity para o Objeto Transfer Object
	 * @param listaResultado
	 * @return
	 */
	private List<ItemPaginaInicialTO> parseTO(List<AgvTabItemPagIni> listaResultado){
		
		ArrayList<ItemPaginaInicialTO> listaFinal =  new ArrayList<ItemPaginaInicialTO>();
		for (Iterator<AgvTabItemPagIni> iterator = listaResultado.iterator(); iterator.hasNext();) {
			AgvTabItemPagIni object = (AgvTabItemPagIni) iterator.next();
			listaFinal.add(object.parseTO());
		}
		
		return listaFinal;
	}

}
