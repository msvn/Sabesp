package com.prime.app.agvirtual.web.jsf.emergencia;

import static com.prime.infra.util.WrapperUtils.isNotNull;
import static com.prime.infra.util.WrapperUtils.toLong;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
import com.prime.app.agvirtual.enums.SituacaoAtendimentoEnum;
import com.prime.app.agvirtual.to.AcatamentoServicoTO;
import com.prime.app.agvirtual.web.jsf.AutoAtendimentoBBean;
import com.prime.app.agvirtual.web.jsf.BasicAutoAtendimentoBBean;
import com.prime.app.agvirtual.web.jsf.BasicNavegacaoBBean;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * BackBean repsonsavel pelo controle da funcionalidade da Pouca Pressao
 * @author gustavons
 */
@Component
@Scope(value="session")
public class PoucaPressaoBBean extends BasicAutoAtendimentoBBean implements InitializingBean{
	private static final Logger agvlogger = LoggerFactory.getLogger(PoucaPressaoBBean.class);
	private static final long serialVersionUID = -2156596137507142102L;
	
	private static final String OUTCOME_IMOVEL_IDENTIFICADO = "EMERGENCIA_POUCA_PRESSAO_IDENTIFICADO";
	
	@Autowired private AutoAtendimentoBBean aaBBean;
	
	String variavelOutcome = "PROSSEGUIR_PESQUISA_RGI";
	
	String resposta = "";
	
	private boolean checkConfirmacao;
	
	public PoucaPressaoBBean(){
		resposta = "";
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
	
	@Override
	protected void realizarAcao() {
		exibirBotaoConclusao = true;
		
		if(acao.getTpAcao().equals("A")){ //A=acatamento E=emcaminhamento R=Retomada
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "PROSSEGUIR");
			exibirAcatamento = true;
		}else if(acao.getTpAcao().equals("R")){ //A=acatamento E=emcaminhamento R=Retomada
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "RETOMADA");
		}
		carregaOrcamentoServExe(); // carrega a descrição do serviço 
	}

	/**
	 * Inicializa as perguntas e respostas para o AutoAtendimento
	 * Reseta todas as variaveis de controle de fluxo de perguntas
	 */
	@Override
	public void inicializar(ActionEvent e) {
		try{
			carregarPerguntasAutoAtendimento(AutoAtendimentoEnum.POUCA_PRESSAO);
			perguntaAtual = (PerguntaAutoAtendimento) getListaPerguntas().get(0); //Seta a primeira pergunta
			setListaPerguntasRespondidas(new ArrayList<PerguntaAutoAtendimento>()); //reseta a lista de respostas
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, null); //reseta o outcome param
			exibirAcatamento = false; //flag do painel de acatamento false
			exibirBotaoConclusao =  false; //flag do botao de conclusao false
			nrSeqPergunta = 2; //Seta o numero de sequencia das perguntas
			variavelOutcome = "PROSSEGUIR_PESQUISA_RGI"; 
			setCheckConfirmacao(false); //reseta o check de confirmação
		}catch (Exception ex) {
			ex.printStackTrace();
			agvlogger.info("Erro busca das perguntas"+ex.getMessage());
			
		}
	}
	
	public void validaConfirmacao(ActionEvent e) {
		
		if(isCheckConfirmacao()){
			/**
			 * Caso a resposta seja Sim ou Não sei, o sistema deve registrar o autoatendimento para identificar o imóvel por endereço.
			 */
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, null);
			int nrPergResp = getListaPerguntasRespondidas().size();
			String resposta = getListaPerguntasRespondidas().get(--nrPergResp).getDsRespostaSelecionada().trim();
			if(resposta.equals("Não")){
				getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "PROSSEGUIR_PESQUISA_SOMENTE_RGI");
				variavelOutcome =  "PROSSEGUIR_PESQUISA_SOMENTE_RGI";	
			}
		}else{
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "ERRO");
//			addMessage(FacesMessage.SEVERITY_ERROR,FacesBundleUtil.getInstance().getString("autoatendimento.erro.confirmar"), null);
			FacesContext.getCurrentInstance().addMessage(null,
				    new FacesMessage(FacesMessage.SEVERITY_ERROR,  FacesBundleUtil.getInstance().getString("autoatendimento.erro.confirmar"), null));
		}
	}

	public void afterPropertiesSet() throws Exception {
		
	}

	@Override
	/**
	 Sim	POUCA PRESSÃO GERAL.	09.0004
	 Não	POUCA PRESSÃO LOCAL.	09.0003
	 Não sei	POUCA PRESSÃO GERAL.	09.0004
	 */
	public String prosseguirCliente(){
		if(!validaCamposCliente(getCliente())){
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, forwardOutcome(OUTCOME_ERROR));
			return forwardOutcome(OUTCOME_ERROR);
		}
		
		aaBBean.adicionarDadosSolicitanteAutoAtendimento(getDadosImoveisTO());
		
		if(!acatarServico(acao)){
			return forwardOutcome(OUTCOME_ERROR);
		}
		
//		AcatamentoServicoTO acatamentoServicoTO = criaAcatamentoTO();
//			
//			try {
//				agvlogger.info("AGV - Acatamento ===> Inicio Acatamento POUCA_PRESSAO \n" );
//				acatamentoServico.acataServico(acatamentoServicoTO);
//				numeroSolicitacao = acatamentoServicoTO.getProtocolo();
//				msgErroAcatamento = acatamentoServicoTO.getMensagemCSI();
//				agvlogger.info("AGV - Acatamento ===> Fim Acatamento POUCA_PRESSAO \n" );
//				
//			} catch (Exception e1) {
//				e1.printStackTrace();
//				agvlogger.info("ERRO ACATAMENTO DE SERVIÇO"+ e1.getMessage());
//			}
//		}
		
		aaBBean.fecharAutoAtendimentoPergunta(12L, SituacaoAtendimentoEnum.CONCLUIDO, 3L);
		
		return forwardOutcome(OUTCOME_PROSSEGUIR);
	}

	public String getVariavelOutcome() {
		return variavelOutcome;
	}

	public void setVariavelOutcome(String variavelOutcome) {
		this.variavelOutcome = variavelOutcome;
	}

	public boolean isCheckConfirmacao() {
		return checkConfirmacao;
	}

	public void setCheckConfirmacao(boolean checkConfirmacao) {
		this.checkConfirmacao = checkConfirmacao;
	}

	@Override
	protected AcatamentoServicoTO criaAcatamentoTO() {
		
		AcatamentoServicoTO acatamentoServicoTO =  new AcatamentoServicoTO();
		acatamentoServicoTO .setOrcamentoOferecido(orcamento);
		acatamentoServicoTO.setRgi(toLong(getDadosImoveisTO().getImovel().getDsRgi()));
		//Caso a resposta seja “Sim” ou “Não sei”, o sistema deve registrar o autoatendimento para identificar o imóvel por endereço.
		if(!resposta.equals("Não")){
			acatamentoServicoTO.setCdGrupoServico(Integer.valueOf(9));
			acatamentoServicoTO.setCdSubServico(Integer.valueOf(4));
		//Se a diretoria é M
		}else if(resposta.equals("Não")){ 
			acatamentoServicoTO.setCdGrupoServico(Integer.valueOf(9));
			acatamentoServicoTO.setCdSubServico(Integer.valueOf(3));
		}
		//Seta solicitante do acatamento
		acatamentoServicoTO.setSolicitante(parseSolicitanteTO(getDadosImoveisTO().getSolicitante()));
		return acatamentoServicoTO;
	}

	@Override
	protected String obterOutcomeImovelIdentificado() {
		return OUTCOME_IMOVEL_IDENTIFICADO;
	}
}
