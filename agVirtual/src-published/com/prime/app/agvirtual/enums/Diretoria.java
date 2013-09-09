package com.prime.app.agvirtual.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

public enum Diretoria implements Serializable {
	
	SELECIONE("(Selecione)"),TODAS("Todas"),M("M"),R("R");
	
    private String realName;

    private Diretoria(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }
    
    public static String getString(Integer value){
		switch( value ) {
			case 1: return "Todas";
			case 2: return "M";
			case 3: return "R";
			default : return null;
		}
	}

    public static List<Diretoria> getList() {
        return Arrays.asList(values());
    }

	public static List<SelectItem> getListSelectItem() {
		List temp  = Arrays.asList(values());
		ArrayList<SelectItem> lista = new ArrayList();
		for (Iterator iterator = temp.iterator(); iterator.hasNext();) {
			Diretoria object = (Diretoria) iterator.next();
			SelectItem item = new SelectItem(String.valueOf(object.ordinal()),object.realName );
			lista.add(item);
		}
		return lista;
	}
	
	public static void main(String[] args) {
		getListSelectItem();
	}
}
