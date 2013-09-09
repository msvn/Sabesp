package com.prime.app.agvirtual.util;

public class ContaUtil {
	
	private static ContaUtil instance = null;
	
	private ContaUtil(){}
	
	static ContaUtil getInstance(){
		if( instance == null )
			instance = new ContaUtil();
		
		return instance;
	}
	
	public static String formatarNumeroRGI(String nrRGI){
		
		if( nrRGI.length() < 10 ) {
			nrRGI += "0" + nrRGI;
		}
		
		return nrRGI;
	}
	
	public static String tratarCampoCodificacaoSabesp(String campo, int tamanhoCampoFinal){
		
		int qtdNumerosDoCampo = campo.length();
		
		String campoTradado = "0";
		
		
		
		if( qtdNumerosDoCampo < tamanhoCampoFinal  ){
			
			for(int i=0; i < (tamanhoCampoFinal - qtdNumerosDoCampo) ; i++) {
				campoTradado += campo;
			}
		}else {
			campoTradado = campo;
		}
		
		return campoTradado;
		
	}

}
