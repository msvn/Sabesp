package com.prime.app.agvirtual.to;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.prime.app.agvirtual.entity.AgvTabDocumento;
import com.prime.app.agvirtual.enums.TipoDocumento;

/**
 * @author gustavons
 *
 */
public class DocumentoTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3222412862479063756L;
	
	private Long cdDocumento;
    private String dsLink;
    private String dsDocumento;
	private String nmDocumento;
	private String tipoDocumento;
	private String tipoPessoa;
	private String nmFisicoDocumento;
	private String realName;
	private TipoDocumento tpDocumentoTela;
	
	@Override
	public boolean equals(Object that) {
		if (that == null || !this.getClass().equals(that.getClass())) {
			return false;
		}

		DocumentoTO other = ((DocumentoTO) that);

		return (this.getCdDocumento() != null ? this.getCdDocumento().equals(
				other.getCdDocumento()) : this.getCdDocumento() == other.getCdDocumento());
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public AgvTabDocumento toEntity() { 

		AgvTabDocumento entity =  new AgvTabDocumento();
		entity.setCdDocumento(cdDocumento);
		entity.setDsDocumento(dsDocumento);
		entity.setDsLink(dsLink);
		entity.setNmDocumento(nmDocumento);
		entity.setTipoPessoa(Integer.valueOf(tipoPessoa));
		entity.setTipoDocumento(Integer.valueOf(tipoDocumento));
		entity.setNmFisicoDocumento(nmFisicoDocumento);
		return entity;
	}

	public Long getCdDocumento() {
		return cdDocumento;
	}

	public void setCdDocumento(Long cdDocumento) {
		this.cdDocumento = cdDocumento;
	}

	public String getDsLink() {
		return dsLink;
	}

	public void setDsLink(String dsLink) {
		this.dsLink = dsLink;
	}

	public String getDsDocumento() {
		return dsDocumento;
	}

	public void setDsDocumento(String dsDocumento) {
		this.dsDocumento = dsDocumento;
	}

	public String getNmDocumento() {
		return nmDocumento;
	}

	public void setNmDocumento(String nmDocumento) {
		this.nmDocumento = nmDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getNmFisicoDocumento() {
		return nmFisicoDocumento;
	}

	public void setNmFisicoDocumento(String nmFisicoDocumento) {
		this.nmFisicoDocumento = nmFisicoDocumento;
	}

	public TipoDocumento getTpDocumentoTela() {
		return tpDocumentoTela;
	}

	public void setTpDocumentoTela(TipoDocumento tpDocumentoTela) {
		this.tpDocumentoTela = tpDocumentoTela;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}
