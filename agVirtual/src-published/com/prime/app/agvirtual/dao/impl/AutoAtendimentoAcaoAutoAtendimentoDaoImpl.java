package com.prime.app.agvirtual.dao.impl;

import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AutoAtendimentoAcaoAutoAtendimentoDao;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcaoAutoAtendimento;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AutoAtendimentoAcaoAutoAtendimentoDaoImpl extends GenericHibernateJpaDao<AutoAtendimentoAcaoAutoAtendimento, Long>
	implements AutoAtendimentoAcaoAutoAtendimentoDao {


}
