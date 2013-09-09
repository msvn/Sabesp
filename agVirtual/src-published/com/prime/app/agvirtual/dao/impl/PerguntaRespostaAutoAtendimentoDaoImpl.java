package com.prime.app.agvirtual.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.PerguntaRespostaAutoAtendimentoDao;
import com.prime.app.agvirtual.entity.PerguntaRespostaAutoAtendimento;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class PerguntaRespostaAutoAtendimentoDaoImpl extends GenericHibernateJpaDao<PerguntaRespostaAutoAtendimento,Integer> 
						implements PerguntaRespostaAutoAtendimentoDao {

	private static final Logger logger = LoggerFactory.getLogger(PerguntaRespostaAutoAtendimentoDaoImpl.class);
	
	public PerguntaRespostaAutoAtendimento findByPerguntaIdRespostaId(
			int codPergunta, int codResposta) throws NotFoundBusinessException {
		
		logger.info("##### Localizando PerguntaResposta para cdPergunta = " + codPergunta + " cdResposta=" + codResposta);

		// TODO - resolver excecao ao setar Integer
//		List<PerguntaRespostaAutoAtendimento> result = findByQuery("select o from PerguntaRespostaAutoAtendimento o where o.pergunta=?1 and o.resposta=?2", cdPergunta, cdResposta);		
		
		String query = "select o from PerguntaRespostaAutoAtendimento o where o.pergunta=" + codPergunta + " and o.resposta= " + codResposta;
		
		logger.info("Query processada====>"+query);
		List<PerguntaRespostaAutoAtendimento> result = findByQuery(query);
		
		if(result==null || result.size() == 0 ) {
			throw new NotFoundBusinessException("Pergunta Resposta nao cadastrada para pergunta, cdPergunta=" + codPergunta + " cdResposta=" + codResposta);
		}

		return result.get(0);
	}
	
	public PerguntaRespostaAutoAtendimento findById(int id) {
		return findById(id, Boolean.FALSE);
	}

}
