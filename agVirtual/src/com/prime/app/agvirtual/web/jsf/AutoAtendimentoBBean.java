package com.prime.app.agvirtual.web.jsf;

import static com.prime.infra.util.WrapperUtils.isNull;
import static com.prime.infra.util.WrapperUtils.toLong;

import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.AgvTabAutoatendimento;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcao;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcessado;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcessadoAcao;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcessadoPergunta;
import com.prime.app.agvirtual.enums.SituacaoAtendimentoEnum;
import com.prime.app.agvirtual.service.AutoAtendimentoService;
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.infra.BusinessException;
import com.prime.infra.web.jsf.BasicBBean;

@Component
@Scope(value="session")
public class AutoAtendimentoBBean extends BasicBBean{
	private static final long serialVersionUID = 8991416518207822226L;
	
	@Autowired private AtendimentoBBean atendimentoBBean;
	@Autowired private AutoAtendimentoService autoAtendimentoService;
	
	private AutoAtendimentoAcessado autoAtendimentoAcessado;
	
	/**
	 * Abrir Auto Atendimento
	 */
	public void abrirAutoAtendimento(String cdAutoAtendimento){	
		realizaValidacoesBasicas(cdAutoAtendimento);
		
		AgvTabAutoatendimento aa = autoAtendimentoService.findById(toLong(cdAutoAtendimento));	
		
		if(isAutoAtendimentoAcao(aa)){
			abrirAutoAtendimentoAcao(aa);
		}else{
			abrirAutoAtendimentoPergunta(aa);
		}
	}
		
	/**
	 *  Registra acesso ao Auto Atendimento Pergunta
	 */
	private void abrirAutoAtendimentoPergunta(AgvTabAutoatendimento aa){
		autoAtendimentoAcessado = createAAAcessado(aa);
		
		if(isPersist())
			autoAtendimentoAcessado = autoAtendimentoService.abrirAAAcessadoPergunta((AutoAtendimentoAcessadoPergunta)autoAtendimentoAcessado);
	}	
	
	/**
	 *  Registra acesso ao Auto Atendimento acao
	 */
	private void abrirAutoAtendimentoAcao(AgvTabAutoatendimento aa){
		autoAtendimentoAcessado = createAAAcessado(aa);
		
		if(isPersist())
			autoAtendimentoAcessado = autoAtendimentoService.abrirAAAcessadoAcao((AutoAtendimentoAcessadoAcao)autoAtendimentoAcessado);
	}		
	
	/**
	 * Metodo utilitario para realizar validacoes
	 */	
	private void realizaValidacoesBasicas(String cdAutoAtendimento){
		// valida atendimento
		if(isNull(atendimentoBBean.getAtendimento())){
			logger.error("Codigo atendimento NULO.");
			// TODO throw BusinessException
		}
		
		// valida codigo auto atendimento
		if(isNull(toLong(cdAutoAtendimento))){
			// TODO throw BusinessException
			logger.error("Codigo Auto Atendimento NULO.");
		}
	}
	
	/**
	 * Metodo utilitario para criar Auto Atendimento Acessado
	 */
	private AutoAtendimentoAcessado createAAAcessado(AgvTabAutoatendimento aa){
		AutoAtendimentoAcessado acessado = null;
		
		if(aa instanceof AutoAtendimentoAcao){
			acessado = new AutoAtendimentoAcessadoAcao();
		}else{
			acessado = new AutoAtendimentoAcessadoPergunta();
		}
		
		acessado.setAtendimento(atendimentoBBean.getAtendimento());  // set Atendimento
		acessado.setAutoAtendimento(aa);	   						 // set Auto Atendimento	
		return acessado;
	}
	
	/**
	 * Metodo utilitario para checar se Auto Atendimento e uma instancia de AA Acao
	 */
	private boolean isAutoAtendimentoAcao(AgvTabAutoatendimento aa){
		return (aa instanceof AutoAtendimentoAcao) ? Boolean.TRUE : Boolean.FALSE;
	}	
	
	/**
	 * Adicionar dados do Solicitante ao Auto Atendimento
	 */
	public void adicionarDadosSolicitanteAutoAtendimento(DadosImoveisTO dadosImoveis){
		if(isPersist()){
			try {
				autoAtendimentoAcessado = autoAtendimentoService.adicionarDadosSolicitanteAutoAtendimento(autoAtendimentoAcessado, dadosImoveis);
			} catch (BusinessException e) {
				String msg = "Erro ao adicionar dados do solicitante ao Auto Atendimento. Erro: " + e.getMessage();
				logger.error(msg);
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
			}
		}
		
	}
	
	/**
	 * Adicionar RGI do imovel ao Auto Atendimento
	 */
	public void adicionarRgiAutoAtendimento(String dsRgi){
		if(isPersist()){
			try {
				autoAtendimentoAcessado = autoAtendimentoService.adicionarRgiAutoAtendimentoAcessado(autoAtendimentoAcessado.getCdAutoAtendimentoAcess(), dsRgi);
			} catch (BusinessException e) {
				String msg = "Erro ao adicionar dados do solicitante ao Auto Atendimento. Erro: " + e.getMessage();
				logger.error(msg);
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
			}
		}
		
	}	
	
	/**
	 * Fechar Auto Atendimento
	 * 
	 * Nao utilizado. Utilizar fecharAutoAtendimentoPergunta ou fecharAutoAtendimentoAcao
	 */
	public void fecharAutoAtendimento(Long cdSituacao){
		if(isPersist())
		autoAtendimentoService.fecharAutoAtendimento(autoAtendimentoAcessado, cdSituacao);
	}
	
	/**
	 * Fechar Auto Atendimento
	 */
	public void fecharAutoAtendimentoPergunta(Long cdConjuntoResposta, SituacaoAtendimentoEnum situacao, Long cdMotivoInsucesso){
		if(isPersist())
		autoAtendimentoService.fecharAAAcessadoPergunta((AutoAtendimentoAcessadoPergunta)autoAtendimentoAcessado, cdConjuntoResposta, situacao, cdMotivoInsucesso);
	}	
	
	/**
	 * Fechar Auto Atendimento
	 */
	public void fecharAutoAtendimentoAcao(Long cdAcao, Long cdBanco, String acatamentoCSI, SituacaoAtendimentoEnum situacao,
			Long cdMotivoInsucesso){
		if(isPersist())
		autoAtendimentoService.fecharAAAcessadoAcao((AutoAtendimentoAcessadoAcao)autoAtendimentoAcessado,cdAcao,cdBanco,acatamentoCSI,situacao,cdMotivoInsucesso);
	}
	
	private boolean isPersist(){
		return AtendimentoBBean.getPersist();
	}
}
