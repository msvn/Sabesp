package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.to.NoticiaTO;

/**
 * Imterface DAO
 * @author gustavons
 *
 */
public interface AgvTabNoticiaDao {

	public List<NoticiaTO> findAll();

	public boolean delete(NoticiaTO noticia);


}
