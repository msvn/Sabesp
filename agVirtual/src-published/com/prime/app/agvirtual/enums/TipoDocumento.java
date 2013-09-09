package com.prime.app.agvirtual.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

public enum TipoDocumento {
	LINK("Link"), DOCUMENTO("Documento");

	private String realName;

	private TipoDocumento(String realName) {
		this.realName = realName;
	}

	public String getRealName() {
		return realName;
	}

	public static List<TipoDocumento> getList() {
		return Arrays.asList(values());
	}

	public static List<SelectItem> getListSelectItem() {
		List temp = Arrays.asList(values());
		ArrayList<SelectItem> lista = new ArrayList();
		for (Iterator iterator = temp.iterator(); iterator.hasNext();) {
			TipoDocumento object = (TipoDocumento) iterator.next();
			SelectItem item = new SelectItem(String.valueOf(object.ordinal()), object.realName);
			lista.add(item);
		}
		return lista;
	}

	public static void main(String[] args) {
		getListSelectItem();
	}
}
