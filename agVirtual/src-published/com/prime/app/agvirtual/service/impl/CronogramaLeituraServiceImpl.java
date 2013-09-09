package com.prime.app.agvirtual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.CronogramaLeituraDao;
import com.prime.app.agvirtual.service.CronogramaLeituraService;
import com.prime.app.agvirtual.to.CronogramaLeituraTO;

@Service
public class CronogramaLeituraServiceImpl implements CronogramaLeituraService {

	@Autowired
    private CronogramaLeituraDao cronogramaLeituraDao;

	@Transactional(readOnly = true)
	public List<CronogramaLeituraTO> findByRgi(String cdRgi) throws Exception {
		return cronogramaLeituraDao.findByRgi(cdRgi);
	}
}
