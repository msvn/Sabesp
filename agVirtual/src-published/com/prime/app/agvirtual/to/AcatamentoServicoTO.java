package com.prime.app.agvirtual.to;

import java.io.Serializable;

public class AcatamentoServicoTO implements Serializable {
	
	private static final long serialVersionUID = -8880620885139153110L;

	private Long rgi;
	
	private SolicitanteTO solicitante;
	
	private Integer cdGrupoServico;

	private Integer cdSubServico;
	
	private Integer prioridade;
	
	private OrcamentoOferecidoTO orcamentoOferecido;

	private String obs;
	
	/**
	 * Mensagem retornada pelo CSI
	 */
	private String mensagemServicoAcatadoCSI;
	
	/**
	 * Mensagem retornada pelo CSI
	 */
	private String mensagemErroCSI;
	
	/**
	 * Mensagem retornada pelo CSI
	 */
	private String mensagemCSI;
	
	
	/**
	 * Atributo com a mensagem para o usuário
	 */
	private String mensagemErroCsiProperties;

	public String getMensagemCSI() {
		return mensagemCSI;
	}

	public void setMensagemCSI(String mensagemCSI) {
		this.mensagemCSI = mensagemCSI;
	}

	/**
	 * Protocolo retornado pelo CSI
	 */
	private String protocolo;

	public String getMensagemServicoAcatadoCSI() {
		return mensagemServicoAcatadoCSI;
	}

	public void setMensagemServicoAcatadoCSI(String mensagemServicoAcatadoCSI) {
		this.mensagemServicoAcatadoCSI = mensagemServicoAcatadoCSI;
	}

	public String getMensagemErroCSI() {
		return mensagemErroCSI;
	}

	public void setMensagemErroCSI(String mensagemErroCSI) {
		this.mensagemErroCSI = mensagemErroCSI;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public Long getRgi() {
		return rgi;
	}

	public void setRgi(Long rgi) {
		this.rgi = rgi;
	}

	public SolicitanteTO getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(SolicitanteTO solicitante) {
		this.solicitante = solicitante;
	}

	public Integer getCdGrupoServico() {
		return cdGrupoServico;
	}

	public void setCdGrupoServico(Integer cdGrupoServico) {
		this.cdGrupoServico = cdGrupoServico;
	}

	public Integer getCdSubServico() {
		return cdSubServico;
	}

	public void setCdSubServico(Integer cdSubServico) {
		this.cdSubServico = cdSubServico;
	}

	public Integer getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}
	
	public OrcamentoOferecidoTO getOrcamentoOferecido() {
		return orcamentoOferecido;
	}

	public void setOrcamentoOferecido(OrcamentoOferecidoTO orcamentoOferecido) {
		this.orcamentoOferecido = orcamentoOferecido;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

}
