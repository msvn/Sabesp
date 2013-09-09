package com.prime.app.agvirtual.entity;

import java.io.Serializable;

public class Endereco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7005148787801790567L;
	
	public Endereco(){}
	
/*	"CDLOGRADR, " +
	"NRENDEREC, " +
	"DSCOMPLEM, " +
	"CDCEP, " +
	"CDBAIRRO " +	*/
	
	private Imovel imovel;	
	
	private String dsTipoLogradouro;
	
	private String dsHonorifico;
	
	private String dsPreposicao;
	
	private String dsEndereco;
	
	private String nrEndereco;
	
	private String nmBairro;
	
	private String nmMunicipio;
	
	private String cdBairro;
	
	private String cdLogradr;
	
	private String cdMunicipio;
	
	private String dsComplemento;

	private String cdCep;
	
	private String dsUF;
	
	private String enderecoCompleto;
	
	private String enderecoCompletoAtendimento;
	
	private String nrTelefone;

	public String getNrTelefone() {
		return nrTelefone;
	}

	public void setNrTelefone(String nrTelefone) {
		this.nrTelefone = nrTelefone;
	}

	public String getDsTipoLogradouro() {
		return dsTipoLogradouro;
	}

	public void setDsTipoLogradouro(String dsTipoLogradouro) {
		this.dsTipoLogradouro = dsTipoLogradouro;
	}

	public String getDsHonorifico() {
		return dsHonorifico;
	}

	public void setDsHonorifico(String dsHonorifico) {
		this.dsHonorifico = dsHonorifico;
	}

	public String getDsPreposicao() {
		return dsPreposicao;
	}

	public void setDsPreposicao(String dsPreposicao) {
		this.dsPreposicao = dsPreposicao;
	}

	public String getDsEndereco() {
		return dsEndereco;
	}

	public void setDsEndereco(String dsEndereco) {
		this.dsEndereco = dsEndereco;
	}

	public String getNrEndereco() {
		return nrEndereco;
	}

	public void setNrEndereco(String nrEndereco) {
		this.nrEndereco = nrEndereco;
	}

	public String getNmBairro() {
		return nmBairro;
	}

	public void setNmBairro(String nmBairro) {
		this.nmBairro = nmBairro;
	}

	public String getNmMunicipio() {
		return nmMunicipio;
	}

	public void setNmMunicipio(String nmMunicipio) {
		this.nmMunicipio = nmMunicipio;
	}

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public String getCdBairro() {
		return cdBairro;
	}

	public void setCdBairro(String cdBairro) {
		this.cdBairro = cdBairro;
	}

	public String getCdLogradr() {
		return cdLogradr;
	}

	public void setCdLogradr(String cdLogradr) {
		this.cdLogradr = cdLogradr;
	}

	public String getCdMunicipio() {
		return cdMunicipio;
	}

	public void setCdMunicipio(String cdMunicipio) {
		this.cdMunicipio = cdMunicipio;
	}

	public String getDsComplemento() {
		return dsComplemento;
	}

	public void setDsComplemento(String dsComplemento) {
		this.dsComplemento = dsComplemento;
	}

	public String getCdCep() {
		return cdCep;
	}

	public void setCdCep(String cdCep) {
		this.cdCep = cdCep;
	}

	public String getDsUF() {
		return dsUF;
	}

	public void setDsUF(String dsUF) {
		this.dsUF = dsUF;
	}

	public String getEnderecoCompleto() {
		return enderecoCompleto;
	}

	public void setEnderecoCompleto(String enderecoCompleto) {
		this.enderecoCompleto = enderecoCompleto;
	}

	public String getEnderecoCompletoAtendimento() {
		return enderecoCompletoAtendimento;
	}

	public void setEnderecoCompletoAtendimento(String enderecoCompletoAtendimento) {
		this.enderecoCompletoAtendimento = enderecoCompletoAtendimento;
	}
	
	

}
