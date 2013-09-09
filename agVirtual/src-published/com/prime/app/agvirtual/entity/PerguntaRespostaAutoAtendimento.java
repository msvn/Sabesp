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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQueries( { @NamedQuery(name = "PerguntaRespostaAutoAtendimento.findAll", query = "select o from PerguntaRespostaAutoAtendimento o") })
@Table(name = "AGV_TAB_PERG_RESP_AUTOATEND", schema = Schema.DB_OWNER)
public class PerguntaRespostaAutoAtendimento implements BaseEntity {

	private static final long serialVersionUID = -7610934649849270570L;

	@Id
	@Column(name = "CD_PERGUNTA_RESPOSTA")
	private int cdPerguntaResposta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_PERGUNTA")
	private PerguntaAutoAtendimento pergunta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_RESPOSTA")
	private RespostaAutoAtendimento resposta;

	@Fetch(value = FetchMode.SUBSELECT)
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
	@JoinTable(name = "AGV_TAB_INTEG_PERG_RESP", schema = Schema.DB_OWNER, 
					joinColumns = { @JoinColumn(name = "CD_PERGUNTA_RESPOSTA") }, 
					inverseJoinColumns = { @JoinColumn(name = "CD_CONJUNTO_RESPOSTA") })
	private List<ConjuntoRespostaAutoAtendimento> listaConjuntoResposta;

	public Object parseTO() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCdPerguntaResposta() {
		return cdPerguntaResposta;
	}

	public void setCdPerguntaResposta(int cdPerguntaResposta) {
		this.cdPerguntaResposta = cdPerguntaResposta;
	}

	public PerguntaAutoAtendimento getPergunta() {
		return pergunta;
	}

	public void setPergunta(PerguntaAutoAtendimento pergunta) {
		this.pergunta = pergunta;
	}

	public RespostaAutoAtendimento getResposta() {
		return resposta;
	}

	public void setResposta(RespostaAutoAtendimento resposta) {
		this.resposta = resposta;
	}

	public List<ConjuntoRespostaAutoAtendimento> getListaConjuntoResposta() {
		return listaConjuntoResposta;
	}

	public void setListaConjuntoResposta(
			List<ConjuntoRespostaAutoAtendimento> listaConjuntoResposta) {
		this.listaConjuntoResposta = listaConjuntoResposta;
	}

}
