package com.prime.app.agvirtual.web.jsf;

import javax.faces.event.ActionEvent;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.web.jsf.util.SessionUtil;
import com.prime.infra.web.jsf.BasicBBean;

/**
 * BackBean respons�vel por armazenar a navega��o do usu�rio na Ag�ncia Virtual.
 * Exemplo: Home > Sua Conta > 2� Via
 * 
 * Obs: Os dados do 1� N�vel (Home) s�o fixos por�m os dados do 2� N�vel (Sua Conta) e 3� N�vel (2� Via) dever ser 
 * 		armazenados para que a navega��o funcione.
 * 
 * @author felipepm
 */
@Component
@Scope(value="session")
public class MigalhaPaoBBean extends BasicBBean {
	
	private static final long serialVersionUID = -4801207356866634812L;
	
	private static final String PARAMETRO_CODIGO_MENU = "cdMenu";
	private static final String PARAMETRO_DESCRICAO_MENU = "dsMenu";
	private static final String PARAMETRO_OUTCOME_MENU = "outcomeMenu";
	private static final String PARAMETRO_CODIGO_SUB_MENU = "cdSubMenu";
	private static final String PARAMETRO_DESCRICAO_SUB_MENU = "dsSubMenu";
	private static final String PARAMETRO_OUTCOME_SUB_MENU = "outcomeSubMenu";
	private static final String PARAMETRO_CODIGO_AUTO_ATENDIMENTO = "cdAutoAtendimento";

	/**
	 * Campos respons�veis por armazenar os dados do 2� N�vel de navega��o.
	 * Exemplo: Home > Sua Conta
	 * 			Os dados de Sua Conta ser�o armazenados nessas vari�veis.
	 */
	private String cdMenu = "";
	private String dsMenu = "";
	private String outcomeMenu = "";
	
	/**
	 * Campos respons�veis por armazenar os dados do 3� N�vel de navega��o.
	 * Exemplo: Home > Sua Conta > 2� Via
	 * 			Os dados de 2� Via ser�o armazenados nessas vari�veis.
	 */
	private String cdSubMenu = "";
	private String dsSubMenu = "";
	private String outcomeSubMenu = "";
	private String cdAutoAtendimento = "";

	private void limpaAtributosBean(){
		cdMenu = "";
		dsMenu = "";
		outcomeMenu = "";
		cdSubMenu = "";
		dsSubMenu = "";
		outcomeSubMenu = "";
		cdAutoAtendimento = "";
	}
	
	private void limpaAtributosSessao(){
		SessionUtil.removerAtributo(PARAMETRO_CODIGO_MENU, getHttpSession(Boolean.FALSE));
		SessionUtil.removerAtributo(PARAMETRO_DESCRICAO_MENU, getHttpSession(Boolean.FALSE));
		SessionUtil.removerAtributo(PARAMETRO_OUTCOME_MENU, getHttpSession(Boolean.FALSE));
		SessionUtil.removerAtributo(PARAMETRO_CODIGO_SUB_MENU, getHttpSession(Boolean.FALSE));
		SessionUtil.removerAtributo(PARAMETRO_DESCRICAO_SUB_MENU, getHttpSession(Boolean.FALSE));
		SessionUtil.removerAtributo(PARAMETRO_OUTCOME_SUB_MENU, getHttpSession(Boolean.FALSE));
		SessionUtil.removerAtributo(PARAMETRO_CODIGO_AUTO_ATENDIMENTO, getHttpSession(Boolean.FALSE));
	}
	
	public void limpaCampos(ActionEvent e) {
		limpaAtributosBean();
		limpaAtributosSessao();
	}

	public String getCdMenu() {
		if (SessionUtil.obterAtributo(PARAMETRO_CODIGO_MENU, getHttpSession(Boolean.FALSE), new String()) != null) {
			cdMenu = SessionUtil.obterAtributo(PARAMETRO_CODIGO_MENU, getHttpSession(Boolean.FALSE), new String());
		}
		return cdMenu;
	}

	public void setCdMenu(String cdMenu) {
		this.cdMenu = cdMenu;
	}

	public String getDsMenu() {
		if (SessionUtil.obterAtributo(PARAMETRO_DESCRICAO_MENU, getHttpSession(Boolean.FALSE), new String()) != null) {
			dsMenu = SessionUtil.obterAtributo(PARAMETRO_DESCRICAO_MENU, getHttpSession(Boolean.FALSE), new String());
		}
		return dsMenu;
	}

	public void setDsMenu(String dsMenu) {
		this.dsMenu = dsMenu;
	}

	public String getOutcomeMenu() {
		if (SessionUtil.obterAtributo(PARAMETRO_OUTCOME_MENU, getHttpSession(Boolean.FALSE), new String()) != null) {
			outcomeMenu = SessionUtil.obterAtributo(PARAMETRO_OUTCOME_MENU, getHttpSession(Boolean.FALSE), new String());
		}
		return outcomeMenu;
	}

	public void setOutcomeMenu(String outcomeMenu) {
		this.outcomeMenu = outcomeMenu;
	}

	public String getDsSubMenu() {
		if (SessionUtil.obterAtributo(PARAMETRO_DESCRICAO_SUB_MENU, getHttpSession(Boolean.FALSE), new String()) != null) {
			dsSubMenu = SessionUtil.obterAtributo(PARAMETRO_DESCRICAO_SUB_MENU, getHttpSession(Boolean.FALSE), new String());
		}
		return dsSubMenu;
	}

	public void setDsSubMenu(String dsSubMenu) {
		this.dsSubMenu = dsSubMenu;
	}

	public String getOutcomeSubMenu() {
		if (SessionUtil.obterAtributo(PARAMETRO_OUTCOME_SUB_MENU, getHttpSession(Boolean.FALSE), new String()) != null) {
			outcomeSubMenu = SessionUtil.obterAtributo(PARAMETRO_OUTCOME_SUB_MENU, getHttpSession(Boolean.FALSE), new String());
		}
		return outcomeSubMenu;
	}

	public void setOutcomeSubMenu(String outcomeSubMenu) {
		this.outcomeSubMenu = outcomeSubMenu;
	}

	public String getCdSubMenu() {
		if (SessionUtil.obterAtributo(PARAMETRO_CODIGO_SUB_MENU, getHttpSession(Boolean.FALSE), new String()) != null) {
			cdSubMenu = SessionUtil.obterAtributo(PARAMETRO_CODIGO_SUB_MENU, getHttpSession(Boolean.FALSE), new String());
		}
		return cdSubMenu;
	}

	public void setCdSubMenu(String cdSubMenu) {
		this.cdSubMenu = cdSubMenu;
	}

	public String getCdAutoAtendimento() {
		if (SessionUtil.obterAtributo(PARAMETRO_CODIGO_AUTO_ATENDIMENTO, getHttpSession(Boolean.FALSE), new String()) != null) {
			cdAutoAtendimento = SessionUtil.obterAtributo(PARAMETRO_CODIGO_AUTO_ATENDIMENTO, getHttpSession(Boolean.FALSE), new String());
		}
		return cdAutoAtendimento;
	}

	public void setCdAutoAtendimento(String cdAutoAtendimento) {
		this.cdAutoAtendimento = cdAutoAtendimento;
	}
}
