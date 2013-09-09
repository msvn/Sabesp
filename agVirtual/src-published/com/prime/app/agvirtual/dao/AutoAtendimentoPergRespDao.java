package com.prime.app.agvirtual.dao;

import com.prime.app.agvirtual.entity.AutoAtendimentoPergResp;

public interface AutoAtendimentoPergRespDao {
	
	/**
	 * Pesquisa pergunta do Auto-Atendimento 
	 * 
	 * @param id
	 * @return
	 */
	public AutoAtendimentoPergResp findById(Long id, boolean lock);
}
