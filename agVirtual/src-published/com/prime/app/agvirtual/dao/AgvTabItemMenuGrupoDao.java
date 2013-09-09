package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabItemMenuGrupo;

/**
 * Imterface DAO
 * @author gustavons
 *
 */
public interface AgvTabItemMenuGrupoDao {
	public AgvTabItemMenuGrupo findById(Long id, boolean lock);
	public List<AgvTabItemMenuGrupo> findByItemPai(String cdItemPai);
}
