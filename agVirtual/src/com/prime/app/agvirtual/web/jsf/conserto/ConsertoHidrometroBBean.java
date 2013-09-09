package com.prime.app.agvirtual.web.jsf.conserto;

import static com.prime.infra.util.WrapperUtils.isNotNull;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.entity.PerguntaAutoAtendimento;
import com.prime.app.agvirtual.enums.AutoAtendimentoEnum;
import com.prime.app.agvirtual.enums.SIMNAOEnum;
import com.prime.app.agvirtual.enums.SituacaoAtendimentoEnum;
import com.prime.app.agvirtual.to.AcatamentoServicoTO;
import com.prime.app.agvirtual.web.jsf.AutoAtendimentoBBean;
import com.prime.app.agvirtual.web.jsf.BasicAutoAtendimentoBBean;
import com.prime.app.agvirtual.web.jsf.BasicNavegacaoBBean;
import com.prime.app.agvirtual.web.jsf.ErasingBean;
import com.prime.app.agvirtual.web.jsf.util.SessionUtil;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * BackBean repsonsavel pelo controle da funcionalidade da Conserto de Hidrômetro
 * @author gustavons
 */
@Component
@Scope(value="session")
public class ConsertoHidrometroBBean extends BasicAutoAtendimentoBBean implements InitializingBean, ErasingBean {
	private static final long serialVersionUID = -8460663760052605214L;
	private static final Logger agvlogger = LoggerFactory.getLogger(ConsertoHidrometroBBean.class);

	private static final String OUTCOME_IMOVEL_IDENTIFICADO = "CONSERTO_HIDROMETRO_IMOVEL_IDENTIFICADO";
	
	@Autowired private AutoAtendimentoBBean aaBBean;
	
	public void inicializarReflection(){		
		initialize();
	}	
	
	/**
	 * Inicializa Bean
	 */
	@Override
	public void initialize() {
		inicializarConserto(null);
		limparListaPerguntasRespondidas();
	}
	
	/**
	 * Registra rgi identificado
	 * 
	 * Chamado ao identificar imovel
	 */
	@Override
	protected void gravarRgiAutoAtendimento(Imovel imovel) {
		if(isNotNull(imovel)) aaBBean.adicionarRgiAutoAtendimento(imovel.getDsRgi()); // grava RGI
	}	
	
	public void inicializarConserto(ActionEvent e) {
		
		//Altera o 2º nível da migalha de pão (Tratamento Diferenciado pois o tela não trata a mudança de local)
		String cdSubMenu = getRequestParameterMap(PARAMETRO_CODIGO_SUB_MENU);
		String dsSubMenu = getRequestParameterMap(PARAMETRO_DESCRICAO_SUB_MENU);
		String outcomeSubMenu = getRequestParameterMap(PARAMETRO_OUTCOME_SUB_MENU);

		SessionUtil.adicionarAtributo(PARAMETRO_CODIGO_SUB_MENU, cdSubMenu, getHttpSession(Boolean.FALSE));
		SessionUtil.adicionarAtributo(PARAMETRO_OUTCOME_SUB_MENU, outcomeSubMenu, getHttpSession(Boolean.FALSE));
		SessionUtil.adicionarAtributo(PARAMETRO_DESCRICAO_SUB_MENU, dsSubMenu, getHttpSession(Boolean.FALSE));
		
		try{
			carregarPerguntasAutoAtendimento(AutoAtendimentoEnum.CONSERTO_HIDROMETRO);
			perguntaAtual = (PerguntaAutoAtendimento) getListaPerguntas().get(0); //Seta a primeira pergunta
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, null); //reseta o outcome param
		}catch (Exception ex) {
			ex.printStackTrace();
			agvlogger.info("Erro busca das perguntas"+ex.getMessage());
		}
		carregaDadosCorrelacao(AutoAtendimentoEnum.CONSERTO_HIDROMETRO);
	}
	
	@Override
	public void inicializar(ActionEvent e) {
		try{
			carregarPerguntasAutoAtendimento(AutoAtendimentoEnum.CONSERTO_HIDROMETRO);
			perguntaAtual = (PerguntaAutoAtendimento) getListaPerguntas().get(0); //Seta a primeira pergunta
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, null); //reseta o outcome param
			
			//Carrega dados do orcamento
			carregaOrcamentoServExe();
			
			// carrega a mensagem de informação para o usuário
			if(orcamento.getPago()){
				orcamento.setMsgCobranca(FacesBundleUtil.getInstance().getString("autoatendimento.orcamento.pago"));	
			}else{
				orcamento.setMsgCobranca(FacesBundleUtil.getInstance().getString("autoatendimento.orcamento.nao.pago"));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			agvlogger.info("Erro busca das perguntas"+ex.getMessage());
		}
	}
	
	@Override
	public void processaAcatamento(ActionEvent e) {
		
		AcatamentoServicoTO acatamentoServicoTO = criaAcatamentoTO();
		
		getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName,null);
		
		listaCorrelacao = correlacaoService.findByAtendimento(AutoAtendimentoEnum.CONSERTO_HIDROMETRO.getCodigo(), false);
		
		try {
			acatamentoServico.acataServico(acatamentoServicoTO);
			numeroSolicitacao = acatamentoServicoTO.getProtocolo();
			msgErroAcatamento = acatamentoServicoTO.getMensagemCSI();
		} catch (Exception e1) {
			e1.printStackTrace();
			agvlogger.info("ERRO ACATAMENTO DE SERVIÇO" + AutoAtendimentoEnum.CONSERTO_HIDROMETRO.getRealName() + e1.getMessage());
		}
		
		exibirAcatamento = false; //flag do painel de acatamento false
		orcamento = null;
	}
	
	@Override
	public void confirmaAcatamento(ActionEvent e) {
		if(WrapperUtils.isNull(radioConfirmacao) || radioConfirmacao.equals(String.valueOf(SIMNAOEnum.NAO.getCodigo()))){ //Se nao estiver selecionado ou se for Não = 2
			addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("autoatendimento.erro.confirmar"), null);
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "ERRO");
		}else{
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, null);
		}
	}

	@Override
	protected void realizarAcao() {
		//Se nao for Rol especial acata
		if(!getDadosImoveisTO().getImovel().isRolEspecial()){
			if(acao.getTpAcao().equals("A")){ //A=acatamento E=emcaminhamento R=Retomada
				exibirAcatamento = true;
			}
		//Se for rol especial não acata
		}else{
			/**
			 * 1.O sistema exibe mensagem informativa sobre indisponibilidade do serviço [M2], 
			 * registra o motivo de insucesso para o relatório de atendimento e encaminha o cliente para atendimento presencial
			 */
			//FIXME ATENDIMENTO REGISTRAR
			addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("atendimento.servico.indisponivel"), null);
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "ERRO");
		}
		
		inicializar(null); // carrega dados do orcamento
	}
	
	@Override
	public String prosseguirCliente() {
		if(!validaCamposCliente(getCliente())){
			return forwardOutcome(OUTCOME_ERROR);
		}
		
		aaBBean.adicionarDadosSolicitanteAutoAtendimento(getDadosImoveisTO());
		
		// realiza acatamento
		if(!acatarServico(acao)){ 				// acao ja carregada
			return forwardOutcome(OUTCOME_ERROR);
		}
		
		exibirAcatamento = false; //flag do painel de acatamento false
		orcamento = null;
		
		aaBBean.fecharAutoAtendimentoPergunta(12L, SituacaoAtendimentoEnum.CONCLUIDO, 3L);

		return forwardOutcome(OUTCOME_PROSSEGUIR);
	}

	@Override
	protected AcatamentoServicoTO criaAcatamentoTO() {
		AcatamentoServicoTO acatamentoServicoTO = new AcatamentoServicoTO();
		
		orcamento.setIdAutoAtendimentoAcessado(AutoAtendimentoEnum.CONSERTO_HIDROMETRO.getCodigo());
		acatamentoServicoTO.setOrcamentoOferecido(orcamento);
		acatamentoServicoTO.setRgi(WrapperUtils.toLong(getDadosImoveisTO().getImovel().getDsRgi()));

		acatamentoServicoTO.setCdGrupoServico(acao.getCdServicoCsi());
		acatamentoServicoTO.setCdSubServico((acao.getCdGrupoServiceCsi()));
		
		//Seta solicitante do acatamento
		acatamentoServicoTO.setSolicitante(parseSolicitanteTO(getDadosImoveisTO().getSolicitante()));
		return acatamentoServicoTO;
	}

	public void afterPropertiesSet() throws Exception {
		setListaPerguntasRespondidas(new ArrayList<PerguntaAutoAtendimento>());
	}
	
	@SuppressWarnings("unchecked")
	public void eraseBeanAtributes() {
		setListaPerguntasRespondidas(new ArrayList());//reseta a lista de respostas	
	}	
	
	/**
	 * Nao identifica imovel caso o mesmo ja teha sido identificado.
	 */	
	@Override
	protected String obterOutcomeImovelIdentificado() {
		return OUTCOME_IMOVEL_IDENTIFICADO;
	}
}
