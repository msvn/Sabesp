package com.prime.app.agvirtual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabPesquisaDao;
import com.prime.app.agvirtual.entity.AgvTabPesquisa;
import com.prime.app.agvirtual.service.PesquisaService;

@Service
public class PesquisaServiceImpl implements PesquisaService {
	
	@Autowired
    private AgvTabPesquisaDao agvTabPesquisaDao;
	
	@Transactional(readOnly = true)
	public List<AgvTabPesquisa> findAll() {
		List<AgvTabPesquisa> listaRetorno = agvTabPesquisaDao.findAll();
		return listaRetorno;
	}

}
