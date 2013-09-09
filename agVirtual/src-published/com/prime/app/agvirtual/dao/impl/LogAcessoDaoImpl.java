/**
 * 
 */
package com.prime.app.agvirtual.dao.impl;

import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.LogAcessoDao;
import com.prime.app.agvirtual.entity.LogAcesso;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class LogAcessoDaoImpl extends GenericHibernateJpaDao<LogAcesso, Long> 
	implements LogAcessoDao {
	
	// metodo persist herdado
}
