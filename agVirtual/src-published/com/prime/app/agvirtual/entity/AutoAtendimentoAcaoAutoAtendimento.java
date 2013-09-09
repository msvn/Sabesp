package com.prime.app.agvirtual.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.persistence.Transient;

@Entity
@NamedQueries({
  @NamedQuery(name = "AutoAtendimentoAcaoAutoAtendimento.findAll", query = "select o from AutoAtendimentoAcaoAutoAtendimento o")
})
@Table(name = "AGV_TAB_AUTOAT_ACAO_AUTOAT", schema = Schema.DB_OWNER)
public class AutoAtendimentoAcaoAutoAtendimento implements Serializable{
	private static final long serialVersionUID = -3745725235800539197L;
	
	@Id
	@Column(name = "CD_AUTOAT_ACAO", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_AUTOAT_ACAO", sequenceName = "SQ_AGV_AUTOAT_ACAO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_AUTOAT_ACAO")
    private Long cdAutoAtendimentoAcao;
	
	@OneToOne
	@JoinColumn(name="CD_AUTOAT_ACESS_ACAO")
	private AutoAtendimentoAcessadoAcao autoAtendimentoAcessado;
	
	@OneToOne
	@JoinColumn(name="CD_ACAO")
	private AcaoAutoAtendimento acao;
	
//	@ManyToOne
//	@JoinColumn(name="CD_BANCO")
	@Transient
	private BancoPagamentoEletronico banco;
	
	@Column(name = "CD_ACATAMENTO_CSI")
	private String cdAcatamentoCSI;
	
	public AutoAtendimentoAcaoAutoAtendimento(){}
	
	public Long getCdAutoAtendimentoAcao() {
		return cdAutoAtendimentoAcao;
	}

	public void setCdAutoAtendimentoAcao(Long cdAutoAtendimentoAcao) {
		this.cdAutoAtendimentoAcao = cdAutoAtendimentoAcao;
	}

	public AutoAtendimentoAcessadoAcao getAutoAtendimentoAcessado() {
		return autoAtendimentoAcessado;
	}

	public void setAutoAtendimentoAcessado(
			AutoAtendimentoAcessadoAcao autoAtendimentoAcessado) {
		this.autoAtendimentoAcessado = autoAtendimentoAcessado;
	}

	public AcaoAutoAtendimento getAcao() {
		return acao;
	}

	public void setAcao(AcaoAutoAtendimento acao) {
		this.acao = acao;
	}


	public String getCdAcatamentoCSI() {
		return cdAcatamentoCSI;
	}

	public void setCdAcatamentoCSI(String cdAcatamentoCSI) {
		this.cdAcatamentoCSI = cdAcatamentoCSI;
	}

	public BancoPagamentoEletronico getBanco() {
		return banco;
	}

	public void setBanco(BancoPagamentoEletronico banco) {
		this.banco = banco;
	}
	
}
