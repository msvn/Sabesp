package com.prime.app.agvirtual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.CanalAtendimentoDao;
import com.prime.app.agvirtual.entity.CanalAtendimento;
import com.prime.app.agvirtual.service.CanalAtendimentoService;

@Service
public class CanalAtendimentoServiceImpl implements CanalAtendimentoService {

    @Autowired
    private CanalAtendimentoDao canalAtendimentoDao;

	@Transactional(readOnly = true)
	public List<CanalAtendimento> findAll() {
		return canalAtendimentoDao.findAll();
	}

}