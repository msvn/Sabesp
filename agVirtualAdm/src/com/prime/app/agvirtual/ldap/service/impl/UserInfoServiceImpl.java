package com.prime.app.agvirtual.ldap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prime.app.agvirtual.ldap.dao.UserInfoDao;
import com.prime.app.agvirtual.ldap.dto.UserInfo;
import com.prime.app.agvirtual.ldap.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	
	@Autowired
	private UserInfoDao userInfoDao;

	public List<String> init() {
		List<String> retorno = new ArrayList<String>();
		for(UserInfo info : userInfoDao.findAll() ){
			System.out.println(info.getName());
			retorno.add(info.getName());
		}
		
		return retorno;
	}

	
}
