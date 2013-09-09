package com.prime.app.agvirtual.service;

import java.util.List;

import com.prime.app.agvirtual.to.AgenciaTO;
import com.prime.app.agvirtual.to.DadosImoveisTO;

public interface AgenciaService {
	
	List<AgenciaTO> findByMunicipio(String cdMunicipio);

	void findEnderecoByAgencia(AgenciaTO agenciaTO);

	void findEnderecoTodasAgencias(List<AgenciaTO> listaAgencia);

	AgenciaTO findAgenciaMaisProximaByRgiRolComum(DadosImoveisTO dadosImoveisTO);
	
	AgenciaTO findAgenciaMaisProximaByRgiRolEspecial(DadosImoveisTO dadosImoveisTO);
	
}
