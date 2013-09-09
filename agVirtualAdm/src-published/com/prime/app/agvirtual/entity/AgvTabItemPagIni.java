package com.prime.app.agvirtual.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.prime.app.agvirtual.to.ItemPaginaInicialTO;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabItemPagIni.findAll", query = "select o from AgvTabItemPagIni o")
})
@Table(name = "AGV_TAB_ITEM_PAG_INI",  schema = Schema.DB_OWNER)
public class AgvTabItemPagIni implements BaseEntity<ItemPaginaInicialTO> {
	
    /**
	 * Serial.
	 */
	private static final long serialVersionUID = 5118037460550979158L;
	
	@Id
    @Column(name="CD_ITEM", nullable = false, length = 8)
    private Long cdItem;
    @Column(name="DS_CAMINHO_IMG", length = 100)
    private String dsCaminhoImg;
    @Column(name="DS_ITEM", length = 40)
    private String dsItem;
    @Column(name="DS_LINK", length = 200)
    private String dsLink;
    @Column(name="DS_RESUMO_ITEM", length = 18)
    private String dsResumoItem;
    @ManyToOne
    @JoinColumn(name = "CD_SECAO")
    private AgvTabSecaoPagIni agvTabSecaoPagIni;

    /**
	 * Construtor padrão.
	 */
    public AgvTabItemPagIni() {
    }

    /**
	 * Construtor padrão.
	 */
    public AgvTabItemPagIni(Long cdItem, String dsCaminhoImg,
                            String dsItem, String dsLink, String dsResumoItem,
                            AgvTabSecaoPagIni agvTabSecaoPagIni) {
        this.cdItem = cdItem;
        this.agvTabSecaoPagIni = agvTabSecaoPagIni;
        this.dsCaminhoImg = dsCaminhoImg;
        this.dsItem = dsItem;
        this.dsLink = dsLink;
        this.dsResumoItem = dsResumoItem;
    }

    /**
	 * Método responsável por converter o objeto
	 * Entity(AgvTabItemPagIni) para o TO(ItemPaginaInicial).
	 */
    public ItemPaginaInicialTO parseTO() {
    	
		ItemPaginaInicialTO item = new ItemPaginaInicialTO();
		item.setCdItem(cdItem.toString());
		item.setDsCaminhoImagem(dsCaminhoImg);
		item.setDsItem(dsItem);
		item.setDsLink(dsLink);
		item.setDsResumoItem(dsResumoItem);
		item.setSecao(agvTabSecaoPagIni.parseTO());
		
		return item;
	}

    public Long getCdItem() {
        return cdItem;
    }

    public void setCdItem(Long cdItem) {
        this.cdItem = cdItem;
    }

    public String getDsCaminhoImg() {
        return dsCaminhoImg;
    }

    public void setDsCaminhoImg(String dsCaminhoImg) {
        this.dsCaminhoImg = dsCaminhoImg;
    }

    public String getDsItem() {
        return dsItem;
    }

    public void setDsItem(String dsItem) {
        this.dsItem = dsItem;
    }

    public String getDsLink() {
        return dsLink;
    }

    public void setDsLink(String dsLink) {
        this.dsLink = dsLink;
    }

    public AgvTabSecaoPagIni getAgvTabSecaoPagIni() {
        return agvTabSecaoPagIni;
    }

    public void setAgvTabSecaoPagIni(AgvTabSecaoPagIni agvTabSecaoPagIni) {
        this.agvTabSecaoPagIni = agvTabSecaoPagIni;
    }

	public String getDsResumoItem() {
		return dsResumoItem;
	}

	public void setDsResumoItem(String dsResumoItem) {
		this.dsResumoItem = dsResumoItem;
	}
}
