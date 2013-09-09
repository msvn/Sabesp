package com.prime.app.agvirtual.entity;

import java.io.Serializable;

/**
 * Interface para todos os Models tratados por esta implementação base.
 * <br>
 * Os que implementarem esta interface devem implementar uma conversão deste objeto Model para um objeto TO.
 * <p>
 * <b> Como utilizar: </b>
 * <br>
 * Defina uma classe implementando esta interface. Acrescente os atributos necessários e implemente
 * a conversao deste objeto Model para sua representação como um Vo.
 * <br> 
 * O exemplo abaixo define um Model para Cliente.
 * Com a declaração abaixo, o tipo ClienteModel fica "amarrado" ao tipo ClienteVo.
 * Esta amarração afeta o método toVo() que passa a retornar um ClienteVo e permite
 * que este Model possa ser utilizado pela implementação base.
 * <p>
 * Ex: 
 * <br>
 */
public interface BaseEntity<T> extends Serializable  {

	/**
	 * Conversão deste objeto Model para o objeto TO relacionado.
	 * 
	 * @return 	T Objeto VO representado por este Model.
	 */
	public T parseTO();
	
}
