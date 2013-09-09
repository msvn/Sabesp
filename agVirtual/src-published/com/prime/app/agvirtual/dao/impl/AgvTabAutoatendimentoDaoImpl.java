package com.prime.app.agvirtual.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabAutoatendimentoDao;
import com.prime.app.agvirtual.entity.AgvTabAutoatendimento;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AgvTabAutoatendimentoDaoImpl extends GenericHibernateJpaDao<AgvTabAutoatendimento, Long> implements AgvTabAutoatendimentoDao {

	private static final Logger logger = LoggerFactory.getLogger(AgvTabAutoatendimentoDaoImpl.class);
	
	public List<AgvTabAutoatendimento> findAll() {
		List<AgvTabAutoatendimento> listaResultado = findByQuery("select e from AgvTabAutoatendimento e");
    	return listaResultado;
	}
}
