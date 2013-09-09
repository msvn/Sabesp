package com.prime.app.agvirtual.dao;

import java.util.ArrayList;
import java.util.List;

import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.Conta;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.to.DadosImoveisTO;

public interface ContaDao {
	
	List<Conta> findByImovel(Imovel imovel);

	ArrayList pesquisaContasEmAberto(DadosImoveisTO dadosImoveisTO);
		
	Conta carregarDadosBoletoRolComum(Conta conta);
	
	Conta carregarDadosBoletoRolEspecial(Conta contaPesquisa,DadosImoveisTO dadosImoveis);

	/**
	 * Pesquisa se cliente e rol especial e seta atributo respectivo atributo
	 * 
	 * @param codigoMunicipio
	 * @param codigoCliente
	 * @return
	 */
	public Cliente carregarDadosCondicaoFaturamento(Cliente cliente);	
	
//	/**
//	 * pesquisa Rgis Por Cod Cliente / Cod Municipio
//	 * 
//	 * @param cdMunicipio
//	 * @param cdCliente
//	 * @return
//	 */
//	public Cliente carregarTodosImoveis(Cliente cliente);	
	
//  not used any more !!! replaced by flag in cliente object
	
	
	/**
	 * Pesquisa historico de contas
	 * 
	 * @param dadosImoveisTO
	 * @return
	 */
	public Cliente carregaHistoricoContasTodosImoveis(Cliente cliente, DadosImoveisTO dadosImoveisTO);

	String pesquisaDadosRgiFilhosConta(String rgi,String dataReferenciaAnoJuliano);

	void carregaDadosConsumoRgisFilhoContaRolEspecial(ArrayList filho,String rgi, String amjRefer);

	Double pesquisaConsumoHidroComposto(Conta contaPesquisa,DadosImoveisTO dadosImoveisTO);

	String pesquisaCdHidroComposto(String rgi, String dataReferenciaAnoJuliano);

	void pesquisaSuperIntendenciaRgi(DadosImoveisTO dadosImoveisTO); 
}
