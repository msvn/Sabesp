package com.prime.app.agvirtual.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.SQLDao;
import com.prime.app.agvirtual.entity.AgvTabNoticia;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class SQLDaoImpl extends GenericHibernateJpaDao<AgvTabNoticia, Long> implements SQLDao {
	
	public List executeQuery(String query) {
	     return findByQuery(query);
	     
//		String params[] = null;
//	        
//		PreparedStatement pstmt=null;
//		ResultSet rs=null;
//		
//		try {
//			pstmt = getHibernateSession().connection().prepareStatement(query);
//			if(params!=null){
//				for (int i = 0; i < params.length; i++) {
//					pstmt.setString(i+1, params[i]);
//				}
//			}
//			// TODO miguel - colocar controle de log
//			System.out.println(query);
//			rs = pstmt.executeQuery();
//			boolean first = true;
//			while (rs.next()) {
//				if(first){
//					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//						System.out.print(rs.getMetaData().getColumnName(i)+ ";");						
//					}						
//					first=false;
//					System.out.print("\n");
//				}
//				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//					System.out.print(rs.getString(i)+ ";");	
//				}				
//				System.out.print("\n");
//			}
//		} catch (Exception e) {
//            e.printStackTrace();
//		} finally{
//			//		fecharRecurso(pstmt, null, rs);
//		}
//		return null;		
		
		
//			org.hibernate.Query query = null;
//		    try {
//		    		query = getHibernateSession().createSQLQuery(value);
//		    		return query.list();
//		        } catch(Exception e) {
//		             e.printStackTrace();	        
//		        }   
//	       return findByQuery(value);
	}
}
