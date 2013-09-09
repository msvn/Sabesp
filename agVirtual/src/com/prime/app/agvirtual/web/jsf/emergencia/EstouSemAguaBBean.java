package com.prime.app.agvirtual.web.jsf.emergencia;

import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.icesoft.net.messaging.expression.IsNotNull;
import com.prime.app.agvirtual.entity.AcaoAutoAtendimento;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.enums.AutoAtendimentoEnum;
import com.prime.app.agvirtual.enums.SituacaoAtendimentoEnum;
import com.prime.app.agvirtual.to.AcatamentoServicoTO;
import com.prime.app.agvirtual.web.jsf.AutoAtendimentoBBean;
import com.prime.app.agvirtual.web.jsf.BasicAutoAtendimentoBBean;
import com.prime.infra.util.WrapperUtils;

import static com.prime.infra.util.WrapperUtils.*;

/**
 * Bean para tela de auto atendimento - Estou Sem Agua
 */
@Component
@Scope(value="session")
public class EstouSemAguaBBean extends BasicAutoAtendimentoBBean{
	private static final Logger logger = LoggerFactory.getLogger(EstouSemAguaBBean.class);
	private static final long serialVersionUID = 8176912045210283598L;

	@Autowired private AutoAtendimentoBBean aaBBean;
	
	// flags do caso de uso
	private boolean validacaoRealizada = Boolean.TRUE;
	private boolean exibirLigacaoInativa;

	/**
	 * Registra rgi identificado
	 * 
	 * Chamado ao identificar imovel
	 */
	@Override
	protected void gravarRgiAutoAtendimento(Imovel imovel) {
		if(isNotNull(imovel)) aaBBean.adicionarRgiAutoAtendimento(imovel.getDsRgi()); // grava RGI
	}
	
	@Override
	public void inicializar(ActionEvent e) {
		initBasicAutoAtendimentoBean();
		carregarPerguntasAutoAtendimento(AutoAtendimentoEnum.ESTOU_SEM_AGUA);
		carregaDadosCorrelacao(AutoAtendimentoEnum.ESTOU_SEM_AGUA);
	}

	/**
	 * Metodo chamado ao identificar Cliente.
	 * 
	 * Neste caso de uso, o ACATAMENTO e funcoes de AUTO-ATENDIMENTO sao chamadas
	 */
	@Override
	public String prosseguirCliente() {
		// valida campos cliente
		if(!validaCamposCliente(getCliente())){
			return forwardOutcome(OUTCOME_ERROR);	
		}
		
		aaBBean.adicionarDadosSolicitanteAutoAtendimento(getDadosImoveisTO());
		
		// realiza acatamento
		if(!realizarAcatamentoServico()){
			return forwardOutcome(OUTCOME_ERROR);
		}
		
		aaBBean.fecharAutoAtendimentoPergunta(12L, SituacaoAtendimentoEnum.CONCLUIDO, 3L);

		return forwardOutcome(OUTCOME_PROSSEGUIR);		
	}
	
	@Override
	protected AcatamentoServicoTO criaAcatamentoTO() {
		AcatamentoServicoTO acatamentoServicoTO = new AcatamentoServicoTO();
		
		acatamentoServicoTO.setRgi(WrapperUtils.toLong(getDadosImoveisTO().getImovel().getDsRgi()));
		acatamentoServicoTO.setSolicitante(parseSolicitanteTO(getDadosImoveisTO().getSolicitante()));				
//		acatamentoServicoTO.setObs("");
//		acatamentoServicoTO.setPrioridade(1);
//		getDadosImoveisTO().getImovel().setDsDiretoria("M");
		
		return acatamentoServicoTO;
	}	
	
	@Override
	protected void realizaValidacaoEspecifica() {
		acao = buscaAcao(getListaPerguntasRespondidas());
		carregaOrcamentoServExe(); // carrega o orçamento
	}
	
	
	// sem uso
	@Override
	protected void realizarAcao() {}	
	
	
	/**
	 * Verifica se a ligacao esta ativa
	 * 
	 * NAO IMPLEMENTADO POIS NAO HA TELA NO CASO DE USO
	 */
	private boolean verificarSeLigacaoEstaInvativa(){
		exibirLigacaoInativa = Boolean.TRUE;  		// TODO - chamada ao CSI
		if(exibirLigacaoInativa){
			// TODO - finalizar auto atendimento
		}
		
		return exibirLigacaoInativa;
	}

	// get / set
	public boolean isValidacaoRealizada() {
		return validacaoRealizada;
	}

	public void setValidacaoRealizada(boolean validacaoRealizada) {
		this.validacaoRealizada = validacaoRealizada;
	}


	public boolean isExibirLigacaoInativa() {
		return exibirLigacaoInativa;
	}


	public void setExibirLigacaoInativa(boolean exibirLigacaoInativa) {
		this.exibirLigacaoInativa = exibirLigacaoInativa;
	}

	
}
