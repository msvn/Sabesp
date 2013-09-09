package com.prime.app.agvirtual.service;

import java.util.List;

import javax.faces.model.SelectItem;

import com.prime.app.agvirtual.entity.AgvTabItemMenuAcesso;

public interface ItemMenuService {
	
	public List<SelectItem> findAllSelectedItems();

	public AgvTabItemMenuAcesso findById(Long valueOf, boolean b);

	public List<AgvTabItemMenuAcesso> findAll();


}
 