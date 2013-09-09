package com.prime.app.agvirtual.web.jsf;

import static com.prime.infra.util.WrapperUtils.isNotNull;
import static com.prime.infra.util.WrapperUtils.isNull;

import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.web.jsf.util.SessionUtil;
import com.prime.infra.web.jsf.BasicBBean;

@Component
@Scope(value="session")
public class AuditoriaAcessoBBean extends BasicBBean{
	private static final long serialVersionUID = 8991416518207822226L;
	
	@Autowired AtendimentoBBean atendimentoBBean;
	@Autowired AutoAtendimentoBBean autoAtendimento;
	
	private String codigoItemMenu;
	
	/**
	 * Registra Log de Acesso, Abre Atendimento e Auto Atendimento.
	 * 
	 * @param codigoItemMenu
	 * @param cdAutoAtendimento
	 */
	public void auditarAcesso(String codigoItemMenu, String cdAutoAtendimento){
		this.codigoItemMenu = codigoItemMenu;
		// Registra Atendimento
		if(!atendimentoBBean.isAtendimentoNaSessao() && isGravar()){
			atendimentoBBean.abrirAtendimento(codigoItemMenu);
		}
		
		// Registra log de acesso
		if(isGravar()){
			atendimentoBBean.gravarLogAcesso(codigoItemMenu);		// grava log de acesso ao sistema
		}
		
		// Registra Acesso Auto Atendimento
		if(isAutoAtendimento(cdAutoAtendimento) && isGravar()){
			autoAtendimento.abrirAutoAtendimento(cdAutoAtendimento);		
		}
	}
	
	// EVENTOS DE TELA
	
	/** 
	 * Captura evento de tela para concluir atendimento
	 */
	public void concluirAtendimento(ActionEvent e){
		atendimentoBBean.concluirAtendimento();		
	}	
	
	/**
	 * Captura evento de tela, Faz logoff do sistema , fechando os atendimento abertos na navegação
	 */
	public String sair(){
		atendimentoBBean.concluirAtendimento();																		   // conclui atendimento
		SessionUtil.removerAtributo(SessionUtil.ATENDIMENTOSESSION, getHttpSession(Boolean.FALSE));    // remove atendimento da sessao
		SessionUtil.removerAtributo(SessionUtil.DADOSIMOVELSESSION, getHttpSession(Boolean.FALSE));    // remove dados do imovel da sessao
		
		return AGVINICIAL;
	}
	
	/**
	 * Action de tela para concluir atendimento.
	 * 
	 * Chamado em menu_user.jspx e templates
	 */
	public String redirectConcluirAtendimento(){
		atendimentoBBean.concluirAtendimento();
		return "CONCLUIR_ATENDIMENTO";
	}	
	
	/**
	 * Redireciona para tela inicial
	 */
	public String paginaInicial(){
		return AGVINICIAL;
	}
	
	/**
	 *  Redireciona para tela de identificacao do imovel
	 */
	public String identificarRgi(){
		if(isNotNull(atendimentoBBean.getAtendimento())){
			atendimentoBBean.concluirAtendimento();
		}
		
		SessionUtil.removerAtributo(SessionUtil.ATENDIMENTOSESSION, getHttpSession(Boolean.FALSE)); 
		SessionUtil.removerAtributo(SessionUtil.DADOSIMOVELSESSION, getHttpSession(Boolean.FALSE));
		return "PAGINAINICIAL";
	}
	
	/**
	 * Redireciona para tela de identificacao do imovel
	 * 
	 * @return
	 */
	public String redirectIdenficiaRgi(){
		return "IDENTIFICA_RGI";
	}	
	
	//##
	
	private boolean isGravar(){
		return AtendimentoBBean.getPersist();
	}
	
	private boolean isAutoAtendimento(String cdAutoAtendimento){
		if(isNull(cdAutoAtendimento) || ("null".equals(cdAutoAtendimento))){
			return false;
		}
		return true;
	}

	public String getCodigoItemMenu() {
		return codigoItemMenu;
	}

	
}
