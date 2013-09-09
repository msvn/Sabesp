package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.PerguntaAutoAtendimento;
import com.prime.app.agvirtual.entity.PerguntaRespostaAutoAtendimento;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;

public interface PerguntaRespostaAutoAtendimentoDao {
	
	/**
	 * Encontra Pergunta/Resposta baseado nos IDs da pergunta e da resposta.
	 * 
	 * Acesso ao <b>Conjunto</b> de possiveis respostas e <b>acoes</b>
	 * 
	 * @param cdPergunta
	 * @param cdResposta
	 * @return
	 */
	public PerguntaRespostaAutoAtendimento findByPerguntaIdRespostaId(int codPergunta, int codResposta) throws NotFoundBusinessException;
	
	/**
	 * Retorna todas as perguntas do Auto-Atendimento
	 * 
	 * @param maxResults
	 * @return
	 */
	public List<PerguntaRespostaAutoAtendimento> findAll(int maxResults);
	
	/**
	 * Pesquisa pergunta do Auto-Atendimento 
	 * 
	 * @param id
	 * @return
	 */
	public PerguntaRespostaAutoAtendimento findById(int id);
	
	
}
