package com.prime.app.agvirtual.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabSubservico;
import com.prime.app.agvirtual.enums.SIMNAOEnum;
import com.prime.infra.util.WrapperUtils;

public class SubServicoTO implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7957747778700774977L;
	
	private String cdServicoCsi;
    private Long cdSubservico;
    private String cdSerCom;
    private String cdServExe;
    private String dsCondExec;
    private String dsFormaPgto;
    private String dsLink;
    private String dsPrazoAtend;
    private String dsPreco;
    private String dsSubservico;
    private Date dataAtualizacao;
    private Date dataPublicacao;
    private boolean flagPublicGuia;
    private boolean flagPublicTabPrecos;
    private List<DocumentoTO> listaDocumentos;
    private List<CanalAtendimentoTO> listaCanalAtendimento;
    private ServicoTO servico;
    
    private String simNaoGuia;
    
    private String simNaoPreco;
    
//    private List<AgvTabAcaoAutoatendimento> agvTabAcaoAutoatendimentoList;
//    private List<AgvTabSubservicoDocumento> agvTabSubservicoDocumentoList;
//    private List<SubserviçoCanalDeAtendiment> subserviçoCanalDeAtendimentList;

    public SubServicoTO() {
    }

    public SubServicoTO(Long cdServicoCsi, String cdSerCom, String cdServExe,Long cdSubservico,
                            String dsCondExec, String dsFormaPgto,
                            String dsLink, String dsPrazoAtend, String dsPreco,
                            String dsSubservico, Date dtAtualizacao,
                            Date dtPublicacao, boolean flPublicGuia,
                            boolean flPublicTabPrecos ,ServicoTO servico) {
        this.cdServicoCsi = cdServicoCsi.toString();
        this.cdSerCom = cdSerCom;
        this.cdServExe = cdServExe;
        this.cdSubservico = cdSubservico;
        this.dsCondExec = dsCondExec;
        this.dsFormaPgto = dsFormaPgto;
        this.dsLink = dsLink;
        this.dsPrazoAtend = dsPrazoAtend;
        this.dsPreco = dsPreco;
        this.dsSubservico = dsSubservico;
        this.dataAtualizacao = (dtAtualizacao);
        this.dataPublicacao = (dtPublicacao);
        this.flagPublicGuia = flPublicGuia;
        this.flagPublicTabPrecos = flPublicTabPrecos;
        this.servico  = servico;
    }
    
    public SubServicoTO(Long codSubServico, String dsSubServico) {
    	this.cdSubservico = (codSubServico);
    	this.dsSubservico = dsSubServico;
	}

    /**
     * Parse to Entity Object
     * @param canais
     * @param documentos
     * @return
     */
	public AgvTabSubservico toEntity(List<CanalAtendimentoTO> canais, List<DocumentoTO> documentos) {
		
    	AgvTabSubservico entity =  new AgvTabSubservico( 
    			 WrapperUtils.toLong(cdServicoCsi), cdSerCom, cdServExe, cdSubservico,
                 dsCondExec,  dsFormaPgto,
                 dsLink,  dsPrazoAtend,  dsPreco,
                 dsSubservico,  new Date(),
                 dataPublicacao,  flagPublicGuia,
                 flagPublicTabPrecos );
    	
    	ArrayList listaDocumentos =  new ArrayList();
    	for (Iterator iterator = documentos.iterator(); iterator.hasNext();) {
			DocumentoTO documentoTO = (DocumentoTO) iterator.next();
			listaDocumentos.add(documentoTO.toEntity());
		}
    	ArrayList listaCanais =  new ArrayList();
    	for (Iterator iterator = canais.iterator(); iterator.hasNext();) {
			CanalAtendimentoTO canalTO = (CanalAtendimentoTO) iterator.next();
			listaCanais.add(canalTO.toEntity());
		}
    	
//    	if(servico != null){
//    	entity.setServico(servico.toEntity(null));
//    	}else{
//    		entity.setServico(null);
//    	}
    	
    	entity.setListaCanalAtendimento(listaCanais);
    	entity.setListaDocumentos(listaDocumentos);
    	
		return entity;
	}
	
	public AgvTabSubservico toEntity() {
		AgvTabSubservico entity =  new AgvTabSubservico( 
   			 WrapperUtils.toLong(cdServicoCsi),  cdSerCom,  cdServExe, cdSubservico,
                dsCondExec,  dsFormaPgto,
                dsLink,  dsPrazoAtend,  dsPreco,
                dsSubservico,  new Date(),
                dataPublicacao,  flagPublicGuia,
                flagPublicTabPrecos);

		return entity;
	}


    public String getCdServicoCsi() {
        return cdServicoCsi;
    }

    public void setCdServicoCsi(String cdServicoCsi) {
        this.cdServicoCsi = cdServicoCsi;
    }

    public Long getCdSubservico() {
        return cdSubservico;
    }

    public void setCdSubservico(Long cdSubservico) {
        this.cdSubservico = cdSubservico;
    }

    public String getDsCondExec() {
    	this.dsCondExec = (dsCondExec != null ? dsCondExec.trim() : "");
        return dsCondExec;
    }

    public void setDsCondExec(String dsCondExec) {
        this.dsCondExec = dsCondExec;
    }

    public String getDsFormaPgto() {
    	this.dsFormaPgto = ( dsFormaPgto != null ? dsFormaPgto.trim() : "" );
        return dsFormaPgto;
    }

    public void setDsFormaPgto(String dsFormaPgto) {
        this.dsFormaPgto = dsFormaPgto;
    }

    public String getDsLink() {
    	this.dsLink = ( dsLink != null ? dsLink.trim() : "" );
        return dsLink;
    }

    public void setDsLink(String dsLink) {
        this.dsLink = dsLink;
    }

    public String getDsPrazoAtend() {
    	this.dsPrazoAtend = ( dsPrazoAtend != null ? dsPrazoAtend.trim() : "" );
        return dsPrazoAtend;
    }

    public void setDsPrazoAtend(String dsPrazoAtend) {
        this.dsPrazoAtend = dsPrazoAtend;
    }

    public String getDsPreco() {
    	dsPreco = (dsPreco != null ? dsPreco.trim() : "");
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

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}
	

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public Boolean getFlagPublicGuia() {
		return flagPublicGuia;
	}

	public void setFlagPublicGuia(Boolean flagPublicGuia) {
		this.flagPublicGuia = flagPublicGuia;
	}

	public Boolean getFlagPublicTabPrecos() {
		return flagPublicTabPrecos;
	}

	public void setFlagPublicTabPrecos(Boolean flagPublicTabPrecos) {
		this.flagPublicTabPrecos = flagPublicTabPrecos;
	}

	public List<DocumentoTO> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<DocumentoTO> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public List<CanalAtendimentoTO> getListaCanalAtendimento() {
		return listaCanalAtendimento;
	}

	public void setListaCanalAtendimento(
			List<CanalAtendimentoTO> listaCanalAtendimento) {
		this.listaCanalAtendimento = listaCanalAtendimento;
	}

	public void setFlagPublicGuia(boolean flagPublicGuia) {
		this.flagPublicGuia = flagPublicGuia;
	}

	public void setFlagPublicTabPrecos(boolean flagPublicTabPrecos) {
		this.flagPublicTabPrecos = flagPublicTabPrecos;
	}

	public ServicoTO getServico() {
		return servico;
	}

	public void setServico(ServicoTO servico) {
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

	public String getSimNaoGuia() {
		
		if( this.flagPublicGuia ) 
			this.simNaoGuia = SIMNAOEnum.SIM.getValue();
		else 
			this.simNaoGuia = SIMNAOEnum.NAO.getValue();			
		
		return simNaoGuia;
	}

	public void setSimNaoGuia(String simNaoGuia) {
		simNaoGuia = simNaoGuia;
	}

	public String getSimNaoPreco() {
		
		if( this.flagPublicTabPrecos )
			this.simNaoPreco = SIMNAOEnum.SIM.getValue();
		else
			this.simNaoPreco = SIMNAOEnum.NAO.getValue();
		
		return simNaoPreco;
	}

	public void setSimNaoPreco(String simNaoPreco) {
		simNaoPreco = simNaoPreco;
	}

	
}