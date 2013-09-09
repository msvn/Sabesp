package com.prime.app.agvirtual.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.Conta;
import com.prime.app.agvirtual.entity.Endereco;
import com.prime.app.agvirtual.entity.Imovel;

/**
 * 
 * @author gustavons
 *
 */
public class RgiHistoricoContas implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2912993758058710516L;

	public RgiHistoricoContas(){
		
	}
	
	private List<Conta> listaContas;
	
	private Imovel imovel = new Imovel();
	
	private Cliente cliente = new Cliente();
	
	private Endereco endereco = new Endereco();
	
	private String nomeEndereco;
	

	public void setListaContas(List<Conta> listaContas) {
		this.listaContas = listaContas;
	}

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(final Imovel imovel) {
		this.imovel = imovel;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(final Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(final Endereco endereco) {
		this.endereco = endereco;
	}

	public String getNomeEndereco(){

		final StringBuilder sb = new StringBuilder();
		sb.append(this.endereco.getDsEndereco());
		sb.append(", ");
		sb.append(this.endereco.getNrEndereco());
		sb.append(" - ");
		sb.append(this.endereco.getNmBairro());
		
		this.nomeEndereco = sb.toString();
			
		
		return nomeEndereco;
		
	}

	public List<Conta> getListaContas() {
		return listaContas;
	}

	public void setNomeEndereco(String nomeEndereco) {
		this.nomeEndereco = nomeEndereco;
	}

}
