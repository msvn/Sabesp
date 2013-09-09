package com.prime.app.agvirtual.service;

import java.util.List;

import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;
import com.prime.app.agvirtual.to.RelatorioAutoAtendimentoAcessadoTO;

public interface RelatorioAutoAtendimentoService {
	public List<RelatorioAutoAtendimentoAcessadoTO> doRelatorio(Long cdAtendimento) throws NotFoundBusinessException;
}
