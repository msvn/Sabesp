package com.prime.app.agvirtual.web.jsf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.prime.app.agvirtual.entity.AgvTabItemMenuGrupo;
import com.prime.app.agvirtual.entity.CanalAtendimento;
import com.prime.app.agvirtual.enums.TipoMenu;
import com.prime.app.agvirtual.service.ItemMenuService;
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.app.agvirtual.web.jsf.util.SessionUtil;
import static com.prime.infra.util.WrapperUtils.*;
import com.prime.infra.web.jsf.BasicBBean;

/**
 * BackBean responsável pelo direcionamento dos .
 * @author felipepm
 */
public class MenuNavegacaoBBean extends BasicPesquisaBBean implements Serializable {
	private static final long serialVersionUID = -2207138676184437855L;
	private static final Logger logger = LoggerFactory.getLogger(MenuNavegacaoBBean.class);

	@Autowired protected ItemMenuService itemMenuService;
	@Autowired protected AuditoriaAcessoBBean auditoriaBBean;
	
	protected List<AgvTabItemMenuGrupo> listaSubMenu;

	protected List<CanalAtendimento> listaCanaisAtendimento;
	
	protected HashMap<String, String> outcomesIdentificarImovel = new HashMap<String,String>();
	
	public MenuNavegacaoBBean(){
		carregarOutcomes(outcomesIdentificarImovel);
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
		return obterParametro(PARAMETRO_CODIGO_MENU);
	}
	
	protected String getDescricaoMenu(){
		return obterParametro(PARAMETRO_DESCRICAO_MENU);
	}

	protected String getOutcomeMenu(){
		return obterParametro(PARAMETRO_OUTCOME_MENU);
	}
	
	protected String getCodigoSubMenu(){
		return obterParametro(PARAMETRO_CODIGO_SUB_MENU);
	}
	
	protected String getDescricaoSubMenu(){
		return obterParametro(PARAMETRO_DESCRICAO_SUB_MENU);
	}

	/**
	 * Obtem <b>outcome</b> para Sub Menu consultado. 
	 */
	protected String getOutcomeSubMenu(){
		String outcome = obterParametro(PARAMETRO_OUTCOME_SUB_MENU);  	 // obtem outcome padrao				
		return obterOutcomeIdenficacaoImovel(outcome);  				 // aplica regra identificar imovel
	}
	
	/**
	 * Verifica se ha necessidade de aplicar regra de identificacao do Imovel.
	 *  
	 * <b>Redireciona para pagina de identificacao caso Imovel nao tenha sido identificado.</b>
	 */
	protected String obterOutcomeIdenficacaoImovel(String outcomePadrao){
		String outcomeIdentificacao = outcomesIdentificarImovel.get(outcomePadrao);
		
		if(isNull(outcomeIdentificacao)){
			logger.info("Nao ha necessidade de identificacao de imovel para outcome=" + outcomePadrao);
		}
		
		// imovel ja identificado ou nao ha necessidade de identificacao		
		if(isImovelIdentificado() || isNull(outcomeIdentificacao)){
			logger.info("Imovel ja identificado retornando outcome=" + outcomePadrao);
			return outcomePadrao;//outcome;
		}
		
		logger.info("Imovel [NAO] identificado retornando outcome identificacao=" + outcomePadrao);
		return outcomeIdentificacao;
	}
	
	/**
	 * Identificacao do Imovel.
	 * 
	 * Carrega outcomes para casos de uso que devem identificar imovel NA PRIMEIRA TELA 
	 */
	private void carregarOutcomes(HashMap<String, String> outcomesIdentificarImovel){
		outcomesIdentificarImovel.put("EMERGENCIA_ESTOU_SEM_AGUA", "EMERGENCIA_ESTOU_SEM_AGUA_IDENTIFICAR");
		outcomesIdentificarImovel.put("LIGACOES_MUDANCA_LIGACAO_AGUA", "LIGACOES_MUDANCA_LIGACAO_AGUA_IDENTIFICAR");
		outcomesIdentificarImovel.put("LIGACOES_MUDANCA_LIGACAO_ESGOTO", "LIGACOES_MUDANCA_LIGACAO_ESGOTO_IDENTIFICACAO");
		outcomesIdentificarImovel.put("SUA_CONTA_SEGUNDA_VIA", "SUA_CONTA_SEGUNDA_VIA_IDENTIFICAR");
		outcomesIdentificarImovel.put("SUA_CONTA_CONSULTA_DADOS_LEITURA", "SUA_CONTA_CONSULTA_DADOS_LEITURA_IDENTIFICAR");       // cronograma de leitura
		outcomesIdentificarImovel.put("SUA_CONTA_CONSULTA_HISTORICO_CONTAS", "SUA_CONTA_CONSULTA_HISTORICO_CONTAS_IDENTIFICAR"); // cronograma de leitura
	}
	
	/**
	 * 
	 */
	private String obtemOutcomeIdentificacaoImovel(){
		return "";
	}
	
	protected String getCodigoAutoAtendimento(){
		if (isNotNull(getRequestParameterMap(PARAMETRO_CODIGO_AUTO_ATENDIMENTO)) && !EMPTY.equalsIgnoreCase(getRequestParameterMap(PARAMETRO_CODIGO_AUTO_ATENDIMENTO))) {
			return getRequestParameterMap(PARAMETRO_CODIGO_AUTO_ATENDIMENTO);
		} else {
			return SessionUtil.obterAtributo(PARAMETRO_CODIGO_AUTO_ATENDIMENTO, getHttpSession(Boolean.FALSE), new String());
		}
	}

	/**
	 * Obtem parametro do request, caso esteja presente, ou da Sessao.
	 */
	private String obterParametro(String paramName){
		if (isNotNull(getRequestParameterMap(paramName)) && !EMPTY.equalsIgnoreCase(getRequestParameterMap(paramName))) {
			return getRequestParameterMap(paramName);
		} else {
			return SessionUtil.obterAtributo(paramName, getHttpSession(Boolean.FALSE), new String());
		}
	}
	
	public Integer getSuaConta() {
		return TipoMenu.SUA_CONTA.getCodigo();
	}
	
	public Integer getLigacoesAguaEsgoto() {
		return TipoMenu.LIGACOES_AGUA_ESGOTO.getCodigo();
	}
	
	public Integer getConsertos() {
		return TipoMenu.CONSERTOS.getCodigo();
	}
	
	public Integer getEmergencias() {
		return TipoMenu.EMERGENCIAS.getCodigo();
	}
	
	public Integer getCorporativo() {
		return TipoMenu.CORPORATIVO.getCodigo();
	}
	
	public Integer getDicasInformacoes() {
		return TipoMenu.DICAS_INFORMACOES.getCodigo();
	}
	
	public Integer getTarifas() {
		return TipoMenu.TARIFAS.getCodigo();
	}
	
	public List<AgvTabItemMenuGrupo> getListaSubMenu() {
		return listaSubMenu;
	}

	public void setListaSubMenu(List<AgvTabItemMenuGrupo> listaSubMenu) {
		this.listaSubMenu = listaSubMenu;
	}

	
}
