package com.prime.app.agvirtual.web.jsf;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.AgvTabCanalAtend;
import com.prime.app.agvirtual.enums.Diretoria;
import com.prime.app.agvirtual.service.CanaisAtendimentoService;
import com.prime.app.agvirtual.to.CanalAtendimentoTO;
import com.prime.app.agvirtual.web.jsf.util.AgvStatus;
import com.prime.infra.web.jsf.CrudServiceBBean;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

@Component
@Scope(value="session")
public class CanaisAtendimentoBBean extends CrudServiceBBean<AgvTabCanalAtend, Long>
implements Serializable , InitializingBean{

	private static final long serialVersionUID = -2924497964816710919L;
	
	private static final Logger agvlogger = LoggerFactory.getLogger(CanaisAtendimentoBBean.class);

    @Autowired
    private CanaisAtendimentoService canaisAtendimentoService;

	/**
	 * Lista de objetos Canal Atendimento
	 */
    private ListDataModel canaisAtendimento;
    
    private CanalAtendimentoTO canalAtendimento =  new CanalAtendimentoTO();
    
    private List<SelectItem> listDiretorias =  Diretoria.getListSelectItem();
    
    private String codDiretoria;
    
    //controle do botao excluir
    private Boolean mostraBotaoExcluir = false;
    
    public ListDataModel getCanaisAtendimento() {
    	
        return canaisAtendimento;
    }
    
    /**
     * Carrega dados do registro para edição
     * @return
     */
    public String edit() {
    	canalAtendimento = (CanalAtendimentoTO) canaisAtendimento.getRowData();
    	setCodDiretoria(canalAtendimento.getCodDiretoria());
    	mostraBotaoExcluir = true;
        return AgvStatus.EDIT.name();
    }
    
    /**
     * Excluir registro
     * @return
     */
    public String excluir() {
    	if(canalAtendimento.getCdCanalAtend() == null){
        	FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Não é possível excluir o registro", null));
    		
    	}else{
    		remove(canalAtendimento.getCdCanalAtend());
    		agvlogger.info("Registro "+canalAtendimento.getCdCanalAtend()+" Registro foi apagado pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
        	FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "O Registro foi excluído com sucesso", null));
    	}
    	    	
        return AgvStatus.REMOVE.name();
    }
    
    
    /**
     * Carrega formulário para novo Canal Atendimento
     * @return
     */
    public String novoCanalAtendimento() {
    	canalAtendimento = new CanalAtendimentoTO();
    	mostraBotaoExcluir = false;
        return AgvStatus.EDIT.name();
    }
    
    /**
     * Salva registro
     * @return
     */
    public String salvar() {
    	boolean flag = validarCampos(canalAtendimento);
    	if(flag){
    		return AgvStatus.EDIT.name();
    	}
        try {
        	canalAtendimento.setCodDiretoria(codDiretoria);
            save(canalAtendimento.toEntity());
            agvlogger.info("Registro com nome "+ canalAtendimento.getNmCanalAtend()+"foi criado pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
            atualizar();
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "O Canal de Atendimento foi alterado", null));
            return AgvStatus.ATUALIZA.name();
        }
        catch (Exception e) {
        	agvlogger.error(e.getMessage());
            e.printStackTrace(); //TODO retirar
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao incluir Canal de Atendimento", null));
    		return AgvStatus.EDIT.name();
        }
    }
    
    /**
     * Valida regras de negócio
     * @param canalAtendimento
     * @return
     */
    private boolean validarCampos(CanalAtendimentoTO canalAtendimento) {
    	if(codDiretoria == null || codDiretoria.equals("0")){
    		FacesContext.getCurrentInstance().addMessage("canal:codDiretoria",
    			    new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("obrigatorio.diretorio"), null));
    		return true;
    	}
		return false;
	}

    /**
     * Atualiza Listagem de Canais de Atendimento
     * @return
     */
	public String atualizar(){
    	canaisAtendimento = new ListDataModel(canaisAtendimentoService.findAll()); //TODO colocar em properties
    	return AgvStatus.ATUALIZA.name();
    }

	public CanalAtendimentoTO getCanalAtendimento() {
		return canalAtendimento;
	}

	public void setCanalAtendimento(CanalAtendimentoTO canalAtendimento) {
		this.canalAtendimento = canalAtendimento;
	}

	public void setCanaisAtendimento(ListDataModel canaisAtendimento) {
		this.canaisAtendimento = canaisAtendimento;
	}

	public List<SelectItem> getListDiretorias() {
		return listDiretorias;
	}

	public void setListDiretorias(List<SelectItem> listDiretorias) {
		this.listDiretorias = listDiretorias;
	}

	public String getCodDiretoria() {
		return codDiretoria;
	}

	public void setCodDiretoria(String codDiretoria) {
		this.codDiretoria = codDiretoria;
	}

	public void afterPropertiesSet() throws Exception {
		if(canaisAtendimento ==  null || !canaisAtendimento.isRowAvailable()){
    		this.canaisAtendimento = new ListDataModel(canaisAtendimentoService.findAll()); //TODO passar para properties
    	}
		
	}

	public Boolean getMostraBotaoExcluir() {
		return mostraBotaoExcluir;
	}

	public void setMostraBotaoExcluir(Boolean mostraBotaoExcluir) {
		this.mostraBotaoExcluir = mostraBotaoExcluir;
	}
	
	
	
}
