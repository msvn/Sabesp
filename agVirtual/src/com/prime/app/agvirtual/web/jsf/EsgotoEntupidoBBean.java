package com.prime.app.agvirtual.web.jsf;

import static com.prime.infra.util.WrapperUtils.isNotNull;

import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

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
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.app.agvirtual.to.OrcamentoOferecidoTO;
import com.prime.app.agvirtual.web.jsf.util.SessionUtil;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

@Component
@Scope(value="session")
public class EsgotoEntupidoBBean extends BasicAutoAtendimentoBBean implements InitializingBean{
	private static final long serialVersionUID = -5587776358275389183L;

	private static final String OUTCOME_IMOVEL_IDENTIFICADO = "EMERGENCIA_ESGOTO_ENTUPIDO_IDENTIFICADO";
	private OrcamentoOferecidoTO orcamento = null;
	
	@Autowired private AutoAtendimentoBBean aaBBean;
	
	@Override
	public void initialize() {
		//initBasicAutoAtendimentoBean();
		carregaDadosCorrelacao(AutoAtendimentoEnum.ESGOTO_ENTUPIDO);
		acao = buscaAcao(getListaPerguntasRespondidas());
		carregaOrcamentoServExe();
		exibirAcatamento = Boolean.TRUE;
	}
	
	@Override
	public void inicializar(ActionEvent e) {
		initialize();
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
//		orcamento=	orcamentoService.getTipoOrcamento(WrapperUtils.toLong(getDadosImoveisTO().getImovel().getDsRgi()),21, 10);
//		orcamento.setValorTotal(orcamentoService.getValorServico(WrapperUtils.toLong(getDadosImoveisTO().getImovel().getDsRgi()), 21, 10,1));//FIXME arrumar ultimo parametro
		exibirAcatamento = true;
	}
	
	@Override
	protected void realizaValidacaoEspecifica() {
		// TODO Auto-generated method stub
		
		PerguntaAutoAtendimento  primeiraPergunta = getListaPerguntasRespondidas().get(0);
		
		PerguntaAutoAtendimento  segundaPergunta = getListaPerguntasRespondidas().get(1);
		
		PerguntaAutoAtendimento  terceiraPergunta = getListaPerguntasRespondidas().get(2);
		
		// 6.2.1.	[FA1] O cliente responde "Não" para a primeira pergunta e "Não" para a segunda;
		if( primeiraPergunta.getDsRespostaSelecionada().equals(SIMNAOEnum.NAO.getValue()) && segundaPergunta.getDsRespostaSelecionada().equals(SIMNAOEnum.NAO.getValue()) ) {

			//TODO: atendimento registra o motivo de insucesso para o relatório de atendimento 
			
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "FA_CANAIS_ATENDIMENTO");
			
		}
		
		acao = buscaAcao(getListaPerguntasRespondidas());  // carrega descricao do orcamento, tela esgotoentupidoconfirmar NAO MEXER
		if(isNotNull(acao)) carregaOrcamentoServExe();
	}

	@Override
	public String prosseguirCliente() {
		
		// valida campos cliente
		if(!validaCamposCliente(getCliente())){
			return forwardOutcome(OUTCOME_ERROR);	
		}
		
		aaBBean.adicionarDadosSolicitanteAutoAtendimento(getDadosImoveisTO());
		
		// realiza acatamento
		if(!acatarServico(acao)){
			return forwardOutcome(OUTCOME_ERROR);
		}
		
		getDadosImoveisTO().setSolicitante(getCliente());
		aaBBean.fecharAutoAtendimentoPergunta(12L, SituacaoAtendimentoEnum.CONCLUIDO, 3L);
		
		return forwardOutcome(OUTCOME_PROSSEGUIR);
	}
	
	@Override
	public void processaAcatamento(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected AcatamentoServicoTO criaAcatamentoTO() {
		AcatamentoServicoTO acatamentoServicoTO = new AcatamentoServicoTO();
		
		acatamentoServicoTO.setRgi(WrapperUtils.toLong(getDadosImoveisTO().getImovel().getDsRgi()));
		acatamentoServicoTO.setSolicitante(parseSolicitanteTO(getCliente()));				
//		acatamentoServicoTO.setObs("");
//		acatamentoServicoTO.setPrioridade(1);
//		getDadosImoveisTO().getImovel().setDsDiretoria("M");
		
		return acatamentoServicoTO;
	}

	public void afterPropertiesSet() throws Exception {
		carregarPerguntasAutoAtendimento( AutoAtendimentoEnum.ESGOTO_ENTUPIDO );
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
	 * Nao identifica imovel caso o mesmo ja teha sido identificado.
	 */	
	@Override
	protected String obterOutcomeImovelIdentificado() {
		return OUTCOME_IMOVEL_IDENTIFICADO;
	}
	
}
