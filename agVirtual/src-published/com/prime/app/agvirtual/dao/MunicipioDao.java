package com.prime.app.agvirtual.dao;

import java.util.ArrayList;
import java.util.List;

import com.prime.app.agvirtual.to.MunicipioTO;

public interface MunicipioDao {

	List<MunicipioTO> findAll();

	int pesquisaCodMunicipio(String cdMunicipio);
	
	double pesquisaTarifaSegundaViaConta(String nrVP, String un);

	ArrayList<String> pesquisaNrVpUn(String cdAtendimento, String nrVP, String un);

}
