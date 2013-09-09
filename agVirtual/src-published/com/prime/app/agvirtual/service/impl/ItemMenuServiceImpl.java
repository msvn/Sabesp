package com.prime.app.agvirtual.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabItemMenuAcessoDao;
import com.prime.app.agvirtual.dao.AgvTabItemMenuGrupoDao;
import com.prime.app.agvirtual.entity.AgvTabItemMenuAcesso;
import com.prime.app.agvirtual.entity.AgvTabItemMenuGrupo;
import com.prime.app.agvirtual.enums.TipoMenu;
import com.prime.app.agvirtual.service.ItemMenuService;

@Service
public class ItemMenuServiceImpl implements ItemMenuService {

	@Autowired
    private AgvTabItemMenuGrupoDao agvTabItemMenuGrupoDao;
	
	@Autowired
    private AgvTabItemMenuAcessoDao agvTabItemMenuAcessoDao;

	@Transactional(readOnly = true)
	public AgvTabItemMenuGrupo findGrupoById(Long id, boolean b) {
		return agvTabItemMenuGrupoDao.findById(id, Boolean.FALSE);
	}
	
	@Transactional(readOnly = true)
	public List<AgvTabItemMenuAcesso> findAcessoByGrupo(AgvTabItemMenuGrupo item) {
		return agvTabItemMenuAcessoDao.findByGrupo(item);
	}
	
	@Transactional(readOnly = true)
	public List<AgvTabItemMenuAcesso> pesquisaTodosItensMenu() {
		return agvTabItemMenuAcessoDao.pesquisaTodosItensMenu();
	}
	
	
	
	@Transactional(readOnly = true)
	public List<SelectItem> findAllSelectedItems() {
		ArrayList<SelectItem> listaResultado =  new ArrayList<SelectItem>();
		List<AgvTabItemMenuAcesso> listTemp = agvTabItemMenuAcessoDao.findAllBloqueio();
		if(listTemp != null){
			for (Iterator<AgvTabItemMenuAcesso> iterator = listTemp.iterator(); iterator.hasNext();) {
				AgvTabItemMenuAcesso docTO = (AgvTabItemMenuAcesso) iterator.next();
				SelectItem item =  new SelectItem(docTO.getCdItemMenu().toString(),docTO.getDsItemMenu());
				listaResultado.add(item);	
			}
		}
		return listaResultado;
	}

	@Transactional
	public AgvTabItemMenuAcesso findAcessoById(Long id, boolean b) {
		return  agvTabItemMenuAcessoDao.findById(id, b);
	}

	@Transactional(readOnly = true)
	public List<AgvTabItemMenuGrupo> findGrupoByItemPai(String cdItemPai) {
		return agvTabItemMenuGrupoDao.findByItemPai(cdItemPai);
	}


	@Transactional(readOnly = true)
	public List<AgvTabItemMenuAcesso> pesquisaTodosLinks() {
		return agvTabItemMenuAcessoDao.findAllBloqueio();
	}

	@Transactional(readOnly = true)
	public TipoMenu pesquisaMenuItemPertence(String idItemMenuSelected) {
		return agvTabItemMenuAcessoDao.pesquisaMenuItemPertence(idItemMenuSelected);
	}
	
}
