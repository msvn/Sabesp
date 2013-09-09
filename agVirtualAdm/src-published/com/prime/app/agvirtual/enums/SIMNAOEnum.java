package com.prime.app.agvirtual.enums;

public enum SIMNAOEnum {
	
	SIM("Sim"), NAO("N�o");
	
	private String value;
	
	private SIMNAOEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
