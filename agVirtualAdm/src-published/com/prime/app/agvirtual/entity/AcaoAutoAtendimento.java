package com.prime.app.agvirtual.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "AcaoAutoAtendimento.findAll", query = "select o from AcaoAutoAtendimento o") })
@Table(name = "AGV_TAB_ACAO_AUTOATENDIMENTO", schema = Schema.DB_OWNER)
public class AcaoAutoAtendimento implements BaseEntity {

	private static final long serialVersionUID = 1318677872193753100L;

	@Id
	@Column(name = "CD_ACAO")
	private Long cdAcao;

	@Column(name = "TP_ACAO")
	private String tpAcao;

	@Column(name = "DS_ACAO")
	private String dsAcao;

	@Column(name = "IN_EXIBICAO_TELEFONE")
	private String inExibicaoTelefone;

	@Column(name = "IN_EXIBICAO_LINK_CHAT")
	private String inExibicaoLinkChat;

	@Column(name = "CD_SERVICO_CSI")
	private Integer cdServiceCSI;

	@OneToMany(mappedBy = "acao")
	private List<ConjuntoRespostaAutoAtendimento> listaConjuntoResposta;

	public Object parseTO() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getCdAcao() {
		return cdAcao;
	}

	public void setCdAcao(Long cdAcao) {
		this.cdAcao = cdAcao;
	}

	public String getTpAcao() {
		return tpAcao;
	}

	public void setTpAcao(String tpAcao) {
		this.tpAcao = tpAcao;
	}

	public String getDsAcao() {
		return dsAcao;
	}

	public void setDsAcao(String dsAcao) {
		this.dsAcao = dsAcao;
	}

	public String getInExibicaoTelefone() {
		return inExibicaoTelefone;
	}

	public void setInExibicaoTelefone(String inExibicaoTelefone) {
		this.inExibicaoTelefone = inExibicaoTelefone;
	}

	public String getInExibicaoLinkChat() {
		return inExibicaoLinkChat;
	}

	public void setInExibicaoLinkChat(String inExibicaoLinkChat) {
		this.inExibicaoLinkChat = inExibicaoLinkChat;
	}

	public Integer getCdServiceCSI() {
		return cdServiceCSI;
	}

	public void setCdServiceCSI(Integer cdServiceCSI) {
		this.cdServiceCSI = cdServiceCSI;
	}

	public List<ConjuntoRespostaAutoAtendimento> getListaConjuntoResposta() {
		return listaConjuntoResposta;
	}

	public void setListaConjuntoResposta(
			List<ConjuntoRespostaAutoAtendimento> listaConjuntoResposta) {
		this.listaConjuntoResposta = listaConjuntoResposta;
	}

}
