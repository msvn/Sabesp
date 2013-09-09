package com.prime.app.agvirtual.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabServicoDao;
import com.prime.app.agvirtual.entity.AgvTabServico;
import com.prime.app.agvirtual.to.ServicoTO;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

/**
 * Imterface DAO
 * @author gustavons
 *
 */

@Repository
public class AgvTabServicoDaoImpl extends GenericHibernateJpaDao<AgvTabServico, Long> implements AgvTabServicoDao {

	public List<ServicoTO> findAll() {
		List<AgvTabServico> listaResultado = findByQuery("select e from AgvTabServico e order by e.nmServico");
    	return parseTO(listaResultado);
	}

	private List<ServicoTO> parseTO(List<AgvTabServico> listaResultado) {
		List<ServicoTO> listaTemp =  new ArrayList<ServicoTO>();
		
		for (Iterator<AgvTabServico> iterator = listaResultado.iterator(); iterator.hasNext();) {
			AgvTabServico agvTabServico = (AgvTabServico) iterator.next();
			listaTemp.add(agvTabServico.parseTO());
		}
		
		return listaTemp;
	}

	public AgvTabServico save(AgvTabServico t) {
		 Session session = getHibernateSession();
		 List temp = t.getAgvListaServSubServ();
		 AgvTabServico entityUpdated = getEntityManager().merge(t);
		 
		 return entityUpdated;
	}

	public List<ServicoTO> findAllServicoVazio() {
		List<AgvTabServico> listaResultado = findByQuery("select e from AgvTabServico e order by e.nmServico where e.servico = null ");
    	return parseTO(listaResultado);
	}

	public AgvTabServico findByName(String name) {
		
		AgvTabServico servicoLoad = (AgvTabServico) getHibernateSession().createCriteria(AgvTabServico.class).add( Restrictions.like("nmServico", name) ).uniqueResult();
			
		return servicoLoad;
	}
	
	
	

}
