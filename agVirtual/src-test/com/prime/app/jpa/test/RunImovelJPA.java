package com.prime.app.jpa.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;

import com.prime.app.agvirtual.entity.Imovel;

public class RunImovelJPA {
	static SessionFactory sf = new AnnotationConfiguration().configure(
			"/hibernate.cfg.xml").buildSessionFactory();

	public static void main(String[] args) throws Exception {
		/*
		 * Session session = sf.openSession(); Query query =
		 * session.createQuery("FROM ImovelPk c WHERE c.codigoRgi=1597");
		 * List<CEA11> resultado = query.list(); for (CEA11 c : resultado) {
		 * System.out.println(c.getId()); } session.close();
		 */
		buscaRgiMaster();
//		getCronogramaLeitura();

	}

	
	private static void buscaRgiMaster(){
		String sqlQuery = "SELECT A.NRRGILIG ,C.CDUNIDCO3,C.CDCONTFAT ,B.CDCOBR FROM PCCG0305 A,CCG03 B,CFE01 C WHERE B.NRGRUPFAT=A.NRGRUPFAT " +
				"AND B.NRRGILIG=A.NRRGILIG AND C.CDMUNICIP=B.CDMUNICIP AND C.CDCLIENTE=B.CDCLIENTE AND C.STREGIST=0";

		
		try{
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, "188");
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				System.out.println(rs.getString("NRRGILIG"));
			}
			System.out.println("FIM");
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}
	

}
