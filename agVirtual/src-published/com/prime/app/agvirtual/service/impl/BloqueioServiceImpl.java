package com.prime.app.agvirtual.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabBloqueioDao;
import com.prime.app.agvirtual.dao.AgvTabBloqueioDetalheDao;
import com.prime.app.agvirtual.dao.AgvTabItemMenuAcessoDao;
import com.prime.app.agvirtual.entity.AgvTabBloqueioDetalhe;
import com.prime.app.agvirtual.entity.AgvTabBloqueio;
import com.prime.app.agvirtual.entity.AgvTabItemMenuAcesso;
import com.prime.app.agvirtual.service.BloqueioService;
import com.prime.app.agvirtual.to.BloqueioTO;
import com.prime.app.agvirtual.to.MunicipioTO;

@Service
public class BloqueioServiceImpl implements BloqueioService {
	
	private static final Logger agvlogger = LoggerFactory.getLogger(BloqueioServiceImpl.class);

	@Autowired
    private AgvTabBloqueioDao bloqueioDao;
	
	@Autowired
    private AgvTabBloqueioDetalheDao bloqueioDetalheDao;
	
	@Autowired
	private AgvTabItemMenuAcessoDao itemMenuAcessoDao;
	
	
	@Transactional
	public AgvTabBloqueio save(BloqueioTO to) {
		
		AgvTabBloqueio temp = to.toEntity();
					
		ArrayList<AgvTabBloqueioDetalhe> lista =  new ArrayList<AgvTabBloqueioDetalhe>();
		
		if(to.isTodosMunicipios() && to.isTodoasFuncionalidades()){
			AgvTabBloqueioDetalhe detalhe = new AgvTabBloqueioDetalhe();
//			detalhe.setTodosFuncionalidade(true);
//			detalhe.setTodosMunicipios(true);
			lista.add(detalhe);
		}else if(to.isTodosMunicipios()){ //Insere todos os detalhes com flag municipios = true
			for (Iterator iterator = to.getServicos().iterator(); iterator.hasNext();) {
				AgvTabItemMenuAcesso servicoElement = (AgvTabItemMenuAcesso) iterator.next();
				AgvTabBloqueioDetalhe detalhe = new AgvTabBloqueioDetalhe();
//				detalhe.setCdMunicipio(ufElement.getCodUf());
//				detalhe.setTodosMunicipios(true);
//				detalhe.setTodosFuncionalidade(false);
				detalhe.setCdUn("1"); //FIXME revisar regra
				detalhe.setAgvTabItemMenuAcesso(servicoElement);
				lista.add(detalhe);
			}
		}else if(to.isTodoasFuncionalidades()){  //Insere todos os detalhes com flag funcionalidades = true
			for (Iterator iterUf = to.getUfs().iterator(); iterUf.hasNext();) {
				MunicipioTO ufElement = (MunicipioTO) iterUf.next();
				AgvTabBloqueioDetalhe detalhe = new AgvTabBloqueioDetalhe();
				detalhe.setCdMunicipio(ufElement.getCodUf());
				detalhe.setCdUn("1"); //FIXME revisar regra
//				detalhe.setTodosFuncionalidade(true);
//				detalhe.setTodosMunicipios(false);
//				detalhe.setAgvTabItemMenuAcesso(servicoElement);
				lista.add(detalhe);
			}
		}else{ //Iterage todos os municipos e Funcionalidades selecionados
		
			for (Iterator iterator = to.getServicos().iterator(); iterator.hasNext();) {
				AgvTabItemMenuAcesso servicoElement = (AgvTabItemMenuAcesso) iterator.next();
				try{
					for (Iterator iterUf = to.getUfs().iterator(); iterUf.hasNext();) {
						MunicipioTO ufElement = (MunicipioTO) iterUf.next();
						AgvTabBloqueioDetalhe detalhe = new AgvTabBloqueioDetalhe();
						detalhe.setCdMunicipio(ufElement.getCodUf());
						detalhe.setCdUn("1"); //FIXME revisar regra
						detalhe.setAgvTabItemMenuAcesso(servicoElement);
						lista.add(detalhe);
					}
				}catch (NoSuchElementException e) {
					agvlogger.error("Exception:"+e.getMessage());
				}
			}
		}
		
		AgvTabBloqueio tempBloq = bloqueioDao.save(temp);
		bloqueioDetalheDao.delete(tempBloq);
		itemMenuAcessoDao.save(lista,tempBloq);
		return temp;
	}

	@Transactional
	public List findAll() {
		return bloqueioDao.findAll();
	}

	@Transactional
	public boolean isItemMenuBloqueado(String cdItemMenu, String cdMunicipio) {
		return bloqueioDao.isItemMenuBloqueado(cdItemMenu,cdMunicipio);
	}
}
