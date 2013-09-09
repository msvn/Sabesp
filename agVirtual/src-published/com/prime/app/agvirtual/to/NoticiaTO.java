package com.prime.app.agvirtual.to;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.prime.app.agvirtual.entity.AgvTabNoticia;

public class NoticiaTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8267406289776884135L;
	
	private Long cdNoticia;
	private String caminhoImagem;
	private String subTitulo;
	private String titulo;
	private Date dataPublicacao;
	private String textoNoticia;
	private String realName;
	


	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Long getCdNoticia() {
		return cdNoticia;
	}
	
	public String getSubTitulo() {
		return subTitulo;
	}

	public void setSubTitulo(String subTitulo) {
		this.subTitulo = subTitulo;
	}

	public Date getDataPublicacao() {
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
		return entity;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
