package com.prime.app.agvirtual.dao.impl;

import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AutoAtendimentoPergRespDao;
import com.prime.app.agvirtual.entity.AutoAtendimentoPergResp;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AutoAtendimentoPergRespDaoImpl extends GenericHibernateJpaDao<AutoAtendimentoPergResp, Long> 
	implements AutoAtendimentoPergRespDao{

}
