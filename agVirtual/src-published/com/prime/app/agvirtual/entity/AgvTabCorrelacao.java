package com.prime.app.agvirtual.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabCorrelacao.findAll", query = "select o from AgvTabCorrelacao o")
})
@Table(name = "AGV_TAB_CORRELACAO", schema = Schema.DB_OWNER)
public class AgvTabCorrelacao implements Serializable {
	private static final long serialVersionUID = -4978674018857167399L;
    
    @Id
	@Column(name = "CD_CORRELACAO", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_CORRELACAO", sequenceName = "SQ_AGV_CORRELACAO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_CORRELACAO")
    private Long cdCorrelacao;
    
	@ManyToOne
    @JoinColumn(name = "CD_AUTOATENDIMENTO")
    private AgvTabAutoatendimento agvTabAutoatendimento;
    
	@ManyToOne (fetch=FetchType.EAGER)
    @JoinColumn(name = "CD_ITEM_MENU_ACESSO")
    private AgvTabItemMenuAcesso itemMenuAcesso;
	
    public AgvTabCorrelacao() {
    }

    public AgvTabCorrelacao(AgvTabAutoatendimento agvTabAutoatendimento,
			AgvTabItemMenuAcesso itemMenuAcesso, Long cdCorrelacao) {
		this.agvTabAutoatendimento = agvTabAutoatendimento;
		this.itemMenuAcesso = itemMenuAcesso;
		this.cdCorrelacao = cdCorrelacao;
	}

	public Long getCdCorrelacao() {
        return cdCorrelacao;
    }

    public void setCdCorrelacao(Long cdCorrelacao) {
        this.cdCorrelacao = cdCorrelacao;
    }


    public AgvTabAutoatendimento getAgvTabAutoatendimento() {
        return agvTabAutoatendimento;
    }

    public void setAgvTabAutoatendimento(AgvTabAutoatendimento agvTabAutoatendimento) {
        this.agvTabAutoatendimento = agvTabAutoatendimento;
    }


	public AgvTabItemMenuAcesso getItemMenuAcesso() {
		return itemMenuAcesso;
	}


	public void setItemMenuAcesso(AgvTabItemMenuAcesso itemMenuAcesso) {
		this.itemMenuAcesso = itemMenuAcesso;
	}

}
