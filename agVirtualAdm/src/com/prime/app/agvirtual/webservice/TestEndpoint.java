package com.prime.app.agvirtual.webservice;
//package com.prime.app.abc.webservice;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
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
//import com.prime.app.abc.dto.TestFilter;
//import com.prime.app.abc.dto.TestResultList;
//import com.prime.app.abc.entity.ParteProduto;
//import com.prime.app.abc.entity.Produto;
//import com.prime.app.abc.service.TestService;
//import com.prime.infra.audit.AuditInfo;
//import com.prime.infra.security.SecurityInfo;
//import com.prime.infra.validator.NumberRangeValidator;
//import com.prime.infra.validator.StringLengthValidator;
//import com.prime.infra.validator.Validator;
//import com.prime.infra.webservice.Paginator;
//import com.prime.infra.webservice.WebServiceException;
//
//@WebService
//@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL)
//public class TestEndpoint extends SpringBeanAutowiringSupport implements TestWebService {
//
//    @Autowired
//    private TestService service;
//
//    @WebMethod
//    public String test(@WebParam(name = "securityInfo") SecurityInfoDTO si, @WebParam(name = "auditInfo") AuditInfo ai,
//        @WebParam(name = "name") String name) {
//
//        Validator v = new StringLengthValidator(2, 7);
//        v.validate(name);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        SecurityInfo securityInfo = new SecurityInfo();
//        if (si.getLoginTimestamp() != null) {
//            try {
//                securityInfo.setLoginTimestamp(sdf.parse(si.getLoginTimestamp()));
//            }
//            catch (ParseException ex) {
//                ex.printStackTrace();
//            }
//        }
//        securityInfo.setRemoteAddr(si.getRemoteAddr());
//        securityInfo.setSystemId(si.getSystemId());
//        securityInfo.setUserName(si.getUserName());
//
//        return service.test(securityInfo, ai, name);
//    }
//
//    @WebMethod
//    public Cd getCd(@WebParam(name = "codigoProduto") Long codigoProduto, @WebParam(name = "index") Integer index,
//        @WebParam(name = "maxResults") Integer maxResults) throws WebServiceException {
//
//        System.out.println("codigoProduto: " + codigoProduto + ", Index: " + index + ", maxResults: " + maxResults);
//
//        Validator v = new NumberRangeValidator((long) 1, (long) 9999);
//        if (!v.isValid(codigoProduto))
//            throw new WebServiceException("Codigo invalido", 10);
//
//        if (index == null) {
//            index = 0;
//        }
//
//        Produto produto;
//        try {
//            produto = service.getCd(codigoProduto, index, maxResults);
//        }
//        catch (Exception e) {
//            throw new WebServiceException("CD nao encontrado", 11);
//        }
//
//        Cd cd = new Cd(produto.getCodigo(), produto.getNomeArtista(), produto.getNomeTitulo(), produto
//            .getAnoLancamento(), produto.getCategoria().getNome());
//
//        cd.setValor(service.getValorProduto(codigoProduto));
//        cd.setData("2008-12-31");
//        cd.setMaxResults(maxResults);
//
//        List<ParteProduto> partes = produto.getPartesProduto();
//        if (partes != null) {
//            for (ParteProduto pp : partes) {
//                Faixa faixa = new Faixa(pp.getOrdem(), pp.getDescricao());
//                cd.add(faixa);
//            }
//            cd.setNextIndex(index + partes.size());
//        }
//
//        return cd;
//    }
//
//    @WebMethod
//    @WebResult(name = "info")
//    public Info test2(Info reqInfo) {
//
//        System.out.println(reqInfo);
//
//        Info info = new Info();
//
//        info.setCodes(reqInfo.getCodes());
//        info.setDate(reqInfo.getDate());
//        info.setPrice(reqInfo.getPrice());
//
//        System.out.println(info);
//        return info;
//    }
//
//    @WebMethod
//    @WebResult(name = "resultado")
//    public String helloWorld(@WebParam(name = "text") String text) {
//        return "Mensagem recebida com sucesso: " + text;
//    }
//
//}
