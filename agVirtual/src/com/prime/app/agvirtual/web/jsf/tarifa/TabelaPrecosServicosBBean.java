package com.prime.app.agvirtual.web.jsf.tarifa;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.prime.app.agvirtual.service.MunicipioService;
import com.prime.app.agvirtual.service.SubServicoService;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.to.SubServicoTO;
import com.prime.app.agvirtual.web.jsf.BasicNavegacaoBBean;
import com.prime.app.agvirtual.web.jsf.component.selectinputtext.UfDictionary;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

@Component
@Scope(value="session")
public class TabelaPrecosServicosBBean extends BasicNavegacaoBBean implements Serializable { 

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -7388947160664984836L;

	/**
	 * Log.
	 */
	private static final Logger agvlogger = LoggerFactory.getLogger(TabelaPrecosServicosBBean.class);
	
	@Autowired
    private MunicipioService municipioService;
	
	@Autowired
	private SubServicoService subServicoService;
	
	private MunicipioTO municipio = new MunicipioTO();
	
	private List<MunicipioTO> listaMunicipio;
	
	private List<SubServicoTO> listaSubServico;

	private String municipioSelecionado;
	
	/**
	 * Armazena o item selecionado no radio da Seção Principal.
	 */
	private String itemSelecionadoRadio;

    private final int municipioLength = 17; //TODO passar para properties
    
    private UfDictionary ufDictionary;
    
    private List<SelectItem> municipioMatchPossibilities;
    
    private boolean exibePainel1 = true;
    
    private boolean exibePainel2 = false;

    /**
	 * Método responsável por pesquisar as tarifas de acordo com os dados selecionados.
	 * @param e ActionEvent
	 */
	public void prosseguir(ActionEvent e) {
		
		if (WrapperUtils.isNull(municipioSelecionado)) {
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Município, preenchimento obrigatório!", null));
		} else {
			try {
				listaSubServico = subServicoService.findAllWithTarifasByMunicipo(new MunicipioTO(WrapperUtils.toLong(municipioSelecionado)));
			} catch (Exception e1) {
				exibeExcecaoCSI();
			}
			
			if (WrapperUtils.isNull(listaSubServico) || listaSubServico.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "A tabela de preços deste município não está disponível.<br/><br/>Telefone: 195<br/>Internet: Atendimento online", null));
			} else {
				exibePainel1 = false;
			    exibePainel2 = true;
			}
		}
    }

	
	/**
	 * Método responsável por limpar as informações.
	 * @return String
	 */
	public String limpar() {
		municipio = new MunicipioTO();
		listaMunicipio = new ArrayList<MunicipioTO>();
		municipioSelecionado = null;
		itemSelecionadoRadio = null;
		municipioMatchPossibilities = new ArrayList<SelectItem>();
	    exibePainel1 = true;
	    exibePainel2 = false;
		
		return TABELA_PRECOS_SERVICOS;
	}
	
	 /**
     * <p>Called by the selectInputText component at set intervals.  By using
     * the change event we can can get the newly typed work and do a look up in
     * the city dictionary.  The list of possible cities calculatd from the city
     * dictionary is assigned back to the component for display.</p>
     * <p>If the component selected a value then we find the respective city
     * information for dispaly purposes.
     *
     * @param event jsf value change event.
     */
    @SuppressWarnings("unchecked")
	public void selectInputValueChanged(ValueChangeEvent event) {

        if (event.getComponent() instanceof SelectInputText) {
        	// get the number of displayable records from the component
            SelectInputText autoComplete =
                    (SelectInputText) event.getComponent();
            // get the new value typed by component user.
            String newWord = (String) event.getNewValue();

            municipioMatchPossibilities = UfDictionary.generateCityMatches(newWord, municipioLength);

            // if there is a selected item then find the city object of the
            // same name
            if (autoComplete.getSelectedItem() != null) {
                try {
					municipio = (MunicipioTO) BeanUtils.cloneBean((MunicipioTO) autoComplete.getSelectedItem().getValue());
				} catch (IllegalAccessException e) {
					agvlogger.error(e.getMessage());
				} catch (InstantiationException e) {
					agvlogger.error(e.getMessage());
				} catch (InvocationTargetException e) {
					agvlogger.error(e.getMessage());
				} catch (NoSuchMethodException e) {
					agvlogger.error(e.getMessage());
				}
				municipioSelecionado = municipio.getIdMun().toString();
            } else {
            	municipioSelecionado = null;
			}
            itemSelecionadoRadio = null;
        }
    }
    
    private void exibeExcecaoCSI() {
    	FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_ERROR,FacesBundleUtil.getInstance().getString("erro.pesquisa.csi"), null));
    }
	
	/**
	 * Lista de município do tipo SelectItem.
	 * 
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getListaMunicipioAsSelectItem() {
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		for (MunicipioTO municipio : getListaMunicipio()) {
			SelectItem item = new SelectItem(String.valueOf(municipio.getIdMun()), municipio.getNome());
			lista.add(item);
		}
		
		return lista;
	}
	
	public MunicipioTO getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioTO municipio) {
		this.municipio = municipio;
	}

	public List<MunicipioTO> getListaMunicipio() {
		return listaMunicipio;
	}

	public void setListaMunicipio(List<MunicipioTO> listaMunicipio) {
		this.listaMunicipio = listaMunicipio;
	}

	public List<SubServicoTO> getListaSubServico() {
		return listaSubServico;
	}

	public void setListaSubServico(List<SubServicoTO> listaSubServico) {
		this.listaSubServico = listaSubServico;
	}

	public String getMunicipioSelecionado() {
		return municipioSelecionado;
	}

	public void setMunicipioSelecionado(String municipioSelecionado) {
		this.municipioSelecionado = municipioSelecionado;
	}

	public String getItemSelecionadoRadio() {
		return itemSelecionadoRadio;
	}

	public void setItemSelecionadoRadio(String itemSelecionadoRadio) {
		this.itemSelecionadoRadio = itemSelecionadoRadio;
	}

	public UfDictionary getUfDictionary() {
		return ufDictionary;
	}

	public void setUfDictionary(UfDictionary ufDictionary) {
		this.ufDictionary = ufDictionary;
	}

	@SuppressWarnings("unchecked")
	public List<SelectItem> getMunicipioMatchPossibilities() {
		try {
			ufDictionary =  new UfDictionary(((ArrayList)municipioService.findAll()));
		} catch (Exception e) {
			exibeExcecaoCSI();
		}
		
		return municipioMatchPossibilities;
	}

	public void setMunicipioMatchPossibilities(
			List<SelectItem> municipioMatchPossibilities) {
		this.municipioMatchPossibilities = municipioMatchPossibilities;
	}

	public boolean isExibePainel1() {
		return exibePainel1;
	}

	public void setExibePainel1(boolean exibePainel1) {
		this.exibePainel1 = exibePainel1;
	}

	public boolean isExibePainel2() {
		return exibePainel2;
	}

	public void setExibePainel2(boolean exibePainel2) {
		this.exibePainel2 = exibePainel2;
	}
}