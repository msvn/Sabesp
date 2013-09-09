package com.prime.app.agvirtual.service;

import java.util.List;

import javax.faces.model.SelectItem;

import com.prime.app.agvirtual.entity.AgvTabCanalAtend;
import com.prime.app.agvirtual.to.CanalAtendimentoTO;

public interface CanaisAtendimentoService {
	
	public List<CanalAtendimentoTO> findAll();

	public List<SelectItem> findAllSelectedItems();

	public AgvTabCanalAtend findById(Long valueOf, boolean b);
}
 