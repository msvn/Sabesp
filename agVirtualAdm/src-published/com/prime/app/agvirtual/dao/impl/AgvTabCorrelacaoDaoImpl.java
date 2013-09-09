package com.prime.app.agvirtual.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabCorrelacaoDao;
import com.prime.app.agvirtual.entity.AgvTabAutoatendimento;
import com.prime.app.agvirtual.entity.AgvTabCorrelacao;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AgvTabCorrelacaoDaoImpl extends GenericHibernateJpaDao<AgvTabCorrelacao, Long> implements AgvTabCorrelacaoDao {

	private static final Logger logger = LoggerFactory.getLogger(AgvTabCorrelacaoDaoImpl.class);

	public AgvTabCorrelacao findById(Long codAuto, Long codFunc, boolean b) {
		AgvTabCorrelacao result = null;
		try{
		org.hibernate.Query query = getHibernateSession().createQuery("" +
				"from AgvTabCorrelacao e where e.agvTabFuncionalidade.cdFuncionalidade = :1 and e.agvTabAutoatendimento.cdAutoatendimento =:2");
    	query.setLong("1",codFunc );
    	query.setLong("2",codAuto );
    	 result = (AgvTabCorrelacao) query.uniqueResult();            
        } catch(Exception e) {
             e.printStackTrace();
        } 
        
        return result;
	}

	public void save(AgvTabAutoatendimento autoAtend ,List<AgvTabCorrelacao> listaFuncionalidadesARemover){
		
		//Remove os objetos deletados
	    for (Iterator iterator = listaFuncionalidadesARemover.iterator(); iterator.hasNext();) {
			AgvTabCorrelacao agvTabCorrelacao = (AgvTabCorrelacao) iterator.next();
			org.hibernate.Query query = getHibernateSession().createQuery("" +
			"delete from AgvTabCorrelacao e where e.cdCorrelacao = :cod");
			query.setLong("cod",agvTabCorrelacao.getCdCorrelacao() );
			query.executeUpdate();
		}
		
		//Adiciona a nova lista
		for (Iterator<AgvTabCorrelacao> iterator = autoAtend.getAgvTabCorrelacaoList().iterator(); iterator.hasNext();) {
			AgvTabCorrelacao type = (AgvTabCorrelacao) iterator.next();
			getEntityManager().merge(type);
		}
	}
}
