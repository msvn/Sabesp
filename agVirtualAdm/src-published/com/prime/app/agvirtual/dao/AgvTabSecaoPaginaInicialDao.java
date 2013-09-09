package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;

/**
 * Interface DAO
 * @author felipepm
 */
public interface AgvTabSecaoPaginaInicialDao {
	
	/**
	 * M�todo respons�vel por obter as Se��es.
	 */
	public List<SecaoPaginaIncialTO> findAll();
	
	/**
	 * M�todo respons�vel por atualizar os dados da Se��o.
	 */
	public void save(SecaoPaginaIncialTO secao);
}
