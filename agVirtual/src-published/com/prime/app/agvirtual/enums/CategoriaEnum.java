package com.prime.app.agvirtual.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import com.prime.infra.util.WrapperUtils;

public enum CategoriaEnum implements Serializable {
	
	SELECIONE("(Selecione)"),AGUA("Agua"),CONTA("Conta"),ESGOTO("Esgoto");
	
    private String realName;

    private CategoriaEnum(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public static List<CategoriaEnum> getList() {
        return Arrays.asList(values());
    }

    public static String getLabel(String value){
    	if(WrapperUtils.isNotNull(value)){
			switch( WrapperUtils.toInt(value) ) {
				case 1: return "Agua";
				case 2: return "Conta";
				case 3: return "Esgoto";
				default : return null;
			}
    	}
    	return null;
	}
    
	public static List<SelectItem> getListSelectItem() {
		List temp  = Arrays.asList(values());
		ArrayList<SelectItem> lista = new ArrayList();
		for (Iterator iterator = temp.iterator(); iterator.hasNext();) {
			CategoriaEnum object = (CategoriaEnum) iterator.next();
			SelectItem item = new SelectItem(String.valueOf(object.ordinal()),object.realName );
			lista.add(item);
		}
		return lista;
	}
	
	public static void main(String[] args) {
		getListSelectItem();
	}
}
