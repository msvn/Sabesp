package com.prime.app.agvirtual.to;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.prime.app.agvirtual.entity.Bairro;
import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.Endereco;
import com.prime.app.agvirtual.entity.Imovel;

public class DadosImoveisTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2912993758058710516L;

	public DadosImoveisTO(){}
	
	private Imovel imovel = new Imovel();
	
	private Cliente cliente = new Cliente();
	
	private Cliente solicitante = new Cliente();
	
	private Endereco endereco = new Endereco();
	
	private Bairro bairro = new Bairro();
	
	private AgenciaTO agenciaTO = null; //Agencia responsavel pelo RGI
	
	private String nomeEndereco;
	
	private List<Imovel> listaImovel;
	
	private List<MunicipioTO> listaMunicipio;
	
	private List<Endereco> listaEndereco;
	
	private List<Endereco> listaNumerosEndereco;
	
	private String jSessionId;
	
	public String getJSessionId(FacesContext facesCtx) {
		if(jSessionId == null){
			HttpSession session = ((HttpSession) facesCtx.getExternalContext().getSession(Boolean.FALSE));
			System.getProperty("jSessionId", session.getId());
			this.setJSessionId(session.getId());
		}
		return jSessionId;
	}

	public void setJSessionId(String sessionId) {
		jSessionId = sessionId;
	}

	public Imovel getImovel() {
		return imovel;
	}

	public Cliente getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Cliente solicitante) {
		this.solicitante = solicitante;
	}

	public void setNomeEndereco(String nomeEndereco) {
		this.nomeEndereco = nomeEndereco;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public List<Imovel> getListaImovel() {
		return listaImovel;
	}

	public void setListaImovel(List<Imovel> listaImovel) {
		this.listaImovel = listaImovel;
	}
	
	
	
	public List<MunicipioTO> getListaMunicipio() {
		return listaMunicipio;
	}

	public void setListaMunicipio(List<MunicipioTO> listaMunicipio) {
		this.listaMunicipio = listaMunicipio;
	}

	public List<Endereco> getListaEndereco() {
		return listaEndereco;
	}

	public void setListaEndereco(List<Endereco> listaEndereco) {
		this.listaEndereco = listaEndereco;
	}

	public String getNomeEndereco(){
		
		if( this.endereco.getDsTipoLogradouro() != null && !this.endereco.getDsTipoLogradouro().equals("") ){

			StringBuilder sb = new StringBuilder();
			sb.append(this.endereco.getDsTipoLogradouro());
			sb.append(" ");
			sb.append(this.endereco.getDsHonorifico());
			sb.append(" ");
			sb.append(this.endereco.getDsPreposicao());
			sb.append(" ");
			sb.append(this.endereco.getDsEndereco());
			sb.append(", ");
			sb.append(this.endereco.getNrEndereco());
			sb.append(" - ");
			sb.append(this.endereco.getNmBairro());
			
			this.nomeEndereco = sb.toString();
			
		}
		
		
		return nomeEndereco;
		
		
	}

	public List<Endereco> getListaNumerosEndereco() {
		return listaNumerosEndereco;
	}

	public void setListaNumerosEndereco(List<Endereco> listaNumerosEndereco) {
		this.listaNumerosEndereco = listaNumerosEndereco;
	}

	public AgenciaTO getAgenciaTO() {
		return agenciaTO;
	}

	public void setAgenciaTO(AgenciaTO agenciaTO) {
		this.agenciaTO = agenciaTO;
	}
	
	

}
