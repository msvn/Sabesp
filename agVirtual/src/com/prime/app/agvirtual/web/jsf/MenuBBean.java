package com.prime.app.agvirtual.web.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.entity.AgvTabItemMenuGrupo;
import com.prime.app.agvirtual.enums.TipoMenu;

/**
 * BackBean com controles da funcinalidade Conteúdo Página Inicial.
 * @author felipepm
 */
@Component
@Scope(value="session")
public class MenuBBean extends MenuNavegacaoBBean {
	private static final long serialVersionUID = -4200478066589336354L;
	private static final Logger logger = LoggerFactory.getLogger(MenuBBean.class);

	private AgvTabItemMenuGrupo firstColumn;
	private AgvTabItemMenuGrupo secondColumn;
	private AgvTabItemMenuGrupo thirdColumn;
	
	public void redirect() {
		
		String cdMenu = getCodigoMenu();
		String dsMenu = TipoMenu.byValue(Integer.parseInt(cdMenu)).getRealName();
		String outcomeMenu = getOutcomeMenu();

		listaSubMenu = itemMenuService.findGrupoByItemPai(cdMenu);

		preencherMenuMigalha(cdMenu, dsMenu, outcomeMenu);
		preencherSubMenuMigalha(EMPTY, EMPTY, EMPTY, EMPTY);
	}

	public void redirectSuaConta() {
		if (!listaSubMenu.isEmpty()) {
			firstColumn = listaSubMenu.get(0);
			secondColumn = listaSubMenu.get(1);
		}
	}
	
	public void redirectLigacoesAguaEsgoto() {
		if (!listaSubMenu.isEmpty()) {
			firstColumn = listaSubMenu.get(0);
			secondColumn = listaSubMenu.get(1);
		}
	}

	public void redirectConsertos() {
		if (!listaSubMenu.isEmpty()) {
			firstColumn = listaSubMenu.get(0);
			secondColumn = listaSubMenu.get(1);
		}
	}

	public void redirectEmergencias() {
		if (!listaSubMenu.isEmpty()) {
			firstColumn = listaSubMenu.get(0);
		}
	}
	
	public void redirectCorporativo() {
		if (!listaSubMenu.isEmpty()) {
			firstColumn = listaSubMenu.get(0);
		}
	}

	public void redirectDicasInformacoes() {
		if (!listaSubMenu.isEmpty()) {
			firstColumn = listaSubMenu.get(0);
			secondColumn = listaSubMenu.get(1);
			thirdColumn = listaSubMenu.get(2);
		}
	}
	
	public void redirectTarifas() {
		if (!listaSubMenu.isEmpty()) {
			firstColumn = listaSubMenu.get(0);
		}
	}

	public String redirectLink() {
		
		String cdMenu = getCodigoMenu();
		String outcomeMenu = getOutcomeMenu();

        switch (Integer.parseInt(cdMenu)) {
			case 1:
					redirect();
					redirectSuaConta();
			return outcomeMenu;
			case 2:
					redirect();
					redirectLigacoesAguaEsgoto();
			return outcomeMenu;
			case 3:
					redirect();
					redirectConsertos();
			return outcomeMenu;
			case 4:
					redirect();
					redirectEmergencias();
			return outcomeMenu;
			case 5: 
					redirect();
					redirectCorporativo();
			return outcomeMenu;
			case 6: 
					redirect();
					redirectDicasInformacoes();
			return outcomeMenu;
			case 7: 
					redirect();
					redirectTarifas();
			return outcomeMenu;
			case 8:
					preencherSubMenuMigalha(EMPTY, EMPTY, EMPTY, EMPTY);
			return outcomeMenu;
			default:
					preencherMenuMigalha(EMPTY, EMPTY, EMPTY);
					preencherSubMenuMigalha(EMPTY, EMPTY, EMPTY, EMPTY);
			return "PAGINAINICIAL";
        }
	}

	public AgvTabItemMenuGrupo getFirstColumn() {
		return firstColumn;
	}

	public void setFirstColumn(AgvTabItemMenuGrupo firstColumn) {
		this.firstColumn = firstColumn;
	}

	public AgvTabItemMenuGrupo getSecondColumn() {
		return secondColumn;
	}

	public void setSecondColumn(AgvTabItemMenuGrupo secondColumn) {
		this.secondColumn = secondColumn;
	}

	public AgvTabItemMenuGrupo getThirdColumn() {
		return thirdColumn;
	}

	public void setThirdColumn(AgvTabItemMenuGrupo thirdColumn) {
		this.thirdColumn = thirdColumn;
	}
	
}
