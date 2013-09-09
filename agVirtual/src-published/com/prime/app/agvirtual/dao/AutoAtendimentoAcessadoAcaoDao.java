package com.prime.app.agvirtual.dao;

import com.prime.app.agvirtual.entity.AutoAtendimentoAcessadoAcao;

public interface AutoAtendimentoAcessadoAcaoDao {
	public AutoAtendimentoAcessadoAcao persist(AutoAtendimentoAcessadoAcao o);
	public AutoAtendimentoAcessadoAcao findById(Long id, boolean lock);
}
