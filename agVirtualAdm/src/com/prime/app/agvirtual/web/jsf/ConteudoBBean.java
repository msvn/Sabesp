package com.prime.app.agvirtual.web.jsf;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventObject;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import com.prime.app.agvirtual.file.InputFileData;
import com.prime.app.agvirtual.service.ItemPaginaInicialService;
import com.prime.app.agvirtual.service.SecaoPaginaInicialService;
import com.prime.app.agvirtual.to.ItemPaginaInicialTO;
import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;
import com.prime.app.agvirtual.web.jsf.util.AgvStatus;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.BasicBBean;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * BackBean com controles da classe Conteúdo.
 */
@Component
@Scope(value="session")
public class ConteudoBBean extends BasicBBean implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -2208761209366959211L;
	
	/**
	 * Log.
	 */
	private static final Logger agvlogger = LoggerFactory.getLogger(ConteudoBBean.class);
	
	@Autowired
    private ItemPaginaInicialService itemService;
	
	@Autowired
    private SecaoPaginaInicialService secaoService;
	
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

	/**
	 * Armazena o item selecionado da Seção Principal.
	 */
	private String itemSelecionadoSecaoPrincipal = null;
	
	/**
	 * Armazena o item selecionado da Seção Secundária 1.
	 */
	private String itemSelecionadoSecaoSecundariaOne = null;
	
	/**
	 * Armazena o item selecionado da Seção Secundária 2.
	 */
	private String itemSelecionadoSecaoSecundariaTwo = null;
	
	/**
	 * Armazena o item selecionado da Seção Barra Horizontal.
	 */
	private String itemSelecionadoBarraHorizontal = null;
	
	/**
	 * Armazena o item selecionado da Seção Inferior Direita.
	 */
	private String itemSelecionadoSecaoInferiorDireita = null;
	
	/**
	 * Armazena o item selecionado no radio da Seção Principal.
	 */
	private String itemSelecionadoRadio;
	
	/**
	 * Atributo para verificar quando será exibida a mensagem de conclusão.
	 */
	private boolean exibeMensagemConclusao = false;
	
	/**
	 * Armazena o caminho da imagem de erro ou de sucesso.
	 */
	private String caminhoImagemErroSucesso = null;
	
	/**
	 * Atributos usado pelo componente Upload
	 */
	//Atributos utilizados pelo componente de Upload
	// File sizes used to generate formatted label
    public static final long MEGABYTE_LENGTH_BYTES = 1048000l;
    public static final long KILOBYTE_LENGTH_BYTES = 1024l;
    // latest file uploaded by client
    private InputFileData currentFile;
    // file upload completed percent (Progress)
    private int fileProgress;
    
    private int fileProgress2;
    
    private int fileProgress3;
    
    private int fileProgress4;
    

    /**
     * Método responsável por salvar os dados das seções.
     * 
     * @param event ActionEvent
     */
    public String salvar() {
    	
    	//Validação dos campos
    	if(!validarCampos()){
    		return AgvStatus.ATUALIZA.name();
    	}
        try {
        	//Monta os dados para salvar no banco
        	List<ItemPaginaInicialTO> lTemp = new ArrayList<ItemPaginaInicialTO>();
        	secaoPrincipal.getSecao().setQtItens(itemSelecionadoRadio);
        	secaoService.save(secaoPrincipal.getSecao());
        	lTemp.add(secaoPrincipal);
        	lTemp.add(secaoSecundariaOne);
        	lTemp.add(secaoSecundariaTwo);
        	lTemp.add(barraHorizontal);
        	lTemp.add(secaoInferiorDireita);
        	itemService.save(lTemp);
        	
        	FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "As informações foram alteradas.", null));
        	caminhoImagemErroSucesso = "/images/mensagem_sucesso.gif";
        	exibeMensagemConclusao = true;
            
            //Carrega novamente os dados da página
            carregarSecoes();
            
            return AgvStatus.ATUALIZA.name();
        }
        catch (Exception e) {
        	agvlogger.error(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar os dados!", null));
            caminhoImagemErroSucesso = "/images/mensagem_erro.gif";
            exibeMensagemConclusao = true;
            return AgvStatus.ATUALIZA.name();
        }
    }
    
    /**
     * Regras de Negócio para validação dos campos.
     */
    private boolean validarCampos() {
    	boolean valida = true;
    	String obrigatorio = FacesBundleUtil.getInstance().getString("abc.error.preenchaCampo");
    	
    	//Seção Principal
    	if ("".equals(secaoPrincipal.getSecao().getTtSecao().trim())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoPrincipalTitulo",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.titulo"), null));
			valida = false;
		}
    	if ("".equals(itemSelecionadoRadio)) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoPrincipalQuantidadeItens",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.qtdItens"), null));
    		valida = false;
		}
    	if ("-1".equals(secaoPrincipal.getCdItem())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:botaoAlterarComboSecaoPrincipal",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.item"), null));
    		valida = false;
		}
    	if ("".equals(secaoPrincipal.getDsItem())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoPrincipalTexto",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.texto"), null));
    		valida = false;
		}
    	if ("".equals(secaoPrincipal.getDsLink())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoPrincipalLink",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.link"), null));
    		valida = false;
		}
    	if ("".equals(secaoPrincipal.getDsCaminhoImagem()) || secaoPrincipal.getDsCaminhoImagem() == null) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoPrincipalImagem",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.arquivo"), null));
			valida = false;
		}
    	
    	//Seção Secundária 1
    	if ("".equals(secaoSecundariaOne.getSecao().getTtSecao().trim())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoSecundariaOneTitulo",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.titulo"), null));
    		valida = false;
		}
    	if ("-1".equals(secaoSecundariaOne.getCdItem())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:botaoAlterarComboSecaoSecundariaOne",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.item"), null));
    		valida = false;
		}
    	if ("".equals(secaoSecundariaOne.getDsItem())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoSecundariaOneTexto",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.texto"), null));
    		valida = false;
		}
    	if ("".equals(secaoSecundariaOne.getDsLink())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoSecundariaOneLink",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.link"), null));
    		valida = false;
		}
    	if ("".equals(secaoSecundariaOne.getDsCaminhoImagem()) || secaoSecundariaOne.getDsCaminhoImagem() == null) {
			FacesContext.getCurrentInstance().addMessage("formConteudo:secaoSecundariaOneImagem",
	    	new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.arquivo"), null));
			valida = false;
		}
    	
    	//Seção Secundária 2
    	if ("".equals(secaoSecundariaTwo.getSecao().getTtSecao().trim())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoSecundariaTwoTitulo",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.titulo"), null));
    		valida = false;
		}
    	if ("-1".equals(secaoSecundariaTwo.getCdItem())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:botaoAlterarComboSecaoSecundariaTwo",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.item"), null));
    		valida = false;
		}
    	if ("".equals(secaoSecundariaTwo.getDsItem())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoSecundariaTwoTexto",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.texto"), null));
    		valida = false;
		}
    	if ("".equals(secaoSecundariaTwo.getDsLink())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoSecundariaTwoLink",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.link"), null));
    		valida = false;
		}
    	if ("".equals(secaoSecundariaTwo.getDsCaminhoImagem()) || secaoSecundariaTwo.getDsCaminhoImagem() == null) {
			FacesContext.getCurrentInstance().addMessage("formConteudo:secaoSecundariaTwoImagem",
	    	new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.arquivo"), null));
			valida = false;
		}
    	
    	//Barra Horizontal
    	if ("".equals(barraHorizontal.getSecao().getTtSecao().trim())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:barraHorizontalTitulo",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.titulo"), null));
    		valida = false;
		}
    	if ("-1".equals(barraHorizontal.getCdItem())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:botaoAlterarComboBarraHorizontal",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.item"), null));
    		valida = false;
		}
    	if ("".equals(barraHorizontal.getDsItem())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:barraHorizontalTexto",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.texto"), null));
    		valida = false;
		}
    	if ("".equals(barraHorizontal.getDsLink())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:barraHorizontalLink",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.link"), null));
    		valida = false;
		}

    	//Seção Inferior Direita
    	if ("-1".equals(secaoInferiorDireita.getCdItem())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:botaoAlterarComboSecaoInferiorDireita",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.item"), null));
    		valida = false;
		}
    	if ("".equals(secaoInferiorDireita.getDsItem())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoInferiorDireitaTexto",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.texto"), null));
    		valida = false;
		}
    	if ("".equals(secaoInferiorDireita.getDsLink())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoInferiorDireitaLink",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.link"), null));
    		valida = false;
		}
    	if ("".equals(secaoInferiorDireita.getDsResumoItem())) {
    		FacesContext.getCurrentInstance().addMessage("formConteudo:secaoInferiorDireitaBreveDescricao",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.breveDescricao"), null));
    		valida = false;
		}
    	if ("".equals(secaoInferiorDireita.getDsCaminhoImagem()) || secaoInferiorDireita.getDsCaminhoImagem() == null) {
			FacesContext.getCurrentInstance().addMessage("formConteudo:secaoInferiorDireitaImagem",
	    	new FacesMessage(FacesMessage.SEVERITY_ERROR, obrigatorio + FacesBundleUtil.getInstance().getString("abc.label.arquivo"), null));
			valida = false;
		}
		return valida;
	}
	
	/**
     * Método responsável por carregar a página.
     */
	public String carregar() {
		exibeMensagemConclusao = false;
		carregarSecoes();
		
		return "CONTEUDO";
	}

	/**
     * Método responsável por carregar os dados das seções.
     */
	private void carregarSecoes() {
		List<SecaoPaginaIncialTO> lSecao = new ArrayList<SecaoPaginaIncialTO>();
		lSecao = secaoService.findAll();
		for (SecaoPaginaIncialTO secao : lSecao) {
			List<ItemPaginaInicialTO> lTemp = new ArrayList<ItemPaginaInicialTO>();
			lTemp = itemService.findByParams(secao);
			try {
				ordenar(secao, lTemp);
			} catch (IllegalAccessException e) {
				agvlogger.error(e.getMessage());
			} catch (InvocationTargetException e) {
				agvlogger.error(e.getMessage());
			}
		}
	}
	
	/**
     * Método responsável por ordenar a lista de acordo com sua seção.
     * 
     * @param secao SecaoPaginaIncialTO
     * @param lista List<ItemPaginaInicialTO>
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
     */
	@SuppressWarnings("unchecked")
	private void ordenar(SecaoPaginaIncialTO secao, List<ItemPaginaInicialTO> lista) throws IllegalAccessException, InvocationTargetException {
		switch (secao.getTpSecao()) {
			case SECAO_PRINCIPAL:
				secaoPrincipal = new ItemPaginaInicialTO();
				secaoPrincipal.setSecao(secao);
				itemSelecionadoRadio = secao.getQtItens();
				listaSecaoPrincipal.clear();
				listaSecaoPrincipal.addAll(lista);
				Collections.sort(listaSecaoPrincipal, LABEL_COMPARATOR);
				BeanUtils.copyProperties(secaoPrincipal, listaSecaoPrincipal.get(0));
				itemSelecionadoSecaoPrincipal = secaoPrincipal.getCdItem();
			break;
			case SECAO_SECUNDARIA_ONE:
				secaoSecundariaOne = new ItemPaginaInicialTO();
				secaoSecundariaOne.setSecao(secao);
				listaSecaoSecundariaOne.clear();
				listaSecaoSecundariaOne.addAll(lista);
				Collections.sort(listaSecaoSecundariaOne, LABEL_COMPARATOR);
				BeanUtils.copyProperties(secaoSecundariaOne, listaSecaoSecundariaOne.get(0));
				itemSelecionadoSecaoSecundariaOne = secaoSecundariaOne.getCdItem();
			break;
			case SECAO_SECUNDARIA_TWO:
				secaoSecundariaTwo = new ItemPaginaInicialTO();
				secaoSecundariaTwo.setSecao(secao);
				listaSecaoSecundariaTwo.clear();
				listaSecaoSecundariaTwo.addAll(lista);
				Collections.sort(listaSecaoSecundariaTwo, LABEL_COMPARATOR);
				BeanUtils.copyProperties(secaoSecundariaTwo, listaSecaoSecundariaTwo.get(0));
				itemSelecionadoSecaoSecundariaTwo = secaoSecundariaTwo.getCdItem();
			break;
			case BARRA_HORIZONTAL:
				barraHorizontal = new ItemPaginaInicialTO();
				barraHorizontal.setSecao(secao);
				listaBarraHorizontal.clear();
				listaBarraHorizontal.addAll(lista);
				Collections.sort(listaBarraHorizontal, LABEL_COMPARATOR);
				BeanUtils.copyProperties(barraHorizontal, listaBarraHorizontal.get(0));
				itemSelecionadoBarraHorizontal = barraHorizontal.getCdItem();
			break;
			case SECAO_INFERIOR_DIREITA:
				secaoInferiorDireita = new ItemPaginaInicialTO();
				secaoInferiorDireita.setSecao(secao);
				listaSecaoInferiorDireita.clear();
				listaSecaoInferiorDireita.addAll(lista);
				Collections.sort(listaSecaoInferiorDireita, LABEL_COMPARATOR);
				BeanUtils.copyProperties(secaoInferiorDireita, listaSecaoInferiorDireita.get(0));
				itemSelecionadoSecaoInferiorDireita = secaoInferiorDireita.getCdItem();
			break;
			default:
			break;
		}
	}
	
	/**
	 * Lista de quantidade de Itens Seção Principal.
	 * 
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getListQtdItens() {
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		SelectItem item = new SelectItem(String.valueOf("4"), "4 Itens");
		lista.add(item);
		item = new SelectItem(String.valueOf("6"), "6 Itens");
		lista.add(item);
		return lista;
	}
	
	/**
	 * Lista de quantidade de Itens Seção Principal.
	 * 
	 * @return List<SelectItem>
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getListItensSecaoPrincipal() {
		Collections.sort(listaSecaoPrincipal, LABEL_COMPARATOR);
		return setCombo(Long.parseLong(secaoPrincipal.getSecao().getQtItens()), listaSecaoPrincipal);
	}
	
	
	
	/**
	 * Lista de quantidade de Itens Seção Secundária 1.
	 * 
	 * @return List<SelectItem>
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getListItensSecaoSecundariaOne() {
		Collections.sort(listaSecaoSecundariaOne, LABEL_COMPARATOR);
		return setCombo(Long.parseLong(secaoSecundariaOne.getSecao().getQtItens()), listaSecaoSecundariaOne);
	}
	
	/**
	 * Lista de quantidade de Itens Seção Secundária 2.
	 * 
	 * @return List<SelectItem>
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getListItensSecaoSecundariaTwo() {
		Collections.sort(listaSecaoSecundariaTwo, LABEL_COMPARATOR);
		return setCombo(Long.parseLong(secaoSecundariaTwo.getSecao().getQtItens()), listaSecaoSecundariaTwo);
	}
	
	/**
	 * Lista de quantidade de Itens Seção Barra Horizontal.
	 * 
	 * @return List<SelectItem>
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getListItensBarraHorizontal() {
		Collections.sort(listaBarraHorizontal, LABEL_COMPARATOR);
		return setCombo(Long.parseLong(barraHorizontal.getSecao().getQtItens()), listaBarraHorizontal);
	}
	
	/**
	 * Lista de quantidade de Itens Seção Inferior Direita.
	 * 
	 * @return List<SelectItem>
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getListItensSecaoInferiorDireita() {
		Collections.sort(listaSecaoInferiorDireita, LABEL_COMPARATOR);
		return setCombo(Long.parseLong(secaoInferiorDireita.getSecao().getQtItens()), listaSecaoInferiorDireita);
	}
	
	/**
	 * Método responsável por criar uma lista de SelectItem a partir duma Lista de ItemPaginaInicialTO.
	 * 
	 * @param lItens List<ItemPaginaInicialTO>
	 * @return List<SelectItem>
	 */
	private List<SelectItem> setCombo(Long qtd, List<ItemPaginaInicialTO> lItens){
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		for(int i=0 ; i < qtd && i < lItens.size(); i++){
			ItemPaginaInicialTO object = lItens.get(i);
			SelectItem item = new SelectItem(String.valueOf(object.getCdItem()), object.getDsItem());
			lista.add(item);
		}
		return lista;
	}
	
	/**
	 * Método responsável por tratar o item selecionado da combo.
	 * 
	 * @param itemSelecionado String
	 * @param secao ItemPaginaInicialTO
	 * @param lItens List<ItemPaginaInicialTO>
	 * @return String
	 */
	private String alterarCombo(String itemSelecionado, ItemPaginaInicialTO secao, List<ItemPaginaInicialTO> lItens){
		exibeMensagemConclusao = false;
		if ("-1".equals(secao.getCdItem())) {
			secao.setDsCaminhoImagem("");
			secao.setDsItem("");
			secao.setDsLink("");
			secao.setDsResumoItem("");
			itemSelecionado = null;
			return itemSelecionado;
		} else if (!"".equals(itemSelecionado)) {
			ItemPaginaInicialTO tempor = null;
			
			tempor = findOnList(itemSelecionado, lItens);	 // ??? obtem
			tempor.setCdItem(itemSelecionado);
			lItens.remove(tempor);  			  			// ??? remove
			tempor.setDsCaminhoImagem(secao.getDsCaminhoImagem());
			tempor.setDsItem(secao.getDsItem());
			tempor.setDsLink(secao.getDsLink());
			tempor.setDsResumoItem(secao.getDsResumoItem());
			tempor.setSecao(secao.getSecao());
			lItens.add(tempor);								// ??? adiciona
			
			// correcao de bug pre HML - rever toda esta logica com o Felipe
			String qty = "";
			if(WrapperUtils.isNotNull(tempor) && WrapperUtils.isNotNull(tempor.getSecao())){
				qty = tempor.getSecao().getQtItens();
			}
			
			tempor = null;
			tempor = findOnList(secao.getCdItem(), lItens);    			// ??? obtem novamente, mas outro, ulalalaaa
			
			if(!"".equals(qty)) tempor.getSecao().setQtItens(qty);      // correcao de bug pre HML
			
			try {
				BeanUtils.copyProperties(secao, tempor);
			} catch (IllegalAccessException e) {
				agvlogger.error(e.getMessage());
			} catch (InvocationTargetException e) {
				agvlogger.error(e.getMessage());
			}
			itemSelecionado = secao.getCdItem();
			itemService.save(lItens);
			return itemSelecionado;
		} else {
			ItemPaginaInicialTO tempor = null;
			tempor = findOnList(secao.getCdItem(), lItens);
			try {
				BeanUtils.copyProperties(secao, tempor);
			} catch (IllegalAccessException e) {
				agvlogger.error(e.getMessage());
			} catch (InvocationTargetException e) {
				agvlogger.error(e.getMessage());
			}
			itemSelecionado = secao.getCdItem();
			return itemSelecionado;
		}
	}
	
	/**
	 * Método responsável tratar a seleção do item na combo da Seção Principal.
	 * 
	 * @param event ActionEvent
	 */
	public void alterarComboSecaoPrincipal(ActionEvent event) {
        setItemSelecionadoSecaoPrincipal(
			alterarCombo(
				this.itemSelecionadoSecaoPrincipal,
				this.secaoPrincipal,
				this.listaSecaoPrincipal));
	}
	
	/**
	 * Método responsável tratar a seleção do item na combo da Seção Secundária 1.
	 * 
	 * @param event ActionEvent
	 */
	public void alterarComboSecaoSecundariaOne(ActionEvent event) {
		setItemSelecionadoSecaoSecundariaOne(
			alterarCombo(
				this.itemSelecionadoSecaoSecundariaOne,
				this.secaoSecundariaOne,
				this.listaSecaoSecundariaOne));
	}
	
	/**
	 * Método responsável tratar a seleção do item na combo da Seção Secundária 2.
	 * 
	 * @param event ActionEvent
	 */
	public void alterarComboSecaoSecundariaTwo(ActionEvent event) {
		setItemSelecionadoSecaoSecundariaTwo(
			alterarCombo(
				this.itemSelecionadoSecaoSecundariaTwo,
				this.secaoSecundariaTwo,
				this.listaSecaoSecundariaTwo));
	}
	
	/**
	 * Método responsável tratar a seleção do item na combo da Seção Barra Horizontal.
	 * 
	 * @param event ActionEvent
	 */
	public void alterarComboBarraHorizontal(ActionEvent event) {
		setItemSelecionadoBarraHorizontal(
			alterarCombo(
				this.itemSelecionadoBarraHorizontal,
				this.barraHorizontal,
				this.listaBarraHorizontal));
	}
	
	/**
	 * Método responsável tratar a seleção do item na combo da Seção Inferior Direita.
	 * 
	 * @param event ActionEvent
	 */
	public void alterarComboSecaoInferiorDireita(ActionEvent event) {
		setItemSelecionadoSecaoInferiorDireita(
			alterarCombo(
				this.itemSelecionadoSecaoInferiorDireita,
				this.secaoInferiorDireita,
				this.listaSecaoInferiorDireita));
	}

	/**
	 * Método responsável por obter o item na lista.
	 * 
	 * @param cdItem String
	 * @param lItens List<ItemPaginaInicialTO>
	 */
	private ItemPaginaInicialTO findOnList(final String cdItem, final List<ItemPaginaInicialTO> lItens) {
		for (ItemPaginaInicialTO item : lItens) {
			if (item.getCdItem().equals(cdItem)) {
				return item;
			}
		}
		return null;
	}
	
	
	 /**
     * <p>Action event method which is triggered when a user clicks on the
     * upload file button. Any errors that occurs
     * during the file uploaded are added the messages output.</p>
     *
     * @param event jsf action event.
     */
    public void uploadFile(ActionEvent event) {
        InputFile inputFile = (InputFile) event.getSource();
        FileInfo fileInfo = inputFile.getFileInfo();
        if (fileInfo.getStatus() == FileInfo.SAVED) {
            // reference our newly updated file for display purposes and
            // added it to our history file list.
            secaoPrincipal.setDsCaminhoImagem(fileInfo.getFile().getName()); // So grava o ultimo caminho
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload do Arquivo feita com sucesso!", null));
        }
    }
    
    
    public void uploadFileSecundario(ActionEvent event) {
        InputFile inputFile = (InputFile) event.getSource();
        FileInfo fileInfo = inputFile.getFileInfo();
        if (fileInfo.getStatus() == FileInfo.SAVED) {
            // reference our newly updated file for display purposes and
            // added it to our history file list.
            secaoSecundariaOne.setDsCaminhoImagem(fileInfo.getFile().getName()); // So grava o ultimo caminho
//          getHttpSession(false).setAttribute("listaSubServicoAdicionados", getListaSubServicoAdicionados());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload do Arquivo feita com sucesso!", null));
        }
    }
    
    public void uploadFileSecundarioTwo(ActionEvent event) {
        InputFile inputFile = (InputFile) event.getSource();
        FileInfo fileInfo = inputFile.getFileInfo();
        if (fileInfo.getStatus() == FileInfo.SAVED) {
            // reference our newly updated file for display purposes and
            // added it to our history file list.
            secaoSecundariaTwo.setDsCaminhoImagem(fileInfo.getFile().getName()); // So grava o ultimo caminho
//          getHttpSession(false).setAttribute("listaSubServicoAdicionados", getListaSubServicoAdicionados());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload do Arquivo feita com sucesso!", null));
        }
    }
    
    public void uploadFileInferior(ActionEvent event) {
        InputFile inputFile = (InputFile) event.getSource();
        FileInfo fileInfo = inputFile.getFileInfo();
        if (fileInfo.getStatus() == FileInfo.SAVED) {
            // reference our newly updated file for display purposes and
            // added it to our history file list.
            secaoInferiorDireita.setDsCaminhoImagem(fileInfo.getFile().getName()); // So grava o ultimo caminho
//          getHttpSession(false).setAttribute("listaSubServicoAdicionados", getListaSubServicoAdicionados());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload do Arquivo feita com sucesso!", null));
        }
    }
    
    /**
     * <p>This method is bound to the inputFile component and is executed
     * multiple times during the file upload process.  Every call allows
     * the user to finds out what percentage of the file has been uploaded.
     * This progress information can then be used with a progressBar component
     * for user feedback on the file upload progress. </p>
     *
     * @param event holds a InputFile object in its source which can be probed
     *              for the file upload percentage complete.
     */
    public void fileUploadProgress(EventObject event) {
        InputFile ifile = (InputFile) event.getSource();
        fileProgress = ifile.getFileInfo().getPercent();
    }
    
    public void fileUploadProgress2(EventObject event) {
        InputFile ifile = (InputFile) event.getSource();
        fileProgress2 = ifile.getFileInfo().getPercent();
    }
    
    public void fileUploadProgress3(EventObject event) {
        InputFile ifile = (InputFile) event.getSource();
        fileProgress3 = ifile.getFileInfo().getPercent();
    }
    
    public void fileUploadProgress4(EventObject event) {
        InputFile ifile = (InputFile) event.getSource();
        fileProgress4 = ifile.getFileInfo().getPercent();
    } 
    
    public void alterarQuantidadeItens(ValueChangeEvent e){
    	exibeMensagemConclusao = false;
    	String qtd = (String) e.getNewValue();
    	secaoPrincipal.getSecao().setQtItens(qtd);
		itemSelecionadoSecaoPrincipal = listaSecaoPrincipal.get(0).getCdItem();
    }
    
    /**
	 * Comparator utility for sorting itens.
	 */
	@SuppressWarnings("unchecked")
	private static final Comparator LABEL_COMPARATOR = new Comparator() {

		public int compare(Object o1, Object o2) {
			ItemPaginaInicialTO item1 = (ItemPaginaInicialTO) o1;
			ItemPaginaInicialTO item2 = (ItemPaginaInicialTO) o2;
			
			

			return item1.getCdItem().compareTo(item2.getCdItem());
		}
	};

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

	public String getItemSelecionadoSecaoPrincipal() {
		return itemSelecionadoSecaoPrincipal;
	}

	public void setItemSelecionadoSecaoPrincipal(String itemSelecionadoSecaoPrincipal) {
		this.itemSelecionadoSecaoPrincipal = itemSelecionadoSecaoPrincipal;
	}

	public String getItemSelecionadoSecaoSecundariaOne() {
		return itemSelecionadoSecaoSecundariaOne;
	}

	public void setItemSelecionadoSecaoSecundariaOne(
			String itemSelecionadoSecaoSecundariaOne) {
		this.itemSelecionadoSecaoSecundariaOne = itemSelecionadoSecaoSecundariaOne;
	}

	public String getItemSelecionadoSecaoSecundariaTwo() {
		return itemSelecionadoSecaoSecundariaTwo;
	}

	public void setItemSelecionadoSecaoSecundariaTwo(
			String itemSelecionadoSecaoSecundariaTwo) {
		this.itemSelecionadoSecaoSecundariaTwo = itemSelecionadoSecaoSecundariaTwo;
	}

	public String getItemSelecionadoBarraHorizontal() {
		return itemSelecionadoBarraHorizontal;
	}

	public void setItemSelecionadoBarraHorizontal(
			String itemSelecionadoBarraHorizontal) {
		this.itemSelecionadoBarraHorizontal = itemSelecionadoBarraHorizontal;
	}

	public String getItemSelecionadoSecaoInferiorDireita() {
		return itemSelecionadoSecaoInferiorDireita;
	}

	public void setItemSelecionadoSecaoInferiorDireita(
			String itemSelecionadoSecaoInferiorDireita) {
		this.itemSelecionadoSecaoInferiorDireita = itemSelecionadoSecaoInferiorDireita;
	}

	public String getItemSelecionadoRadio() {
		return itemSelecionadoRadio;
	}

	public void setItemSelecionadoRadio(String itemSelecionadoRadio) {
		this.itemSelecionadoRadio = itemSelecionadoRadio;
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

	public InputFileData getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(InputFileData currentFile) {
		this.currentFile = currentFile;
	}

	public int getFileProgress() {
		return fileProgress;
	}

	public void setFileProgress(int fileProgress) {
		this.fileProgress = fileProgress;
	}

	public int getFileProgress2() {
		return fileProgress2;
	}

	public void setFileProgress2(int fileProgress2) {
		this.fileProgress2 = fileProgress2;
	}

	public int getFileProgress3() {
		return fileProgress3;
	}

	public void setFileProgress3(int fileProgress3) {
		this.fileProgress3 = fileProgress3;
	}

	public int getFileProgress4() {
		return fileProgress4;
	}

	public void setFileProgress4(int fileProgress4) {
		this.fileProgress4 = fileProgress4;
	}

	public boolean isExibeMensagemConclusao() {
		return exibeMensagemConclusao;
	}

	public void setExibeMensagemConclusao(boolean exibeMensagemConclusao) {
		this.exibeMensagemConclusao = exibeMensagemConclusao;
	}

	public String getCaminhoImagemErroSucesso() {
		return caminhoImagemErroSucesso;
	}

	public void setCaminhoImagemErroSucesso(String caminhoImagemErroSucesso) {
		this.caminhoImagemErroSucesso = caminhoImagemErroSucesso;
	}
	
}
