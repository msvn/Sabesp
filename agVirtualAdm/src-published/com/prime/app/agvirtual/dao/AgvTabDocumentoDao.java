package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabDocumento;
import com.prime.app.agvirtual.to.DocumentoTO;

/**
 * 
 * @author gustavons
 *
 */
public interface AgvTabDocumentoDao {
	public List<DocumentoTO> findAll();
	public AgvTabDocumento findById(Long valueOf, boolean b);
}
