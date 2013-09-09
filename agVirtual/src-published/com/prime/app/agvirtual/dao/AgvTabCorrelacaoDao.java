package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabAutoatendimento;
import com.prime.app.agvirtual.entity.AgvTabCorrelacao;

/**
 * Imterface DAO
 * @author gustavons
 *
 */
public interface AgvTabCorrelacaoDao {

	AgvTabCorrelacao findById(Long valueOf, Long codFunc, boolean b);
	
	public List<AgvTabCorrelacao> findByAtendimento(Long codAuto, boolean b);

	void save(AgvTabAutoatendimento autoAtend, List<AgvTabCorrelacao> listaFuncionalidadesARemover);

}
