package com.prime.app.agvirtual.web.jsf;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;
import com.prime.app.agvirtual.entity.AgvTabBloqueio;
import com.prime.app.agvirtual.entity.AgvTabBloqueioDetalhe;
import com.prime.app.agvirtual.entity.AgvTabItemMenuAcesso;
import com.prime.app.agvirtual.enums.UnidadeNegocio;
import com.prime.app.agvirtual.service.BloqueioService;
import com.prime.app.agvirtual.service.ItemMenuService;
import com.prime.app.agvirtual.service.MunicipioService;
import com.prime.app.agvirtual.to.BloqueioTO;
import com.prime.app.agvirtual.to.LoginTO;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.web.jsf.component.selectinputtext.UfDictionary;
import com.prime.app.agvirtual.web.jsf.util.AgvStatus;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.CrudServiceBBean;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

@Component
/**
 * BackBean com controles da tela de Bloqueio de Servicos
 */
public class BloqueioBBean extends CrudServiceBBean<AgvTabBloqueio, Long> 
implements Serializable ,InitializingBean {

	private static final long serialVersionUID = -2924497964816710919L;
	
	protected Effect valueChangeEffect;
	
	
	public BloqueioBBean() {
        valueChangeEffect = new Highlight("#fda505");
    }
	
	private static ArrayList<MunicipioTO> listaUfsStatic;
	
	private static final Logger agvlogger = LoggerFactory.getLogger(BloqueioBBean.class);
    
    @Autowired
    private ItemMenuService itemMenuService;
    
    @Autowired
    private BloqueioService bloqueioService;
    
    @Autowired
    private MunicipioService municipioService;
	
	private static String LISTAMUNICIPIOS = "listaMunicipios";
    
    private static String LISTASERVICOS = "listaServicos";
    
	/**
	 * Armazena o caminho da imagem de erro ou de sucesso.
	 */
	private String caminhoImagemErroSucesso = null;
    
    //Objeto a ser persistido
    private BloqueioTO bloqueioTO =  new BloqueioTO();
    
    private ListDataModel listaBloqueios =  new ListDataModel();
    
    private List<SelectItem> listaServicos = new ArrayList<SelectItem>();
    
    // Filtro UF
    private List<MunicipioTO> listaUf = new ArrayList<MunicipioTO>();
    
    private List<SelectItem> listaUn = new ArrayList<SelectItem>();
    
    private int ufListLength = 17; //TODO passar para properties

    private MunicipioTO selectedUf = new MunicipioTO();
    
    private List ufMatchPossibilities;
    
    private UfDictionary ufDictionary;
    
    private Boolean edit = true;
    
    private String cdUn;
    
    //Atributo de controle 
    private Boolean flagTodosMunicipos = false;

    //Atributo de controle 
    private Boolean flagTodasFuncionalidades = false;
    
    //Atributos do form
    
    private String cdServico;
    
    private String cdUf;
    
    private List<AgvTabItemMenuAcesso> listaServicosAdicionados = new ArrayList<AgvTabItemMenuAcesso>();
    
    private HashSet<MunicipioTO> listaUfsAdicionados = new HashSet<MunicipioTO>();
    
    private String municipioSelecionado;

	private boolean existeMsgErro;
   
    
    /**
     * Adiciona Serviço a lista de serviços a serem bloqueados.
     * 
     */ 
    public void adicionarServico(ActionEvent e){
    	listaServicosAdicionados = getSessionAttribute(LISTASERVICOS);
		/**
		 * Valida se o item selecionado ja não esta na lista 
		 */
		boolean estaNaLista = false;
		String descricaoMessage = null;
		for (Iterator<AgvTabItemMenuAcesso> iter  = listaServicosAdicionados.iterator(); iter.hasNext();) {
			AgvTabItemMenuAcesso elementTemp = (AgvTabItemMenuAcesso) iter.next();
			if(elementTemp.getCdItemMenu().toString().equals(cdServico)){
				descricaoMessage = elementTemp.getDsItemMenu();
				estaNaLista = true;
				break;
			}
		}
		if(estaNaLista){
			String messageBundle = FacesBundleUtil.getInstance().getString("av.bloqueio.validacao.servico.duplicado");
			String messageHandled = messageBundle.replace("{0}", descricaoMessage);
			FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, messageHandled, null));
			caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
		}else{
			AgvTabItemMenuAcesso temp = itemMenuService.findById(WrapperUtils.toLong(cdServico),false);
			if(temp != null){
				listaServicosAdicionados.add(temp);
				getHttpSession(false).setAttribute(LISTASERVICOS,listaServicosAdicionados); 
				caminhoImagemErroSucesso = null; //limpa erro da tela
			}else{
				FacesContext.getCurrentInstance().addMessage(null,
			            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao incluir Serviço", null));
				 caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
			}
		}
    }
    
    /**
     * Adiciona uf na lista de municipios a serem bloqueados
     * @param e
     */
    public void adicionarUf(ActionEvent e){
    	listaUfsAdicionados = getSessionAttribute(LISTAMUNICIPIOS);
    	
		boolean estaNaLista = false;
		
		/**
		 * Verifica se o municipo selecionado é SaoPaulo , caso seja , verifica se ja nao existe um municipio Sao Paulo adicionado na lista
		 * Sao Paulo tem uma particularidade , que é a existencia de 5 Unidade de negocio para o mesmo municipio.
		 */
		if(verificaUnidadeNegocio(listaUfsAdicionados,selectedUf)){
			FacesContext.getCurrentInstance().addMessage(null,
			 new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("av.bloqueio.validacao.municipio.sao.paulo"),null));
			caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
		}else{
			for (Iterator<MunicipioTO> iter  = listaUfsAdicionados.iterator(); iter.hasNext();) {
				MunicipioTO elementTemp = (MunicipioTO) iter.next();
				if(elementTemp.getIdMun().toString().equals(selectedUf.getIdMun().toString())){
					estaNaLista = true;
					break;
				}
			}
			MunicipioTO temp = (selectedUf);
			if(!estaNaLista){
				verificaMunicipiSaoPaulo(temp);
				listaUfsAdicionados.add(temp);
				selectedUf = new MunicipioTO();
				getHttpSession(false).setAttribute(LISTAMUNICIPIOS,listaUfsAdicionados);
				municipioSelecionado = null;
			}
			else{
				String messageBundle = FacesBundleUtil.getInstance().getString("av.bloqueio.validacao.municipio.duplicado");
				String messageHandled = messageBundle.replace("{0}", temp.getNome());
				FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_INFO, messageHandled, null));
				caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
			}
		}
    }
    
    /**
     * Por Sao Paulo ter mais de uma unidade de negocio , é necessário fazer um "de para" entre os códigos cadastrados no banco.
     * @param temp
     */
    private void verificaMunicipiSaoPaulo(MunicipioTO temp) {
    	if(temp.getIdMun().toString().equals("99991") ||
    			temp.getIdMun().toString().equals("99992") ||
    					temp.getIdMun().toString().equals("99993") ||
    						temp.getIdMun().toString().equals("99994") ||
    							temp.getIdMun().toString().equals("99990")){
    		temp.setIdMun(100l); //Cod Sao Paulo
    	}
	}

	/**
     * Verifica se existe mais de uma unidade de negocio para o mesmo bloqueio
     * @param listaUfsAdicionados2
     * @param selectedUf2
     * @return
     */
    private boolean verificaUnidadeNegocio(
			HashSet<MunicipioTO> listaUfsAdicionados, MunicipioTO selectedUf) {
    	if(selectedUf.getNome().toUpperCase().startsWith("são paulo".toUpperCase()) || selectedUf.getNome().toUpperCase().startsWith("sao paulo".toUpperCase()) ){
	    	for (Iterator<MunicipioTO> iterator  = listaUfsAdicionados.iterator(); iterator.hasNext();) {
				MunicipioTO municipioTO = (MunicipioTO) iterator .next();
				String temp = municipioTO.getNome();
				if(temp.toUpperCase().startsWith("são paulo".toUpperCase()) ||  selectedUf.getNome().toUpperCase().startsWith("sao paulo".toUpperCase())){
					return true;
				}
			}
    	}
		return false;
	}

    
    /**
     * Excluir a UF
     * @param e
     */
	public void excluirUf(ActionEvent e){
 	   	String codUfParam = getRequestParameterMap("codUfParam");
 		
 	   listaUfsAdicionados = getSessionAttribute(LISTAMUNICIPIOS);
 	   MunicipioTO MunicipioTORemove = null;
 		
 		for (Iterator<MunicipioTO> iter = listaUfsAdicionados.iterator(); iter.hasNext();) {
 			MunicipioTO ufTemp = (MunicipioTO) iter.next();
 			if(ufTemp.getCodUf().toString().equals(codUfParam)){
 				MunicipioTORemove = ufTemp;
 				break;
 			}
 		}
 		if(MunicipioTORemove != null){
 			listaUfsAdicionados.remove(MunicipioTORemove);
 			getHttpSession(false).setAttribute(LISTAMUNICIPIOS, listaUfsAdicionados);
 			municipioSelecionado = null;
 		}
    }
    
    public void excluirServico(ActionEvent e){
    	FacesContext context = FacesContext.getCurrentInstance();
 	   	Map map = context.getExternalContext().getRequestParameterMap();
 	   	String codServicoParam = (String) map.get("cdServicoParam");
 		
 	   	listaServicosAdicionados = getSessionAttribute(LISTASERVICOS);
 	   AgvTabItemMenuAcesso servicoTORemove = null;
 		
 		for (Iterator<AgvTabItemMenuAcesso> iter = listaServicosAdicionados.iterator(); iter.hasNext();) {
 			AgvTabItemMenuAcesso servicoTemp = (AgvTabItemMenuAcesso) iter.next();
 			if(servicoTemp.getCdItemMenu().toString().equals(codServicoParam)){
 				servicoTORemove = servicoTemp;
 				break;
 			}
 		}
 		if(servicoTORemove != null){
 			listaServicosAdicionados.remove(servicoTORemove);
 			getHttpSession(false).setAttribute(LISTASERVICOS, listaServicosAdicionados);
 		}
    }
    
    /**
     * <p>Called by the selectInputText component at set intervals.  By using
     * the change event we can can get the newly typed work and do a look up in
     * the city dictionary.  The list of possible cities calculatd from the city
     * dictionary is assigned back to the component for display.</p>
     * <p>If the component selected a value then we find the respective city
     * information for dispaly purposes.
     *
     * @param event jsf value change event.
     */
    public void selectInputValueChanged(ValueChangeEvent event) {

        if (event.getComponent() instanceof SelectInputText) {

            // get the number of displayable records from the component
            SelectInputText autoComplete =
                    (SelectInputText) event.getComponent();
            // get the new value typed by component user.
            String newWord = (String) event.getNewValue();
            
            if(newWord.length() == 0 || newWord.trim().length() == 0 ){
            	selectedUf = new MunicipioTO();
            }

            ufMatchPossibilities = UfDictionary.generateCityMatches(newWord, ufListLength);

            // if there is a selected item then find the city object of the
            // same name
            if (autoComplete.getSelectedItem() != null) {
                try {
                	selectedUf = (MunicipioTO) BeanUtils.cloneBean((MunicipioTO) autoComplete.getSelectedItem().getValue());
				} catch (IllegalAccessException e) {
					agvlogger.error(e.getMessage());
				} catch (InstantiationException e) {
					agvlogger.error(e.getMessage());
				} catch (InvocationTargetException e) {
					agvlogger.error(e.getMessage());
				} catch (NoSuchMethodException e) {
					agvlogger.error(e.getMessage());
				}
				municipioSelecionado = selectedUf.getIdMun().toString();
            } else {
            	municipioSelecionado = null;
			}
        }
    }
    
    
    /**
     * Utility method for finding detailed city information fromn the list of
     * possibile city names.
     *
     * @param cityName city name to do city search on.
     * @return found city object if any, null otherwise.
     */
    private MunicipioTO getFindUfMatch(String ufName) {
        if (ufMatchPossibilities != null) {
            SelectItem uf;
            for(int i = 0, max = ufMatchPossibilities.size(); i < max; i++){
                uf = (SelectItem)ufMatchPossibilities.get(i);
                int compare =  uf.getLabel().toUpperCase().compareToIgnoreCase(ufName.toUpperCase());
                if (compare >= 0 ) {
                	MunicipioTO t = (MunicipioTO) uf.getValue();
                	t.setNome(uf.getLabel());
                    return t;
                }
            }
        }
        return null;
    }
    
    /**
     * Carrega dados do registro para edição
     * @return
     */
    public String edit() {
    	AgvTabBloqueio  t = (AgvTabBloqueio) listaBloqueios.getRowData();
    	bloqueioTO =  new BloqueioTO();
    	
    	bloqueioTO.setCdBloqueio(t.getCdBloqueio());
    	bloqueioTO.setDataBloqueio(WrapperUtils.parseDate(t.getDtInclusao()));
    	bloqueioTO.setDescricao(t.getNmBloqueio());
    	bloqueioTO.setMensagem(t.getDsMensagem());
//    	bloqueioTO.setNmBloqueio(nmBloqueio)
    	
    	listaUfsAdicionados = new HashSet<MunicipioTO>();
    	listaServicosAdicionados =  new ArrayList<AgvTabItemMenuAcesso>();
    	
    	/**
    	 * Retorna objetos de detalhe , recupera quais sao as funcionalidades e quais sao os Municipios evitando 
    	 * a duplicidade
    	 */
    	HashMap tempFuncionalidade = new HashMap();
    	HashMap tempMunicipio = new HashMap();
    	
    	for (Iterator iterator = t.getAgvTabBloqueioDetalheList().iterator(); iterator.hasNext();) {
    		AgvTabBloqueioDetalhe element = (AgvTabBloqueioDetalhe) iterator.next();
			if(element.getTodosFuncionalidade() != null && !element.getTodosFuncionalidade()){
				tempFuncionalidade.put(element.getAgvTabItemMenuAcesso().getCdItemMenu(),element.getAgvTabItemMenuAcesso());
				bloqueioTO.setTodoasFuncionalidades(false);
			}else{
				bloqueioTO.setTodoasFuncionalidades(true);
			}
			if(element.getTodosMunicipios() != null && !element.getTodosMunicipios()){
				tempMunicipio.put(element.getCdMunicipio(),element);
				bloqueioTO.setTodosMunicipios(false);
			}else{
				bloqueioTO.setTodosMunicipios(true);
			}
		}
    	
    	for (Iterator iterator = tempMunicipio.values().iterator(); iterator.hasNext();) {
			AgvTabBloqueioDetalhe elementFor = (AgvTabBloqueioDetalhe) iterator.next();
			carregaNomeSaoPauloUnidadeNegocio(elementFor);
			listaUfsAdicionados.add(elementFor.getMunicipio().parseTo());
		}
    	
    	if(!bloqueioTO.isTodoasFuncionalidades()){
    		listaServicosAdicionados.addAll(tempFuncionalidade.values());
    	}
    	
    	getHttpSession(false).setAttribute(LISTASERVICOS, listaServicosAdicionados);
    	getHttpSession(false).setAttribute(LISTAMUNICIPIOS, listaUfsAdicionados);
    	caminhoImagemErroSucesso = null;
        return AgvStatus.EDIT.name();
    }
    
    private void carregaNomeSaoPauloUnidadeNegocio(AgvTabBloqueioDetalhe element) {
    	
    	if (element.getCdUn().toString().equals("51")) {
    		element.getMunicipio().setNomeMunicipio("São Paulo (Centro)");
		}else
		if (element.getCdUn().toString().equals("52")) {
			element.getMunicipio().setNomeMunicipio("São Paulo (Norte)");
		}else
		if (element.getCdUn().toString().equals("53")) {
			element.getMunicipio().setNomeMunicipio("São Paulo (Sul)");
		}else
		if (element.getCdUn().toString().equals("54")) {
			element.getMunicipio().setNomeMunicipio("São Paulo (Leste)");
		}else
		if (element.getCdUn().toString().equals("55")) {
			element.getMunicipio().setNomeMunicipio("São Paulo (Oeste)");
		}
	}

	/**
     * Carrega dados do registro para edição
     * @return
     */
    public String excluir() {
    	if(bloqueioTO.getCdBloqueio() == null){
        	FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não é possível excluir o registro", null));
    		
    	}else{
    		LoginTO userSession = getSessionAttribute(LoginBBean.USER);
    		if(userSession != null){
    			bloqueioTO.setNmUserExclusao(userSession.getUser());
    		}
    		
    		bloqueioTO.setExcluido(true);
    		bloqueioTO.setDataBloqueio(WrapperUtils.parseDate(new Date()));
    		
    		save(bloqueioTO.toEntity());
    		agvlogger.info("Registro "+bloqueioTO.getCdBloqueio()+" Bloqueio foi apagado pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
        	FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "O bloqueio "+ bloqueioTO.getDescricao() +" foi excluído.", null));
        	caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
    	}
    	atualizar();    	
        return AgvStatus.ATUALIZA.name();
    }
    
    
    /**
     * Carrega formulário para nova notícia
     * @return
     */
    public String novoBloqueio() {
    	bloqueioTO = new BloqueioTO();
    	getHttpSession(false).setAttribute(LISTASERVICOS, new ArrayList());
    	getHttpSession(false).setAttribute(LISTAMUNICIPIOS, new HashSet());
    	flagTodasFuncionalidades = false;
    	municipioSelecionado = null;
    	flagTodosMunicipos = false;
    	ufDictionary =  new UfDictionary(((ArrayList)municipioService.findAll()));
    	caminhoImagemErroSucesso = null;
       	selectedUf = new MunicipioTO();
        return AgvStatus.NOVO.name();
    }
    
    /**
     * Salva registro
     * @return
     */
    public String salvar() {
    	
        try {
        	LoginTO userSession = getSessionAttribute(LoginBBean.USER);
    		if(userSession != null){
    			bloqueioTO.setNmUserCriacao(userSession.getUser());
    		}
        	bloqueioTO.setDataBloqueio((WrapperUtils.parseDate(new Date())));
        	
        	/**
        	 * Para todos os municipios ou todos as funcionalidades
        	 */
        	
        	if(flagTodasFuncionalidades){
//        		listaServicosAdicionados = itemMenuService.findAll();
        		bloqueioTO.setTodoasFuncionalidades(true);
        	}
        	if(flagTodosMunicipos){
//        		listaUfsAdicionados.addAll(municipioService.findAll());
        		bloqueioTO.setTodosMunicipios(true);
        	}
        	/**
        	 * 
        	 */
        	bloqueioTO.setExcluido(false);
        	bloqueioTO.setServicos(listaServicosAdicionados);
        	bloqueioTO.setUfs(listaUfsAdicionados);
        	boolean flag = validarCampos(bloqueioTO);
        	if(flag){
        		return AgvStatus.EDIT.name();
        	}
        	bloqueioService.save(bloqueioTO);
//            save(bloqueioTO.toEntity());
            agvlogger.info("Registro do Bloqueio foi criado pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
            atualizar();
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "O bloqueio foi incluído", null));
            caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
            return AgvStatus.ATUALIZA.name();
        }
        catch (Exception e) {
        	agvlogger.error(e.getMessage());
            e.printStackTrace(); //TODO retirar
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao incluir Registro Bloqueio", null));
            caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    		return AgvStatus.EDIT.name();
        }
    }
    
    /**
     * Atualiza tela de noícias com nova busca
     * @return
     */
    public String atualizar() {
    	this.listaBloqueios = new ListDataModel(bloqueioService.findAll()); //TODO arrumar 
        return AgvStatus.ATUALIZA.name();
    }

	private boolean validarCampos(BloqueioTO bloqueioTO) {
		boolean flag = false;
		if(bloqueioTO.getUfs().size() <= 0 && flagTodosMunicipos == false){
			FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("av.bloqueio.validacao.uf"), null));
			caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
			flag = true;
		}
		if(bloqueioTO.getServicos().size() <= 0 && flagTodasFuncionalidades == false){
			FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("av.bloqueio.validacao.servicos"), null));
			caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
			flag = true;
		}
		if(bloqueioTO.getDescricao() ==  null || bloqueioTO.getDescricao().trim().equals("") ){
			FacesContext.getCurrentInstance().addMessage(null,
		            new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("av.bloqueio.validacao.descricao"), null));
			caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
			flag = true;
		}
		if(bloqueioTO.getMensagem() ==  null || bloqueioTO.getMensagem().trim().equals("") ){
			FacesContext.getCurrentInstance().addMessage(null,
		            new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("av.bloqueio.validacao.motivo"), null));
			caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
			flag = true;
		}
		
		return flag;
	}

	public void afterPropertiesSet() throws Exception {
		this.listaBloqueios = new ListDataModel(bloqueioService.findAll()); 
	}

	public ListDataModel getListaBloqueios() {
		return listaBloqueios;
	}

	public void setListaBloqueios(ListDataModel listaBloqueios) {
		this.listaBloqueios = listaBloqueios;
	}

	public List<MunicipioTO> getListaUf() {
		return listaUf;
	}

	public void setListaUf(ArrayList<MunicipioTO> listaUf) {
		this.listaUf = listaUf;
	}

	public List<SelectItem> getListaServicos() {
		return itemMenuService.findAllSelectedItems();
	}

	public void setListaServicos(List<SelectItem> listaServicos) {
		this.listaServicos = listaServicos;
	}

	public MunicipioTO getSelectedUf() {
		return selectedUf;
	}

	public void setSelectedUf(MunicipioTO selectedUf) {
		this.selectedUf = selectedUf;
	}

	public BloqueioTO getBloqueioTO() {
		return bloqueioTO;
	}

	public void setBloqueioTO(BloqueioTO bloqueioTO) {
		this.bloqueioTO = bloqueioTO;
	}
	
	public Effect getValueChangeEffect() {
        return valueChangeEffect;
    }

    public void setValueChangeEffect(Effect valueChangeEffect) {
        this.valueChangeEffect = valueChangeEffect;
    }


	public List getUfMatchPossibilities() {
		return ufMatchPossibilities;
	}


	public void setUfMatchPossibilities(List ufMatchPossibilities) {
		this.ufMatchPossibilities = ufMatchPossibilities;
	}


	public int getUfListLength() {
		return ufListLength;
	}


	public void setUfListLength(int ufListLength) {
		this.ufListLength = ufListLength;
	}


	public UfDictionary getUfDictionary() {
		return ufDictionary;
	}


	public void setUfDictionary(UfDictionary ufDictionary) {
		this.ufDictionary = ufDictionary;
	}


	public void setListaUf(List<MunicipioTO> listaUf) {
		this.listaUf = listaUf;
	}


	public List getListaServicosAdicionados() {
		if(getSessionAttribute(LISTASERVICOS) == null){
			getHttpSession(false).setAttribute(LISTASERVICOS, listaServicosAdicionados);
		}else{
			listaServicosAdicionados = getSessionAttribute(LISTASERVICOS);
		}
		return listaServicosAdicionados;
	}

	public void setListaServicosAdicionados(List listaServicosAdicionados) {
		this.listaServicosAdicionados = listaServicosAdicionados;
	}

	public HashSet getListaUfsAdicionados() {
		if(getSessionAttribute(LISTAMUNICIPIOS) == null){
			getHttpSession(false).setAttribute(LISTAMUNICIPIOS, listaUfsAdicionados);
		}else{
			listaUfsAdicionados = getSessionAttribute(LISTAMUNICIPIOS);
		}
		return listaUfsAdicionados;
	}

	public void setListaUfsAdicionados(HashSet listaUfsAdicionados) {
		this.listaUfsAdicionados = listaUfsAdicionados;
	}

	public String getCdServico() {
		return cdServico;
	}

	public void setCdServico(String cdServico) {
		this.cdServico = cdServico;
	}

	public String getCdUf() {
		return cdUf;
	}

	public void setCdUf(String cdUf) {
		this.cdUf = cdUf;
	}
	
	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public List<SelectItem> getListaUn() {
		return UnidadeNegocio.getListSelectItem();
	}

	public void setListaUn(List<SelectItem> listaUn) {
		this.listaUn = listaUn;
	}

	public String getCdUn() {
		return cdUn;
	}

	public void setCdUn(String cdUn) {
		this.cdUn = cdUn;
	}

	public Boolean getFlagTodosMunicipos() {
		return flagTodosMunicipos;
	}

	public void setFlagTodosMunicipos(Boolean flagTodosMunicipos) {
		this.flagTodosMunicipos = flagTodosMunicipos;
	}

	public Boolean getFlagTodasFuncionalidades() {
		return flagTodasFuncionalidades;
	}

	public void setFlagTodasFuncionalidades(Boolean flagTodasFuncionalidades) {
		this.flagTodasFuncionalidades = flagTodasFuncionalidades;
	}
	
	public void tipoChanged(ValueChangeEvent e) {
		flagTodosMunicipos = (Boolean) e.getNewValue();
	}
	
	public void tipoChangedFunciona(ValueChangeEvent e) {
		flagTodasFuncionalidades = (Boolean) e.getNewValue();
	}

	public String getCaminhoImagemErroSucesso() {
		return caminhoImagemErroSucesso;
	}

	public void setCaminhoImagemErroSucesso(String caminhoImagemErroSucesso) {
		this.caminhoImagemErroSucesso = caminhoImagemErroSucesso;
	}

	public String getMunicipioSelecionado() {
		return municipioSelecionado;
	}

	public void setMunicipioSelecionado(String municipioSelecionado) {
		this.municipioSelecionado = municipioSelecionado;
	}
	
	@SuppressWarnings("unchecked")
	public boolean getExisteMsgErro(){
		Iterator iter = FacesContext.getCurrentInstance().getMessages();
		existeMsgErro = iter.hasNext();
		return existeMsgErro;
	}
}
