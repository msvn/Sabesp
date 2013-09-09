package com.prime.app.agvirtual.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.prime.app.agvirtual.to.MunicipioTO;

@Entity
@Table(name = "AGV_TAB_MUNICIPIO" , schema = Schema.DB_OWNER)
public class AgvTabMunicipio implements Serializable {
	
	@Id
	@Column(name = "CD_MUNICIPIO", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_UF", sequenceName = "SQ_AGV_UF", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_UF")
	private Long id;
	
	@Column(name="CD_UN")
	private Long codigoUf;
	
	@Column(name="DS_MUNICIPIO", length = 50)
	private String nomeMunicipio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigoUf() {
		return codigoUf;
	}

	public void setCodigoUf(Long codigoUf) {
		this.codigoUf = codigoUf;
	}

	public String getNomeMunicipio() {
		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio) {
		this.nomeMunicipio = nomeMunicipio;
	}

	public MunicipioTO parseTo() {
		MunicipioTO to =  new MunicipioTO();
		to.setCodUf(codigoUf);
		to.setNome(nomeMunicipio);
		to.setIdMun(id);
		return to;
	}

}
