package com.prime.app.agvirtual.entity;

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
public class AutoAtendimentoPergResp extends AgvTabAutoatendimento {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1719931710332393742L;

	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "autoAtendimento", fetch = FetchType.EAGER)
	@org.hibernate.annotations.Where(clause = "DS_DIRETORIA = 'R' OR DS_DIRETORIA = 'T'")
	@org.hibernate.annotations.OrderBy(clause = "NR_ORDEM_PERGUNTA asc ")
	private List<PerguntaAutoAtendimento> listaPergAutoAtendimentoRegional;

	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "autoAtendimento", fetch = FetchType.EAGER)
	@org.hibernate.annotations.Where(clause = "DS_DIRETORIA = 'M' OR DS_DIRETORIA = 'T'")
	@org.hibernate.annotations.OrderBy(clause = "NR_ORDEM_PERGUNTA asc ")
	private List<PerguntaAutoAtendimento> listaPergAutoAtendimentoMetropolitana;

	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "autoAtendimento", fetch = FetchType.EAGER)
	@org.hibernate.annotations.Where(clause = "DS_DIRETORIA = 'T'")
	@org.hibernate.annotations.OrderBy(clause = "NR_ORDEM_PERGUNTA asc ")
	private List<PerguntaAutoAtendimento> listaPergAutoAtendimentoTodasDiretorias;

	public List<PerguntaAutoAtendimento> getListaPergAutoAtendimentoRegional() {
		return listaPergAutoAtendimentoRegional;
	}

	public void setListaPergAutoAtendimentoRegional(
			List<PerguntaAutoAtendimento> listaPergAutoAtendimentoRegional) {
		this.listaPergAutoAtendimentoRegional = listaPergAutoAtendimentoRegional;
	}

	public List<PerguntaAutoAtendimento> getListaPergAutoAtendimentoMetropolitana() {
		return listaPergAutoAtendimentoMetropolitana;
	}

	public void setListaPergAutoAtendimentoMetropolitana(
			List<PerguntaAutoAtendimento> listaPergAutoAtendimentoMetropolitana) {
		this.listaPergAutoAtendimentoMetropolitana = listaPergAutoAtendimentoMetropolitana;
	}

	public List<PerguntaAutoAtendimento> getListaPergAutoAtendimentoTodasDiretorias() {
		return listaPergAutoAtendimentoTodasDiretorias;
	}

	public void setListaPergAutoAtendimentoTodasDiretorias(
			List<PerguntaAutoAtendimento> listaPergAutoAtendimentoTodasDiretorias) {
		this.listaPergAutoAtendimentoTodasDiretorias = listaPergAutoAtendimentoTodasDiretorias;
	}

}
