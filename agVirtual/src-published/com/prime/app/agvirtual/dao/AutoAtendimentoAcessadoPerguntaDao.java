package com.prime.app.agvirtual.dao;

import com.prime.app.agvirtual.entity.AutoAtendimentoAcessadoPergunta;

public interface AutoAtendimentoAcessadoPerguntaDao {
	public AutoAtendimentoAcessadoPergunta persist(AutoAtendimentoAcessadoPergunta o);
	public AutoAtendimentoAcessadoPergunta findById(Long id, boolean lock);
}
