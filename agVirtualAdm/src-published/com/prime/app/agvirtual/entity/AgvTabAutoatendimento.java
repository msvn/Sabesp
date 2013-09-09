package com.prime.app.agvirtual.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.transaction.annotation.Transactional;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabAutoatendimento.findAll", query = "select o from AgvTabAutoatendimento o")
})
@Table(name = "AGV_TAB_AUTOATENDIMENTO", schema = Schema.DB_OWNER)
public class AgvTabAutoatendimento implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 161717126994607602L;
    @Id
	@Column(name = "CD_AUTOATENDIMENTO", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_AUTOATENDIMENTO", sequenceName = "SQ_AGV_AUTOATENDIMENTO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_AUTOATENDIMENTO")
    private Long cdAutoatendimento;
    @Column(name="DS_AUTOATENDIMENTO", length = 200)
    private String dsAutoatendimento;
    
//    @OneToMany(mappedBy = "agvTabAutoatendimento")
//    private List<AgvTabPesquisa> agvTabPesquisaList;
    @OneToMany(mappedBy = "agvTabAutoatendimento" , fetch=FetchType.EAGER)
    private List<AgvTabCorrelacao> agvTabCorrelacaoList;
//    @OneToMany(mappedBy = "agvTabAutoatendimento")
//    private List<AgvTabAutoatendimentoAcess> agvTabAutoatendimentoAcessList;
//    @OneToMany(mappedBy = "agvTabAutoatendimento")
//    private List<AgvTabPergAutoatendimento> agvTabPergAutoatendimentoList;
    
    @Transient
    private int numeroOrdem;

    public AgvTabAutoatendimento() {
    }

    public AgvTabAutoatendimento(Long cdAutoatendimento,
                                 String dsAutoatendimento) {
        this.cdAutoatendimento = cdAutoatendimento;
        this.dsAutoatendimento = dsAutoatendimento;
    }

    public Long getCdAutoatendimento() {
        return cdAutoatendimento;
    }

    public void setCdAutoatendimento(Long cdAutoatendimento) {
        this.cdAutoatendimento = cdAutoatendimento;
    }

    public String getDsAutoatendimento() {
        return dsAutoatendimento;
    }

    public void setDsAutoatendimento(String dsAutoatendimento) {
        this.dsAutoatendimento = dsAutoatendimento;
    }

    public List<AgvTabCorrelacao> getAgvTabCorrelacaoList() {
        return agvTabCorrelacaoList;
    }

    public void setAgvTabCorrelacaoList(List<AgvTabCorrelacao> agvTabCorrelacaoList) {
        this.agvTabCorrelacaoList = agvTabCorrelacaoList;
    }

    public AgvTabCorrelacao addAgvTabCorrelacao(AgvTabCorrelacao agvTabCorrelacao) {
        getAgvTabCorrelacaoList().add(agvTabCorrelacao);
        agvTabCorrelacao.setAgvTabAutoatendimento(this);
        return agvTabCorrelacao;
    }

    public AgvTabCorrelacao removeAgvTabCorrelacao(AgvTabCorrelacao agvTabCorrelacao) {
        getAgvTabCorrelacaoList().remove(agvTabCorrelacao);
        agvTabCorrelacao.setAgvTabAutoatendimento(null);
        return agvTabCorrelacao;
    }

	public int getNumeroOrdem() {
		return numeroOrdem;
	}

	public void setNumeroOrdem(int numeroOrdem) {
		this.numeroOrdem = numeroOrdem;
	}
    
    
}
