package com.prime.app.jpa.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class RunGenericRDMS {
	static SessionFactory sf = new AnnotationConfiguration().configure(
	"/hibernate.cfg.xml").buildSessionFactory();
	
	public static void findQBE(String query) {
		findQBE(query,null);
	}
	
	public static void findQBE(String query, String... params) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		
		try {
			pstmt = sf.openSession().connection().prepareStatement(query);
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					pstmt.setString(i+1, params[i]);
				}
			}
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
		}
		
	}
	
}
