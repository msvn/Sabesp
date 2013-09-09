package com.prime.app.agvirtual.web.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.service.AcatamentoService;
import com.prime.app.agvirtual.to.AcatamentoTO;
import com.prime.app.agvirtual.to.TabelaTO;
import com.prime.infra.web.jsf.BasicBBean;
import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.LINCEnvironment;
import com.unisys.jellybeans.LINCStatus;

@Component
@Scope("session")
public class AcatamentoBBean extends BasicBBean{
	
	LINCEnvironment objLINC = null;
	LINCStatus objStatusLine = null;
	IspecModel objCurrentIspec = null;
	
	private List<TabelaTO> listaColunas = new ArrayList<TabelaTO>();
	
	@Autowired
	AcatamentoService acatamentoService;
	
	AcatamentoTO acatamentoTO = new AcatamentoTO("52791947","20");
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8163350377193087002L;

	@SuppressWarnings("unchecked")
	public void consulta(ActionEvent e){
		
		acatamentoTO.setDsTela50("teste@teste.com");
		
		listaColunas = acatamentoService.consulta(acatamentoTO);
		
	}

	public List getListaColunas() {
		return listaColunas;
	}

	public void setListaColunas(List listaColunas) {
		this.listaColunas = listaColunas;
	}

	public AcatamentoTO getAcatamentoTO() {
		return acatamentoTO;
	}

	public void setAcatamentoTO(AcatamentoTO acatamentoTO) {
		this.acatamentoTO = acatamentoTO;
	}
	
}
