package com.prime.app.agvirtual.dao;

import com.prime.app.agvirtual.entity.AutoAtendimentoAcao;

public interface AutoAtendimentoAcaoDao {
	public AutoAtendimentoAcao findById(Long id, boolean lock);
}
