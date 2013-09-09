package com.prime.app.agvirtual.web.jsf;

import static com.prime.infra.util.WrapperUtils.toLong;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.Atendimento;
import com.prime.app.agvirtual.enums.SituacaoAtendimentoEnum;
import com.prime.app.agvirtual.service.AtendimentoService;
import com.prime.app.agvirtual.service.LogAcessoService;
import com.prime.app.agvirtual.web.jsf.util.SessionUtil;
import com.prime.infra.BusinessException;
import com.prime.infra.web.jsf.BasicBBean;

/**
 *  Funcionalidades de registro do Atendimento e Log de Acesso
 */
@Component
@Scope(value="session")
public class AtendimentoBBean extends BasicBBean{
	private static final Logger logger = LoggerFactory.getLogger(AtendimentoBBean.class);
	private static final long serialVersionUID = 8991416518207822226L;
	
	public static final String BEAN_NAME = "atendimentoBBean";  // usado em ApplicationSessionListener
	
	@Autowired private AtendimentoService atendimentoService;	
	@Autowired private LogAcessoService logAcessoService;
	
	private Atendimento atendimento;
	private String codSessao;	
	
	/**
	 * Abre atendimento
	 */
	public void abrirAtendimento(String cdItemMenu){ 
		codSessao = getSessionId();
		String nrIp = getRemoteAddress();
		
		if(isPersist()){
			atendimento = atendimentoService.abreAtendimento(codSessao, nrIp); 	 // cria atendimento
			SessionUtil.adicionarAtributo(SessionUtil.ATENDIMENTOSESSION, atendimento, getHttpSession(Boolean.FALSE));   // armazena atendimento na sessao			
		}
	}		
	
	/** 
	 * Conclui atendimento
	 */
	public void concluirAtendimento(){
		String codSessao = getSessionId();
		
		if(isPersist()){
			try {
				atendimentoService.concluirAtendimento(codSessao, SituacaoAtendimentoEnum.CONCLUIDO.getCodigo());
			} catch (BusinessException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			}
		}
		
		SessionUtil.removerAtributo(SessionUtil.ATENDIMENTOSESSION, getHttpSession(Boolean.FALSE));    // remove atendimento da sessao
		SessionUtil.removerAtributo(SessionUtil.DADOSIMOVELSESSION, getHttpSession(Boolean.FALSE));    // remove dados do imovel da sessao
		
	}			
	/**
	 * Conclui atendimento motivo TimeOut
	 * 
	 * Chamaddo pelo session listener 
	 */
	public void concluirAtendimentoTimeOut(){
		try{
			if((atendimento != null) && isPersist()){
				atendimentoService.concluirAtendimento(codSessao, SituacaoAtendimentoEnum.TIMEOUT.getCodigo());
			}
		}catch(BusinessException e){
			addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			logger.error("Erro ao concluir atendimento, codSessao=" + codSessao);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Erro ao concluir atendimento, codSessao=" + codSessao);
		}
	}
	
	/**
	 * Grava log de acesso
	 * 
	 * Abre atendimento, caso nao tenha sido aberto
	 * 
	 * TODO - verificar necessidade de insert in bulk
	 */
	public void gravarLogAcesso(String cdItemMenu){
		String dsUsuario = "";  	    		// TODO - Obter nome do usuario

		if(!isAtendimentoNaSessao() && isPersist()){
			abrirAtendimento(cdItemMenu);
		}
		
		if(isPersist())
		gravaLog(cdItemMenu, dsUsuario, atendimento);
	}
	
	/**
	 * Grava log
	 * 
	 * @param atendimento
	 */
	private void gravaLog(String cdItemMenu, String dsUsuario, Atendimento atendimento){
		logAcessoService.criarLogAcesso(toLong(cdItemMenu), dsUsuario, atendimento); 		 // cria log de acesso
	}
	
	/**
	 * Verifica se atendimento ja esta na sessao
	 */
	protected boolean isAtendimentoNaSessao(){
		atendimento = SessionUtil.obterAtributo(SessionUtil.ATENDIMENTOSESSION, getHttpSession(Boolean.FALSE), new Atendimento());
		
		if(atendimento!=null) 
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}
	
	public Long getCodigoAtendimento(){
		if(atendimento==null) {
			logger.info("Codigo do atendimento retornado nulo pois atendimento esta nulo.");
			return null;
		}
	
		return atendimento.getCdAtendimento();
	}

	// ###
	private static boolean persist = Boolean.FALSE;
	public boolean isPersist() {
		return persist;
	}

	public void setPersist(boolean persist) {
		AtendimentoBBean.persist = persist;
	}
	
	public void persist(ActionEvent e){
		persist = (persist) ? Boolean.FALSE : Boolean.TRUE;
	}

	public static boolean getPersist() {
		return persist;
	}	
	// ###
	
	/**
	 * Obtem id da sessao
	 * 
	 */
	// TODO - Modificar para concatenar HH:MM:SS
	private String getSessionId(){
		try{
			HttpSession session = ((HttpSession)getFacesContext().getExternalContext().getSession(Boolean.FALSE));
			
			System.out.println("-->> Session info:");
			System.out.println("-> Id: " + session.getId());
			System.out.println("-> creation time: " + session.getCreationTime());
			System.out.println("-> Last Accessed time: " + session.getLastAccessedTime());
			
			return session.getId();
		}catch(Exception e){
			logger.error("Erro ao obter ID da sessao!", e);
			return null;
		}
	}
	
	/**
	 * Obtem numero do endereco remoto do cliente
	 * 
	 * ATENCAO: Se a rede estiver configurada com NAT, varios clientes diferentes <b>podem ter o mesmo numero de IP</b> 
	 * 
	 */
	private String getRemoteAddress(){
		try{
			HttpServletRequest request = (HttpServletRequest)getFacesContext().getExternalContext().getRequest();
			
			return request.getRemoteAddr();
		}catch(Exception e){
			logger.error("Erro ao obter endereco de IP do cliente!", e);
			return null;
		}
	}

	/**
	 * Retorna atendimento Aberto
	 */
	public Atendimento getAtendimento() {
		return atendimento;
	}
	
	public void matarSessao(ActionEvent e){
		HttpSession session = (HttpSession)getFacesContext().getExternalContext().getSession(Boolean.FALSE);
		session.invalidate();
	}
}
