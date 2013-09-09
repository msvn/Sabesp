package com.prime.app.agvirtual.dao.impl;

import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AutoAtendimentoAcaoDao;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcao;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AutoAtendimentoAcaoDaoImpl extends GenericHibernateJpaDao<AutoAtendimentoAcao, Long> 
	implements AutoAtendimentoAcaoDao{

}
