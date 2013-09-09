package com.prime.app.agvirtual.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import collections.Collection;

import com.prime.app.agvirtual.dao.AgvTabMunicipioDao;
import com.prime.app.agvirtual.entity.AgvTabMunicipio;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AgvTabMunicipioDaoImpl extends GenericHibernateJpaDao<AgvTabMunicipio, Long> implements AgvTabMunicipioDao {

	private static final Logger logger = LoggerFactory.getLogger(AgvTabMunicipioDaoImpl.class);
	
	public ArrayList<MunicipioTO> findAll() {
    	List<AgvTabMunicipio> listaResultado = findByQuery("select e from AgvTabMunicipio e");
    	return  parseTO(listaResultado);
    }
	 
	/**
	 * Faz o Parse do Entity para o Objeto Transfer Object
	 * @param listaResultado 
	 * @param listaResultado
	 * @return
	 */
	private ArrayList<MunicipioTO> parseTO(List<AgvTabMunicipio> listaResultado){
    	ArrayList<MunicipioTO> list =  new ArrayList<MunicipioTO>();
    	
    	for (Iterator<AgvTabMunicipio> iterator = listaResultado.iterator(); iterator.hasNext();) {
    		AgvTabMunicipio element = (AgvTabMunicipio) iterator.next();
    		
    		MunicipioTO munTo = new MunicipioTO();
    		munTo.setCodUf(element.getCodigoUf());
    		munTo.setIdMun(element.getId());
    		munTo.setNome(element.getNomeMunicipio());
    		list.add(munTo); 
		}
		return list;
    }

}
