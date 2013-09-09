package com.prime.app.agvirtual.to;

import java.io.Serializable;

/**
 * Classe TO referente ao Banco Conveniado CSI.
 * @author mauriciopf
 */
public class BancoConveniadoTO  implements Serializable {

	/**
	 * Código do Banco Conveniado.
	 */
	private Integer cdBancoConveniado;
	
	/**
	 * Nome do Banco Conveniado.
	 */
	private String nmBancoConveniado;
	
	/**
	 * Indicador do Convênio Débito Automático.
	 */
	private String inConvenioDebAutomatico;
	
	/**
	 * URL do site do Banco.
	 */
	private String linkPaginaBanco;
	
	
	/**
	 * Se banco recebe em debitoautomatico
	 */
	private boolean debitoautomatico;
	
	/**
	 * Método Construtor.
	 */
	public BancoConveniadoTO() {
		
	}
	

	public boolean isDebitoautomatico() {
		return debitoautomatico;
	}


	public void setDebitoautomatico(boolean debitoautomatico) {
		this.debitoautomatico = debitoautomatico;
	}



	/**
	 * @return the cdBancoConveniado
	 */
	public Integer getCdBancoConveniado() {
		return cdBancoConveniado;
	}

	/**
	 * @param cdBancoConveniado the cdBancoConveniado to set
	 */
	public void setCdBancoConveniado(Integer cdBancoConveniado) {
		this.cdBancoConveniado = cdBancoConveniado;
	}

	/**
	 * @return the nmBancoConveniado
	 */
	public String getNmBancoConveniado() {
		return nmBancoConveniado;
	}

	/**
	 * @param nmBancoConveniado the nmBancoConveniado to set
	 */
	public void setNmBancoConveniado(String nmBancoConveniado) {
		this.nmBancoConveniado = nmBancoConveniado;
	}

	/**
	 * @return the inConvenioDebAutomatico
	 */
	public String getInConvenioDebAutomatico() {
		return inConvenioDebAutomatico;
	}

	/**
	 * @param inConvenioDebAutomatico the inConvenioDebAutomatico to set
	 */
	public void setInConvenioDebAutomatico(String inConvenioDebAutomatico) {
		this.inConvenioDebAutomatico = inConvenioDebAutomatico;
	}

	/**
	 * @return the linkPaginaBanco
	 */
	public String getLinkPaginaBanco() {
		return linkPaginaBanco;
	}

	/**
	 * @param linkPaginaBanco the linkPaginaBanco to set
	 */
	public void setLinkPaginaBanco(String linkPaginaBanco) {
		this.linkPaginaBanco = linkPaginaBanco;
	}

}
