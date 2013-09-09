package com.prime.app.agvirtual.webservice;

import java.util.List;

import com.prime.infra.webservice.ResultList;

public class RetornoDTO extends ResultList {
    
    private List<NameDTO> names;

    public RetornoDTO() {
    }

    public List<NameDTO> getNames() {
        return names;
    }
    
    public void setNames(List<NameDTO> names) {
        this.names = names;
    }

}