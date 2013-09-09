package com.prime.app.agvirtual.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "AutoAtendimentoAcessadoAcao.findAll", query = "select o from AutoAtendimentoAcessadoAcao o") })
@Table(name = "AGV_TAB_AUTOAT_ACESS_ACAO", schema = Schema.DB_OWNER)
@PrimaryKeyJoinColumn(name = "CD_AUTOAT_ACESS_ACAO")
public class AutoAtendimentoAcessadoAcao extends AutoAtendimentoAcessado implements Serializable{
	private static final long serialVersionUID = 161717126994607602L;
	
//	// TODO - Why use @OneToMany 
//	@OneToOne
//	private AutoAtendimentoAcaoAutoAtendimento aaAcaoAA;
//
//	public AutoAtendimentoAcaoAutoAtendimento getAaAcaoAA() {
//		return aaAcaoAA;
//	}
//
//	public void setAaAcaoAA(AutoAtendimentoAcaoAutoAtendimento aaAcaoAA) {
//		this.aaAcaoAA = aaAcaoAA;
//	}
	
}
