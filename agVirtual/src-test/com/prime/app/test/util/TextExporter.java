package com.prime.app.test.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;

public class TextExporter {
	public Properties props = new PropsUtil().load("/testcase.properties");
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");	
		
	public void findQBE(String query, String... params) {
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
				if(first){
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						System.out.print(rs.getMetaData().getColumnName(i)+ ";");						
					}						
					first=false;
					System.out.print("\n");
				}
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.print(rs.getString(i)+ ";");	
				}				
				System.out.print("\n");
			}
		} catch (Exception e) {
            e.printStackTrace();
		} finally{
			//		fecharRecurso(pstmt, null, rs);
		}
		//	return retorno
		
	}

	private Session getHibernateSession() {		
		return (Session)emf.createEntityManager().getDelegate();
	}
	
	public static void main(String[] args) {		
		TextExporter txt = new TextExporter();
		txt.findQBE(txt.props.getProperty("sqlquery.menus"));
		
	}
}
