package com.prime.app.agvirtual.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TestFilter {
    private String nameVal;
    
    public String getNameVal() {
        return nameVal;
    }
    
    public void setNameVal(String nameVal) {
        this.nameVal = nameVal;
    }
    
    @Override
    public String toString() {
        return super.toString() + "TestFilter: nameVal="+getNameVal();
    }
}
