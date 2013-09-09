package com.prime.app.agvirtual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabCorrelacaoDao;
import com.prime.app.agvirtual.entity.AgvTabCorrelacao;
import com.prime.app.agvirtual.service.CorrelacaoService;

@Service
public class CorrelacaoServiceImpl implements CorrelacaoService {

	@Autowired
    private AgvTabCorrelacaoDao correlacaoDao;

	@Transactional
	public List<AgvTabCorrelacao> findByAtendimento(Long codAuto, boolean b){
		return correlacaoDao.findByAtendimento(codAuto, b);
	}
}