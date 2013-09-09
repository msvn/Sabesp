package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabAutoatendimento;

/**
 * Imterface DAO
 * @author gustavons
 *
 */
public interface AgvTabAutoatendimentoDao {

	AgvTabAutoatendimento findById(Long valueOf, boolean b);

	List<AgvTabAutoatendimento> findAll();

}
