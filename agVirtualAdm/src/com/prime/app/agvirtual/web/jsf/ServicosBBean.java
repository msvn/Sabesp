package com.prime.app.agvirtual.web.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.AgvTabServico;
import com.prime.app.agvirtual.entity.AgvTabSubservico;
import com.prime.app.agvirtual.enums.Categoria;
import com.prime.app.agvirtual.service.ServicoService;
import com.prime.app.agvirtual.service.SubServicoService;
import com.prime.app.agvirtual.to.ServicoTO;
import com.prime.app.agvirtual.to.SubServicoTO;
import com.prime.app.agvirtual.web.jsf.util.AgvStatus;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.CrudServiceBBean;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * BackBean com controles da classe Serviços
 */
@Component
//@Scope("request")
public class ServicosBBean extends CrudServiceBBean<AgvTabServico, Long> 
implements Serializable , InitializingBean {
	
	private static final Logger agvlogger = LoggerFactory.getLogger(ServicosBBean.class);

    /**
	 * 
	 */
	private static final long serialVersionUID = -2924497964816710919L;

    @Autowired
    private SubServicoService subServicoService;
    
    @Autowired
    private ServicoService servicoService;
    
	/**
	 * Lista de objetos servicos
	 */
    private ListDataModel servicos;
    
    private String codSubServico;
    
    private ServicoTO servico = new ServicoTO();
    
    /**
     * Array utilizado para selecionar as categorias
     */
    private List<SelectItem> listCategorias = Categoria.getListSelectItem();
    
    /**
     * Array utilizado para selecionar SubServiços
     */
    private List<SelectItem> listSubServico = new ArrayList<SelectItem>();
    
    
    private List<SubServicoTO> listaSubServicoAdicionados;
    
    private List<SubServicoTO> listaSubServicoExcluido;
    
    /**
     * Variavel que representa o id selecionado no combo categoria
     */
    private String codCategoria;
    
    private Boolean mostraBotaoExcluir = false;
    
    private String caminhoImagemErroSucesso= null;
    private Boolean mostraTituloNovo = false;

	private boolean existeMsgErro;
	
	private String nameEditValue;

    /**
     * Retorna listagem de Servicos
     * @return
     */
    public ListDataModel getServicos() {
        return servicos;
    }
    
    /**
     * Salva registro
     * @return
     */
    public String salvar() {
    	boolean flag = validarCampos(servico);
    	if(flag){
    		return AgvStatus.EDIT.name();
    	}
        try {
        	
        	
        	if( mostraTituloNovo ) {
        		
            	if( !this.nameEditValue.equals( servico.getNmServico()  ) ){

    	        	if( servicoService.nameAlreadyExists(servico.getNmServico())  ){
    	                
    	        		FacesContext.getCurrentInstance().addMessage(null,
    	                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Serviço já cadastrado!", null));
    	                    caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    	            		return AgvStatus.EDIT.name();
    	
    	        	}

            		
            	}
        		
        	}else{
        		
	        	if( servicoService.nameAlreadyExists(servico.getNmServico())  ){
	                
	        		FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Serviço já cadastrado!", null));
	                    caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
	            		return AgvStatus.EDIT.name();
	
	        	}        		
        		
        	}
        	
        	AgvTabServico servicoModel = servico.toEntity(listaSubServicoAdicionados);
        	
        	if( servicoModel.getCdServico() == null ) {
        		// save

            	servico.setCdCategoria(codCategoria);
            	listaSubServicoAdicionados = getSessionAttribute("listaSubServicoAdicionados");
            	listaSubServicoExcluido = getSessionAttribute("listaSubServicoExcluido");
            	servicoService.save(servico.toEntity(listaSubServicoAdicionados) , listaSubServicoExcluido);
                agvlogger.info("Registro com Nome "+ servico.getNmServico() +"foi criado pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
                atualizar();
                FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "O Serviço foi salvo", null));
                caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");

        		
        	}else {
        		// update
        		
            	servico.setCdCategoria(codCategoria);
            	listaSubServicoAdicionados = getSessionAttribute("listaSubServicoAdicionados");
            	listaSubServicoExcluido = getSessionAttribute("listaSubServicoExcluido");
            	servicoService.save(servico.toEntity(listaSubServicoAdicionados) , listaSubServicoExcluido);
                agvlogger.info("Registro com Nome "+ servico.getNmServico() +"foi criado pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
                atualizar();
                FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "O Serviço foi alterado", null));
                caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");

        		
        	}
        	
            return AgvStatus.ATUALIZA.name();
        }
        catch (Exception e) {
        	agvlogger.error(e.getMessage());
            e.printStackTrace(); //TODO retirar
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao incluir Serviço", null));
            caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    		return AgvStatus.EDIT.name();
        }
    }
    
    /**
     * Valida os campos
     * @param servico2
     * @return
     */
    private boolean validarCampos(ServicoTO servico2) {
    	boolean exibeMensagensErro = false;
    	
    	if("".equals(servico.getNmServico().trim())){
    		FacesContext.getCurrentInstance().addMessage(null,
    	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nome não preenchido.", null));
    		 caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    		 exibeMensagensErro = true;
    	} else {
    		servico.setNmServico(WrapperUtils.replaceHTML(servico.getNmServico()));
    	}
    	
    	if( this.codCategoria.equals("0") ){
    		FacesContext.getCurrentInstance().addMessage(null,
    	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Categoria não escolhida.", null));
    		 caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    		 exibeMensagensErro = true;
    	}
    	
    	if("".equals(servico.getDsObservacao().trim())){
    		FacesContext.getCurrentInstance().addMessage(null,
    	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Observação não preenchida.", null));
    		 caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    		 exibeMensagensErro = true;
    	} else {
    		servico.setDsObservacao(WrapperUtils.replaceHTML(servico.getDsObservacao()));
    	}
    	
    	String[] row = servico.getDsObservacao().split("\\s") ;
    	
    	if(row.length == 1 && servico.getDsObservacao().length() > 29 ){
			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Serviço: Insira espaços entre as palavras.", null));
			 caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
			 exibeMensagensErro = true;  
    	}
    	return exibeMensagensErro;
	}

	/**
     * Adiciona subServico 
     */
    public void adicionaSubServ(ActionEvent e){
		listaSubServicoAdicionados = getSessionAttribute("listaSubServicoAdicionados");
		/**
		 * Valida se o item selecionado ja não esta na lista 
		 */
		boolean estaNaLista = false;
		if(codSubServico != null){
			for (Iterator iter  = listaSubServicoAdicionados.iterator(); iter.hasNext();) {
				SubServicoTO elementTemp = (SubServicoTO) iter .next();
				if(elementTemp.getCdSubservico().toString().equals(codSubServico)){
					estaNaLista = true;
					break;
				}
			}
			if(estaNaLista){
				FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Subserviço já selecionado", null));
				 caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
			}else{
				AgvTabSubservico temp = subServicoService.findById(WrapperUtils.toLong(codSubServico),false);
				listaSubServicoAdicionados.add(temp.parseTO());
				getHttpSession(false).setAttribute("listaSubServicoAdicionados", getListaSubServicoAdicionados());
				caminhoImagemErroSucesso = null;
			}
		}
    }
    

    /**
     * Remove subServico 
     */
    public void excluirSubServ(ActionEvent e){
        String codSubServ = getRequestParameterMap("codSubServ");
    	
		listaSubServicoAdicionados = getSessionAttribute("listaSubServicoAdicionados");
		SubServicoTO subServRemove = null;
		
		for (Iterator<SubServicoTO> iter = listaSubServicoAdicionados.iterator(); iter.hasNext();) {
			SubServicoTO subServ = (SubServicoTO) iter.next();
			if(subServ.getCdSubservico().toString().equals(codSubServ)){
				subServRemove =  subServ; 
				getListaSubServicoExcluido().add(subServRemove);
				break;
			}
		}
		if(subServRemove != null){
			listaSubServicoAdicionados.remove(subServRemove);
			getHttpSession(false).setAttribute("listaSubServicoAdicionados", getListaSubServicoAdicionados());
			getHttpSession(false).setAttribute("listaSubServicoExcluido", getListaSubServicoExcluido());
//			if(listSubServico == null || listSubServico.isEmpty()){
//				listSubServico = subServicoService.findAllSelectedItems();
//			}
		}
    }
    
    
    /**
     * Atualiza tela de Serviços com nova busca
     * @return
     */
    public String atualizar() {
    	this.servicos = new ListDataModel(servicoService.findAll()); //TODO colocar em properties
        return AgvStatus.ATUALIZA.name();
    }

    
    /**
     * Edita um objeto Serviço
     * @return
     */
    public String edit() {
    	servico = (ServicoTO) servicos.getRowData();
    	listSubServico =  subServicoService.findAllSelectedItemsNaoIncluidos(); 
    	getHttpSession(false).setAttribute("listaSubServicoAdicionados", servico.getListaSubServico());
    	getHttpSession(false).setAttribute("listaSubServicoExcluido", new ArrayList());
    	mostraBotaoExcluir = true;
    	mostraTituloNovo = true;
    	this.nameEditValue = servico.getNmServico();
    	listaSubServicoAdicionados = servico.getListaSubServico();
    	codCategoria =  servico.getCdCategoria();
    	codSubServico = "";
        return AgvStatus.EDIT.name();
    }
    
    
    /**
     * Exclui o serviço.
     * @return
     */
    public String excluir(){
    	if(servico.getCdServico() == null){
        	FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não é possível excluir o registro", null));
        	caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    		
    	}else{
    		subServicoService.excluir((List)getSessionAttribute("listaSubServicoAdicionados"));
//    		subServicoService.excluir();
    		remove(servico.getCdServico());
    		agvlogger.info("Registro "+servico.getCdServico()+"  Serviço foi apagado pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
        	FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "O Serviço "+ servico.getNmServico() +" foi excluído.", null));
        	 caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
    	}
    	atualizar();
        return AgvStatus.ATUALIZA.name();
    }

	public List<SelectItem> getListCategorias() {
		return listCategorias;
	}

	public void setListCategorias(List<SelectItem> listCategorias) {
		this.listCategorias = listCategorias;
	}

	public ServicoTO getServico() {
		return servico;
	}

	public void setServico(ServicoTO servico) {
		this.servico = servico;
	}
	
	public List<SelectItem> getListSubServico() {
		return listSubServico;
	}
	
	public String novoServico(){
		servico =  new ServicoTO();
		getHttpSession(false).setAttribute("listaSubServicoAdicionados", null); //limpa listagem de subserviços
		listaSubServicoAdicionados =  new ArrayList<SubServicoTO>();
		listaSubServicoExcluido =  new ArrayList<SubServicoTO>();
		mostraBotaoExcluir = false;
		mostraTituloNovo = false;
		caminhoImagemErroSucesso = null;
		this.codCategoria = null;
		this.listCategorias = Categoria.getListSelectItem();
		listSubServico =  subServicoService.findAllSelectedItemsNaoIncluidos(); 
		codSubServico = "";
		return AgvStatus.EDIT.name();
	}

	public void setListSubServico(List<SelectItem> listSubServico) {
		this.listSubServico = listSubServico;
	}

	public String getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(String codCategoria) {
		this.codCategoria = codCategoria;
	}

	public String getCodSubServico() {
		return codSubServico;
	}

	public void setCodSubServico(String codSubServico) {
		this.codSubServico = codSubServico;
	}

	public void afterPropertiesSet() throws Exception {
		servicos = new ListDataModel(servicoService.findAll());
	}
	
	public void carregarListener(ActionEvent ev){
		servicos = new ListDataModel(servicoService.findAll());
	}

	public List<SubServicoTO> getListaSubServicoAdicionados() {
		List temp = getSessionAttribute("listaSubServicoAdicionados");
		if(temp == null){
			listaSubServicoAdicionados =  new ArrayList<SubServicoTO>();
			getHttpSession(false).setAttribute("listaSubServicoAdicionados", listaSubServicoAdicionados);
		}else{
			listaSubServicoAdicionados = temp;
		}
		return listaSubServicoAdicionados;
	}

	public void setListaSubServicoAdicionados(
		List<SubServicoTO> listaSubServicoAdicionados) {
		this.listaSubServicoAdicionados = listaSubServicoAdicionados;
	}

	public List<SubServicoTO> getListaSubServicoExcluido() {
		List temp = getSessionAttribute("listaSubServicoExcluido");
		if(temp == null){
			listaSubServicoExcluido =  new ArrayList<SubServicoTO>();
			getHttpSession(false).setAttribute("listaSubServicoExcluido", listaSubServicoExcluido);
		}else{
			listaSubServicoExcluido = temp;
		}
		return listaSubServicoExcluido;
	}

	public void setListaSubServicoExcluido(
			List<SubServicoTO> listaSubServicoExcluido) {
		this.listaSubServicoExcluido = listaSubServicoExcluido;
	}

	public Boolean getMostraBotaoExcluir() {
		return mostraBotaoExcluir;
	}

	public void setMostraBotaoExcluir(Boolean mostraBotaoExcluir) {
		this.mostraBotaoExcluir = mostraBotaoExcluir;
	}

	public String getCaminhoImagemErroSucesso() {
		return caminhoImagemErroSucesso;
	}

	public void setCaminhoImagemErroSucesso(String caminhoImagemErroSucesso) {
		this.caminhoImagemErroSucesso = caminhoImagemErroSucesso;
	}

	public Boolean getMostraTituloNovo() {
		return mostraTituloNovo;
	}

	public void setMostraTituloNovo(Boolean mostraTituloNovo) {
		this.mostraTituloNovo = mostraTituloNovo;
	}
	
	public boolean getExisteMsgErro(){
		Iterator iter = FacesContext.getCurrentInstance().getMessages();
		existeMsgErro = iter.hasNext();
		return existeMsgErro;
	}
	
	
}
