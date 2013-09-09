package com.prime.app.agvirtual.web.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.Endereco;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.service.AtendimentoService;
import com.prime.app.agvirtual.service.ContaService;
import com.prime.app.agvirtual.service.MunicipioService;
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.web.jsf.util.SessionUtil;
import com.prime.infra.BusinessException;
import static com.prime.infra.util.WrapperUtils.*;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

public class BasicPesquisaBBean extends BasicNavegacaoBBean {	
	private static final Logger logger = LoggerFactory.getLogger(BasicAutoAtendimentoBBean.class);	

	@Autowired
	protected ContaService contaService;	            // efetuaPesquisa()
	
	@Autowired
	private AtendimentoService atendimentoService;      // efetuaPesquisa()
	
	@Autowired
	private MunicipioService municipioService;   		// getComboMunicipio()
	
	@Autowired
	protected AuditoriaAcessoBBean auditoriaBBean; 		// auditoria
	
	public static final String LINHAS_PAGINACAO_05 = "5";
	public static final String LINHAS_PAGINACAO_10 = "10";
	
	protected boolean modalRendered = false;              // Atributo de controle do modal. Usado na tela de pesquisa para exibicao do popup XPTO
	private String radioSelecionada = "PROSSEGUIR_RGI";   // radio selecionada
	private String nomeEnderecoView;					  // nome do endereco
	private String idMunicipioSelecionadoView;			  // municipio selecionado
	private String nomeMunicipioSelecionadoView;		  // municipio selecionado
	private Endereco enderecoSelecionadoView;			  // Endereço Selecionado
	private Imovel imovelSelecionadoView; 				  // Imovel Selecionado
	private String nomeBairroView;
	private String numeroEnderecoView;
	private boolean userThrowException;

	private DadosImoveisTO dadosImoveisTO = new DadosImoveisTO();		// imovel sendo pesquisado
	private Imovel imovel = new Imovel();
	private Imovel imovelResultado = new Imovel();
	private boolean rgiinvalido;                          // controla exibicao, ver tela pesquisargi E erro
	
	String ultimoRgi = "";	
	
	/**
	 * Metodo utilizado para limpar dados na Sessao
	 */
	protected void initBasicPesquisaBBean(){
		imovel = new Imovel();
		nomeEnderecoView = "";
	}

	/**
	 *  Inicializa Bean.
	 * 
	 *  <b>Reimplementar</b> este metodo ao extender esta classe <b>caso necessite inicializar dados</b>
	 */
	public void initialize(){}
	
	/**
	 * Chamada em tela para <b>inicializacao do BBean</b>.
	 * 
	 * Metodo chamado apos identificacao do imovel pela TELA ou pelo metodo confirmarRgi 
	 * 
	 * <b>Reimplementar</b> este metodo ao extender esta classe <b>caso necessite inicializar dados</b>
	 */
	public void inicializar(ActionEvent e){
		initialize();
	}
	
	/**
	 * pesquisarRGI
	 */
	public void pesquisarRGI(ActionEvent ex){
		logger.info(getJSessionId()+"==>Inicada pesquisa de RGI: " + imovel.getDsRgi());
		
		if(!ultimoRgi.equals("") && ultimoRgi.equals(imovel.getDsRgi()) ){
			addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("erro.pesquisa.rgi.repetido.invalido"), null);
		}
		else
		{
			if(imovel.getDsRgi() == null || imovel.getDsRgi().length() == 0){
				addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("erro.pesquisa.rgi.vazio"), null);
			}
			else
			{
				if(dadosImoveisTO.getImovel().getDsRgi() == null ) {
					efetuaPesquisa();
				}else if(!(dadosImoveisTO.getImovel().getDsRgi().equals(imovel.getDsRgi())))
				{
					efetuaPesquisa();	
				}
			}
		}
		
//		colocarDadosDoImovelNaSessao(dadosImoveisTO);
//		gravarRgiAutoAtendimento(dadosImoveisTO.getImovel());
	}

	/**
	 * Pesquisa de RGI
	 */
	private void efetuaPesquisa() {
		try{
			
			this.dadosImoveisTO = this.contaService.identificarImovel(imovel,auditoriaBBean.getCodigoItemMenu(), getJSessionId());
			
			if(this.dadosImoveisTO.getImovel() != null){
				logger.info(getJSessionId()+"==>RGI:" + this.dadosImoveisTO.getImovel().getDsRgi() + " foi encontrado com Sucesso");
				
				this.rgiinvalido = Boolean.FALSE;
				if(this.dadosImoveisTO.getImovel().getPermissionaria()){
					logger.error(getJSessionId()+"==>RGI:" + this.dadosImoveisTO.getImovel().getDsRgi() + " é permissionária");
					addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("erro.pesquisa.rgi.permissionaria"), null);
					ultimoRgi = imovel.getDsRgi();
				}
			}else if(imovelResultado == null){
				 logger.error(getJSessionId()+"==>RGI Não foi encontrado");
				 addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("erro.pesquisa.rgi.invalido"), null);
				 ultimoRgi = imovel.getDsRgi();
			}		
		}catch (BusinessException be) {
			this.rgiinvalido = Boolean.TRUE;
			
			ultimoRgi = imovel.getDsRgi();
//			dadosImoveisTO =  new DadosImoveisTO();
		}catch (Exception e) {
			logger.error(getJSessionId()+"==>Erro Fatal ao pesquisa RGI");
			addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("erro.pesquisa.csi"), null);
			dadosImoveisTO =  new DadosImoveisTO();
		}
	}	
	
	/**
	 * Pesquisa Rgi digitado pelo usuário , verifica se este existe , se é permissionária
	 * Verifica a existencia de um atendimento aberto , caso não exista cria um novo.
	 * @param actionEvent
	 */
	public void definirPesquisaRGIEndereco(ActionEvent actionEvent){
		logger.info(getJSessionId()+"==>Inicada pesquisa de RGI:"+imovel.getDsRgi());
		
		if( this.radioSelecionada != null && !this.radioSelecionada.equals("") ) {
			
			// pesquisa por RGI
			if( this.radioSelecionada.equals("PROSSEGUIR_RGI") ){
				
					if(!ultimoRgi.equals("") && ultimoRgi.equals(imovel.getDsRgi()) ){
						addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("erro.pesquisa.rgi.repetido.invalido"), null);
					}else{
						if(imovel.getDsRgi() == null || imovel.getDsRgi().length() == 0){
							addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("erro.pesquisa.rgi.vazio"), null);
						}
						else
						{
							if(dadosImoveisTO.getImovel().getDsRgi() == null ) {
								efetuaPesquisa();
							// pesquisa somente se RGI for diferente do rgi logado
							}else if(!(dadosImoveisTO.getImovel().getDsRgi().equals(imovel.getDsRgi()))){
								efetuaPesquisa();	
							}
						}
					}		
			// Pesquisa por endereco				
			}else{
				
				if( this.radioSelecionada.equals("PROSSEGUIR_ENDERECO") ){				
						
						if( !validarCampoEndereco() ){
							setProsseguirOutcome(this.radioSelecionada);
							pesquisarEndereco();
						}
//						this.radioSelecionada = null;
						
				}
			}
			
//			gravarRgiAutoAtendimento(dadosImoveisTO.getImovel());
//			colocarDadosDoImovelNaSessao(dadosImoveisTO);   // coloca dados do imovel na sessao
		}	
	}	
	/**
	 * Pesquisa enderecos
	 * 
	 * @param actionEvent
	 */
	public void pesquisarEndereco(){
		Endereco enderecoPesquisa = new Endereco();
		Imovel imovelPesquisa =new Imovel();
		
		// busca enderecos
		imovelPesquisa.setCdMunicipio(this.idMunicipioSelecionadoView);
		enderecoPesquisa.setImovel(imovelPesquisa);
		enderecoPesquisa.setNrEndereco(nomeEnderecoView);
		this.dadosImoveisTO.setListaEndereco( this.contaService.localizarEnderecos(enderecoPesquisa) );
	}	
	
	
	// validacao
	private boolean validarCampoEndereco(){
		boolean status = false;
		
		if( this.nomeEnderecoView == null || this.nomeEnderecoView.equals("") ){
			addMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("erro.debitoautomatico.endereco"), null);
			 status = true;	
		}
		
		return status;
		
	}	
	
	// carrega dados
	public List<SelectItem> getComboMunicipio(){
		
		ArrayList<SelectItem> lista = null;	
		
		if( this.dadosImoveisTO.getListaMunicipio() == null  ) {
			
			this.dadosImoveisTO.setListaMunicipio( this.municipioService.findAll()  );
			
			ArrayList<SelectItem> listaRetorno = new ArrayList<SelectItem>();
		
			for(MunicipioTO municipioLocal : this.dadosImoveisTO.getListaMunicipio()){
				SelectItem item = new SelectItem( String.valueOf(municipioLocal.getIdMun()), municipioLocal.getNome() );
				listaRetorno.add(item);
			}
			
			lista = listaRetorno;
			
		}else {
			
			ArrayList<SelectItem> listaRetorno = new ArrayList<SelectItem>();
			
			for(MunicipioTO municipioLocal : this.dadosImoveisTO.getListaMunicipio()){
				SelectItem item = new SelectItem( String.valueOf(municipioLocal.getIdMun()), municipioLocal.getNome() );
				listaRetorno.add(item);
			}
			
			lista = listaRetorno;
		}
		
		return lista;
	}	
	
	public void selecionaRegistroEndereco(RowSelectorEvent event){
		
		Endereco endereco = this.dadosImoveisTO.getListaEndereco().get(event.getRow());
		this.enderecoSelecionadoView = endereco;
				
	}
	
	public void carregarNumerosEndereco(ActionEvent event){
		this.dadosImoveisTO.setListaNumerosEndereco( contaService.localizarNumerosEndereco(this.enderecoSelecionadoView) );
		
		this.nomeEnderecoView = this.enderecoSelecionadoView.getDsEndereco();
		this.nomeBairroView = this.enderecoSelecionadoView.getNmBairro();
		
		gravarRgiAutoAtendimento(dadosImoveisTO.getImovel());
		colocarDadosDoImovelNaSessao(dadosImoveisTO);
	}
	
	/**
	 * Coloca dados do imovel na sessao
	 */
	private boolean colocarDadosDoImovelNaSessao(DadosImoveisTO dadosImoveisTO) {
		return SessionUtil.adicionarAtributo(SessionUtil.DADOSIMOVELSESSION, dadosImoveisTO, getHttpSession(Boolean.FALSE));
	}	
	
	/**
	 * Obtem dados do imovel da sessao 
	 * 
	 * Chamado em tela
	 */
	public DadosImoveisTO obterDadosImovelIdentificado() {
		return SessionUtil.obterAtributo(SessionUtil.DADOSIMOVELSESSION, getHttpSession(Boolean.FALSE), new DadosImoveisTO());
	}		
	
	/**
	 * Verifica se imovel ja foi identificado
	 */
	public Boolean isImovelIdentificado(){
		return (isNotNull(obterDadosImovelIdentificado()))? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * Atualiza dados do Solicitante
	 * 
	 * Utilizado por todos os Auto Atendimentos
	 * @param cliente
	 */
	protected void atualizarDadosSolicitante(Cliente cliente){
		DadosImoveisTO dados = getDadosImoveisTO();
		dados.setSolicitante(cliente);
		SessionUtil.adicionarAtributo(SessionUtil.DADOSIMOVELSESSION, dados, getHttpSession(Boolean.FALSE));		
	}
	
	/**
	 * Registra RGI do Auto Atendimento
	 * 
	 * Identificacao do imovel feita em N pontos distintos.
	 * 
	 * Sobrescreva este metodo em todos os Auto Atendimentos
	 */
	protected void gravarRgiAutoAtendimento(Imovel imovel){}
	
	/**
	 * Captura acao do usuario de confirmacao do RGI.
	 * Identifica imovel
	 */
	public void confirmarRgi(ActionEvent e){
		colocarDadosDoImovelNaSessao(dadosImoveisTO);
		gravarRgiAutoAtendimento(dadosImoveisTO.getImovel());
		inicializar(e);
	}

	/**
	 * Método responsável por armazenar os dados do 2º Nível de navegação.
	 * Exemplo: Home > Sua Conta
	 * 			Os dados de Sua Conta serão armazenados através deste método.
	 */
	public void preencherMenuMigalha(String codigo, String descricao, String outcome){
		SessionUtil.adicionarAtributo(PARAMETRO_CODIGO_MENU, codigo, getHttpSession(Boolean.FALSE));
		SessionUtil.adicionarAtributo(PARAMETRO_DESCRICAO_MENU, descricao, getHttpSession(Boolean.FALSE));
		SessionUtil.adicionarAtributo(PARAMETRO_OUTCOME_MENU, outcome, getHttpSession(Boolean.FALSE));
	}

	/**
	 * Método responsável por armazenar os dados do 3º Nível de navegação.
	 * Exemplo: Home > Sua Conta > 2ª Via
	 * 			Os dados de 2ª Via serão armazenados através deste método.
	 */
	public void preencherSubMenuMigalha(String codigo, String descricao, String outcome, String cdAutoAtendimento){
		SessionUtil.adicionarAtributo(PARAMETRO_CODIGO_SUB_MENU, codigo, getHttpSession(Boolean.FALSE));
		SessionUtil.adicionarAtributo(PARAMETRO_OUTCOME_SUB_MENU, outcome, getHttpSession(Boolean.FALSE));
		SessionUtil.adicionarAtributo(PARAMETRO_DESCRICAO_SUB_MENU, descricao, getHttpSession(Boolean.FALSE));
		SessionUtil.adicionarAtributo(PARAMETRO_CODIGO_AUTO_ATENDIMENTO, cdAutoAtendimento, getHttpSession(Boolean.FALSE));
	}
	
	protected String getCodigoMenu(){
		if (isNotNull(getRequestParameterMap(PARAMETRO_CODIGO_MENU)) && !EMPTY.equalsIgnoreCase(getRequestParameterMap(PARAMETRO_CODIGO_MENU))) {
			return getRequestParameterMap(PARAMETRO_CODIGO_MENU);
		} else {
			return SessionUtil.obterAtributo(PARAMETRO_CODIGO_MENU, getHttpSession(Boolean.FALSE), new String());
		}
	}
	
	protected String getDescricaoMenu(){
		if (isNotNull(getRequestParameterMap(PARAMETRO_DESCRICAO_MENU)) && !EMPTY.equalsIgnoreCase(getRequestParameterMap(PARAMETRO_DESCRICAO_MENU))) {
			return getRequestParameterMap(PARAMETRO_DESCRICAO_MENU);
		} else {
			return SessionUtil.obterAtributo(PARAMETRO_DESCRICAO_MENU, getHttpSession(Boolean.FALSE), new String());
		}
	}

	protected String getOutcomeMenu(){
		if (isNotNull(getRequestParameterMap(PARAMETRO_OUTCOME_MENU)) && !EMPTY.equalsIgnoreCase(getRequestParameterMap(PARAMETRO_OUTCOME_MENU))) {
			return getRequestParameterMap(PARAMETRO_OUTCOME_MENU);
		} else {
			return SessionUtil.obterAtributo(PARAMETRO_OUTCOME_MENU, getHttpSession(Boolean.FALSE), new String());
		}
	}
	
	protected String getCodigoSubMenu(){
		if (isNotNull(getRequestParameterMap(PARAMETRO_CODIGO_SUB_MENU)) && !EMPTY.equalsIgnoreCase(getRequestParameterMap(PARAMETRO_CODIGO_SUB_MENU))) {
			return getRequestParameterMap(PARAMETRO_CODIGO_SUB_MENU);
		} else {
			return SessionUtil.obterAtributo(PARAMETRO_CODIGO_SUB_MENU, getHttpSession(Boolean.FALSE), new String());
		}
	}
	
	protected String getDescricaoSubMenu(){
		if (isNotNull(getRequestParameterMap(PARAMETRO_DESCRICAO_SUB_MENU)) && !EMPTY.equalsIgnoreCase(getRequestParameterMap(PARAMETRO_DESCRICAO_SUB_MENU))) {
			return getRequestParameterMap(PARAMETRO_DESCRICAO_SUB_MENU);
		} else {
			return SessionUtil.obterAtributo(PARAMETRO_DESCRICAO_SUB_MENU, getHttpSession(Boolean.FALSE), new String());
		}
	}

	protected String getOutcomeSubMenu(){
		if (isNotNull(getRequestParameterMap(PARAMETRO_OUTCOME_SUB_MENU)) && !EMPTY.equalsIgnoreCase(getRequestParameterMap(PARAMETRO_OUTCOME_SUB_MENU))) {
			return getRequestParameterMap(PARAMETRO_OUTCOME_SUB_MENU);
		} else {
			return SessionUtil.obterAtributo(PARAMETRO_OUTCOME_SUB_MENU, getHttpSession(Boolean.FALSE), new String());
		}
	}
	
	protected String getCodigoAutoAtendimento(){
		if (isNotNull(getRequestParameterMap(PARAMETRO_CODIGO_AUTO_ATENDIMENTO)) && !EMPTY.equalsIgnoreCase(getRequestParameterMap(PARAMETRO_CODIGO_AUTO_ATENDIMENTO))) {
			return getRequestParameterMap(PARAMETRO_CODIGO_AUTO_ATENDIMENTO);
		} else {
			return SessionUtil.obterAtributo(PARAMETRO_CODIGO_AUTO_ATENDIMENTO, getHttpSession(Boolean.FALSE), new String());
		}
	}
	
	// get / set	
	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public Imovel getImovelResultado() {
		return imovelResultado;
	}

	public void setImovelResultado(Imovel imovelResultado) {
		this.imovelResultado = imovelResultado;
	}

	public String getUltimoRgi() {
		return ultimoRgi;
	}

	public void setUltimoRgi(String ultimoRgi) {
		this.ultimoRgi = ultimoRgi;
	}

	public DadosImoveisTO getDadosImoveisTO() {
		if((isNull(dadosImoveisTO) || isNull(dadosImoveisTO.getImovel().getDsRgi()) ) 
				&& isImovelIdentificado() ){
			this.dadosImoveisTO = obterDadosImovelIdentificado();	// obtem dados do Imovel da sessao
		}
		return dadosImoveisTO;
	}

	public void setDadosImoveisTO(DadosImoveisTO dadosImoveisTO) {
		this.dadosImoveisTO = dadosImoveisTO;
	}

	public boolean isModalRendered() {
		return modalRendered;
	}

	public void setModalRendered(boolean modalRendered) {
		this.modalRendered = modalRendered;
	}
	
	public void atualizarModal(ActionEvent e) {
		this.modalRendered = !modalRendered;
	}

	public String getRadioSelecionada() {
		return radioSelecionada;
	}

	public void setRadioSelecionada(String radioSelecionada) {
		this.radioSelecionada = radioSelecionada;
	}

	public String getNomeEnderecoView() {
		return nomeEnderecoView;
	}

	public void setNomeEnderecoView(String nomeEnderecoView) {
		this.nomeEnderecoView = nomeEnderecoView;
	}

	public String getIdMunicipioSelecionadoView() {
		return idMunicipioSelecionadoView;
	}

	public void setIdMunicipioSelecionadoView(String idMunicipioSelecionadoView) {
		this.idMunicipioSelecionadoView = idMunicipioSelecionadoView;
	}

	public String getNomeMunicipioSelecionadoView() {
		return nomeMunicipioSelecionadoView;
	}

	public void setNomeMunicipioSelecionadoView(String nomeMunicipioSelecionadoView) {
		this.nomeMunicipioSelecionadoView = nomeMunicipioSelecionadoView;
	}

	public Endereco getEnderecoSelecionadoView() {
		return enderecoSelecionadoView;
	}

	public void setEnderecoSelecionadoView(Endereco enderecoSelecionadoView) {
		this.enderecoSelecionadoView = enderecoSelecionadoView;
	}

	public Imovel getImovelSelecionadoView() {
		return imovelSelecionadoView;
	}

	public void setImovelSelecionadoView(Imovel imovelSelecionadoView) {
		this.imovelSelecionadoView = imovelSelecionadoView;
	}

	public String getNomeBairroView() {
		return nomeBairroView;
	}

	public void setNomeBairroView(String nomeBairroView) {
		this.nomeBairroView = nomeBairroView;
	}

	public String getNumeroEnderecoView() {
		return numeroEnderecoView;
	}

	public void setNumeroEnderecoView(String numeroEnderecoView) {
		this.numeroEnderecoView = numeroEnderecoView;
	}

	public boolean isUserThrowException() {
		return userThrowException;
	}

	public void setUserThrowException(boolean userThrowException) {
		this.userThrowException = userThrowException;
	}

	public boolean isRgiinvalido() {
		return rgiinvalido;
	}

	public void setRgiinvalido(boolean rgiinvalido) {
		this.rgiinvalido = rgiinvalido;
	}
	
	public String getJSessionId(){
		return this.dadosImoveisTO.getJSessionId(getFacesContext());
	}
}
