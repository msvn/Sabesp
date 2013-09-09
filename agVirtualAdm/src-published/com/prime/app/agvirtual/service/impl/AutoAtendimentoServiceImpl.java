package com.prime.app.agvirtual.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabAutoatendimentoDao;
import com.prime.app.agvirtual.dao.AgvTabCorrelacaoDao;
import com.prime.app.agvirtual.entity.AgvTabAutoatendimento;
import com.prime.app.agvirtual.entity.AgvTabCorrelacao;
import com.prime.app.agvirtual.service.AutoAtendimentoService;

@Service
public class AutoAtendimentoServiceImpl implements AutoAtendimentoService {
	
	@Autowired
    private AgvTabAutoatendimentoDao dao;
	
	@Autowired
	private AgvTabCorrelacaoDao correlacaoDao;
	
	@Transactional
	public List<SelectItem> findAllSelecteItem() {
		List<AgvTabAutoatendimento> listTempFunc = dao.findAll();
		ArrayList<SelectItem> lista =  new ArrayList<SelectItem>();
		if(listTempFunc != null){
			lista.add(new SelectItem(-1L, "[Selecione]"));
			for (Iterator iterator = listTempFunc.iterator(); iterator.hasNext();) {
				AgvTabAutoatendimento agvTabAutoatendimento = (AgvTabAutoatendimento) iterator.next();
				SelectItem item =  new SelectItem(agvTabAutoatendimento.getCdAutoatendimento(),agvTabAutoatendimento.getDsAutoatendimento());
				lista.add(item);
			}
		}
		return lista;
	}
	

	@Transactional
	public AgvTabAutoatendimento findById(Long codAutoAtendimento, boolean b) {
		return dao.findById(codAutoAtendimento,b);
	}

	@Transactional
	public void save(AgvTabAutoatendimento autoAtend,List<AgvTabCorrelacao> listaFuncionalidadesARemover) {
		correlacaoDao.save(autoAtend,listaFuncionalidadesARemover);
	}



}
