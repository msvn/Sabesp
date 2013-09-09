package com.prime.app.agvirtual.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQueries( { @NamedQuery(name = "RespostaAutoAtendimento.findAll", query = "select o from RespostaAutoAtendimento o") })
@Table(name = "AGV_TAB_RESP_AUTOATENDIMENTO", schema = Schema.DB_OWNER)
public class RespostaAutoAtendimento implements BaseEntity {

	private static final long serialVersionUID = -5732002573361578877L;

	@Id
	@Column(name = "CD_RESPOSTA")
	private Long cdResposta;

	@Column(name = "DS_RESPOSTA")
	private String dsResposta;

	@Column(name = "NR_ORDEM_RESPOSTA")
	private Integer nrOrdemResposta;

	@Fetch(value = FetchMode.SUBSELECT)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "AGV_TAB_PERG_RESP_AUTOATEND", schema = Schema.DB_OWNER, 
			joinColumns = { @JoinColumn(name = "CD_RESPOSTA") }, 
			inverseJoinColumns = { @JoinColumn(name = "CD_PERGUNTA") })
	private List<PerguntaAutoAtendimento> perguntas;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Object parseTO() {
		// TODO Auto-generated method stub
		return null;
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

	public Integer getNrOrdemResposta() {
		return nrOrdemResposta;
	}

	public void setNrOrdemResposta(Integer nrOrdemResposta) {
		this.nrOrdemResposta = nrOrdemResposta;
	}

	public List<PerguntaAutoAtendimento> getPerguntas() {
		return perguntas;
	}

	public void setPerguntas(List<PerguntaAutoAtendimento> perguntas) {
		this.perguntas = perguntas;
	}

}
