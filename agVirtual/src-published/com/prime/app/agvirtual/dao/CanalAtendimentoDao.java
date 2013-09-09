package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.CanalAtendimento;


/**
 * Interface DAO com m�todos para Canais de Atendimento.
 * @author felipepm
 */
public interface CanalAtendimentoDao {

	List<CanalAtendimento> findAll();

}
