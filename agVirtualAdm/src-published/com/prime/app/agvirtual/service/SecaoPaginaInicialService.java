package com.prime.app.agvirtual.service;

import java.util.List;

import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;

/**
 * Servi�o com m�todos para Se��o da P�gina Inicial.
 * @author felipepm
 */
public interface SecaoPaginaInicialService {
	
	/**
	 * M�todo respons�vel por obter as Se��es.
	 */
	public List<SecaoPaginaIncialTO> findAll();
	
	/**
	 * M�todo respons�vel por atualizar os dados da Se��o.
	 */
	public void save(SecaoPaginaIncialTO secao);

}
