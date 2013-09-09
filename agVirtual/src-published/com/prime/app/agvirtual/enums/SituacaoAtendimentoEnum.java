package com.prime.app.agvirtual.enums;

/**
 * Possiveis situacoes do ATENDIMENTO
 * 
 * Tabela: agv_sta_atendimento
 */
public enum SituacaoAtendimentoEnum {
	CONCLUIDO(1L, "Concluído"), CANCELADO(2L, "Cancelado"), TIMEOUT(3L, "Time-out"), EMANDAMENTO(
			4L, "Em andamento");

	private SituacaoAtendimentoEnum(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	private Long codigo;
	private String descricao;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
