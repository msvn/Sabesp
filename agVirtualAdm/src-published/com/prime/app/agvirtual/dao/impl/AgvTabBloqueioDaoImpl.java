package com.prime.app.agvirtual.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabBloqueioDao;
import com.prime.app.agvirtual.entity.AgvTabBloqueio;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

 
@Repository
public class AgvTabBloqueioDaoImpl extends GenericHibernateJpaDao<AgvTabBloqueio, Long> implements AgvTabBloqueioDao {

	private static final Logger logger = LoggerFactory.getLogger(AgvTabBloqueioDaoImpl.class);

	public AgvTabBloqueio save(AgvTabBloqueio entity) {			 
			 return persist(entity);
	}
	
	public List findAll(){
		List<AgvTabBloqueio> listaResultado = findByQuery("select e from AgvTabBloqueio e  where e.excluido = false order by e.dtInclusao desc" );
    	return (listaResultado);
	}
}
