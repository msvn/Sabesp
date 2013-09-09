package com.prime.app.agvirtual.dao;

import java.util.ArrayList;
import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabBloqueioDetalhe;
import com.prime.app.agvirtual.entity.AgvTabBloqueio;
import com.prime.app.agvirtual.entity.AgvTabItemMenuAcesso;

/**
 * Imterface DAO
 * @author gustavons
 *
 */
public interface AgvTabItemMenuAcessoDao {

	List<AgvTabItemMenuAcesso> findAllBloqueio();

	AgvTabItemMenuAcesso findById(Long valueOf, boolean b);

	void save(ArrayList<AgvTabBloqueioDetalhe> lista, AgvTabBloqueio temp);

	List<AgvTabItemMenuAcesso> findAll();

}
