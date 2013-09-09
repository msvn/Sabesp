package com.prime.app.agvirtual.ldap.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.ldap.dao.UserInfoDao;
import com.prime.app.agvirtual.ldap.dto.UserInfo;
import com.prime.app.agvirtual.ldap.mapper.UserInfoMapper;
import com.prime.infra.dao.ldap.GenericLdapDao;

/**
 * 
 * pagedSearch(String filter, ParameterizedContextMapper<?> mapper, int pageSize): List<T>: 
 *    Executa uma busca páginada para casos em que o resultado esperado excede a 
 *    quantidade máxima de resultados retornados pelo LDAP server.
 *    
 * search(String filter, ParameterizedContextMapper<?> mapper, PagedResultsCookie cookie, int pageSize): List<T>: 
 *    Executa uma busca páginada para casos em que o resultado esperado excede a 
 *    quantidade máxima de resultados retornados pelo LDAP server.
 *    
 * getSimpleLdapTemplate(): SimpleLdapTemplate: A instancia do SimpleLdapTemplate usada pelo GenericLdapDao. 
 *
 */
@Repository
public class UserInfoDaoImpl extends GenericLdapDao implements UserInfoDao {
	private final int PAGE_SIZE = 500;

	public List<UserInfo> findAll() {
		final String filter ="(objectclass=organizationalUnit)";
		return pagedSearch(filter, new UserInfoMapper(), PAGE_SIZE);
	}

}
