package com.prime.app.agvirtual.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabItemMenuGrupo.findAll", query = "select o from AgvTabItemMenuGrupo o")
})
@Table(name = "AGV_TAB_ITEM_MENU_GRUPO" , schema = Schema.DB_OWNER)
@PrimaryKeyJoinColumn(name = "CD_ITEM_MENU_GRUPO")
public class AgvTabItemMenuGrupo extends AgvTabItemMenu implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4819791827257357973L;

    @Column(name="CD_ITEM_PAI")
    private Long cdItemPai;

    @Fetch(value=FetchMode.SUBSELECT)
    @ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.MERGE})
    @JoinTable(name="AGV_TAB_MENU_ACESSO_GRUPO", schema = Schema.DB_OWNER,
	    joinColumns = { @JoinColumn( name="CD_ITEM_MENU_GRUPO") },
	    inverseJoinColumns = @JoinColumn( name="CD_ITEM_MENU_ACESSO")
    ) 
	private List<AgvTabItemMenuAcesso> itensAcesso;
    
    public AgvTabItemMenuGrupo() {
    	
    }

	public Long getCdItemPai() {
		return cdItemPai;
	}

	public void setCdItemPai(Long cdItemPai) {
		this.cdItemPai = cdItemPai;
	}

	public List<AgvTabItemMenuAcesso> getItensAcesso() {
		return itensAcesso;
	}

	public void setItensAcesso(List<AgvTabItemMenuAcesso> itensAcesso) {
		this.itensAcesso = itensAcesso;
	}
}
