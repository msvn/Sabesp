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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "AGV_LOG_ACESSO", schema = Schema.DB_OWNER)
public class LogAcesso implements Serializable {
	private static final long serialVersionUID = -7384094706789980895L;
	
	@Id
	@Column(name = "CD_LOG_ACESSO", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_LOGACESSO", sequenceName = "SQ_AGV_LOGACESSO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_LOGACESSO")
    private Long cdLogAcesso;
	
	@Column(name="DT_ACESSO")
	private Date dataAcesso;
	
	@ManyToOne
	@JoinColumn(name = "CD_ATENDIMENTO" )
	private Atendimento atendimento;
	
	@ManyToOne
	@JoinColumn(name = "CD_ITEM_MENU" )
	private AgvTabItemMenu itemMenu;
	
	@Column(name="DS_USUARIO")
	private String descricaoUsuario;
	
    public LogAcesso() {}

	public LogAcesso(Atendimento atendimento, Long cdLogAcesso,
			Date dataAcesso, String descricaoUsuario, AgvTabItemMenu itemMenu) {
		this.atendimento = atendimento;
		this.cdLogAcesso = cdLogAcesso;
		this.dataAcesso = dataAcesso;
		this.descricaoUsuario = descricaoUsuario;
		this.itemMenu = itemMenu;
	}

	public Long getCdLogAcesso() {
		return cdLogAcesso;
	}

	public void setCdLogAcesso(Long cdLogAcesso) {
		this.cdLogAcesso = cdLogAcesso;
	}

	public Date getDataAcesso() {
		return dataAcesso;
	}

	public void setDataAcesso(Date dataAcesso) {
		this.dataAcesso = dataAcesso;
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public AgvTabItemMenu getItemMenu() {
		return itemMenu;
	}

	public void setItemMenu(AgvTabItemMenu itemMenu) {
		this.itemMenu = itemMenu;
	}

	public String getDescricaoUsuario() {
		return descricaoUsuario;
	}

	public void setDescricaoUsuario(String descricaoUsuario) {
		this.descricaoUsuario = descricaoUsuario;
	}
    
}
