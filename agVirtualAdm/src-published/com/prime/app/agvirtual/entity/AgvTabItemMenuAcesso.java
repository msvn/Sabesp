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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabItemMenuAcesso.findAll", query = "select o from AgvTabItemMenuAcesso o")
})
@Table(name = "AGV_TAB_ITEM_MENU_ACESSO", schema = Schema.DB_OWNER)
@PrimaryKeyJoinColumn(name = "CD_ITEM_MENU_ACESSO")
public class AgvTabItemMenuAcesso extends AgvTabItemMenu implements Serializable {
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -3611511061707182116L;
	
	@Column(name="DS_LINK", length = 100)
	private String dsLink;
	
    @OneToMany(mappedBy = "agvTabItemMenuAcesso")
    private List<AgvTabBloqueioDetalhe> AgvTabBloqueioDetalheList;
    
    @Fetch(value=FetchMode.SUBSELECT)
    @ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.MERGE})
    @JoinTable(name="AGV_TAB_MENU_ACESSO_GRUPO", schema = Schema.DB_OWNER, 
		joinColumns = { @JoinColumn( name="CD_ITEM_MENU_ACESSO") },
	    inverseJoinColumns = @JoinColumn( name="CD_ITEM_MENU_GRUPO")
    ) 
	private List<AgvTabItemMenuGrupo> itensGrupo;
    
    @OneToOne
    @Fetch(FetchMode.JOIN)
	@JoinColumn(name="CD_AUTOATENDIMENTO")
    private AutoAtendimento autoAtendimento;
    
    public AutoAtendimento getAutoAtendimento() {
		return autoAtendimento;
	}

	public void setAutoAtendimento(AutoAtendimento autoAtendimento) {
		this.autoAtendimento = autoAtendimento;
	}

    public AgvTabItemMenuAcesso() {
    }
    
    public AgvTabItemMenuAcesso(long cdMenu) {
    	this.setCdItemMenu(cdMenu);
	}

	public String getDsLink() {
		return dsLink;
	}

	public void setDsLink(String dsLink) {
		this.dsLink = dsLink;
	}

    public List<AgvTabBloqueioDetalhe> getAgvTabBloqueioDetalheList() {
        return AgvTabBloqueioDetalheList;
    }

    public void setAgvTabBloqueioDetalheList(List<AgvTabBloqueioDetalhe> AgvTabBloqueioDetalheList) {
        this.AgvTabBloqueioDetalheList = AgvTabBloqueioDetalheList;
    }

	public List<AgvTabItemMenuGrupo> getItensGrupo() {
		return itensGrupo;
	}

	public void setItensGrupo(List<AgvTabItemMenuGrupo> itensGrupo) {
		this.itensGrupo = itensGrupo;
	}
}
