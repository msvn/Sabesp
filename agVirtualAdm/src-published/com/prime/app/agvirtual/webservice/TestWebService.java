package com.prime.app.agvirtual.webservice;

import com.prime.infra.audit.AuditInfo;
import com.prime.infra.webservice.WebServiceException;

public interface TestWebService {
    
    String test(SecurityInfoDTO si, AuditInfo ai, String name);

    Cd getCd(Long codigoProdutoInteger, Integer index, Integer maxResults) throws WebServiceException;

    Info test2(Info reqInfo);
    
    String helloWorld(String text);
    
}
