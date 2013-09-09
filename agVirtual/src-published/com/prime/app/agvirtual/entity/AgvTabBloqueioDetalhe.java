package com.prime.app.agvirtual.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabBloqueioDetalhe.findAll", query = "select o from AgvTabBloqueioDetalhe o")
})
@Table(name = "AGV_TAB_BLOQUEIO_DETALHE", schema = Schema.DB_OWNER)
public class AgvTabBloqueioDetalhe implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4647396451376553549L;
	
	@Id
	@Column(name = "CD_BLOQUEIO_DETALHE", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_BLOQUEIO_DET", sequenceName = "SQ_AGV_BLOQUEIO_DET", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_BLOQUEIO_DET")
    private Long cdBloqueioDetalhe;
	
    @Column(name="CD_MUNICIPIO")
    private Long cdMunicipio;
    
    @OneToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="CD_MUNICIPIO" ,insertable=false,updatable=false)
    private AgvTabMunicipio municipio;
    
    @Column(name="CD_UN")
    private String cdUn;
    
    @ManyToOne
    @JoinColumn(name = "CD_ITEM_MENU_ACESSO")
    private AgvTabItemMenuAcesso agvTabItemMenuAcesso;
    
    @ManyToOne
    @JoinColumn(name = "CD_BLOQUEIO")
    private AgvTabBloqueio agvTabBloqueio;
    
    @Column(name="FL_TODAS_MUNICIPIO", length = 1)
    private Boolean todosMunicipios;
    
    @Column(name="FL_TODAS_FUNC", length = 1)
    private Boolean todosFuncionalidade;

    public AgvTabBloqueioDetalhe() {
    }

    public Long getCdBloqueioDetalhe() {
        return cdBloqueioDetalhe;
    }

    public void setCdBloqueioDetalhe(Long cdBloqueioDetalhe) {
        this.cdBloqueioDetalhe = cdBloqueioDetalhe;
    }


    public Long getCdMunicipio() {
        return cdMunicipio;
    }

    public void setCdMunicipio(Long cdMunicipio) {
        this.cdMunicipio = cdMunicipio;
    }

    public String getCdUn() {
        return cdUn;
    }

    public void setCdUn(String cdUn) {
        this.cdUn = cdUn;
    }

    public AgvTabBloqueio getAgvTabBloqueio() {
        return agvTabBloqueio;
    }

    public void setAgvTabBloqueio(AgvTabBloqueio agvTabBloqueio) {
        this.agvTabBloqueio = agvTabBloqueio;
    }

	public AgvTabItemMenuAcesso getAgvTabItemMenuAcesso() {
		return agvTabItemMenuAcesso;
	}

	public void setAgvTabItemMenuAcesso(AgvTabItemMenuAcesso agvTabItemMenuAcesso) {
		this.agvTabItemMenuAcesso = agvTabItemMenuAcesso;
	}

	public AgvTabMunicipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(AgvTabMunicipio municipio) {
		this.municipio = municipio;
	}

	public Boolean getTodosMunicipios() {
		return todosMunicipios;
	}

	public void setTodosMunicipios(Boolean todosMunicipios) {
		this.todosMunicipios = todosMunicipios;
	}

	public Boolean getTodosFuncionalidade() {
		return todosFuncionalidade;
	}

	public void setTodosFuncionalidade(Boolean todosFuncionalidade) {
		this.todosFuncionalidade = todosFuncionalidade;
	}
}
