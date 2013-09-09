package com.prime.app.agvirtual.web.jsf.imovel;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.app.agvirtual.web.jsf.util.SessionUtil;
import com.prime.infra.web.jsf.BasicBBean;

/**
 * Bean de acesso a objetos utilizados por varias telas de uma mesma sessao
 * 
 * No momento objetos sendo colocados na sessao
 *
 */
@Component
@Scope(value="session")
public class ImovelIdentificadoBBean extends BasicBBean{
	private DadosImoveisTO dadosImovelIdentificado;
	private Cliente cliente;
	private String teste = "xxxxx";
	
	private int acessado = 0;
	
	public DadosImoveisTO getDadosImovelIdentificado() {
		acessado++;
		return SessionUtil.obterAtributo(SessionUtil.DADOSIMOVELSESSION, getHttpSession(Boolean.FALSE), new DadosImoveisTO());
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setDadosImovelIdentificado(DadosImoveisTO dadosImovelIdentificado) {
		this.dadosImovelIdentificado = dadosImovelIdentificado;
	}

	public String getTeste() {
		return teste;
	}

	public void setTeste(String teste) {
		this.teste = teste;
	}

}
