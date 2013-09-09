package com.prime.app.agvirtual.to;

import java.io.Serializable;

import com.prime.infra.util.WrapperUtils;

/**
 * 
 * @author gustavons
 *
 */
public class MunicipioTO implements Serializable {
	
	private static final long serialVersionUID = 8267406289776880835L;
	
	private String nome;
	
	private Long codUf;
	
	private Long idMun;
	
	public MunicipioTO() {
		
	}

	/**
	 * 
	 * @param string
	 * @param i
	 */
	public MunicipioTO(String string, int i) {
		this.nome = string;
		this.codUf = WrapperUtils.toLong(i);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCodUf() {
		return codUf;
	}

	public void setCodUf(Long codUf) {
		this.codUf = codUf;
	}

	public Long getIdMun() {
		return idMun;
	}

	public void setIdMun(Long idMun) {
		this.idMun = idMun;
	}
	
}
