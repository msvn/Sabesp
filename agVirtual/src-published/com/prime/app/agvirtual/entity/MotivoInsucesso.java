package com.prime.app.agvirtual.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "MotivoInsucesso.findAll", query = "select o from MotivoInsucesso o") })
@Table(name = "AGV_TAB_MOTIVO_INSUCESSO", schema = Schema.DB_OWNER)
public class MotivoInsucesso {

	@Id
	@Column(name = "CD_MOTIVO_INSUCESSO", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_MOTIVO_INS", sequenceName = "SQ_AGV_MOTIVO_INS", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_MOTIVO_INS")	
	private Long codigo;

	@Column(name = "DS_MOTIVO_INSUCESSO", nullable = false)
	private String motivo;

	public MotivoInsucesso(){}
	
	public MotivoInsucesso(Long codigo, String motivo) {
		this.codigo = codigo;
		this.motivo = motivo;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}
