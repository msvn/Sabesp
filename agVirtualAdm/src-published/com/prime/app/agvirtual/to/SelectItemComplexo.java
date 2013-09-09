package com.prime.app.agvirtual.to;

import java.io.Serializable;

public class SelectItemComplexo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String value;
	
	private String label;
	
	private Object obj1;
	
	private Object obj2;

	public SelectItemComplexo(String value, String dsItemMenu,
			Object docTO, Object object) {
		this.value = value;
		this.label = dsItemMenu;
		this.obj1 = docTO;
		this.obj2 = object;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getObj1() {
		return obj1;
	}

	public void setObj1(Object obj1) {
		this.obj1 = obj1;
	}

	public Object getObj2() {
		return obj2;
	}

	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}
	
}
