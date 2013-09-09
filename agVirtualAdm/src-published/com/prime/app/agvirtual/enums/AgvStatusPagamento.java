package com.prime.app.agvirtual.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * Status de Pagamento utilizados no CSI
 * @author gustavons
 *
 */
public enum AgvStatusPagamento {
	
	EM_ABERTO(0, "Em aberto"),
	QUITADO_AGENCIA(1, "Quitado pela agência"),
	BAIXA_PARCIAL(2, "Baixa parcial (em relação ao total do débito)"),
	BAIXADO(3, "Baixado"),
	SUSPENSO(4, "Suspenso (em revisão)"),
	CANCELADO_POR_REFORMA(5, "Cancelado (por reforma – cancelamento de débito)"),
	CANCELAMENTO_AUTOMATICO_DEBITO(6, "Cancelamento automático de débito"),
	PARCELAMENTO_EM_LOTE(7, "Parcelamento em lote"),
	CANCELAMENTO_ACORDO_DE_DEBITO_PENDENTE(8, "Cancelamento de acordo de déb. pendente");
	
	private Integer codigo;

	private String realName;

	private AgvStatusPagamento(Integer codigo, String realName) {
		this.codigo = codigo;
		this.realName = realName;
	}
	
	public static AgvStatusPagamento byValue(Integer value){
		switch (value) {
			case 0: return EM_ABERTO;
			case 1: return QUITADO_AGENCIA;
			case 2: return BAIXA_PARCIAL;
			case 3: return BAIXADO;
			case 4: return SUSPENSO;
			case 5: return CANCELADO_POR_REFORMA;
			case 6: return CANCELAMENTO_AUTOMATICO_DEBITO;
			case 7: return PARCELAMENTO_EM_LOTE;
			case 8: return CANCELAMENTO_ACORDO_DE_DEBITO_PENDENTE;
			default: return null;
		}
	}

	public static List<AgvStatusPagamento> getList() {
		return Arrays.asList(values());
	}

	public static List<SelectItem> getListSelectItem() {
		List<AgvStatusPagamento> temp = Arrays.asList(values());
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		for (Iterator<AgvStatusPagamento> iterator = temp.iterator(); iterator.hasNext();) {
			AgvStatusPagamento object = (AgvStatusPagamento) iterator.next();
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
