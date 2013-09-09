package com.prime.app.agvirtual.service;

import java.math.BigDecimal;

import com.prime.app.agvirtual.to.OrcamentoOferecidoTO;

public interface OrcamentoService {
	
	OrcamentoOferecidoTO getTipoOrcamento(Long rgi,Integer cdGrupoServico, Integer cdSubServico , String cdMunicipio);

	BigDecimal getValorServico(Long rgi, Integer cdGrupoServico, Integer cdSubServico, Integer cdServicoExecutado , String cdMunicipio);
	
}
