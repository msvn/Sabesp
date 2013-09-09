package com.prime.app.agvirtual.to;

import java.util.HashSet;
import java.util.List;

import com.prime.app.agvirtual.entity.AgvTabBloqueio;
import com.prime.app.agvirtual.entity.AgvTabItemMenuAcesso;
import com.prime.infra.util.WrapperUtils;

public class BloqueioTO implements BasicTO<AgvTabBloqueio>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2596696742704800815L;
	
	private Long cdBloqueio;
	private String dataBloqueio;
	private String dataCancelamento;
	private String descricao;
	private String nmUserCriacao;
	private String nmUserExclusao;
	private boolean excluido;
	private String mensagem;
	private String nmBloqueio;
	
	private boolean todosMunicipios;
	private boolean todoasFuncionalidades;
	
	private List<AgvTabItemMenuAcesso> servicos;
	private HashSet<MunicipioTO> ufs;
	
	public Long getCdBloqueio() {
		return cdBloqueio;
	}
	public String getDataBloqueio() {
		return dataBloqueio;
	}
	public String getDescricao() {
		return descricao;
	}
	public String getMensagem() {
		return mensagem;
	}
	public String getNmBloqueio() {
		return nmBloqueio;
	}
	public List<AgvTabItemMenuAcesso> getServicos() {
		return servicos;
	}
	public HashSet<MunicipioTO> getUfs() {
		return ufs;
	}
	public boolean isExcluido() {
		return excluido;
	}
	public void setCdBloqueio(Long cdBloqueio) {
		this.cdBloqueio = cdBloqueio;
	}
	public void setDataBloqueio(String dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public void setNmBloqueio(String nmBloqueio) {
		this.nmBloqueio = nmBloqueio;
	}
	public void setServicos(List<AgvTabItemMenuAcesso> listaServicosAdicionados) {
		this.servicos = listaServicosAdicionados;
	}
	public void setUfs(HashSet<MunicipioTO> listaUfsAdicionados) {
		this.ufs = listaUfsAdicionados;
	}
	public AgvTabBloqueio toEntity() {
		
		AgvTabBloqueio tabBloqueio =  new AgvTabBloqueio();
		tabBloqueio.setCdBloqueio(cdBloqueio);
		tabBloqueio.setNmBloqueio(descricao);
		tabBloqueio.setDsMensagem(mensagem);
		tabBloqueio.setDtCancelamento(null);
		tabBloqueio.setExcluido(excluido);
		tabBloqueio.setNmUserCriacao(nmUserCriacao);
		tabBloqueio.setNmUserExclusao(nmUserExclusao);		
		tabBloqueio.setDtInclusao(WrapperUtils.toDate(dataBloqueio));
		
		return tabBloqueio;
	}
	public String getDataCancelamento() {
		return dataCancelamento;
	}
	public void setDataCancelamento(String dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}
	public String getNmUserCriacao() {
		return nmUserCriacao;
	}
	public void setNmUserCriacao(String nmUserCriacao) {
		this.nmUserCriacao = nmUserCriacao;
	}
	public String getNmUserExclusao() {
		return nmUserExclusao;
	}
	public void setNmUserExclusao(String nmUserExclusao) {
		this.nmUserExclusao = nmUserExclusao;
	}
	public boolean isTodosMunicipios() {
		return todosMunicipios;
	}
	public void setTodosMunicipios(boolean todosMunicipios) {
		this.todosMunicipios = todosMunicipios;
	}
	public boolean isTodoasFuncionalidades() {
		return todoasFuncionalidades;
	}
	public void setTodoasFuncionalidades(boolean todoasFuncionalidades) {
		this.todoasFuncionalidades = todoasFuncionalidades;
	}
}
