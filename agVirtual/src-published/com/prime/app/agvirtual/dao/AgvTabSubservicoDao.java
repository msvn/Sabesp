package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabSubservico;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.to.ServicoTO;
import com.prime.app.agvirtual.to.SubServicoTO;

/**
 * Imterface DAO
 * @author gustavons
 *
 */
public interface AgvTabSubservicoDao {

	public List<SubServicoTO> findAll();

	public AgvTabSubservico findById(Long id, boolean lock);

	public AgvTabSubservico save(AgvTabSubservico t);

	public void alterar(AgvTabSubservico entity);
	
	public List<SubServicoTO> findAllServicoVazio();

	public List<SubServicoTO> findAllWithTarifasByMunicipo(MunicipioTO municipioTO);

}
