package com.prime.app.agvirtual.web.jsf.canaisatendimento;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.CanalAtendimento;
import com.prime.app.agvirtual.service.CanalAtendimentoService;
import com.prime.app.agvirtual.web.jsf.MenuNavegacaoBBean;

/**
 * BackBean com controles da funcinalidade Canais de Atendimento.
 * @author felipepm
 */
@Component
@Scope("session")
public class CanaisAtendimentoBBean extends MenuNavegacaoBBean {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -5938633193756902772L;
	
	protected static final String CODIGO_CANAIS_ATENDIMENTO = "8";
	
	@Autowired
	protected CanalAtendimentoService canalAtendimentoService;
	
	/**
	 * Log.
	 */
	private static final Logger agvlogger = LoggerFactory.getLogger(CanaisAtendimentoBBean.class);

	public String carregar(){
		
		preencherMenuMigalha(CODIGO_CANAIS_ATENDIMENTO, getDescricaoMenu(), getOutcomeMenu());
		preencherSubMenuMigalha(EMPTY, EMPTY, EMPTY, EMPTY);
		return getOutcomeMenu();
	}

	public String redirectCanalAtendimento() {

		preencherSubMenuMigalha(EMPTY, getDescricaoSubMenu(), getOutcomeSubMenu(), getCodigoAutoAtendimento());
		return getOutcomeSubMenu();
	}
	
	public List<CanalAtendimento> getListaCanaisAtendimento() {

		listaCanaisAtendimento = canalAtendimentoService.findAll();
		return listaCanaisAtendimento;
	}

	public void setListaCanaisAtendimento(List<CanalAtendimento> listaCanaisAtendimento) {
		this.listaCanaisAtendimento = listaCanaisAtendimento;
	}

}
