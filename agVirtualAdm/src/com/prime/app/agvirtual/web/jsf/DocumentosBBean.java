package com.prime.app.agvirtual.web.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.EventObject;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;
import com.prime.app.agvirtual.entity.AgvTabDocumento;
import com.prime.app.agvirtual.enums.TipoDocumento;
import com.prime.app.agvirtual.enums.TipoPessoa;
import com.prime.app.agvirtual.file.InputFileData;
import com.prime.app.agvirtual.service.DocumentoService;
import com.prime.app.agvirtual.to.DocumentoTO;
import com.prime.app.agvirtual.web.jsf.util.AgvStatus;
import com.prime.infra.web.jsf.CrudServiceBBean;

@Component
//@Scope("request")
/**
 * BackBean com controles da tela de Documentos
 */
public class DocumentosBBean extends CrudServiceBBean<AgvTabDocumento, Long> implements Serializable , InitializingBean{

	
	protected Effect valueChangeEffect;
	
	public DocumentosBBean() {
        valueChangeEffect = new Highlight("#fda505");
        valueChangeEffect.setFired(true);
}
    /**
	 * 
	 */
	private static final long serialVersionUID = -2924497964816710919L;
	
	private static final Logger agvlogger = LoggerFactory.getLogger(DocumentosBBean.class);

    @Autowired
    private DocumentoService documentoService;

	/**
	 * Lista de objetos noticias
	 */
    private ListDataModel documentos =  new ListDataModel();
    
    /**
     * Objeto documento utilizado para editar
     */
    private DocumentoTO documento =  new DocumentoTO();
    
    private String tipoDocumento = ""; // getRequestAttribute("tipoDocumento");
    
    //Atributo de controle , Link true / Doc false
    private Boolean flagLinkDoc = true;
    
    private List listTipoPessoa = TipoPessoa.getListSelectItem();
    
    private List listTipoDocumento = TipoDocumento.getListSelectItem();
    
    //Atributos utilizados pelo componente de Upload
	// File sizes used to generate formatted label
    public static final long MEGABYTE_LENGTH_BYTES = 1048000l;
    
    public static final long KILOBYTE_LENGTH_BYTES = 1024l;

    // latest file uploaded by client
    private InputFileData currentFile;
    // file upload completed percent (Progress)
    private int fileProgress;
    
    private String nomeArquivoUpload = "";

    private boolean autoUpload = false;
    
    //controle do botao excluir
    private Boolean mostraBotaoExcluir = false;

    
    /**
     * Retorna listagem de Notícias
     * @return
     */
    public ListDataModel getDocumentos() {
        return documentos;
    }
    
    /**
     * Carrega dados do registro para edição
     * @return
     */
    public String edit() {
    	documento = (DocumentoTO) documentos.getRowData();
    	if(documento.getTipoDocumento().equals(String.valueOf(TipoDocumento.LINK.ordinal()))){
    		flagLinkDoc = true;
    		this.tipoDocumento =  String.valueOf(TipoDocumento.LINK.ordinal());
    	}else{
    		flagLinkDoc = false;
    		this.tipoDocumento =  String.valueOf(TipoDocumento.DOCUMENTO.ordinal());
    	}
    	//Limpa lista de arquivos
//    	nomeArquivoUpload;
    	
    	if(documento.getNmFisicoDocumento() != null){
			recuperaNomeArquivo();
    	}
		
    	fileProgress = 0;
    	mostraBotaoExcluir =true;
        return AgvStatus.EDIT.name();
    }

	private void recuperaNomeArquivo() {
		String t  =  documento.getNmFisicoDocumento();
		int temp = 0;
		int tempFinal = 0 ;
		while(true){
			temp = t.indexOf("\\",temp+1);
			if(temp == -1){
				System.out.println(tempFinal);
				documento.setRealName(t.substring(tempFinal+1));
				break;
			}
			tempFinal = temp;
		}
	}
    
    /**
     * Carrega dados do registro para edição
     * @return
     */
    public String excluir() {
    	if(documento.getCdDocumento() == null){
        	FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Não é possível excluir o registro", null));
    		
    	}else{
    		remove(documento.getCdDocumento());
    		
    		//Deleta Arquivo do servidor se este existir
    		if(documento.getNmFisicoDocumento() != null){
    			String docExcluir = documento.getNmFisicoDocumento();
    			File f=  new File(docExcluir);
    			f.delete();
    		}
    		
    		agvlogger.info("Registro "+documento.getCdDocumento()+" Documento foi apagado pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
        	FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "O Registro foi excluído com sucesso", null));
    	}
    	
    	atualizar();
        return AgvStatus.REMOVE.name();
    }
    
    
    /**
     * Carrega formulário para novo Documento
     * @return
     */
    public String novoDocumento() {
    	documento = new DocumentoTO();
    	fileProgress = 0;
    	documento.setTipoDocumento(String.valueOf(TipoDocumento.LINK.ordinal()));
    	flagLinkDoc = true;
    	documento.setTipoPessoa(String.valueOf(TipoPessoa.FISICA.ordinal()));
    	mostraBotaoExcluir =false;
        return AgvStatus.EDIT.name();
    }
    
    public String tipoChanged(ValueChangeEvent e) {
    	tipoDocumento = String.valueOf(e.getNewValue());
    	if(tipoDocumento.equals(String.valueOf(TipoDocumento.LINK.ordinal()))){
    		flagLinkDoc = true;
    		documento.setTipoDocumento(TipoDocumento.LINK.name());
    	}else{
    		flagLinkDoc = false;
    		documento.setTipoDocumento(TipoDocumento.DOCUMENTO.name());
    	}
    	return AgvStatus.EDIT.name();
    }
    
    /**
     * Salva registro
     * @return
     */
    public String salvar() {
    	boolean flag = validarCampos(documento);
    	if(flag){
    		return AgvStatus.EDIT.name();
    	}
        try {
            save(documento.toEntity());
            agvlogger.info("Registro do Documento com Título "+ documento.getNmDocumento()+"foi criado pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
            atualizar();
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "O Documento foi alterado", null));
            return AgvStatus.ATUALIZA.name();
        }
        catch (Exception e) {
        	agvlogger.error(e.getMessage());
            e.printStackTrace(); //TODO retirar
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao incluir Documento", null));
    		return AgvStatus.EDIT.name();
        }
    }
    
    /**
     * Atualiza tela de documentos com nova busca
     * @return
     */
    public String atualizar() {
    	this.documentos = new ListDataModel(documentoService.findAll()); //TODO colocar em properties
        return AgvStatus.ATUALIZA.name();
    }

    /**
     * Métodos com regras de validação
     * @param documento2
     * @return
     */
	private boolean validarCampos(DocumentoTO documento) {
		if(documento.getTipoDocumento().equals(String.valueOf(TipoDocumento.LINK.ordinal()))){
			if(documento.getDsLink() == null || documento.getDsLink().equals("")){
				FacesContext.getCurrentInstance().addMessage("documento:dsLink",
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Necessário descrever o Link", null));
				return true;
			}
		}else{
			if(documento.getNmFisicoDocumento() == null || documento.getNmFisicoDocumento().equals("")){
				FacesContext.getCurrentInstance().addMessage("documento:nmFisicoDocumento",
			            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Necessário selecionar um Documento", null));
				return true;
			}
		}
		
		return false;
	}

	public DocumentoTO getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoTO documento) {
		this.documento = documento;
	}

	public void afterPropertiesSet() throws Exception {
		if(documentos == null || !documentos.isRowAvailable()){
    		this.documentos = new ListDataModel(documentoService.findAll()); //TODO passar para properties
    	}		
	}

	public List getListTipoPessoa() {
		return listTipoPessoa;
	}

	public void setListTipoPessoa(List listTipoPessoa) {
		this.listTipoPessoa = listTipoPessoa;
	}

	public List getListTipoDocumento() {
		return listTipoDocumento;
	}

	public void setListTipoDocumento(List listTipoDocumento) {
		this.listTipoDocumento = listTipoDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Boolean getFlagLinkDoc() {
		return flagLinkDoc;
	}

	public void setFlagLinkDoc(Boolean flagLinkDoc) {
		this.flagLinkDoc = flagLinkDoc;
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
            currentFile = new InputFileData(fileInfo);
            
            	//Delete arquivo antigo 
            	if(documento.getNmFisicoDocumento() != null){
        			String docExcluir = documento.getNmFisicoDocumento();
        			File f=  new File(docExcluir);
        			f.delete();
        		}
            	documento.setNmFisicoDocumento(fileInfo.getFile().getPath());
            	documento.setRealName(fileInfo.getFile().getName());
            	nomeArquivoUpload = fileInfo.getFile().getName();
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

	public Effect getValueChangeEffect() {
		return valueChangeEffect;
	}

	public void setValueChangeEffect(Effect valueChangeEffect) {
		this.valueChangeEffect = valueChangeEffect;
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

	public boolean isAutoUpload() {
		return autoUpload;
	}

	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}

	public void setDocumentos(ListDataModel documentos) {
		this.documentos = documentos;
	}
    
	public static void main(String[] args) {
		String t  =  "F:\\Develop\\eclipse\\Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\agvirtual\\upload\\PÃ´r-do-sol.jpg";
		int temp = 0;
		int tempFinal = 0 ;
		while(true){
			temp = t.indexOf("\\",temp+1);
			if(temp == -1){
				System.out.println(tempFinal);
				System.out.println(t.substring(tempFinal+1));
				break;
			}
			tempFinal = temp;
		}
	}

	public Boolean getMostraBotaoExcluir() {
		return mostraBotaoExcluir;
	}

	public void setMostraBotaoExcluir(Boolean mostraBotaoExcluir) {
		this.mostraBotaoExcluir = mostraBotaoExcluir;
	}
	
	
}
