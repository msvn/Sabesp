package com.prime.app.agvirtual.service;

import java.util.List;

import com.prime.app.agvirtual.entity.AcaoAutoAtendimento;
import com.prime.app.agvirtual.entity.PerguntaAutoAtendimento;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;
import com.prime.infra.BusinessException;

public interface AcaoAutoAtendimentoService {
	public AcaoAutoAtendimento findAcao(List<PerguntaAutoAtendimento> perguntasRespondidas, String diretoria) throws NotFoundBusinessException, BusinessException, Exception;
	public AcaoAutoAtendimento findAcaoByAutoAtendimento(Long codAutoAtendimentoAcao) throws NotFoundBusinessException, Exception;
}
