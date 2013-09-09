package com.prime.app.agvirtual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.TarifaDao;
import com.prime.app.agvirtual.service.TarifaService;
import com.prime.app.agvirtual.to.CategoriaTO;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.to.TarifaTO;
import com.prime.infra.BusinessException;

@Service
public class TarifaServiceImpl implements TarifaService {

	@Autowired
    private TarifaDao tarifaDao;

	@Transactional(readOnly = true)
	public List<CategoriaTO> findCategoria() throws Exception {
		return tarifaDao.findCategoria();
	}

	@Transactional(readOnly = true)
	public List<TarifaTO> findTipoTarifaByMunicipio(MunicipioTO municipio) throws Exception{
		return tarifaDao.findTipoTarifaByMunicipio(municipio);
	}

	@Transactional(readOnly = true)
	public List<List<TarifaTO>> findTarifaByParams(String cdMunicipio,
			String cdCateroria, String cdTipoTarifa) throws Exception {
		return tarifaDao.findTarifaByParams(cdMunicipio, cdCateroria, cdTipoTarifa);
	}

	public List<TarifaTO> findTabelaPrecosByMunicipio(MunicipioTO municipio) throws BusinessException {

		return null;
	}

}