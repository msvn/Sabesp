package com.prime.app.agvirtual.webservice;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Code {
    private Integer code;

    public Code() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
