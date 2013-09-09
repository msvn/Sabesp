package com.prime.app.agvirtual.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * Mapeamento dos Ids de AutoAtendimento / Tabela AutoAtendimento oracle 
 * @author gustavons
 *
 */
public enum AutoAtendimentoEnum {
	
	ESTOU_SEM_AGUA(1L, "Estou Sem Água"),
	MUDANCA_LIGACAO_AGUA(2L, "Mudança da Ligação de Água"),
	MUDANCA_LIGACAO_ESGOTO(3L, "Mudança da Ligação de Esgoto"),
	ESGOTO_ENTUPIDO(4L, "Esgoto Entupido"),
	POUCA_PRESSAO(5L,"Pouca Pressão"), 
	CONSERTO_CAVALETE(7L,"Conserto de Cavalete"),
	CONSERTO_HIDROMETRO(8L,"Conserto de Hidrômetro"),
	CONSERTO_REGISTRO(9L,"Conserto de Registro"),
	VAZAMENTO_REDE(6l,"Vazamento na Rede");
	
	private Long codigo;

	private String realName;

	private AutoAtendimentoEnum(Long codigo, String realName) {
		this.codigo = codigo;
		this.realName = realName;
	}
	
	public static AutoAtendimentoEnum byValue(Integer value){
		switch (value) {
			case 1: return ESTOU_SEM_AGUA;
			case 2: return MUDANCA_LIGACAO_AGUA;
			case 3: return MUDANCA_LIGACAO_ESGOTO;
			case 4: return ESGOTO_ENTUPIDO;
			case 5: return POUCA_PRESSAO;
			case 6: return VAZAMENTO_REDE;
			case 7: return CONSERTO_CAVALETE;
			case 8: return CONSERTO_HIDROMETRO;
			case 9: return CONSERTO_REGISTRO;
			default: return null;
		}
	}

	public static List<AutoAtendimentoEnum> getList() {
		return Arrays.asList(values());
	}

	public static List<SelectItem> getListSelectItem() {
		List<AutoAtendimentoEnum> temp = Arrays.asList(values());
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		for (Iterator<AutoAtendimentoEnum> iterator = temp.iterator(); iterator.hasNext();) {
			AutoAtendimentoEnum object = (AutoAtendimentoEnum) iterator.next();
			SelectItem item = new SelectItem(String.valueOf(object.ordinal()), object.realName);
			lista.add(item);
		}
		return lista;
	}

	public static void main(String[] args) {
		getListSelectItem();
	}
	
	public Long getCodigo() {
		return codigo;
	}
	
	public String getRealName() {
		return realName;
	}

}
