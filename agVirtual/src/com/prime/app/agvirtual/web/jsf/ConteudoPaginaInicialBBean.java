package com.prime.app.agvirtual.web.jsf;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.AgvTabItemMenuAcesso;
import com.prime.app.agvirtual.entity.AgvTabItemMenuGrupo;
import com.prime.app.agvirtual.enums.TipoMenu;
import com.prime.app.agvirtual.service.ItemMenuService;
import com.prime.app.agvirtual.service.ItemPaginaInicialService;
import com.prime.app.agvirtual.service.NoticiaService;
import com.prime.app.agvirtual.service.SecaoPaginaInicialService;
import com.prime.app.agvirtual.to.ItemPaginaInicialTO;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.to.NoticiaTO;
import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;
import com.prime.infra.util.WrapperUtils;

/**
 * BackBean com controles da funcinalidade Conteúdo Página Inicial.
 * @author felipepm
 */
@Component
@Scope(value="session")
public class ConteudoPaginaInicialBBean extends MenuNavegacaoBBean implements Serializable, InitializingBean {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -4200478066589336354L;

	/**
	 * Log.
	 */
	private static final Logger agvlogger = LoggerFactory.getLogger(ConteudoPaginaInicialBBean.class);
	
	@Autowired
	private ItemMenuService itemMenuService;
	
	@Autowired
    private ItemPaginaInicialService itemService;
	
	@Autowired
    private SecaoPaginaInicialService secaoService;
	
	@Autowired
    private NoticiaService noticiaService;
	
	
	private List<AgvTabItemMenuAcesso> listaComboBoxItemMenu =  new ArrayList<AgvTabItemMenuAcesso>();
	
	private List<SelectItem> listaComboBoxItemMenuSelectItem = new ArrayList<SelectItem>();
	
	private String idItemMenuSelected;			  // Item Menu selecionado
	
	/**
	 * Armazena os dados da Seção Principal.
	 */
	private ItemPaginaInicialTO secaoPrincipal = new ItemPaginaInicialTO();

	/**
	 * Armazena os dados da Seção Secondária 1.
	 */
	private ItemPaginaInicialTO secaoSecundariaOne = new ItemPaginaInicialTO();
	
	/**
	 * Armazena os dados da Seção Secondária 2.
	 */
	private ItemPaginaInicialTO secaoSecundariaTwo = new ItemPaginaInicialTO();
	
	/**
	 * Armazena os dados da Barra Horizontal.
	 */
	private ItemPaginaInicialTO barraHorizontal = new ItemPaginaInicialTO();
	
	/**
	 * Armazena os dados da Seção Inferior Direita.
	 */
	private ItemPaginaInicialTO secaoInferiorDireita = new ItemPaginaInicialTO();
	
	/**
	 * Armazena os dados da combo da Seção Principal.
	 */
	private List<ItemPaginaInicialTO> listaSecaoPrincipal = new ArrayList<ItemPaginaInicialTO>();
	
	/**
	 * Armazena os dados da combo da Seção Secundária 1.
	 */
	private List<ItemPaginaInicialTO> listaSecaoSecundariaOne = new ArrayList<ItemPaginaInicialTO>();
	
	/**
	 * Armazena os dados da combo da Seção Secundária 2.
	 */
	private List<ItemPaginaInicialTO> listaSecaoSecundariaTwo = new ArrayList<ItemPaginaInicialTO>();
	
	/**
	 * Armazena os dados da combo da Seção Barra Horizontal.
	 */
	private List<ItemPaginaInicialTO> listaBarraHorizontal = new ArrayList<ItemPaginaInicialTO>();
	
	/**
	 * Armazena os dados da combo da Seção Inferior Direita.
	 */
	private List<ItemPaginaInicialTO> listaSecaoInferiorDireita = new ArrayList<ItemPaginaInicialTO>();
	
	private List<NoticiaTO> listaNoticia = new ArrayList<NoticiaTO>();
	
	private String secaoPrincipalDescricao;
	
	private String secaoSecundariaOneDescricao;
	
	private String secaoSecundariaTwoDescricao;
	
	private String pathImages;
	
	/**
	 * Constante para representar a Lista de Notícicas
	 */
	public static final String LISTA_NOTICIA = "listaNoticia";
	
	public static final String ITEM_PAGINA_INICIAL = "itemPaginaInicial";
	
	public ConteudoPaginaInicialBBean() {
		carregaJSessionId();
	}

	/**
	 * 
	 */
	private void carregaJSessionId() {
		try{
			HttpSession session = ((HttpSession) getFacesContext().getExternalContext().getSession(Boolean.FALSE));
			System.getProperty("jSessionId", session.getId());
			getDadosImoveisTO().setJSessionId(session.getId());
		}catch(Exception e){
			agvlogger.error("Erro ao obter ID da sessao!", e);			
		}
	}

	public void afterPropertiesSet() throws Exception {
		carregar();	
	}
	
	public String carregarItemMenu(){
		AgvTabItemMenuAcesso tempItemMenu = null; 
		TipoMenu tipoMenu  = null;
		String outcomeSubMenu =  null;
		if(idItemMenuSelected.equals("0")){
			return null;
		}
		for (Iterator<AgvTabItemMenuAcesso> iterator = listaComboBoxItemMenu.iterator(); iterator.hasNext();) {
			AgvTabItemMenuAcesso element = (AgvTabItemMenuAcesso) iterator.next();
			if(idItemMenuSelected.equals(element.getCdItemMenu().toString())){
				tempItemMenu = element;
				break;
			}
		}
		
		if(tempItemMenu != null){
		tipoMenu = itemMenuService.pesquisaMenuItemPertence(idItemMenuSelected);
		
		String cdSubMenu = tempItemMenu.getCdItemMenu().toString();
		String dsSubMenu = tempItemMenu.getDsItemMenu();
		outcomeSubMenu = tempItemMenu.getDsLink();
		String cdAutoAtendimento  = "";
		
		if(tempItemMenu.getAutoAtendimento() != null)
			cdAutoAtendimento = tempItemMenu.getAutoAtendimento().getCdAutoatendimento().toString();
		
		preencherSubMenuMigalha(cdSubMenu, dsSubMenu, outcomeSubMenu, cdAutoAtendimento);
		if(tipoMenu != null)
		preencherMenuMigalha(tipoMenu.getCodigo().toString(), tipoMenu.getRealName(), tipoMenu.getOutcome());
		
		// Executa tarefas relacionadas a auditoria da agencia
		auditoriaBBean.auditarAcesso(cdSubMenu, cdAutoAtendimento);
		}
		
		return outcomeSubMenu;
		
	}
	
	/**
     * Método responsável por carregar os dados das seções.
     */
	public String carregar() {
		List<SecaoPaginaIncialTO> lSecao = new ArrayList<SecaoPaginaIncialTO>();
		lSecao = secaoService.findAll();
		for (SecaoPaginaIncialTO secao : lSecao) {
			List<ItemPaginaInicialTO> lTemp = new ArrayList<ItemPaginaInicialTO>();
			lTemp = itemService.findByParams(secao);
			ordenar(secao, lTemp);
		}
		setListaNoticia(noticiaService.findAll());
		
		return "PAGINAINICIAL";
	}
	
	/**
	 * Carrega todos os itens de menu no combo box da pagina inicial 
	 * ordenados por nome.
	 */
	private void carregarComboItensMenu() {
		listaComboBoxItemMenu = itemMenuService.pesquisaTodosItensMenu();
	}

	/**
     * Método responsável por ordenar a lista de acordo com sua seção.
     * 
     * @param secao SecaoPaginaIncialTO
     * @param lista List<ItemPaginaInicialTO>
     */
	private void ordenar(SecaoPaginaIncialTO secao, List<ItemPaginaInicialTO> lista) {
		switch (secao.getTpSecao()) {
			case SECAO_PRINCIPAL:
				secaoPrincipal = new ItemPaginaInicialTO();
				this.secaoPrincipalDescricao = secao.getTtSecao();
				secaoPrincipal.setSecao(secao);
				listaSecaoPrincipal.clear();
				listaSecaoPrincipal.addAll(lista);
			break;
			case SECAO_SECUNDARIA_ONE:
				secaoSecundariaOne = new ItemPaginaInicialTO();
				this.secaoSecundariaOneDescricao = secao.getTtSecao();
				secaoSecundariaOne.setSecao(secao);
				listaSecaoSecundariaOne.clear();
				listaSecaoSecundariaOne.addAll(lista);
			break;
			case SECAO_SECUNDARIA_TWO:
				secaoSecundariaTwo = new ItemPaginaInicialTO();
				this.secaoSecundariaTwoDescricao = secao.getTtSecao();
				secaoSecundariaTwo.setSecao(secao);
				listaSecaoSecundariaTwo.clear();
				listaSecaoSecundariaTwo.addAll(lista);
			break;
			case BARRA_HORIZONTAL:
				barraHorizontal = new ItemPaginaInicialTO();
				barraHorizontal.setSecao(secao);
				listaBarraHorizontal.clear();
				listaBarraHorizontal.addAll(lista);
			break;
			case SECAO_INFERIOR_DIREITA:
				secaoInferiorDireita = new ItemPaginaInicialTO();
				secaoInferiorDireita.setSecao(secao);
				listaSecaoInferiorDireita.clear();
				listaSecaoInferiorDireita.addAll(lista);
			break;
			default:
			break;
		}
	}
	
	public String direcionarLink() {
		String cdMenu = getCodigoMenu();
		String dsMenu = TipoMenu.byValue(Integer.parseInt(cdMenu)).getRealName();
		String outcomeMenu = getOutcomeMenu();
		String outcomeSubMenu = getOutcomeSubMenu();

        listaSubMenu = itemMenuService.findGrupoByItemPai(cdMenu);
        
        preencherMenuMigalha(cdMenu, dsMenu, outcomeMenu);

        for (Object iterable : listaSubMenu.toArray()) {
        	AgvTabItemMenuGrupo itemGrupo = (AgvTabItemMenuGrupo) iterable;
        	if (preencherCamposSubMenuMigalha(outcomeSubMenu, itemGrupo.getItensAcesso().toArray())) {
				break;
			}
		}
        
		return outcomeSubMenu;
	}
	
	private boolean preencherCamposSubMenuMigalha(String outcomeSubMenu, Object[] objects) {
		
		for (Object object : objects) {
			AgvTabItemMenuAcesso itemAcesso = (AgvTabItemMenuAcesso) object;
			if (outcomeSubMenu.equalsIgnoreCase(itemAcesso.getDsLink())) {
				String cdAutoAtendimento = EMPTY;
				//Se o auto atendimento for null é porque esse item não tem auto atendimento
				if (WrapperUtils.isNotNull(itemAcesso.getAutoAtendimento())) {
					cdAutoAtendimento = itemAcesso.getAutoAtendimento().getCdAutoatendimento().toString();
				}
				
				preencherSubMenuMigalha(itemAcesso.getCdItemMenu().toString(), itemAcesso.getDsItemMenu(), itemAcesso.getDsLink(), cdAutoAtendimento);
				// Executa tarefas relacionadas a auditoria da agencia
				auditoriaBBean.auditarAcesso(itemAcesso.getCdItemMenu().toString(), cdAutoAtendimento);
				return true;
			}
		}
		return false;
	}

	public TimeZone getTimeZone() {
		return java.util.TimeZone.getDefault();
	}
	
	public ItemPaginaInicialTO getSecaoPrincipal() {
		return secaoPrincipal;
	}

	public void setSecaoPrincipal(ItemPaginaInicialTO secaoPrincipal) {
		this.secaoPrincipal = secaoPrincipal;
	}

	public ItemPaginaInicialTO getSecaoSecundariaOne() {
		return secaoSecundariaOne;
	}

	public void setSecaoSecundariaOne(ItemPaginaInicialTO secaoSecundariaOne) {
		this.secaoSecundariaOne = secaoSecundariaOne;
	}

	public ItemPaginaInicialTO getSecaoSecundariaTwo() {
		return secaoSecundariaTwo;
	}

	public void setSecaoSecundariaTwo(ItemPaginaInicialTO secaoSecundariaTwo) {
		this.secaoSecundariaTwo = secaoSecundariaTwo;
	}

	public ItemPaginaInicialTO getBarraHorizontal() {
		return barraHorizontal;
	}

	public void setBarraHorizontal(ItemPaginaInicialTO barraHorizontal) {
		this.barraHorizontal = barraHorizontal;
	}

	public ItemPaginaInicialTO getSecaoInferiorDireita() {
		return secaoInferiorDireita;
	}

	public void setSecaoInferiorDireita(ItemPaginaInicialTO secaoInferiorDireita) {
		this.secaoInferiorDireita = secaoInferiorDireita;
	}

	public List<ItemPaginaInicialTO> getListaSecaoPrincipal() {
		return listaSecaoPrincipal;
	}

	public void setListaSecaoPrincipal(List<ItemPaginaInicialTO> listaSecaoPrincipal) {
		this.listaSecaoPrincipal = listaSecaoPrincipal;
	}

	public List<ItemPaginaInicialTO> getListaSecaoSecundariaOne() {
		return listaSecaoSecundariaOne;
	}

	public void setListaSecaoSecundariaOne(
			List<ItemPaginaInicialTO> listaSecaoSecundariaOne) {
		this.listaSecaoSecundariaOne = listaSecaoSecundariaOne;
	}

	public List<ItemPaginaInicialTO> getListaSecaoSecundariaTwo() {
		return listaSecaoSecundariaTwo;
	}

	public void setListaSecaoSecundariaTwo(
			List<ItemPaginaInicialTO> listaSecaoSecundariaTwo) {
		this.listaSecaoSecundariaTwo = listaSecaoSecundariaTwo;
	}

	public List<ItemPaginaInicialTO> getListaBarraHorizontal() {
		return listaBarraHorizontal;
	}

	public void setListaBarraHorizontal(
			List<ItemPaginaInicialTO> listaBarraHorizontal) {
		this.listaBarraHorizontal = listaBarraHorizontal;
	}

	public List<ItemPaginaInicialTO> getListaSecaoInferiorDireita() {
		return listaSecaoInferiorDireita;
	}

	public void setListaSecaoInferiorDireita(
			List<ItemPaginaInicialTO> listaSecaoInferiorDireita) {
		this.listaSecaoInferiorDireita = listaSecaoInferiorDireita;
	}

	public List<NoticiaTO> getListaNoticia() {
		return listaNoticia;
	}

	public void setListaNoticia(List<NoticiaTO> listaNoticia) {
		this.listaNoticia = listaNoticia;
	}

	public String getSecaoSecundariaOneDescricao() {
		return secaoSecundariaOneDescricao;
	}

	public void setSecaoSecundariaOneDescricao(String secaoSecundariaOneDescricao) {
		this.secaoSecundariaOneDescricao = secaoSecundariaOneDescricao;
	}

	public String getSecaoSecundariaTwoDescricao() {
		return secaoSecundariaTwoDescricao;
	}

	public void setSecaoSecundariaTwoDescricao(String secaoSecundariaTwoDescricao) {
		this.secaoSecundariaTwoDescricao = secaoSecundariaTwoDescricao;
	}

	public String getSecaoPrincipalDescricao() {
		return secaoPrincipalDescricao;
	}

	public void setSecaoPrincipalDescricao(String secaoPrincipalDescricao) {
		this.secaoPrincipalDescricao = secaoPrincipalDescricao;
	}

	public String getPathImages() {
		try {
			pathImages = "http://"+InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {	
			pathImages = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("com.path.sabesp.http.server");
		}
		
		return pathImages;
	}

	public void setPathImages(String pathImages) {
		this.pathImages = pathImages;
	}

	public List<AgvTabItemMenuAcesso> getListaComboBoxItemMenu() {
		return listaComboBoxItemMenu;
	}

	public void setListaComboBoxItemMenu(
			List<AgvTabItemMenuAcesso> listaComboBoxItemMenu) {
		this.listaComboBoxItemMenu = listaComboBoxItemMenu;
	}

	public String getIdItemMenuSelected() {
		return idItemMenuSelected;
	}

	public void setIdItemMenuSelected(String idItemMenuSelected) {
		this.idItemMenuSelected = idItemMenuSelected;
	}

	public List<SelectItem> getListaComboBoxItemMenuSelectItem() {
		
		if(listaComboBoxItemMenu.size() == 0 ){
			carregarComboItensMenu();
		}
		ArrayList<SelectItem> listaRetorno = new ArrayList<SelectItem>();
			
		listaRetorno.add(new SelectItem( String.valueOf(0), "Selecione..."));
			for(AgvTabItemMenuAcesso element : this.listaComboBoxItemMenu){
				SelectItem item = new SelectItem( String.valueOf(element.getCdItemMenu()), element.getDsItemMenu());
				listaRetorno.add(item);
			}
		return listaRetorno;
	}

	public void setListaComboBoxItemMenuSelectItem(
			List<SelectItem> listaComboBoxItemMenuSelectItem) {
		this.listaComboBoxItemMenuSelectItem = listaComboBoxItemMenuSelectItem;
	}
	
	
	
}
