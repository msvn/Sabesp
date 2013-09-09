package com.prime.app.agvirtual.web.jsf.conserto;

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
import com.prime.app.agvirtual.to.AcatamentoServicoTO;
import com.prime.app.agvirtual.web.jsf.AutoAtendimentoBBean;
import com.prime.app.agvirtual.web.jsf.BasicAutoAtendimentoBBean;
import com.prime.app.agvirtual.web.jsf.BasicNavegacaoBBean;
import com.prime.app.agvirtual.web.jsf.ErasingBean;
import com.prime.app.agvirtual.web.jsf.util.SessionUtil;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * BackBean repsonsavel pelo controle da funcionalidade da Troca do Registro do Cavalete.
 * @author felipepm
 */
@Component
@Scope(value="session")
public class ConsertoRegistroBBean extends BasicAutoAtendimentoBBean implements ErasingBean {
	private static final long serialVersionUID = -8460663760052605214L;
	private static final Logger agvlogger = LoggerFactory.getLogger(ConsertoRegistroBBean.class);

	private static final String OUTCOME_IMOVEL_IDENTIFICADO = "CONSERTO_REGISTRO_IMOVEL_IDENTIFICADO";
	
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
	
	@SuppressWarnings("unchecked")
	public void inicializarConserto(ActionEvent e) {
		
		//Altera o 2º nível da migalha de pão (Tratamento Diferenciado pois o tela não trata a mudança de local)
		String cdSubMenu = getRequestParameterMap(PARAMETRO_CODIGO_SUB_MENU);
		String dsSubMenu = getRequestParameterMap(PARAMETRO_DESCRICAO_SUB_MENU);
		String outcomeSubMenu = getRequestParameterMap(PARAMETRO_OUTCOME_SUB_MENU);

		SessionUtil.adicionarAtributo(PARAMETRO_CODIGO_SUB_MENU, cdSubMenu, getHttpSession(Boolean.FALSE));
		SessionUtil.adicionarAtributo(PARAMETRO_OUTCOME_SUB_MENU, outcomeSubMenu, getHttpSession(Boolean.FALSE));
		SessionUtil.adicionarAtributo(PARAMETRO_DESCRICAO_SUB_MENU, dsSubMenu, getHttpSession(Boolean.FALSE));
		
		initBasicAutoAtendimentoBean();
		
		try{
			carregarPerguntasAutoAtendimento(AutoAtendimentoEnum.CONSERTO_REGISTRO);
			perguntaAtual = (PerguntaAutoAtendimento) getListaPerguntas().get(0); //Seta a primeira pergunta
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, null); //reseta o outcome param
			setListaPerguntasRespondidas(new ArrayList()); //reseta a lista de respostas			
			setCheckConfirmacao(false); 				   //reseta o check de confirmação
			
			carregaDadosCorrelacao(AutoAtendimentoEnum.CONSERTO_REGISTRO);
		}catch (Exception ex) {
			ex.printStackTrace();
			agvlogger.info("Erro busca das perguntas"+ex.getMessage());
		}
	}

	@Override
	public void confirmaAcatamento(ActionEvent e){		
		if(!checkConfirmacao){
			addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("autoatendimento.erro.confirmar"), null);
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "ERRO");
		}else{
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, null);
		}
	}
	
	@Override
	public String prosseguirCliente() {

		if(!validaCamposCliente(getCliente())){
			return forwardOutcome(OUTCOME_ERROR);
		}
		
		// realiza acatamento
		if(!acatarServico(acao)){
			return forwardOutcome(OUTCOME_ERROR);
		}

		getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName,null);
		
		return forwardOutcome(OUTCOME_PROSSEGUIR);
	}
	
	@Override
	protected AcatamentoServicoTO criaAcatamentoTO() {
		AcatamentoServicoTO acatamentoServicoTO = new AcatamentoServicoTO();
		
		acatamentoServicoTO.setRgi(WrapperUtils.toLong(getDadosImoveisTO().getImovel().getDsRgi()));
		acatamentoServicoTO.setSolicitante(parseSolicitanteTO(getDadosImoveisTO().getSolicitante()));

		PerguntaAutoAtendimento pergResp = getListaPerguntasRespondidas().get(0);
		
		acatamentoServicoTO.setObs(pergResp.getDsRespostaSelecionada());

		if("37".equals(pergResp.getCodRespostaSelecionado())){ //Não veda (tento fechar e não consigo)
			acatamentoServicoTO.setPrioridade(21);
		}
		return acatamentoServicoTO;	
	}

	@Override
	protected void realizarAcao() {
		acao = buscaAcao(getListaPerguntasRespondidas());
		carregaOrcamentoServExe(); // carrega a descrição do serviço 
		orcamento.setMsgCobranca(FacesBundleUtil.getInstance().getString("trocaregistro.orcamento.info")); // carrega a mensagem de informação para o usuário
	}
	
	@SuppressWarnings("unchecked")
	public void eraseBeanAtributes() {
		setCheckConfirmacao(false); //reseta o check de confirmação
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
