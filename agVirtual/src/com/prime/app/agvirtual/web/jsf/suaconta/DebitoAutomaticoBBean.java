/**
 * 
 */
package com.prime.app.agvirtual.web.jsf.suaconta;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.to.DebitoAutomaticoTO;
import com.prime.infra.report.util.FindReport;
import com.prime.infra.web.jsf.ReportBBean;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

@Component
@Scope(value="session")
public class DebitoAutomaticoBBean extends ReportBBean implements Serializable, InitializingBean {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 6212254115610002207L;
	
	/**
	 * Log.
	 */
	private static final Logger agvlogger = LoggerFactory.getLogger(DebitoAutomaticoBBean.class);
	
	/**
	 * Atributo de controle do Formulário.
	 */
	private DebitoAutomaticoTO debitoAuto;
	
	/**
	 * Atributo de controle de renderização da página.
	 */
	private boolean telaSucesso = false;
	
	/**
	 * Atributo de controle do modal.
	 */
	private boolean modalRendered = false;
	
	/**
	 * Caminho do relatório.
	 */
	private static final String PATH = "/com/prime/app/agvirtual/web/jsf/report/formulario_debito_automatico.jasper";
	
	private static final String IMAGE_PATH = "/agvirtual/images/autoriza_relatorio.jpg";

	protected boolean existeMsgErroValidacao;

	
	/**
	 * Construtor.
	 */
	public DebitoAutomaticoBBean() {
		this.debitoAuto = new DebitoAutomaticoTO();
		this.telaSucesso = false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void afterPropertiesSet() throws Exception {
	}
	
	/**
	 * Abre um PDF com os dados preenchidos para a impressão.
	 * @return String
	 */
	public String imprimir() {
		if (!validarCampos()) {
			return "";
		}
		
		this.telaSucesso = true;
		//gera o pdf
		render();
		return "PROSSEGUIR";
	}
	
	private boolean validarCampos() {
		
		if ("".equals(this.debitoAuto.getNome())) {
    		FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.nome"), null));
			return false;
		}
		
		if ("".equals(this.debitoAuto.getRgi())) {
    		FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.rgi"), null));
			return false;
		}
		
		if ("".equals(this.debitoAuto.getCpfCnpj())) {
    		FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.cpfcnpj"), null));
			return false;
		} else {
			if (this.debitoAuto.getCpfCnpj().length() <= 11) {
				if ("".equals(this.debitoAuto.getRg())) {
		    		FacesContext.getCurrentInstance().addMessage(null,
		    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
		    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.rg"), null));
					return false;
				}
			} else {
				this.debitoAuto.setRg("");
			}
		}
		
		if ("".equals(this.debitoAuto.getEndereco())) {
    		FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.endereco"), null));
			return false;
		}
		
		if ("".equals(this.debitoAuto.getBairro())) {
    		FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.bairro"), null));
			return false;
		}
		
		if ("".equals(this.debitoAuto.getCidade())) {
    		FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.cidade"), null));
			return false;
		}
		
		if ("".equals(this.debitoAuto.getDdd())) {
    		FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.ddd"), null));
			return false;
		}
		
		if ("".equals(this.debitoAuto.getTelefone())) {
    		FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.telefone"), null));
			return false;
		}
		
		if ("".equals(this.debitoAuto.getNomeBanco())) {
    		FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.nomebanco"), null));
			return false;
		}
		
		if ("".equals(this.debitoAuto.getBairroBanco())) {
    		FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.bairro"), null));
			return false;
		}
		
		if ("".equals(this.debitoAuto.getCdBanco())) {
    		FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.banco"), null));
			return false;
		}
		
		if ("".equals(this.debitoAuto.getContaCorrente())) {
    		FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.conta"), null));
			return false;
		}
		
		if ("".equals(this.debitoAuto.getAgencia())) {
    		FacesContext.getCurrentInstance().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    				FacesBundleUtil.getInstance().getString("erro.debitoautomatico.agencia"), null));
			return false;
		}		
		
		if (this.debitoAuto.getRgi().length() < 10) {
			while (this.debitoAuto.getRgi().length() < 10) {
				String aux = "0" + this.debitoAuto.getRgi();
				this.debitoAuto.setRgi(aux);
			}
		}
		
		return true;
	}
	
	/**
	 * Volta para a tela do formulário.
	 * @return String
	 */
	public void voltar(ActionEvent e) {
		this.telaSucesso = false;
	}
	
	/**
	 * Conclui o atendimento.
	 * @return String
	 */
	public String concluir() {
		this.telaSucesso = false;
		this.debitoAuto = new DebitoAutomaticoTO();
		return RELATORIO_ATENDIMENTO;
	}
	
	public void atualizarModal(ActionEvent e) {
		this.modalRendered = !modalRendered;
	}

	/**
	 * @return the debitoAuto
	 */
	public DebitoAutomaticoTO getDebitoAuto() {
		return debitoAuto;
	}

	/**
	 * @param debitoAuto the debitoAuto to set
	 */
	public void setDebitoAuto(DebitoAutomaticoTO debitoAuto) {
		this.debitoAuto = debitoAuto;
	}

	/**
	 * @return the telaSucesso
	 */
	public boolean isTelaSucesso() {
		return telaSucesso;
	}

	/**
	 * @param telaSucesso the telaSucesso to set
	 */
	public void setTelaSucesso(boolean telaSucesso) {
		this.telaSucesso = telaSucesso;
	}

	/**
	 * @return the modalRendered
	 */
	public boolean isModalRendered() {
		return modalRendered;
	}

	/**
	 * @param modalRendered the modalRendered to set
	 */
	public void setModalRendered(boolean modalRendered) {
		this.modalRendered = modalRendered;
	}

	@Override
	protected JRDataSource getJRDataSource() {
		ArrayList<DebitoAutomaticoTO> resultado  = new ArrayList<DebitoAutomaticoTO>();
		resultado.add(this.debitoAuto);
	    return new JRBeanCollectionDataSource(resultado);
	}
	@Override
	protected Map<String, Object> getReportParameters() {
		HashMap<String, Object> parameters = new HashMap<String, Object>();

		String caminhoCompletoImagem =  obterCaminhoCompletoApp() + IMAGE_PATH;
		agvlogger.info("Caminho da Imagem " + caminhoCompletoImagem);
		
		parameters.put("image", caminhoCompletoImagem);
		
        return parameters;
	}
	
	private String obterCaminhoCompletoApp(){
		HttpServletRequest request = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
		return "http://" + request.getLocalName() + ":" + request.getLocalPort();
	}

	@Override
	protected InputStream getReportStream() {
		return FindReport.getReport(PATH);
	}

	@SuppressWarnings("unchecked")
	public boolean isExisteMsgErroValidacao() {
		Iterator iter = FacesContext.getCurrentInstance().getMessages();
		existeMsgErroValidacao = iter.hasNext();
		return existeMsgErroValidacao;
	}

	public void setExisteMsgErroValidacao(boolean existeMsgErroValidacao) {
		this.existeMsgErroValidacao = existeMsgErroValidacao;
	}
}
