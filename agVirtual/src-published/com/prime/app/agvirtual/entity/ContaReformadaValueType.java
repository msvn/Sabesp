package com.prime.app.agvirtual.entity;

import com.ibm.icu.math.BigDecimal;

public class ContaReformadaValueType {
	
	private boolean exibir;
	
	private String vlMulta;
	
	private String vlAtualizacaoMonetaria;
	
	private String vlJurosMora;
	
	private String vlDebitos;
	
	private String vlCreditos;
	
	private String vlTotal;
	
	private String vlRetencao;
	
	private String vlServico;

	public String getVlMulta() {
		return vlMulta;
	}

	public void setVlMulta(String vlMulta) {
		this.vlMulta = vlMulta;
	}

	public String getVlAtualizacaoMonetaria() {
		return vlAtualizacaoMonetaria;
	}

	public void setVlAtualizacaoMonetaria(String vlAtualizacaoMonetaria) {
		this.vlAtualizacaoMonetaria = vlAtualizacaoMonetaria;
	}

	public String getVlJurosMora() {
		return vlJurosMora;
	}

	public void setVlJurosMora(String vlJurosMora) {
		this.vlJurosMora = vlJurosMora;
	}

	public String getVlDebitos() {
		return vlDebitos;
	}

	public void setVlDebitos(String vlDebitos) {
		this.vlDebitos = vlDebitos;
	}

	public String getVlCreditos() {
		return vlCreditos;
	}

	public void setVlCreditos(String vlCreditos) {
		this.vlCreditos = vlCreditos;
	}

	public boolean isExibir() {
		return exibir;
	}

	public void setExibir(boolean exibir) {
		this.exibir = exibir;
	}

	public String getVlTotal() {
		BigDecimal creditos = new BigDecimal( String.valueOf(this.vlCreditos) );
		BigDecimal debitos = new BigDecimal( String.valueOf(this.vlDebitos) );
		
		
		
		if(creditos.doubleValue() > 0.0) {
			vlTotal = creditos.subtract(debitos).toString();
		}
			
		return vlTotal;
	}

	public void setVlTotal(String vlTotal) {
		this.vlTotal = vlTotal;
	}

	public String getVlRetencao() {
		return vlRetencao;
	}

	public void setVlRetencao(String vlRetencao) {
		this.vlRetencao = vlRetencao;
	}

	public String getVlServico() {
		return vlServico;
	}

	public void setVlServico(String vlServico) {
		this.vlServico = vlServico;
	}	
	

}
