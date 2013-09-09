package com.prime.app.agvirtual.entity;

import java.io.Serializable;

public class Bairro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125726431317731464L;
	
	public Bairro(){}
	
	private String cdMunicipio;
	
	private String cdBairro;
	
	private String nmBairro;

	public String getCdMunicipio() {
		return cdMunicipio;
	}

	public void setCdMunicipio(String cdMunicipio) {
		this.cdMunicipio = cdMunicipio;
	}

	public String getCdBairro() {
		return cdBairro;
	}

	public void setCdBairro(String cdBairro) {
		this.cdBairro = cdBairro;
	}

	public String getNmBairro() {
		return nmBairro;
	}

	public void setNmBairro(String nmBairro) {
		this.nmBairro = nmBairro;
	}


	
	
}
