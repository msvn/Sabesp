package com.prime.app.agvirtual.to;

import java.io.Serializable;

public class TabelaTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1015113882520056280L;

	private String nomeColuna;
	
	private String valorColuna;

	public TabelaTO(String coluna, String valor) {
		this.nomeColuna = coluna;
		this.valorColuna = valor;
	}

	public String getNomeColuna() {
		return nomeColuna;
	}

	public void setNomeColuna(String nomeColuna) {
		this.nomeColuna = nomeColuna;
	}

	public String getValorColuna() {
		return valorColuna;
	}

	public void setValorColuna(String valorColuna) {
		this.valorColuna = valorColuna;
	}
	
	

}
