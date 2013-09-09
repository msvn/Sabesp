package com.prime.app.agvirtual.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabMunicipioUnidadeNegocio.findAll", query = "select o from AgvTabMunicipioUnidadeNegocio o")
})
@Table(name = "AGV_TAB_MUNICIPI_UN", schema = Schema.DB_OWNER)
public class AgvTabMunicipioUnidadeNegocio implements Serializable {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 7206598260398465916L;

	@Id
	@Column(name = "CD_BLOQUEIO", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_BLOQUEIO", sequenceName = "SQ_AGV_BLOQUEIO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_BLOQUEIO")
    private Long cdBloqueio;
	
	 @OneToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="CD_MUNICIPIO" ,insertable=false,updatable=false)
    private AgvTabMunicipio municipio;
	
	@Column(name="CD_MUNICIPIO")
    private Long cdMunicipio;
	
	@Column(name="CD_UN")
	private Integer cdUnidadeNegocio;
	
	@OneToOne(mappedBy="municipioUnidadeNegocio" , fetch = FetchType.LAZY)
	private AgvTabBloqueio bloqueio;

	public Long getCdBloqueio() {
		return cdBloqueio;
	}

	public void setCdBloqueio(Long cdBloqueio) {
		this.cdBloqueio = cdBloqueio;
	}

	public AgvTabMunicipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(AgvTabMunicipio municipio) {
		this.municipio = municipio;
	}

	public Long getCdMunicipio() {
		return cdMunicipio;
	}

	public void setCdMunicipio(Long cdMunicipio) {
		this.cdMunicipio = cdMunicipio;
	}

	public Integer getCdUnidadeNegocio() {
		return cdUnidadeNegocio;
	}

	public void setCdUnidadeNegocio(Integer cdUnidadeNegocio) {
		this.cdUnidadeNegocio = cdUnidadeNegocio;
	}

	public AgvTabBloqueio getBloqueio() {
		return bloqueio;
	}

	public void setBloqueio(AgvTabBloqueio bloqueio) {
		this.bloqueio = bloqueio;
	}
	
}
