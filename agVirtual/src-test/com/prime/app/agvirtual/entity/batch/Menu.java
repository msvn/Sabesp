package com.prime.app.agvirtual.entity.batch;

import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtField;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtFieldFile;

@FixedLenghtFieldFile(nameFile="Menu.txt")
public class Menu {
	@FixedLenghtField(position=1, lenght=8, paddingChar=0)
	private String idAtendimento;
	@FixedLenghtField(position=9, lenght=8, paddingChar=0)
	private String idOpcaoMenu;
	@FixedLenghtField(position=17, lenght=14, paddingChar=0, paddingAlign=1)
	private String dataAcesso;

	public String getIdAtendimento() {
		return idAtendimento;
	}

	public void setIdAtendimento(String idAtendimento) {
		this.idAtendimento = idAtendimento;
	}

	public String getIdOpcaoMenu() {
		return idOpcaoMenu;
	}

	public void setIdOpcaoMenu(String idOpcaoMenu) {
		this.idOpcaoMenu = idOpcaoMenu;
	}

	public String getDataAcesso() {
		return dataAcesso;
	}

	public void setDataAcesso(String dataAcesso) {
		this.dataAcesso = dataAcesso;
	}

}
