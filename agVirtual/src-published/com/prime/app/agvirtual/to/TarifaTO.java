package com.prime.app.agvirtual.to;

import java.io.Serializable;

public class TarifaTO implements Serializable {
	public static final int TODAS = 0;
	public static final int RESIDENCIAL = 1;
	public static final int COMERCIAL = 2;
	public static final int INDUSTRIAL = 3;
	public static final int PUBLICA = 4;
	public static final int COMUM = 0;  //normal
	public static final int SOCIAL = 1; 
	public static final int FAVELA = 3; //social2
	public static final int ASSISTENCIAL = 2;
	public static final int COM_CONTRATO = 4;
	public static final int CONTRATO_DE_PROGRAMA = 7;

	public static final String RESIDENCIAL_LBL = "Residencial";
	public static final String COMERCIAL_LBL = "Comercial";
	public static final String INDUSTRIAL_LBL = "Industrial";
	public static final String PUBLICA_LBL = "Pública";
	public static final String COMUM_LBL = "";
	public static final String SOCIAL_LBL = "Social";
	public static final String FAVELA_LBL = "Favela";
	public static final String ASSISTENCIAL_LBL = "Assistencial";
	public static final String COM_CONTRATO_LBL = "com contrato";
	public static final String CONTRATO_DE_PROGRAMA_LBL = "contrato de programa";
	
	public static final String TARIFAS_ATUAIS = "1";
	public static final String TARIFAS_ANTERIORES = "2";
	public static final String PROXIMAS_TARIFAS = "3";
	
	

	public static final String CONSUMO_10="0 a 10";
	public static final String CONSUMO_20="21 a 30";
	public static final String CONSUMO_30="11 a 20";
	public static final String CONSUMO_50="31 a 50";
	public static final String CONSUMO_999999="acima de 50";

	private static final long serialVersionUID = -8640478271268135816L;

	private Long codigo;

	private String classeConsumo;
	
	private String tarifaAgua;
	
	private String tarifaEsgoto;
	
	private Long cdTipoTarifa;
	
	private String nmTipoTarifa;
	
	private Long cdTipoCobranca;
	
	private int count;
	
	public TarifaTO() {
		
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getClasseConsumo() {
		return classeConsumo;
	}

	public void setClasseConsumo(String classeConsumo) {
		this.classeConsumo = classeConsumo;
	}

	public String getTarifaAgua() {
		return tarifaAgua;
	}

	public void setTarifaAgua(String tarifaAgua) {
		this.tarifaAgua = tarifaAgua;
	}

	public String getTarifaEsgoto() {
		return tarifaEsgoto;
	}

	public void setTarifaEsgoto(String tarifaEsgoto) {
		this.tarifaEsgoto = tarifaEsgoto;
	}

	public Long getCdTipoTarifa() {
		return cdTipoTarifa;
	}

	public void setCdTipoTarifa(Long cdTipoTarifa) {
		this.cdTipoTarifa = cdTipoTarifa;
	}

	public String getNmTipoTarifa() {
		return nmTipoTarifa;
	}

	public void setNmTipoTarifa(String nmTipoTarifa) {
		this.nmTipoTarifa = nmTipoTarifa;
	}

	public Long getCdTipoCobranca() {
		return cdTipoCobranca;
	}

	public void setCdTipoCobranca(Long cdTipoCobranca) {
		this.cdTipoCobranca = cdTipoCobranca;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
