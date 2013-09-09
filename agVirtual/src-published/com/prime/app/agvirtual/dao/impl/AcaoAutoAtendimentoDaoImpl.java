package com.prime.app.agvirtual.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AcaoAutoAtendimentoDao;
import com.prime.app.agvirtual.entity.AcaoAutoAtendimento;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AcaoAutoAtendimentoDaoImpl extends GenericHibernateJpaDao<AcaoAutoAtendimento, Long> implements AcaoAutoAtendimentoDao{
	private static final Logger logger = LoggerFactory.getLogger(AcaoAutoAtendimentoDaoImpl.class);
	
	public AcaoAutoAtendimento findAcaoByAutoAtendimento(Long cdAutoAtendimento) {
		
		String query = "select e from AcaoAutoAtendimento e where e.autoAtendimentoAcao.cdAutoatendimento=?1";
		
		List<AcaoAutoAtendimento> listaResultado = findByQuery(query, cdAutoAtendimento);
		
		if (!listaResultado.isEmpty()) {
			return listaResultado.get(0);
		} else {
			return null;
		}
	}
}
