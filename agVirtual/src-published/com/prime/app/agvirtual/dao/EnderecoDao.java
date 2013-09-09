package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.Endereco;
import com.prime.app.agvirtual.to.AgenciaTO;

public interface EnderecoDao {
	
	public Endereco findByEndereco(Endereco endereco);
	
	public Endereco findDadosBasicosByEndereco(Endereco endereco);
	
	public Endereco findByEnderecoAgenciaAtendimento(Endereco enderecoPesquisa);
	
	public List<Endereco> getEnderecosByNameAndMunicipio(Endereco enderecoPesquisa);
	
	public List<Endereco> getEnderecosByMunicipio(Endereco enderecoPesquisa);
	
	public List<Endereco> carregarNumeroEnderecoRGI(Endereco enderecoPesquisa);
	
	public Endereco findEnderecoAgencia(AgenciaTO agencia, boolean b);
	
	public Endereco getEnderecoByRGI(String dsRGI);
	

}
