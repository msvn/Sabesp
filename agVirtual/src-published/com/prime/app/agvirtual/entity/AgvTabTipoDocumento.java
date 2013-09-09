package com.prime.app.agvirtual.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabTipoDocumento.findAll", query = "select o from AgvTabTipoDocumento o")
})
@Table(name = "AGV_TAB_TIPO_DOCUMENTO", schema = Schema.DB_OWNER)
public class AgvTabTipoDocumento implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7368193199047110768L;
	@Id
    @Column(name="CD_TIPO_DOCUMENTO", nullable = false)
    private Long cdTipoDocumento;
    @Column(name="DS_TIPO_DOCUMENTO", length = 20)
    private String nmTipoDocumento;
    

    public AgvTabTipoDocumento() {
    }

    public AgvTabTipoDocumento(Long cdTipoDocumento, String nmTipoDocumento) {
        this.cdTipoDocumento = cdTipoDocumento;
        this.nmTipoDocumento = nmTipoDocumento;
    }

    public Long getCdTipoDocumento() {
        return cdTipoDocumento;
    }

    public void setCdTipoDocumento(Long cdTipoDocumento) {
        this.cdTipoDocumento = cdTipoDocumento;
    }

    public String getNmTipoDocumento() {
        return nmTipoDocumento;
    }

    public void setNmTipoDocumento(String nmTipoDocumento) {
        this.nmTipoDocumento = nmTipoDocumento;
    }

}
