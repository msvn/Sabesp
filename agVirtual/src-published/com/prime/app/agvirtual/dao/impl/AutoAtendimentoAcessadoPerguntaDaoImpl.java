package com.prime.app.agvirtual.dao.impl;

import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AutoAtendimentoAcessadoPerguntaDao;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcessadoPergunta;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AutoAtendimentoAcessadoPerguntaDaoImpl extends GenericHibernateJpaDao<AutoAtendimentoAcessadoPergunta, Long> 
implements AutoAtendimentoAcessadoPerguntaDao{
	
}
