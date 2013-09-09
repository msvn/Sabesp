package com.prime.app.agvirtual.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.OrcamentoDao;
import com.prime.app.agvirtual.service.OrcamentoService;
import com.prime.app.agvirtual.to.OrcamentoOferecidoTO;

@Service
public class OrcamentoServiceImpl implements OrcamentoService {

	@Autowired
    private OrcamentoDao orcamento;
	
	@Transactional
	public OrcamentoOferecidoTO getTipoOrcamento(Long rgi,Integer cdGrupoServico, Integer cdSubServico , String cdMunicipio) {
		return orcamento.getTipoOrcamento(rgi, cdGrupoServico, cdSubServico , cdMunicipio);
	}

	@Transactional
	public BigDecimal getValorServico(Long rgi, Integer cdGrupoServico, Integer cdSubServico, Integer cdServicoExecutado , String cdMunicipio) {
		return orcamento.getValorServico(rgi, cdGrupoServico, cdSubServico, cdServicoExecutado , cdMunicipio);
	}
}