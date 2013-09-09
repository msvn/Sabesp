package com.prime.app.agvirtual.dto;

import java.util.List;

import com.prime.app.agvirtual.webservice.Ret;
import com.prime.infra.webservice.ResultList;

public class TestResultList extends ResultList {

    private List<Ret> resultados;
    
    public List<Ret> getResultados() {
        return resultados;
    }
    public void setResultados(List<Ret> resultados) {
        this.resultados = resultados;
    }
    @Override
    public String toString() {
        return super.toString() + "TestResultList: resultados:"+getResultados();
    }
    
}
