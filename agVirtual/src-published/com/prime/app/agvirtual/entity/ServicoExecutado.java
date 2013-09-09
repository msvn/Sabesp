package com.prime.app.agvirtual.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "ServicoExecutado.findAll", query = "select o from ServicoExecutado o") })
@Table(name = "AGV_TAB_SERV_EXE ", schema = Schema.DB_OWNER)
public class ServicoExecutado implements BaseEntity {

	private static final long serialVersionUID = 1318677872193753100L;

	@Id
	@Column(name = "CD_SERV_EXE")
	private Long cdServExe;

	@Column(name = "CD_SERV_EXE_CSI")
	private Long cdServExeCsi;

	@Column(name = "DS_ATRIBUTO_IMOVEL")
	private String dsAtributoImovel;

	@Column(name = "DS_VALOR_ATRIBUTO")
	private String dsValorAtributo;

	@Column(name = "CD_ACAO")
	private Long cdAcao;
	
	@ManyToOne
	@JoinColumn(name = "CD_ACAO" , insertable=false, updatable=false)
	private AcaoAutoAtendimento acaoServExe;

	public Long getCdServExe() {
		return cdServExe;
	}

	public void setCdServExe(Long cdServExe) {
		this.cdServExe = cdServExe;
	}

	public Long getCdServExeCsi() {
		return cdServExeCsi;
	}

	public void setCdServExeCsi(Long cdServExeCsi) {
		this.cdServExeCsi = cdServExeCsi;
	}

	public String getDsAtributoImovel() {
		return dsAtributoImovel;
	}

	public void setDsAtributoImovel(String dsAtributoImovel) {
		this.dsAtributoImovel = dsAtributoImovel;
	}

	public String getDsValorAtributo() {
		return dsValorAtributo;
	}

	public void setDsValorAtributo(String dsValorAtributo) {
		this.dsValorAtributo = dsValorAtributo;
	}

	public Long getCdAcao() {
		return cdAcao;
	}

	public void setCdAcao(Long cdAcao) {
		this.cdAcao = cdAcao;
	}

	public Object parseTO() {
		return null;
	}

	public AcaoAutoAtendimento getAcaoServExe() {
		return acaoServExe;
	}

	public void setAcaoServExe(AcaoAutoAtendimento acaoServExe) {
		this.acaoServExe = acaoServExe;
	}
	
}
