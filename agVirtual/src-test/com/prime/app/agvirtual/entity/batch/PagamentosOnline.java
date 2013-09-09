package com.prime.app.agvirtual.entity.batch;

import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtField;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtFieldFile;

@FixedLenghtFieldFile(nameFile="PagamentosOnline.txt")
public class PagamentosOnline {
	@FixedLenghtField(position=1, lenght=8, paddingChar=0)	
	private String idAtendimento;
	@FixedLenghtField(position=1, lenght=14, paddingChar=0)	
	private String DataIniServico;
	@FixedLenghtField(position=1, lenght=14, paddingChar=0)	
	private String DataFimServico;
	@FixedLenghtField(position=1, lenght=4, paddingChar=0)	
	private String idBanco;

	public String getIdAtendimento() {
		return idAtendimento;
	}

	public void setIdAtendimento(String idAtendimento) {
		this.idAtendimento = idAtendimento;
	}

	public String getDataIniServico() {
		return DataIniServico;
	}

	public void setDataIniServico(String dataIniServico) {
		DataIniServico = dataIniServico;
	}

	public String getDataFimServico() {
		return DataFimServico;
	}

	public void setDataFimServico(String dataFimServico) {
		DataFimServico = dataFimServico;
	}

	public String getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(String idBanco) {
		this.idBanco = idBanco;
	}

}
