package com.prime.app.agvirtual.web.jsf;


import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.service.ContaService;

@Component
@Scope(value="request")
public class BasicRequestBBean {
	private static final Logger logger = LoggerFactory.getLogger(BasicRequestBBean.class);
	
	@Autowired private ContaService contaService; 
	
	public BasicRequestBBean(){
		logger.info("contaService at CONSTRUCTOR = " + contaService);
	}
	
	private String str;
	
	public void concat(ActionEvent e){
		str += "xpto|";
		
		logger.info("contaService on CONCAT = " + contaService);
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	
}
