package com.prime.app.agvirtual.to;

import java.io.Serializable;
import java.util.List;

import com.prime.app.agvirtual.entity.AutoAtendimentoAcessado;

public class RelatorioAutoAtendimentoAcessadoTO  implements Serializable {
	private Long cdRgi;
	private String socilicitante;
	private String dsEndereco;
	private List<AutoAtendimentoAcessado> listaAAAcessado;

	public Long getCdRgi() {
		return cdRgi;
	}

	public void setCdRgi(Long cdRgi) {
		this.cdRgi = cdRgi;
	}

	public String getSocilicitante() {
		return socilicitante;
	}

	public void setSocilicitante(String socilicitante) {
		this.socilicitante = socilicitante;
	}

	public String getDsEndereco() {
		return dsEndereco;
	}

	public void setDsEndereco(String dsEndereco) {
		this.dsEndereco = dsEndereco;
	}

	public List<AutoAtendimentoAcessado> getListaAAAcessado() {
		return listaAAAcessado;
	}

	public void setListaAAAcessado(
			List<AutoAtendimentoAcessado> listaAAAcessado) {
		this.listaAAAcessado = listaAAAcessado;
	}

}
