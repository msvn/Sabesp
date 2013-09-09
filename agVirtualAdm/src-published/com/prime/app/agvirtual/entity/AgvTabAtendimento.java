package com.prime.app.agvirtual.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "AGV_TAB_ATENDIMENTO", schema = Schema.DB_OWNER)
public class AgvTabAtendimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7384094706789980895L;

	@Id
	@Column(name = "CD_ATENDIMENTO", nullable = true)
	@SequenceGenerator(name = "SQ_AGV_ATENDIMENTO", sequenceName = "SQ_AGV_ATENDIMENTO", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_ATENDIMENTO")
	private Long cdAtendimento;

	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CD_SESSAO", length = 18)
	private Long cdSessao;

	@Column(name = "DT_INICIO")
	private Date dtInclusao;

	@Column(name = "DT_FINAL")
	private Date dtFinal;

	@Column(name = "CD_STA_ATENDIMENTO", length = 18)
	private Integer cdStaAtendimento;

	@Column(name = "DS_END_IP", length = 40)
	private String nrIp;

	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "atendimento", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<AgvLogAcesso> listaLog;

	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "atendimento", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<AgvTabAutoatendimentoAcess> listaAutoAtendimentoAcess;

	public AgvTabAtendimento() {
	}

	public String getNrIp() {
		return nrIp;
	}

	public void setNrIp(String nrIp) {
		this.nrIp = nrIp;
	}

	public Long getCdAtendimento() {
		return cdAtendimento;
	}

	public void setCdAtendimento(Long cdAtendimento) {
		this.cdAtendimento = cdAtendimento;
	}

	public Long getCdSessao() {
		return cdSessao;
	}

	public void setCdSessao(Long cdSessao) {
		this.cdSessao = cdSessao;
	}

	public Date getDtInclusao() {
		return dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	public Date getDtFinal() {
		return dtFinal;
	}

	public void setDtFinal(Date dtFinal) {
		this.dtFinal = dtFinal;
	}

	public Integer getCdStaAtendimento() {
		return cdStaAtendimento;
	}

	public void setCdStaAtendimento(Integer cdStaAtendimento) {
		this.cdStaAtendimento = cdStaAtendimento;
	}

	public List<AgvLogAcesso> getListaLog() {
		return listaLog;
	}

	public void setListaLog(List<AgvLogAcesso> listaLog) {
		this.listaLog = listaLog;
	}

	public List<AgvTabAutoatendimentoAcess> getListaAutoAtendimentoAcess() {
		return listaAutoAtendimentoAcess;
	}

	public void setListaAutoAtendimentoAcess(
			List<AgvTabAutoatendimentoAcess> listaAutoAtendimentoAcess) {
		this.listaAutoAtendimentoAcess = listaAutoAtendimentoAcess;
	}

}
