package com.prime.app.agvirtual.dao.impl;

import com.prime.app.agvirtual.dao.PerguntaAutoAtendimentoDao;
import com.prime.app.agvirtual.entity.PerguntaAutoAtendimento;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

public class PerguntaAutoAtendimentoDaoImpl extends GenericHibernateJpaDao<PerguntaAutoAtendimento, Integer> 
	implements PerguntaAutoAtendimentoDao{

	public PerguntaAutoAtendimento findById(int id) {
		return super.findById(id, Boolean.FALSE);
	}

}
