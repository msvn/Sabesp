package com.prime.app.agvirtual.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "AgvTabAutoatendimento.findAll", query = "select o from AgvTabAutoatendimento o") })
@Table(name = "AGV_TAB_AUTOATENDIMENTO", schema = Schema.DB_OWNER)
@Inheritance(strategy = InheritanceType.JOINED)
public class AgvTabAutoatendimento implements BaseEntity {
	private static final long serialVersionUID = 161717126994607602L;

	@Id
	@Column(name = "CD_AUTOATENDIMENTO", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_AUTOATENDIMENTO", sequenceName = "SQ_AGV_AUTOATENDIMENTO", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_AUTOATENDIMENTO")
	private Long cdAutoatendimento;

	@Column(name = "DS_AUTOATENDIMENTO", length = 60)
	private String dsAutoatendimento;

//	@OneToMany(mappedBy = "agvTabAutoatendimento", fetch = FetchType.EAGER)
//	private List<AgvTabCorrelacao> listaCorrelacao;
	
	public Object parseTO() {
		// TODO Auto-generated method stub
		return null;
	}

	public AgvTabAutoatendimento() {
	}

	public AgvTabAutoatendimento(Long cdAutoatendimento, String dsAutoatendimento) {
		this.cdAutoatendimento = cdAutoatendimento;
		this.dsAutoatendimento = dsAutoatendimento;
	}

	public Long getCdAutoatendimento() {
		return cdAutoatendimento;
	}

	public void setCdAutoatendimento(Long cdAutoatendimento) {
		this.cdAutoatendimento = cdAutoatendimento;
	}

	public String getDsAutoatendimento() {
		return dsAutoatendimento;
	}

	public void setDsAutoatendimento(String dsAutoatendimento) {
		this.dsAutoatendimento = dsAutoatendimento;
	}

	public List<AgvTabCorrelacao> getListaCorrelacao() {
		return null;
	}
	
	
//	public List<AgvTabCorrelacao> getListaCorrelacao() {
//		return listaCorrelacao;
//	}
//
//	public void setListaCorrelacao(List<AgvTabCorrelacao> listaCorrelacao) {
//		this.listaCorrelacao = listaCorrelacao;
//	}
	

}
