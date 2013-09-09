package com.prime.app.agvirtual.web.jsf.suaconta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.service.CsiService;
import com.prime.app.agvirtual.to.BancoConveniadoTO;
import com.prime.app.agvirtual.web.jsf.BasicNavegacaoBBean;

@Component
@Scope(value="session")
public class BancoConveniadoBBean extends BasicNavegacaoBBean implements Serializable, InitializingBean {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1067819006718556065L;
	
	/**
	 * Log.
	 */
	private static final Logger agvlogger = LoggerFactory.getLogger(BancoConveniadoBBean.class);
	
	/**
	 * Service CSI.
	 */
	@Autowired
	private CsiService csiService;
	
	private List<BancoConveniadoTO> listaBancoConveniado;
	
	/**
	 * Lista de Bancos Conveniados.
	 */
	private ListDataModel listaBancoConveniadoEsquerda;
	
	/**
	 * Lista de Bancos Conveniados.
	 */
	private ListDataModel listaBancoConveniadoDireita;

	/**
	 * {@inheritDoc}
	 */
	public void afterPropertiesSet() throws Exception {
		
	}
	
	/**
	 * Direciona para a página inicial.
	 * @return String
	 */
	public String paginaInicial() {
		return AGVINICIAL;
	}
	
	/**
	 * Direciona para a página de relatório do atendimento.
	 * @return String
	 */
	public String concluir() {
		return RELATORIO_ATENDIMENTO;
	}

	/**
	 * @return the listaBancoConveniado
	 */
	public List<BancoConveniadoTO> getListaBancoConveniado() {
		if (this.listaBancoConveniado == null) {
			this.listaBancoConveniado = csiService.consultarBancoConveniado();
		}
		return listaBancoConveniado;
	}

	/**
	 * @param listaBancoConveniado the listaBancoConveniado to set
	 */
	public void setListaBancoConveniado(List<BancoConveniadoTO> listaBancoConveniado) {
		this.listaBancoConveniado = listaBancoConveniado;
	}

	/**
	 * @return the listaBancoConveniadoEsquerda
	 */
	public ListDataModel getListaBancoConveniadoEsquerda() {
		if (this.listaBancoConveniadoEsquerda == null) {
			List<BancoConveniadoTO> lista = new ArrayList<BancoConveniadoTO>();
			List<BancoConveniadoTO> listaBancos = getListaBancoConveniado();
			for (int i = 0; i < listaBancos.size(); i++) {
				if (i % 2 == 0) {
					lista.add(this.listaBancoConveniado.get(i));
				}
			}
			this.listaBancoConveniadoEsquerda = new ListDataModel(lista);
		}
		return listaBancoConveniadoEsquerda;
	}

	/**
	 * @param listaBancoConveniadoEsquerda the listaBancoConveniadoEsquerda to set
	 */
	public void setListaBancoConveniadoEsquerda(
			ListDataModel listaBancoConveniadoEsquerda) {
		this.listaBancoConveniadoEsquerda = listaBancoConveniadoEsquerda;
	}

	/**
	 * @return the listaBancoConveniadoDireita
	 */
	public ListDataModel getListaBancoConveniadoDireita() {
		if (this.listaBancoConveniadoDireita == null) {
			List<BancoConveniadoTO> lista = new ArrayList<BancoConveniadoTO>();
			List<BancoConveniadoTO> listaBancos = getListaBancoConveniado();
			for (int i = 0; i < listaBancos.size(); i++) {
				if (i % 2 == 1) {
					lista.add(this.listaBancoConveniado.get(i));
				}
			}
			this.listaBancoConveniadoDireita = new ListDataModel(lista);
		}
		return listaBancoConveniadoDireita;
	}

	/**
	 * @param listaBancoConveniadoDireita the listaBancoConveniadoDireita to set
	 */
	public void setListaBancoConveniadoDireita(
			ListDataModel listaBancoConveniadoDireita) {
		this.listaBancoConveniadoDireita = listaBancoConveniadoDireita;
	}

}
