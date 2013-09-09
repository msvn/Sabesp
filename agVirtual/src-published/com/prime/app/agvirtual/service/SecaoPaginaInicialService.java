package com.prime.app.agvirtual.service;

import java.util.List;

import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;

/**
 * Serviço com métodos para Seção da Página Inicial.
 * @author felipepm
 */
public interface SecaoPaginaInicialService {
	
	/**
	 * Método responsável por obter as Seções.
	 */
	public List<SecaoPaginaIncialTO> findAll();
	
	/**
	 * Método responsável por atualizar os dados da Seção.
	 */
	public void save(SecaoPaginaIncialTO secao);

}
