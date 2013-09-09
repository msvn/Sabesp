package com.prime.app.agvirtual.to;

import java.io.Serializable;

/**
 * Classe TO referente a Orientações para Débito Automático.
 * @author mauriciopf
 */
public class DebitoAutomaticoTO  implements Serializable {
	
	private String nome;
	
	private String rg;
	
	private String cpfCnpj;
	
	private String rgi;
	
	private String endereco;
	
	private String bairro;
	
	private String cidade;
	
	private String ddd;
	
	private String telefone;
	
	private String nomeBanco;
	
	private String bairroBanco;
	
	private String cdBanco;
	
	private String contaCorrente;
	
	private String agencia;

	/**
	 * Método Construtor.
	 */
	public DebitoAutomaticoTO() {
		
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the rg
	 */
	public String getRg() {
		return rg;
	}

	/**
	 * @param rg the rg to set
	 */
	public void setRg(String rg) {
		this.rg = rg;
	}

	/**
	 * @return the cpfCnpj
	 */
	public String getCpfCnpj() {
		return cpfCnpj;
	}

	/**
	 * @param cpfCnpj the cpfCnpj to set
	 */
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	/**
	 * @return the rgi
	 */
	public String getRgi() {
		return rgi;
	}

	/**
	 * @param rgi the rgi to set
	 */
	public void setRgi(String rgi) {
		this.rgi = rgi;
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * @param bairro the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the ddd
	 */
	public String getDdd() {
		return ddd;
	}

	/**
	 * @param ddd the ddd to set
	 */
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the nomeBanco
	 */
	public String getNomeBanco() {
		return nomeBanco;
	}

	/**
	 * @param nomeBanco the nomeBanco to set
	 */
	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	/**
	 * @return the bairroBanco
	 */
	public String getBairroBanco() {
		return bairroBanco;
	}

	/**
	 * @param bairroBanco the bairroBanco to set
	 */
	public void setBairroBanco(String bairroBanco) {
		this.bairroBanco = bairroBanco;
	}

	/**
	 * @return the cdBanco
	 */
	public String getCdBanco() {
		return cdBanco;
	}

	/**
	 * @param cdBanco the cdBanco to set
	 */
	public void setCdBanco(String cdBanco) {
		this.cdBanco = cdBanco;
	}

	/**
	 * @return the contaCorrente
	 */
	public String getContaCorrente() {
		return contaCorrente;
	}

	/**
	 * @param contaCorrente the contaCorrente to set
	 */
	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	/**
	 * @return the agencia
	 */
	public String getAgencia() {
		return agencia;
	}

	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

}
