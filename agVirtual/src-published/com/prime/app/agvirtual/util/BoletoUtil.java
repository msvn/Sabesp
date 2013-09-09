package com.prime.app.agvirtual.util;

public class BoletoUtil {
	
	private static BoletoUtil instance = null;
	
	private BoletoUtil(){}
	
	public static BoletoUtil getInstance(){
		
		if( instance == null )
			instance  = new BoletoUtil();
		
		return instance;
	}
	
	public final String dacMod11(String field) {
		int sum = 0;
		int multiplier = 2;
		int res = 0;

		for (int i = field.length(); i > 0; i--) {
			int digit = Character.getNumericValue(field.charAt(i - 1));
			sum += digit * (multiplier++);
			if (multiplier > 9)
				multiplier = 2;
		}
		res = 11 - sum % 11;
		if ((res == 0) || (res == 10) || (res == 11))
			res = 1;
		return Integer.toString(res);
	}

	public final String dacMod10(String field) {
		int sum = 0;
		boolean two = true;

		for (int i = field.length(); i > 0; i--) {
			int digit = Character.getNumericValue(field.charAt(i - 1));
			if (two) {
				int tmp = digit * 2;
				if (tmp > 9)
					tmp = (tmp / 10) + (tmp % 10);
				sum += tmp;
				two = false;
			} else {
				sum += digit;
				two = true;
			}
		}
		int res = 10 - sum % 10;
		if (res == 10)
			res = 0;
		return Integer.toString(res);
	}
	
	/**
	 *  Função convertida do legado (oContas.cls, linha 1818)
	 *  Registra pedido de impressão via internet e
	 *  Retorna dados para impressão de barras
	 * @param sValor Sem os digitos verificadores
	 * @return
	 */
	public final String imprimeBarra(String sValor) {
		String retorno = "";
		String texto = "";
		String fino ="1";
		String largo ="3";
		String barCodes[] = new String[100];
		barCodes[0] = "00110";
		barCodes[1] = "10001";
		barCodes[2] = "01001";
		barCodes[3] = "11000";
		barCodes[4] = "00101";
		barCodes[5] = "10100";
		barCodes[6] = "01100";
		barCodes[7] = "00011";
		barCodes[8] = "10010";
		barCodes[9] = "01010";
		for (int f1 = 9; f1 >= 0; f1--) {
			for (int f2 = 9; f2 >= 0; f2--) {
				int f = f1 * 10 + f2;
				texto = "";
				for (int i = 0; i < 5; i++) {
					texto = texto.concat(""+barCodes[f1].charAt(i)).concat(""+barCodes[f2].charAt(i));
				}
				barCodes[f]=texto;
			}
		}
		
		retorno = retorno.concat("1111");
		
		texto = sValor;
		if(sValor.length()%2!=0){
			texto = "0".concat(texto);
		}		
//		Draw dos dados
		while (texto.length()>0) {
			int i = Integer.parseInt(texto.substring(0, 2));
			texto = texto.substring(2, texto.length());
				    
			String f = barCodes[i];
			for (i = 0; i < 10; i+=2) {
				
				String aux = "";
				if ((""+f.charAt(i)).equals("0")) {
					retorno = retorno.concat(fino);
				} else {
					retorno = retorno.concat(largo);
				}				
				
				if ((""+f.charAt(i+1)).equals("0")) {
					retorno = retorno.concat(fino);
				} else {
					retorno = retorno.concat(largo);
				}
				retorno = retorno.concat(aux);
			}
		}
		// Draw guarda final
		return retorno.concat(largo).concat(fino).concat("1");
	}
	
	public static void main(String[] args) {
		System.out.println(
		BoletoUtil.getInstance().imprimeBarra("82680000000514200971353200000031102113409052")
		);
	}
}
