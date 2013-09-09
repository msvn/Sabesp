package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.to.ItemPaginaInicialTO;
import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;

/**
 * Interface DAO para o objeto AgvTabItemPaginaInicial
 * @author felipepm
 */
public interface AgvTabItemPaginaInicialDao {
	
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
