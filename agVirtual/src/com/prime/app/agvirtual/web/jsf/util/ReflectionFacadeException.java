package com.prime.app.agvirtual.web.jsf.util;

/**
 * @author felipepm
 */
public class ReflectionFacadeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ReflectionFacadeException(final Exception e){
		super(e.getMessage());
	}
	public ReflectionFacadeException(final String e){
		super(e);
	}
}
