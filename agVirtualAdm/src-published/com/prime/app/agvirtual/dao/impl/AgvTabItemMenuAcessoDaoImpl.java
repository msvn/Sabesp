package com.prime.app.agvirtual.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabItemMenuAcessoDao;
import com.prime.app.agvirtual.entity.AgvTabBloqueioDetalhe;
import com.prime.app.agvirtual.entity.AgvTabBloqueio;
import com.prime.app.agvirtual.entity.AgvTabItemMenuAcesso;
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

	public List<AgvTabItemMenuAcesso> findAll() {
		List<AgvTabItemMenuAcesso> listaResultado = findByQuery("select e from AgvTabItemMenuAcesso e");
    	return listaResultado;
	}
}
