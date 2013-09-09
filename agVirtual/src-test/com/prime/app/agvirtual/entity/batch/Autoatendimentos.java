package com.prime.app.agvirtual.entity.batch;

import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtField;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtFieldFile;

@FixedLenghtFieldFile(nameFile = "Autoatendimentos.txt")
public class Autoatendimentos {
	@FixedLenghtField(position = 1, lenght = 8, paddingChar = 0)
	private String IdAtendimento;
	@FixedLenghtField(position = 1, lenght = 8, paddingChar = 0)
	private String IdAutoatendimento;
	@FixedLenghtField(position = 1, lenght = 14, paddingChar = 0)
	private String DataIniAutoatendimento;
	@FixedLenghtField(position = 1, lenght = 14, paddingChar = 0)
	private String DataFimAutoatendimento;
	@FixedLenghtField(position = 1, lenght = 1, paddingChar = 0)
	private String StatusAutoatendimentoAGV;
	@FixedLenghtField(position = 1, lenght = 50)
	private String MotivoInsucesso;
	@FixedLenghtField(position = 1, lenght = 3, paddingChar = 0)
	private String IdUnidadeNegocio;
	@FixedLenghtField(position = 1, lenght = 30)
	private String DsUnidadeNegocio;
	@FixedLenghtField(position = 1, lenght = 3, paddingChar = 0)
	private String IDAtendimentoComercial;
	@FixedLenghtField(position = 1, lenght = 20)
	private String DsAtendimentocomercial;
	@FixedLenghtField(position = 1, lenght = 5, paddingChar = 0)
	private String IdMunicipio;
	@FixedLenghtField(position = 1, lenght = 30)
	private String DsMunicipio;

	public String getIdAtendimento() {
		return IdAtendimento;
	}

	public void setIdAtendimento(String idAtendimento) {
		IdAtendimento = idAtendimento;
	}

	public String getIdAutoatendimento() {
		return IdAutoatendimento;
	}

	public void setIdAutoatendimento(String idAutoatendimento) {
		IdAutoatendimento = idAutoatendimento;
	}

	public String getDataIniAutoatendimento() {
		return DataIniAutoatendimento;
	}

	public void setDataIniAutoatendimento(String dataIniAutoatendimento) {
		DataIniAutoatendimento = dataIniAutoatendimento;
	}

	public String getDataFimAutoatendimento() {
		return DataFimAutoatendimento;
	}

	public void setDataFimAutoatendimento(String dataFimAutoatendimento) {
		DataFimAutoatendimento = dataFimAutoatendimento;
	}

	public String getStatusAutoatendimentoAGV() {
		return StatusAutoatendimentoAGV;
	}

	public void setStatusAutoatendimentoAGV(String statusAutoatendimentoAGV) {
		StatusAutoatendimentoAGV = statusAutoatendimentoAGV;
	}

	public String getMotivoInsucesso() {
		return MotivoInsucesso;
	}

	public void setMotivoInsucesso(String motivoInsucesso) {
		MotivoInsucesso = motivoInsucesso;
	}

	public String getIdUnidadeNegocio() {
		return IdUnidadeNegocio;
	}

	public void setIdUnidadeNegocio(String idUnidadeNegocio) {
		IdUnidadeNegocio = idUnidadeNegocio;
	}

	public String getDsUnidadeNegocio() {
		return DsUnidadeNegocio;
	}

	public void setDsUnidadeNegocio(String dsUnidadeNegocio) {
		DsUnidadeNegocio = dsUnidadeNegocio;
	}

	public String getIDAtendimentoComercial() {
		return IDAtendimentoComercial;
	}

	public void setIDAtendimentoComercial(String iDAtendimentoComercial) {
		IDAtendimentoComercial = iDAtendimentoComercial;
	}

	public String getDsAtendimentocomercial() {
		return DsAtendimentocomercial;
	}

	public void setDsAtendimentocomercial(String dsAtendimentocomercial) {
		DsAtendimentocomercial = dsAtendimentocomercial;
	}

	public String getIdMunicipio() {
		return IdMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		IdMunicipio = idMunicipio;
	}

	public String getDsMunicipio() {
		return DsMunicipio;
	}

	public void setDsMunicipio(String dsMunicipio) {
		DsMunicipio = dsMunicipio;
	}

}
