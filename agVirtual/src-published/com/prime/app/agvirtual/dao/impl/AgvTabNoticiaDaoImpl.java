package com.prime.app.agvirtual.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabNoticiaDao;
import com.prime.app.agvirtual.entity.AgvTabNoticia;
import com.prime.app.agvirtual.to.NoticiaTO;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;
import com.prime.infra.util.WrapperUtils;

@Repository
public class AgvTabNoticiaDaoImpl extends GenericHibernateJpaDao<AgvTabNoticia, Long> 	implements AgvTabNoticiaDao {

	private static final Logger logger = LoggerFactory.getLogger(AgvTabNoticiaDaoImpl.class);
	
    
//    public List<Cliente> findByCodigoPersona(String codPersona) {
//    	return findByQuery("select e from Cliente e where e.codigoCliente=?1 and e.recConciliacao=?2 order by e.nomeEmpresaCliente asc", codPersona,Cliente.REC_CONCILIACAO);
//    }
//    
//    public List<Cliente> findByCodigoKGL(String codKGL) {
//    	return findByQuery("select e from Cliente e where e.codigoClienteKGL=?1 and e.recConciliacao=?2 order by e.nomeEmpresaCliente asc", codKGL.toUpperCase(),Cliente.REC_CONCILIACAO);
//    }
//    
//	public Cliente findOneByCodigoPersona(String codPersona) {
//		Cliente cliente;
//		try {
//			cliente = findOneByQuery("select e from Cliente e where e.codigoCliente=?1 and e.recConciliacao=?2", codPersona, Cliente.REC_CONCILIACAO);
//		} catch(Exception e) {
//			 cliente = null;
//		}	
//		return cliente;		
//	}
//	
//	public Cliente findOneByCodigoPersonaTopN(String codPersona) {
//		Cliente cliente;
//		try {
//			cliente = findOneByQuery("select e from Cliente e where e.codigoCliente=?1 and e.grupoCliente.codigoTopN=?2 and e.recConciliacao=?3", codPersona,Boolean.TRUE,Cliente.REC_CONCILIACAO);
//		} catch(Exception e) {
//			 cliente = null;
//		}	
//		return cliente;		
//	}
//	
//	
//	public Cliente findOneByCodigoKGL(String codKGL) {
//		Cliente cliente;
//		try {
//			cliente = findOneByQuery("select e from Cliente e where e.codigoClienteKGL=?1 and e.recConciliacao=?2", codKGL, Cliente.REC_CONCILIACAO);
//		} catch(Exception e) {
//			cliente = null;
//		}
//		return cliente;
//	}
//	
//	
//	public Cliente findOneByCodigoKGLTopN(String codKGL) {
//		Cliente cliente;
//		try {
//			cliente = findOneByQuery("select e from Cliente e where e.codigoClienteKGL=?1 and e.grupoCliente.codigoTopN=?2 and e.recConciliacao=?3", codKGL,Boolean.TRUE,Cliente.REC_CONCILIACAO);
//		} catch(Exception e) {
//			 cliente = null;
//		}	
//		return cliente;		
//	}
//	
//    public List<Cliente> findAll() {
//    	return findByQuery("select e from Cliente e where e.recConciliacao=?1 order by e.nomeEmpresaCliente asc",Cliente.REC_CONCILIACAO);
//    }
//
//    public List<Cliente> findAllTopN() {
//    	return findByQuery("select e from Cliente e where e.recConciliacao=?1 and e.grupoCliente.codigoTopN=?2 order by e.nomeEmpresaCliente asc",Cliente.REC_CONCILIACAO,Boolean.TRUE);
//    }
//
//    public List<Cliente> findAllAprovTopN() {
//    	return findByQuery("select e from Cliente e where e.grupoCliente.grupoKGL=?1 order by e.nomeEmpresaCliente asc", Boolean.TRUE);
//    }
//    
//    public List<Cliente> findByCodigoGrupoNulo() {
//    	return findByQuery("select e from Cliente e where e.grupoCliente.codigo is null and e.recConciliacao=?1", Cliente.REC_CONCILIACAO);
//    }
    

	public void excluseByCriterion() {
	    try {
	    	org.hibernate.Query query = getHibernateSession().createQuery("delete from Cliente e where e.grupoCliente.codigo is null and e.recConciliacao=:codigo");
//	    	query.setString("codigo", Cliente.REC_CONCILIACAO);
	        query.executeUpdate();            
	        } catch(Exception e) {
	             e.printStackTrace();
	        }    
	}


	@SuppressWarnings("unchecked")
	public List<NoticiaTO> findAll() {
		List<AgvTabNoticia> listaResultado = new ArrayList<AgvTabNoticia>();
		try {
			Criteria sql = getHibernateSession().createCriteria(AgvTabNoticia.class);
			sql.add(Restrictions.le("dtPublicacao", WrapperUtils.addDaysToDate(new Date(), 1)));
			sql.addOrder(Order.desc("dtPublicacao"));
			listaResultado = sql.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

    	return parseTO(listaResultado);
    }
	
	/**
	 * Faz o Parse do Entity para o Objeto Transfer Object
	 * @param listaResultado
	 * @return
	 */
	private List<NoticiaTO> parseTO(List<AgvTabNoticia> listaResultado){
		
		ArrayList<NoticiaTO> listaFinal =  new ArrayList<NoticiaTO>();
		for (Iterator<AgvTabNoticia> iterator = listaResultado.iterator(); iterator.hasNext();) {
			AgvTabNoticia object = (AgvTabNoticia) iterator.next();
			listaFinal.add(object.parseTO());
		}
		
		return listaFinal ;
		
	}

	public boolean delete(NoticiaTO noticia) {
		return false;
	}

}
