package com.prime.app.agvirtual.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@NamedQueries( { @NamedQuery(name = "AutoAtendimentoAcessado.findAll", query = "select o from AutoAtendimentoAcessado o") })
@Table(name = "AGV_TAB_AUTOAT_ACESSADO", schema = Schema.DB_OWNER)
@Inheritance(strategy=InheritanceType.JOINED)
public class AutoAtendimentoAcessado implements Serializable {
	private static final long serialVersionUID = 161717126994607602L;

	@Id
	@Column(name = "CD_AUTOAT_ACESSADO", nullable = false)
	@SequenceGenerator(name = "", sequenceName = "", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "")
	private Long cdAutoAtendimentoAcess;

	@ManyToOne
	@JoinColumn(name = "CD_ATENDIMENTO")
	private Atendimento atendimento;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "CD_AUTOATENDIMENTO")
	private AgvTabAutoatendimento autoAtendimento;

	@ManyToOne
	@JoinColumn(name = "CD_STA_ATENDIMENTO")
	private SituacaoAtendimento situacaoAtendimento;

	@Column(name = "CD_ATEND_COMERCIAL")
	private Long cdAtendComercial;

	@ManyToOne
	@JoinColumn(name = "CD_MOTIVO_INSUCESSO")
	private MotivoInsucesso motivoInsucesso;

	@Column(name = "CD_MUNICIPIO")
	private Integer cdMunicipio;

	@Column(name = "CD_RGI")
	private Long cdRgi;

	@Column(name = "CD_UN", length = 2)
	private String cdUnidadeNegocio;

	@Column(name = "DS_DOCUMENTO", length = 20)
	private String documento;

	@Column(name = "DT_FIM")
	private Date dtFim;

	@Column(name = "DT_INICIO")
	private Date dtInicio;

	@Column(name = "DS_EMAIL", length = 40)
	private String email;

	@Column(name = "DS_RELACAO_SOLICITANTE", length = 30)
	private String relacaoSolicitante;

	@Column(name = "DS_SOLICITANTE", length = 60)
	private String socilicitante;

	@Column(name = "DS_TELEFONE", length = 20)
	private String telefone;

	@Transient
	private int status;

	@Transient
	private String observacao;

	public Long getCdAtendComercial() {
		return cdAtendComercial;
	}

	public Long getCdRgi() {
		return cdRgi;
	}

	public String getCdUnidadeNegocio() {
		return cdUnidadeNegocio;
	}

	public String getDocumento() {
		return documento;
	}

	public Date getDtFim() {
		return dtFim;
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public String getEmail() {
		return email;
	}

	public String getRelacaoSolicitante() {
		return relacaoSolicitante;
	}

	public String getSocilicitante() {
		return socilicitante;
	}

	public String getTelefone() {
		return telefone;
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public void setCdAtendComercial(Long cdAtendComercial) {
		this.cdAtendComercial = cdAtendComercial;
	}

	public void setCdRgi(Long cdRgi) {
		this.cdRgi = cdRgi;
	}

	public void setCdUnidadeNegocio(String cdUnidadeNegocio) {
		this.cdUnidadeNegocio = cdUnidadeNegocio;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRelacaoSolicitante(String relacaoSolicitante) {
		this.relacaoSolicitante = relacaoSolicitante;
	}

	public void setSocilicitante(String socilicitante) {
		this.socilicitante = socilicitante;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public AgvTabAutoatendimento getAutoAtendimento() {
		return autoAtendimento;
	}

	public void setAutoAtendimento(AgvTabAutoatendimento autoAtendimento) {
		this.autoAtendimento = autoAtendimento;
	}

	public Integer getCdMunicipio() {
		return cdMunicipio;
	}

	public void setCdMunicipio(Integer cdMunicipio) {
		this.cdMunicipio = cdMunicipio;
	}

	public Long getCdAutoAtendimentoAcess() {
		return cdAutoAtendimentoAcess;
	}

	public void setCdAutoAtendimentoAcess(Long cdAutoAtendimentoAcess) {
		this.cdAutoAtendimentoAcess = cdAutoAtendimentoAcess;
	}

	public SituacaoAtendimento getSituacaoAtendimento() {
		return situacaoAtendimento;
	}

	public void setSituacaoAtendimento(SituacaoAtendimento situacaoAtendimento) {
		this.situacaoAtendimento = situacaoAtendimento;
	}

	public MotivoInsucesso getMotivoInsucesso() {
		return motivoInsucesso;
	}

	public void setMotivoInsucesso(MotivoInsucesso motivoInsucesso) {
		this.motivoInsucesso = motivoInsucesso;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
