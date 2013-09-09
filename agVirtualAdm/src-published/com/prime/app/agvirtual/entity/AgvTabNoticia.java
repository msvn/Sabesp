package com.prime.app.agvirtual.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.prime.app.agvirtual.to.NoticiaTO;

@Entity
@NamedQueries( { @NamedQuery(name = "AgvTabNoticia.findAll", query = "select o from AgvTabNoticia o") })
@Table(name = "AGV_TAB_NOTICIA", schema = Schema.DB_OWNER)
public class AgvTabNoticia implements BaseEntity<NoticiaTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_NOTICIA", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_NOTICIA", sequenceName = "SQ_AGV_NOTICIA", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_NOTICIA")
	private Long cdNoticia;
	@Column(name = "DS_CAMINHO_IMAGEM", length = 200)
	private String dsCaminhoImagem;
	@Column(name = "DS_SUBTITULO", length = 200)
	private String dsSubtitulo;
	@Column(name = "DS_TEXTO", length = 4000)
	private String dsTexto;
	@Column(name = "DT_PUBLICACAO")
	private Date dtPublicacao;
	@Column(name = "TT_NOTICIA", length = 60)
	private String ttNoticia;

	@Column(name = "DS_USUARIO_CRIACAO", length = 50)
	private String dsUsuarioCriacao;
	@Column(name = "DS_USUARIO_ALTERACAO", length = 50)
	private String dsUsuarioAlteracao;
	@Column(name = "DT_CRIACAO")
	private Date dtCriacao;
	@Column(name = "DT_ALTERACAO")
	private Date dtAlteracao;

	public AgvTabNoticia() {
	}

	public AgvTabNoticia(Long cdNoticia, String dsCaminhoImagem,
			String dsSubtitulo, String dsTexto, String dsUsuarioAlteracao,
			String dsUsuarioCriacao, Date dtAlteracao, Date dtCriacao,
			Date dtPublicacao, String ttNoticia) {
		this.cdNoticia = cdNoticia;
		this.dsCaminhoImagem = dsCaminhoImagem;
		this.dsSubtitulo = dsSubtitulo;
		this.dsTexto = dsTexto;
		this.dsUsuarioAlteracao = dsUsuarioAlteracao;
		this.dsUsuarioCriacao = dsUsuarioCriacao;
		this.dtAlteracao = dtAlteracao;
		this.dtCriacao = dtCriacao;
		this.dtPublicacao = dtPublicacao;
		this.ttNoticia = ttNoticia;
	}

	public Long getCdNoticia() {
		return cdNoticia;
	}

	public void setCdNoticia(Long cdNoticia) {
		this.cdNoticia = cdNoticia;
	}

	public String getDsCaminhoImagem() {
		return dsCaminhoImagem;
	}

	public void setDsCaminhoImagem(String dsCaminhoImagem) {
		this.dsCaminhoImagem = dsCaminhoImagem;
	}

	public String getDsSubtitulo() {
		return dsSubtitulo;
	}

	public void setDsSubtitulo(String dsSubtitulo) {
		this.dsSubtitulo = dsSubtitulo;
	}

	public String getDsTexto() {
		return dsTexto;
	}

	public void setDsTexto(String dsTexto) {
		this.dsTexto = dsTexto;
	}

	public Date getDtPublicacao() {
		return dtPublicacao;
	}

	public void setDtPublicacao(Date dtPublicacao) {
		this.dtPublicacao = dtPublicacao;
	}

	public String getTtNoticia() {
		return ttNoticia;
	}

	public void setTtNoticia(String ttNoticia) {
		this.ttNoticia = ttNoticia;
	}

	public NoticiaTO parseTO() {
		NoticiaTO element = new NoticiaTO();
		element.setCaminhoImagem(getDsCaminhoImagem());
		element.setSubTitulo(getDsSubtitulo());
		element.setDataPublicacao(getDtPublicacao());
		element.setCdNoticia(getCdNoticia());
		element.setTitulo(getTtNoticia());
		element.setTextoNoticia(getDsTexto());
		element.setDataUsuarioCriacao(getDtCriacao());
		element.setDataUsuarioAlteracao(getDtAlteracao());
		return element;
	}

	public String getDsUsuarioCriacao() {
		return dsUsuarioCriacao;
	}

	public void setDsUsuarioCriacao(String dsUsuarioCriacao) {
		this.dsUsuarioCriacao = dsUsuarioCriacao;
	}

	public String getDsUsuarioAlteracao() {
		return dsUsuarioAlteracao;
	}

	public void setDsUsuarioAlteracao(String dsUsuarioAlteracao) {
		this.dsUsuarioAlteracao = dsUsuarioAlteracao;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Date getDtAlteracao() {
		return dtAlteracao;
	}

	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}

}
