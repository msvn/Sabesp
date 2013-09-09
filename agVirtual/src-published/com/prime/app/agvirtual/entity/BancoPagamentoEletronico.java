package com.prime.app.agvirtual.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "BancoPagamentoEletronico.findAll", query = "select o from BancoPagamentoEletronico o") })
@Table(name = "AGV_TAB_BANCO_PGTO_ELET", schema = Schema.DB_OWNER)
public class BancoPagamentoEletronico implements Serializable{
	private static final long serialVersionUID = -3745725235800539197L;
	
	@Id
	@Column(name="CD_BANCO")
	private Long codigo;
	
	@Column(name="CD_BANCO_MERCADO")
	private Long codigoBancoMercado;
	
	@Column(name="DS_BANCO")
	private String descricao;
	
	@Column(name="DS_LINK")
	private String link;

	public BancoPagamentoEletronico(){}
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigoBancoMercado() {
		return codigoBancoMercado;
	}

	public void setCodigoBancoMercado(Long codigoBancoMercado) {
		this.codigoBancoMercado = codigoBancoMercado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
