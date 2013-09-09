package com.prime.app.agvirtual.web.jsf.test;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.prime.infra.web.jsf.BasicBBean;

@Component
public class SegundaViaContaBBean extends BasicBBean {
	private String radioSelecionada = "PROSSEGUIR_RGI";
	
	private String itemContent;
	private List<String> loadList = new ArrayList<String>();
	
	
	// handle request
	public void loadItem(ActionEvent e){
		logger.info("running loadItem");
		if(itemContent!=null) loadList.add("xpto");
	}
	public String runLink(){
		logger.info("running runLink");
		return radioSelecionada;
	}
	
	// load data
	public void loadList(){
		loadList.add("Sabesp 01");
		loadList.add("Sabesp 02");
		loadList.add("Sabesp 03");
	}
	
	// get / set
	public String getRadioSelecionada() {
		return radioSelecionada;
	}

	public void setRadioSelecionada(String radioSelecionada) {
		this.radioSelecionada = radioSelecionada;
	}

	public String getItemContent() {
		logger.info("itemContent=" + itemContent);
		return itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public List<String> getLoadList() {
		System.out.println("running getLoadList()");
		return loadList;
	}

	public void setLoadList(List<String> loadList) {
		this.loadList = loadList;
	}

}
