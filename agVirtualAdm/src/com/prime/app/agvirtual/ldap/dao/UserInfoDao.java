package com.prime.app.agvirtual.ldap.dao;

import java.util.List;

import com.prime.app.agvirtual.ldap.dto.UserInfo;

public interface UserInfoDao {
	public List<UserInfo> findAll();

}
