package com.prime.app.agvirtual.web.jsf;

import javax.faces.event.ActionEvent;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session")
public class BasicSessionBBean {
	
	private String str;
	
	public void concat(ActionEvent e){
		str += "xpto|";
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
}
