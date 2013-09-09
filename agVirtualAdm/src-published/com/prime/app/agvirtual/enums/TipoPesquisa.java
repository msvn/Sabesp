package com.prime.app.agvirtual.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

public enum TipoPesquisa {
	
	SATISFACAO(1, "Satisfação"),
	
	ESPECIFICA(2, "Específica");

	private Integer codigo;

	private String realName;

	private TipoPesquisa(Integer codigo, String realName) {
		this.codigo = codigo;
		this.realName = realName;
	}
	
	public static TipoPesquisa byValue(Integer value){
		switch (value) {
			case 1: return SATISFACAO;
			case 2: return ESPECIFICA;
			default: return null;
		}
	}

	public static List<TipoPesquisa> getList() {
		return Arrays.asList(values());
	}

	public static List<SelectItem> getListSelectItem() {
		List<TipoPesquisa> temp = Arrays.asList(values());
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		for (Iterator<TipoPesquisa> iterator = temp.iterator(); iterator.hasNext();) {
			TipoPesquisa object = (TipoPesquisa) iterator.next();
			SelectItem item = new SelectItem(object.codigo, object.realName);
			lista.add(item);
		}
		return lista;
	}

	public static void main(String[] args) {
		getListSelectItem();
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public String getRealName() {
		return realName;
	}
	
}
