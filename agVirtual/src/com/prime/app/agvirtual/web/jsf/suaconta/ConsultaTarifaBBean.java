package com.prime.app.agvirtual.web.jsf.suaconta;

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
import com.prime.app.agvirtual.service.TarifaService;
import com.prime.app.agvirtual.to.CategoriaTO;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.to.TarifaTO;
import com.prime.app.agvirtual.web.jsf.BasicNavegacaoBBean;
import com.prime.app.agvirtual.web.jsf.component.selectinputtext.UfDictionary;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.BasicBBean;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

@Component
@Scope(value="session")
public class ConsultaTarifaBBean extends BasicNavegacaoBBean implements Serializable { 

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -7388947160664984836L;

	/**
	 * Log.
	 */
	private static final Logger agvlogger = LoggerFactory.getLogger(ConsultaTarifaBBean.class);
	
	@Autowired
    private MunicipioService municipioService;
	
	@Autowired
	private TarifaService tarifaService;
	
	private MunicipioTO municipio = new MunicipioTO();
	
	private List<MunicipioTO> listaMunicipio;
	
	private List<CategoriaTO> listaCategoria;
	
	private List<List<TarifaTO>> listaTarifa;

	private String municipioSelecionado;
	
	private String categoriaSelecionada;
	
	/**
	 * Armazena o item selecionado no radio da Seção Principal.
	 */
	private String itemSelecionadoRadio;

    private final int municipioLength = 17; //TODO passar para properties
    
    private UfDictionary ufDictionary;
    
    private List<SelectItem> municipioMatchPossibilities;
    
    private boolean exibePainel1 = true;
    
    private boolean exibePainel2 = false;

    private String dataVigencia = WrapperUtils.parseDate(WrapperUtils.getFirstDayFromNextMonth(), WrapperUtils.DATA_POR_EXTENSO);
  
    /**
	 * Método responsável por pesquisar as tarifas de acordo com os dados selecionados.
	 * @param e ActionEvent
	 */
	public void prosseguir(ActionEvent e) {
		if (municipioSelecionado == null || "".equalsIgnoreCase(municipioSelecionado)) {
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Município, preenchimento obrigatório!", null));
		} else if (categoriaSelecionada == null || "-1".equalsIgnoreCase(categoriaSelecionada)) {
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Categoria, preenchimento obrigatório!", null));
		} else if (itemSelecionadoRadio == null || "".equalsIgnoreCase(itemSelecionadoRadio)) {
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Tarifa, preenchimento obrigatório!", null));
		} else {
			try {
				listaTarifa = tarifaService.findTarifaByParams(municipioSelecionado, categoriaSelecionada, itemSelecionadoRadio);
			} catch (Exception e1) {
				exibeExcecaoCSI();
			}
			
			if (listaTarifa == null || listaTarifa.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "As tarifas deste município não estão disponíveis.<br/><br/>Telefone: 195<br/>Internet: Atendimento online", null));
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
		listaCategoria = new ArrayList<CategoriaTO>();
		municipioSelecionado = null;
		categoriaSelecionada = null;
		itemSelecionadoRadio = null;
		municipioMatchPossibilities = new ArrayList<SelectItem>();
	    exibePainel1 = true;
	    exibePainel2 = false;
		
		return "SUA_CONTA_CONSULTA_TARIFA";
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
            categoriaSelecionada = null;
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
	
	/**
	 * Lista de agências do município.
	 * 
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getCategoriasAsMunicipio() {
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		SelectItem item = new SelectItem("-1", "Selecione");
		lista.add(item);
		item = new SelectItem("0", "Todas");
		lista.add(item);
		for (CategoriaTO categoria : getListaCategoria()) {
			item = new SelectItem(String.valueOf(categoria.getCodigo()), categoria.getNome());
			lista.add(item);
		}
		return lista;
	}
	
	/**
	 * Lista de quantidade de Itens Seção Principal.
	 * 
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getListaTipoTarifa() {
		List<TarifaTO> listaTipoTarifa = new ArrayList<TarifaTO>();
		ArrayList<SelectItem> lista = new ArrayList<SelectItem>();
		
		MunicipioTO municipio = new MunicipioTO();
		municipio.setIdMun(Long.parseLong(municipioSelecionado));
		try {
			listaTipoTarifa = tarifaService.findTipoTarifaByMunicipio(municipio);
		} catch (Exception e) {
			exibeExcecaoCSI();
		}
		itemSelecionadoRadio = null;

		for (TarifaTO tarifaTO : listaTipoTarifa) {
			SelectItem item = new SelectItem(String.valueOf(tarifaTO.getCdTipoTarifa()), tarifaTO.getNmTipoTarifa());
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

	public List<CategoriaTO> getListaCategoria() {
		
		if (listaCategoria == null || listaCategoria.isEmpty()) {
			try {
				listaCategoria = tarifaService.findCategoria();
			}catch (Exception e) {
				exibeExcecaoCSI();
			}
			categoriaSelecionada = null;
		}
		return listaCategoria;
	}

	public void setListaCategoria(List<CategoriaTO> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public List<List<TarifaTO>> getListaTarifa() {
		return listaTarifa;
	}


	public void setListaTarifa(List<List<TarifaTO>> listaTarifa) {
		this.listaTarifa = listaTarifa;
	}


	public String getMunicipioSelecionado() {
		return municipioSelecionado;
	}

	public void setMunicipioSelecionado(String municipioSelecionado) {
		this.municipioSelecionado = municipioSelecionado;
	}

	public String getCategoriaSelecionada() {
		return categoriaSelecionada;
	}

	public void setCategoriaSelecionada(String categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
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


	public String getDataVigencia() {
		return dataVigencia;
	}


	public void setDataVigencia(String dataVigencia) {
		this.dataVigencia = dataVigencia;
	}
	
	
}