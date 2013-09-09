package com.prime.app.agvirtual.to;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.prime.app.agvirtual.entity.AgvTabSecaoPagIni;
import com.prime.app.agvirtual.enums.TipoSecao;

public class SecaoPaginaIncialTO implements BasicTO<AgvTabSecaoPagIni> {

    /**
	 * Serial.
	 */
	private static final long serialVersionUID = -1233000787103911204L;
	
	/**
	 * Identificador sequ�ncial das se��es da p�gina inicial.
	 */
	private Long cdSecao;
	
	/**
	 * Tipo da Sess�o:
	 * 1 - Sess�o Principal (Sua Conta);
	 * 2 - Se��o Secund�ria 1 (Consertos);
	 * 3 - Se��o Secund�ria 2 (Liga��es de �gua e esgoto);
	 * 4 - Barra horizontal (Emerg�ncias);
	 * 5 - Se��o Inferior direita (Dicas).
	 */
	private TipoSecao tpSecao;
	
	/**
	 * T�tulo da se��o.
	 */
	private String ttSecao;
	
	/**
	 * Quantidade de Itens que a se��o possui.
	 */
	private String qtItens;

	/**
	 * Construtor padr�o.
	 */
	public SecaoPaginaIncialTO() {
    	
    }
	
	/**
	 * Construtor padr�o.
	 */
	public SecaoPaginaIncialTO(Long cdSecao, TipoSecao tpSecao, String ttSecao,
			String qtItens) {
		super();
		this.cdSecao = cdSecao;
		this.tpSecao = tpSecao;
		this.ttSecao = ttSecao;
		this.qtItens = qtItens;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public AgvTabSecaoPagIni toEntity() { 

		AgvTabSecaoPagIni entity =  new AgvTabSecaoPagIni();
		entity.setCdSecao(cdSecao);
		entity.setQtItens(Long.parseLong(qtItens));
		entity.setTpSecao(new Long(tpSecao.getCodigo()));
		entity.setTtSecao(ttSecao);
		
		return entity;
	}

	public Long getCdSecao() {
		return cdSecao;
	}

	public void setCdSecao(Long cdSecao) {
		this.cdSecao = cdSecao;
	}

	public TipoSecao getTpSecao() {
		return tpSecao;
	}

	public void setTpSecao(TipoSecao tpSecao) {
		this.tpSecao = tpSecao;
	}

	public String getTtSecao() {
		return ttSecao;
	}

	public void setTtSecao(String ttSecao) {
		this.ttSecao = ttSecao;
	}

	public String getQtItens() {
		return qtItens;
	}

	public void setQtItens(String qtItens) {
		this.qtItens = qtItens;
	}

}