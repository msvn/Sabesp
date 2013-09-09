package com.prime.app.agvirtual.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

public enum TipoSecao {
	
	SECAO_PRINCIPAL(1, "Sua Conta"),
	
	SECAO_SECUNDARIA_ONE(2, "Consertos"),
	
	SECAO_SECUNDARIA_TWO(3, "Ligações de água e esgoto"),
	
	BARRA_HORIZONTAL(4, "Emergências"),
	
	SECAO_INFERIOR_DIREITA(5, "Dicas");

	private Integer codigo;

	private String realName;

	private TipoSecao(Integer codigo, String realName) {
		this.codigo = codigo;
		this.realName = realName;
	}
	
	public static TipoSecao byValue(Integer value){
		switch (value) {
			case 1: return SECAO_PRINCIPAL;
			case 2: return SECAO_SECUNDARIA_ONE;
			case 3: return SECAO_SECUNDARIA_TWO;
			case 4: return BARRA_HORIZONTAL;
			case 5: return SECAO_INFERIOR_DIREITA;
			default: return null;
		}
	}

	public static List<TipoSecao> getList() {
		return Arrays.asList(values());
	}

	public static List<SelectItem> getListSelectItem() {
		List<TipoSecao> temp = Arrays.asList(values());
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		for (Iterator<TipoSecao> iterator = temp.iterator(); iterator.hasNext();) {
			TipoSecao object = (TipoSecao) iterator.next();
			SelectItem item = new SelectItem(String.valueOf(object.ordinal()), object.realName);
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
