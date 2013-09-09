package com.prime.app.agvirtual.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import com.prime.infra.util.WrapperUtils;

public enum SIMNAOEnum {
	
	SIM("Sim",1), NAO("Não",2);
	
	private String value;
	
	public String getValue() {
		return value;
	}
	
	private int codigo;

	private SIMNAOEnum(String value, int codigo) {
		this.value = value;
		this.codigo = codigo;
	}
	
	public static List<SelectItem> getListSelectItem() {
		List temp = Arrays.asList(values());
		ArrayList<SelectItem> lista = new ArrayList();
		for (Iterator iterator = temp.iterator(); iterator.hasNext();) {
			SIMNAOEnum object = (SIMNAOEnum) iterator.next();
			SelectItem item = new SelectItem(String.valueOf(object.getCodigo()), String.valueOf(object.getValue()));
			lista.add(item);
		}
		return lista;
	}

	public String getCodigoString(){
		return WrapperUtils.toString(codigo);
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
