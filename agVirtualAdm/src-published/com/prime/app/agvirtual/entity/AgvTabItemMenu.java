package com.prime.app.agvirtual.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabItemMenu.findAll", query = "select o from AgvTabItemMenu o")
})
@Table(name = "AGV_TAB_ITEM_MENU", schema = Schema.DB_OWNER)
@Inheritance(strategy=InheritanceType.JOINED)
public class AgvTabItemMenu implements Serializable {
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 6993868581890731802L;
	@Id
    @Column(name="CD_ITEM_MENU", nullable = false)
    private Long cdItemMenu;
    @Column(name="DS_ITEM_MENU", length = 60)
    private String dsItemMenu;
    @Column(name="TP_ITEM_MENU", length = 20)
    private String tpItemMenu;

    public AgvTabItemMenu() {
    }

    public AgvTabItemMenu(Long cdItemMenu,  String dsItemMenu, String tpItemMenu) {
        this.cdItemMenu = cdItemMenu;
        this.dsItemMenu = dsItemMenu;
        this.tpItemMenu = tpItemMenu;
    }

    public Long getCdItemMenu() {
        return cdItemMenu;
    }

    public void setCdItemMenu(Long cdItemMenu) {
        this.cdItemMenu = cdItemMenu;
    }

    public String getDsItemMenu() {
        return dsItemMenu;
    }

    public void setDsItemMenu(String dsItemMenu) {
        this.dsItemMenu = dsItemMenu;
    }

	public String getTpItemMenu() {
		return tpItemMenu;
	}

	public void setTpItemMenu(String tpItemMenu) {
		this.tpItemMenu = tpItemMenu;
	}

}
