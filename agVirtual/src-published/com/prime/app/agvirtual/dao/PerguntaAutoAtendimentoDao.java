package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.PerguntaAutoAtendimento;

public interface PerguntaAutoAtendimentoDao {
	/**
	 * Retorna todas as perguntas do Auto-Atendimento
	 * 
	 * @param maxResults
	 * @return
	 */
	public List<PerguntaAutoAtendimento> findAll(int maxResults);
	
	/**
	 * Pesquisa pergunta do Auto-Atendimento 
	 * 
	 * @param id
	 * @return
	 */
	public PerguntaAutoAtendimento findById(int id);
}
