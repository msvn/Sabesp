package com.prime.app.agvirtual.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "SituacaoAtendimento.findAll", query = "select o from SituacaoAtendimento o") })
@Table(name = "AGV_STA_ATENDIMENTO", schema = Schema.DB_OWNER)
public class SituacaoAtendimento {
	@Id
	@Column(name = "CD_STA_ATENDIMENTO", nullable = false)
	@SequenceGenerator(name = "", sequenceName = "", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "")
	private Long codigo;
	
	@Column(name = "DS_STA_ATENDIMENTO")
	private String descricao;
	
//	@OneToMany(mappedBy = "situacaoAtendimento", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
//	private List<AutoAtendimentoAcessado> listaServicoAutoAtendimento;

	public SituacaoAtendimento(){
		
	}
	
	public SituacaoAtendimento(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

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

//	public List<AutoAtendimentoAcessado> getListaServicoAutoAtendimento() {
//		return listaServicoAutoAtendimento;
//	}
//
//	public void setListaServicoAutoAtendimento(
//			List<AutoAtendimentoAcessado> listaServicoAutoAtendimento) {
//		this.listaServicoAutoAtendimento = listaServicoAutoAtendimento;
//	}
	
}
