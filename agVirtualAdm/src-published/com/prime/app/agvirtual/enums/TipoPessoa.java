package com.prime.app.agvirtual.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

public enum TipoPessoa implements Serializable {
	
	FISICA("Física"),JURIDICO("Jurídica"),AMBOS("Ambos");
	
    private String realName;

    private TipoPessoa(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public static List<TipoPessoa> getList() {
        return Arrays.asList(values());
    }

	public static List<SelectItem> getListSelectItem() {
		List temp  = Arrays.asList(values());
		ArrayList<SelectItem> lista = new ArrayList();
		for (Iterator iterator = temp.iterator(); iterator.hasNext();) {
			TipoPessoa object = (TipoPessoa) iterator.next();
			SelectItem item = new SelectItem(String.valueOf(object.ordinal()),object.realName );
			lista.add(item);
		}
		return lista;
	}
	
	public static void main(String[] args) {
		getListSelectItem();
	}
}
