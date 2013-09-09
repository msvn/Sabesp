package com.prime.app.agvirtual.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.prime.app.agvirtual.enums.TipoSecao;
import com.prime.app.agvirtual.to.SecaoPaginaIncialTO;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabSecaoPagIni.findAll", query = "select o from AgvTabSecaoPagIni o")
})
@Table(name = "AGV_TAB_SECAO_PAG_INI", schema = Schema.DB_OWNER)
public class AgvTabSecaoPagIni implements BaseEntity<SecaoPaginaIncialTO> {
	
    /**
	 * Serial.
	 */
	private static final long serialVersionUID = -4796545375632248181L;
	@Id
    @Column(name="CD_SECAO", nullable = false, length = 8)
    private Long cdSecao;
    @Column(name="QT_ITENS", length = 1)
    private Long qtItens;
    @Column(name="TP_SECAO", length = 1)
    private Long tpSecao;
    @Column(name="TT_SECAO", length = 20)
    private String ttSecao;
    @OneToMany(mappedBy = "agvTabSecaoPagIni")
    private List<AgvTabItemPagIni> agvTabItemPagIniList;

    public AgvTabSecaoPagIni() {
    }

    public AgvTabSecaoPagIni(Long cdSecao, Long qtItens, Long tpSecao,
                             String ttSecao) {
        this.cdSecao = cdSecao;
        this.qtItens = qtItens;
        this.tpSecao = tpSecao;
        this.ttSecao = ttSecao;
    }

    public Long getCdSecao() {
        return cdSecao;
    }

    public void setCdSecao(Long cdSecao) {
        this.cdSecao = cdSecao;
    }

    public Long getQtItens() {
        return qtItens;
    }

    public void setQtItens(Long qtItens) {
        this.qtItens = qtItens;
    }

    public Long getTpSecao() {
        return tpSecao;
    }

    public void setTpSecao(Long tpSecao) {
        this.tpSecao = tpSecao;
    }

    public String getTtSecao() {
        return ttSecao;
    }

    public void setTtSecao(String ttSecao) {
        this.ttSecao = ttSecao;
    }

    public List<AgvTabItemPagIni> getAgvTabItemPagIniList() {
        return agvTabItemPagIniList;
    }

    public void setAgvTabItemPagIniList(List<AgvTabItemPagIni> agvTabItemPagIniList) {
        this.agvTabItemPagIniList = agvTabItemPagIniList;
    }

    public AgvTabItemPagIni addAgvTabItemPagIni(AgvTabItemPagIni agvTabItemPagIni) {
        getAgvTabItemPagIniList().add(agvTabItemPagIni);
        agvTabItemPagIni.setAgvTabSecaoPagIni(this);
        return agvTabItemPagIni;
    }

    public AgvTabItemPagIni removeAgvTabItemPagIni(AgvTabItemPagIni agvTabItemPagIni) {
        getAgvTabItemPagIniList().remove(agvTabItemPagIni);
        agvTabItemPagIni.setAgvTabSecaoPagIni(null);
        return agvTabItemPagIni;
    }
    
    /**
	 * Método responsável por converter o objeto
	 * Entity(AgvTabSecaoPagIni) para o TO(SecaoPaginaIncial).
	 */
	public SecaoPaginaIncialTO parseTO() {
		
		SecaoPaginaIncialTO secao = new SecaoPaginaIncialTO();
		
		secao.setCdSecao(cdSecao);		
		secao.setQtItens(qtItens.toString());
		secao.setTpSecao(TipoSecao.byValue(tpSecao.intValue()));
		secao.setTtSecao(ttSecao);
		return secao;
	}
}
