package com.prime.app.agvirtual.service;

import java.util.List;

import com.prime.app.agvirtual.to.CronogramaLeituraTO;


public interface CronogramaLeituraService {
	
	List<CronogramaLeituraTO> findByRgi(String cdRgi) throws Exception;
}
