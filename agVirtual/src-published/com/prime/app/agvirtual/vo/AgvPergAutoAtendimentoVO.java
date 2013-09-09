package com.prime.app.agvirtual.vo;

import java.util.ArrayList;
import java.util.List;

public class AgvPergAutoAtendimentoVO extends BasicVO {
	private int codPergunta;
	private String dsPergunta;
	private int nrOrdemPergunta;
	private int codAutoAtendimento;

	private List<AgvRespAutoAtendimentoVO> listaRespostas = new ArrayList<AgvRespAutoAtendimentoVO>();

	public AgvPergAutoAtendimentoVO(int codAutoAtendimento, int codPergunta,
			String dsPergunta, int nrOrdemPergunta) {
		this.codAutoAtendimento = codAutoAtendimento;
		this.codPergunta = codPergunta;
		this.dsPergunta = dsPergunta;
		this.nrOrdemPergunta = nrOrdemPergunta;
	}

	public int getCodPergunta() {
		return codPergunta;
	}

	public void setCodPergunta(int codPergunta) {
		this.codPergunta = codPergunta;
	}

	public String getDsPergunta() {
		return dsPergunta;
	}

	public void setDsPergunta(String dsPergunta) {
		this.dsPergunta = dsPergunta;
	}

	public int getNrOrdemPergunta() {
		return nrOrdemPergunta;
	}

	public void setNrOrdemPergunta(int nrOrdemPergunta) {
		this.nrOrdemPergunta = nrOrdemPergunta;
	}

	public int getCodAutoAtendimento() {
		return codAutoAtendimento;
	}

	public void setCodAutoAtendimento(int codAutoAtendimento) {
		this.codAutoAtendimento = codAutoAtendimento;
	}

	public List getListaRespostas() {
		return listaRespostas;
	}

	public void setListaRespostas(List listaRespostas) {
		this.listaRespostas = listaRespostas;
	}

}
