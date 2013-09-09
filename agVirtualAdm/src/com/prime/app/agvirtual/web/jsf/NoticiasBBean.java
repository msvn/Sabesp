package com.prime.app.agvirtual.web.jsf;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import com.prime.app.agvirtual.entity.AgvTabNoticia;
import com.prime.app.agvirtual.file.InputFileData;
import com.prime.app.agvirtual.service.NoticiaService;
import com.prime.app.agvirtual.to.NoticiaTO;
import com.prime.app.agvirtual.web.jsf.util.AgvStatus;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.CrudServiceBBean;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * BackBean com controles da classe Notícia
 */
@Component
@Scope(value="session")
public class NoticiasBBean extends CrudServiceBBean<AgvTabNoticia, Long>
implements Serializable , InitializingBean{ 
	
	private String dataHidden;
	
	private String dia;
	
	private String mes;
	
	private String ano;
	
	SimpleDateFormat dateFormat =  new SimpleDateFormat("dd/MM/yyyy");

	private static final long serialVersionUID = -2924497964816710919L;
	
	private static final Logger agvlogger = LoggerFactory.getLogger(NoticiasBBean.class);

    @Autowired
    private NoticiaService noticiaService;
    
    private String uploadSuccess = "";
    
    private String caminhoImagemErroSucesso= null;

	/**
	 * Lista de objetos noticias
	 */
    private ListDataModel noticias =  new ListDataModel();
    
    /**
     * Objeto noticia utilizado para editar
     */
    private NoticiaTO noticia;
    
    private Date dataPublicacao;
    
	private String novo;
	
	private String titulo;
	
	private boolean mostraBotaoExcluir =  false;
	
	//Atributos utilizados pelo componente de Upload
	// File sizes used to generate formatted label
    public static final long MEGABYTE_LENGTH_BYTES = 1048000l;
    public static final long KILOBYTE_LENGTH_BYTES = 1024l;

    // files associated with the current user
    private List fileList = Collections.synchronizedList(new ArrayList());
    // latest file uploaded by client
    private InputFileData currentFile;
    // file upload completed percent (Progress)
    private int fileProgress;

    private boolean autoUpload = false;

	private String value;

	private boolean existeMsgErro;
	

    /**
     * Retorna listagem de Notícias
     * @return
     */
    public ListDataModel getNoticias() {
        return noticias;
    }
    
    /**
     * Carrega dados do registro para edição
     * @return
     */
    public String edit() {
    	
    	this.titulo = "Alterar Notícia";
    	
    	noticia = (NoticiaTO) noticias.getRowData();
    	
    	this.dataHidden = WrapperUtils.parseDate(this.noticia.getDataPublicacao());
    	
		dia = dataHidden.split("/")[0];
		mes = dataHidden.split("/")[1];
		ano = dataHidden.split("/")[2];
    	
    	noticia.setSubTitulo(WrapperUtils.replaceHTML(noticia.getSubTitulo()));
    	
    	mostraBotaoExcluir = true;
    	fileProgress = 0;
    	uploadSuccess = noticia.getCaminhoImagem();
    	noticia.setRealName(recuperaNomeArquivo(noticia.getCaminhoImagem()));
        return AgvStatus.EDIT.name();
    }
    
    private String recuperaNomeArquivo(String caminho) {
    	if(caminho != null){
			String t  =  caminho;
			int temp = 0;
			int tempFinal = 0 ;
			while(true){
				temp = t.indexOf("\\",temp+1);
				if(temp == -1){
					return t.substring(tempFinal+1);
				}
				tempFinal = temp;
			}
    	}
    	return "";
	}
    
    public String carregar() {
    	this.noticias = new ListDataModel(noticiaService.findAll());
    	return "NOTICIA";
    }
    
    /**
     * Carrega dados do registro para edição
     * @return
     */
    public String excluir() {
    	if(noticia.getCdNoticia() == null){
        	FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Não é possível excluir a notícia", null));
        	caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    	}else{
    		remove(noticia.getCdNoticia());
    		agvlogger.info("Registro "+noticia.getCdNoticia()+" Notícia foi apagada pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
        	
    		FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "A notícia "+ noticia.getTitulo() +" foi excluída.", null));
    		caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
    	}
    	
    	atualizar();
        return AgvStatus.REMOVE.name();
    }
    
    
    /**
     * Carrega formulário para nova notícia
     * @return
     */
    public String novaNoticia() {
    	
    	this.titulo = "Incluir Notícia";
    	
    	noticia = new NoticiaTO("", "", "", "", "");
    	
    	this.dataHidden = WrapperUtils.parseDate(WrapperUtils.getToday());
    	
    	caminhoImagemErroSucesso = null;

    	mostraBotaoExcluir = false;
    	setSessionAttribute("filePath","");
    	fileProgress = 0;
    	uploadSuccess = "";
        return AgvStatus.EDIT.name();
    }
    
    /**
     * Salva registro
     * @return
     */
    public String salvar() {
    	
    	
    	noticia.setSubTitulo(WrapperUtils.replaceHTML(noticia.getSubTitulo()));
    	
    	boolean flag = validarCampos(noticia);
    	if(flag){
    		caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    		return AgvStatus.EDIT.name();
    	}
        try {
        	
        	AgvTabNoticia noticiaModel = noticia.toEntity();
        	
        	if( noticiaModel.getCdNoticia() == null ) {
        		// save
        		noticia.setDataUsuarioCriacao(new Date());
        		
                save(noticia.toEntity());
                agvlogger.info("Registro da Notícia com Título "+ noticia.getTitulo()+" foi criado pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
                atualizar();
                FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "A notícia " + noticia.getTitulo() + " foi salva", null));
                caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
        		
        	}else {
        		// update
        		noticia.setDataUsuarioAlteracao(new Date());
        		
                save(noticia.toEntity());
                agvlogger.info("Registro da Notícia com Título "+ noticia.getTitulo()+" foi criado pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
                atualizar();
                FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "A notícia " + noticia.getTitulo() + " foi alterada", null));
                caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
        	}
        	

            return AgvStatus.ATUALIZA.name();
        }
        catch (Exception e) {
        	agvlogger.error(e.getMessage());
            e.printStackTrace(); //TODO retirar
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao incluir notícia", null));
            caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    		return AgvStatus.EDIT.name();
        }
    }
    
    /*
     * Regras de Negócio para validaçãod dos campos.
     */
    private boolean validarCampos(NoticiaTO noticia) {
    	boolean flag = false;
    	
    	handleTrimFields();
   	 
    	
    	this.dataHidden = WrapperUtils.parseDate(this.noticia.getDataPublicacao());
    	if( noticia.getTitulo() == null || noticia.getTitulo().trim().equals("") ){
			FacesContext.getCurrentInstance().addMessage(null,
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Título não preenchido", null));
					flag = true;
    	}else if(noticia.getTitulo().length() > 60){
			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Título deve conter até 60 caracteres.", null));
	   	    		 flag = true;  
    		
    	}
    	
    	if( noticia.getSubTitulo() == null || noticia.getSubTitulo().trim().equals("") ){
   		 	FacesContext.getCurrentInstance().addMessage(null,
   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Subtítulo não preenchido.", null));
   	    		 flag = true;

    	}else if(noticia.getSubTitulo().length() > 200){
			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "SubTítulo deve conter até 200 caracteres.", null));
	   	    		 flag = true;  
		}
    	
    	String[] row = noticia.getSubTitulo().split("\\s") ;
    	
    	if(row.length == 1 && noticia.getSubTitulo().length() > 29 ){
			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "SubTítulo: Insira espaços entre as palavras.", null));
	   	    		 flag = true;  
    	}
    	
		if( validarDatasValidas() ){
			flag = true;
		}
   	


    		
		Date myDate = (Date)noticia.getDataPublicacao();
		Date today =  WrapperUtils.getToday();

		if (WrapperUtils.addDaysToDate(myDate,1).before(today)) {
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "A data não pode ser anterior à data atual", null));
   		 	flag = true;
		}    		
    		
    	
    	
    	if( noticia.getTextoNoticia() == null || noticia.getTextoNoticia().trim().equals("") ){
	   		 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Texto não preenchido", null));
	   	    		 flag = true;    		
    	}else{
    		if(noticia.getTextoNoticia().length() > 4000){
    			 FacesContext.getCurrentInstance().addMessage(null,
    	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Texto deve conter até 4000 caracteres.", null));
    	   	    		 flag = true;  
    		}
    	}
    	

    	if(flag){
    		caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    	}
		return flag;
	}

	/**
     * Atualiza tela de noícias com nova busca
     * @return
     */
    public String atualizar() {	
		
    	this.noticias = new ListDataModel(noticiaService.findAll());
        return AgvStatus.ATUALIZA.name();
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
            currentFile = new InputFileData(fileInfo);
            noticia.setCaminhoImagem(fileInfo.getFile().getName()); // So grava o ultimo caminho
            noticia.setRealName(fileInfo.getFile().getName()); // So grava o ultimo caminho
            uploadSuccess = fileInfo.getFile().getPath();
//          getHttpSession(false).setAttribute("listaSubServicoAdicionados", getListaSubServicoAdicionados());
            synchronized (fileList) {
                fileList.add(currentFile);
//                FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_INFO, "O Arquivo foi carregado.", null));
                caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
            }
        }
    }
    

	public void uploadActionListener(ActionEvent actionEvent) {
        InputFile inputFile = (InputFile) actionEvent.getSource();
        currentFile = new InputFileData(inputFile.getFileInfo());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "O Arquivo foi carregado!", null));
        caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
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

     
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public InputFileData getCurrentFile() {
        return currentFile;
    }

    public int getFileProgress() {
        return fileProgress;
    }

    public List getFileList() {
        return fileList;
    }

	public boolean isAutoUpload() {
        return autoUpload;
    }

    public void setAutoUpload(boolean autoUpload) {
        this.autoUpload = autoUpload;
    }
    
	public void afterPropertiesSet() throws Exception {
		if(!noticias.isRowAvailable()){
    		this.noticias = new ListDataModel(noticiaService.findAll());
    	}
//		uploadSuccess = getSessionAttribute("filePath");
	}


	public NoticiaTO getNoticia() {
		return noticia;
	}

	public void setNoticias(ListDataModel noticias) {
		this.noticias = noticias;
	}

	public void setNoticia(NoticiaTO noticia) {
		this.noticia = noticia;
	}

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	
	public String getNovo() {
		return novo;
	}

	public void setNovo(String novo) {
		this.novo = novo;
	}

	public void setCurrentFile(InputFileData currentFile) {
		this.currentFile = currentFile;
	}

	public void setFileProgress(int fileProgress) {
		this.fileProgress = fileProgress;
	}

	public String getUploadSuccess() {
		return uploadSuccess;
	}

	public void setUploadSuccess(String uploadSuccess) {
		this.uploadSuccess = uploadSuccess;
	}

	public boolean isNovaNoticia() {
		return mostraBotaoExcluir;
	}

	public void setNovaNoticia(boolean novaNoticia) {
		this.mostraBotaoExcluir = novaNoticia;
	}

	public boolean isMostraBotaoExcluir() {
		return mostraBotaoExcluir;
	}

	public void setMostraBotaoExcluir(boolean mostraBotaoExcluir) {
		this.mostraBotaoExcluir = mostraBotaoExcluir;
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
	
	public void setExisteMsgErro(boolean existeMsgErro) {
		this.existeMsgErro = existeMsgErro;
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
    	
    	noticia.setDataPublicacao(WrapperUtils.toDate(dataPublicacao));
		
		return false;
		
	}
	
	private void handleTrimFields(){
		
		this.dia = ( dia != null ? dia.trim() : "" );
		this.mes = ( mes != null ? mes.trim() : "" );
		this.ano = ( ano != null ? ano.trim() : "" );
			
	}

	
	
}
