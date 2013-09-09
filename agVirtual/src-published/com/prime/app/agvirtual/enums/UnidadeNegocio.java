package com.prime.app.agvirtual.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * 
 * @author gustavons
 *
 */
public enum UnidadeNegocio implements Serializable {
	
	NORTE("Norte"),Sul("Sul"),LESTE("Leste"),OESTE("Oeste"),CENTRO("Centro"),TODAS("Todas");
	
    private String realName;

    private UnidadeNegocio(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public static List<UnidadeNegocio> getList() {
        return Arrays.asList(values());
    }

	public static List<SelectItem> getListSelectItem() {
		List<UnidadeNegocio> temp  = Arrays.asList(values());
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		for (Iterator<UnidadeNegocio> iterator = temp.iterator(); iterator.hasNext();) {
			UnidadeNegocio object = (UnidadeNegocio) iterator.next();
			SelectItem item = new SelectItem(String.valueOf(object.ordinal()),object.realName );
			lista.add(item);
		}
		return lista;
	}
	
	public static void main(String[] args) {
		getListSelectItem();
	}
}
