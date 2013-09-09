package com.prime.app.agvirtual.web.jsf.atendimento;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.service.RelatorioAutoAtendimentoService;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;
import com.prime.app.agvirtual.to.RelatorioAutoAtendimentoAcessadoTO;
import com.prime.app.agvirtual.web.jsf.AtendimentoBBean;
import com.prime.infra.report.util.FindReport;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.BasicBBean;
import com.prime.infra.web.jsf.ReportBBean;

@Component
@Scope(value="session")
public class RelatorioAtendimentoBBean extends ReportBBean {
	private static final Logger logger = LoggerFactory.getLogger(RelatorioAtendimentoBBean.class);
	private static final long serialVersionUID = 123801361979620462L;

	@Autowired private AtendimentoBBean atendimentoBBean;
	@Autowired private RelatorioAutoAtendimentoService relatorioAAService;
	
	private List<RelatorioAutoAtendimentoAcessadoTO> listaAAAcessadoTO;

	private static final String PATH = "/com/prime/app/agvirtual/web/jsf/report/formulario_auto_atendimento.jrxml";
	
	/**
	 * Carrega relatorio
	 */
	public void carregarRelatorio() {
		try {
			Long cdAtendimento = atendimentoBBean.getCodigoAtendimento();
			
			if(cdAtendimento==null) return;  // nao ha atendimento em aberto / situacao de test 
			
			listaAAAcessadoTO = relatorioAAService.doRelatorio(cdAtendimento);
		} catch (NotFoundBusinessException e) {
			e.printStackTrace();
			addMessage(FacesMessage.SEVERITY_ERROR,"Nao ha atendimentos a serem exibidos!", null);
		}
	}

	/**
	 * Captura evento de tela para Carregar Relatorio
	 */
	public void initialize(ActionEvent e) {
		carregarRelatorio();
	}
	
	/**
	 * Captura evento de tela para imprimir
	 */
	public void imprimir(ActionEvent e){
		// constroi relatorio
		render();
	}
	
	public List<RelatorioAutoAtendimentoAcessadoTO> getListaAAAcessadoTO() {
		carregarRelatorio();
		return listaAAAcessadoTO;
	}

	public void setListaAAAcessadoTO(
			List<RelatorioAutoAtendimentoAcessadoTO> listaAAAcessadoTO) {
		this.listaAAAcessadoTO = listaAAAcessadoTO;
	}

	/**
	 * Obtem dados
	 */
	@Override
	protected JRDataSource getJRDataSource() {
		return new JRBeanCollectionDataSource(this.listaAAAcessadoTO);
	}

	/**
	 * Obtem parametros de entrada, no caso nao ha
	 */
	@Override
	protected Map<String, Object> getReportParameters() {
		return new HashMap<String, Object>();
	}

	/**
	 * Obtem template do relatorio do Jasper 
	 */
	@Override
	protected InputStream getReportStream() {
		return FindReport.getReport(null);
	}

}
