package com.prime.app.agvirtual.service;

import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabBloqueio;
import com.prime.app.agvirtual.to.BloqueioTO;

public interface BloqueioService {
	
	AgvTabBloqueio save(BloqueioTO to);

	List findAll();

}
 