package com.prime.app.agvirtual.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prime.app.agvirtual.dao.AgvTabCanalAtendDao;
import com.prime.app.agvirtual.entity.AgvTabCanalAtend;
import com.prime.app.agvirtual.service.CanaisAtendimentoService;
import com.prime.app.agvirtual.to.CanalAtendimentoTO;

@Service
public class CanaisAtendimentoServiceImpl implements CanaisAtendimentoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CanaisAtendimentoServiceImpl.class);

    @Autowired
    AgvTabCanalAtendDao canalDao;
    
	public List<CanalAtendimentoTO> findAll() {
		return canalDao.findAll();
	}

	public List<SelectItem> findAllSelectedItems() {
		ArrayList<SelectItem> listaResultado =  new ArrayList<SelectItem>();
		List<CanalAtendimentoTO> listTemp = canalDao.findAll();
		if(listTemp != null){
			for (Iterator<CanalAtendimentoTO> iterator = listTemp.iterator(); iterator.hasNext();) {
				CanalAtendimentoTO canalAtendimentoTO = (CanalAtendimentoTO) iterator.next();
				SelectItem item =  new SelectItem(canalAtendimentoTO.getCdCanalAtend().toString(),canalAtendimentoTO.getNmCanalAtend());
				listaResultado.add(item);	
			}
		}
		return listaResultado;
	}

	public AgvTabCanalAtend findById(Long valueOf, boolean b) {
		return canalDao.findById(valueOf,b);
	}

}
