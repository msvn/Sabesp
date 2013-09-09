package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.AutoAtendimentoAcessado;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;

public interface AutoAtendimentoAcessadoDao {
	public AutoAtendimentoAcessado findById(Long id, boolean lock);
	public AutoAtendimentoAcessado persist(AutoAtendimentoAcessado o);
	public List<AutoAtendimentoAcessado> findAll();
	
	public List findAllGroupByRgi(Long cdAtendimento) throws NotFoundBusinessException;
	public List<AutoAtendimentoAcessado> findByRgi(Long cdRgi, Long cdAtendimento);	
	
}
