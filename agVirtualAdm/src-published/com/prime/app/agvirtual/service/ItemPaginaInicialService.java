package com.prime.app.agvirtual.service;

import java.util.List;

import com.prime.app.agvirtual.to.ItemPaginaInicialTO;
import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;

/**
 * Serviço com métodos para Item Página Inicial.
 * @author felipepm
 */
public interface ItemPaginaInicialService {
	
	/**
	 * Método responsável por obter os Itens das Seções.
	 */
	public List<ItemPaginaInicialTO> findByParams(SecaoPaginaIncialTO secao);
	
	/**
	 * Método responsável por obter os Itens das Seções.
	 */
	public List<ItemPaginaInicialTO> findAll();
	
	/**
	 * Método responsável por Atualizar os Itens das Seções.
	 */
	public void save(List<ItemPaginaInicialTO> lItens);
}
