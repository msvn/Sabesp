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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabBloqueio.findAll", query = "select o from AgvTabBloqueio o")
})
@Table(name = "AGV_TAB_BLOQUEIO", schema = Schema.DB_OWNER)
public class AgvTabBloqueio implements Serializable {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -7384094706789980895L;
	
	@Id
	@Column(name = "CD_BLOQUEIO", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_BLOQUEIO", sequenceName = "SQ_AGV_BLOQUEIO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_BLOQUEIO")
    private Long cdBloqueio;
	
    @Column(name="DS_MENSAGEM", length = 200)
    private String dsMensagem;
    @Column(name="DT_EXCLUSAO")
    private Date dtCancelamento;
    @Column(name="DT_CRIACAO")
    private Date dtInclusao;
    @Column(name="DS_BLOQUEIO", length = 40)
    private String nmBloqueio;
    @Column(name="DS_USR_CRIACAO", length = 40)
    private String nmUserCriacao;
    @Column(name="DS_USR_EXCLUSAO", length = 40)
    private String nmUserExclusao;    
    @Column(name="FL_EXCLUIDO")
    private Boolean excluido;
    @OneToOne
    @Fetch(FetchMode.JOIN)
	@JoinColumn(name="CD_BLOQUEIO")
    private AgvTabMunicipioUnidadeNegocio municipioUnidadeNegocio;
    
    @Fetch(value=FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "agvTabBloqueio",cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@Sort(type=SortType.NATURAL)
    private List<AgvTabBloqueioDetalhe> agvTabBloqueioDetalheList;

    public AgvTabBloqueio() {
    }

    public AgvTabBloqueio(Long cdBloqueio, String dsMensagem,
    		Date dtCancelamento, Date dtInclusao,
                          String nmBloqueio) {
        this.cdBloqueio = cdBloqueio;
        this.dsMensagem = dsMensagem;
        this.dtCancelamento = dtCancelamento;
        this.dtInclusao = dtInclusao;
        this.nmBloqueio = nmBloqueio;
    }

    public Long getCdBloqueio() {
        return cdBloqueio;
    }

    public void setCdBloqueio(Long cdBloqueio) {
        this.cdBloqueio = cdBloqueio;
    }

    public String getDsMensagem() {
        return dsMensagem;
    }

    public void setDsMensagem(String dsMensagem) {
        this.dsMensagem = dsMensagem;
    }

    public Date getDtCancelamento() {
        return dtCancelamento;
    }

    public void setDtCancelamento(Date dtCancelamento) {
        this.dtCancelamento = dtCancelamento;
    }

    public Date getDtInclusao() {
        return dtInclusao;
    }

    public void setDtInclusao(Date dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    public String getNmBloqueio() {
        return nmBloqueio;
    }

    public void setNmBloqueio(String nmBloqueio) {
        this.nmBloqueio = nmBloqueio;
    }

    public List<AgvTabBloqueioDetalhe> getAgvTabBloqueioDetalheList() {
        return agvTabBloqueioDetalheList;
    }

    public void setAgvTabBloqueioDetalheList(List<AgvTabBloqueioDetalhe> AgvTabBloqueioDetalheList) {
    }

    public AgvTabBloqueioDetalhe addAgvTabBloqueioDetalhe(AgvTabBloqueioDetalhe AgvTabBloqueioDetalhe) {
        getAgvTabBloqueioDetalheList().add(AgvTabBloqueioDetalhe);
        AgvTabBloqueioDetalhe.setAgvTabBloqueio(this);
        return AgvTabBloqueioDetalhe;
    }

    public AgvTabBloqueioDetalhe removeAgvTabBloqueioDetalhe(AgvTabBloqueioDetalhe AgvTabBloqueioDetalhe) {
        getAgvTabBloqueioDetalheList().remove(AgvTabBloqueioDetalhe);
        AgvTabBloqueioDetalhe.setAgvTabBloqueio(null);
        return AgvTabBloqueioDetalhe;
    }

	public String getNmUserCriacao() {
		return nmUserCriacao;
	}

	public void setNmUserCriacao(String nmUserCriacao) {
		this.nmUserCriacao = nmUserCriacao;
	}

	public String getNmUserExclusao() {
		return nmUserExclusao;
	}

	public void setNmUserExclusao(String nmUserExclusao) {
		this.nmUserExclusao = nmUserExclusao;
	}

	public AgvTabMunicipioUnidadeNegocio getMunicipioUnidadeNegocio() {
		return municipioUnidadeNegocio;
	}

	public void setMunicipioUnidadeNegocio(
			AgvTabMunicipioUnidadeNegocio municipioUnidadeNegocio) {
		this.municipioUnidadeNegocio = municipioUnidadeNegocio;
	}

	public Boolean getExcluido() {
		return excluido;
	}

	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

	
}
