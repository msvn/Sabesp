package com.prime.app.agvirtual.to;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author gustavons
 *
 */
public class CronogramaLeituraTO  implements Serializable {
	
	/**
	 * Exemplo 
	 * Mês referência  	Leitura Intermediária  	Leitura Mensal  	Vencimento
	 * Março / 2010 	05/12/2010 				05/12/2010 			05/12/2010
	 */
	
	private Date dataLeituraIntermediaria = null;
	
	private Date dataLeituraMensal = null;
	
	private Date mesReferencia = null;
	
	private Date dataVencimento = null;

	public Date getDataLeituraIntermediaria() {
		return dataLeituraIntermediaria;
	}

	public void setDataLeituraIntermediaria(Date dataLeituraIntermediaria) {
		this.dataLeituraIntermediaria = dataLeituraIntermediaria;
	}

	public Date getDataLeituraMensal() {
		return dataLeituraMensal;
	}

	public void setDataLeituraMensal(Date dataLeituraMensal) {
		this.dataLeituraMensal = dataLeituraMensal;
	}

	public Date getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(Date mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
}
