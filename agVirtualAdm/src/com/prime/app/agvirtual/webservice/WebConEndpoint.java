package com.prime.app.agvirtual.webservice;
//package com.prime.app.abc.webservice;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.jws.WebMethod;
//import javax.jws.WebParam;
//import javax.jws.WebResult;
//import javax.jws.WebService;
//import javax.jws.soap.SOAPBinding;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.context.support.SpringBeanAutowiringSupport;
//
//import com.prime.app.abc.service.TestService;
//import com.prime.infra.webservice.Paginator;
//
//@WebService
//@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL)
//public class WebConEndpoint extends SpringBeanAutowiringSupport implements WebConWebService {
//    
//    @Autowired
//    private TestService service;
//    
//    @WebMethod
//    @WebResult(name="retorno")
//    public RetornoDTO test1(
//           @WebParam(name="securityInfo")SecurityInfoDTO securityInfoDTO, 
//           @WebParam(name="paginator")Paginator paginator, 
//           @WebParam(name="param")ParamDTO param) {
//        
//        int index;
//        if (paginator.getIndex() != null)
//            index = new Integer(paginator.getIndex());
//        else
//            index = 0;
//
//        int end = index+(paginator.getMaxResults() != null ?
//            new Integer(paginator.getMaxResults()).intValue() : 49);
//
//        List<NameDTO> names = new ArrayList<NameDTO>();
//        for (int i=index; i<end; i++) {
//            NameDTO name = new NameDTO();
//            name.setName(i+" - securityInfoDTO: "+securityInfoDTO+", param: "+param);
//            names.add(name);
//        }
//        
//        paginator.setNextIndex(Integer.toString(index+names.size()));
//        
//        RetornoDTO retorno = new RetornoDTO();
//        retorno.setPaginator(paginator);
//        retorno.setNames(names);
//        return retorno;
//    }
//
//}
