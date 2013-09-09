package com.prime.app.agvirtual.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import collections.Collection;

import com.prime.app.agvirtual.dao.AgvTabMunicipioDao;
import com.prime.app.agvirtual.dao.CanalAtendimentoDao;
import com.prime.app.agvirtual.entity.CanalAtendimento;
import com.prime.app.agvirtual.entity.AgvTabMunicipio;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class CanalAtendimentoDaoImpl extends GenericHibernateJpaDao<CanalAtendimento, Long> implements CanalAtendimentoDao {

	private static final Logger logger = LoggerFactory.getLogger(CanalAtendimentoDaoImpl.class);
	
	public List<CanalAtendimento> findAll() {
		
		//TODO FELIPEPM - MOCK PARA A 1ª FASE. ALTERAR PARA NA BASE NA 2ª FASE.
		List<CanalAtendimento> listaCanaisAtendimento = new ArrayList<CanalAtendimento>();
		CanalAtendimento canalAtendimento = new CanalAtendimento();
		
		canalAtendimento.setCdItem(1L);
		canalAtendimento.setDsItem("Agências de atendimento");
		canalAtendimento.setDsLink("AGENCIASATENDIMENTO");
		listaCanaisAtendimento.add(canalAtendimento);
		
		canalAtendimento = new CanalAtendimento();
		canalAtendimento.setCdItem(2L);
		canalAtendimento.setDsItem("Atendimento a grandes consumidores");
		canalAtendimento.setDsLink("");
		listaCanaisAtendimento.add(canalAtendimento);
		
		canalAtendimento = new CanalAtendimento();
		canalAtendimento.setCdItem(3L);
		canalAtendimento.setDsItem("Atendimento telefônico");
		canalAtendimento.setDsLink("");
		listaCanaisAtendimento.add(canalAtendimento);
		
		canalAtendimento = new CanalAtendimento();
		canalAtendimento.setCdItem(4L);
		canalAtendimento.setDsItem("Atendimento on-line");
		canalAtendimento.setDsLink("");
		listaCanaisAtendimento.add(canalAtendimento);
		
		canalAtendimento = new CanalAtendimento();
		canalAtendimento.setCdItem(5L);
		canalAtendimento.setDsItem("Ouvidoria");
		canalAtendimento.setDsLink("");
		listaCanaisAtendimento.add(canalAtendimento);
		
		canalAtendimento = new CanalAtendimento();
		canalAtendimento.setCdItem(6L);
		canalAtendimento.setDsItem("TACE (Técnico de Atendimento Comercial Externo)");
		canalAtendimento.setDsLink("TACE");
		listaCanaisAtendimento.add(canalAtendimento);
		
    	return listaCanaisAtendimento;
    }
}
