package com.prime.app.agvirtual.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabItemMenuGrupoDao;
import com.prime.app.agvirtual.entity.AgvTabItemMenuGrupo;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AgvTabItemMenuGrupoDaoImpl extends GenericHibernateJpaDao<AgvTabItemMenuGrupo, Long> implements AgvTabItemMenuGrupoDao {

	private static final Logger logger = LoggerFactory.getLogger(AgvTabItemMenuGrupoDaoImpl.class);
	 
	
	public List<AgvTabItemMenuGrupo> findByItemPai(String cdItemPai) {
		List<AgvTabItemMenuGrupo> listaResultado = findByQuery("select e from AgvTabItemMenuGrupo e where e.cdItemPai = " + cdItemPai);
    	return listaResultado;
	}
}
