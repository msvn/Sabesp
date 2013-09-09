package com.prime.app.agvirtual.web.jsf.suaconta;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.service.CronogramaLeituraService;
import com.prime.app.agvirtual.to.CronogramaLeituraTO;
import com.prime.app.agvirtual.web.jsf.BasicPesquisaBBean;

@Component
@Scope(value="session")
public class CronogramaLeituraBBean extends BasicPesquisaBBean {
	
	private static final Logger agvlogger = LoggerFactory.getLogger(CronogramaLeituraBBean.class);
	
	private static final long serialVersionUID = 6897044564463030350L;
	
	@Autowired
	private CronogramaLeituraService cronogramaLeituraService;
	
	private List<CronogramaLeituraTO> listaCronogramaLeitura;

	private String retorno = "";

	private boolean modalRendered = false;   	// Atributo de controle do modal.
	
	private boolean rolEspecial = false;
	
	/**
	 * Inicializa Bean.
	 */
	@Override
	public void initialize() {
		try {
			listaCronogramaLeitura = cronogramaLeituraService.findByRgi(getDadosImoveisTO().getImovel().getDsRgi());
			
			for (CronogramaLeituraTO cronograma : listaCronogramaLeitura) {
				if (cronograma.getDataLeituraIntermediaria() == null) {
					rolEspecial = false;
				} else {
					rolEspecial = true;
				}
			}
		} catch (Exception ex) {
			super.setUserThrowException(true);
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getCause().getMessage(), null));
			
		}		
	}

	/**
	 * Captura evento de TELA
	 */
	public void inicializar(ActionEvent e){
		initialize();
	}
	
	public void afterPropertiesSet() throws Exception {
	}
		
	public CronogramaLeituraBBean() {
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}	
	
	public String paginaInicial(){	
		return AGVINICIAL;
	}
	
	public String concluir(){	
		return RELATORIO_ATENDIMENTO;
	}
	
	public void atualizarModal(ActionEvent e) {
		this.modalRendered = !modalRendered;
	}
	
	public List<CronogramaLeituraTO> getListaCronogramaLeitura() {
		return listaCronogramaLeitura;
	}

	public void setListaCronogramaLeitura(List<CronogramaLeituraTO> listaCronogramaLeitura) {
		this.listaCronogramaLeitura = listaCronogramaLeitura;
	}

	public boolean isModalRendered() {
		return modalRendered;
	}

	public void setModalRendered(boolean modalRendered) {
		this.modalRendered = modalRendered;
	}

	public boolean isRolEspecial() {
		return rolEspecial;
	}

	public void setRolEspecial(boolean rolEspecial) {
		this.rolEspecial = rolEspecial;
	}
	
}
