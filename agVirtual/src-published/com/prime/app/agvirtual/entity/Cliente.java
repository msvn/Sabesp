package com.prime.app.agvirtual.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.prime.app.agvirtual.enums.TipoRelacaoClienteImovel;
import com.prime.infra.BusinessException;
import static com.prime.infra.util.WrapperUtils.*;

public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3332552588436947025L;


	private String cdMunicipio;

	private String cdCliente;

	private String cdContFat;

	private String nmCLiente;

	private String dsEmail;

	private String numeroInscricaoEstadual;

	private String cpf;

	private String cnpj;
	
	private String ddd1;
	
	private int ddd2;
	
	private String telefone1;
	
	private String telefone2;
	
	private String codCategoria;
	
	private String cdRelacaoImovel;
	
	private TipoRelacaoClienteImovel tipoRelacionamento;

	private List<Imovel> imoveis;

	private String dsRgiImovelMaster;
	
	/**
	 * Lista de Rgis filhos , utilizados na busca de contas em aberto , 2 via de conta rol especial
	 */
	private List<String> listaRgis;
	
	public Cliente() {
		imoveis = new ArrayList<Imovel>(2);
	}

	public Cliente(String cdCliente, String cdMunicipio) {
		super();
		this.cdCliente = cdCliente;
		this.cdMunicipio = cdMunicipio;
	}
	
	
	public String getDdd1() {
		return ddd1;
	}

	public void setDdd1(String ddd1) {
		this.ddd1 = ddd1;
	}

	public int getDdd2() {
		return ddd2;
	}

	public void setDdd2(int ddd2) {
		this.ddd2 = ddd2;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(String codCategoria) {
		this.codCategoria = codCategoria;
	}

	public String getCdRelacaoImovel() {
		return cdRelacaoImovel;
	}

	public void setCdRelacaoImovel(String cdRelacaoImovel) {
		this.cdRelacaoImovel = cdRelacaoImovel;
	}

	public TipoRelacaoClienteImovel getTipoRelacionamento() {
		return tipoRelacionamento;
	}

	public void setTipoRelacionamento(TipoRelacaoClienteImovel tipoRelacionamento) {
		this.tipoRelacionamento = tipoRelacionamento;
	}

	public String getNumeroInscricaoEstadual() {
		return numeroInscricaoEstadual;
	}

	public void setNumeroInscricaoEstadual(String numeroInscricaoEstadual) {
		this.numeroInscricaoEstadual = numeroInscricaoEstadual;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCdMunicipio() {
		return cdMunicipio;
	}

	public void setCdMunicipio(String cdMunicipio) {
		this.cdMunicipio = cdMunicipio;
	}

	public String getCdCliente() {
		return cdCliente;
	}

	public void setCdCliente(String cdCliente) {
		this.cdCliente = cdCliente;
	}

	public String getCdContFat() {
		return cdContFat;
	}

	public void setCdContFat(String cdContFat) {						
		this.cdContFat = cdContFat;
	}

	public String getNmCLiente() {
		return nmCLiente;
	}

	public void setNmCLiente(String nmCLiente) {
		this.nmCLiente = nmCLiente;
	}

	public String getDsEmail() {
		if(dsEmail==null) return null;
		return dsEmail.trim();
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	public boolean isRolEspecial() {
		if((cdContFat!=null) && !("".equals(cdContFat))){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public List<Imovel> getImoveis() {
		return imoveis;
	}

	public void setImoveis(List<Imovel> imoveis) {
		this.imoveis = imoveis;
	}
		
	public String getDsRgiImovelMaster() {
		return dsRgiImovelMaster;
	}

	public void setDsRgiImovelMaster(String dsRgiImovelMaster) {
		this.dsRgiImovelMaster = dsRgiImovelMaster;
	}

	public void addImovel(Imovel imovel){
		imoveis.add(imovel);
	}
	
	/**
	 * Retorna imovel rgi master
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public Imovel getImovelRgiMaster(){
		Imovel imovel = null;

		for(Imovel imovelTmp : imoveis){
			if(imovelTmp.getDsRgi().equals(dsRgiImovelMaster)){
				imovel = imovelTmp;
				break;
			}
		}
		if(imovel==null) {
			return imoveis.get(0);
		}
		
		
//		if(imovel==null) throw new BusinessException("Este cliente nao tem imovel master!");
		
		return imovel;
	}	
	
	/**
	 * Retorna lista de rgis pertencentes a este cliente.
	 * 
	 * @return
	 */
	public List<String> getListaDsRgiImoveis(){
		List<String> listaDsRgis = new ArrayList<String>();
		
		for(Imovel imovel : imoveis){
			listaDsRgis.add(imovel.getDsRgi());
		}
		
		return listaDsRgis;
	}

	public List<String> getListaRgis() {
		return listaRgis;
	}

	public void setListaRgis(List<String> listaRgis) {
		this.listaRgis = listaRgis;
	}

	public String getDocumentoPreechido(){
		if(isNotNull(this.cpf) && (!"".equals(this.cpf))){
			return this.cpf;
		}else{
			return this.cnpj;
		}
	}
}
