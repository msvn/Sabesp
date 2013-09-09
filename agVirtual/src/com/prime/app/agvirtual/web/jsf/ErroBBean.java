package com.prime.app.agvirtual.web.jsf;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.BasicBBean;

@Component
@Scope(value = "request")
public class ErroBBean extends BasicBBean {
	private static final long serialVersionUID = 5567255712316614943L;

	private String dsErro;
	private Exception exception;

	public ErroBBean() {
	}

	public String getDsErro() {
		return dsErro;
	}

	public void setDsErro(String dsErro) {
		this.dsErro = dsErro;
	}

	public boolean isShowError() {
		if (WrapperUtils.isNotNull(exception)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}
