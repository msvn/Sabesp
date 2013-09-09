package com.prime.app.agvirtual.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQueries( { @NamedQuery(name = "ConjuntoRespostaAutoAtendimento.findAll", query = "select o from ConjuntoRespostaAutoAtendimento o") })
@Table(name = "AGV_TAB_CONJ_RESPOSTA", schema = Schema.DB_OWNER)
public class ConjuntoRespostaAutoAtendimento implements Serializable {

	private static final long serialVersionUID = -1662068926641211146L;

	@Id
	@Column(name = "CD_CONJUNTO_RESPOSTA")
	private Long cdConjuntoResposta;

	@Column(name = "DS_DIRETORIA")
	private String dsDiretoria;

	@Fetch(value = FetchMode.SUBSELECT)
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
	@JoinTable(name = "AGV_TAB_INTEG_PERG_RESP", schema = Schema.DB_OWNER, 
			joinColumns = { @JoinColumn(name = "CD_CONJUNTO_RESPOSTA") }, 
			inverseJoinColumns = { @JoinColumn(name = "CD_PERGUNTA_RESPOSTA") })
	private List<PerguntaRespostaAutoAtendimento> listaPerguntasRespostas;

	@ManyToOne
	@JoinColumn(name = "CD_ACAO")
	private AcaoAutoAtendimento acao;

	public Long getCdConjuntoResposta() {
		return cdConjuntoResposta;
	}

	public void setCdConjuntoResposta(Long cdConjuntoResposta) {
		this.cdConjuntoResposta = cdConjuntoResposta;
	}

	public String getDsDiretoria() {
		return dsDiretoria;
	}

	public void setDsDiretoria(String dsDiretoria) {
		this.dsDiretoria = dsDiretoria;
	}

	public List<PerguntaRespostaAutoAtendimento> getListaPerguntasRespostas() {
		return listaPerguntasRespostas;
	}

	public void setListaPerguntasRespostas(
			List<PerguntaRespostaAutoAtendimento> listaPerguntasRespostas) {
		this.listaPerguntasRespostas = listaPerguntasRespostas;
	}

	public AcaoAutoAtendimento getAcao() {
		return acao;
	}

	public void setAcao(AcaoAutoAtendimento acao) {
		this.acao = acao;
	}

}
