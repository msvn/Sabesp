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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabRespPesquisa.findAll", query = "select o from AgvTabRespPesquisa o")
})
@Table(name = "AGV_TAB_RESP_PESQUISA", schema = Schema.DB_OWNER)
public class AgvTabRespPesquisa implements Serializable {
    
	private static final long serialVersionUID = -4978674018857167399L;
    
    @Id
	@Column(name = "CD_RESPOSTA", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_RESP_PESQUISA", sequenceName = "SQ_AGV_RESP_PESQUISA", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_RESP_PESQUISA")
    private Long cdResposta;
    @Column(name="DS_RESPOSTA", length = 60)
    private String dsResposta;
    @Column(name="FL_OBSERVACAO", length = 1)
    private String flObservacao;
    
    @Transient
    private boolean flagObs;
    
    @ManyToOne
    @JoinColumn(name = "CD_PERGUNTA")
    private AgvTabPerguntaPesquisa agvTabPerguntaPesquisa;

    public AgvTabRespPesquisa() {
    	
    }

    public AgvTabRespPesquisa(Long cdResposta, String dsResposta, String flObservacao,
    						  AgvTabPerguntaPesquisa agvTabPerguntaPesquisa) {
    	this.cdResposta = cdResposta;
    	this.dsResposta = dsResposta;
    	this.flObservacao = flObservacao;
    	this.agvTabPerguntaPesquisa = agvTabPerguntaPesquisa;
    }

	public Long getCdResposta() {
		return cdResposta;
	}

	public void setCdResposta(Long cdResposta) {
		this.cdResposta = cdResposta;
	}

	public String getDsResposta() {
		return dsResposta;
	}

	public void setDsResposta(String dsResposta) {
		this.dsResposta = dsResposta;
	}

	public String getFlObservacao() {
		return flObservacao;
	}

	public void setFlObservacao(String flObservacao) {
		this.flObservacao = flObservacao;
	}

	public AgvTabPerguntaPesquisa getAgvTabPerguntaPesquisa() {
		return agvTabPerguntaPesquisa;
	}

	public void setAgvTabPerguntaPesquisa(
			AgvTabPerguntaPesquisa agvTabPerguntaPesquisa) {
		this.agvTabPerguntaPesquisa = agvTabPerguntaPesquisa;
	}
	
	public void bidirecionalRemocao(){
		this.agvTabPerguntaPesquisa.getRespostaList().remove(this);
	}

	public boolean isFlagObs() {
		if ("S".equalsIgnoreCase(this.flObservacao))
			setFlagObs(true);
		else
			setFlagObs(false);
		return flagObs;
	}

	public void setFlagObs(boolean flagObs) {
		if (flagObs)
			setFlObservacao("S");
		else
			setFlObservacao("N");
		this.flagObs = flagObs;
	}
    
}
