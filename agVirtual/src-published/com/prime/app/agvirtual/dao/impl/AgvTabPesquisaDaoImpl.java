package com.prime.app.agvirtual.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabPesquisaDao;
import com.prime.app.agvirtual.entity.AgvTabPesquisa;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AgvTabPesquisaDaoImpl 
		extends GenericHibernateJpaDao<AgvTabPesquisa, Long> 
		implements AgvTabPesquisaDao {
	
	private static final Logger logger = LoggerFactory.getLogger(AgvTabPesquisaDaoImpl.class);
	
	public List<AgvTabPesquisa> findAll() {
		List<AgvTabPesquisa> listaResultado = 
			findByQuery("select e from AgvTabPesquisa e where e.dtEncerramento = null order by e.dtFimVigencia asc");
    	return listaResultado;
	}

}
