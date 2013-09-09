package com.prime.app.agvirtual.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabBloqueioDetalheDao;
import com.prime.app.agvirtual.entity.AgvTabBloqueioDetalhe;
import com.prime.app.agvirtual.entity.AgvTabBloqueio;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AgvTabBloqueioDetalheDaoImpl extends GenericHibernateJpaDao<AgvTabBloqueioDetalhe, Long> implements AgvTabBloqueioDetalheDao {

	private static final Logger logger = LoggerFactory.getLogger(AgvTabBloqueioDetalheDaoImpl.class);

	public void delete(AgvTabBloqueio entity) {
		
	    try {
	    	org.hibernate.Query query = getHibernateSession().createQuery("delete from AgvTabBloqueioDetalhe e where e.agvTabBloqueio.cdBloqueio =:codigo");
		    query.setLong("codigo", entity.getCdBloqueio());
	        query.executeUpdate();            
	        } catch(Exception e) {
	             e.printStackTrace();
	             logger.error(e.getMessage());
	        }    
	}

}
