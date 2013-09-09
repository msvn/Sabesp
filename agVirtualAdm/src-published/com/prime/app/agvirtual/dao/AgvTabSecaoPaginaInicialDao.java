package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;

/**
 * Interface DAO
 * @author felipepm
 */
public interface AgvTabSecaoPaginaInicialDao {
	
	/**
	 * Método responsável por obter as Seções.
	 */
	public List<SecaoPaginaIncialTO> findAll();
	
	/**
	 * Método responsável por atualizar os dados da Seção.
	 */
	public void save(SecaoPaginaIncialTO secao);
}
