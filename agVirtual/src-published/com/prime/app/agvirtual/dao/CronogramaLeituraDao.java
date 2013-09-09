package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.to.CronogramaLeituraTO;

public interface CronogramaLeituraDao {

	List<CronogramaLeituraTO> findByRgi(String cdRgi) throws Exception;
}
