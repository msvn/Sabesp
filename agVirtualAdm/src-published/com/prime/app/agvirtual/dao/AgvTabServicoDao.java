package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabServico;
import com.prime.app.agvirtual.to.ServicoTO;

/**
 * Imterface DAO
 * @author gustavons
 *
 */
public interface AgvTabServicoDao {

	public List<ServicoTO> findAll();

	public AgvTabServico save(AgvTabServico t);

	public AgvTabServico findById(Long valueOf, boolean b);

	public List<ServicoTO> findAllServicoVazio();
	
	AgvTabServico findByName(String name);

}
