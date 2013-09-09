package com.prime.app.agvirtual.ldap.mapper;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.simple.ParameterizedContextMapper;

import com.prime.app.agvirtual.ldap.dto.UserInfo;

public class UserInfoMapper implements ParameterizedContextMapper<UserInfo> {

	public UserInfo mapFromContext(Object ctx) {
		DirContextAdapter adapter = (DirContextAdapter) ctx;
		UserInfo info = new UserInfo();
		info.setName(adapter.getStringAttribute("cn"));
		System.out.println(info);
		return info;
	}

}
