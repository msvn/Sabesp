package com.prime.app.agvirtual.entity;

import java.io.Serializable;

/**
 * Interface para todos os Models tratados por esta implementa��o base.
 * <br>
 * Os que implementarem esta interface devem implementar uma convers�o deste objeto Model para um objeto TO.
 * <p>
 * <b> Como utilizar: </b>
 * <br>
 * Defina uma classe implementando esta interface. Acrescente os atributos necess�rios e implemente
 * a conversao deste objeto Model para sua representa��o como um Vo.
 * <br> 
 * O exemplo abaixo define um Model para Cliente.
 * Com a declara��o abaixo, o tipo ClienteModel fica "amarrado" ao tipo ClienteVo.
 * Esta amarra��o afeta o m�todo toVo() que passa a retornar um ClienteVo e permite
 * que este Model possa ser utilizado pela implementa��o base.
 * <p>
 * Ex: 
 * <br>
 */
public interface BaseEntity<T> extends Serializable  {

	/**
	 * Convers�o deste objeto Model para o objeto TO relacionado.
	 * 
	 * @return 	T Objeto VO representado por este Model.
	 */
	public T parseTO();
	
}
