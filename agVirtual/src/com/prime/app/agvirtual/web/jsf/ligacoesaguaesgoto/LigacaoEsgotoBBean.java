package com.prime.app.agvirtual.web.jsf.ligacoesaguaesgoto;

import static com.prime.infra.util.WrapperUtils.isNotNull;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.entity.PerguntaAutoAtendimento;
import com.prime.app.agvirtual.enums.AutoAtendimentoEnum;
import com.prime.app.agvirtual.enums.SituacaoAtendimentoEnum;
import com.prime.app.agvirtual.to.AcatamentoServicoTO;
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.app.agvirtual.web.jsf.AutoAtendimentoBBean;
import com.prime.app.agvirtual.web.jsf.BasicAutoAtendimentoBBean;
import com.prime.app.agvirtual.web.jsf.BasicNavegacaoBBean;
import com.prime.app.agvirtual.web.jsf.util.SessionUtil;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * BackBean repsonsavel pelo controle da funcionalidade da troca de ligação de esgoto.
 * @author gustavons
 */
@Component
@Scope(value="session")
public class LigacaoEsgotoBBean extends BasicAutoAtendimentoBBean {
	private static final Logger agvlogger = LoggerFactory.getLogger(LigacaoEsgotoBBean.class);
	private static final long serialVersionUID = -2156596137507142102L;
	
	@Autowired private AutoAtendimentoBBean aaBBean;
	
	public LigacaoEsgotoBBean(){}

	/**
	 * Inicializa as perguntas e respostas para o AutoAtendimento
	 * Reseta todas as variaveis de controle de fluxo de perguntas
	 */
	@Override
	public void initialize(){
		initBasicAutoAtendimentoBean();
		
		carregarPerguntasAutoAtendimento(AutoAtendimentoEnum.MUDANCA_LIGACAO_ESGOTO);
		perguntaAtual = (PerguntaAutoAtendimento) getListaPerguntas().get(0); //Seta a primeira pergunta
		setListaPerguntasRespondidas(new ArrayList<PerguntaAutoAtendimento>()); //reseta a lista de respostas
		getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, null); //reseta o outcome param
		exibirAcatamento = false; //flag do painel de acatamento false
		exibirBotaoConclusao =  false; //flag do botao de conclusao false
		nrSeqPergunta = 2; //Seta o numero de sequencia das perguntas
		
		carregaDadosCorrelacao(AutoAtendimentoEnum.MUDANCA_LIGACAO_ESGOTO);		
	}
	
	@Override
	protected void realizarAcao() {
		exibirBotaoConclusao = true;
		if(acao.getTpAcao().equals("A")){ //A=acatamento E=emcaminhamento R=Retomada
			exibirAcatamento = true;
			
			carregaOrcamentoServExe();
			
			if(orcamento.getPago()){
				orcamento.setMsgCobranca(FacesBundleUtil.getInstance().getString("autoatendimento.orcamento.pago"));	
			}else{
				orcamento.setMsgCobranca(FacesBundleUtil.getInstance().getString("autoatendimento.orcamento.nao.pago"));
			}
			
		}else if(acao.getTpAcao().equals("E")){ //A=acatamento E=emcaminhamento R=Retomada
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "PROSSEGUIR_ENCAMINHAMENTO");
		}
	}

	public void afterPropertiesSet() throws Exception {}

	@Override
	public String prosseguirCliente() {
		if(!validaCamposCliente(getCliente())){
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, forwardOutcome(OUTCOME_ERROR));
			return forwardOutcome(OUTCOME_ERROR);
		}
		
		aaBBean.adicionarDadosSolicitanteAutoAtendimento(getDadosImoveisTO());
		
		if(!acatarServico(acao)){
			return forwardOutcome(OUTCOME_ERROR);
		}
		
		aaBBean.fecharAutoAtendimentoPergunta(12L, SituacaoAtendimentoEnum.CONCLUIDO, 3L);
		
		return forwardOutcome(OUTCOME_PROSSEGUIR);
	}

	@Override
	protected AcatamentoServicoTO criaAcatamentoTO() {
		AcatamentoServicoTO acatamentoServicoTO = new AcatamentoServicoTO();
		
		orcamento.setQtParcela(WrapperUtils.toInt(qtParcelas)); 
		orcamento.setIdAutoAtendimentoAcessado(AutoAtendimentoEnum.MUDANCA_LIGACAO_ESGOTO.getCodigo());
		
		acatamentoServicoTO.setOrcamentoOferecido(orcamento);
		acatamentoServicoTO.setRgi(WrapperUtils.toLong(getDadosImoveisTO().getImovel().getDsRgi()));
		
		acatamentoServicoTO.setCdGrupoServico(acao.getCdServicoCsi());
		acatamentoServicoTO.setCdSubServico(acao.getCdGrupoServiceCsi());
		//Seta solicitante do acatamento
		return acatamentoServicoTO;
	}
	
	@Override
	public void confirmaAcatamento(ActionEvent e) {
		
		if( !checkAprovarOrcamento ) {
			addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("autoatendimento.aprovarorcamento.maioridade"), null);
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "ERRO");		// evita que navegacao continue	
		}else {
			super.confirmaAcatamento(e);	
		}
		
		
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
}
