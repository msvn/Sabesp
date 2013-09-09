package com.prime.app.agvirtual.webservice;

import com.prime.app.agvirtual.dto.TestFilter;
import com.prime.app.agvirtual.dto.TestResultList;
import com.prime.infra.webservice.Paginator;

public interface Test3WebService {
    TestResultList buscaInfo(SecurityInfoDTO securityInfo, Paginator paginator, TestFilter filter);
}
