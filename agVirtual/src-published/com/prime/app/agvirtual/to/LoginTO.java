package com.prime.app.agvirtual.to;

import java.io.Serializable;
import java.util.Date;

/**
 * TO usado no login no sistema Administração
 * @author gustavons
 *
 */
public class LoginTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3641707545178963249L;
	/**
	 * 
	 */
	
	private Long cdUser ;
	private String user;
	private String email;
	private String ip;
	private Date dataInicioSessao;
	private String codSessao;
	
	
	public LoginTO(Long cdUser, String codSessao, Date dataInicioSessao,
			String email, String ip, String user) {
		super();
		this.cdUser = cdUser;
		this.codSessao = codSessao;
		this.dataInicioSessao = dataInicioSessao;
		this.email = email;
		this.ip = ip;
		this.user = user;
	}
	public LoginTO() {
	}
	public Long getCdUser() {
		return cdUser;
	}
	public void setCdUser(Long cdUser) {
		this.cdUser = cdUser;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getDataInicioSessao() {
		return dataInicioSessao;
	}
	public void setDataInicioSessao(Date dataInicioSessao) {
		this.dataInicioSessao = dataInicioSessao;
	}
	public String getCodSessao() {
		return codSessao;
	}
	public void setCodSessao(String codSessao) {
		this.codSessao = codSessao;
	}
	
	

}
