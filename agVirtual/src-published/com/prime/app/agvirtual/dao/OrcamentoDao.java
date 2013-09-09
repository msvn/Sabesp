package com.prime.app.agvirtual.dao;

import java.math.BigDecimal;
import java.util.List;

import com.prime.app.agvirtual.to.OrcamentoOferecidoTO;

public interface OrcamentoDao {

	OrcamentoOferecidoTO getTipoOrcamento(Long rgi, Integer cdGrupoServico, Integer cdSubServico , String cdMuncipio);

	BigDecimal getValorServico(Long rgi, Integer cdGrupoServico, Integer cdSubServico, Integer cdServicoExecutado , String cdMunicipio);

	List<Integer> getVicePresidenciaUnidadeNegocio(Long dsRgi);
	
	List<Integer> getVicePresidenciaUnidadeNegocio(String cdMunicipio);

}
