package com.prime.app.agvirtual.service;

import java.util.List;

import com.prime.app.agvirtual.to.NoticiaTO;

/**
 * Servi�o com m�todos para not�cia
 * @author gustavons
 *
 */
public interface NoticiaService {
	public List<NoticiaTO> findAll();
	public boolean delete(NoticiaTO noticia);

}
