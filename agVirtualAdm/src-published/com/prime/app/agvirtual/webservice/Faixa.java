package com.prime.app.agvirtual.webservice;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Faixa {

    private int ordem;
    private String titulo;

    public Faixa() {
    }

    public Faixa(int ordem, String titulo) {
        this.ordem = ordem;
        this.titulo = titulo;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
