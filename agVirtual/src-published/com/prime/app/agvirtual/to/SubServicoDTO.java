package com.prime.app.agvirtual.to;

import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabServico;

public class SubServicoDTO extends SubServicoTO {

	private static final long serialVersionUID = -9018307405509762124L;
	private List<SubServicoTO> listaSubServico;

	public List<SubServicoTO> getListaSubServico() {
		return listaSubServico;
	}

	public void setListaSubServico(List<SubServicoTO> listaSubServico) {
		this.listaSubServico = listaSubServico;
	}

	public AgvTabServico getServicoEntity() {
		return servicoEntity;
	}

	public void setServicoEntity(AgvTabServico servicoEntity) {
		this.servicoEntity = servicoEntity;
	}

	private AgvTabServico servicoEntity;

}
