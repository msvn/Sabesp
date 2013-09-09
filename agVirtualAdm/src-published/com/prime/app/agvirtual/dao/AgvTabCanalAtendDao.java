package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabCanalAtend;
import com.prime.app.agvirtual.to.CanalAtendimentoTO;

/**
 * Imterface DAO
 * @author gustavons
 *
 */
public interface AgvTabCanalAtendDao {

	public List<CanalAtendimentoTO> findAll();

	public AgvTabCanalAtend findById(Long valueOf, boolean b);

}
