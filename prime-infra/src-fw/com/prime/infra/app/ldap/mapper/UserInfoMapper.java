package com.prime.infra.app.ldap.mapper;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.simple.ParameterizedContextMapper;

import com.prime.infra.app.ldap.dto.UserInfo;

public class UserInfoMapper implements ParameterizedContextMapper<UserInfo> {

	public UserInfo mapFromContext(Object ctx) {
		DirContextAdapter adapter = (DirContextAdapter) ctx;
		UserInfo info = new UserInfo();
		info.setName(adapter.getStringAttribute("cn"));
		System.out.println(info);
		return info;
	}

}
