package com.prime.app.agvirtual.to;

import java.io.Serializable;

import com.prime.app.agvirtual.enums.TipoRelacaoClienteImovel;

public class ClienteTO implements Serializable {
	
	private int ddd1;
	private int ddd2;
	private long telefone1;
	private long telefone2;
	private String codCategoria;
	private String nome;
	private String cdRelacaoImovel;
	private String email;
	private String cpf;
	private String cnpj;
	private TipoRelacaoClienteImovel tipoRelacionamento;
	
	public int getDdd1() {
		return ddd1;
	}
	public void setDdd1(int ddd1) {
		this.ddd1 = ddd1;
	}
	public int getDdd2() {
		return ddd2;
	}
	public void setDdd2(int ddd2) {
		this.ddd2 = ddd2;
	}
	public long getTelefone1() {
		return telefone1;
	}
	public void setTelefone1(long telefone1) {
		this.telefone1 = telefone1;
	}
	public long getTelefone2() {
		return telefone2;
	}
	public void setTelefone2(long telefone12) {
		this.telefone2 = telefone12;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCdRelacaoImovel() {
		return cdRelacaoImovel;
	}
	public void setCdRelacaoImovel(String cdRelacaoImovel) {
		this.cdRelacaoImovel = cdRelacaoImovel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getCodCategoria() {
		return codCategoria;
	}
	public void setCodCategoria(String codCategoria) {
		this.codCategoria = codCategoria;
	}
	public TipoRelacaoClienteImovel getTipoRelacionamento() {
		return tipoRelacionamento;
	}
	public void setTipoRelacionamento(TipoRelacaoClienteImovel tipoRelacionamento) {
		this.tipoRelacionamento = tipoRelacionamento;
	}
	
}
