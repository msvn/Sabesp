package com.prime.app.agvirtual.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.icu.util.Calendar;
import com.prime.app.agvirtual.to.AgenciaTO;

public class Imovel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2878695821711846977L;

	private static final Logger logger = LoggerFactory.getLogger(Imovel.class);

	public Imovel() {

	}

	public Imovel(String dsRgi) {
		this.dsRgi = dsRgi;
	}

	private String cdMunicipio;

	private String cdImovel;

	private String fat;

	private String dsCep;

	private String dsCidade;

	private String dsEndereco;

	private String dsGrupoFaturamento;

	private String nrGrupoFaturamento; // CDGRUPFAT

	private String dsRgi;
	
	private String dsDiretoria;

	private Integer idImovel;

	private Integer idImovelMaster;

	private String inImovelMaster;

	private String inInscricaoGEC;

	private String inNecessidadeLoginGEC;

	private String dsBairro;

	private String cdCliente;

	private Boolean permissionaria = false;

	private String codigoSuperIntendencia; //cdunidcon2
	
	private String codigoVicePresidencia; //cdunidcon3

	private String codigoControleFaturamento;

	private List<Conta> contaList = new ArrayList<Conta>();

	private String numeroRGIToBoleto;

	private AgenciaTO agencia;

	private Endereco endereco;

	private boolean master;

	private boolean rolEspecial; // TODO - colocar este atributo na conta tp=6

	// -> rol especial

	public String getDsCep() {
		return dsCep;
	}

	public void setDsCep(String dsCep) {
		this.dsCep = dsCep;
	}

	public String getDsCidade() {
		return dsCidade;
	}

	public void setDsCidade(String dsCidade) {
		this.dsCidade = dsCidade;
	}

	public String getDsEndereco() {
		return dsEndereco;
	}

	public void setDsEndereco(String dsEndereco) {
		this.dsEndereco = dsEndereco;
	}

	public String getDsGrupoFaturamento() {
		return dsGrupoFaturamento;
	}

	public void setDsGrupoFaturamento(String dsGrupoFaturamento) {
		this.dsGrupoFaturamento = dsGrupoFaturamento;
	}

	public String getDsRgi() {
		return dsRgi;
	}

	public void setDsRgi(String dsRgi) {
		this.dsRgi = dsRgi;
	}

	public Integer getIdImovel() {
		return idImovel;
	}

	public void setIdImovel(Integer idImovel) {
		this.idImovel = idImovel;
	}

	public Integer getIdImovelMaster() {
		return idImovelMaster;
	}

	public void setIdImovelMaster(Integer idImovelMaster) {
		this.idImovelMaster = idImovelMaster;
	}

	public String getInImovelMaster() {
		return inImovelMaster;
	}

	public void setInImovelMaster(String inImovelMaster) {
		this.inImovelMaster = inImovelMaster;
	}

	public String getInInscricaoGEC() {
		return inInscricaoGEC;
	}

	public void setInInscricaoGEC(String inInscricaoGEC) {
		this.inInscricaoGEC = inInscricaoGEC;
	}

	public String getInNecessidadeLoginGEC() {
		return inNecessidadeLoginGEC;
	}

	public void setInNecessidadeLoginGEC(String inNecessidadeLoginGEC) {
		this.inNecessidadeLoginGEC = inNecessidadeLoginGEC;
	}

	public String toString() {

		return ToStringBuilder.reflectionToString(this);

	}

	public List<Conta> getContaList() {
		return contaList;
	}

	public void setContaList(List<Conta> contaList) {
		this.contaList = contaList;
	}

	public String getDsBairro() {
		return dsBairro;
	}

	public void setDsBairro(String dsBairro) {
		this.dsBairro = dsBairro;
	}

	public String getCdMunicipio() {
		return cdMunicipio;
	}

	public void setCdMunicipio(String cdMunicipio) {
		this.cdMunicipio = cdMunicipio;
	}

	public String getCdImovel() {
		return cdImovel;
	}

	public void setCdImovel(String cdImovel) {
		this.cdImovel = cdImovel;
	}

	public String getFat() {
		return fat;
	}

	public void setFat(String fat) {
		this.fat = fat;
	}

	public String getCdCliente() {
		return cdCliente;
	}

	public void setCdCliente(String cdCliente) {
		this.cdCliente = cdCliente;
	}

	private void popularContas() {

		Conta conta = new Conta();
		conta.setDtReferencia(Calendar.getInstance().getTime());
		conta.setDtVencimento(Calendar.getInstance().getTime());
		conta.setVlTotal(60.00);
		conta.setSituacaoConta("Em atraso");

		Conta conta1 = new Conta();
		conta1.setDtReferencia(Calendar.getInstance().getTime());
		conta1.setDtVencimento(Calendar.getInstance().getTime());
		conta1.setVlTotal(60.00);
		conta1.setSituacaoConta("Em atraso");

		Conta conta2 = new Conta();
		conta2.setDtReferencia(Calendar.getInstance().getTime());
		conta2.setDtVencimento(Calendar.getInstance().getTime());
		conta2.setVlTotal(60.00);
		conta2.setSituacaoConta("Em atraso");

		Conta conta3 = new Conta();
		conta3.setDtReferencia(Calendar.getInstance().getTime());
		conta3.setDtVencimento(Calendar.getInstance().getTime());
		conta3.setVlTotal(60.00);
		conta3.setSituacaoConta("Em atraso");

		Conta conta4 = new Conta();
		conta4.setDtReferencia(Calendar.getInstance().getTime());
		conta4.setDtVencimento(Calendar.getInstance().getTime());
		conta4.setVlTotal(60.00);
		conta4.setSituacaoConta("Em atraso");

		Conta conta5 = new Conta();
		conta5.setDtReferencia(Calendar.getInstance().getTime());
		conta5.setDtVencimento(Calendar.getInstance().getTime());
		conta5.setVlTotal(60.00);
		conta5.setSituacaoConta("Em atraso");

		this.contaList.add(conta);
		this.contaList.add(conta1);
		this.contaList.add(conta2);
		this.contaList.add(conta3);
		this.contaList.add(conta4);
		this.contaList.add(conta5);

	}

	public Boolean getPermissionaria() {
		return permissionaria;
	}

	public void setPermissionaria(Boolean permissionaria) {
		this.permissionaria = permissionaria;
	}

	public String getCodigoSuperIntendencia() {
		return codigoSuperIntendencia;
	}

	public void setCodigoSuperIntendencia(String codigoSuperIntendencia) {
		this.codigoSuperIntendencia = codigoSuperIntendencia;
	}

	public String getCodigoControleFaturamento() {
		return codigoControleFaturamento;
	}

	public void setCodigoControleFaturamento(String codigoControleFaturamento) {
		this.codigoControleFaturamento = codigoControleFaturamento;
	}

	public String getNumeroRGIBoletoCodBar() {

		if (this.dsRgi.length() != 8) {
			int rGILength = (this.dsRgi.length() - 2);
			final int tamanhoTotal = 8;
			String zeros = "";

			String newRGI = this.dsRgi.substring(0, rGILength);

			int zerosCompletar = (tamanhoTotal - newRGI.length());

			for (int i = 0; i < zerosCompletar; i++) {
				zeros += "0";
			}

			numeroRGIToBoleto = zeros + newRGI;
		} else {
			numeroRGIToBoleto = this.dsRgi;
		}

		return numeroRGIToBoleto;

	}

	public String getNumeroRGIToBoleto() {

		if (this.dsRgi.length() != 8) {
			int rGILength = (this.dsRgi.length() - 2);
			final int tamanhoTotal = 8;
			String zeros = "";

			String barraNumero = "/"
					+ this.dsRgi.substring(rGILength, this.dsRgi.length());

			String newRGI = this.dsRgi.substring(0, rGILength);

			int zerosCompletar = (tamanhoTotal - newRGI.length());

			for (int i = 0; i < zerosCompletar; i++) {
				zeros += "0";
			}

			numeroRGIToBoleto = zeros + newRGI + barraNumero;
		} else {
			numeroRGIToBoleto = this.dsRgi;
		}

		return numeroRGIToBoleto;
	}

	public void setNumeroRGIToBoleto(String numeroRGIToBoleto) {
		this.numeroRGIToBoleto = numeroRGIToBoleto;
	}

	public String getNrGrupoFaturamento() {
		return nrGrupoFaturamento;
	}

	public void setNrGrupoFaturamento(String nrGrupoFaturamento) {
		this.nrGrupoFaturamento = nrGrupoFaturamento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public AgenciaTO getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaTO agencia) {
		this.agencia = agencia;
	}

	public boolean isRolEspecial() {
		return rolEspecial;
	}

	public void setRolEspecial(boolean rolEspecial) {
		this.rolEspecial = rolEspecial;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public String getDsDiretoria() {
		return dsDiretoria;
	}

	public void setDsDiretoria(String dsDiretoria) {
		this.dsDiretoria = dsDiretoria;
	}

	public String getCodigoVicePresidencia() {
		return codigoVicePresidencia;
	}

	public void setCodigoVicePresidencia(String codigoVicePresidencia) {
		this.codigoVicePresidencia = codigoVicePresidencia;
	}
	
}
