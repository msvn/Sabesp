package com.prime.app.agvirtual.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabSecaoPaginaInicialDao;
import com.prime.app.agvirtual.entity.AgvTabSecaoPagIni;
import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

/**
 * @author felipepm
 */
@Repository
public class AgvTabSecaoPaginaInicialDaoImpl
	extends GenericHibernateJpaDao<AgvTabSecaoPagIni, Long>
		implements AgvTabSecaoPaginaInicialDao {

	/**
	 * Log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(AgvTabSecaoPaginaInicialDaoImpl.class);
	
	/**
	 * {@inheritDoc}
	 */
	public List<SecaoPaginaIncialTO> findAll() {
		List<AgvTabSecaoPagIni> listaResultado = findByQuery("select e from AgvTabSecaoPagIni e");
    	return parseTO(listaResultado);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(SecaoPaginaIncialTO secao) {
		try {
			Session session = getHibernateSession();
	 		getEntityManager().merge(secao.toEntity());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Faz o Parse do Entity para o Objeto Transfer Object
	 * @param listaResultado
	 * @return
	 */
	private List<SecaoPaginaIncialTO> parseTO(List<AgvTabSecaoPagIni> listaResultado){
		
		ArrayList<SecaoPaginaIncialTO> listaFinal =  new ArrayList<SecaoPaginaIncialTO>();
		for (Iterator<AgvTabSecaoPagIni> iterator = listaResultado.iterator(); iterator.hasNext();) {
			AgvTabSecaoPagIni object = (AgvTabSecaoPagIni) iterator.next();
			listaFinal.add(object.parseTO());
		}
		
		return listaFinal;
	}
}
