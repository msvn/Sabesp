package com.prime.app.agvirtual.web.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.apache.commons.httpclient.util.DateParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.AgvTabCanalAtend;
import com.prime.app.agvirtual.entity.AgvTabDocumento;
import com.prime.app.agvirtual.entity.AgvTabSubservico;
import com.prime.app.agvirtual.service.CanaisAtendimentoService;
import com.prime.app.agvirtual.service.DocumentoService;
import com.prime.app.agvirtual.service.SubServicoService;
import com.prime.app.agvirtual.to.CanalAtendimentoTO;
import com.prime.app.agvirtual.to.DocumentoTO;
import com.prime.app.agvirtual.to.SubServicoTO;
import com.prime.app.agvirtual.web.jsf.util.AgvStatus;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.CrudServiceBBean;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * BackBean com controles da classe SubServiço
 */
@Component
@Scope(value="session")
public class SubServicosBBean extends CrudServiceBBean<AgvTabSubservico, Long> 
implements Serializable,InitializingBean  {

	private String dataHidden;
	
	private String dia;
	
	private String mes;
	
	private String ano;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2924497964816710919L;

	private static final Logger agvlogger = LoggerFactory.getLogger(NoticiasBBean.class);
	
    @Autowired
    private SubServicoService subServicoService;
    
    @Autowired
    private CanaisAtendimentoService atendimentoService;
    
    @Autowired
    private DocumentoService docService;
    
    private static String LISTADOC = "listaDocumentoAdicionados";
    
    private static String LISTACANALATEN = "listaCanalAtendimentoAdicionados";

	/**
	 * Lista de objetos servicos
	 */
    
    private String codSubServico;
    
    private String dataAtualizacaoTO;
    
    private ListDataModel subServicos =  new ListDataModel();
    
    private SubServicoTO subServico;
    
    List<SelectItem> listaCanalAtendimento;
    
    List<SelectItem> listaDocumentos;
    
    List<CanalAtendimentoTO> listaCanalAtendimentoAdicionados =  new ArrayList<CanalAtendimentoTO>();
    
    List<DocumentoTO> listaDocumentoAdicionados =  new ArrayList<DocumentoTO>();
    
    private String codCanalAtendimento;
    
    private String codDocumento;
    
    private String titulo;
    
    //controle do botao excluir
    private Boolean mostraBotaoExcluir = false;
    
    //atributo usado para mostrar msg erro
    private String caminhoImagemErroSucesso= null;

	private boolean existeMsgErro;

    /**
     * Retorna listagem de SubServicos
     * @return
     */
    public ListDataModel getSubServicos() {
        return subServicos;
    }
    
    /**
     * Carrega formulário para nova notícia
     * Carrega listagens de Canais de Atendimento e Documentos
     * @return
     */
    public String novoSubServ() {
    	
    	this.titulo = "Novo Subserviço";
    	caminhoImagemErroSucesso = null;
    	subServico = new SubServicoTO();
    	subServico.setDataAtualizacao(WrapperUtils.getToday());
    	
    	this.dataHidden = WrapperUtils.parseDate(WrapperUtils.getToday());

    	dataAtualizacaoTO = WrapperUtils.parseDate(new Date());
    	getHttpSession(false).setAttribute(LISTACANALATEN,new ArrayList<CanalAtendimentoTO>() );
    	getHttpSession(false).setAttribute(LISTADOC,new ArrayList<DocumentoTO>());
    	mostraBotaoExcluir = false;
        return AgvStatus.EDIT.name();
    }
    
    /**
     * 
     * @return
     */
    public String atualizar() throws DateParseException{
    	subServicos = new ListDataModel(subServicoService.findAll()); //TODO colocar em properties
    	return AgvStatus.ATUALIZA.name();
    }
    
    /**
     * Edita um objeto SubServiço
     * Carrega listagens de Canais de Atendimento e Documentos
     * @return
     */
    public String edit() {
    	
    	this.titulo = "Alterar Subserviço";
    	subServico = (SubServicoTO) subServicos.getRowData();
    	dataAtualizacaoTO = WrapperUtils.parseDate(subServico.getDataAtualizacao());
    	subServico = subServicoService.findById(subServico.getCdSubservico(), false).parseTO();
/*
    	getHttpSession(false).setAttribute(LISTACANALATEN, subServico.getListaCanalAtendimento());
    	getHttpSession(false).setAttribute(LISTADOC, subServico.getListaDocumentos());
    	
*/    	
    	
    	this.dataHidden = WrapperUtils.parseDate(this.subServico.getDataPublicacao());
    	
		dia = dataHidden.split("/")[0];
		mes = dataHidden.split("/")[1];
		ano = dataHidden.split("/")[2];
    	
    	caminhoImagemErroSucesso = null;
    	mostraBotaoExcluir = true;
        return AgvStatus.EDIT.name();
    }
    
    /**
     * Exclui um objeto SubServiço
     * @return
     */
    public String excluir() throws Exception {
    	if(subServico.getCdSubservico() == null){
        	FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Não é possível excluir o registro.", null));
        	caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");    		
    	}else{
    		remove(subServico.getCdSubservico());
        	FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "O subserviço "+ subServico.getDsSubservico() +" foi excluído.", null));
        	caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
    	}
    	atualizar();
        return AgvStatus.ATUALIZA.name();
    }
    
    /**
     * Salva registro
     * @return
     */
    public String salvar() {
    	
    	//subServico.setDataPublicacao( WrapperUtils.toDate(dataHidden) );
    	
    	boolean flag = validarCampos(subServico);
    	if(flag){
    		caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    		return AgvStatus.EDIT.name();
    	}
        
    	if(subServico.getCdSubservico() != null){
    		try {
        		subServicoService.alterar(subServico.toEntity(getListaCanalAtendimentoAdicionados(),getListaDocumentoAdicionados()));
        		
        		atualizar();
        		 
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "O Subserviço "+ subServico.getDsSubservico() +" foi alterado", null));
                		caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
                        return AgvStatus.ATUALIZA.name();
    		} catch (Exception e) {
            	agvlogger.error(e.getMessage());
                e.printStackTrace(); //TODO retirar
                FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao alterar Subserviço", null));
                caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
        		return AgvStatus.EDIT.name();
            }
    		
    	}else{
    		try {
        		subServicoService.save(subServico.toEntity(getListaCanalAtendimentoAdicionados(),getListaDocumentoAdicionados()));
        		
        		 atualizar();
        		
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "O Subserviço "+ subServico.getDsSubservico() +" foi salvo", null));
                caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
                        return AgvStatus.ATUALIZA.name();
                    
    		} catch (Exception e) {
            	agvlogger.error(e.getMessage());
                e.printStackTrace(); //TODO retirar
                FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao incluir Subserviço", null));
                caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
        		return AgvStatus.EDIT.name();
            }
    	}
    }
    
    /**
     * Regras de Validação ao incluir/alterar subServiço
     * @param subServico2
     * @return
     */
    private boolean validarCampos(SubServicoTO subServicoValida) {
    	boolean flag = false;
    	
    	if( subServicoValida.getDsSubservico() == null || "".equals(subServicoValida.getDsSubservico().trim())  ){
    		
			FacesContext.getCurrentInstance().addMessage(null,
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nome não preenchido.", null));
					flag = true;
    	} else {
    		subServicoValida.setDsSubservico(WrapperUtils.replaceHTML(subServicoValida.getDsSubservico()));
    	}
    	
    	if( subServicoValida.getDsPrazoAtend() == null || "".equals(subServicoValida.getDsPrazoAtend().trim()) ){
			
    		FacesContext.getCurrentInstance().addMessage(null,
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Prazo para Atendimento não preenchido.", null));
					flag = true;    		
    	} else {
    		subServicoValida.setDsPrazoAtend(WrapperUtils.replaceHTML(subServicoValida.getDsPrazoAtend()));
    	}
    	
    	if( validarDatasValidas() ){
    		flag = true;
    	}
    	
    	if( subServicoValida.getDsCondExec() == null || "".equals(subServicoValida.getDsCondExec().trim()) ){
    		
    		FacesContext.getCurrentInstance().addMessage(null,
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condições para Execução não preenchido.", null));
					flag = true;
    	} else {
    		subServicoValida.setDsCondExec(WrapperUtils.replaceHTML(subServicoValida.getDsCondExec()));
    	}
    	
		return flag;
	}
    
    
    /**
     * Adiciona Canal de Atendimento ao SubServiço
     * @param e
     */
    public void adicionarCanalAtendimento(ActionEvent e){
    	listaCanalAtendimentoAdicionados = getSessionAttribute(LISTACANALATEN);
		/**
		 * Valida se o item selecionado ja não esta na lista 
		 */
		boolean estaNaLista = false;
		for (Iterator<CanalAtendimentoTO> iter  = listaCanalAtendimentoAdicionados.iterator(); iter.hasNext();) {
			CanalAtendimentoTO elementTemp = (CanalAtendimentoTO) iter .next();
			if(elementTemp.getCdCanalAtend().toString().equals(codCanalAtendimento)){
				estaNaLista = true;
				break;
			}
		}
		if(estaNaLista){
			FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Canal de Atendimento já selecionado", null));
			 caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
		}else{
			AgvTabCanalAtend temp = atendimentoService.findById(WrapperUtils.toLong(codCanalAtendimento),false);
			listaCanalAtendimentoAdicionados.add(temp.parseTO());
			getHttpSession(false).setAttribute(LISTACANALATEN,listaCanalAtendimentoAdicionados); 
		}
    }
    
    /**
     * Exclui Canal de Atendimento do SubServiço
     * @param e
     */
    public void excluirCanalAtendimento(ActionEvent e){
 	   	String codCanalAtendimento = (String)getRequestParameterMap("codCanalAtendimentoParam");
 		
 	   	listaCanalAtendimentoAdicionados = getSessionAttribute(LISTACANALATEN);
 	   	CanalAtendimentoTO canalAtendRemove = null;
 		
 		for (Iterator<CanalAtendimentoTO> iter = listaCanalAtendimentoAdicionados.iterator(); iter.hasNext();) {
 			CanalAtendimentoTO canalTemp = (CanalAtendimentoTO) iter.next();
 			if(canalTemp.getCdCanalAtend().toString().equals(codCanalAtendimento)){
 				canalAtendRemove = canalTemp;
 				break;
 			}
 		}
 		if(canalAtendRemove != null){
 			
 			listaCanalAtendimentoAdicionados.remove(canalAtendRemove);
 			getHttpSession(false).setAttribute(LISTACANALATEN, listaCanalAtendimentoAdicionados);
 		}
    	
    }
    
    /**
     * Adiciona Documento ao SubServiço
     * @param e
     */
    public void adicionarDocumento(ActionEvent e){
    	listaDocumentoAdicionados = getSessionAttribute(LISTADOC);
		/**
		 * Valida se o item selecionado ja não esta na lista 
		 */
		boolean estaNaLista = false;
		for (Iterator<DocumentoTO> iter  = listaDocumentoAdicionados.iterator(); iter.hasNext();) {
			DocumentoTO elementTemp = (DocumentoTO) iter .next();
			if(elementTemp.getCdDocumento().toString().equals(codDocumento)){
				estaNaLista = true;
				break;
			}
		}
		if(estaNaLista){
			FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Documento já selecionado", null));
			 caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
		}else{
			AgvTabDocumento temp = docService.findById(WrapperUtils.toLong(codDocumento),false);
			listaDocumentoAdicionados.add(temp.parseTO());
			getHttpSession(false).setAttribute(LISTADOC,listaDocumentoAdicionados); 
		}
    }
    
    /**
     * Exclui Documento do SubServiço
     * @param e
     */
    public void excluirDocumento(ActionEvent e){
	    String codDocumento = (String) getRequestParameterMap("codDocumentoParam");
		
	    listaDocumentoAdicionados = getSessionAttribute(LISTADOC);
		DocumentoTO documentoRemove = null;
		
		for (Iterator<DocumentoTO> iter = listaDocumentoAdicionados.iterator(); iter.hasNext();) {
			DocumentoTO docTemp = (DocumentoTO) iter.next();
			if(docTemp.getCdDocumento().toString().equals(codDocumento)){
				documentoRemove = docTemp;
				break;
			}
		}
		if(documentoRemove != null){
			listaDocumentoAdicionados.remove(documentoRemove);
			getHttpSession(false).setAttribute(LISTADOC, listaDocumentoAdicionados);
		}
    }
    
    public boolean validarDatasValidas(){
		
		int totalDiaMes = WrapperUtils.daysInMonth();
		
		if( this.dia.equals("") ){
			
			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dia digitado inválido", null));
	   	    		   
			 return true;
			 
		}else if(  WrapperUtils.toInt(this.dia) > totalDiaMes  ) {

			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dia digitado inválido", null));
	   	    		   
			 return true;
			
		}
		
		if( this.mes.equals("")  ) {

			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mês digitado inválido", null));
	   	    		   
			 return true;
			
		}else if( WrapperUtils.toInt(this.mes) > 12 ){
			
			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mês digitado inválido", null));
	   	    		   
			 return true;			
			
		}
		
		if( this.ano.equals("") ) {

			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ano digitado inválido", null));
	   	    		   
			 return true;
			
		}

    	String dataPublicacao = dia + "/" + mes + "/" + ano;
    	
    	if (WrapperUtils.toDate(dataPublicacao).before(getDataDoDia())) {
    		 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "A data da publicação deve ser igual ou posterior à atual", null));
	   	    		   
			 return true;
		}
    	
    	subServico.setDataPublicacao( WrapperUtils.toDate(dataPublicacao) );
		
		return false;
		
	}	

	 private Date getDataDoDia() {
    	Calendar dataAtual = Calendar.getInstance();
    	dataAtual.setTime(new Date());
    	dataAtual.set(Calendar.HOUR_OF_DAY, 0);
    	dataAtual.set(Calendar.MINUTE, 0);
    	dataAtual.set(Calendar.SECOND, 0);
    	dataAtual.set(Calendar.MILLISECOND, 0);
    	return dataAtual.getTime();
    }

	/**
     * 
     */
	public void afterPropertiesSet() throws Exception {
		atualizar();
/*	   	listaCanalAtendimento  = atendimentoService.findAllSelectedItems();
    	listaDocumentos = docService.findAllSelectedItems();
*/
	}
	

	public String getCodSubServico() {
		return codSubServico;
	}

	public void setCodSubServico(String codSubServico) {
		this.codSubServico = codSubServico;
	}

	public void setSubServicos(ListDataModel subServicos) {
		this.subServicos = subServicos;
	}

	public String getCodCanalAtendimento() {
		return codCanalAtendimento;
	}

	public void setCodCanalAtendimento(String codCanalAtendimento) {
		this.codCanalAtendimento = codCanalAtendimento;
	}

	public String getCodDocumento() {
		return codDocumento;
	}

	public void setCodDocumento(String codDocumento) {
		this.codDocumento = codDocumento;
	}

	public SubServicoTO getSubServico() {
		return subServico;
	}

	public void setSubServico(SubServicoTO subServico) {
		this.subServico = subServico;
	}

	public String getDataAtualizacaoTO() {
		return dataAtualizacaoTO;
	}

	public void setDataAtualizacaoTO(String dataAtualizacaoTO) {
		this.dataAtualizacaoTO = dataAtualizacaoTO;
	}

	public List<SelectItem> getListaCanalAtendimento() {
		return listaCanalAtendimento;
	}

	public void setListaCanalAtendimento(List<SelectItem> listaCanalAtendimento) {
		this.listaCanalAtendimento = listaCanalAtendimento;
	}

	public List<SelectItem> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<SelectItem> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public List<CanalAtendimentoTO> getListaCanalAtendimentoAdicionados() {
		if(getSessionAttribute(LISTACANALATEN) == null){
			getHttpSession(false).setAttribute(LISTACANALATEN, listaCanalAtendimentoAdicionados);
		}else{
			listaCanalAtendimentoAdicionados = getSessionAttribute(LISTACANALATEN);
		}
		return listaCanalAtendimentoAdicionados;
	}

	public Boolean getMostraBotaoExcluir() {
		return mostraBotaoExcluir;
	}

	public void setMostraBotaoExcluir(Boolean mostraBotaoExcluir) {
		this.mostraBotaoExcluir = mostraBotaoExcluir;
	}

	public void setListaCanalAtendimentoAdicionados(
			List<CanalAtendimentoTO> listaCanalAtendimentoAdicionados) {
		this.listaCanalAtendimentoAdicionados = listaCanalAtendimentoAdicionados;
	}

	public List<DocumentoTO> getListaDocumentoAdicionados() {
		if(getSessionAttribute(LISTADOC) == null){
			getHttpSession(false).setAttribute(LISTADOC, listaDocumentoAdicionados);
		}else{
			listaDocumentoAdicionados = getSessionAttribute(LISTADOC);
		}
		return listaDocumentoAdicionados;
	}

	public void setListaDocumentoAdicionados(
			List<DocumentoTO> listaDocumentoAdicionados) {
		this.listaDocumentoAdicionados = listaDocumentoAdicionados;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCaminhoImagemErroSucesso() {
		return caminhoImagemErroSucesso;
	}

	public void setCaminhoImagemErroSucesso(String caminhoImagemErroSucesso) {
		this.caminhoImagemErroSucesso = caminhoImagemErroSucesso;
	}
	
	@SuppressWarnings("unchecked")
	public boolean getExisteMsgErro(){
		Iterator iter = FacesContext.getCurrentInstance().getMessages();
		existeMsgErro = iter.hasNext();
		return existeMsgErro;
	}

	public String getDataHidden() {
		return dataHidden;
	}

	public void setDataHidden(String dataHidden) {
		this.dataHidden = dataHidden;
	}
	
	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}
}
