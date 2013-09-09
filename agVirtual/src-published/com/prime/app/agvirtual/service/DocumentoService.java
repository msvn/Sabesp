package com.prime.app.agvirtual.service;

import java.util.List;

import javax.faces.model.SelectItem;

import com.prime.app.agvirtual.entity.AgvTabDocumento;
import com.prime.app.agvirtual.to.DocumentoTO;

public interface DocumentoService {
	public List<DocumentoTO> findAll();
	public List<SelectItem> findAllSelectedItems();
	public AgvTabDocumento findById(Long valueOf, boolean b);
}
