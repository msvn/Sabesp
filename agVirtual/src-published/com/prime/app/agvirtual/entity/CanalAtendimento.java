package com.prime.app.agvirtual.entity;

import java.io.Serializable;

public class CanalAtendimento implements Serializable {

    /**
	 * Serial.
	 */
	private static final long serialVersionUID = -453568965170737526L;

    private Long cdItem;

    private String dsItem;
    
    private String dsLink;

	public CanalAtendimento() {
    	
    }

	public Long getCdItem() {
		return cdItem;
	}

	public void setCdItem(Long cdItem) {
		this.cdItem = cdItem;
	}

	public String getDsItem() {
		return dsItem;
	}

	public void setDsItem(String dsItem) {
		this.dsItem = dsItem;
	}

	public String getDsLink() {
		return dsLink;
	}

	public void setDsLink(String dsLink) {
		this.dsLink = dsLink;
	}
}
