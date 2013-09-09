package com.prime.app.agvirtual.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.prime.app.agvirtual.to.DocumentoTO;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabDocumento.findAll", query = "select o from AgvTabDocumento o")
})
@Table(name = "AGV_TAB_DOCUMENTO", schema = Schema.DB_OWNER)
public class AgvTabDocumento implements BaseEntity<DocumentoTO>{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2228162724836110363L;
	
	@Id
	@Column(name = "CD_DOCUMENTO", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_DOCUMENTO", sequenceName = "SQ_AGV_DOCUMENTO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_DOCUMENTO")
    private Long cdDocumento;
    @Column(name="DS_LINK", length = 60)
    private String dsLink;
    @Column(name="DS_DESCRICAO_DOC", length = 220)
    private String dsDocumento;
    @Column(name="DS_DOCUMENTO", length = 50)
    private String nmDocumento;
    @Column(name="DS_FISICO_DOC", length = 250)
    private String nmFisicoDocumento;
    @Column(name="TP_DOCUMENTO", length = 2)
    private Integer tipoDocumento;
    @Column(name="TP_PESSOA", length = 2)
    private Integer tipoPessoa;

    public AgvTabDocumento() {
    }

    public AgvTabDocumento(Long cdDocumento,
                           String dsLink,
                           String nmDocumento ,
                           String dsDocumento ,
                           String nmFisicoDocumento,
                           Integer tipoDocumento,
                           Integer tipoPessoa
                           
    						) {
        this.cdDocumento = cdDocumento;
        this.dsLink = dsLink;
        this.nmDocumento = nmDocumento;
        this.dsDocumento = dsDocumento;
        this.tipoDocumento = tipoDocumento;
        this.nmFisicoDocumento =nmFisicoDocumento;
        this.tipoPessoa = tipoPessoa;
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

    public String getNmDocumento() {
        return nmDocumento;
    }

    public void setNmDocumento(String nmDocumento) {
        this.nmDocumento = nmDocumento;
    }

	public String getDsDocumento() {
		return dsDocumento;
	}

	public void setDsDocumento(String dsDocumento) {
		this.dsDocumento = dsDocumento;
	}

	public String getNmFisicoDocumento() {
		return nmFisicoDocumento;
	}

	public void setNmFisicoDocumento(String nmFisicoDocumento) {
		this.nmFisicoDocumento = nmFisicoDocumento;
	}

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(Integer tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public DocumentoTO parseTO() {
		DocumentoTO documentoTO =  new DocumentoTO();
		documentoTO.setCdDocumento(cdDocumento);
		documentoTO.setDsDocumento(dsDocumento);
		documentoTO.setDsLink(dsLink);
		documentoTO.setNmDocumento(nmDocumento);
		documentoTO.setNmFisicoDocumento(nmFisicoDocumento);
		documentoTO.setTipoDocumento(tipoDocumento.toString());
		documentoTO.setTipoPessoa(tipoPessoa.toString());
		return documentoTO;
	}
	
}
