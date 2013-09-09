package com.prime.app.agvirtual.webservice;

import java.util.List;

public class Retorno {
    
    private String valor;
    private List<Retorno> filhos;
            

    public Retorno() {
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public List<Retorno> getFilhos() {
        return filhos;
    }

    public void setFilhos(List<Retorno> filhos) {
        this.filhos = filhos;
    }


}