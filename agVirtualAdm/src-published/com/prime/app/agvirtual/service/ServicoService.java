package com.prime.app.agvirtual.service;

	import java.util.List;

import javax.faces.model.SelectItem;

import com.prime.app.agvirtual.entity.AgvTabServico;
import com.prime.app.agvirtual.to.ServicoTO;
import com.prime.app.agvirtual.to.SubServicoTO;

/**
 * Servi�o com m�todos para SubServi�o
 * @author gustavons
 *
 */
public interface ServicoService {
	
	public List<ServicoTO> findAll();
	
	public AgvTabServico save(AgvTabServico t, List<SubServicoTO> listaSubServicoExcluido);

	public AgvTabServico findById(Long valueOf, boolean b);
	
	public List<SelectItem> findAllSelectedItems();
	
	boolean nameAlreadyExists(String name);

}
