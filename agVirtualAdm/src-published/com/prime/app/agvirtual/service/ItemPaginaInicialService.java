package com.prime.app.agvirtual.service;

import java.util.List;

import com.prime.app.agvirtual.to.ItemPaginaInicialTO;
import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;

/**
 * Servi�o com m�todos para Item P�gina Inicial.
 * @author felipepm
 */
public interface ItemPaginaInicialService {
	
	/**
	 * M�todo respons�vel por obter os Itens das Se��es.
	 */
	public List<ItemPaginaInicialTO> findByParams(SecaoPaginaIncialTO secao);
	
	/**
	 * M�todo respons�vel por obter os Itens das Se��es.
	 */
	public List<ItemPaginaInicialTO> findAll();
	
	/**
	 * M�todo respons�vel por Atualizar os Itens das Se��es.
	 */
	public void save(List<ItemPaginaInicialTO> lItens);
}
