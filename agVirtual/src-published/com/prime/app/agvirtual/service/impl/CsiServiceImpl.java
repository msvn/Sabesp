package com.prime.app.agvirtual.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.CsiDao;
import com.prime.app.agvirtual.service.CsiService;
import com.prime.app.agvirtual.to.BancoConveniadoTO;

@Service
public class CsiServiceImpl implements CsiService {
	
	@Autowired
	private CsiDao csiDao;

	@Transactional(readOnly = true)
	public ArrayList<BancoConveniadoTO> consultarBancoConveniado() {
		return csiDao.consultarBancoConveniado();
	}

}
