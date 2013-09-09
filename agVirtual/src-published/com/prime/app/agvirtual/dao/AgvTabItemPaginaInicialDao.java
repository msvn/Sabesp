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
