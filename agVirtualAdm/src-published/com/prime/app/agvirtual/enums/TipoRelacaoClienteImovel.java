package com.prime.app.agvirtual.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * Utilizado na pesquisa de cliente / Atendimento
 * @author gustavons
 *
 */
public enum TipoRelacaoClienteImovel {
	
	PROPRIETARIO(1, "Proprietário"),
	
	LOCATARIO(2, "Locatário"),
	
	REPRESENTANTEPROPRIETARIO (3 ,"Representante do proprietário"),

	REPRESENTANTELOCATARIO (4 , "Representante do Locatário"),

	ADMIMOVEIS(5,"Administrador de Imóveis");

	private Integer codigo;

	private String realName;

	private TipoRelacaoClienteImovel(Integer codigo, String realName) {
		this.codigo = codigo;
		this.realName = realName;
	}
	
	public static TipoRelacaoClienteImovel byValue(Integer value){
		switch (value) {
			case 1: return PROPRIETARIO;
			case 2: return LOCATARIO;
			default: return null;
		}
	}

	public static List<TipoRelacaoClienteImovel> getList() {
		return Arrays.asList(values());
	}

	public static List<SelectItem> getListSelectItem() {
		List<TipoRelacaoClienteImovel> temp = Arrays.asList(values());
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		for (Iterator<TipoRelacaoClienteImovel> iterator = temp.iterator(); iterator.hasNext();) {
			TipoRelacaoClienteImovel object = (TipoRelacaoClienteImovel) iterator.next();
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
