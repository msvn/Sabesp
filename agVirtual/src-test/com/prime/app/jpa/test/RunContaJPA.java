package com.prime.app.jpa.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.prime.infra.util.WrapperUtils;

public class RunContaJPA {
	static SessionFactory sf = new AnnotationConfiguration().configure(
			"/hibernate.cfg.xml").buildSessionFactory();

	public static void main(String[] args) throws Exception {
		/*
		 * Session session = sf.openSession(); Query query =
		 * session.createQuery("FROM ImovelPk c WHERE c.codigoRgi=1597");
		 * List<CEA11> resultado = query.list(); for (CEA11 c : resultado) {
		 * System.out.println(c.getId()); } session.close();
//		 */
		
//		buscaDataLeituraConta();
//		buscaHistoricoContas();
//		getCodHistoricoContas();
//		pesquisaDebitos();
//		pesquisaCdHidrometro();
//		pesquisaDepositos();
//		teste();
		getContasEmAbertoByRGI();
//		testeAcatamento5x();
//		testeLigacaoSuprimida();
//		buscaConta();
//		getColumnMetada();
//		testeSituacaoConta();
//		testenRCicloSuperintendencia();
//		testeDebitoAutomaticoExtratoConta();
//		testeDebitoAutomaticoConta();
//		getColumnMetada();

	}
	
	private static void pesquisaCdHidrometro() {
		String sql = "select NRRGILIG , cdhidro from cfe44   ";
		String result = "";
		try{
			
			Session session = sf.openSession();
			PreparedStatement ps = session.connection().prepareStatement(sql);
			
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString(1) +"|"+ rs.getString(2));
			}
			System.out.println("FIM");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void pesquisaDepositos() {
		String sql = "select vlcmonet,vljurmora,vlmulta,vldescont from cpu14 where  nrrgipar=47487780 and amjrefer= 362 and tpconta= 6 and nrseqcta2= 1  and nrseqcta= 1";
		String result = "";
		try{
			Session session = sf.openSession();
			PreparedStatement ps = session.connection().prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
			System.out.println("FIM");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	 

	private static void pesquisaDebitos() throws HibernateException, SQLException {
		
		String sqlDebito = "SELECT VLCONTAG2 FROM CEA22 tbl WHERE tbl.NRRGILIG = 65009002 AND tbl.AMJREFER = 299" +
				" AND tbl.TPCONTA = 6 AND tbl.NRSEQCTA2 = 1 AND tbl.NRSEQCTA = 1 AND tbl.STREGIST=1  " +
				"AND tbl.TPREGISTR=1 OR tbl.TPREGISTR=3";
		Session session = sf.openSession();
		PreparedStatement ps = session.connection().prepareStatement(sqlDebito);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
		session.close();
	}

	private static void getContasEmAbertoByRGI() throws HibernateException, SQLException {
		String sqlQuery = "SELECT amjrefer,djvenc,vlcontag ,TPCONTA ,NRSEQCTA2 ,ISPEC ,STPAGTO , STCORTSUP " +
				",CDCOBR, VLAGUA, VLESGOTO, VLDESCONT , nrseqcta  " +
				"FROM EVENT WHERE NRRGILIG=? " +
				"AND STCONTA=0 AND STPAGTO IN (0,4) order by amjrefer desc "; 
		
		Session session = sf.openSession();
		PreparedStatement ps = session.connection().prepareStatement(sqlQuery);
		String rgi = "751866830";
		ps.setString(1,rgi);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
		session.close();
		
		
		String parcela = "SELECT " +
		"QTPARCELA, " +
		"NRACORDO " +
		"FROM CPU01 tbl " +
		"WHERE tbl.NRRGIPAR = " + rgi + " "+
		" AND tbl.AMJREFER = " + 370 +
		" AND tbl.TPCONTA = "  + 6 +
		" AND tbl.NRSEQCTA2 = " + 1 ;
		
		Session session2 = sf.openSession();
		PreparedStatement ps2 = session2.connection().prepareStatement(parcela);
		ResultSet rs2 = ps.executeQuery();
		while (rs2.next()) {
			System.out.println(rs2.getString(1));
		}
		session.close();
	}
	
	private static void getCodHistoricoContas() throws HibernateException, SQLException {
		String sqlQuery = 
			"Select cdhistfat,djatualiz from cfe06 where  nrrgilig = 4180720 and amjrefer = 344  and tpconta = 6 and nrseqcta2 =  1  and cdhistfat in (50,51,52,53,54,55,56,99,69,300,387) and nrseqcta = 1  and  cdunidco2 = 52 order by djatualiz desc"; 
		
		/**
		 * 20/09/2010 14:49:42  
			INFO: agvirtual: Query param 1 ==> 4180720
			20/09/2010 14:49:42  
			INFO: agvirtual: Query param 2 ==> 363
			20/09/2010 14:49:42  
			INFO: agvirtual: Query param 3 ==> 6
			20/09/2010 14:49:42  
			INFO: agvirtual: Query param 4 ==> 1
			20/09/2010 14:49:42  
			INFO: agvirtual: Query param 5 ==> 1
			20/09/2010 14:49:42  
			INFO: agvirtual: Query param 6 ==> 239
		 */
		Session session = sf.openSession();
		PreparedStatement ps = session.connection().prepareStatement(sqlQuery);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
		session.close();
	}
	
	
	
			
	

	
	private static void buscaHistoricoContas() throws SQLException, FileNotFoundException{
			Session session = sf.openSession();
//			CCG50.TPRETENC = 0 (não tem retenção) e CCG50.CDCOBR =9 (ROL COMUM) E (CCG03 - CADASTRO E EVENT - DÉBITO).CDCOBR=7 (ROL ESPECIAL)
			PreparedStatement ps = session.connection().prepareStatement(
					"select a.amjrefer,a.tpconta,a.nrseqcta2,a.nrseqcta,a.qtconsfat,a.stconsumo,a.qtconsumo,a.vlleitura, b.qtconsumo as qtconscomp,b.vlleitura as vlleitcomp from cea15 a left outer join cfe46 b  on a.nrrgilig=b.nrrgilig and a.amjrefer=b.amjrefer and a.tpconta=b.tpconta and a.nrseqcta2=b.nrseqcta2 and a.nrseqcta=b.nrseqcta where a.nrrgilig= ? and a.stregist=1 and a.amjrefer > ? order by a.amjrefer desc");
			ps.setString(1, "2720");
			Long nAmjLimite =  WrapperUtils.getMonthsFromDate( WrapperUtils.removeMonthsFromNow(20) );
			System.out.println(nAmjLimite);
			ps.setString(2, nAmjLimite.toString());
			ResultSet rs = ps.executeQuery();
			StringBuffer stringB = new StringBuffer();
			while (rs.next()) {
				for (int i = 1; i <= 1; i++) {
						stringB.append(rs.getString(i) +"|");
				}
				stringB.append("\n");
				System.out.println(stringB.toString());
			}
	}
	
	
	private static void buscaDataLeituraConta() throws SQLException, FileNotFoundException{
		Session session = sf.openSession();
//		CCG50.TPRETENC = 0 (não tem retenção) e CCG50.CDCOBR =9 (ROL COMUM) E (CCG03 - CADASTRO E EVENT - DÉBITO).CDCOBR=7 (ROL ESPECIAL)
		PreparedStatement ps = session.connection().prepareStatement(
				" SELECT " +
				" a.DJLEIT " +
				" from CTF11 a , cfe01 b where " +
				" a.amjrefer = ? " +
				" and a.cdunidcom=b.cdunidco2 " +
				" and a.nrciclo=b.nrciclo" +
				" and a.tpevento = 8 ");
		ps.setString(1, "360");
		ResultSet rs = ps.executeQuery();
		StringBuffer stringB = new StringBuffer();
		while (rs.next()) {
			for (int i = 1; i <= 1; i++) {
					stringB.append(rs.getString(i) +"|");
			}
			stringB.append("\n");
			System.out.println(stringB.toString());
		}
	}
	
	


	private static void testeContaEmitidaTace() throws HibernateException, SQLException{
		Session session = sf.openSession();
		PreparedStatement ps = session.connection().prepareStatement("select djproces from " +
				"ctf11 where cdunidcom = ? " +
				"and amjrefer = ? " +
				"and nrciclo = ? " +
				"and tpevento=33");
		
		ps.setString(1,"");
		ps.setString(2,"");
		ps.setString(3,"912190");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
		session.close();
	}
	
	
	private static void testeDebitoAutomaticoConta() throws HibernateException, SQLException{
		Session session = sf.openSession();
		PreparedStatement ps = session.connection().prepareStatement("SELECT DTINCL,DTEXCL FROM PCCG0901 WHERE" +
				" NRRGILIG=? ORDER BY DTGRAV DESC, HRTRANS DESC");
		ps.setString(1,"912190");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1));
			System.out.println(rs.getString(2));
		}
		session.close();
	}
	
	private static void testeLigacaoSuprimida() throws HibernateException, SQLException{
		Session session = sf.openSession();
		PreparedStatement ps = session.connection().prepareStatement(
				"SELECT b.STLIGACAO,b.CDCOBR " +
				"FROM PCCG0303 a , CCG03 b " +
				"WHERE a.NRGRUPFAT=b.NRGRUPFAT " +
				"AND a.CDMUNICIP=? " +
				"AND a.NRRGILIG = ?"+
				"AND a.NRRGILIG=b.NRRGILIG ");
		ps.setString(1,"206"); 
		ps.setString(2,"912190");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1));
			System.out.println(rs.getString(2));
		}
		System.out.println("FIM");
		session.close();
	}
	
	private static void testeAcatamento5x() throws HibernateException, SQLException{
		Session session = sf.openSession();
		PreparedStatement ps = session.connection().prepareStatement(
				"Select count(*) from cea01 where cdmunicip= ? " + 
				 " and nrrgilig= ? and amjrefer= ? and tpconta= ? " + 
				 " and nmispec = 'CAPAF' and nmuserid<>'INTERNET' and stacatam in " +
                 "('A','I')");
		ps.setString(1,"350"); 
		ps.setString(2,"912190");
		ps.setString(3,"260");
		ps.setString(4,"6");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
		System.out.println("FIM");
		session.close();
	}
	
	private static void testenRCicloSuperintendencia() throws HibernateException, SQLException{
		Session session = sf.openSession();
		PreparedStatement ps = session.connection().prepareStatement(
				"select nrciclo , cdunidcom , nrrgilig from " +
				"cfe01 where " +
				"nrrgilig = ? ");
		
//		PreparedStatement ps = session.connection().prepareStatement("select * from ccg03 where nrrgilig=? ");
		ps.setString(1,"530935260");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1) +" | "+ rs.getString(2) +"|"+ rs.getString(3));
		}
		session.close();
	}
	
	
	
	
	private static void testeDebitoAutomaticoExtratoConta() throws HibernateException, SQLException{
		Session session = sf.openSession();
		PreparedStatement ps = session.connection().prepareStatement("SELECT B.AMJREFEXT,B.AMJREFER ,B.STEXTRATO "+  
				"FROM CEB36 B  WHERE  B.TPConta =1 AND B.NRRGILIG=191" + 
"				 AND B.AMJREFER =362 AND B.NRSEQCTA2=1");
//				" SELECT B.AMJREFEXT,B.AMJREFER ,B.STEXTRATO " +
//				" FROM CEB36 B where" +
//				" B.TPCTAEXT = 7 "+
//				" AND B.NRRGILIG= 912190 ");
//				" WHERE " +
//				" B.TPCTAEXT = 7 " +
//				" AND B.NRRGILIG= 912190 " +
//				" AND B.AMJREFER = 358" +
//				" AND B.NRSEQCTA2 = 1");
		
//		PreparedStatement ps = session.connection().prepareStatement("select * from ccg03 where nrrgilig=? ");
//		ps.setString(1,"912190");
//		ps.setString(2,"912190");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1) +" | "+ rs.getString(2) +"|"+ rs.getString(3));
		}
		session.close();
	}
	
	
//	  o status do extrato STEXTRATO dirá 0 (ZERO) = ABERTO; 1 = QUITADO (INFOR=CLIENTE); 3 = PAGO (INFOR=BANCO)
	
	
	private static void testeSituacaoConta() throws HibernateException, SQLException{
		Session session = sf.openSession();
		PreparedStatement ps = session.connection().prepareStatement("SELECT STCONTA,STPAGTO FROM EVENT " +
		"WHERE NRRGILIG=912190 AND AMJREFER=360 ");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1));
			System.out.println(rs.getString(2));
		}
		session.close();
	}
	
	private static void teste() throws SQLException, FileNotFoundException{
		Session session = sf.openSession();
//		CCG50.TPRETENC = 0 (não tem retenção) e CCG50.CDCOBR =9 (ROL COMUM) E (CCG03 - CADASTRO E EVENT - DÉBITO).CDCOBR=7 (ROL ESPECIAL)
		PreparedStatement ps = session.connection().prepareStatement("select TPRETENC , CDORGPUB  from ccg50 where NRRGILIG = ? ");
		ps.setString(1, "191");
		ResultSet rs = ps.executeQuery();
		StringBuffer stringB = new StringBuffer();
		while (rs.next()) {
			
			for (int i = 1; i <= 2; i++) {
					stringB.append(rs.getString(i) +"|");
			}
			stringB.append("\n");
			System.out.println(stringB.toString());
		}
	}
	
	
	private static void buscaConta() throws SQLException, FileNotFoundException{
	 try {
			Session session = sf.openSession();
			File f = new File("C:\\rgi.txt");
			FileReader reader = new FileReader(f);
			BufferedReader input = new BufferedReader(reader);
			
			/*DatabaseMetaData meta = session.connection().getMetaData();
			ResultSet rsColumns = meta.getColumns(null, null, "EVENT", null);
			int size = rsColumns.getInt("COLUMN_SIZE");*/
			
			//testar RGi 912190
			String linha;
//				PreparedStatement ps = session.connection().prepareStatement("SELECT amjrefer,djvenc,vlcontag ,TPCONTA ,NRSEQCTA2 ,ISPEC ,STPAGTO , STCORTSUP ,CDCOBR  " +
//						"FROM EVENT WHERE NRRGILIG=? " +
//						"AND STCONTA=0 AND STPAGTO IN (0,4)");
				
				
				PreparedStatement ps = session.connection().prepareStatement("SELECT NRRGILIG, amjrefer,djvenc,vlcontag ,NRSEQCTA2 ,ISPEC ,STPAGTO , STCORTSUP ,CDCOBR, VLAGUA, VLESGOTO, VLDESCONT  " +
				"FROM EVENT WHERE TPCONTA=6 " +
				"AND STCONTA=0 AND STPAGTO IN (0,4)");
				
				ResultSet rs = ps.executeQuery();
					StringBuffer stringB = new StringBuffer();
				while (rs.next()) {
					
					for (int i = 1; i <= 5; i++) {
							stringB.append("======>"+rs.getString(i) +"|");
					}
					stringB.append("\n");
					System.out.println(stringB.toString());
				}
			
		} catch (IOException ioe) {
			System.out.println(ioe);
		}   

	}

	private static void getColumnMetada() throws Exception {
		Session session = sf.openSession();
		DatabaseMetaData meta = session.connection().getMetaData();

		// tabela RDMS

		ResultSet rsColumns = meta.getColumns(null, null, "CFE06", null);
		
		System.out.println("********* colunas *********");

		while (rsColumns.next()) {

			String columnName = rsColumns.getString("COLUMN_NAME");

			System.out.println("column name=" + columnName);

			String columnType = rsColumns.getString("TYPE_NAME");

			System.out.println("type:" + columnType);

			int size = rsColumns.getInt("COLUMN_SIZE");

			System.out.println("size:" + size);

			int nullable = rsColumns.getInt("NULLABLE");

			if (nullable == DatabaseMetaData.columnNullable) {

				System.out.println("nullable true");

			} else {

				System.out.println("nullable false");

			}

			int position = rsColumns.getInt("ORDINAL_POSITION");

			System.out.println("position:" + position);
			System.out.println("\n");
		}

	}
	
	

}
