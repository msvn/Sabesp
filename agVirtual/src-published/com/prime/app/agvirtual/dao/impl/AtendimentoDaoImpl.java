package com.prime.app.agvirtual.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AtendimentoDao;
import com.prime.app.agvirtual.entity.Atendimento;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AtendimentoDaoImpl extends GenericHibernateJpaDao<Atendimento, Long> implements AtendimentoDao {
	private static final Logger logger = LoggerFactory.getLogger(AtendimentoDaoImpl.class);

	public Atendimento findById(Long id) {
		return findById(id, Boolean.FALSE);
	}
	
	public List<Atendimento> findAll() {
		return getEntityManager().createQuery("from Atendimento").getResultList();
	}

	public Atendimento findByCodSessao(String cdSessao) {
		logger.info("Localizando atendimento para cdSessao = " + cdSessao);
		
		String queryString = "select o from Atendimento o where o.cdSessao = ?1";
		List<Atendimento> result = findByQuery(queryString, cdSessao);
		
		if(result.isEmpty()) return null;
		
		if(result.size()>1){
			logger.warn("Esperado [1] atendimento para sessao " + cdSessao + ", foram encontrados [" + result.size() + "].");
		}
			
		return result.get(0);
	}
}
