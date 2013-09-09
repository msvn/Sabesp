package com.prime.app.agvirtual.service;

import com.prime.app.agvirtual.entity.Atendimento;
import com.prime.infra.BusinessException;

public interface AtendimentoService {
	public Atendimento abreAtendimento(String codSessao, String nrIp);
	public boolean concluirAtendimento(String codSessao, Long codSituacao) throws BusinessException;
}
