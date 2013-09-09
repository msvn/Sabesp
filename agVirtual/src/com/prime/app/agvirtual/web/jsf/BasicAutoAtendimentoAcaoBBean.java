package com.prime.app.agvirtual.web.jsf;

import javax.faces.application.FacesMessage;

import com.prime.app.agvirtual.entity.AcaoAutoAtendimento;
import com.prime.app.agvirtual.enums.AutoAtendimentoEnum;


public abstract class BasicAutoAtendimentoAcaoBBean extends BasicAutoAtendimentoBBean{
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -2775054974536982470L;

	/**
	 * Acata servico
	 * 
	 * Busca acao baseado no codigo do auto atendimento acao
	 */	
	@Override
	protected boolean realizarAcatamentoServico() {
		AcaoAutoAtendimento acao = buscaAcao(AutoAtendimentoEnum.CONSERTO_CAVALETE.getCodigo());
		
		if(acao!=null){
			return acatarServico(acao);
		}

		return Boolean.FALSE;

	}
	
	/**
	 * Busca acao. 
	 * 
	 * Acao contem dados necessarios para acatamento do servico
	 */
	protected AcaoAutoAtendimento buscaAcao(Long cdAutoAtendimento){
		try {
			acao = acaoService.findAcaoByAutoAtendimento(cdAutoAtendimento);
		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
		}
		return acao;
	}	
	

}
