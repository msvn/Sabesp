package com.prime.app.agvirtual.webservice;

import com.prime.infra.webservice.Paginator;

public interface WebConWebService {
    public RetornoDTO test1(SecurityInfoDTO securityInfoDTO, Paginator paginator, ParamDTO param);
}
