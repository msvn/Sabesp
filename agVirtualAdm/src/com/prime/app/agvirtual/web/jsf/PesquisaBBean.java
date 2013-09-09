package com.prime.app.agvirtual.web.jsf;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.prime.app.agvirtual.entity.AgvTabPerguntaPesquisa;
import com.prime.app.agvirtual.entity.AgvTabPesquisa;
import com.prime.app.agvirtual.entity.AgvTabRespPesquisa;
import com.prime.app.agvirtual.enums.TipoPesquisa;
import com.prime.app.agvirtual.service.AutoAtendimentoService;
import com.prime.app.agvirtual.service.PesquisaService;
import com.prime.app.agvirtual.to.LoginTO;
import com.prime.app.agvirtual.web.jsf.util.AgvStatus;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.CrudServiceBBean;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

@Component
@Scope(value="session")
public class PesquisaBBean extends CrudServiceBBean<AgvTabPesquisa, Long> 
implements Serializable , InitializingBean{
	
	private String dataHidden;
	
	private String dataHiddenFim;
	
	private String dia;
	
	private String mes;
	
	private String ano;
	
	private String diaFim;
	
	private String mesFim;
	
	private String anoFim;
	
	private static final Logger agvlogger = LoggerFactory.getLogger(PesquisaBBean.class);

	private static final long serialVersionUID = -2924497964816710919L;
	
	private boolean showPanelRepostas;
	
	private boolean showPanelRepostasMessage;

	@Autowired
	private PesquisaService pesquisaService;
	
	@Autowired
    private AutoAtendimentoService autoAtendService;
	
    private ListDataModel listaPesquisas;    

    private AgvTabPesquisa pesquisaTO = new AgvTabPesquisa();
    
    private AgvTabPesquisa pesquisaClone;
    
    private AgvTabPerguntaPesquisa perguntaTO = new AgvTabPerguntaPesquisa();
    
    private AgvTabPerguntaPesquisa perguntaSelecionadaView = new AgvTabPerguntaPesquisa();
    
    private AgvTabRespPesquisa respostaTO = new AgvTabRespPesquisa();
    
    private TipoPesquisa tipoPesquisa;
    
    private List<SelectItem> listaAutoatendimentos;
    
    private boolean encerraPesquisa = false;

	private boolean existeMsgErro;
	
	private boolean editarPesquisa;
	
	private String caminhoImagemErroSucesso = null;
    
    /**
     * Carrega formulário para nova Pesquisa.
     * @return String
     */
    public String novaPesquisa() {
    	this.showPanelRepostas = false;
    	this.showPanelRepostasMessage = true;
    	this.editarPesquisa = false;
    	this.pesquisaTO = new AgvTabPesquisa();
    	this.pesquisaTO.setAgvTabAutoatendimento(null);
    	
    	//pesquisaTO.setDtIniVigencia(WrapperUtils.getToday());
    	this.dataHidden = WrapperUtils.parseDate(WrapperUtils.getToday());
    	this.dataHiddenFim = WrapperUtils.parseDate(WrapperUtils.getToday());
    	
    	this.perguntaSelecionadaView = new AgvTabPerguntaPesquisa();
    	this.encerraPesquisa = false;
    	pesquisaTO.setTpPesquisa(1);
    	
        return AgvStatus.EDIT.name();
    }

//    /**
//     * Limpa os dados da página.
//     * @return String
//     */
//    public String limparCampos() {
//        return novaPesquisa();
//    }
    
    /**
     * Edita uma Pesquisa.
     * @return String
     */
    public String edit(){
    	this.showPanelRepostasMessage = true;
    	this.showPanelRepostas = false;
    	this.editarPesquisa = true;
    	this.encerraPesquisa = false;
    	pesquisaTO = (AgvTabPesquisa) listaPesquisas.getRowData();
    	
    	this.perguntaSelecionadaView = new AgvTabPerguntaPesquisa();
    	
    	pesquisaTO = get(pesquisaTO.getCdPesquisa());
    	
    	this.dataHidden = WrapperUtils.parseDate(this.pesquisaTO.getDtIniVigencia());
    	
    	this.dataHiddenFim = WrapperUtils.parseDate(this.pesquisaTO.getDtFimVigencia());
    	
		dia = dataHidden.split("/")[0];
		mes = dataHidden.split("/")[1];
		ano = dataHidden.split("/")[2];
		
		diaFim = dataHiddenFim.split("/")[0];
		mesFim = dataHiddenFim.split("/")[1];
		anoFim = dataHiddenFim.split("/")[2];
	
		pesquisaClone = get(pesquisaTO.getCdPesquisa());
    	
    	return AgvStatus.EDIT.name();
    }    
    

    
    /**
     * Adiciona uma pergunta ao objeto Pesquisa.
     * @param e ActionEvent
     */
    public void addPergunta(ActionEvent e) {
    	boolean estaNaLista = false;
    	if (!"".equals(this.perguntaTO.getDsPergunta().trim())) {
    		for (AgvTabPerguntaPesquisa pergunta : this.pesquisaTO.getPerguntaList()) {
				if (pergunta.getDsPergunta().equalsIgnoreCase(this.perguntaTO.getDsPergunta().trim())) {
					
					 String dsPergunta = this.perguntaTO.getDsPergunta().trim();
					
					this.perguntaTO.setDsPergunta( dsPergunta );
					
					FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pergunta já existente!", null));
						caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
					estaNaLista = true;
					break;
				}
			}
    		if (!estaNaLista) {
    			this.pesquisaTO.bidirecionalInclusao(this.perguntaTO);
    			this.perguntaTO = new AgvTabPerguntaPesquisa();
    		}
    	} else {
    		
			 String dsPergunta = this.perguntaTO.getDsPergunta().trim();
				
			this.perguntaTO.setDsPergunta( dsPergunta );
    		
    		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Digite uma pergunta!", null));
    		caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    	}
    }
    
    /**
     * Adiciona uma resposta ao objeto pergunta.
     * @param e ActionEvent
     */
    public void addResposta(ActionEvent e) {
    	boolean estaNaLista = false;
    	if (this.perguntaSelecionadaView.getDsPergunta() != null) {
	    	if (!"".equals(this.respostaTO.getDsResposta().trim())) {
	    		for (AgvTabRespPesquisa resposta : this.perguntaSelecionadaView.getRespostaList()) {
					if (resposta.getDsResposta().equalsIgnoreCase(this.respostaTO.getDsResposta().trim())) {
						
						
						String dsResposta = this.respostaTO.getDsResposta().trim();
						
						this.respostaTO.setDsResposta( dsResposta );
						
						FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resposta já existente!", null));
							caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
						estaNaLista = true;
						break;
					}
				}
	    		if (!estaNaLista) {
	    			this.perguntaSelecionadaView.bidirecionalInclusao(this.respostaTO);
	    	    	this.respostaTO = new AgvTabRespPesquisa();
	    		}
	    	} else {
	    		
				String dsResposta = this.respostaTO.getDsResposta().trim();
				
				this.respostaTO.setDsResposta( dsResposta );
	    		
	    		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Digite uma resposta!", null));
	    			caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
	    	}
    	} else {
    		
			String dsResposta = this.respostaTO.getDsResposta().trim();
			
			this.respostaTO.setDsResposta( dsResposta );
    		
    		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione uma pergunta para incluir uma resposta!", null));
    			caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    	}
    }

    /**
     * Remove pergunta da lista.
     * Seta em um atributo de sessão quais objetos foram deletados pelo usuário para depois ser refletido 
     * na base de dados
     * @param e ActionEvent
     **/
	public void removePergunta(ActionEvent e) {
		
		this.showPanelRepostas = false;
		this.showPanelRepostasMessage = true;

        String dsPergunta = getRequestParameterMap("dsPergunta");

        AgvTabPerguntaPesquisa perguntaSelecionada = this.pesquisaTO.getPergunta(dsPergunta);
        
        this.perguntaSelecionadaView = perguntaSelecionada;
        this.perguntaSelecionadaView.bidirecionalRemocao();
    }
	
	/**
     * Remove resposta da lista.
     * Seta em um atributo de sessão quais objetos foram deletados pelo usuário para depois ser refletido 
     * na base de dados
     * @param e ActionEvent
     **/
	public void removeResposta(ActionEvent e) {
         String dsResposta = getRequestParameterMap("dsResposta");         
         
         AgvTabRespPesquisa respostaRetornada = this.perguntaSelecionadaView.getResposta(dsResposta);
         
         respostaRetornada.bidirecionalRemocao();
    }
	
	/**
	 * Seleciona a pergunta para a inclusão da resposta.
	 * @param e ActionEvent
	 */
	public void selecionaPergunta(RowSelectorEvent e) {
		
		this.showPanelRepostas = true;
		this.showPanelRepostasMessage = false;
		
        AgvTabPerguntaPesquisa perguntaSelecionada = this.pesquisaTO.getPerguntaList().get(e.getRow());
        perguntaSelecionada.setSelected(true);
        
        int count = 0;
        for (AgvTabPerguntaPesquisa element : this.pesquisaTO.getPerguntaList()) {
        	if (count != e.getRow()) {
        		element.setSelected(false);
			}
        	count ++;
		}
        this.perguntaSelecionadaView = perguntaSelecionada;
	}
	
	/**
	 * Atualiza a listagem.
	 * @return String
	 */
	public String atualizar(){
		
		this.listaPesquisas = new ListDataModel(this.pesquisaService.findAll());
		
        return AgvStatus.ATUALIZA.name();
	}
	
	/**
	 * @param e ActionEvent
	 */
	public void tipoPesquisaChanged(ValueChangeEvent e) {
		this.pesquisaTO.setNrPrioridade(null);
		this.pesquisaTO.setAgvTabAutoatendimento(null);
	}
	
	
    /**
     * Salva registro de Pesquisa , atualizando seus relacionamentos.
     * @return String
     */
    public String salvar() {
		LoginTO userSession = getSessionAttribute(LoginBBean.USER);
		
		if (isEncerraPesquisa()) {
    		return salvarEncerramento(userSession);
		}
		
		if (this.pesquisaTO.getCdPesquisa() == null) {
			return salvarInclusao(userSession);
		} else {
			return salvarAlteracao(userSession);
		}
    }
    
    private String salvarInclusao(LoginTO userSession) {
    	try {
    		this.pesquisaTO.setDtCriacao(new Date());
			if(userSession != null) {
    			this.pesquisaTO.setNmUsrCriacao(userSession.getUser());
    		}

			
			// Salva a nova pesquisa.
    		save(this.pesquisaTO);
    		
			FacesContext.getCurrentInstance().addMessage(null,
	    		new FacesMessage(FacesMessage.SEVERITY_INFO, "A Pesquisa " + pesquisaTO.getNmPesquisa() + " foi salva", null));
			caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
//			agvlogger.info("Registro da Pesquisa foi criado pelo usuário:" + "user1");
    		return atualizar();
		} catch (Exception e) {
			agvlogger.error(e.getMessage());
            e.printStackTrace(); //TODO retirar
            FacesContext.getCurrentInstance().addMessage(null,
            	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao incluir Pesquisa!", null));
            caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
			return AgvStatus.EDIT.name();
		}
    }
    
    private String salvarAlteracao(LoginTO userSession) {
    	try {
			this.pesquisaClone.setDtEncerramento(new Date());
			if(userSession != null) {
				this.pesquisaClone.setNmUsrEncerramento(userSession.getUser());
    		}
			
			//this.pesquisaTO.setDtIniVigencia( WrapperUtils.toDate(this.dataHidden) );
			//this.pesquisaTO.setDtFimVigencia( WrapperUtils.toDate(this.dataHiddenFim) );
			
			// Salva o clone da pesquisa para manter o histórico antes da alteração.
			save(this.pesquisaClone);
			
			// Atribui null a todos os códigos ligados a pesquisa, pois terá que gerar uma nova.
			this.pesquisaTO.setCdPesquisa(null);
			for (AgvTabPerguntaPesquisa pergunta : this.pesquisaTO.getPerguntaList()) {
				for (AgvTabRespPesquisa resposta : pergunta.getRespostaList()) {
					resposta.setCdResposta(null);
				}
				pergunta.setCdPergunta(null);
			}
			
			if (this.pesquisaTO.getDtIniVigencia().before(getDataDoDia())) {
				this.pesquisaTO.setDtIniVigencia(new Date());
			}
			
			// Salva a nova pesquisa com os dados alterados.
			save(this.pesquisaTO);
			
			FacesContext.getCurrentInstance().addMessage(null,
	    		new FacesMessage(FacesMessage.SEVERITY_INFO, "A Pesquisa " + pesquisaTO.getNmPesquisa() + " foi alterada!", null));
			caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
//			agvlogger.info("Registro da Pesquisa foi alterado pelo usuário:" + "user1");
    		return atualizar();
		} catch (Exception e) {
			agvlogger.error(e.getMessage());
            e.printStackTrace(); //TODO retirar
            FacesContext.getCurrentInstance().addMessage(null,
            	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao alterar Pesquisa!", null));
            caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
			return AgvStatus.EDIT.name();
		}
    }
    
    private String salvarEncerramento(LoginTO userSession) {
    	try {
    		this.pesquisaTO.setDtEncerramento(new Date());
    		if(userSession != null) {
				this.pesquisaTO.setNmUsrEncerramento(userSession.getUser());
    		}
    		
    		// Salva o encerramento da pesquisa.
			save(this.pesquisaTO);
			
			FacesContext.getCurrentInstance().addMessage(null,
	    		new FacesMessage(FacesMessage.SEVERITY_INFO, "A Pesquisa " + pesquisaTO.getNmPesquisa() + " foi encerrada!", null));
			caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
//			agvlogger.info("Registro da Pesquisa foi encerrado pelo usuário:" + "user1");
    		return atualizar();
		} catch (Exception e) {
			agvlogger.error(e.getMessage());
            e.printStackTrace(); //TODO retirar
            FacesContext.getCurrentInstance().addMessage(null,
            	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao encerrar Pesquisa!", null));
            caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
			return AgvStatus.EDIT.name();
		}
    }
    
    public String validarCampos() {
    	boolean valido = true;
    	
		//this.pesquisaTO.setDtIniVigencia( WrapperUtils.toDate(this.dataHidden) );
		//this.pesquisaTO.setDtFimVigencia( WrapperUtils.toDate(this.dataHiddenFim) );

    	
    	// Valida campo nome.
    	if ("".equals(this.pesquisaTO.getNmPesquisa().trim())) {
    		
    		String nmPesquisa = this.pesquisaTO.getNmPesquisa().trim();
    		this.pesquisaTO.setNmPesquisa( nmPesquisa );
    		
    		FacesContext.getCurrentInstance().addMessage(null,
        		new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nome não preenchido!", null));
    		valido = false;
    	}
    	
    	if( validarDatasValidasInicio() ){
    		valido = true;
    		return "";
    	}
    	
    	if( validarDatasValidasFim() ){
    		valido = true;
    		return "";
    	}
    	

    	// Valida datas de vigência.
    	if (this.pesquisaTO.getDtIniVigencia() != null) {
    		if (this.pesquisaTO.getDtFimVigencia() != null) {
        		if (!this.encerraPesquisa) {
        			if (this.pesquisaTO.getDtIniVigencia().before(getDataDoDia())) {
        	    		FacesContext.getCurrentInstance().addMessage(null,
        	        		new FacesMessage(FacesMessage.SEVERITY_ERROR, "A data inicial de vigência deve ser maior ou igual à data atual!", null));
        	    		valido = false;
        	    	}
        			if (this.pesquisaTO.getDtFimVigencia().before(this.pesquisaTO.getDtIniVigencia())) {
        	    		FacesContext.getCurrentInstance().addMessage(null,
        	        		new FacesMessage(FacesMessage.SEVERITY_ERROR, "A data final de vigência deve ser maior ou igual à data inicial!", null));
        	    		valido = false;
        	    	}
        		}
        	} else {
        		FacesContext.getCurrentInstance().addMessage(null,
                	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data final de vigência não preenchida!", null));
        		valido = false;
        	}
    	} else {
    		FacesContext.getCurrentInstance().addMessage(null,
            	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data inicial de vigência não preenchida!", null));
    		valido = false;
    	}
    	
    	// Valida o tipo de pesquisa.
    	if (this.pesquisaTO.getTpPesquisa() != null) {
    		if (this.pesquisaTO.getTpPesquisa() == 2) {
    			if (this.pesquisaTO.getAgvTabAutoatendimento().getCdAutoatendimento() == -1L) {
    				FacesContext.getCurrentInstance().addMessage(null,
		            	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Autoatendimento não preenchido!", null));
		    		valido = false;
    			}
    			if (this.pesquisaTO.getNrPrioridade() == null) {
    				FacesContext.getCurrentInstance().addMessage(null,
		            	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Prioridade não preenchida!", null));
		    		valido = false;
    			}
    		}
    	} else {
    		FacesContext.getCurrentInstance().addMessage(null,
            	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tipo de pesquisa não selecionado!", null));
    		valido = false;
    	}
    	
    	
    	
    	// Valida as perguntas e respostas.
    	if (this.pesquisaTO.getPerguntaList().isEmpty()) {
    		FacesContext.getCurrentInstance().addMessage(null,
    		    new FacesMessage(FacesMessage.SEVERITY_ERROR, "A pesquisa deve possuir pelo menos uma pergunta!", null));
    		valido = false;
		} else {
			for (AgvTabPerguntaPesquisa pergunta : this.pesquisaTO.getPerguntaList()) {
				if (pergunta.getRespostaList().isEmpty()) {
					FacesContext.getCurrentInstance().addMessage(null,
    		    		new FacesMessage(FacesMessage.SEVERITY_ERROR, "A pergunta '" + pergunta.getDsPergunta() + "' deve possuir pelo menos uma resposta!", null));
					valido = false;
    			}
			}
		}
    	
    	if (!valido){
    		caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.erro");
    		return AgvStatus.EDIT.name();
    	}
    	else
    		return salvar();
    }
    
    
    public String encerrarPesquisa(){
    	
    	remove(pesquisaTO.getCdPesquisa());
    	
    	agvlogger.info("Registro "+ pesquisaTO.getCdPesquisa() +"  Pesquisa encerrada pelo usuário:" + "user1");//TODO recuperar usuário da sessão para log
    	FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_INFO, "A Pesquisa "+ pesquisaTO.getNmPesquisa() +" foi encerrada.", null));
    	
    	caminhoImagemErroSucesso = FacesBundleUtil.getInstance().getString("imagem.sucesso");
    	
    	this.encerraPesquisa = true;
    	return atualizar();
    	//return validarCampos();
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
	
	public List<SelectItem> getListTipoPesquisa() {
		return TipoPesquisa.getListSelectItem();
	}
	
	public List<SelectItem> getListaAutoatendimentos() {
		if (this.listaAutoatendimentos == null) {
			this.listaAutoatendimentos = autoAtendService.findAllSelecteItem();
		}
		return listaAutoatendimentos;
	}

	public void setListaAutoatendimentos(List<SelectItem> listaAutoatendimentos) {
		this.listaAutoatendimentos = listaAutoatendimentos;
	}

	public void afterPropertiesSet() throws Exception {
		listaPesquisas = new ListDataModel(this.pesquisaService.findAll());
	}

	public ListDataModel getListaPesquisas() {
		return listaPesquisas;
	}

	public void setListaPesquisas(ListDataModel listaPesquisas) {
		this.listaPesquisas = listaPesquisas;
	}

	public AgvTabPesquisa getPesquisaTO() {
		
		return pesquisaTO;
	}

	public void setPesquisaTO(AgvTabPesquisa pesquisaTO) {
		this.pesquisaTO = pesquisaTO;
	}

	public AgvTabPerguntaPesquisa getPerguntaTO() {
		return perguntaTO;
	}

	public void setPerguntaTO(AgvTabPerguntaPesquisa perguntaTO) {
		this.perguntaTO = perguntaTO;
	}

	public AgvTabRespPesquisa getRespostaTO() {

		return this.respostaTO;
	}

	public void setRespostaTO(AgvTabRespPesquisa respostaTO) {
		this.respostaTO = respostaTO;
	}
	
	public AgvTabPerguntaPesquisa getPerguntaSelecionadaView() {
		return perguntaSelecionadaView;
	}

	public void setPerguntaSelecionadaView(
			AgvTabPerguntaPesquisa perguntaSelecionadaView) {
		this.perguntaSelecionadaView = perguntaSelecionadaView;
	}

	public TipoPesquisa getTipoPesquisa() {
		return tipoPesquisa;
	}

	public void setTipoPesquisa(TipoPesquisa tipoPesquisa) {
		this.tipoPesquisa = tipoPesquisa;
	}

	public boolean isEncerraPesquisa() {
		return encerraPesquisa;
	}

	public void setEncerraPesquisa(boolean encerraPesquisa) {
		this.encerraPesquisa = encerraPesquisa;
	}

	public AgvTabPesquisa getPesquisaClone() {
		return pesquisaClone;
	}

	public void setPesquisaClone(AgvTabPesquisa pesquisaClone) {
		this.pesquisaClone = pesquisaClone;
	}
	
	@SuppressWarnings("unchecked")
	public boolean getExisteMsgErro(){
		Iterator iter = FacesContext.getCurrentInstance().getMessages();
		existeMsgErro = iter.hasNext();
		return existeMsgErro;
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

	public String getDataHidden() {
		return dataHidden;
	}

	public void setDataHidden(String dataHidden) {
		this.dataHidden = dataHidden;
	}

	public String getDataHiddenFim() {
		return dataHiddenFim;
	}

	public void setDataHiddenFim(String dataHiddenFim) {
		this.dataHiddenFim = dataHiddenFim;
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

	public String getDiaFim() {
		return diaFim;
	}

	public void setDiaFim(String diaFim) {
		this.diaFim = diaFim;
	}

	public String getMesFim() {
		return mesFim;
	}

	public void setMesFim(String mesFim) {
		this.mesFim = mesFim;
	}

	public String getAnoFim() {		
		return anoFim;
	}

	public void setAnoFim(String anoFim) {
		this.anoFim = anoFim;
	}

	public boolean isEditarPesquisa() {
		return editarPesquisa;
	}

	public void setEditarPesquisa(boolean editarPesquisa) {
		this.editarPesquisa = editarPesquisa;
	}

	public boolean isShowPanelRepostas() {
		return showPanelRepostas;
	}

	public void setShowPanelRepostas(boolean showPanelRepostas) {
		this.showPanelRepostas = showPanelRepostas;
	}

	public boolean isShowPanelRepostasMessage() {
		return showPanelRepostasMessage;
	}

	public void setShowPanelRepostasMessage(boolean showPanelRepostasMessage) {
		this.showPanelRepostasMessage = showPanelRepostasMessage;
	}

	public boolean validarDatasValidasInicio(){
		
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
    	
    	this.pesquisaTO.setDtIniVigencia( WrapperUtils.toDate(dataPublicacao) );
		
		return false;
		
	}
	
	public boolean validarDatasValidasFim(){
		
		int totalDiaMes = WrapperUtils.daysInMonth();
		
		if( this.diaFim.equals("") ){
			
			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dia digitado inválido", null));
	   	    		   
			 return true;
			 
		}else if(  WrapperUtils.toInt(this.diaFim) > totalDiaMes  ) {

			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dia digitado inválido", null));
	   	    		   
			 return true;
			
		}
		
		if( this.mesFim.equals("")  ) {

			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mês digitado inválido", null));
	   	    		   
			 return true;
			
		}else if( WrapperUtils.toInt(this.mesFim) > 12 ){
			
			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mês digitado inválido", null));
	   	    		   
			 return true;			
			
		}
		
		if( this.anoFim.equals("") ) {

			 FacesContext.getCurrentInstance().addMessage(null,
	   	             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ano digitado inválido", null));
	   	    		   
			 return true;
			
		}
		
		
    	String dataPublicacao = diaFim + "/" + mesFim + "/" + anoFim;
    	
    	this.pesquisaTO.setDtFimVigencia( WrapperUtils.toDate(dataPublicacao) );
		
		return false;
		
	}
	
	
	
}
