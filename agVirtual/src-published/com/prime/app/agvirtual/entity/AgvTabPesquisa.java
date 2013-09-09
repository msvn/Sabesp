package com.prime.app.agvirtual.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabPesquisa.findAll", query = "select o from AgvTabPesquisa o")
})
@Table(name = "AGV_TAB_PESQUISA", schema = Schema.DB_OWNER)
public class AgvTabPesquisa implements Serializable {
    
	private static final long serialVersionUID = -4978674018857167399L;
    
    @Id
	@Column(name = "CD_PESQUISA", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_PESQUISA", sequenceName = "SQ_AGV_PESQUISA", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_PESQUISA")
    private Long cdPesquisa;
    @Column(name = "DT_INI_VIGENCIA")
    private Date dtIniVigencia;
    @Column(name = "DT_FIM_VIGENCIA")
    private Date dtFimVigencia;
    @Column(name = "NR_PRIORIDADE", length = 1)
    private Integer nrPrioridade;
    @Column(name = "DT_ENCERRAMENTO")
    private Date dtEncerramento;
    @Column(name = "TP_PESQUISA", length = 1)
    private Integer tpPesquisa;
    @Column(name = "DS_USR_CRIACAO", length = 20)
    private String nmUsrCriacao;
    @Column(name = "DT_CRIACAO")
    private Date dtCriacao;
    @Column(name = "DS_USR_ENCERRAMENTO", length = 20)
    private String nmUsrEncerramento;
    @Column(name = "DS_PESQUISA", length = 40)
    private String nmPesquisa;
    
	@ManyToOne
    @JoinColumn(name = "CD_AUTOATENDIMENTO")
    private AgvTabAutoatendimento autoAtendimento = new AgvTabAutoatendimento();
		
	
	@OneToMany(cascade ={CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE}, mappedBy="agvTabPesquisa", fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@Fetch(FetchMode.SUBSELECT)		
	@JoinColumn(name="CD_PESQUISA")
	private List<AgvTabPerguntaPesquisa> perguntaList = new ArrayList<AgvTabPerguntaPesquisa>();

    public AgvTabPesquisa() {
    	
    }

    public AgvTabPesquisa(Long cdPesquisa, Date dtIniVigencia, Date dtFimVigencia,
    					  Integer nrPrioridade, Date dtEncerramento, Integer tpPesquisa, 
    					  String nmUsrCriacao, Date dtCriacao, String nmUsrEncerramento,
    					  AgvTabAutoatendimento autoAtendimento) {
    	this.cdPesquisa = cdPesquisa;
    	this.dtIniVigencia = dtIniVigencia;
    	this.dtFimVigencia = dtFimVigencia;
    	this.nrPrioridade = nrPrioridade;
    	this.dtEncerramento = dtEncerramento;
    	this.tpPesquisa = tpPesquisa;
    	this.nmUsrCriacao = nmUsrCriacao;
    	this.dtCriacao = dtCriacao;
    	this.nmUsrEncerramento = nmUsrEncerramento;
        this.autoAtendimento = autoAtendimento;
    }

	public Long getCdPesquisa() {
		return cdPesquisa;
	}

	public void setCdPesquisa(Long cdPesquisa) {
		this.cdPesquisa = cdPesquisa;
	}

	public Date getDtIniVigencia() {
		return dtIniVigencia;
	}

	public void setDtIniVigencia(Date dtIniVigencia) {
		this.dtIniVigencia = dtIniVigencia;
	}

	public Date getDtFimVigencia() {
		return dtFimVigencia;
	}

	public void setDtFimVigencia(Date dtFimVigencia) {
		this.dtFimVigencia = dtFimVigencia;
	}

	public Integer getNrPrioridade() {
		return nrPrioridade;
	}

	public void setNrPrioridade(Integer nrPrioridade) {
		this.nrPrioridade = nrPrioridade;
	}

	public Date getDtEncerramento() {
		return dtEncerramento;
	}

	public void setDtEncerramento(Date dtEncerramento) {
		this.dtEncerramento = dtEncerramento;
	}

	public AgvTabAutoatendimento getAutoAtendimento() {
		if(this.autoAtendimento == null){
			this.autoAtendimento = new AgvTabAutoatendimento();
		}
		return autoAtendimento;
	}

	public void setAutoAtendimento(AgvTabAutoatendimento autoAtendimento) {
		this.autoAtendimento = autoAtendimento;
	}

	public Integer getTpPesquisa() {
		return tpPesquisa;
	}

	public void setTpPesquisa(Integer tpPesquisa) {
		this.tpPesquisa = tpPesquisa;
	}

	public String getNmUsrCriacao() {
		return nmUsrCriacao;
	}

	public void setNmUsrCriacao(String nmUsrCriacao) {
		this.nmUsrCriacao = nmUsrCriacao;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getNmUsrEncerramento() {
		return nmUsrEncerramento;
	}

	public void setNmUsrEncerramento(String nmUsrEncerramento) {
		this.nmUsrEncerramento = nmUsrEncerramento;
	}

	public List<AgvTabPerguntaPesquisa> getPerguntaList() {
		return perguntaList;
	}

	public void setPerguntaList(
			List<AgvTabPerguntaPesquisa> perguntaList) {
		this.perguntaList = perguntaList;
	}
	
	public String getNmPesquisa() {
		return nmPesquisa;
	}

	public void setNmPesquisa(String nmPesquisa) {
		this.nmPesquisa = nmPesquisa;
	}
    
    public void bidirecionalInclusao(AgvTabPerguntaPesquisa pergunta){
    	pergunta.setAgvTabPesquisa(this);
       	perguntaList.add(pergunta);   
    	
    }
    
    public AgvTabPerguntaPesquisa getPergunta(String descricao){
    	
    	AgvTabPerguntaPesquisa perguntaRetornar = null;
    	
    	for(AgvTabPerguntaPesquisa pergunta : this.perguntaList){
    		
    		if( pergunta.getDsPergunta().equalsIgnoreCase(descricao) ) {
    			perguntaRetornar = pergunta;
    			break;
    		}
    	}
    	
		return perguntaRetornar;
    }
	
}
