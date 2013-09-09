package com.prime.app.agvirtual.to;

import java.io.Serializable;

public class AcatamentoTO implements Serializable {
	
	private String nrRgiLig;
	
	private String cdGrpServ;
	
	private String cdServCom;
	
	private String dsTela50;
	
	private String dsProxTel;
	
	private String stok;

	public AcatamentoTO(String nrRgi, String codGrp) {
		this.nrRgiLig = nrRgi;
		this.cdGrpServ= codGrp;
	}

	public AcatamentoTO() {
		
	}

	public String getNrRgiLig() {
		return nrRgiLig;
	}

	public void setNrRgiLig(String nrRgiLig) {
		this.nrRgiLig = nrRgiLig;
	}

	public String getCdGrpServ() {
		return cdGrpServ;
	}

	public void setCdGrpServ(String cdGrpServ) {
		this.cdGrpServ = cdGrpServ;
	}

	public String getCdServCom() {
		return cdServCom;
	}

	public void setCdServCom(String cdServCom) {
		this.cdServCom = cdServCom;
	}

	public String getDsTela50() {
		return dsTela50;
	}

	public void setDsTela50(String dsTela50) {
		this.dsTela50 = dsTela50;
	}

	public String getDsProxTel() {
		return dsProxTel;
	}

	public void setDsProxTel(String dsProxTel) {
		this.dsProxTel = dsProxTel;
	}

	public String getStok() {
		return stok;
	}

	public void setStok(String stok) {
		this.stok = stok;
	}

}
