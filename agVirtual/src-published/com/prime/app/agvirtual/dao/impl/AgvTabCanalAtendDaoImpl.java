package com.prime.app.agvirtual.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabCanalAtendDao;
import com.prime.app.agvirtual.entity.AgvTabCanalAtend;
import com.prime.app.agvirtual.to.CanalAtendimentoTO;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AgvTabCanalAtendDaoImpl extends GenericHibernateJpaDao<AgvTabCanalAtend, Long> implements AgvTabCanalAtendDao {

	private static final Logger logger = LoggerFactory.getLogger(AgvTabCanalAtendDaoImpl.class);
	
	public List findAll() {
    	List<AgvTabCanalAtend> listaResultado = findByQuery("select e from AgvTabCanalAtend e");
    	return parseTO(listaResultado);
    }
	 
	/**
	 * Faz o Parse do Entity para o Objeto Transfer Object
	 * @param listaResultado 
	 * @param listaResultado
	 * @return
	 */
	private ArrayList<CanalAtendimentoTO> parseTO(List<AgvTabCanalAtend> listaResultado){
    	ArrayList<CanalAtendimentoTO> list = new ArrayList<CanalAtendimentoTO>();
    	
    	for (Iterator<AgvTabCanalAtend> iterator = listaResultado.iterator(); iterator.hasNext();) {
    		AgvTabCanalAtend element = (AgvTabCanalAtend) iterator.next();
    		
    		CanalAtendimentoTO canalAtendimentoTO = new CanalAtendimentoTO();
    		canalAtendimentoTO.setCdCanalAtend(element.getCdCanalAtend());
    		canalAtendimentoTO.setCodDiretoria(element.getCodDiretoria().toString());
    		canalAtendimentoTO.setDsDiaHorarioAtend(element.getDsDiaHorarioAtend());
    		canalAtendimentoTO.setDsInfoCanalAtend(element.getDsInfoCanalAtend());
    		canalAtendimentoTO.setDsLink(element.getDsLink());
    		canalAtendimentoTO.setDsLinkUrl(element.getDsLinkUrl());
    		canalAtendimentoTO.setDsTelefone(element.getDsTelefone());
    		canalAtendimentoTO.setNmCanalAtend(element.getNmCanalAtend());
    		list.add(canalAtendimentoTO); 
		}
    	
		return list;
    }

}
