package com.prime.app.agvirtual.to;

import java.io.Serializable;

import com.prime.app.agvirtual.entity.Endereco;

public class SolicitanteTO implements Serializable {

	private static final long serialVersionUID = 31117878650295961L;
	
	private Integer idSolicitante;
	
	private String dsNome;
	
	private String dsRelacao;
	
	private String dsTelefone;
	
	private String dsTelefoneOpcional;
	
	private String dsEmail;
	
	private String docSolicitante;
	
	private Endereco endereco;

	public Integer getIdSolicitante() {
		return idSolicitante;
	}

	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}

	public String getDsNome() {
		return dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsRelacao() {
		return dsRelacao;
	}

	public void setDsRelacao(String dsRelacao) {
		this.dsRelacao = dsRelacao;
	}

	public String getDsTelefone() {
		return dsTelefone;
	}

	public void setDsTelefone(String dsTelefone) {
		this.dsTelefone = dsTelefone;
	}

	public String getDsTelefoneOpcional() {
		return dsTelefoneOpcional;
	}

	public void setDsTelefoneOpcional(String dsTelefoneOpcional) {
		this.dsTelefoneOpcional = dsTelefoneOpcional;
	}

	public String getDsEmail() {
		return dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	public String getDocSolicitante() {
		return docSolicitante;
	}

	public void setDocSolicitante(String docSolicitante) {
		this.docSolicitante = docSolicitante;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
}
