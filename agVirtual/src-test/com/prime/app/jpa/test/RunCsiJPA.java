package com.prime.app.jpa.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;

import com.prime.app.agvirtual.entity.Imovel;

public class RunCsiJPA {
	static SessionFactory sf = new AnnotationConfiguration().configure(
			"/hibernate.cfg.xml").buildSessionFactory();

	public static void main(String[] args) throws Exception {
		/*
		 * Session session = sf.openSession(); Query query =
		 * session.createQuery("FROM ImovelPk c WHERE c.codigoRgi=1597");
		 * List<CEA11> resultado = query.list(); for (CEA11 c : resultado) {
		 * System.out.println(c.getId()); } session.close();
		 */
		buscaBancos();
//		getCronogramaLeitura();

	}

	private static void findByRgi2() {
		Session session = sf.openSession();

		// Query query = session.createQuery(
		// "FROM Imovel p WHERE p.imovelProfile.imovelPk.rgi = 1597");

		Criteria criteria = session.createCriteria(Imovel.class);
		criteria.add(Restrictions.eq("imovelPk.rgi", "1597"));

		Imovel imovelLoad = (Imovel) criteria.uniqueResult();

		System.out.println(imovelLoad.getCdMunicipio());

		System.out.println(imovelLoad.getCdImovel());

		session.close();

	}
	
	private static void buscaBancos(){
		String sqlQuery = "select cdbanco,nmbanco,nmrazsoc,tpcobranc from ctb51 where stdebban='A' order by 2";

		
		try{
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, "188");
			pstmt.setString(2, "1649");
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				System.out.println(rs.getString("cdbanco"));
				System.out.println(rs.getString("nmbanco"));
				System.out.println(rs.getString("nmrazsoc"));
				System.out.println(rs.getString("tpcobranc"));
			}
			
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}
	

}
