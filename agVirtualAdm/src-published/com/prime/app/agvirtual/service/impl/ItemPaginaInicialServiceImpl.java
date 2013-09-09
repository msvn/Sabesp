package com.prime.app.agvirtual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabItemPaginaInicialDao;
import com.prime.app.agvirtual.service.ItemPaginaInicialService;
import com.prime.app.agvirtual.to.ItemPaginaInicialTO;
import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;

/**
 * @author felipepm
 */
@Service
public class ItemPaginaInicialServiceImpl implements ItemPaginaInicialService {

    @Autowired
    private AgvTabItemPaginaInicialDao itemDao;
    
    /**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly = true)
    public List<ItemPaginaInicialTO> findByParams(SecaoPaginaIncialTO secao) {
    	return itemDao.findByParams(secao);
	}

    /**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly = true)
	public List<ItemPaginaInicialTO> findAll() {
		return itemDao.findAll();
	}
    
    /**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly = true)
	public void save(List<ItemPaginaInicialTO> lItens) {
    	itemDao.save(lItens);
	}

}
