package com.prime.app.agvirtual.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Referente ao compo STCONSUMO da tabela cea15
 * @author gustavons
 *
 */
public enum TipoConsumo {
	
	REAL("R","Real"),

	EMISSAO_PELA_MEDIA("M","Emissão pela Média"),

	SEM_EMISSAO_PELA_MEDIA("N" ,"Sem Emissão no Mês"),

	SUBSTITUIDO("S","Substituído"),

	RODIZIO("Z","Rodízio"), 

	TROCA_HIDROMETRO("T","Troca de Hidrômetro"),

	INATIVO("I","Inativo");

	private String codigo;

	private String realName;

	private TipoConsumo(String codigo, String realName) {
		this.codigo = codigo;
		this.realName = realName;
	}
	
	public static TipoConsumo byValue(String value){
			
		if(value != null){
			if(value.equals("R")){
				return REAL;
			}else
			if(value.equals("M")){
				return EMISSAO_PELA_MEDIA;
			}else
			if(value.equals("N")){
				return SEM_EMISSAO_PELA_MEDIA;
			}else
			if(value.equals("S")){
				return SUBSTITUIDO;
			}else
			if(value.equals("Z")){
				return RODIZIO;
			}else
			if(value.equals("T")){
				return TROCA_HIDROMETRO;
			}else
			if(value.equals("I")){
				return INATIVO;
			}
		}
		return REAL;
	}

	public static List<TipoConsumo> getList() {
		return Arrays.asList(values());
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getRealName() {
		return realName;
	}
}
