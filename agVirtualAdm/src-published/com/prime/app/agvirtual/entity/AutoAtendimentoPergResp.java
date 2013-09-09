package com.prime.app.agvirtual.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQueries( { @NamedQuery(name = "AutoAtendimentoPergResp.findAll", query = "select o from AutoAtendimentoPergResp o") })
@Table(name = "AGV_TAB_AUTOAT_PERGRESP", schema = Schema.DB_OWNER)
@PrimaryKeyJoinColumn(name = "CD_AUTOATENDIMENTO")
public class AutoAtendimentoPergResp extends AutoAtendimento {

	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "autoAtendimento" , fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "NR_ORDEM_PERGUNTA asc")
	private List<PerguntaAutoAtendimento> listaPergAutoAtendimento;

	public List<PerguntaAutoAtendimento> getListaPergAutoAtendimento() {
		return listaPergAutoAtendimento;
	}

	public void setListaPergAutoAtendimento(
			List<PerguntaAutoAtendimento> listaPergAutoAtendimento) {
		this.listaPergAutoAtendimento = listaPergAutoAtendimento;
	}

}
