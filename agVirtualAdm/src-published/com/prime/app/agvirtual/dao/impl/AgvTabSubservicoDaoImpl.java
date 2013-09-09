package com.prime.app.agvirtual.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabSubservicoDao;
import com.prime.app.agvirtual.entity.AgvTabSubservico;
import com.prime.app.agvirtual.to.SubServicoTO;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

/**
 * Imterface DAO
 * @author gustavons
 *
 */
@Repository
public class AgvTabSubservicoDaoImpl extends GenericHibernateJpaDao<AgvTabSubservico, Long> implements AgvTabSubservicoDao {

	public List<SubServicoTO> findAll(){
		List<AgvTabSubservico> listaResultado = findByQuery("select e from AgvTabSubservico e order by e.dsSubservico asc");
    	return parseTO(listaResultado);
	}
	
	/**
	 * Faz De Para do objeto entity para o TO
	 * @param listaResultado
	 * @return
	 */
	private List<SubServicoTO> parseTO(List<AgvTabSubservico> listaResultado) {
		
		ArrayList<SubServicoTO> lista =  new ArrayList<SubServicoTO>();
		
		for (Iterator<AgvTabSubservico> iterator = listaResultado.iterator(); iterator.hasNext();) {
			AgvTabSubservico element = (AgvTabSubservico) iterator.next();
			
			SubServicoTO to =  new SubServicoTO();
			to.setCdServicoCsi( (element.getCdServicoCsi() == null ? null : element.getCdServicoCsi().toString()));
			to.setCdSerCom(element.getCdSerCom());
			to.setCdServExe(element.getCdServExe());
			to.setCdSubservico(element.getCdSubservico());
			to.setDataAtualizacao(element.getDtAtualizacao());
			to.setDataPublicacao(element.getDtPublicacao());
			to.setDsCondExec(element.getDsCondExec());
			to.setDsFormaPgto(element.getDsFormaPgto());
			to.setDsLink(element.getDsLink());
			to.setDsPrazoAtend(element.getDsPrazoAtend());
			to.setDsPreco(element.getDsPreco());
			to.setDsSubservico(element.getDsSubservico());
			to.setFlagPublicGuia(element.getFlPublicGuia());
			to.setFlagPublicTabPrecos(element.getFlPublicTabPrecos());

			lista.add(to);
		}
		
		return lista;
	}

	/**
	 * Salva AgvTabSubservico
	 */
	public AgvTabSubservico save(AgvTabSubservico t) { 
		Session session = getHibernateSession();
 		AgvTabSubservico entityUpdated = getEntityManager().merge(t);
// 		getEntityManager().persist(t);
		return entityUpdated;
	}

	public void alterar(AgvTabSubservico entity) {
		Session session = getHibernateSession();
		getEntityManager().merge(entity);
	}

	public List<SubServicoTO> findAllServicoVazio() {
		List<AgvTabSubservico> listaResultado = findByQuery("select e from AgvTabSubservico e where e.servico = null");
    	return parseTO(listaResultado);
	}	
}
