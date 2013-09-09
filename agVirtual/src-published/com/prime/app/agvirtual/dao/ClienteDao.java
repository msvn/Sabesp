package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.to.DadosImoveisTO;

public interface ClienteDao {
	
	Cliente findByCliente(Cliente cliente);

	String pesquisaNumeroInscricaoEstadual(DadosImoveisTO dadosImoveisTO);
	
	public String pesquisaRgiMaster(List<String> rgis);

	void pesquisaNumeroCpfCnpjCliente(DadosImoveisTO dadosImoveisTO);
	
}
