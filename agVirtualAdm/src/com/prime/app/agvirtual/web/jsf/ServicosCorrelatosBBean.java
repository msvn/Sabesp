package com.prime.app.agvirtual.web.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.AgvTabAutoatendimento;
import com.prime.app.agvirtual.entity.AgvTabCorrelacao;
import com.prime.app.agvirtual.service.AutoAtendimentoService;
import com.prime.app.agvirtual.service.ItemMenuService;
import com.prime.app.agvirtual.service.ServicosCorrelatosService;
import com.prime.app.agvirtual.web.jsf.util.AgvStatus;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.CrudServiceBBean;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

@Component
@Scope(value="session")
public class ServicosCorrelatosBBean extends CrudServiceBBean<AgvTabAutoatendimento, Long> 
implements Serializable , InitializingBean{
	
	private static final Logger agvlogger = LoggerFactory.getLogger(ServicosCorrelatosBBean.class);

	private static final long serialVersionUID = -2924497964816710919L;

	private static final String LISTAFUNCIONALIDADE = "listaFunc";
	
	private static final String LISTAFUNCIONALIDADEREMOVER = "listaFuncRemover";
	
	@Autowired
	ServicosCorrelatosService servicosCorrelatosService;

    @Autowired
    private AutoAtendimentoService autoAtendService;
    
    @Autowired
    private ItemMenuService itemMenuService;
    
    private ListDataModel listaAutoAtendimento;
    
    private String codAutoAtendimento;
    
    private String codFuncionalidade;
    
    private String dsAutoAtendimento;
    
    private AgvTabAutoatendimento autoAtend;
    
    private List<SelectItem> listaFuncionalidades;
    
    private List<AgvTabCorrelacao> listaFuncionalidadesAdicionadas;
    
    private List<AgvTabCorrelacao> listaFuncionalidadesARemover;

	private boolean existeMsgErro;

	private String caminhoImagemErroSucesso = null;
    
    /**
     * Adiciona uma funcionalidade ao objeto Correlação
     * @param e
     */
    public void addFuncionalidade(ActionEvent e){
    	listaFuncionalidadesAdicionadas = getSessionAttribute(LISTAFUNCIONALIDADE);
		/**
		 * Valida se o item selecionado ja não esta na lista 
		 */
		boolean estaNaLista = false;
		for (Iterator iter  = listaFuncionalidadesAdicionadas.iterator(); iter.hasNext();) {
			AgvTabCorrelacao elementTemp = (AgvTabCorrelacao) iter .next();
			if(elementTemp.getAgvTabItemMenuAcesso() != null && 
					elementTemp.getAgvTabItemMenuAcesso().getCdItemMenu().toString().equals(codFuncionalidade)){
				estaNaLista = true;
				break;
			}
		}
		if(estaNaLista){
			
			
			
			
			FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Serviço já relacionado.", null));
			caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
		}else{
//			AgvTabCorrelacao temp = funcionalidadeService.findById(WrapperUtils.toLong(codAutoAtendimento),Long.valueOf(codFuncionalidade) ,false);
			AgvTabCorrelacao temp =  new AgvTabCorrelacao();
				temp =  new AgvTabCorrelacao();
				temp.setAgvTabAutoatendimento(autoAtend);
				temp.setAgvTabItemMenuAcesso(itemMenuService.findById(WrapperUtils.toLong(codFuncionalidade),false));
			listaFuncionalidadesAdicionadas.add(temp);
			getHttpSession(false).setAttribute(LISTAFUNCIONALIDADE, listaFuncionalidadesAdicionadas);
		}
    }
    
    /**
     * Edita um AutoAtendimento
     * @return
     */
    public String edit(){
    	//Carrega lista de funcionalidades
    	listaFuncionalidades = itemMenuService.findAllSelectedItems();
    	//Recupera AutoAtendimento da listagem
    	autoAtend = (AgvTabAutoatendimento) listaAutoAtendimento.getRowData();
    	//recupera lista de Correlação para atributo de tela
    	listaFuncionalidadesAdicionadas = autoAtend.getAgvTabCorrelacaoList();
    	//Zera atributos de controle da sessão
    	getHttpSession(false).setAttribute(LISTAFUNCIONALIDADE, listaFuncionalidadesAdicionadas);
    	getHttpSession(false).setAttribute(LISTAFUNCIONALIDADEREMOVER, new ArrayList());
    	codAutoAtendimento = autoAtend.getCdAutoatendimento().toString();
    	dsAutoAtendimento = autoAtend.getDsAutoatendimento();
    	return AgvStatus.EDIT.name();
    }
    
    
    /**
     * Salva registro de AutoAtendimento , atualizando seus relacionamentos na tabela de Correlação
     * @return
     */
    public String salvar() {
    	
        try {
        	//Carrega Objeto AutoAtendimento
        	autoAtend = autoAtendService.findById(WrapperUtils.toLong(codAutoAtendimento), false);
        	//Seta no Arquivo de Correlação o objeto AutoAtendimento
        	listaFuncionalidadesAdicionadas = getSessionAttribute(LISTAFUNCIONALIDADE);
        	for (Iterator<AgvTabCorrelacao> iterator = listaFuncionalidadesAdicionadas.iterator(); iterator.hasNext();) {
				AgvTabCorrelacao element = (AgvTabCorrelacao) iterator.next();
				element.setAgvTabAutoatendimento(autoAtend);
			}
        	//Recupera objetos que foram removidos pelo usuário e que ainda não foi refletido no banco
        	listaFuncionalidadesARemover = ((List<AgvTabCorrelacao>)getSessionAttribute(LISTAFUNCIONALIDADEREMOVER));
        	autoAtend.setAgvTabCorrelacaoList(listaFuncionalidadesAdicionadas);
        	//Salva as alterações
        	autoAtendService.save(autoAtend,listaFuncionalidadesARemover);
            agvlogger.info("Registro  com Nome "+ autoAtend.getDsAutoatendimento() +"foi alterado pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
            atualizar();
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "O autoatendimento "+ autoAtend.getDsAutoatendimento() +" foi atualizado.", null));
            caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
            return AgvStatus.ATUALIZA.name();
        }
        catch (Exception e) {
        	agvlogger.error(e.getMessage());
            e.printStackTrace(); //TODO retirar
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Alterar AutoAtendimento", null));
            caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    		return AgvStatus.EDIT.name();
        }
    }

    /**
     * Remove serviço correlato ao AutoAtendimento
     * Seta em um atributo de sessão quais objetos foram deletados pelo usuário para depois ser refletido 
     * na base de dados
     * @param e
     */
	public void removeFuncionalidade(ActionEvent e){
    	 FacesContext context = FacesContext.getCurrentInstance();
         Map map = context.getExternalContext().getRequestParameterMap();
         String codServParam = (String) map.get("cdFuncionalidade");
     	
         listaFuncionalidadesAdicionadas = getSessionAttribute(LISTAFUNCIONALIDADE);
         AgvTabCorrelacao servico = null;
 		
 		for (Iterator<AgvTabCorrelacao> iter = listaFuncionalidadesAdicionadas.iterator(); iter.hasNext();) {
 			AgvTabCorrelacao element = (AgvTabCorrelacao) iter.next();
 			if(element.getAgvTabItemMenuAcesso() != null &&  
 					element.getAgvTabItemMenuAcesso().getCdItemMenu().toString().equals(codServParam)){
 				servico = element;
 				break;
 			}
 		}
 		((List<AgvTabCorrelacao>)getSessionAttribute(LISTAFUNCIONALIDADEREMOVER)).add(servico);
 		
 		if(servico != null){
 			listaFuncionalidadesAdicionadas.remove(servico);
 			getHttpSession(false).setAttribute(LISTAFUNCIONALIDADE, listaFuncionalidadesAdicionadas);
 		}
    }
	
	/**
	 * Atualiza a listagem 
	 * @return
	 */
	public String atualizar(){
		this.listaAutoAtendimento = new ListDataModel( servicosCorrelatosService.findAll() );
        return AgvStatus.ATUALIZA.name();
	}

	public void afterPropertiesSet() throws Exception {
		listaAutoAtendimento = new ListDataModel( servicosCorrelatosService.findAll() );
		listaFuncionalidades = itemMenuService.findAllSelectedItems();
	}

	public String getCodAutoAtendimento() {
		return codAutoAtendimento;
	}

	public void setCodAutoAtendimento(String codAutoAtendimento) {
		this.codAutoAtendimento = codAutoAtendimento;
	}

	/**
	 * Recupera sempre o objeto que esta na sessão , caso não exista o cria
	 * @return
	 */
	public List<AgvTabCorrelacao> getListaFuncionalidadesAdicionadas() {
		if(getSessionAttribute(LISTAFUNCIONALIDADE) == null){
			getHttpSession(false).setAttribute(LISTAFUNCIONALIDADE, new ArrayList());
		}else{
			listaFuncionalidadesAdicionadas = getSessionAttribute(LISTAFUNCIONALIDADE);
		}
		return listaFuncionalidadesAdicionadas;
	}

	public void setListaFuncionalidadesAdicionadas(
			List<AgvTabCorrelacao> listaFuncionalidadesAdicionadas) {
		this.listaFuncionalidadesAdicionadas = listaFuncionalidadesAdicionadas;
	}

	public ListDataModel getListaAutoAtendimento() {
		return listaAutoAtendimento;
	}

	public void setListaAutoAtendimento(ListDataModel listaAutoAtendimento) {
		this.listaAutoAtendimento = listaAutoAtendimento;
	}

	public AgvTabAutoatendimento getAutoAtend() {
		return autoAtend;
	}

	public void setAutoAtend(AgvTabAutoatendimento autoAtend) {
		this.autoAtend = autoAtend;
	}

	public List<SelectItem> getListaFuncionalidades() {
		return listaFuncionalidades;
	}

	public void setListaFuncionalidades(List<SelectItem> listaFuncionalidades) {
		this.listaFuncionalidades = listaFuncionalidades;
	}

	public String getDsAutoAtendimento() {
		return dsAutoAtendimento;
	}

	public void setDsAutoAtendimento(String dsAutoAtendimento) {
		this.dsAutoAtendimento = dsAutoAtendimento;
	}

	public String getCodFuncionalidade() {
		return codFuncionalidade;
	}

	public void setCodFuncionalidade(String codFuncionalidade) {
		this.codFuncionalidade = codFuncionalidade;
	}
	
	public boolean getExisteMsgErro(){
		Iterator iter = FacesContext.getCurrentInstance().getMessages();
		existeMsgErro = iter.hasNext();
		return existeMsgErro;
	}

	public String getCaminhoImagemErroSucesso() {
		return caminhoImagemErroSucesso ;
	}

	public void setCaminhoImagemErroSucesso(String caminhoImagemErroSucesso) {
		this.caminhoImagemErroSucesso = caminhoImagemErroSucesso;
	}

	public void setExisteMsgErro(boolean existeMsgErro) {
		this.existeMsgErro = existeMsgErro;
	}
}
