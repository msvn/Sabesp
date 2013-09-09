package com.prime.app.agvirtual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabSecaoPaginaInicialDao;
import com.prime.app.agvirtual.service.SecaoPaginaInicialService;
import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;

/**
 * @author felipepm
 */
@Service
public class SecaoPaginaInicialServiceImpl implements SecaoPaginaInicialService {

    @Autowired
    private AgvTabSecaoPaginaInicialDao secaoDao;
    
    /**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly = true)
	public List<SecaoPaginaIncialTO> findAll() {
		return secaoDao.findAll();
	}

    /**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public void save(SecaoPaginaIncialTO secao) {
		secaoDao.save(secao);
	}

}
