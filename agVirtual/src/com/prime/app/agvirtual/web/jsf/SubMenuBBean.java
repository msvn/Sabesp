package com.prime.app.agvirtual.web.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * BackBean com controle do SubMenu(Sua Conta, Ligações de Água e Esgoto, Consertos, Emergências, Corporativo,
 * Dicas e Informações, Tarifas).
 * @author felipepm
 */
@Component
@Scope(value="session")
public class SubMenuBBean extends MenuNavegacaoBBean {
	private static final Logger logger = LoggerFactory.getLogger(SubMenuBBean.class);
	private static final long serialVersionUID = 1067819006718556065L;

	public String direcionarSubMenu() {
		String cdSubMenu = getCodigoSubMenu();
		String dsSubMenu = getDescricaoSubMenu();
		String outcomeSubMenu = getOutcomeSubMenu();
		String cdAutoAtendimento = getCodigoAutoAtendimento();

		// Executa tarefas relacionadas a auditoria da agencia
		auditoriaBBean.auditarAcesso(cdSubMenu, cdAutoAtendimento);
		
		preencherSubMenuMigalha(cdSubMenu, dsSubMenu, outcomeSubMenu, cdAutoAtendimento);
		
		return outcomeSubMenu;
	}
}