package com.prime.app.agvirtual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.MunicipioDao;
import com.prime.app.agvirtual.service.MunicipioService;
import com.prime.app.agvirtual.to.MunicipioTO;

@Service
public class MunicipioServiceImpl implements MunicipioService {

	@Autowired
    private MunicipioDao municipioDao;

	@Transactional(readOnly = true)
	public List<MunicipioTO> findAll() {
		return municipioDao.findAll();
	}

}