package com.prime.app.agvirtual.web.jsf.conserto;

import static com.prime.infra.util.WrapperUtils.toLong;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.enums.AutoAtendimentoEnum;
import com.prime.app.agvirtual.to.AcatamentoServicoTO;
import com.prime.app.agvirtual.web.jsf.BasicAutoAtendimentoAcaoBBean;
import com.prime.app.agvirtual.web.jsf.BasicNavegacaoBBean;
import com.prime.app.agvirtual.web.jsf.ErasingBean;
import com.prime.app.agvirtual.web.jsf.util.SessionUtil;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * BackBean repsonsavel pelo controle da funcionalidade da Conserto do Cavalete
 * @author felipepm
 */
@Component
@Scope(value="session")
public class ConsertoCavaleteBBean extends BasicAutoAtendimentoAcaoBBean implements ErasingBean {
	private static final long serialVersionUID = -8460663760052605214L;
	private static final Logger agvlogger = LoggerFactory.getLogger(ConsertoCavaleteBBean.class);
	
	private static final String PE_INFERIOR_CAVALETE = "PE_INFERIOR_CAVALETE";
	private static final String OUTCOME_IMOVEL_IDENTIFICADO = "CONSERTO_CAVALETE_IMOVEL_IDENTIFICADO";
	
	private boolean peInferiorCavalete = false;
	
	@Override
	protected void realizarAcao() {}

	public void inicializarReflection(){
		initialize();
	}
	
	@Override
	public void initialize() {
		//Altera o 2º nível da migalha de pão (Tratamento Diferenciado pois o tela não trata a mudança de local)
		String cdSubMenu = getRequestParameterMap(PARAMETRO_CODIGO_SUB_MENU);
		String dsSubMenu = getRequestParameterMap(PARAMETRO_DESCRICAO_SUB_MENU);
		String outcomeSubMenu = getRequestParameterMap(PARAMETRO_OUTCOME_SUB_MENU);

		SessionUtil.adicionarAtributo(PARAMETRO_CODIGO_SUB_MENU, cdSubMenu, getHttpSession(Boolean.FALSE));
		SessionUtil.adicionarAtributo(PARAMETRO_OUTCOME_SUB_MENU, outcomeSubMenu, getHttpSession(Boolean.FALSE));
		SessionUtil.adicionarAtributo(PARAMETRO_DESCRICAO_SUB_MENU, dsSubMenu, getHttpSession(Boolean.FALSE));
		
		try{
			//Validações do pé inferior do cavalete
			String regraCavalete = getRequestParameterMap("localCavalete");
			if (PE_INFERIOR_CAVALETE.equalsIgnoreCase(regraCavalete)) {
				peInferiorCavalete = true;
			} else {
				peInferiorCavalete = false;
			}
			
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, null); //reseta o outcome param
			carregaDadosCorrelacao(AutoAtendimentoEnum.CONSERTO_CAVALETE);
			
			acao = buscaAcao(AutoAtendimentoEnum.CONSERTO_CAVALETE.getCodigo());
			
			carregaOrcamentoServExe(); // carrega a descrição do serviço 
			orcamento.setMsgCobranca(FacesBundleUtil.getInstance().getString("consertocavalete.orcamento.info")); // carrega a mensagem de informação para o usuário
		}catch (Exception ex) {
			ex.printStackTrace();
			agvlogger.info("Erro busca das perguntas"+ex.getMessage());
		}
		
		//Limpando os dados da página de pesquisa do RGI
		setImovel(new Imovel());
		setNomeEnderecoView("");
		setCheckConfirmacao(false); //reseta o check de confirmação
		limparListaPerguntasRespondidas();
	}
	
	/**
	 * Inicializa Bean. 
	 * Metodo chamado em TELA pelo faces
	 */
	public void inicializarConserto(ActionEvent e) {
		initialize();
	}
	
	@Override
	public String prosseguirCliente() {
		// valida campos cliente
		if(!validaCamposCliente(getCliente())){
			return forwardOutcome(OUTCOME_ERROR);
		}
		// realiza acatamento
		if(!acatarServico(acao)){
			return forwardOutcome(OUTCOME_ERROR);
		}
		return forwardOutcome(OUTCOME_PROSSEGUIR);		
	}

	@Override
	public void confirmaAcatamento(ActionEvent e){		
		if(!getCheckConfirmacao()){
			addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("autoatendimento.erro.confirmar"), null);
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "ERRO");
		}else{
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, null);
		}
	}

	@Override
	protected AcatamentoServicoTO criaAcatamentoTO() {
		AcatamentoServicoTO acatamentoServicoTO = new AcatamentoServicoTO();
		
		acatamentoServicoTO.setRgi(toLong(getDadosImoveisTO().getImovel().getDsRgi()));
		acatamentoServicoTO.setSolicitante(parseSolicitanteTO(getDadosImoveisTO().getSolicitante()));
		
		//Regra caso o local selecionado seja pé interno do cavalete
		if (peInferiorCavalete) {
			acatamentoServicoTO.setPrioridade(22);
		}
		
		return acatamentoServicoTO;
	}

	public boolean isPeInferiorCavalete() {
		return peInferiorCavalete;
	}

	public void setPeInferiorCavalete(boolean peInferiorCavalete) {
		this.peInferiorCavalete = peInferiorCavalete;
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
