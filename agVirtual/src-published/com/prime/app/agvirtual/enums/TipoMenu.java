package com.prime.app.agvirtual.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * Enum utilizado na divisão dos Menus
 * @author gustavons
 *
 */
public enum TipoMenu {
	
	SUA_CONTA(1, "Sua Conta" , "SUBMENUSUACONTA"),
	
	LIGACOES_AGUA_ESGOTO(2, "Ligações de Água e Esgoto" , "SUBMENULIGACOESAGUAESGOTO"),
	
	CONSERTOS(3, "Consertos" , "SUBMENUCONSERTOS"),
	
	EMERGENCIAS(4, "Emergências" , "SUBMENUEMERGENCIAS"),
	
	CORPORATIVO(5, "Corporativo", "SUBMENUCORPORATIVO"),
	
	DICAS_INFORMACOES(6, "Dicas e Informações","SUBMENUDICASINFORMACOES"),
	
	TARIFAS(7, "Tarifas" , "SUBMENUTARIFAS");

	private Integer codigo;

	private String realName;
	
	private String outcome;

	private TipoMenu(Integer codigo, String realName , String outcome) {
		this.codigo = codigo;
		this.realName = realName;
		this.outcome = outcome;
	}
	
	public static TipoMenu byValue(Integer value){
		switch (value) {
			case 1: return SUA_CONTA;
			case 2: return LIGACOES_AGUA_ESGOTO;
			case 3: return CONSERTOS;
			case 4: return EMERGENCIAS;
			case 5: return CORPORATIVO;
			case 6: return DICAS_INFORMACOES;
			case 7: return TARIFAS;
			default: return null;
		}
	}
	
	public static List<TipoMenu> getList() {
		return Arrays.asList(values());
	}

	public static List<SelectItem> getListSelectItem() {
		List<TipoMenu> temp = Arrays.asList(values());
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		for (Iterator<TipoMenu> iterator = temp.iterator(); iterator.hasNext();) {
			TipoMenu object = (TipoMenu) iterator.next();
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

	public String getOutcome() {
		return outcome;
	}
	
}
