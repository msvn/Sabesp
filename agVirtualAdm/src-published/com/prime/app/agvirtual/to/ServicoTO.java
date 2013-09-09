package com.prime.app.agvirtual.to;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabServico;
import com.prime.app.agvirtual.entity.AgvTabSubservico;
import com.prime.infra.util.WrapperUtils;

public class ServicoTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3778659581903234158L;
	

	private Long cdServico;

	private String dsObservacao;

	private String nmServico;
	
	private String cdCategoria;

	private List<SubServicoTO> listaSubServico;
	
	private boolean selected;

    public ServicoTO() {
    }

    public ServicoTO(Long cdServico, String dsObservacao,
                         String nmServico , String cdCategoria) {
        this.cdServico = cdServico;
        this.dsObservacao = dsObservacao;
        this.nmServico = nmServico;
        this.cdCategoria = cdCategoria;
    }
    
    public ServicoTO(String nmServ, int i) {
    	this.nmServico = nmServ;
    	this.cdServico = WrapperUtils.toLong(i);
	}

	public AgvTabServico toEntity(List<SubServicoTO> listaSubServicoAdicionados) {
    	AgvTabServico entity =  new AgvTabServico();
    	entity.setCdServico(cdServico);
    	entity.setDsObservacao(dsObservacao);
    	entity.setNmServico(nmServico);
    	entity.setCdCategoria(cdCategoria);
    	entity.setAgvListaServSubServ(listToEntitySubServ(listaSubServicoAdicionados));
    	
		return entity;
	}
    
    public List<AgvTabSubservico> listToEntitySubServ(List<SubServicoTO> listaSubServicoAdicionados){
    	List<AgvTabSubservico>  temp =  new ArrayList<AgvTabSubservico>();
    	if(listaSubServicoAdicionados != null){
	    	for (Iterator<SubServicoTO> iterator = listaSubServicoAdicionados.iterator(); iterator.hasNext();) {
	    		SubServicoTO element = (SubServicoTO) iterator.next();
				temp.add(element.toEntity());
			}
    	}
		return temp;
    }

	public Long getCdServico() {
		return cdServico;
	}

	public void setCdServico(Long cdServico) {
		this.cdServico = cdServico;
	}

	public String getDsObservacao() {
		this.dsObservacao = (this.dsObservacao != null ? this.dsObservacao.trim() : "");
		return dsObservacao;
	}

	public void setDsObservacao(String dsObservacao) {
		this.dsObservacao = dsObservacao;
	}

	public String getNmServico() {
		this.nmServico = (this.nmServico != null ? this.nmServico.trim() : "");
		return nmServico;
	}

	public void setNmServico(String nmServico) {
		this.nmServico = nmServico;
	}

	public List<SubServicoTO> getListaSubServico() {
		if(listaSubServico == null){
			listaSubServico =  new ArrayList<SubServicoTO>();
		}
		return listaSubServico;
	}

	public void setListaSubServico(List<SubServicoTO> listaSubServico) {
		this.listaSubServico = listaSubServico;
	}

	public String getCdCategoria() {
		return cdCategoria;
	}

	public void setCdCategoria(String cdCategoria) {
		this.cdCategoria = cdCategoria;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
}