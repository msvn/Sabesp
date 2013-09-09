package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.ConjuntoRespostaAutoAtendimento;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;

public interface ConjuntoRespotaAutoAtendimentoDao {
	public ConjuntoRespostaAutoAtendimento findById(Long id);
	public List<Object> findByCodPerguntasRespostas(List codPerguntasRespostas, String diretoria) throws Exception;
	public List<Object> findByCodPerguntasRespostasLigacaoAgua(List codPerguntasRespostas, String diretoria) throws Exception;
}
