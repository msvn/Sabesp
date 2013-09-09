package com.prime.app.agvirtual.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabItemMenuAcessoDao;
import com.prime.app.agvirtual.entity.AgvTabItemMenuAcesso;
import com.prime.app.agvirtual.service.ItemMenuService;

@Service
public class ItemMenuServiceImpl implements ItemMenuService {

	@Autowired
    private AgvTabItemMenuAcessoDao agvTabItemMenuDao;
	
/*	@Autowired
    private AgvTabFuncionalidadeDao agvTabFuncionalidade;*/
	
	@Transactional(readOnly = true)
	public List<SelectItem> findAllSelectedItems() {
		ArrayList<SelectItem> listaResultado =  new ArrayList<SelectItem>();
		List<AgvTabItemMenuAcesso> listTemp = agvTabItemMenuDao.findAllBloqueio();
		if(listTemp != null){
			for (Iterator<AgvTabItemMenuAcesso> iterator = listTemp.iterator(); iterator.hasNext();) {
				AgvTabItemMenuAcesso docTO = (AgvTabItemMenuAcesso) iterator.next();
				SelectItem item =  new SelectItem(docTO.getCdItemMenu().toString(),docTO.getDsItemMenu());
				listaResultado.add(item);	
			}
		}
		/*List<AgvTabFuncionalidade> listTempFunc = agvTabFuncionalidade.findAllFuncionalidades();
		if(listTempFunc != null){
			for (Iterator iterator = listTempFunc.iterator(); iterator.hasNext();) {
				AgvTabFuncionalidade agvTabFuncionalidade = (AgvTabFuncionalidade) iterator.next();
				SelectItemComplexo item =  new SelectItemComplexo(agvTabFuncionalidade.getCdFuncionalidade().toString(),agvTabFuncionalidade.getDsFuncionalidade(),null,agvTabFuncionalidade);
				listaResultado.add(item);	
			}
		}*/
		return listaResultado;
	}

	@Transactional
	public AgvTabItemMenuAcesso findById(Long valueOf, boolean b) {
		return  agvTabItemMenuDao.findById(valueOf,b);
	}

	@Transactional
	public List<AgvTabItemMenuAcesso> findAll() {
		return  agvTabItemMenuDao.findAll();
	}


}
