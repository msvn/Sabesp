package com.prime.app.agvirtual.service;

import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabCorrelacao;

public interface CorrelacaoService {
	
	public List<AgvTabCorrelacao> findByAtendimento(Long codAuto, boolean b);
	
}
