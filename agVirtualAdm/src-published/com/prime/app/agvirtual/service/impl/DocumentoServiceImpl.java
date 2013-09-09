package com.prime.app.agvirtual.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prime.app.agvirtual.dao.AgvTabDocumentoDao;
import com.prime.app.agvirtual.entity.AgvTabDocumento;
import com.prime.app.agvirtual.service.DocumentoService;
import com.prime.app.agvirtual.to.DocumentoTO;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    @Autowired
    AgvTabDocumentoDao docDao;
    
	public List<DocumentoTO> findAll() {
		return docDao.findAll();
	}

	public List<SelectItem> findAllSelectedItems() {
		ArrayList<SelectItem> listaResultado =  new ArrayList<SelectItem>();
		List<DocumentoTO> listTemp = docDao.findAll();
		if(listTemp != null){
			for (Iterator<DocumentoTO> iterator = listTemp.iterator(); iterator.hasNext();) {
				DocumentoTO docTO = (DocumentoTO) iterator.next();
				SelectItem item =  new SelectItem(docTO.getCdDocumento().toString(),docTO.getNmDocumento());
				listaResultado.add(item);	
			}
		}
		return listaResultado;
	}

	public AgvTabDocumento findById(Long valueOf, boolean b) {
		return docDao.findById(valueOf,b);
	}

}
