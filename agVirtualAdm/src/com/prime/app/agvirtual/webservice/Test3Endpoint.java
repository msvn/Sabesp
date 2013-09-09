package com.prime.app.agvirtual.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.prime.app.agvirtual.dto.TestFilter;
import com.prime.app.agvirtual.dto.TestResultList;
import com.prime.app.agvirtual.webservice.Ret;
import com.prime.app.agvirtual.webservice.SecurityInfoDTO;
import com.prime.app.agvirtual.webservice.Test3WebService;
import com.prime.infra.webservice.Paginator;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL)
public class Test3Endpoint implements Test3WebService {
    @WebMethod
    @WebResult(name = "resultList")
    public TestResultList buscaInfo(@WebParam(name = "securityInfo") SecurityInfoDTO securityInfo,
        @WebParam(name = "paginator") Paginator paginator, @WebParam(name = "filter") TestFilter filter) {

        System.out.println("\nSecurityInfo: " + securityInfo + ", \n" + "Paginator: " + paginator + ", \n" + "Filter: "
            + filter);

        List<Ret> retornos = new ArrayList<Ret>();

        TestResultList resultList = new TestResultList();

        for (int i = 0; i < 20; i++) {

            Ret ret = new Ret();
            ret.setText(i + securityInfo.getUserName() + " - " + filter.getNameVal());

            retornos.add(ret);

        }

        resultList.setResultados(retornos);

        resultList.setPaginator(paginator);
        // resultList.setValor("");

        System.out.println("ResultList: " + resultList);

        return resultList;

    }
}
