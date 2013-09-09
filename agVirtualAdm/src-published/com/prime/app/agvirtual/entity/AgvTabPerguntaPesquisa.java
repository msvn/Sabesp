package com.prime.app.agvirtual.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabPerguntaPesquisa.findAll", query = "select o from AgvTabPerguntaPesquisa o")
})
@Table(name = "AGV_TAB_PERGUNTA_PESQUISA", schema = Schema.DB_OWNER)
public class AgvTabPerguntaPesquisa implements Serializable {
    
	private static final long serialVersionUID = -4978674018857167399L;
    
    @Id
	@Column(name = "CD_PERGUNTA", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_PERGUNTA_PESQUISA", sequenceName = "SQ_AGV_PERGUNTA_PESQUISA", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_PERGUNTA_PESQUISA")
    private Long cdPergunta;
    @Column(name="DS_PERGUNTA", length = 60)
    private String dsPergunta;
    
    @ManyToOne
    @JoinColumn(name = "CD_PESQUISA")
    private AgvTabPesquisa agvTabPesquisa;
    	
    
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="agvTabPerguntaPesquisa", fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@Fetch(FetchMode.SUBSELECT)
	@JoinColumn(name="CD_PERGUNTA")	
	private List<AgvTabRespPesquisa> respostaList = new ArrayList<AgvTabRespPesquisa>();
	
	@Transient
	private boolean selected = Boolean.FALSE;

    public AgvTabPerguntaPesquisa() {
    	
    }

    public AgvTabPerguntaPesquisa(Long cdPergunta, String dsPergunta, 
    		 					  AgvTabPesquisa agvTabPesquisa) {
    	this.cdPergunta = cdPergunta;
    	this.dsPergunta = dsPergunta;
    	this.agvTabPesquisa = agvTabPesquisa;
    }

	public Long getCdPergunta() {
		return cdPergunta;
	}

	public void setCdPergunta(Long cdPergunta) {
		this.cdPergunta = cdPergunta;
	}

	public String getDsPergunta() {
		return dsPergunta;
	}

	public void setDsPergunta(String dsPergunta) {
		this.dsPergunta = dsPergunta;
	}

	public AgvTabPesquisa getAgvTabPesquisa() {
		return agvTabPesquisa;
	}

	public void setAgvTabPesquisa(AgvTabPesquisa agvTabPesquisa) {
		this.agvTabPesquisa = agvTabPesquisa;
	}
	
	public List<AgvTabRespPesquisa> getRespostaList() {
		return respostaList;
	}

	public void setRespostaList(
			List<AgvTabRespPesquisa> respostaList) {
		this.respostaList = respostaList;
	}
    
    public void bidirecionalInclusao(AgvTabRespPesquisa resposta){
    	resposta.setAgvTabPerguntaPesquisa(this);
   		this.respostaList.add(resposta);
    }
    
    public void bidirecionalRemocao(){
    	
    	this.respostaList.clear();
    	
    	this.agvTabPesquisa.getPerguntaList().remove(this);    	
    	
    }
    
    public AgvTabRespPesquisa getResposta(String descricao){
    	
    	AgvTabRespPesquisa respostaRetornar = null;
    	
    	for(AgvTabRespPesquisa resposta : respostaList){
    		
    		if( resposta.getDsResposta().equalsIgnoreCase(descricao) ){
    			respostaRetornar = resposta;
    			break;
    		}
    				
    	}
    	
    	return respostaRetornar;
    	
    }

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
    
}
