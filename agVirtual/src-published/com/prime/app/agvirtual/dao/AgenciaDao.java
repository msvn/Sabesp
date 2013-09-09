package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.to.AgenciaTO;
import com.prime.app.agvirtual.to.DadosImoveisTO;

public interface AgenciaDao {

	List<AgenciaTO> findByMunicipio(String cdMunicipio);

	AgenciaTO findAgenciaMaisProximaByRgiRolEspecial(DadosImoveisTO dadosImoveisTO);
	
	AgenciaTO findAgenciaMaisProximaByRgiRolComum(DadosImoveisTO dadosImoveisTO);

}
