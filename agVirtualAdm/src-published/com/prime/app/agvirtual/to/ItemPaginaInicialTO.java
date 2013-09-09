package com.prime.app.agvirtual.to;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.prime.app.agvirtual.entity.AgvTabItemPagIni;

public class ItemPaginaInicialTO implements BasicTO<AgvTabItemPagIni> {

    /**
	 * Serial.
	 */
	private static final long serialVersionUID = -8769548249255627114L;

	/**
	 * Seções da página inicial:
	 * - Sua Conta;
	 * - Consertos;
	 * - Ligações de água e esgoto;
	 * - Emergências;
	 * - Dicas;
	 */
	private SecaoPaginaIncialTO secao;
	
	/**
	 * Identificador único do Item.
	 */
	private String cdItem;
	
	/**
	 * Nome do Item.
	 */
	private String dsItem;
	
	/**
	 * Breve descrição do Item.
	 */
	private String dsResumoItem;
	
	/**
	 * URL de onde o item deverá direcionar o cliente.
	 */
	private String dsLink;
	
	/**
	 * Localização da imagem do Item.
	 */
	private String dsCaminhoImagem;
	
	/**
	 * Construtor padrão.
	 */
	public ItemPaginaInicialTO() {
    	
    }

	/**
	 * Construtor padrão.
	 */
	public ItemPaginaInicialTO(SecaoPaginaIncialTO secao, String cdItem,
			String dsItem, String dsResumoItem, String dsLink,
			String dsCaminhoImagem) {
		super();
		this.secao = secao;
		this.cdItem = cdItem;
		this.dsItem = dsItem;
		this.dsResumoItem = dsResumoItem;
		this.dsLink = dsLink;
		this.dsCaminhoImagem = dsCaminhoImagem;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public AgvTabItemPagIni toEntity() { 

		AgvTabItemPagIni entity =  new AgvTabItemPagIni();
		entity.setCdItem(Long.parseLong(cdItem));
		entity.setDsCaminhoImg(dsCaminhoImagem);
		entity.setDsItem(dsItem);
		entity.setDsLink(dsLink);
		entity.setDsResumoItem(dsResumoItem);
		entity.setAgvTabSecaoPagIni(secao.toEntity());
		
		return entity;
	}

	public SecaoPaginaIncialTO getSecao() {
		if (secao == null) {
			secao = new SecaoPaginaIncialTO();
		}
		return secao;
	}

	public void setSecao(SecaoPaginaIncialTO secao) {
		this.secao = secao;
	}

	public String getCdItem() {
		return cdItem;
	}

	public void setCdItem(String cdItem) {
		this.cdItem = cdItem;
	}

	public String getDsItem() {
		return dsItem;
	}

	public void setDsItem(String dsItem) {
		this.dsItem = dsItem;
	}

	public String getDsResumoItem() {
		if (dsResumoItem != null) {
			return dsResumoItem.trim();
		} else {
			return dsResumoItem;
		}
	}

	public void setDsResumoItem(String dsResumoItem) {
		if (dsResumoItem != null) {
			this.dsResumoItem = dsResumoItem.trim();
		} else {
			this.dsResumoItem = dsResumoItem;
		}
	}

	public String getDsLink() {
		if (dsLink != null) {
			return dsLink.trim();
		} else {
			return dsLink;
		}
	}

	public void setDsLink(String dsLink) {
		if (dsLink != null) {
			this.dsLink = dsLink.trim();
		} else {
			this.dsLink = dsLink;
		}
	}

	public String getDsCaminhoImagem() {
		return dsCaminhoImagem;
	}

	public void setDsCaminhoImagem(String dsCaminhoImagem) {
		this.dsCaminhoImagem = dsCaminhoImagem;
	}
}