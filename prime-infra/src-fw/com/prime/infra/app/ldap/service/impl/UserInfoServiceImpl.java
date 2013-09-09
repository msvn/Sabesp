package com.prime.infra.app.ldap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prime.infra.app.ldap.dao.UserInfoDao;
import com.prime.infra.app.ldap.dto.UserInfo;
import com.prime.infra.app.ldap.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	
	@Autowired
	private UserInfoDao userInfoDao;

	public List init() {
		List retorno = new ArrayList();
		for(UserInfo info : userInfoDao.findAll() ){
			System.out.println(info.getName());
			retorno.add(info.getName());
		}
		
		return retorno;
	}

	
}
