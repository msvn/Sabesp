package com.prime.app.agvirtual.enums;

public enum TipoPesquisa {
	
	TIPO_PESQUISA_RGI(1 , "RGI"),
	
	TIPO_PESQUISA_ENDERECO(2, "ENDERECO"),
	
	TIPO_PESQUISA_RGI_NUMRO(3, "RGI_NUM");
	
	private Integer codigo;

	private String realName;
	
	private TipoPesquisa(Integer codigo, String realName) {
		this.codigo = codigo;
		this.realName = realName;
	}
	
	public static TipoPesquisa byValue(Integer value){
		switch( value ) {
			case 1: return TIPO_PESQUISA_RGI;
			case 2: return TIPO_PESQUISA_ENDERECO;
			case 3: return TIPO_PESQUISA_RGI_NUMRO;
			default : return null;
		}
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public String getRealName() {
		return realName;
	}
	
}
