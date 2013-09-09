package com.prime.app.test.util;

import java.util.Date;

import com.prime.app.agvirtual.entity.Conta;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.service.impl.ContaServiceImpl;

public class TestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		gerarCodigoBarras();

	}
	
	static void gerarCodigoBarras(){
		
		ContaServiceImpl contaService = new ContaServiceImpl();
		
		Conta conta = new Conta();
		Imovel imovel  = new Imovel();
		imovel.setDsRgi("00000031");
		
		conta.setTpConta("1");
		conta.setDataReferenciaAnoJuliano("366");
		conta.setImovel( imovel );
		
		conta.setNrSequenciaConta2("1");
		double vl = 40.92;
		conta.setVlTotal(vl);
		
		/*System.out.println( contaService.carregarOCR( conta )[2] );*/
		
		conta.setIspec("CFRAB");
		conta.setDataReferenciaAnoJuliano("2628");
		Date data = new Date();
		data.setYear(04);
		data.setDate(02);
		
		conta.setDtReferencia(data);
		
		System.out.println( contaService.carregarCodigoBarras(conta) );
	}

}
