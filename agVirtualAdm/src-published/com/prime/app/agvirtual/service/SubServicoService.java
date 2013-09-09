package com.prime.app.agvirtual.service;

import java.util.List;

import javax.faces.model.SelectItem;

import com.prime.app.agvirtual.entity.AgvTabSubservico;
import com.prime.app.agvirtual.to.SubServicoTO;

/**
 * Serviço com métodos para SubServiço
 * @author gustavons
 *
 */
public interface SubServicoService {
	
	public List<SubServicoTO> findAll();
	
	public List<SelectItem> findAllSelectedItems();
	
	public AgvTabSubservico findById(Long id, boolean lock);

	public void save(AgvTabSubservico entity);

	public void alterar(AgvTabSubservico entity);

	public void excluir(List sessionAttribute);

	public List<SelectItem> findAllSelectedItemsNaoIncluidos();

}
