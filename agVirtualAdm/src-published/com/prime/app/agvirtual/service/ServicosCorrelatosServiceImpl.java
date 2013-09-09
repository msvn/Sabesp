package com.prime.app.agvirtual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prime.app.agvirtual.dao.AgvTabAutoatendimentoDao;
import com.prime.app.agvirtual.entity.AgvTabAutoatendimento;

@Service
public class ServicosCorrelatosServiceImpl implements ServicosCorrelatosService {
	
	@Autowired
	private AgvTabAutoatendimentoDao autoAtendimentoDao;

	public List<AgvTabAutoatendimento> findAll() {
		
		return autoAtendimentoDao.findAll();
		
	}

}
