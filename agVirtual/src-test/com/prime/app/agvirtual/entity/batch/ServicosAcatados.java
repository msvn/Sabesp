package com.prime.app.agvirtual.entity.batch;

import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtField;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtFieldFile;

@FixedLenghtFieldFile(nameFile="ServicosAcatados.txt")
public class ServicosAcatados {
	
	@FixedLenghtField(position=1, lenght=8, paddingChar=0)	
	private String idAtendimento;
	@FixedLenghtField(position=1, lenght=7)
	private String idServicoAcatado;
	@FixedLenghtField(position=1, lenght=14, paddingChar=0)
	private String dataServicoAcatado;
	@FixedLenghtField(position=1, lenght=30)
	private String statusServicoCSI;
	@FixedLenghtField(position=1, lenght=3, paddingChar=0)
	private String idUnidadeNegocio;
	@FixedLenghtField(position=1, lenght=20)
	private String dsUnidadeNegocio;
	@FixedLenghtField(position=1, lenght=3, paddingChar=0)
	private String idAtendimentoComercial;
	@FixedLenghtField(position=1, lenght=30)
	private String dsAtendimentoComercial;

	public String getIdAtendimento() {
		return idAtendimento;
	}

	public void setIdAtendimento(String idAtendimento) {
		this.idAtendimento = idAtendimento;
	}

	public String getIdServicoAcatado() {
		return idServicoAcatado;
	}

	public void setIdServicoAcatado(String idServicoAcatado) {
		this.idServicoAcatado = idServicoAcatado;
	}

	public String getDataServicoAcatado() {
		return dataServicoAcatado;
	}

	public void setDataServicoAcatado(String dataServicoAcatado) {
		this.dataServicoAcatado = dataServicoAcatado;
	}

	public String getStatusServicoCSI() {
		return statusServicoCSI;
	}

	public void setStatusServicoCSI(String statusServicoCSI) {
		this.statusServicoCSI = statusServicoCSI;
	}

	public String getIdUnidadeNegocio() {
		return idUnidadeNegocio;
	}

	public void setIdUnidadeNegocio(String idUnidadeNegocio) {
		this.idUnidadeNegocio = idUnidadeNegocio;
	}

	public String getDsUnidadeNegocio() {
		return dsUnidadeNegocio;
	}

	public void setDsUnidadeNegocio(String dsUnidadeNegocio) {
		this.dsUnidadeNegocio = dsUnidadeNegocio;
	}

	public String getIdAtendimentoComercial() {
		return idAtendimentoComercial;
	}

	public void setIdAtendimentoComercial(String idAtendimentoComercial) {
		this.idAtendimentoComercial = idAtendimentoComercial;
	}

	public String getDsAtendimentoComercial() {
		return dsAtendimentoComercial;
	}

	public void setDsAtendimentoComercial(String dsAtendimentoComercial) {
		this.dsAtendimentoComercial = dsAtendimentoComercial;
	}

}
