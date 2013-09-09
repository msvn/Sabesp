package com.prime.app.agvirtual.service;

import java.util.List;

import com.prime.app.agvirtual.to.CategoriaTO;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.to.TarifaTO;
import com.prime.infra.BusinessException;

public interface TarifaService {
	
	List<CategoriaTO> findCategoria() throws Exception;
	
	List<TarifaTO> findTipoTarifaByMunicipio(MunicipioTO municipio) throws Exception;
	
	List<List<TarifaTO>> findTarifaByParams(String cdMunicipio, String cdCateroria, String cdTipoTarifa) throws Exception;
	
	List<TarifaTO> findTabelaPrecosByMunicipio(MunicipioTO municipio) throws BusinessException;
}
