package com.prime.app.agvirtual.to;

import java.io.Serializable;

/**
 * @author felipepm
 */
public class CategoriaTO implements Serializable {
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -6313635810290002581L;
	
	private Long codigo;

	private String nome;
	
	public CategoriaTO() {
		
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
