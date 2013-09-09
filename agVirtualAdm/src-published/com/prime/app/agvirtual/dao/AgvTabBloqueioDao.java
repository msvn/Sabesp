package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabBloqueio;

/**
 * Imterface DAO
 * @author gustavons
 *
 */
public interface AgvTabBloqueioDao {

	AgvTabBloqueio save(AgvTabBloqueio entity);

	List findAll();
}
