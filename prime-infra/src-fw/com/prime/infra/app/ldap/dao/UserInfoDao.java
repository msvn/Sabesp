package com.prime.infra.app.ldap.dao;

import java.util.List;

import com.prime.infra.app.ldap.dto.UserInfo;

public interface UserInfoDao {
	public List<UserInfo> findAll();

}
