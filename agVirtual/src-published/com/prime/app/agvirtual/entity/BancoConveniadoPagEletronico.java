package com.prime.app.agvirtual.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AGV_TAB_BANCO_PGTO_ELET",  schema = Schema.DB_OWNER)
public class BancoConveniadoPagEletronico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3332552588436947025L;

	@Id
    @Column(name="CD_BANCO", nullable = false)
	private String cdBanco;

	@Column(name="CD_BANCO_MERCADO", nullable = false)
	private String cdBancoMercado;

	@Column(name="DS_BANCO", nullable = false)
	private String dsBanco;

	@Column(name="DS_LINK", nullable = false)
	private String dsLink;

	public BancoConveniadoPagEletronico(){}

	public String getCdBanco() {
		return cdBanco;
	}

	public String getCdBancoMercado() {
		return cdBancoMercado;
	}

	public String getDsBanco() {
		return dsBanco;
	}

	public String getDsLink() {
		return dsLink;
	}

	public void setCdBanco(String cdBanco) {
		this.cdBanco = cdBanco;
	}
	
	public void setCdBancoMercado(String cdBancoMercado) {
		this.cdBancoMercado = cdBancoMercado;
	}
	
	public void setDsBanco(String dsBanco) {
		this.dsBanco = dsBanco;
	}
	
	public void setDsLink(String dsLink) {
		this.dsLink = dsLink;
	}
	
	
	
}
