package com.prime.app.agvirtual.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.prime.app.agvirtual.to.CanalAtendimentoTO;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabCanalAtend.findAll", query = "select o from AgvTabCanalAtend o")
})
@Table(name = "AGV_TAB_CANAL_ATEND", schema = Schema.DB_OWNER)
public class AgvTabCanalAtend implements BaseEntity<CanalAtendimentoTO>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4862414422394641024L;
    
    @Id
	@Column(name = "CD_CANAL_ATEND", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_CANAL_ATEND", sequenceName = "SQ_AGV_CANAL_ATEND", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_CANAL_ATEND")
    private Long cdCanalAtend;
    @Column(name="DS_INFO_CANAL_ATEND", length = 200)
    private String dsInfoCanalAtend;
    @Column(name="DS_LINK", length = 50)
    private String dsLink;
    @Column(name="DS_LINK_URL", length = 200)
    private String dsLinkUrl;
    @Column(name="DS_TELEFONE", length = 15)
    private String dsTelefone;
    @Column(name="DS_CANAL_ATEND", length = 30)
    private String nmCanalAtend;
    @Column(name="DS_DIAHORA_ATEND", length = 200)
    private String dsDiaHorarioAtend;
    @Column(name="CD_DIRETORIA")
    private Integer codDiretoria;
    
    public AgvTabCanalAtend() {
    }

    public AgvTabCanalAtend(Long cdCanalAtend, String dsInfoCanalAtend,
                            String dsLink, String dsTelefone,
                            String nmCanalAtend , String dsLinkUrl , String dsDiaHorarioAtend,
                            Integer codDiretoria) {
        this.cdCanalAtend = cdCanalAtend;
        this.dsInfoCanalAtend = dsInfoCanalAtend;
        this.dsLink = dsLink;
        this.dsTelefone = dsTelefone;
        this.nmCanalAtend = nmCanalAtend;
        this.dsLinkUrl = dsLinkUrl;
        this.dsDiaHorarioAtend =  dsDiaHorarioAtend;
        this.codDiretoria = codDiretoria;
    }

    public Long getCdCanalAtend() {
        return cdCanalAtend;
    }

    public void setCdCanalAtend(Long cdCanalAtend) {
        this.cdCanalAtend = cdCanalAtend;
    }

    public String getDsInfoCanalAtend() {
        return dsInfoCanalAtend;
    }

    public void setDsInfoCanalAtend(String dsInfoCanalAtend) {
        this.dsInfoCanalAtend = dsInfoCanalAtend;
    }

    public String getDsLink() {
        return dsLink;
    }

    public void setDsLink(String dsLink) {
        this.dsLink = dsLink;
    }

    public String getDsTelefone() {
        return dsTelefone;
    }

    public void setDsTelefone(String dsTelefone) {
        this.dsTelefone = dsTelefone;
    }

    public String getNmCanalAtend() {
        return nmCanalAtend;
    }

    public void setNmCanalAtend(String nmCanalAtend) {
        this.nmCanalAtend = nmCanalAtend;
    }

	public String getDsLinkUrl() {
		return dsLinkUrl;
	}

	public void setDsLinkUrl(String dsLinkUrl) {
		this.dsLinkUrl = dsLinkUrl;
	}

	public String getDsDiaHorarioAtend() {
		return dsDiaHorarioAtend;
	}

	public void setDsDiaHorarioAtend(String dsDiaHorarioAtend) {
		this.dsDiaHorarioAtend = dsDiaHorarioAtend;
	}

	public Integer getCodDiretoria() {
		return codDiretoria;
	}

	public void setCodDiretoria(Integer codDiretoria) {
		this.codDiretoria = codDiretoria;
	}

	public CanalAtendimentoTO parseTO() {
		CanalAtendimentoTO to =  new CanalAtendimentoTO();
		to.setCdCanalAtend(cdCanalAtend);
		to.setCodDiretoria(codDiretoria.toString());
		to.setDsDiaHorarioAtend(dsDiaHorarioAtend);
		to.setDsInfoCanalAtend(dsInfoCanalAtend);
		to.setDsLink(dsLink);
		to.setDsLinkUrl(dsLinkUrl);
		to.setDsTelefone(dsTelefone);
		to.setNmCanalAtend(getDsLink());
		return to;
	}
}
