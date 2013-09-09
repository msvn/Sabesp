package com.prime.app.agvirtual.dao;

import java.util.ArrayList;

import com.prime.app.agvirtual.to.BancoConveniadoTO;
import com.prime.app.agvirtual.to.TarifaTO;

/**
 * 
 * @author gustavons
 * 
 */
public interface CsiDao {

	ArrayList<BancoConveniadoTO> consultarBancoConveniado();

	TarifaTO findTarifaByParams(String... params);

}
