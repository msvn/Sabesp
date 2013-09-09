package com.prime.app.agvirtual.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * TOdos Status de Conta utilizado pelo CSI
 * @author gustavons
 *
 */
public enum AgvStatusConta {
	
	VALIDO(0, "Válido"),
	PARCELADA(1, "Parcelada (CFRAB)"),
	REFORMADA(2, "Reformada (CFRAC)"),
	ACORDO_ROMPIDO(3, "Acordo rompido (com saldo do parcelamento em CFRAB)"),
	DEBITO_PENDENTE(4, "Débito pendente (CFRAE, CFRAF, CFRAG)"),
	VENCIMENTO_POSTERGADO(5, "Vencimento postergado"),
	SUSPENSO(6, "Suspenso"),
	CANCELADO_SISTEMA(7, "Cancelado no sistema"),
	CANCELAMENTO_PRIMEIRA_PARCELA(8, "Cancelamento 1ª parcela"),
	CANCELAMENTO_TEMP_REFORMA(9, "Cancelamento temporário por reforma");
	
	private Integer codigo;

	private String realName;

	private AgvStatusConta(Integer codigo, String realName) {
		this.codigo = codigo;
		this.realName = realName;
	}
	
	public static AgvStatusConta byValue(Integer value){
		switch (value) {
			case 0: return VALIDO;
			case 1: return PARCELADA;
			case 2: return REFORMADA;
			case 3: return ACORDO_ROMPIDO;
			case 4: return DEBITO_PENDENTE;
			case 5: return VENCIMENTO_POSTERGADO;
			case 6: return SUSPENSO;
			case 7: return CANCELADO_SISTEMA;
			case 8: return CANCELAMENTO_PRIMEIRA_PARCELA;
			case 9: return CANCELAMENTO_TEMP_REFORMA;
			default: return null;
		}
	}

	public static List<AgvStatusConta> getList() {
		return Arrays.asList(values());
	}

	public static List<SelectItem> getListSelectItem() {
		List<AgvStatusConta> temp = Arrays.asList(values());
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		for (Iterator<AgvStatusConta> iterator = temp.iterator(); iterator.hasNext();) {
			AgvStatusConta object = (AgvStatusConta) iterator.next();
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
