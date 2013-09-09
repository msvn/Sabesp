package com.prime.app.agvirtual.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AutoAtendimentoAcessadoDao;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcessado;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AutoAtendimentoAcessadoDaoImpl extends GenericHibernateJpaDao<AutoAtendimentoAcessado, Long> implements AutoAtendimentoAcessadoDao{
	private static final Logger logger = LoggerFactory.getLogger(AutoAtendimentoAcessadoDaoImpl.class);
	
	public List<AutoAtendimentoAcessado> findAll(){
		List<AutoAtendimentoAcessado> retorno = findByQuery("select o from AutoAtendimentoAcessado o");
		return retorno;
	}
	
	public List findAllGroupByRgi(Long cdAtendimento) throws NotFoundBusinessException{
		String queryString = "select o.cdRgi, o.socilicitante from AutoAtendimentoAcessado o where o.atendimento.cdAtendimento = ?1 and o.cdRgi != null GROUP BY o.cdRgi, o.socilicitante";
		
		List<AutoAtendimentoAcessado> result = findByQuery(queryString, cdAtendimento);
		
		if(result==null || result.isEmpty()) throw new NotFoundBusinessException("");
		
		return result;
	}
	
	public List<AutoAtendimentoAcessado> findByRgi(Long cdRgi,Long cdAtendimento){
		String queryString = "select o from AutoAtendimentoAcessado o WHERE o.atendimento.cdAtendimento = ?1 AND o.cdRgi = ?2";
		
		logger.info("queryString -->>" + queryString); System.out.println("queryString -->>" + queryString);
		logger.info("cdAtendimento -->>" + cdAtendimento); System.out.println("cdAtendimento -->>" + cdAtendimento);
		logger.info("cdAtendimento -->>" + cdRgi); System.out.println("cdAtendimento -->>" + cdRgi);
		
		List<AutoAtendimentoAcessado> result = findByQuery(queryString, cdAtendimento, cdRgi);
		
		return result;
	}
}
