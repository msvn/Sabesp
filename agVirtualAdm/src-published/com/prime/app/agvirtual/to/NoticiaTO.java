package com.prime.app.agvirtual.to;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.prime.app.agvirtual.entity.AgvTabNoticia;

public class NoticiaTO implements Serializable {

	private static final long serialVersionUID = 8267406289776884135L;
	
	private Long cdNoticia;
	private String caminhoImagem;
	private String subTitulo;
	private String titulo;
	private Date dataPublicacao;
	private String textoNoticia;
	private String realName;
	private Date dataUsuarioCriacao;
	private Date dataUsuarioAlteracao;

	public String getTitulo() {
		return (titulo != null && !titulo.equals("") ? titulo.trim() : titulo);
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Long getCdNoticia() {
		return cdNoticia;
	}
	
	public String getSubTitulo() {
		return (subTitulo != null && !subTitulo.equals("") ? subTitulo.trim() : subTitulo );
	}

	public void setSubTitulo(String subTitulo) {
		this.subTitulo = subTitulo;
	}

	public Date getDataPublicacao() {
		//FIXME URGENTE  miguel - Locale IceFaces  
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public void setCdNoticia(Long cdNoticia) {
		this.cdNoticia = cdNoticia;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}


	public String getTextoNoticia() {
		return textoNoticia;
	}

	public void setTextoNoticia(String textoNoticia) {
		this.textoNoticia = textoNoticia;
	}

	public NoticiaTO() {
	}

	public NoticiaTO(Long cdNoticia, String dsCaminhoImagem, String dsSubtitulo,
			String dsTexto, Date dtPublicacao, String ttNoticia) {

		this.cdNoticia = cdNoticia;
		this.caminhoImagem = dsCaminhoImagem;
		this.subTitulo = dsSubtitulo;
		this.titulo = ttNoticia;
		this.dataPublicacao = dtPublicacao;
		this.textoNoticia = dsTexto;
	}

	public NoticiaTO(String caminhoImagem, String realName, String subTitulo,
			String textoNoticia, String titulo) {
		super();
		this.caminhoImagem = caminhoImagem;
		this.realName = realName;
		this.subTitulo = subTitulo;
		this.textoNoticia = textoNoticia;
		this.titulo = titulo;
	}
	
	@Override
	public boolean equals(Object that) {
		if (that == null || !this.getClass().equals(that.getClass())) {
			return false;
		}

		NoticiaTO other = ((NoticiaTO) that);

		return (this.getCdNoticia() != null ? this.getCdNoticia().equals(
				other.getCdNoticia()) : this.getCdNoticia() == other.getCdNoticia());
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public AgvTabNoticia toEntity() {
		AgvTabNoticia entity =  new AgvTabNoticia();
		entity.setCdNoticia(getCdNoticia());
		entity.setDsCaminhoImagem(getCaminhoImagem());
		entity.setDsSubtitulo(getSubTitulo());
		entity.setDsTexto(getTextoNoticia());
		entity.setDtPublicacao(getDataPublicacao());
		entity.setTtNoticia(getTitulo());
		entity.setDtCriacao(getDataUsuarioCriacao());
		entity.setDtAlteracao(getDataUsuarioAlteracao());
		return entity;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getDataUsuarioCriacao() {
		return dataUsuarioCriacao;
	}

	public void setDataUsuarioCriacao(Date dataUsuarioCriacao) {
		this.dataUsuarioCriacao = dataUsuarioCriacao;
	}

	public Date getDataUsuarioAlteracao() {
		return dataUsuarioAlteracao;
	}

	public void setDataUsuarioAlteracao(Date dataUsuarioAlteracao) {
		this.dataUsuarioAlteracao = dataUsuarioAlteracao;
	}

}
