package com.prime.app.agvirtual.web.jsf.emergencia;

import static com.prime.infra.util.WrapperUtils.isNotNull;

import java.util.ArrayList;
import java.util.List;

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
import com.prime.app.agvirtual.web.jsf.ErasingBean;
import com.prime.app.agvirtual.web.jsf.constantes.Perguntas;
import com.prime.app.agvirtual.web.jsf.constantes.Respostas;
import com.prime.app.agvirtual.web.jsf.util.ReflectionFacade;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * BackBean repsonsavel pelo controle da funcionalidade de Vazamento na Rede
 * @author gustavons
 */
@Component
@Scope(value="session")
public class VazamentoBBean extends BasicAutoAtendimentoBBean implements InitializingBean, ErasingBean {
	private static final Logger agvlogger = LoggerFactory.getLogger(VazamentoBBean.class);
	private static final long serialVersionUID = -2156596137507142102L;
	
	private static final String OUTCOME_IMOVEL_IDENTIFICADO = "EMERGENCIA_VAZAMENTO_REDE_IDENTIFICADO";
	
	@Autowired private AutoAtendimentoBBean aaBBean;
	
	/**
	 * Registra rgi identificado
	 * 
	 * Chamado ao identificar imovel
	 */
	@Override
	protected void gravarRgiAutoAtendimento(Imovel imovel) {
		if(isNotNull(imovel)) aaBBean.adicionarRgiAutoAtendimento(imovel.getDsRgi()); // grava RGI
	}
	
	private String resposta = "";
	private boolean checkConfirmacao;
	private String variavelOutcome = "PROSSEGUIR_PESQUISA_RGI";
	private String encaminhar;
	
	public VazamentoBBean(){
		resposta = "";
	}
	
	@Override
	protected void realizarAcao() {	

		//TODO - registrar log para AutoAtendimento 
		
		exibirBotaoConclusao = true;
		
		if(acao.getTpAcao().equals(TIPO_ACAO_ACATAMENTO)){ //A=acatamento E=emcaminhamento R=Retomada
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "PROSSEGUIR");
			exibirAcatamento = true;
			carregaOrcamentoServExe(); // carrega a descrição do serviço
		}else if(acao.getTpAcao().equals(TIPO_ACAO_RETOMADA)){ //A=acatamento E=emcaminhamento R=Retomada
			
			Integer conjResposta0 = (Integer) WrapperUtils.toInt( getListaPerguntasRespondidas().get(0).getCodRespostaSelecionado() );
			Integer conjResposta1 = (Integer) WrapperUtils.toInt( getListaPerguntasRespondidas().get(1).getCodRespostaSelecionado() );
			Integer conjResposta2 = (Integer) WrapperUtils.toInt( getListaPerguntasRespondidas().get(2).getCodRespostaSelecionado() );
			
			if( conjResposta0 == 28 && conjResposta1 == 10 && conjResposta2 == 4 )
			{
				
				executeMethodByClass("consertoCavaleteBBean", "inicializarReflection", new Object[] {});
			
				getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "CONSERTO_CAVALETE_RETOMADA");
			}

			if( conjResposta0 == 28 && conjResposta1 == 11 && conjResposta2 == 4 )
			{
				
				executeMethodByClass("consertoHidrometroBBean", "inicializarReflection", new Object[] {});				
				
				getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "CONSERTO_HIDROMETRO_RETOMADA");
			}

			if( conjResposta0 == 29 && conjResposta1 == 20 && conjResposta2 == 4 )
				getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "EMERGENCIA_ESGOTO_ENTUPIDO_RETOMADA");			

			
			//carregaOrcamentoServExe(); // carrega a descrição do serviço
		}else if(acao.getTpAcao().equals(TIPO_ACAO_ENCAMINHAMENTO)){
			// verifica regra de encaminhamento
			boolean encaminhar = execRegraEncaminhamento(getListaPerguntasRespondidas());
			if(encaminhar){
				realizaEncaminhamento();
			}			
		}
	}
	
	/**
	 * Encaminhar usando regra
	 * 
	 * a	a	b
	 * a	b	b
	 */
	private void realizaEncaminhamento(){		
		getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, encaminhar);
	}
	
	/**
	 * Valida pergunta
	 */
	public boolean execRegraEncaminhamento(List<PerguntaAutoAtendimento> perguntas){
		Boolean result = Boolean.FALSE;
		
		for(PerguntaAutoAtendimento pergunta : perguntas){
			agvlogger.info("Pergunta: " + pergunta.getCdPergunta() + " - " + pergunta.getDsPergunta());
			agvlogger.info("Resposta: " + pergunta.getCodRespostaSelecionado() + " - " + pergunta.getDsRespostaSelecionada());
			
			// valida pergunta 1
			if(pergunta.getCdPergunta() == Perguntas.ESCOLHA_TIPO_DE_VAZAMENTO){
				if(!validaPergunta(pergunta, Perguntas.ESCOLHA_TIPO_DE_VAZAMENTO, Respostas.AGUA)){
					break;
				}	
			}
			
			// valida pergunta 2			
			if(pergunta.getCdPergunta() == Perguntas.ESCOLHA_LOCAL_ONDE_HA_VAZAMENTO){
				if(!validaPergunta(pergunta, Perguntas.ESCOLHA_LOCAL_ONDE_HA_VAZAMENTO, Respostas.CALCADA_CIMENTADA)){
					if(!validaPergunta(pergunta, Perguntas.ESCOLHA_LOCAL_ONDE_HA_VAZAMENTO, Respostas.CALCADA_DE_TERRA)){
						break;
					}else{
						encaminhar = "CONSERTO_HIDROMETRO";
					}
				}else{
					encaminhar = "CONSERTO_CAVALETE";
				}
			}
			
			// valida pergunta 3			
			if(pergunta.getCdPergunta() == Perguntas.AGUA_INFILTRANDO_OU_INVADINDO_IMOVEL){
				if(!validaPergunta(pergunta, Perguntas.AGUA_INFILTRANDO_OU_INVADINDO_IMOVEL, Respostas.NAO)){
					break;
				}
				result = Boolean.TRUE;  // validado
			}
		}
		
		return result;
	}
	
	/**
	 * Valida pergunta respondida.
	 */
	public boolean validaPergunta(PerguntaAutoAtendimento pergunta, int codPergunta, String codResposta){
		if(pergunta.getCdPergunta()==codPergunta){
			if(pergunta.getCodRespostaSelecionado().equals(codResposta)){
				agvlogger.info("Pergunta OK");
				return Boolean.TRUE;
			}
			agvlogger.info("Pergunta NOK");
		}
		
		return Boolean.FALSE;
	}
	
	/**
	 * Carrega proxima pergunta 
	 * 
	 *  Neste caso de uso uma pergunta pode ter respostas diferentes.
	 *  Ex: Pergunta 1 - Resposta: Agua  ->  
	 *      Pergunta 1 - 
	 */
	@Override
	protected void proximaPergunta(){
		int numRespostas = getListaPerguntasRespondidas().size();
		int orderPergunta =getListaPerguntasRespondidas().get(--numRespostas).getNrOrdemPergunta();
		orderPergunta++;
		perguntaAtual = null;
		
		for (Object element : getListaPerguntas()) {
			PerguntaAutoAtendimento pergElement = (PerguntaAutoAtendimento) element;
			if(pergElement.getNrOrdemPergunta().intValue() == (orderPergunta)){
				// 
				carregaPerguntaPorDiretoria(orderPergunta, pergElement);
			}
		}
	}

	/**
	 * @param contador
	 * @param pergElement
	 */
	private void carregaPerguntaPorDiretoria(int orderPergunta,
			PerguntaAutoAtendimento pergElement) {
		if(orderPergunta == 1){
			perguntaAtual = pergElement;
		}else if(orderPergunta == 2){
			int codRepostaAnterior = WrapperUtils.toInt(getListaPerguntasRespondidas().get(0).getCodRespostaSelecionado());
			if(pergElement.getCdPergunta() == 14 && codRepostaAnterior == 28){
				perguntaAtual = pergElement;
				nrSeqPergunta++;
			} 
			if(pergElement.getCdPergunta() == 21 && codRepostaAnterior == 29){
				perguntaAtual = pergElement;
				nrSeqPergunta++;
			}
		}else if(orderPergunta == 3){
			 int codPerguntaAnterior = getListaPerguntasRespondidas().get(1).getCdPergunta();
			if(pergElement.getCdPergunta() == 15 && codPerguntaAnterior == 14){
				perguntaAtual = pergElement;
				nrSeqPergunta++;
			}
			if(pergElement.getCdPergunta() == 16 && codPerguntaAnterior == 21){
				perguntaAtual = pergElement;
				nrSeqPergunta++;
			}
		}
	}
	
	
	/**
	 * Delete a ultima pergunta respondida
	 * e adiciona uma nova pergunta atual 
	 * Se pergunta igual a 2 , verificar qual foi a primeira resposta da pergunta 1
	 */
	@Override
	public void perguntaAnterior(ActionEvent e){
		int nrTemp = getListaPerguntasRespondidas().size();
		int contador =getListaPerguntasRespondidas().get(--nrTemp).getNrOrdemPergunta();
		PerguntaAutoAtendimento respostaDelete =  null;
		for (PerguntaAutoAtendimento pergRespondida : getListaPerguntasRespondidas()) {
			if(pergRespondida.getNrOrdemPergunta().intValue() == (contador)){
				respostaDelete = pergRespondida;
				break;
			}
		}
		
		if(respostaDelete !=  null){
			getListaPerguntasRespondidas().remove(respostaDelete);
		}
		
		for (Object element : getListaPerguntas()) {
			PerguntaAutoAtendimento temp = (PerguntaAutoAtendimento) element;
			if(temp.getNrOrdemPergunta().intValue() == (contador)){
				carregaPerguntaPorDiretoria(contador,temp);
				break;
			}
		}
		
		exibirAcatamento = false;
		exibirBotaoConclusao = false;
	}
	

	/**
	 * Inicializa as perguntas e respostas para o AutoAtendimento
	 * Reseta todas as variaveis de controle de fluxo de perguntas
	 */
	@Override
	public void inicializar(ActionEvent e) {
		try{
			carregarPerguntasAutoAtendimento(AutoAtendimentoEnum.VAZAMENTO_REDE);
			perguntaAtual = (PerguntaAutoAtendimento) getListaPerguntas().get(0); //Seta a primeira pergunta
			setListaPerguntasRespondidas(new ArrayList<PerguntaAutoAtendimento>()); //reseta a lista de respostas
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, null); //reseta o outcome param
			exibirAcatamento = false; //flag do painel de acatamento false
			exibirBotaoConclusao =  false; //flag do botao de conclusao false
			nrSeqPergunta = 2; //Seta o numero de sequencia das perguntas
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
			}
		}else{
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "ERRO");
//			addMessage(FacesMessage.SEVERITY_ERROR,FacesBundleUtil.getInstance().getString("autoatendimento.erro.confirmar"), null);
			FacesContext.getCurrentInstance().addMessage(null,
				    new FacesMessage(FacesMessage.SEVERITY_ERROR,  FacesBundleUtil.getInstance().getString("autoatendimento.erro.confirmar"), null));
		}
	}

	public void afterPropertiesSet() throws Exception {
		carregarPerguntasAutoAtendimento(AutoAtendimentoEnum.VAZAMENTO_REDE);
		perguntaAtual = (PerguntaAutoAtendimento) getListaPerguntas().get(0); //Seta a primeira pergunta
		setListaPerguntasRespondidas(new ArrayList<PerguntaAutoAtendimento>()); //reseta a lista de respostas
	}

	@Override
	public String prosseguirCliente(){
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
		acatamentoServicoTO.setRgi(WrapperUtils.toLong(getDadosImoveisTO().getImovel().getDsRgi()));
		acatamentoServicoTO.setCdGrupoServico(acao.getCdServicoCsi());
		acatamentoServicoTO.setCdSubServico(acao.getCdGrupoServiceCsi());
		//Seta solicitante do acatamento
		acatamentoServicoTO.setSolicitante(parseSolicitanteTO(getDadosImoveisTO().getSolicitante()));
		return acatamentoServicoTO;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getVariavelOutcome() {
		return variavelOutcome;
	}

	public void setVariavelOutcome(String variavelOutcome) {
		this.variavelOutcome = variavelOutcome;
	}

	public void eraseBeanAtributes() {
		carregarPerguntasAutoAtendimento(AutoAtendimentoEnum.VAZAMENTO_REDE);
		perguntaAtual = (PerguntaAutoAtendimento) getListaPerguntas().get(0); //Seta a primeira pergunta
		setListaPerguntasRespondidas(new ArrayList<PerguntaAutoAtendimento>()); //reseta a lista de respostas
		existeMsgErroValidacao = false;
		
		getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, null); //reseta o outcome param
		exibirAcatamento = false; //flag do painel de acatamento false
		exibirBotaoConclusao =  false; //flag do botao de conclusao false
		nrSeqPergunta = 2; //Seta o numero de sequencia das perguntas
		setCheckConfirmacao(false); //reseta o check de confirmação

		
	}
	
	@Override
	protected String obterOutcomeImovelIdentificado() {
		return OUTCOME_IMOVEL_IDENTIFICADO; 
	}
	
}
