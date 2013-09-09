package com.prime.app.agvirtual.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabMunicipioDao;
import com.prime.app.agvirtual.service.MunicipioService;

@Service
public class MunicipioServiceImpl implements MunicipioService {

    @Autowired
    private AgvTabMunicipioDao municipioDao;

	@Transactional(readOnly = true)
	public ArrayList findAll() {
		return municipioDao.findAll();
	}

}