package com.prime.app.agvirtual.entity.batch;

import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtField;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtFieldFile;

@FixedLenghtFieldFile(nameFile = "PesquisaSatisfacao")
public class PesquisaSatisfacao {
	@FixedLenghtField(position = 1, lenght = 8, paddingChar = 0)
	private String idAtendimento;
	@FixedLenghtField(position = 1, lenght = 8, paddingChar = 0)
	private String idPesquisa;
	@FixedLenghtField(position = 1, lenght = 8, paddingChar = 0)
	private String idAutoatendimento;
	@FixedLenghtField(position = 1, lenght = 8, paddingChar = 0)
	private String idPergunta;
	@FixedLenghtField(position = 1, lenght = 8, paddingChar = 0)
	private String idResposta;
	@FixedLenghtField(position = 1, lenght = 300)
	private String ObsResposta;
	@FixedLenghtField(position = 1, lenght = 14, paddingChar = 0)
	private String dataPesquisa;
	@FixedLenghtField(position = 1, lenght = 3, paddingChar = 0)
	private String idUnidadeNegocio;
	@FixedLenghtField(position = 1, lenght = 30)
	private String dsUnidadeNegocio;

	public String getIdAtendimento() {
		return idAtendimento;
	}

	public void setIdAtendimento(String idAtendimento) {
		this.idAtendimento = idAtendimento;
	}

	public String getIdPesquisa() {
		return idPesquisa;
	}

	public void setIdPesquisa(String idPesquisa) {
		this.idPesquisa = idPesquisa;
	}

	public String getIdAutoatendimento() {
		return idAutoatendimento;
	}

	public void setIdAutoatendimento(String idAutoatendimento) {
		this.idAutoatendimento = idAutoatendimento;
	}

	public String getIdPergunta() {
		return idPergunta;
	}

	public void setIdPergunta(String idPergunta) {
		this.idPergunta = idPergunta;
	}

	public String getIdResposta() {
		return idResposta;
	}

	public void setIdResposta(String idResposta) {
		this.idResposta = idResposta;
	}

	public String getObsResposta() {
		return ObsResposta;
	}

	public void setObsResposta(String obsResposta) {
		ObsResposta = obsResposta;
	}

	public String getDataPesquisa() {
		return dataPesquisa;
	}

	public void setDataPesquisa(String dataPesquisa) {
		this.dataPesquisa = dataPesquisa;
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

}
