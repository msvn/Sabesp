package com.prime.app.agvirtual.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.ConjuntoRespotaAutoAtendimentoDao;
import com.prime.app.agvirtual.entity.ConjuntoRespostaAutoAtendimento;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;
import com.prime.app.agvirtual.util.Utils;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class ConjuntoRespotaAutoAtendimentoDaoImpl extends GenericHibernateJpaDao<ConjuntoRespostaAutoAtendimento, Long> implements ConjuntoRespotaAutoAtendimentoDao{
	private static final Logger logger = LoggerFactory.getLogger(ConjuntoRespotaAutoAtendimentoDaoImpl.class);
	
	public ConjuntoRespostaAutoAtendimento findById(Long id) {
		return super.findById(id, Boolean.FALSE);
	}
	
	public List<Object> findByCodPerguntasRespostas(List codPerguntasRespostas , String diretoria) throws Exception {
		logger.debug("Inicio ConjuntoRespotaAutoAtendimentoDaoImpl -> findByCodPerguntasRespostas");
		
		String splitedCodPerguntasRespostas = Utils.spritList(codPerguntasRespostas, ",");
		
		String queryString = "select cd_conjunto_resposta from agv_tab_integ_perg_resp "+
 		" where cd_pergunta_resposta IN ( " + splitedCodPerguntasRespostas + " ) " +
		" group by cd_conjunto_resposta having " +
		" count(cd_conjunto_resposta) >" +   ((codPerguntasRespostas.size() == 0) ? " 0 " : String.valueOf(codPerguntasRespostas.size()-1));
		
		logger.debug("findByCodPerguntasRespostas==>"+queryString);
		System.err.println("findByCodPerguntasRespostas==>"+queryString);
		
		Query query = getEntityManager().createNativeQuery(queryString);
		
		List<Object> listaConjuntoRespostaId = null;
		try{
			/**
			 * Retorna lista de ConjuntoResposta para o conjunto de respostas
			 */
			listaConjuntoRespostaId = query.getResultList();
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		
		//Se a lista vier nula, não existe uma ação especifica para este conjunto de resposta , deve seguir o fluxo e mostrar ao 
		// usuario a proxima pergunta.
		if(listaConjuntoRespostaId == null || listaConjuntoRespostaId.isEmpty())
			throw new NotFoundBusinessException("Nao encontrado conjunto resposta para perguntas respostas = " + splitedCodPerguntasRespostas);

		logger.debug("Fim ConjuntoRespotaAutoAtendimentoDaoImpl -> findByCodPerguntasRespostas");
		
		return listaConjuntoRespostaId;
	}

	public List<Object> findByCodPerguntasRespostasLigacaoAgua(List codPerguntasRespostas , String diretoria) throws Exception {
		logger.debug("Inicio ConjuntoRespotaAutoAtendimentoDaoImpl -> findByCodPerguntasRespostasLigacaoAgua");				String splitedCodPerguntasRespostas = Utils.spritList(codPerguntasRespostas, ",");				String queryString = "select integ.cd_conjunto_resposta from agv_tab_integ_perg_resp integ, agv_tab_conj_resposta conj_resposta "+ 		" where cd_pergunta_resposta IN ( " + splitedCodPerguntasRespostas + " ) " + 		" and integ.cd_conjunto_resposta = conj_resposta.cd_conjunto_resposta " + 		" and conj_resposta.ds_diretoria = '" + diretoria + "'" +		" group by integ.cd_conjunto_resposta having " +		" count(integ.cd_conjunto_resposta) >" +   ((codPerguntasRespostas.size() == 0) ? " 0 " : String.valueOf(codPerguntasRespostas.size()-1));				logger.debug("findByCodPerguntasRespostasLigacaoAgua==>"+queryString);				Query query = getEntityManager().createNativeQuery(queryString);				List<Object> listaConjuntoRespostaId = null;		try{			/**			 * Retorna lista de ConjuntoResposta para o conjunto de respostas			 */			listaConjuntoRespostaId = query.getResultList();		}catch(Exception e){			logger.error(e.getMessage());
			throw e;
		}				//Se a lista vier nula, não existe uma ação especifica para este conjunto de resposta , deve seguir o fluxo e mostrar ao 		// usuario a proxima pergunta.		if(listaConjuntoRespostaId == null || listaConjuntoRespostaId.size() != 1)			throw new NotFoundBusinessException("Nao encontrado conjunto resposta para perguntas respostas = " + splitedCodPerguntasRespostas);		logger.debug("Fim ConjuntoRespotaAutoAtendimentoDaoImpl -> findByCodPerguntasRespostasLigacaoAgua");				return listaConjuntoRespostaId;	}
	
}