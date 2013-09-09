package com.prime.app.agvirtual.service;

import java.util.List;

import javax.faces.model.SelectItem;

import com.prime.app.agvirtual.entity.AgvTabItemMenuAcesso;
import com.prime.app.agvirtual.entity.AgvTabItemMenuGrupo;
import com.prime.app.agvirtual.enums.TipoMenu;

public interface ItemMenuService {
	
	public AgvTabItemMenuAcesso findAcessoById(Long id, boolean b);
	
	public AgvTabItemMenuGrupo findGrupoById(Long id, boolean b);

	public List<AgvTabItemMenuGrupo> findGrupoByItemPai(String cdItemPai);
	
	public List<AgvTabItemMenuAcesso> findAcessoByGrupo(AgvTabItemMenuGrupo item);
	
	public List<SelectItem> findAllSelectedItems();
	
	public List<AgvTabItemMenuAcesso> pesquisaTodosItensMenu();

	public TipoMenu pesquisaMenuItemPertence(String idItemMenuSelected);
}
 