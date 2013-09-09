/**
 * 
 */
package com.prime.infra.dao.jpa;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author miguelsv
 * 
 */
public abstract class GenericRDMSJpaDao<T, ID extends Serializable> extends GenericHibernateJpaDao<T, ID> {

	@PersistenceContext(unitName = "manager2")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public ArrayList<String> findQBEOneTuple(String query, String... params) {
		ArrayList<String> retorno=new ArrayList<String>();		
		try {
			retorno = findQBE(query, params).get(0);
		} catch (Exception e) {
			retorno.add("");
			retorno.add("");
			retorno.add("");
		}
		return retorno;
	}
	
	public ArrayList<ArrayList<String>> findQBE(String query) {
		return findQBE(query,null);
	}
	
	public ArrayList<ArrayList<String>> findQBE(String query, String... params) {
		ArrayList<ArrayList<String>> retorno = new ArrayList<ArrayList<String>>();
		ArrayList<String> lista = new ArrayList<String>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			pstmt = getHibernateSession().connection().prepareStatement(query);
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					pstmt.setString(i+1, params[i]);
				}
			}
			// TODO miguel - colocar controle de log
			System.out.println(query);
			rs = pstmt.executeQuery();
			boolean first = true;
			while (rs.next()) {
//				if(first){
//					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//						System.out.print(rs.getMetaData().getColumnName(i)+ ";");						
//					}						
//					first=false;
//					System.out.print("\n");
//				}
				lista = new ArrayList<String>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//					System.out.print(rs.getString(i)+ ";");	
					lista.add(rs.getString(i));
				}				
//				System.out.print("\n");
				retorno.add(lista);
			}
		} catch (Exception e) {
            e.printStackTrace();
		} finally{
			fecharRecurso(pstmt, null, rs);
		}
		return retorno;
		
	}
	
	protected void fecharRecurso(PreparedStatement pstmt, Statement stmt,ResultSet rs)  {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
	
			if (stmt != null) {
				stmt.close();
			}
	
			if (rs != null) {
				rs.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
