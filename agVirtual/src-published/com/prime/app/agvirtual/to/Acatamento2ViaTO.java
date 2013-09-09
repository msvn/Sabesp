package com.prime.app.agvirtual.to;

import java.io.Serializable;
import java.util.List;

import com.prime.app.agvirtual.entity.Conta;
import com.prime.app.agvirtual.entity.Endereco;


public class Acatamento2ViaTO implements Serializable {

	private static final long serialVersionUID = -6982251995497391792L;

	private Long rgi;
	
	private Endereco endereco;
	
	private List<Conta> contas;

	/**
	 * Mensagem retornada pelo CSI
	 */
	private String mensagemErro2Via;
	
	/**
	 * Mensagem retornada pelo CSI
	 */
	private String mensagemSucesso2Via;

	public Long getRgi() {
		return rgi;
	}

	public void setRgi(Long rgi) {
		this.rgi = rgi;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public String getMensagemErro2Via() {
		return mensagemErro2Via;
	}

	public void setMensagemErro2Via(String mensagemErro2Via) {
		this.mensagemErro2Via = mensagemErro2Via;
	}

	public String getMensagemSucesso2Via() {
		return mensagemSucesso2Via;
	}

	public void setMensagemSucesso2Via(String mensagemSucesso2Via) {
		this.mensagemSucesso2Via = mensagemSucesso2Via;
	}

}
