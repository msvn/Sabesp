package com.prime.app.agvirtual.service.exception;

import com.prime.infra.BusinessException;

public class NotFoundBusinessException extends BusinessException{
	private static final long serialVersionUID = 1L;

	public NotFoundBusinessException(String message) {
		super(message);
	}
	
}
