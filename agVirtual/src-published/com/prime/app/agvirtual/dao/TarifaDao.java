package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.to.CategoriaTO;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.to.TarifaTO;

public interface TarifaDao {
	public String getTarifaByMunicipio(String cdMunicipio);
	
	public String getCatagoriaByRGI(String rgi);
	
	List<CategoriaTO> findCategoria();
	
	List<TarifaTO> findTipoTarifaByMunicipio(MunicipioTO municipio);

	List<List<TarifaTO>> findTarifaByParams(String cdMunicipio, String cdCateroria, String cdTipoTarifa);
}
