package com.prime.app.agvirtual.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.prime.app.agvirtual.to.ServicoTO;
import com.prime.app.agvirtual.to.SubServicoTO;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabServico.findAll", query = "select o from AgvTabServico o")
})
@Table(name = "AGV_TAB_SERVICO" , schema = Schema.DB_OWNER)
public class AgvTabServico implements BaseEntity<ServicoTO>{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -3745725235800539197L;
	
	
	@Id
	@Column(name = "CD_SERVICO", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_SERVICO", sequenceName = "SQ_AGV_SERVICO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_SERVICO")
    private Long cdServico;
    @Column(name="DS_OBSERVACAO", length = 200)
    private String dsObservacao;
    @Column(name="DS_SERVICO", length = 50)
    private String nmServico;
    @Column(name="CD_CATEGORIA", length = 10)
    private String cdCategoria;
    
	@JoinColumn(name="CD_SERVICO")
	@OneToMany(cascade ={CascadeType.MERGE
			}, mappedBy="servico", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)		
    private List<AgvTabSubservico> agvListaServSubServ;
    

    public AgvTabServico() {
    }

    public AgvTabServico(Long cdServico, String dsObservacao,
                         String nmServico) {
        this.cdServico = cdServico;
        this.dsObservacao = dsObservacao;
        this.nmServico = nmServico;
    }

    public Long getCdServico() {
        return cdServico;
    }

    public void setCdServico(Long cdServico) {
        this.cdServico = cdServico;
    }

    public String getDsObservacao() {
        return dsObservacao;
    }

    public void setDsObservacao(String dsObservacao) {
        this.dsObservacao = dsObservacao;
    }

    public String getNmServico() {
        return nmServico;
    }

    public void setNmServico(String nmServico) {
        this.nmServico = nmServico;
    }
    

	public ServicoTO parseTO() {
		ServicoTO to =  new ServicoTO();
		
		to.setCdServico(getCdServico());
		to.setDsObservacao(getDsObservacao());
		to.setNmServico(getNmServico());
		to.setCdCategoria(getCdCategoria());
		
		if(agvListaServSubServ != null){
			 List<SubServicoTO> lista = new ArrayList<SubServicoTO>(); 
			 
			for (Iterator<AgvTabSubservico> iter = agvListaServSubServ.iterator(); iter.hasNext();) {
				AgvTabSubservico entity = (AgvTabSubservico) iter.next();
				SubServicoTO subTO  = entity.parseTO();
				lista.add(subTO);
			}
			to.setListaSubServico(lista);
		}
		return to;
	}

	public String getCdCategoria() {
		return cdCategoria;
	}

	public void setCdCategoria(String cdCategoria) {
		this.cdCategoria = cdCategoria;
	}

	public List<AgvTabSubservico> getAgvListaServSubServ() {
		return agvListaServSubServ;
	}

	public void setAgvListaServSubServ(List<AgvTabSubservico> agvListaServSubServ) {
		this.agvListaServSubServ = agvListaServSubServ;
	}
}
