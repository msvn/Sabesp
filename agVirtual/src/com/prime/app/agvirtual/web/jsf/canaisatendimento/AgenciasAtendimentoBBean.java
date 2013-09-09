package com.prime.app.agvirtual.web.jsf.canaisatendimento;

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
import com.prime.app.agvirtual.service.AgenciaService;
import com.prime.app.agvirtual.service.MunicipioService;
import com.prime.app.agvirtual.to.AgenciaTO;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.web.jsf.BasicNavegacaoBBean;
import com.prime.app.agvirtual.web.jsf.ErasingBean;
import com.prime.app.agvirtual.web.jsf.component.selectinputtext.UfDictionary;

/**
 * BackBean com controles da funcionalidade Canal de Atendimento.
 */
@Component
@Scope(value="session")
public class AgenciasAtendimentoBBean extends BasicNavegacaoBBean implements Serializable, ErasingBean{

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -5938633193756902772L;
	
	/**
	 * Log.
	 */
	private static final Logger agvlogger = LoggerFactory.getLogger(AgenciasAtendimentoBBean.class);
	
	@Autowired
    private MunicipioService municipioService;
	
	@Autowired
	private AgenciaService agenciaService;
	
	private MunicipioTO municipio = new MunicipioTO();
	
	private AgenciaTO agencia;
	
	private List<MunicipioTO> listaMunicipio;
	
	private List<AgenciaTO> listaAgencia;
	
	private String agenciaSelecionada;
	
	private String municipioSelecionado;

    private final int municipioLength = 17; //TODO passar para properties
    
    private UfDictionary ufDictionary;
    
    private List<SelectItem> municipioMatchPossibilities;
    
    private boolean exibePainel1 = true;
    
    private boolean exibePainel2 = false;
    
    private boolean exibePainel3 = false;
    
    private boolean exibePainel4 = false;

    /**
	 * Método responsável por pesquisar a(s) agência(s) de acordo com o município selecionado.
	 * @param e ActionEvent
	 */
	public void prosseguir(ActionEvent e) {
		if (municipioSelecionado == null || "".equalsIgnoreCase(municipioSelecionado)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Campo Município, preenchimento obrigatório!", null));
		} else {
	    	listaAgencia = agenciaService.findByMunicipio(municipioSelecionado);
	    	
	    	if (listaAgencia.isEmpty()) {
	    		FacesContext.getCurrentInstance().addMessage(null,
	    			new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Não há agências para este município!", null));
			} else if (listaAgencia.size() == 1) {
	    		agencia = listaAgencia.get(0);
	    		exibePainel1 = false;
				exibePainel2 = true;
			} else if (listaAgencia.size() > 1) {
				exibePainel1 = false;
				exibePainel3 = true;
			}
		}
    }
	
	/**
	 * Método responsável por exibir as informações da(s) agência(s) de acordo com a opção selecionada na combo.
	 * @param e ActionEvent
	 */
	public void exibirAgencias(ActionEvent e) {
		
		if ("Selecione".equalsIgnoreCase(agenciaSelecionada)) {
			//Exibe erro
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Campo Agência, preenchimento obrigatório!", null));
		} else if ("Todas".equalsIgnoreCase(agenciaSelecionada)) {
			agenciaService.findEnderecoTodasAgencias(listaAgencia);
			exibePainel3 = false;
			exibePainel4 = true;
		} else {
			for (AgenciaTO agenciaTO : getListaAgencia()) {
				if (agenciaTO.getNmAbrUnid().equalsIgnoreCase(agenciaSelecionada)) {
					agenciaService.findEnderecoByAgencia(agenciaTO);
					agencia = agenciaTO;
					exibePainel3 = false;
					exibePainel2 = true;
					break;
				}
			}
		}
		
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
        }
    }

	/**
	 * Lista de agências do município.
	 * 
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getAgenciasByMunicipio() {
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		SelectItem item = new SelectItem("Selecione", "Selecione");
		lista.add(item);
		item = new SelectItem("Todas", "Todas");
		lista.add(item);
		for (AgenciaTO agencia : getListaAgencia()) {
			item = new SelectItem(agencia.getNmAbrUnid(), agencia.getNmAbrUnid());
			lista.add(item);
		}
		return lista;
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

	public AgenciaTO getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaTO agencia) {
		this.agencia = agencia;
	}

	public List<MunicipioTO> getListaMunicipio() {
		return listaMunicipio;
	}

	public void setListaMunicipio(List<MunicipioTO> listaMunicipio) {
		this.listaMunicipio = listaMunicipio;
	}

	public List<AgenciaTO> getListaAgencia() {
		return listaAgencia;
	}

	public void setListaAgencia(List<AgenciaTO> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}

	public String getAgenciaSelecionada() {
		return agenciaSelecionada;
	}

	public void setAgenciaSelecionada(String agenciaSelecionada) {
		this.agenciaSelecionada = agenciaSelecionada;
	}

	public String getMunicipioSelecionado() {
		return municipioSelecionado;
	}

	public void setMunicipioSelecionado(String municipioSelecionado) {
		this.municipioSelecionado = municipioSelecionado;
	}

	public UfDictionary getUfDictionary() {
		return ufDictionary;
	}

	public void setUfDictionary(UfDictionary ufDictionary) {
		this.ufDictionary = ufDictionary;
	}

	@SuppressWarnings("unchecked")
	public List<SelectItem> getMunicipioMatchPossibilities() {
		ufDictionary =  new UfDictionary(((ArrayList)municipioService.findAll()));

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

	public boolean isExibePainel3() {
		return exibePainel3;
	}

	public void setExibePainel3(boolean exibePainel3) {
		this.exibePainel3 = exibePainel3;
	}

	public boolean isExibePainel4() {
		return exibePainel4;
	}

	public void setExibePainel4(boolean exibePainel4) {
		this.exibePainel4 = exibePainel4;
	}

	public void eraseBeanAtributes() {
		municipio = new MunicipioTO();
		agencia = new AgenciaTO();
		listaMunicipio = new ArrayList<MunicipioTO>();
		listaAgencia = new ArrayList<AgenciaTO>();
		agenciaSelecionada = "";
		municipioSelecionado = "";
	    exibePainel1 = true;
	    exibePainel2 = false;
	    exibePainel3 = false;
	    exibePainel4 = false;
	}
}
