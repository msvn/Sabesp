package com.prime.app.agvirtual.web.jsf;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.Conta;
import com.prime.app.agvirtual.entity.Imovel;

@Component
@Scope(value="session")
public class HistoricoContasBBean extends BasicPesquisaBBean {
	private static final Logger logger = LoggerFactory.getLogger(HistoricoContasBBean.class);
	private static final long serialVersionUID = 1L;
	
	private boolean modalRendered = false;  	// * Atributo de controle do modal.

	private List<Imovel> listaImoveis;
	private List<Conta> listaContas;
	private boolean clienteRolEspecial;

	public HistoricoContasBBean() {}

	@Override
	public void initialize() {
		Cliente cliente = null;
		
		try {
			cliente = contaService.pesquisaHistoricoContas(getDadosImoveisTO());			
		} catch (Exception ex) {
			ex.printStackTrace();
			addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao obter historico de contas", ex.getMessage());
		}

		if (cliente.isRolEspecial()) {
			logger.info("Carregando lista de contas para rol especial");
			listaContas = cliente.getImovelRgiMaster().getContaList();
		} else {
			listaContas = cliente.getImoveis().get(0).getContaList();
		}

		listaImoveis = cliente.getImoveis();
		clienteRolEspecial = cliente.isRolEspecial();		
	}
	
	/**
	 * Carrega o histórico de contas.
	 * Chamado em TELA.
	 */
	public void inicializar(ActionEvent e) {
		initialize();
	}

	public String carregaHistorioEspecial() {
		return "CONTAESPECIAL";
	}

	public void afterPropertiesSet() throws Exception {

	}

	// get / set
	public boolean isModalRendered() {
		return modalRendered;
	}

	public void setModalRendered(boolean modalRendered) {
		this.modalRendered = modalRendered;
	}

	public void atualizarModal(ActionEvent e) {
		this.modalRendered = !modalRendered;
	}

	public List<Imovel> getListaImoveis() {
		return listaImoveis;
	}

	public void setListaImoveis(List<Imovel> listaImoveis) {
		this.listaImoveis = listaImoveis;
	}

	public List<Conta> getListaContas() {
		return listaContas;
	}

	public void setListaContas(List<Conta> listaContas) {
		this.listaContas = listaContas;
	}

	public boolean isClienteRolEspecial() {
		return clienteRolEspecial;
	}

	public void setClienteRolEspecial(boolean clienteRolEspecial) {
		this.clienteRolEspecial = clienteRolEspecial;
	}

	
}
