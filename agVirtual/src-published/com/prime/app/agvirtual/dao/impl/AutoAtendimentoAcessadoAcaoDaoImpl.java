package com.prime.app.agvirtual.dao.impl;

import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AutoAtendimentoAcessadoAcaoDao;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcessadoAcao;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AutoAtendimentoAcessadoAcaoDaoImpl extends GenericHibernateJpaDao<AutoAtendimentoAcessadoAcao, Long> 
	implements AutoAtendimentoAcessadoAcaoDao{

}
