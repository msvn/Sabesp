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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQueries( { @NamedQuery(name = "Atendimento.findAll", query = "select o from Atendimento o") })
@Table(name = "AGV_TAB_ATENDIMENTO", schema = Schema.DB_OWNER)
public class Atendimento implements Serializable {
	private static final long serialVersionUID = -7384094706789980895L;

	@Id
	@Column(name = "CD_ATENDIMENTO", nullable = true)
	@SequenceGenerator(name = "SQ_AGV_ATENDIMENTO", sequenceName = "SQ_AGV_ATENDIMENTO", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_ATENDIMENTO")
	private Long cdAtendimento;

	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CD_SESSAO", length = 18)
	private String cdSessao;

	@Column(name = "DT_INICIO")
	private Date dtInclusao;

	@Column(name = "DT_FINAL")
	private Date dtFinal;

	@Column(name = "CD_STA_ATENDIMENTO", length = 18)
	private Long cdStaAtendimento;

	@Column(name = "DS_END_IP", length = 40)
	private String nrIp;

	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "atendimento", fetch = FetchType.EAGER)
	private List<LogAcesso> listaLog;

	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "atendimento")
	private List<AutoAtendimentoAcessado> listaAutoAtendimentoAcess;

	public Atendimento() {}
	
	public Atendimento(Long cdAtendimento, String cdSessao,
			Long cdStaAtendimento, Date dtFinal, Date dtInclusao,
			List<AutoAtendimentoAcessado> listaAutoAtendimentoAcess,
			List<LogAcesso> listaLog, String nrIp) {
		this.cdAtendimento = cdAtendimento;
		this.cdSessao = cdSessao;
		this.cdStaAtendimento = cdStaAtendimento;
		this.dtFinal = dtFinal;
		this.dtInclusao = dtInclusao;
		this.listaAutoAtendimentoAcess = listaAutoAtendimentoAcess;
		this.listaLog = listaLog;
		this.nrIp = nrIp;
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

	public String getCdSessao() {
		return cdSessao;
	}

	public void setCdSessao(String cdSessao) {
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

	public Long getCdStaAtendimento() {
		return cdStaAtendimento;
	}

	public void setCdStaAtendimento(Long cdStaAtendimento) {
		this.cdStaAtendimento = cdStaAtendimento;
	}

	public List<LogAcesso> getListaLog() {
		return listaLog;
	}

	public void setListaLog(List<LogAcesso> listaLog) {
		this.listaLog = listaLog;
	}

	public List<AutoAtendimentoAcessado> getListaAutoAtendimentoAcess() {
		return listaAutoAtendimentoAcess;
	}

	public void setListaAutoAtendimentoAcess(
			List<AutoAtendimentoAcessado> listaAutoAtendimentoAcess) {
		this.listaAutoAtendimentoAcess = listaAutoAtendimentoAcess;
	}

}
