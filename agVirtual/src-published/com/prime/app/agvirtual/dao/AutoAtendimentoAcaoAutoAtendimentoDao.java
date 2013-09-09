package com.prime.app.agvirtual.dao;

import com.prime.app.agvirtual.entity.AutoAtendimentoAcaoAutoAtendimento;

public interface AutoAtendimentoAcaoAutoAtendimentoDao {
	public AutoAtendimentoAcaoAutoAtendimento persist(AutoAtendimentoAcaoAutoAtendimento o);
	public AutoAtendimentoAcaoAutoAtendimento findById(Long id, boolean lock);
}
