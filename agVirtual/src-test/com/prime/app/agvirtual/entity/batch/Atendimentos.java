package com.prime.app.agvirtual.entity.batch;

import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtField;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtFieldFile;

@FixedLenghtFieldFile(nameFile = "Atendimentos.txt")
public class Atendimentos {
	@FixedLenghtField(position=1, lenght=8, paddingChar=0)	
	private String IdAtendimento;
	@FixedLenghtField(position=9, lenght=14, paddingChar=0, paddingAlign=1)	
	private String DataAbertura;
	@FixedLenghtField(position=23, lenght=14, paddingChar=0, paddingAlign=1)
	private String DataEncerramento;
	@FixedLenghtField(position=37, lenght=4, paddingChar=0)
	private String QtdRGIs;
	
	public String getIdAtendimento() {
		return IdAtendimento;
	}
	public void setIdAtendimento(String idAtendimento) {
		IdAtendimento = idAtendimento;
	}
	public String getDataAbertura() {
		return DataAbertura;
	}
	public void setDataAbertura(String dataAbertura) {
		DataAbertura = dataAbertura;
	}
	public String getDataEncerramento() {
		return DataEncerramento;
	}
	public void setDataEncerramento(String dataEncerramento) {
		DataEncerramento = dataEncerramento;
	}
	public String getQtdRGIs() {
		return QtdRGIs;
	}
	public void setQtdRGIs(String qtdRGIs) {
		QtdRGIs = qtdRGIs;
	}

	
}
