package com.prime.app.agvirtual.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.prime.infra.util.WrapperUtils;

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

	@Column(name = "CD_SERVCOM_CSI") //SubGrupo
	private Integer cdServicoCsi;
	
	@Column(name = "CD_GRPSERV_CSI") // Grupo
	private Integer cdGrupoServiceCsi;

	@OneToMany(mappedBy = "acao")
	private List<ConjuntoRespostaAutoAtendimento> listaConjuntoResposta;
	
	@OneToMany(mappedBy = "acaoServExe" , fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<ServicoExecutado> listaServExe;
	
	@ManyToOne
	@JoinColumn(name = "CD_AUTOATENDIMENTO")
	private AutoAtendimentoAcao autoAtendimentoAcao;

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

	public Integer getCdServicoCsi() {
		return cdServicoCsi;
	}

	public void setCdServicoCsi(Integer cdServicoCsi) {
		this.cdServicoCsi = cdServicoCsi;
	}

	public List<ConjuntoRespostaAutoAtendimento> getListaConjuntoResposta() {
		return listaConjuntoResposta;
	}

	public void setListaConjuntoResposta(
			List<ConjuntoRespostaAutoAtendimento> listaConjuntoResposta) {
		this.listaConjuntoResposta = listaConjuntoResposta;
	}

	public AutoAtendimentoAcao getAutoAtendimentoAcao() {
		return autoAtendimentoAcao;
	}

	public void setAutoAtendimentoAcao(AutoAtendimentoAcao autoAtendimentoAcao) {
		this.autoAtendimentoAcao = autoAtendimentoAcao;
	}

	public Integer getCdGrupoServiceCsi() {
		return cdGrupoServiceCsi;
	}

	public void setCdGrupoServiceCsi(Integer cdGrupoServiceCsi) {
		this.cdGrupoServiceCsi = cdGrupoServiceCsi;
	}

	public List<ServicoExecutado> getListaServExe() {
		return listaServExe;
	}

	public void setListaServExe(List<ServicoExecutado> listaServExe) {
		this.listaServExe = listaServExe;
	}
	

}
