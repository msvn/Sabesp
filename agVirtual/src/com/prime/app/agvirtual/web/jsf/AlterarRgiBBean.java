package com.prime.app.agvirtual.web.jsf;

import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.app.agvirtual.web.jsf.imovel.ImovelIdentificadoBBean;
import com.prime.app.agvirtual.web.jsf.util.SessionUtil;

@Component
@Scope(value="session")
public class AlterarRgiBBean extends BasicPesquisaBBean{
	private static final long serialVersionUID = -7221774315816966482L;
	
	@Autowired  private ImovelIdentificadoBBean imovelIdentificado;
	
	@Autowired private MigalhaPaoBBean migalhaBBean;

	/**
	 * Redireciona para tela de identificacao do imovel
	 */
	public String identificarRgi(){
		return "IDENTIFICA_RGI";
	} 
	
	/**
	 * Captura evento de tela para apagar imovel identificado
	 */
	public void apagaRgi(ActionEvent e){
		imovelIdentificado.setDadosImovelIdentificado(null);
		SessionUtil.removerAtributo(SessionUtil.DADOSIMOVELSESSION, getHttpSession(Boolean.FALSE));    // remove dados do imovel da sessao
		preencherMenuMigalha("", "", "");
        preencherSubMenuMigalha("", "");
	}
	
	public void preencherMenuMigalha(String codigo, String descricao, String outcome){
		migalhaBBean.setCdMenu(codigo);
		migalhaBBean.setDsMenu(descricao);
		migalhaBBean.setOutcomeMenu(outcome);
	}
	
	public void preencherSubMenuMigalha(String outcome, String descricao){
		migalhaBBean.setOutcomeSubMenu(outcome);
		migalhaBBean.setDsSubMenu(descricao);
	}
	
	/**
	 *  Expoe propriedade ao Faces. Ver tela alterarrgi. 
	 */
	public DadosImoveisTO getDadosImovelIdentificado(){
		return obterDadosImovelIdentificado();
	}
}
