package com.prime.app.jpa.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;

import com.prime.app.agvirtual.entity.Imovel;

public class RunAgenciaJPA {
	static SessionFactory sf = new AnnotationConfiguration().configure(
			"/hibernate.cfg.xml").buildSessionFactory();

	public static void main(String[] args) throws Exception {
		/*
		 * Session session = sf.openSession(); Query query =
		 * session.createQuery("FROM ImovelPk c WHERE c.codigoRgi=1597");
		 * List<CEA11> resultado = query.list(); for (CEA11 c : resultado) {
		 * System.out.println(c.getId()); } session.close();
		 */
		buscaEnderecoAgenciaSaoPaulo();
//		buscaEnderecoAgencia();
//		buscaBancos();
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
	
	
	/**
	 * Endereço dos atendimentos comerciais de são paulo (cdmunicip=100)

		select a.cdmunicip, a.tpunidcom, a.cdunidcom, a.cdlogradr, b.tplograd, b.cdtitlog, b.cdsgprep, b.dsenderec, a.nrenderec, 
		a.cdbairro from CTB18 a, ccg05 b where tpunidcom=5 and cdmunicip=100 and a.cdmunicip = b.cdmunicip and a.cdlogradr = b.cdlogradr 
		order by a.CDUNIDCOM
		
	*/
	
	private static void buscaEnderecoAgenciaSaoPaulo(){
//		String sqlQuery = "select a.cdmunicip, a.tpunidcom, a.cdunidcom, a.cdlogradr, b.tplograd, b.cdtitlog, b.cdsgprep, b.dsenderec, a.nrenderec, "+ 
//		"a.cdbairro from CTB18 a, ccg05 b where tpunidcom=5 and cdmunicip=100 and a.cdmunicip = b.cdmunicip and a.cdlogradr = b.cdlogradr "+ 
//		"order by a.CDUNIDCOM ";
		
		try{
			
		String sqlUnidCom = "SELECT B.CDMUNICIP, B.CDUNIDCOM FROM PCCG0305 A,CCG03 B WHERE A.NRRGILIG=590531433 " +
		"AND B.NRGRUPFAT=A.NRGRUPFAT AND B.NRRGILIG=A.NRRGILIG";
		
			String sqlQuery = "SELECT A.NMGERAL, A.CDBAIRRO, D.NMBAIRRO, A.CDCEP, A.CDDDD, A.CDLOGRADR,b.DSENDEREC, A.DSCOMPLEM, A.NRENDEREC, " +
					"A.NRTELEF, A.CDDDDFAX, A.NRTELEFAX, A.DSRAMAL, A.HRINICIO, A.HRFIM, A.HRINIINTV, A.HRFIMINTV, A.CDUNIDCOM, A.CDMUNICIP, " +
					"C.NMGERAL, A.NMABRUNID, A.TPUNIDCOM, A.STACATAM FROM CTB18 A , CCG05 B, CTB17 C, PCTB3002 D WHERE A.TPUNIDCOM=5 and A.CDUNIDCOM = ?  " +
					"and a.cdmunicip = b.cdmunicip and a.cdlogradr = b.cdlogradr AND C.CDMUNICIP=A.CDMUNICIP AND D.CDMUNICIP=A.CDMUNICIP " +
					"AND D.CDBAIRRO=A.CDBAIRRO";
			
			String unidComRgi = "";
			String cdMunicipio = "";
		
			PreparedStatement pstmt2 = sf.openSession().connection().prepareStatement(sqlUnidCom);
			ResultSet rs2 = pstmt2.executeQuery();
			
			while( rs2.next() ){
				System.out.println(rs2.getString(1));
				System.out.println(rs2.getString(2));
				cdMunicipio = rs2.getString(1);
				unidComRgi = rs2.getString(2);
			}
		
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, unidComRgi);
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getString(5));
				System.out.println(rs.getString(6));
				System.out.println(rs.getString(7));
				System.out.println(rs.getString(8));
				System.out.println(rs.getString(9));
				System.out.println(rs.getString(10));
				System.out.println(rs.getString(11));
				System.out.println(rs.getString(12));
				System.out.println(rs.getString(13));
				System.out.println(rs.getString(14));
				System.out.println(rs.getString(15));
				System.out.println(rs.getString(16));
				System.out.println(rs.getString(17));
				System.out.println(rs.getString(18));
				System.out.println(rs.getString(19));
				System.out.println(rs.getString(20));
				System.out.println(rs.getString(21));
				System.out.println(rs.getString(22));
				System.out.println(rs.getString(23));
				System.out.println("===================================");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		
		System.err.println("FIM=======");
	}
		
	/**
		Endereço dos atendimentos comerciais diferente de 100. 
		
		select a.cdmunicip, a.tpunidcom, a.cdunidcom, a.cdlogradr, b.tplograd, b.cdtitlog, b.cdsgprep, b.dsenderec, a.nrenderec, a.cdbairro 
		from CTB18 a, ccg05 b where tpunidcom=5 and cdunidcom=40 and a.cdmunicip = b.cdmunicip and a.cdlogradr = b.cdlogradr order by a.CDUNIDCOM

	 */
	
	private static void buscaEnderecoAgencia(){
		String sqlQuery = "select a.cdmunicip, a.tpunidcom, a.cdunidcom, a.cdlogradr, b.tplograd, b.cdtitlog, b.cdsgprep, b.dsenderec, a.nrenderec, a.cdbairro "+ 
		"from CTB18 a, ccg05 b where tpunidcom=5 and cdunidcom=40 and a.cdmunicip = b.cdmunicip and a.cdlogradr = b.cdlogradr order by a.CDUNIDCOM";
		
		try{
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getString(5));
				System.out.println(rs.getString(6));
				System.out.println(rs.getString(7));
				System.out.println(rs.getString(8));
				System.out.println(rs.getString(9));
				System.out.println(rs.getString(10));
				System.out.println("===================================");
			}
			
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}

}
