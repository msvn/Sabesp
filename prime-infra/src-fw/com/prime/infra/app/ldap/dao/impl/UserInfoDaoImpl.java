package com.prime.infra.app.ldap.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prime.infra.app.ldap.dao.UserInfoDao;
import com.prime.infra.app.ldap.dto.UserInfo;
import com.prime.infra.app.ldap.mapper.UserInfoMapper;
import com.prime.infra.dao.ldap.GenericLdapDao;

/**
 * 
 * pagedSearch(String filter, ParameterizedContextMapper<?> mapper, int pageSize): List<T>: 
 *    Executa uma busca p�ginada para casos em que o resultado esperado excede a 
 *    quantidade m�xima de resultados retornados pelo LDAP server.
 *    
 * search(String filter, ParameterizedContextMapper<?> mapper, PagedResultsCookie cookie, int pageSize): List<T>: 
 *    Executa uma busca p�ginada para casos em que o resultado esperado excede a 
 *    quantidade m�xima de resultados retornados pelo LDAP server.
 *    
 * getSimpleLdapTemplate(): SimpleLdapTemplate: A instancia do SimpleLdapTemplate usada pelo GenericLdapDao. 
 *
 */
@Repository
public class UserInfoDaoImpl extends GenericLdapDao implements UserInfoDao {
	private final int PAGE_SIZE = 500;

	public List<UserInfo> findAll() {
		final String filter = "(objectclass=user)";
		return pagedSearch(filter, new UserInfoMapper(), PAGE_SIZE);
	}

}
