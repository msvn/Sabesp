package com.prime.app.agvirtual.service;

import java.util.ArrayList;
import java.util.List;

import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.Conta;
import com.prime.app.agvirtual.entity.Endereco;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.to.Acatamento2ViaTO;
import com.prime.app.agvirtual.to.DadosImoveisTO;

public interface ContaService {

	public DadosImoveisTO identificarImovel(Imovel imovel, String cdItemMenu, String jsession)  throws Exception ;
	
	public List<Imovel> localizarImovel(Imovel imovel);

	public ArrayList pesquisaContasEmAberto(DadosImoveisTO dadosImoveisTO);
	
	public List<Endereco> localizarEnderecos(Endereco enderecoPesquisa);

	public void solicitaAcatamento2ViaCorreio(Acatamento2ViaTO acatamentoTemp) throws Exception;

	public double pesquisaTarifaSegundaViaConta(DadosImoveisTO dadosImoveisTO);
	
	public List<Endereco> localizarNumerosEndereco(Endereco endereco);

	public Cliente pesquisaHistoricoContas(DadosImoveisTO dadosImoveisTO) throws Exception;
	
	public void gerarCodigoBarras(Conta conta);
	
	public Conta complementarDadosBoleto(Conta contaPesquisa, DadosImoveisTO dadosImoveisTO) throws Exception;
	
	public String[] carregarCodigoBarras(Conta conta);

	public Conta complementarDadosBoletoRolEspecial(Conta conta,DadosImoveisTO dadosImoveisTO) throws Exception;
	
	
}
