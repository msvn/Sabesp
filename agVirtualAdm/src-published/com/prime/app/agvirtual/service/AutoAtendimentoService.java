package com.prime.app.agvirtual.service;

import java.util.List;

import javax.faces.model.SelectItem;

import com.prime.app.agvirtual.entity.AgvTabAutoatendimento;
import com.prime.app.agvirtual.entity.AgvTabCorrelacao;


public interface AutoAtendimentoService {

	AgvTabAutoatendimento findById(Long codAutoAtendimento, boolean b);

	void save(AgvTabAutoatendimento autoAtend, List<AgvTabCorrelacao> listaFuncionalidadesARemover);
	
	List<SelectItem> findAllSelecteItem();
	
}
 