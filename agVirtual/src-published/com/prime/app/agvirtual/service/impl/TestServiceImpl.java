package com.prime.app.agvirtual.service.impl;
//package com.prime.app.abc.service.impl;
//
//import java.math.BigDecimal;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.prime.app.abc.entity.Produto;
//import com.prime.app.abc.service.EstoqueService;
//import com.prime.app.abc.service.ProdutoService;
//import com.prime.app.abc.service.TestService;
//import com.prime.infra.ErrorMessage;
//import com.prime.infra.SystemException;
//import com.prime.infra.annotation.Audit;
//import com.prime.infra.annotation.Authorize;
//import com.prime.infra.audit.AuditInfo;
//import com.prime.infra.security.SecurityInfo;
//
//@Service
//public class TestServiceImpl implements TestService {
//
//    @Autowired
//    private ProdutoService produtoService;
//
//    @Autowired
//    private EstoqueService estoqueService;
//
////    @Audit
////    @Authorize
////    public String test(SecurityInfo si, AuditInfo ai, String name) {
////        if ("null".equals(name))
////            throw new SystemException(ErrorMessage.NULL_ARGUMENT);
////
////        return "Bem vindo, " + name + "!";
////    }
//
//    public Produto getCd(Long codigoProduto, Integer index, Integer maxResults) {
//        return produtoService.getProdutoWithPartesProdutoRange(codigoProduto, index, maxResults);
//    }
//
//    public BigDecimal getValorProduto(Long codigoProduto) {
//        return estoqueService.getProdutoValor(codigoProduto);
//    }
//
//	public String test(SecurityInfo si, AuditInfo ai, String name) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
