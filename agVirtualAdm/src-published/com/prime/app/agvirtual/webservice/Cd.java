package com.prime.app.agvirtual.webservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Cd {

    private Long codigo;
    private String nomeTitulo;
    private String nomeArtista;
    private Date anoLancamento;
    // private byte[] imagem;
    private String genero;
    private List<Faixa> faixas;
    private Integer nextIndex;
    private Integer maxResults;
    private BigDecimal valor;
    private String data;

    public Cd() {
    }

    public Cd(Long codigo, String nomeTitulo, String nomeArtista, Date anoLancamento,
    // byte[] imagem,
        String genero) {
        this.codigo = codigo;
        this.nomeTitulo = nomeTitulo;
        this.nomeArtista = nomeArtista;
        this.anoLancamento = anoLancamento;
        // this.imagem = imagem;
        this.genero = genero;
        faixas = new ArrayList<Faixa>();
    }

    public void add(Faixa pp) {
        faixas.add(pp);
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNomeTitulo() {
        return nomeTitulo;
    }

    public void setNomeTitulo(String nomeTitulo) {
        this.nomeTitulo = nomeTitulo;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public void setNomeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
    }

    public Date getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Date anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    // public byte[] getImagem() {
    // return imagem;
    // }

    // public void setImagem(byte[] imagem) {
    // this.imagem = imagem;
    // }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<Faixa> getFaixas() {
        return faixas;
    }

    public void setFaixas(List<Faixa> faixas) {
        this.faixas = faixas;
    }

    public Integer getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(Integer nextIndex) {
        this.nextIndex = nextIndex;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString() {
        return new ToStringBuilder(this).append("codigo", codigo).append("nomeTitulo", nomeTitulo).append(
            "nomeArtista", nomeArtista).append("anoLancamento", anoLancamento).append("genero", genero).append(
            "nextIndex", nextIndex).append("maxResults", maxResults).append("valor", valor).append("data", data)
            .append("faixas", faixas).toString();
    }

}
