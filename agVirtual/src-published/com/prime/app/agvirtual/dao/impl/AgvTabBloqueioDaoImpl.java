package com.prime.app.agvirtual.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabBloqueioDao;
import com.prime.app.agvirtual.entity.AgvTabBloqueio;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

 
@Repository
public class AgvTabBloqueioDaoImpl extends GenericHibernateJpaDao<AgvTabBloqueio, Long> implements AgvTabBloqueioDao {

	private static final Logger logger = LoggerFactory.getLogger(AgvTabBloqueioDaoImpl.class);

	public AgvTabBloqueio save(AgvTabBloqueio entity) { 
		
			 Session session = getHibernateSession();
//			 List temp = t.getAgvListaServSubServ();
			 AgvTabBloqueio entityUpdated = getEntityManager().merge(entity);
			 return entityUpdated;
	}
	
	public List findAll(){
		List<AgvTabBloqueio> listaResultado = findByQuery("select e from AgvTabBloqueio e  where e.excluido = false order by e.dtInclusao desc" );
    	return (listaResultado);
	}

	public boolean isItemMenuBloqueado(String cdItemMenu, String cdMunicipio) {
		
		String sqlQuery =
			"select * from agv_tab_bloqueio b , agv_tab_bloqueio_detalhe d "+  
			"where b.fl_excluido = 0 and d.cd_bloqueio = b.cd_bloqueio " +
			"and ( d.cd_item_menu_acesso = ? or d.fl_todas_func = 1) " +
			"and ( d.cd_municipio= ? or d.fl_todas_municipio = 1) ";
			
			PreparedStatement pstmt = null;
			ResultSet rs =  null;
			
			logger.info("#BloqueioDaoImpl# -> Método isItemMenuBloqueado  # \n");
			logger.info("Query ==> \n" + sqlQuery);
			try{
				pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
				
				pstmt.setString(1, cdItemMenu);
				pstmt.setString(2, cdMunicipio);

				logger.info("Query param 1 ==> "+  cdItemMenu);
				logger.info("Query param 2 ==> "+ cdMunicipio);
				
				rs = pstmt.executeQuery();
				if(rs.next()){
					return true;
				}else{
					return false;
				}

			}catch (Exception e) {
				logger.error("Erro consulta RGI:"+ e.getMessage());
				new IllegalArgumentException("Erro pesquisar NR Ciclo da conta, pesquisa de contas em aberto");
			}
			return true;
	}
}
