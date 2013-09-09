package com.prime.app.agvirtual.web.jsf.test;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.CanalAtendimento;
import com.prime.app.agvirtual.service.CanalAtendimentoService;
import com.prime.app.agvirtual.service.MunicipioService;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.infra.web.jsf.BasicBBean;

@Component
public class ContaMBBean extends BasicBBean {
	private static final long serialVersionUID = 7777784265132316143L;
	private static final Logger agvlogger = LoggerFactory.getLogger(ContaMBBean.class);

	@Autowired private CanalAtendimentoService canaisAtendimentoService;
	@Autowired private MunicipioService municipioService;

	private String nomeCanalAtendimento = "Simple text";
	private Boolean flagShowLinks = Boolean.FALSE;
	private List<CanalAtendimento> listaCanaisAtendimento;
	private List<MunicipioTO> municipios;
	
//	// constructor
//	public ContaMBBean(){
//		municipios = municipioService.findAll();
//		logger.debug("Municipios inicialized now !!!"); agvlogger.debug("Municipios inicialized now !!!");
//	}
	
	// handle requests
	public void handleRequest(ActionEvent e) {
		changeNomeCanalAtd("xpto");
	}

	public void showLinks(ActionEvent e) {
		flagShowLinks = Boolean.TRUE;
	}

	
	// work with data
	private void changeNomeCanalAtd(String nome) {
		nomeCanalAtendimento = nome;
	}
	private void loadMunicipios(){
		municipios = municipioService.findAll();
		logger.debug("Municipios inicialized now !!!"); agvlogger.debug("Municipios inicialized now !!!");
	}

	// get / set
	public List<CanalAtendimento> getListaCanaisAtendimento() {
		listaCanaisAtendimento = canaisAtendimentoService.findAll();
		return listaCanaisAtendimento;
	}

	public void setListaCanaisAtendimento(
			List<CanalAtendimento> listaCanaisAtendimento) {
		this.listaCanaisAtendimento = listaCanaisAtendimento;
	}

	public String getNomeCanalAtendimento() {
		return nomeCanalAtendimento;
	}

	public void setNomeCanalAtendimento(String nomeCanalAtendimento) {
		this.nomeCanalAtendimento = nomeCanalAtendimento;
	}

	public Boolean getFlagShowLinks() {
		return flagShowLinks;
	}

	public void setFlagShowLinks(Boolean flagShowLinks) {
		this.flagShowLinks = flagShowLinks;
	}

	public List<MunicipioTO> getMunicipios() {
		loadMunicipios();
		return municipios;
	}

	public void setMunicipios(List<MunicipioTO> municipios) {
		this.municipios = municipios;
	}
	
	

}
