package com.prime.app.agvirtual.vo;

public class AgvRespAutoAtendimentoVO extends BasicVO{
	private int codResposta;
	private String dsResposta;
	private int nrOrdemResposta;

	public AgvRespAutoAtendimentoVO(int codResposta, String dsResposta,
			int nrOrdemResposta) {
		this.codResposta = codResposta;
		this.dsResposta = dsResposta;
		this.nrOrdemResposta = nrOrdemResposta;
	}

	public int getCodResposta() {
		return codResposta;
	}

	public void setCodResposta(int codResposta) {
		this.codResposta = codResposta;
	}

	public String getDsResposta() {
		return dsResposta;
	}

	public void setDsResposta(String dsResposta) {
		this.dsResposta = dsResposta;
	}

	public int getNrOrdemResposta() {
		return nrOrdemResposta;
	}

	public void setNrOrdemResposta(int nrOrdemResposta) {
		this.nrOrdemResposta = nrOrdemResposta;
	}

}
