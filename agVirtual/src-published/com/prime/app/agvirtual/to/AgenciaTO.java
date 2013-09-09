package com.prime.app.agvirtual.to;

import java.io.Serializable;

import com.prime.app.agvirtual.entity.Endereco;

/**
 * @author felipepm
 */
public class AgenciaTO implements Serializable {
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -6313635810290002581L;
	
	private Long codigo;

	private String nmGeral;
	
	private String cdBairro;
	
	private String cdCep;
	
	private String cdDDD;
	
	private String cdLogradouro;
	
	private String dsComplemento;
	
	private String nrEndereco;
	
	private String nrTelefone;
	
	private String cdDDDFax;
	
	private String nrTelFax;
	
	private String dsRamal;
	
	private String horaInicio;
	
	private String horaFim;
	
	private String horaInicioIntervalo;
	
	private String horaFimIntervalo;
	
	private String cdUnidCom;  // unidade de negocio CDUNIDCOM

	private String nmAbrUnid;
	
	private String cdMunicip;
	
	private Endereco endereco;
	
	public AgenciaTO() {
		
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNmGeral() {
		return nmGeral;
	}

	public void setNmGeral(String nmGeral) {
		this.nmGeral = nmGeral;
	}

	public String getCdBairro() {
		return cdBairro;
	}

	public void setCdBairro(String cdBairro) {
		this.cdBairro = cdBairro;
	}

	public String getCdCep() {
		return cdCep;
	}

	public void setCdCep(String cdCep) {
		this.cdCep = cdCep;
	}

	public String getCdDDD() {
		return cdDDD;
	}

	public void setCdDDD(String cdDDD) {
		this.cdDDD = cdDDD;
	}

	public String getCdLogradouro() {
		return cdLogradouro;
	}

	public void setCdLogradouro(String cdLogradouro) {
		this.cdLogradouro = cdLogradouro;
	}

	public String getDsComplemento() {
		return dsComplemento;
	}

	public void setDsComplemento(String dsComplemento) {
		this.dsComplemento = dsComplemento;
	}

	public String getNrEndereco() {
		return nrEndereco;
	}

	public void setNrEndereco(String nrEndereco) {
		this.nrEndereco = nrEndereco;
	}

	public String getNrTelefone() {
		return nrTelefone;
	}

	public void setNrTelefone(String nrTelefone) {
		this.nrTelefone = nrTelefone;
	}

	public String getCdDDDFax() {
		return cdDDDFax;
	}

	public void setCdDDDFax(String cdDDDFax) {
		this.cdDDDFax = cdDDDFax;
	}

	public String getNrTelFax() {
		return nrTelFax;
	}

	public void setNrTelFax(String nrTelFax) {
		this.nrTelFax = nrTelFax;
	}

	public String getDsRamal() {
		return dsRamal;
	}

	public void setDsRamal(String dsRamal) {
		this.dsRamal = dsRamal;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}

	public String getHoraInicioIntervalo() {
		return horaInicioIntervalo;
	}

	public void setHoraInicioIntervalo(String horaInicioIntervalo) {
		this.horaInicioIntervalo = horaInicioIntervalo;
	}

	public String getHoraFimIntervalo() {
		return horaFimIntervalo;
	}

	public void setHoraFimIntervalo(String horaFimIntervalo) {
		this.horaFimIntervalo = horaFimIntervalo;
	}

	public String getCdUnidCom() {
		return cdUnidCom;
	}

	public void setCdUnidCom(String cdUnidCom) {
		this.cdUnidCom = cdUnidCom;
	}

	public String getNmAbrUnid() {
		return nmAbrUnid;
	}

	public void setNmAbrUnid(String nmAbrUnid) {
		this.nmAbrUnid = nmAbrUnid;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getCdMunicip() {
		return cdMunicip;
	}

	public void setCdMunicip(String cdMunicip) {
		this.cdMunicip = cdMunicip;
	}

}
