package com.prime.app.agvirtual.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabServicoDao;
import com.prime.app.agvirtual.dao.AgvTabSubservicoDao;
import com.prime.app.agvirtual.entity.AgvTabServico;
import com.prime.app.agvirtual.entity.AgvTabSubservico;
import com.prime.app.agvirtual.service.ServicoService;
import com.prime.app.agvirtual.to.ServicoTO;
import com.prime.app.agvirtual.to.SubServicoTO;

@Service
public class ServicoServiceImpl implements ServicoService {

	@Autowired
    private AgvTabServicoDao servDao;
	
	@Autowired
    private AgvTabSubservicoDao subServDao;
	
	@Transactional(readOnly = true)
	public List<ServicoTO> findAll() {
		return servDao.findAll();
	}

	@Transactional(readOnly = true)
	public AgvTabServico save(AgvTabServico t ,List<SubServicoTO> listaSubServicoExcluido ) {
		
		if(listaSubServicoExcluido != null){
			for (Iterator iterator2 = listaSubServicoExcluido.iterator(); iterator2
					.hasNext();) {
				SubServicoTO subServicoTO = (SubServicoTO) iterator2.next();
				subServicoTO.setServico(null);
				subServDao.alterar(subServicoTO.toEntity());
				
			}
		}
		for (Iterator iterator = t.getAgvListaServSubServ().iterator(); iterator.hasNext();) {
			AgvTabSubservico type = (AgvTabSubservico) iterator.next();
			type.setServico(t);
		}
		
		return  servDao.save(t);
	}

	@Transactional(readOnly = true)
	public AgvTabServico findById(Long valueOf, boolean b) {
		return servDao.findById(valueOf, b);
	}
	
	@Transactional(readOnly = true)
	public List<SelectItem> findAllSelectedItems() {
		ArrayList<SelectItem> listaResultado =  new ArrayList<SelectItem>();
		List<ServicoTO> listTemp = servDao.findAll();
		if(listTemp != null){
			for (Iterator<ServicoTO> iterator = listTemp.iterator(); iterator.hasNext();) {
				ServicoTO docTO = (ServicoTO) iterator.next();
				SelectItem item =  new SelectItem(docTO.getCdServico().toString(),docTO.getNmServico());
				listaResultado.add(item);	
			}
		}
		return listaResultado;
	}

	@Transactional(readOnly = true)
	public boolean nameAlreadyExists(String name) {
		
		if( servDao.findByName(name) == null )
			return false;
		else
			return true;
	}
	
	

}
