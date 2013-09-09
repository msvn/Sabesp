package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.Atendimento;

/**
 * Dao de atendimento
 *
 */
public interface AtendimentoDao {
	public Atendimento findById(Long id);
	public List<Atendimento> findAll();
	public Atendimento persist(Atendimento entity);
	public Atendimento findByCodSessao(String cdSessao);
}
