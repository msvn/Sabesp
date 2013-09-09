package com.prime.app.agvirtual.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "AutoAtendimentoAcessadoPergunta.findAll", query = "select o from AutoAtendimentoAcessadoPergunta o") })
@Table(name = "AGV_TAB_AUTOAT_ACESS_PERGUNTA", schema = Schema.DB_OWNER)
@PrimaryKeyJoinColumn(name = "CD_AUTOAT_ACESS_PERGUNTA")
public class AutoAtendimentoAcessadoPergunta extends AutoAtendimentoAcessado implements Serializable{
	private static final long serialVersionUID = 161717126994607602L;
	
	@OneToOne
	@JoinColumn(name="CD_CONJUNTO_RESPOSTA")
	private ConjuntoRespostaAutoAtendimento conjuntoResposta;

	public AutoAtendimentoAcessadoPergunta(){}
	
	public ConjuntoRespostaAutoAtendimento getConjuntoResposta() {
		return conjuntoResposta;
	}

	public void setConjuntoResposta(ConjuntoRespostaAutoAtendimento conjuntoResposta) {
		this.conjuntoResposta = conjuntoResposta;
	}

}
