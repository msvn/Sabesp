package com.prime.app.agvirtual.entity;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.prime.app.agvirtual.to.CanalAtendimentoTO;
import com.prime.app.agvirtual.to.DocumentoTO;
import com.prime.app.agvirtual.to.SubServicoTO;
import com.prime.infra.util.WrapperUtils;

@Entity
@NamedQueries({
  @NamedQuery(name = "AgvTabSubservico.findAll", query = "select o from AgvTabSubservico o")
})
@Table(name = "AGV_TAB_SUBSERVICO" , schema = Schema.DB_OWNER)
public class AgvTabSubservico implements BaseEntity<SubServicoTO>{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7405184165967299264L;

	@Column(name="CD_GRPSERV_CSI")
    private Long cdServicoCsi;
    
    @Id
	@Column(name = "CD_SUBSERVICO", nullable = false)
	@SequenceGenerator(name = "SQ_AGV_SUBSERVICO", sequenceName = "SQ_AGV_SUBSERVICO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGV_SUBSERVICO")
    private Long cdSubservico;
    
    @Column(name="DS_COND_EXEC", length = 200)
    private String dsCondExec;
    @Column(name="DS_FORMA_PGTO", length = 50)
    private String dsFormaPgto;
    @Column(name="DS_LINK", length = 60)
    private String dsLink;
    @Column(name="DS_PRAZO_ATEND", length = 50)
    private String dsPrazoAtend;
    @Column(name="DS_PRECO", length = 20)
    private String dsPreco;
    @Column(name="DS_SUBSERVICO", length = 50)
    private String dsSubservico;
    @Column(name="DT_ATUALIZACAO")
    private Date dtAtualizacao;
    @Column(name="DT_PUBLICACAO")
    private Date dtPublicacao;
    @Column(name="FL_PUBLIC_GUIA", length = 1)
    private Boolean flPublicGuia;
    @Column(name="FL_PUBLIC_TAB_PRECOS", length = 1)
    private Boolean flPublicTabPrecos;
    @Column(name="CD_SERVCOM_CSI")
    private String cdSerCom;
    @Column(name="CD_SERVEXE_CSI")
    private String cdServExe;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "CD_SERVICO")
    private AgvTabServico servico;
    
	@Fetch(value=FetchMode.SUBSELECT)
	@ManyToMany(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.REFRESH)
	@JoinTable(
			name="AGV_TAB_SUBSERV_DOCUMENTO",
			joinColumns = { @JoinColumn(name="CD_SERVICO" ,nullable = true ) },
			inverseJoinColumns = {	@JoinColumn(name="CD_DOCUMENTO", nullable = false)}
	)
	@Sort(type=SortType.NATURAL)
    private List<AgvTabDocumento> listaDocumentos;
    
    		
    @Fetch(value=FetchMode.SUBSELECT)
	@ManyToMany(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.REFRESH)
	@JoinTable(
			name="AGV_TAB_SUBSERV_CANAL_ATEND",
			joinColumns = { @JoinColumn(name="CD_SERVICO" ,nullable = true ) },
			inverseJoinColumns = {	@JoinColumn(name="CD_CANAL_ATEND", nullable = false)}
	)
	@Sort(type=SortType.NATURAL)
    private List<AgvTabCanalAtend> listaCanalAtendimento;

    public AgvTabSubservico() {
    }

    public AgvTabSubservico(Long cdServicoCsi, String cdSerCom, String cdServExe, Long cdSubservico,
                            String dsCondExec, String dsFormaPgto,
                            String dsLink, String dsPrazoAtend, String dsPreco,
                            String dsSubservico, Date dtAtualizacao,
                            Date dtPublicacao, Boolean flPublicGuia,
                            Boolean flPublicTabPrecos) {
        this.cdServicoCsi = cdServicoCsi;
        this.cdSerCom =cdSerCom;
        this.cdServExe =cdServExe;        
        this.cdSubservico = cdSubservico;
        this.dsCondExec = dsCondExec;
        this.dsFormaPgto = dsFormaPgto;
        this.dsLink = dsLink;
        this.dsPrazoAtend = dsPrazoAtend;
        this.dsPreco = dsPreco;
        this.dsSubservico = dsSubservico;
        this.dtAtualizacao = dtAtualizacao;
        this.dtPublicacao = dtPublicacao;
        this.flPublicGuia = flPublicGuia;
        this.flPublicTabPrecos = flPublicTabPrecos;
    }

    public Long getCdServicoCsi() {
        return cdServicoCsi;
    }

    public void setCdServicoCsi(Long cdServicoCsi) {
        this.cdServicoCsi = cdServicoCsi;
    }

    public Long getCdSubservico() {
        return cdSubservico;
    }

    public void setCdSubservico(Long cdSubservico) {
        this.cdSubservico = cdSubservico;
    }

    public String getDsCondExec() {
        return dsCondExec;
    }

    public void setDsCondExec(String dsCondExec) {
        this.dsCondExec = dsCondExec;
    }

    public String getDsFormaPgto() {
        return dsFormaPgto;
    }

    public void setDsFormaPgto(String dsFormaPgto) {
        this.dsFormaPgto = dsFormaPgto;
    }

    public String getDsLink() {
        return dsLink;
    }

    public void setDsLink(String dsLink) {
        this.dsLink = dsLink;
    }

    public String getDsPrazoAtend() {
        return dsPrazoAtend;
    }

    public void setDsPrazoAtend(String dsPrazoAtend) {
        this.dsPrazoAtend = dsPrazoAtend;
    }

    public String getDsPreco() {
        return dsPreco;
    }

    public void setDsPreco(String dsPreco) {
        this.dsPreco = dsPreco;
    }

    public String getDsSubservico() {
        return dsSubservico;
    }

    public void setDsSubservico(String dsSubservico) {
        this.dsSubservico = dsSubservico;
    }

    public Date getDtAtualizacao() {
        return dtAtualizacao;
    }

    public void setDtAtualizacao(Date dtAtualizacao) {
        this.dtAtualizacao = dtAtualizacao;
    }

    public Date getDtPublicacao() {
        return dtPublicacao;
    }

    public void setDtPublicacao(Date dtPublicacao) {
        this.dtPublicacao = dtPublicacao;
    }

    public Boolean getFlPublicGuia() {
        return flPublicGuia;
    }

    public void setFlPublicGuia(Boolean flPublicGuia) {
        this.flPublicGuia = flPublicGuia;
    }

    public Boolean getFlPublicTabPrecos() {
        return flPublicTabPrecos;
    }

    public void setFlPublicTabPrecos(Boolean flPublicTabPrecos) {
        this.flPublicTabPrecos = flPublicTabPrecos;
    }
    
    public List<AgvTabDocumento> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<AgvTabDocumento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public List<AgvTabCanalAtend> getListaCanalAtendimento() {
		return listaCanalAtendimento;
	}

	public void setListaCanalAtendimento(
			List<AgvTabCanalAtend> listaCanalAtendimento) {
		this.listaCanalAtendimento = listaCanalAtendimento;
	}

	public SubServicoTO parseTO(){
    	SubServicoTO to =  new SubServicoTO();
		to.setCdServicoCsi(WrapperUtils.isNull(getCdServicoCsi()) ? null : getCdServicoCsi().toString());
		to.setCdSerCom(getCdSerCom());
		to.setCdServExe(getCdServExe());
		to.setCdSubservico(getCdSubservico());
		to.setDataAtualizacao(getDtAtualizacao());
		to.setDataPublicacao(getDtPublicacao());
		to.setDsCondExec(getDsCondExec());
		to.setDsFormaPgto(getDsFormaPgto());
		to.setDsLink(getDsLink());
		to.setDsPrazoAtend(getDsPrazoAtend());
		to.setDsPreco(getDsPreco());
		to.setDsSubservico(getDsSubservico());
		to.setFlagPublicGuia(getFlPublicGuia());
		to.setFlagPublicTabPrecos(getFlPublicTabPrecos());
		
		ArrayList<CanalAtendimentoTO> listaCanalTo = new ArrayList<CanalAtendimentoTO>();
		if(listaCanalAtendimento != null && !listaCanalAtendimento.isEmpty()){
			for (Iterator<AgvTabCanalAtend> iterator = listaCanalAtendimento.iterator(); iterator.hasNext();) {
				AgvTabCanalAtend element = (AgvTabCanalAtend) iterator.next();
				listaCanalTo.add(element.parseTO());
			}
		}
		
		ArrayList<DocumentoTO> listaDocTo = new ArrayList<DocumentoTO>();
		if(listaDocumentos != null && !listaDocumentos.isEmpty()){
			for (Iterator<AgvTabDocumento> iterator = listaDocumentos.iterator(); iterator.hasNext();) {
				AgvTabDocumento element = (AgvTabDocumento) iterator.next();
				listaDocTo.add(element.parseTO());
			}
		}
		
		to.setListaCanalAtendimento(listaCanalTo);
		to.setListaDocumentos(listaDocTo);
		
		return to;
    }

	public AgvTabServico getServico() {
		return servico;
	}

	public void setServico(AgvTabServico servico) {
		this.servico = servico;
	}

	public String getCdSerCom() {
		return cdSerCom;
	}

	public void setCdSerCom(String cdSerCom) {
		this.cdSerCom = cdSerCom;
	}

	public String getCdServExe() {
		return cdServExe;
	}

	public void setCdServExe(String cdServExe) {
		this.cdServExe = cdServExe;
	}
	
}
