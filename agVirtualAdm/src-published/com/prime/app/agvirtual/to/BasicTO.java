package com.prime.app.agvirtual.to;

import java.io.Serializable;

/**
 * 
 * @author gustavons
 * @nomeProjeto agVirtualAdm
 * @data 07/07/2010
 * @param <T>
 */
public interface BasicTO<T> extends Serializable {
	
	/**
	 * Conversão deste objeto VO para o objeto Model relacionado.
	 * 
	 * @return 	T Objeto Model representado por este VO.
	 */
	public T toEntity();

}
