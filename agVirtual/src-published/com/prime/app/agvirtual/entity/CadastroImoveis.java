package com.prime.app.agvirtual.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "CCG01",  schema = Schema.RDMS_OWNER)
public class CadastroImoveis {

	public CadastroImoveis(){}	
	
	@Column(name="NRENDEREC")
	private String numeroEndereco;
	
	@Column(name="DSCOMPLEM")
	private String complementoString;	
	
	@Column(name="CDCEP")
	private String codigoCep;
	
	@Column(name="CDBAIRRO")
	private String codigoBairro;
	
	@Column(name="CDLOGRADR")
	private String codigoLogrador;

	@Id
	@Column(name="CDIMOVEL")
	private String codigoImovel;

	@Column(name="CDMUNICIP")
	private String codigoMunicipio;
	
	public String getCodigoLogrador() {
		return codigoLogrador;
	}

	public void setCodigoLogrador(String codigoLogrador) {
		this.codigoLogrador = codigoLogrador;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(String numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public String getComplementoString() {
		return complementoString;
	}

	public void setComplementoString(String complementoString) {
		this.complementoString = complementoString;
	}

	public String getCodigoCep() {
		return codigoCep;
	}

	public void setCodigoCep(String codigoCep) {
		this.codigoCep = codigoCep;
	}

	public String getCodigoBairro() {
		return codigoBairro;
	}

	public void setCodigoBairro(String codigoBairro) {
		this.codigoBairro = codigoBairro;
	}

	public String getCodigoImovel() {
		return codigoImovel;
	}

	public void setCodigoImovel(String codigoImovel) {
		this.codigoImovel = codigoImovel;
	}

	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}
	
	
	
}
