package com.prime.app.agvirtual.web.jsf;

import static com.prime.infra.util.WrapperUtils.isNotNull;
import static com.prime.infra.util.WrapperUtils.isNull;
import static com.prime.infra.util.WrapperUtils.parseMoney;
import static com.prime.infra.util.WrapperUtils.toInt;
import static com.prime.infra.util.WrapperUtils.toLong;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.prime.app.agvirtual.dao.AcatamentoServicoCSIDao;
import com.prime.app.agvirtual.eae.EAEBusinessException;
import com.prime.app.agvirtual.entity.AcaoAutoAtendimento;
import com.prime.app.agvirtual.entity.AgvTabCorrelacao;
import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.PerguntaAutoAtendimento;
import com.prime.app.agvirtual.entity.ServicoExecutado;
import com.prime.app.agvirtual.enums.AutoAtendimentoEnum;
import com.prime.app.agvirtual.enums.SIMNAOEnum;
import com.prime.app.agvirtual.enums.TipoMenu;
import com.prime.app.agvirtual.enums.TipoRelacaoClienteImovel;
import com.prime.app.agvirtual.service.AcaoAutoAtendimentoService;
import com.prime.app.agvirtual.service.AgenciaService;
import com.prime.app.agvirtual.service.AutoAtendimentoService;
import com.prime.app.agvirtual.service.CorrelacaoService;
import com.prime.app.agvirtual.service.ItemMenuService;
import com.prime.app.agvirtual.service.OrcamentoService;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;
import com.prime.app.agvirtual.to.AcatamentoServicoTO;
import com.prime.app.agvirtual.to.AgenciaTO;
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.app.agvirtual.to.OrcamentoOferecidoTO;
import com.prime.app.agvirtual.to.SolicitanteTO;
import com.prime.app.agvirtual.util.AcatamentoMensagem;
import com.prime.app.agvirtual.web.jsf.util.SessionUtil;
import com.prime.infra.BusinessException;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * Classe que contem todos os métodos comuns utilizados por BBeans de AutoAtendimento
 * @author gustavons
 *
 */
public abstract class BasicAutoAtendimentoBBean extends BasicNavegacaoPesquisaBBean {
	private static final Logger agvlogger = LoggerFactory.getLogger(BasicAutoAtendimentoBBean.class);
	private static final long serialVersionUID = 1254934166103221376L;

	public static final String TIPO_ACAO_ACATAMENTO 	= "A";
	public static final String TIPO_ACAO_ENCAMINHAMENTO = "E";
	public static final String TIPO_ACAO_RETOMADA 		= "R";
	
	@Autowired
	protected AutoAtendimentoService autoAtendimentoService;
	
	@Autowired
	protected AcatamentoServicoCSIDao acatamentoServico;
	
	@Autowired
	protected AgenciaService agenciaService;
	
	@Autowired
	protected OrcamentoService orcamentoService; 
	
	@Autowired
	protected CorrelacaoService correlacaoService;
	
	@Autowired 
	protected AcaoAutoAtendimentoService acaoService;
	
	@Autowired
	private ItemMenuService itemMenuService;
	
	private List<PerguntaAutoAtendimento> listaPerguntas;
	private List<PerguntaAutoAtendimento> listaPerguntasRespondidas;
	protected List<AgvTabCorrelacao> listaCorrelacao;
	private List<SelectItem> listaSimNao;
	protected List<SelectItem> listaCategorias;
	
	protected AcaoAutoAtendimento acao;
	private Cliente cliente; 							// tela identificar cliente	
	protected AgenciaTO agencia;
	protected OrcamentoOferecidoTO orcamento = null;    // Orcamento utilizado na cobrança de algum serviço
	protected PerguntaAutoAtendimento perguntaAtual ;
	protected int nrSeqPergunta = 1;	
	protected String numeroSolicitacao = "";			// Numero Solicitacao do acatamento -  solicitação de serviço
	protected String msgErroAcatamento = "";
	protected String msgErroAcatamentoTraduzida = "";
	protected String qtParcelas = "1";
	protected String valorParcela = "";
	
	// flags controle tela
	protected boolean exibirBotaoConclusao;
	protected boolean exibirAcatamento;
	protected boolean checkConfirmacao;  				// usado nas telas como CHECKBOX
	protected String  radioConfirmacao;                 // usado nas telas como RADIO
	
	protected boolean exibirTextoParcial;
	
	protected boolean checkAprovarOrcamento;

	public BasicAutoAtendimentoBBean(){	
		initBasicAutoAtendimentoBean();
	}
	
	/**
	 * Carrega todas as perguntas para o auto atendimento
	 */
	public void carregarPerguntasAutoAtendimento(AutoAtendimentoEnum autoAtendimento){
		if(getDadosImoveisTO().getImovel().getDsDiretoria() == null){
			listaPerguntas = autoAtendimentoService.findAAPergRespById(autoAtendimento.getCodigo()).getListaPergAutoAtendimentoTodasDiretorias();
		}else if(getDadosImoveisTO().getImovel().getDsDiretoria().equals("M")){ //FIXME criar constatnte
			listaPerguntas = autoAtendimentoService.findAAPergRespById(autoAtendimento.getCodigo()).getListaPergAutoAtendimentoMetropolitana();
//			reordenaPerguntas(listaPerguntas);
		}else if(getDadosImoveisTO().getImovel().getDsDiretoria().equals("R")){ //FIXME criar constatnte
			listaPerguntas = autoAtendimentoService.findAAPergRespById(autoAtendimento.getCodigo()).getListaPergAutoAtendimentoRegional();		
//			reordenaPerguntas(listaPerguntas);
		}
	}
	
	private void reordenaPerguntas(List<PerguntaAutoAtendimento> listaPerguntas) {
		for (int i = 0; i < listaPerguntas.size(); i++) {
			listaPerguntas.get(i).setNrOrdemPergunta(i + 1);
		}
	}
	
	// *********************************
	// METODOS DE NEGOCIO
	// ********************************* 
	
	/**
	 * Realiza todas as acoes necessarias para acatamento do auto atendimento
	 * 
	 * Ex: exibe tela especifica de exec da acao / carrega dados
	 */
	protected abstract void realizarAcao();	
	
	/**
	 * Cria TO com informacoes necessarias ao acatamento
	 */
	protected abstract AcatamentoServicoTO criaAcatamentoTO();	
	
	/**
	 * Executa o acatamento chamado no actionlistener da tela de cliente / telas de Ligação de esgoto e Água
	 * @param e
	 */
	public void processaAcatamento(ActionEvent e){}

	/**
	 * Busca acao. 
	 * 
	 * Acao contem dados necessarios para acatamento do servico
	 */
	protected AcaoAutoAtendimento buscaAcao(List<PerguntaAutoAtendimento> listaPerguntasRespondidas){
		try {
			acao = localizarAcao(listaPerguntasRespondidas);
		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
		}
		return acao;
	}	
	
	/**
	 * Acatar servico
	 */
	protected boolean realizarAcatamentoServico(){
//		AcaoAutoAtendimento acao = buscaAcao(getListaPerguntasRespondidas());
		
		if(acao!=null){
			return acatarServico(acao);
		} else {
			tratarErroSistemico(new NullPointerException("Ação não carregada, favor carregar a ação!"));
		}

		return Boolean.FALSE;
	}	
	
	/**
	 * Metodo generico para acatamento do servico
	 * 
	 * Sobrescreva este metodo caso precise executar uma regra mais especifica
	 */
	protected boolean acatarServico(AcaoAutoAtendimento acao){
		agvlogger.info("Inicio acatarServico");
		
//		String retorno = "";
		
		AcatamentoServicoTO acatamentoServicoTO = criaAcatamentoTO();
		
		// set dados da acao
		acatamentoServicoTO.setCdGrupoServico(acao.getCdGrupoServiceCsi());
		acatamentoServicoTO.setCdSubServico(acao.getCdServicoCsi());
		try {
			acatamentoServico.acataServico(acatamentoServicoTO);
			numeroSolicitacao = acatamentoServicoTO.getProtocolo();
			msgErroAcatamento = acatamentoServicoTO.getMensagemCSI();
			
			agvlogger.info("LOG ACATAMENTO: CdServico=" + acao.getCdServicoCsi() + ", CdGrupoServico=" + acao.getCdGrupoServiceCsi() + ", numeroSolicitacao=" + numeroSolicitacao + ", msgErro=" + msgErroAcatamento);
			
			if(isNotNull(msgErroAcatamento) && !msgErroAcatamento.isEmpty()){
				// Faz o de para da msg de erro para a msg a ser apresentada para o usuário.
				msgErroAcatamento = AcatamentoMensagem.recuperaMensagem(msgErroAcatamento);
				agvlogger.info("Servico [NAO] acatado, msg de erro retornada: " + msgErroAcatamento);
				return Boolean.FALSE;
			}
			
			agvlogger.info("Servico acatado com sucesso!");
			return Boolean.TRUE;	
		}catch(EAEBusinessException e){
			// trata erro de negocio
			String msg = "Erro ao acatar servico para codGrupoServico = [" + acao.getCdServicoCsi() + "], cdSubGrupoServico = [" + acao.getCdGrupoServiceCsi()+"].";
			addMessage(FacesMessage.SEVERITY_ERROR, msg, null);
//			if(this.agvlogger.isInfoEnabled()){agvlogger.isDebugEnabled();
				msgErroAcatamento = AcatamentoMensagem.recuperaMensagem(e.getMessage());
				addMessage(FacesMessage.SEVERITY_ERROR, msgErroAcatamento, null);
//			}
			agvlogger.error(msg, e);			
		}catch (Exception e) {
			// trata erro sistemico (ou nao esperado)			
			String msg = "Erro ao acatar servico para codGrupoServico = [" + acao.getCdServicoCsi() + "], cdSubGrupoServico = [" + acao.getCdGrupoServiceCsi()+"]";
			addMessage(FacesMessage.SEVERITY_ERROR, msg, null);
			agvlogger.error(msg, e);
			tratarErroSistemico(e);
		}
		
		return Boolean.FALSE;
	}
	
	/**
	 * Verifica se o usuario concordou com o acatamento para efetuar a solicição
	 * @param e
	 */
	public void confirmaAcatamento(ActionEvent e){
		boolean realizarAcatamento = Boolean.FALSE;
		
		
		
		// radio confirmacao - usado em telas parcelamento
		if(isNotNull(radioConfirmacao)){
			realizarAcatamento = validaRadioConfirmacao(radioConfirmacao);
			radioConfirmacao = null;         										// evita que estado seja mantido
		// check - usado nas demais telas	
		}else{
			realizarAcatamento = checkConfirmacao;
		}
		
		if(realizarAcatamento){
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, null);
		}else{
			addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("autoatendimento.erro.confirmar"), null);
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "ERRO");		// evita que navegacao continue	
		}
	}
	
	private boolean validaRadioConfirmacao(String radioConfirmacao){
		return (isNotNull(radioConfirmacao) && radioConfirmacao.equals(String.valueOf(SIMNAOEnum.SIM.getCodigo())));
	}
	
	/**
	 * Verifica se existe ação a tomar
	 * 
	 * Utilizado nos casos de uso onde a acao deve ser consultada a cada pergunta respondida.
	 * 
	 * Ver caso de uso: VazamentoBBean
	 */
	public void verificaAcao(ActionEvent e){
		//Verifica se o usuario selecionou uma resposta
		boolean naoTemAcao = false;
		if(perguntaAtual.getCodRespostaSelecionado() == null){
				addMessage(FacesMessage.SEVERITY_ERROR,  FacesBundleUtil.getInstance().getString("erro.reposta.autoatendimento"), null);
				getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "ERRO");
				acao = null;
			}else{
				//Verifica Ação a ser tomada no banco
				listaPerguntasRespondidas.add(perguntaAtual);
				try{
					acao = localizarAcao(listaPerguntasRespondidas);
				}catch (NotFoundBusinessException ex) {
					naoTemAcao = true;		
				}catch (Exception e2) {
					addMessage(FacesMessage.SEVERITY_ERROR, e2.getMessage(), null);
				}
				if(naoTemAcao){
					//Verifica proxima Pergunta
					proximaPergunta();		
				}else{
					//realizaaco
					perguntaAtual = null; //controle some com as perguntas
					realizarAcao();
				}
			}
	}
	
	/**
	 * Carrega os dados do orcamento 
	 * Verifica se existe mais de um ServExe para a ação , se nao existir executa a pesquisa de orçamento
	 * Se existir mais de um ServExe , valida pelo campo atributo Imovel qual o valor que bate com o do Rgi
	 * Caso o valor do atributo nao seja encontrado , redireciona a página para a tela de encaminhamento.
	 */
	protected void carregaOrcamentoServExe() {
		ServicoExecutado servTemp = null;
		if(acao != null){
			orcamento = orcamentoService.getTipoOrcamento(toLong(getDadosImoveisTO().getImovel().getDsRgi()), acao.getCdGrupoServiceCsi(),acao.getCdServicoCsi() , null);
			orcamento.setDsOrcamento(acao.getDsAcao());
			
			if(acao.getListaServExe() !=  null && acao.getListaServExe().size() == 1 ){
				//So existe um servexe para essa ação
				servTemp = acao.getListaServExe().get(0);
				if (WrapperUtils.isNotNull(getDadosImoveisTO().getImovel().getDsRgi())) {
					orcamento.setValorTotal(orcamentoService.getValorServico(toLong(getDadosImoveisTO().getImovel().getDsRgi()), acao.getCdGrupoServiceCsi(),acao.getCdServicoCsi(),servTemp.getCdServExeCsi().intValue(), null));
				} else {
					orcamento.setValorTotal(new BigDecimal("0"));
				}
			}else if( acao.getListaServExe() !=  null && acao.getListaServExe().size() > 1 ){
				//existe um servexe para essa ação			
				for (ServicoExecutado serv : acao.getListaServExe()) {
					if(serv.getDsAtributoImovel().equals("CDCPH")){
						if(serv.getDsValorAtributo().equals(getDadosImoveisTO().getImovel().getCdCapacidadeHidrometro().toString())){
							servTemp = serv;
							break;
						}
					}else if(serv.getDsAtributoImovel().equals("CDDIAMETR")){
						if(serv.getDsValorAtributo().equals(getDadosImoveisTO().getImovel().getCdDiametroHidrometro().toString())){
							servTemp = serv;
							break;
						}
					}
				}
				if(getDadosImoveisTO().getImovel().getCdCapacidadeHidrometro().intValue() > 8){
					getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "PROSSEGUIR_ENCAMINHAMENTO");
				}else {
					if( servTemp == null ) {
						agvlogger.error("Erro no cadastro da tabela ServExe: CDCPH ou CDDIAMETR não cadastrado.");
						addMessage(FacesMessage.SEVERITY_ERROR,  FacesBundleUtil.getInstance().getString("autoatendimento.cavalete.exception.texto"), null);
						
					}else {
						orcamento.setValorTotal(orcamentoService.getValorServico(toLong(getDadosImoveisTO().getImovel().getDsRgi()), acao.getCdGrupoServiceCsi(),acao.getCdServicoCsi(),servTemp.getCdServExeCsi().intValue() , null));
					}
				}
			}
			orcamento.setDsOrcamento(acao.getDsAcao());
		}
	}

	/**
	 *  valida se TODAS as perguntas foram respondias e carrega respondidas
	 */
	public void validarCarregarRespondidas(ActionEvent e) {
		setListaPerguntasRespondidas(obterPerguntasRespondidas(getListaPerguntas()));
		
		if( isTodasPerguntasRespondidas() )
			realizaValidacaoEspecifica();
	}	
	
	/**
	 * Limpa perguntas respondidas.
	 */
	protected void limparListaPerguntasRespondidas(){
		if(isNotNull(listaPerguntasRespondidas)){
			listaPerguntasRespondidas.clear();
		}
	}
	
	/**
	 * Sobrescreva este metodo caso precise realizar validacoes especificas nas PERGUNTAS respondidas.
	 * 
	 * Ver utilizacao no caso de uso <b>esgoto entupido</b>
	 */
	protected void realizaValidacaoEspecifica(){}
	
	/**
	 *  valida se todas perguntas foram respondidas
	 */
	protected boolean isTodasPerguntasRespondidas(){
		if( this.listaPerguntasRespondidas.isEmpty() ){		
			addMessage(FacesMessage.SEVERITY_ERROR,  FacesBundleUtil.getInstance().getString("erro.reposta.autoatendimento"), null);
			getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "ERRO");
			
			return false;		
			
		}else{
			// todas as perguntas devem ser respondidas
			if( this.listaPerguntas.size() != this.listaPerguntasRespondidas.size() ) {		
				addMessage(FacesMessage.SEVERITY_ERROR,  FacesBundleUtil.getInstance().getString("erro.reposta.autoatendimento"), null);
				getHttpRequest().setAttribute(BasicNavegacaoBBean.prosseguirOutcomeParamName, "ERRO");
				
				return false;
			}
			
		}

		return true;
	}
	
	/**
	 *  percorre lista de perguntas e retorna todas as preenchidas 
	 */
	private List<PerguntaAutoAtendimento> obterPerguntasRespondidas(List<PerguntaAutoAtendimento> perguntas){
		List<PerguntaAutoAtendimento> perguntasRespondidas = new ArrayList<PerguntaAutoAtendimento>();
		for(PerguntaAutoAtendimento pergunta : perguntas){
			if(pergunta.getCodRespostaSelecionado() != null){
				perguntasRespondidas.add(pergunta);
				System.out.println("Pergunta respondida --> " + pergunta.getCdPergunta() + " - resposta" + pergunta.getCodRespostaSelecionado());
			}
		}	
		setListaPerguntasRespondidas(perguntasRespondidas);
		System.out.println("Total -> " + perguntasRespondidas.size());
		
		return perguntasRespondidas;
	}
	
	/**
	 * Carrega proxima pergunta
	 */
	protected void proximaPergunta(){
		int nrTemp = listaPerguntasRespondidas.size();
		int contador =listaPerguntasRespondidas.get(--nrTemp).getNrOrdemPergunta();
		contador++;
		perguntaAtual = null;
		for (Object element : listaPerguntas) {
			PerguntaAutoAtendimento temp = (PerguntaAutoAtendimento) element;
			if(temp.getNrOrdemPergunta().intValue() == (contador)){
				perguntaAtual = temp;
				nrSeqPergunta++;
				break;
			}
		}
	}
	
	
	/**
	 * Delete a ultima pergunta respondida
	 * e adiciona uma nova pergunta atual 
	 */
	public void perguntaAnterior(ActionEvent e){
		int nrTemp = listaPerguntasRespondidas.size();
		int contador =listaPerguntasRespondidas.get(--nrTemp).getNrOrdemPergunta();
		PerguntaAutoAtendimento respostaDelete =  null;
		for (PerguntaAutoAtendimento pergRespondida : listaPerguntasRespondidas) {
			if(pergRespondida.getNrOrdemPergunta().intValue() == (contador)){
				respostaDelete = pergRespondida;
				break;
			}
		}
		
		if(respostaDelete !=  null){
			listaPerguntasRespondidas.remove(respostaDelete);
		}
		
		for (Object element : listaPerguntas) {
			PerguntaAutoAtendimento temp = (PerguntaAutoAtendimento) element;
			if(temp.getNrOrdemPergunta().intValue() == (contador)){
				perguntaAtual = temp;
				break;
			}
		}
		
		exibirAcatamento = false;
		exibirBotaoConclusao = false;
	}
	
	/**
	 * METODO ANTIGO - Em um novo caso de uso, utilize metodo <b>buscaAcao</b>
	 * 
	 * 1 - consulta db
	 * 2 - executa acao especifica
	 * 
	 * @param perguntaAtual2 
	 * @return 
	 * 
	 */
	protected boolean carregarAcao(){
		try {
			// consulta DB ou memoria para verificar se perg/resp tem um CONJUNTO
			acao = autoAtendimentoService.findAcao(perguntaAtual.getCdPergunta(),Integer.valueOf(perguntaAtual.getCodRespostaSelecionado()) , getDadosImoveisTO().getImovel().getDsDiretoria()); 	
			
			if(acao!=null)  return Boolean.TRUE;
		} catch (BusinessException e) {
			e.printStackTrace();
			addMessage(FacesMessage.SEVERITY_ERROR,(e.getMessage()), null);
		}
		return Boolean.FALSE;
	}
	
	// navegacao
	/**
	 * Método que valida se os dados do cliente são válidos
	 */
	public String prosseguirCliente(){
		try{
			if(!validaCamposCliente(getCliente())){
				return "ERRO";	
			}else{
				DadosImoveisTO dados = getDadosImoveisTO();
				dados.setSolicitante(getCliente());
				getHttpSession(false).setAttribute(SessionUtil.DADOSIMOVELSESSION,dados);
			}
		}catch (Exception e) {
			return "ERRO";
		}
		return "PROSSEGUIR";
	}	
	
	// validacao
	/**
	 * Validações de CPF . CNPJ , Campos Obrigatórios //FIXME fazer validação de email completa e repassar esse método para bbean abstrato
	 * @param cliente 
	 * @return
	 */
	protected boolean validaCamposCliente(Cliente cliente) {
		
		if ("".equals(cliente.getNmCLiente())) {
    		FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_ERROR,  FacesBundleUtil.getInstance().getString("erro.pesquisa.cliente.nome"), null));
			return false;
		}
    	if ("".equals(cliente.getDdd1())) {
    		FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_ERROR,  FacesBundleUtil.getInstance().getString("erro.pesquisa.cliente.ddd"), null));
    		return false;
		}
    	if ("".equals(cliente.getTelefone1())) {
    		FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("erro.pesquisa.cliente.telefone"), null));
    		return false;
		}
    	if ("".equals(cliente.getCdRelacaoImovel())) {
    		FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("erro.pesquisa.cliente.cdrelacionamento"), null));
    		return false;
		}
// nao sera mais obrigatorio    	
//    	if ("".equals(cliente.getDsEmail())) {
//    		FacesContext.getCurrentInstance().addMessage(null,
//		    new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("erro.pesquisa.cliente.email"), null));
//    		return false;
//		}
    	if ("".equals(cliente.getCnpj()) && "".equals(cliente.getCpf())) {
    		FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("erro.pesquisa.cliente.cnpj"), null));
    		return false;
		}
    	
    	atualizarDadosSolicitante(cliente);   // atualiza dados do solicitante
		return true;
	}
	
	protected SolicitanteTO parseSolicitanteTO(Cliente cliente) {
		
		SolicitanteTO s =  new SolicitanteTO();
		s.setDsEmail(cliente.getDsEmail());
		s.setDsNome(cliente.getNmCLiente());
		s.setDocSolicitante( cliente.getCnpj() == null ?cliente.getCpf() : cliente.getCnpj()) ;
		s.setDsRelacao(cliente.getCodCategoria());  //FIXME gustavo passar para descriçao
		s.setDsTelefone(String.valueOf(cliente.getTelefone1()));
//		s.setEndereco(endereco)
		
		return s;
	}
	
	/**
	 * Faz chamada ao servico para obter objeto acao, veja metodo buscaAcao
	 * 
	 * @param perguntasRespondidas
	 * @return
	 * @throws Exception
	 */
	protected AcaoAutoAtendimento localizarAcao(List<PerguntaAutoAtendimento> perguntasRespondidas) throws NotFoundBusinessException, BusinessException, Exception {
		if(isNull(perguntasRespondidas)) return null;		
		String diretoria = getDadosImoveisTO().getImovel().getDsDiretoria() == null ? "T" : getDadosImoveisTO().getImovel().getDsDiretoria(); 
		AcaoAutoAtendimento acao =  acaoService.findAcao(perguntasRespondidas , diretoria);
		return acao;
	}
	
	/**
	 * Carrega dados correlacao
	 * 
	 * Lista de correlacao sao links a serem exibidos ao concluir o Auto Atendimento
	 */
	protected void carregaDadosCorrelacao(AutoAtendimentoEnum autoAtendimento){
		if(isNull(listaCorrelacao)) this.listaCorrelacao = correlacaoService.findByAtendimento(autoAtendimento.getCodigo(), false);
	}	
	
	/**
	 * Inicializa Bean 
	 * 
	 * Utilize este metodo para eliminar dados na sessao 
	 * 
	 * Migambis
	 */
	protected void initBasicAutoAtendimentoBean(){
		initBasicPesquisaBBean();
		
		cliente = new Cliente();
		listaCategorias = TipoRelacaoClienteImovel.getListSelectItem();
		listaPerguntas = new ArrayList<PerguntaAutoAtendimento>();
		listaPerguntasRespondidas =  new ArrayList<PerguntaAutoAtendimento>();
		listaCorrelacao =  new ArrayList<AgvTabCorrelacao>();
		listaSimNao = SIMNAOEnum.getListSelectItem();
		checkConfirmacao = Boolean.FALSE;
		radioConfirmacao = null;
	}
	
	public String redirectConcluirSolicitacao() {
		String cdSubMenu = getCodigoSubMenu();
		String dsSubMenu = getDescricaoSubMenu();
		String outcomeSubMenu = getOutcomeSubMenu();
		String cdAutoAtendimento = getCodigoAutoAtendimento();
		TipoMenu tipoMenu = null;

		tipoMenu = itemMenuService.pesquisaMenuItemPertence(cdSubMenu);
		
		String cdMenu = tipoMenu.getCodigo().toString();
		String dsMenu = tipoMenu.getRealName();
		String outcomeMenu = tipoMenu.getOutcome();

		preencherMenuMigalha(cdMenu, dsMenu, outcomeMenu);
		
		preencherSubMenuMigalha(cdSubMenu, dsSubMenu, outcomeSubMenu, cdAutoAtendimento);

		// Executa tarefas relacionadas a auditoria da agencia
		auditoriaBBean.auditarAcesso(cdSubMenu, cdAutoAtendimento);

		return outcomeSubMenu;
	}
	
	// get / set
	public boolean isExibirAcatamento() {
		return exibirAcatamento;
	}

	public void setExibirAcatamento(boolean exibirAcatamento) {
		this.exibirAcatamento = exibirAcatamento;
	}

	public List<PerguntaAutoAtendimento> getListaPerguntas() {
		return listaPerguntas;
	}

	public void setListaPerguntas(List<PerguntaAutoAtendimento> listaPerguntas) {
		this.listaPerguntas = listaPerguntas;
	}

	public List<PerguntaAutoAtendimento> getListaPerguntasRespondidas() {
		return listaPerguntasRespondidas;
	}

	public void setListaPerguntasRespondidas(
			List<PerguntaAutoAtendimento> listaPerguntasRespondidas) {
		this.listaPerguntasRespondidas = listaPerguntasRespondidas;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<SelectItem> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<SelectItem> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public PerguntaAutoAtendimento getPerguntaAtual() {
		return perguntaAtual;
	}

	public void setPerguntaAtual(PerguntaAutoAtendimento perguntaAtual) {
		this.perguntaAtual = perguntaAtual;
	}

	public int getNrSeqPergunta() {
		return nrSeqPergunta;
	}

	public void setNrSeqPergunta(int nrSeqPergunta) {
		this.nrSeqPergunta = nrSeqPergunta;
	}

	public boolean isExibirBotaoConclusao() {
		return exibirBotaoConclusao;
	}

	public void setExibirBotaoConclusao(boolean exibirBotaoConclusao) {
		this.exibirBotaoConclusao = exibirBotaoConclusao;
	}

	public AgenciaTO getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaTO agencia) {
		this.agencia = agencia;
	}


	public List<SelectItem> getListaSimNao() {
		return listaSimNao;
	}


	public void setListaSimNao(List<SelectItem> listaSimNao) {
		this.listaSimNao = listaSimNao;
	}

	public boolean getCheckConfirmacao() {
		return checkConfirmacao;
	}

	public void setCheckConfirmacao(boolean checkConfirmacao) {
		this.checkConfirmacao = checkConfirmacao;
	}
	
	public String getRadioConfirmacao() {
		return radioConfirmacao;
	}

	public void setRadioConfirmacao(String radioConfirmacao) {
		this.radioConfirmacao = radioConfirmacao;
	}

	public List<AgvTabCorrelacao> getListaCorrelacao() {
		return listaCorrelacao;
	}

	public void setListaCorrelacao(List<AgvTabCorrelacao> listaCorrelacao) {
		this.listaCorrelacao = listaCorrelacao;
	}
	
	public String getNumeroSolicitacao() {
		return numeroSolicitacao;
	}

	public void setNumeroSolicitacao(String numeroSolicitacao) {
		this.numeroSolicitacao = numeroSolicitacao;
	}
	
	public OrcamentoOferecidoTO getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(OrcamentoOferecidoTO orcamento) {
		this.orcamento = orcamento;
	}

	public CorrelacaoService getCorrelacaoService() {
		return correlacaoService;
	}

	public void setCorrelacaoService(CorrelacaoService correlacaoService) {
		this.correlacaoService = correlacaoService;
	}
	
	public String getQtParcelas() {
		return qtParcelas;
	}

	public void setQtParcelas(String qtParcelas) {
		this.qtParcelas = qtParcelas;
	}
	
	public String getValorParcela() {
		if(isNull(getOrcamento())) return "";
				
		BigDecimal vlrTotal = getOrcamento().getValorTotal();
		
		if(isNull(vlrTotal) || ("".equals(vlrTotal)) || ("".equals(qtParcelas))) return "";
		
		if(qtParcelas.equals("1")){
			return parseMoney(getOrcamento().getValorTotal().toString());
		}else{
			return parseMoney(String.valueOf((getOrcamento().getValorTotal().doubleValue()) / toInt(qtParcelas)));
		}
	}


	public void setValorParcela(String valorParcela) {
		this.valorParcela = valorParcela;
	}

	public String getMsgErroAcatamento() {
		return msgErroAcatamento;
	}

	public void setMsgErroAcatamento(String msgErroAcatamento) {
		this.msgErroAcatamento = msgErroAcatamento;
	}

	public String getMsgErroAcatamentoTraduzida() {
		// TODO - traduzir mensagem de erro
		return msgErroAcatamentoTraduzida;
	}

	public boolean isExibirTextoParcial() {

		if( getListaPerguntasRespondidas() !=null && listaPerguntasRespondidas.isEmpty() )			
			this.exibirTextoParcial = true;
		else
			this.exibirTextoParcial = false;
		
		return exibirTextoParcial;

	}

	public void setExibirTextoParcial(boolean exibirTextoParcial) {
		this.exibirTextoParcial = exibirTextoParcial;
	}

	public boolean isCheckAprovarOrcamento() {
		return checkAprovarOrcamento;
	}

	public void setCheckAprovarOrcamento(boolean checkAprovarOrcamento) {
		this.checkAprovarOrcamento = checkAprovarOrcamento;
	}


	
	
	
}
