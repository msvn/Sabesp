package com.prime.app.jpa.test;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;

import com.prime.app.agvirtual.dao.ImovelDao;
import com.prime.app.agvirtual.dao.impl.ImovelDaoImpl;
import com.prime.app.agvirtual.entity.Endereco;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.infra.util.WrapperUtils;

public class RunJPA {
	static SessionFactory sf = new AnnotationConfiguration().configure(
			"/hibernate.cfg.xml").buildSessionFactory();

	public static void main(String[] args) throws Exception {
		/*
		 * Session session = sf.openSession(); Query query =
		 * session.createQuery("FROM ImovelPk c WHERE c.codigoRgi=1597");
		 * List<CEA11> resultado = query.list(); for (CEA11 c : resultado) {
		 * System.out.println(c.getId()); } session.close();
		 */

//		getColumnMetada();
//		getMunicipio();
//		getEnderecoQuery();
//		getEnderecoByNameMunicipio();
//		findImovel();
//		getCategoriaByMunicipio();
//		getTarifaByTipoTarifaCategoriaMunicipio();
//		getTarifaByCategoriaMunicipio();
//		getCronogramaLeitura();
//		getAgencia();
//		getColumnMetada();
		execute();
		
		}

	private static void execute() throws Exception{
//		String sqlQuery = " SELECT c.NMGERAL,c.CDUNIDCOM FROM CTB18 c, CCG03 ccg WHERE c.TPUNIDCOM=5 AND c.CDUNIDCOM=ccg.CDUNIDCOM AND c.CDUNIDCOM=912";

//		String sqlQuery = " SELECT B.CDMUNICIP FROM PCCG0305 A, CCG03 B WHERE A.NRRGILIG=B.NRRGILIG AND A.NRGRUPFAT=B.NRGRUPFAT AND A.NRRGILIG=2720";
		
//		String sqlQuery = "SELECT NMMUNICIP FROM CTB17 WHERE CDMUNICIP=188";
		
		String sqlQuery = " SELECT NMGERAL,CDUNIDCOM FROM CTB18 WHERE TPUNIDCOM=1";
//		SELECT NMGERAL FROM CTB18 WHERE TPUNIDCOM=1 AND CDUNIDCOM=CCG03.CDUNIDCOM
		try{
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
//			pstmt.setString(1, "188");
//			pstmt.setString(2, "1649");
			
			ResultSet rs = pstmt.executeQuery();
			System.out.println("query");
			while( rs.next() ){
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				

			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
			

	private static void findImovel() throws Exception {

		Session session = sf.openSession();

		ImovelDao imovelDao = new ImovelDaoImpl();

		Imovel imovel = new Imovel();
		imovel.setDsRgi("1597");

		Imovel imoveCarregado = imovelDao.findByImovel(imovel);

		session.close();
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
	
	private static void getImovel(){
		String sqlQuery = 
			"SELECT " +
			"CDBAIRRO " +
			"FROM CCG01 " +
			"where CDMUNICIP=? " +
			"and CDIMOVEL=?";

		
		
		try{
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, "188");
			pstmt.setString(2, "1649");
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				System.out.println(rs.getString("CDBAIRRO"));
				

			}
			
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}
	
	private static void getEndereco(){
		
		String sqlQuery = 
			"SELECT " +
/*			"CDLOGRADR, " +
			"NRENDEREC, " +
			"DSCOMPLEM, " +
			"CDCEP, " +*/
			"CDBAIRRO " +
			"FROM " +
			"CCG01 " +
			"WHERE " +
			"CDMUNICIP=? " +
			"AND CDIMOVEL=?"; 
		
		try {
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, "188");
			pstmt.setString(2, "1649");
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				/*enderecoCarregado.setCdLogradr(rs.getString("CDLOGRADR"));
				enderecoCarregado.setNrEnderec(rs.getString("NRENDEREC"));
				enderecoCarregado.setDsComplem(rs.getString("DSCOMPLEM"));
				enderecoCarregado.setCdcep(rs.getString("CDCEP"));*/
				System.out.println(rs.getString("CDBAIRRO"));
			}
			
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}

	private static void getColumnMetada() throws Exception {
		Session session = sf.openSession();
		DatabaseMetaData meta = session.connection().getMetaData();

		// tabela RDMS

		ResultSet rsColumns = meta.getColumns(null, null, "CPU03", null);
		
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
	
	private static void getMunicipio(){
		String sqlQuery =
			"SELECT A.CDMUNICIP, A.NMMUNICIP, A.TPCODIFIC, A.STGERAL, B.DJINICIO, B.DJFIM " +
			"FROM CTB17 A LEFT OUTER JOIN CEB91 B " +
			"ON A.CDMUNICIP=B.CDMUNICIP " +
			"AND B.STREGIST='1' " +
			"WHERE A.CDCLASLOC='1' " +
			"AND A.CDMUNICIP < 60000 " +
			"ORDER BY A.NMMUNICIP ";

		try{
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				System.out.println(rs.getString("CDMUNICIP"));
				System.out.println(rs.getString("NMMUNICIP"));
			}
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}
	
	private static void getAgencia(){
		String cdMunicipio = "100";
		String sqlQuery;
		if ("100".equalsIgnoreCase(cdMunicipio)) {
			sqlQuery =
				"SELECT A.NMGERAL, A.CDBAIRRO, A.CDCEP, A.CDDDD, A.CDLOGRADR, A.DSCOMPLEM, A.NRENDEREC, A.NRTELEF, A.CDDDDFAX, " +
				"A.NRTELEFAX, A.DSRAMAL, A.HRINICIO, A.HRFIM, A.HRINIINTV, A.HRFIMINTV, A.CDUNIDCOM, A.CDMUNICIP, A.NMABRUNID, B.CDATCOM, A.TPUNIDCOM, B.CDMUNICIP, A.STACATAM " +
				"FROM CTB18 A, CTB23 B " +
				"WHERE B.CDATCOM=A.CDUNIDCOM " +
				"AND A.STACATAM='S' " +
				"AND A.TPUNIDCOM=5 " +
				"AND A.CDMUNICIP=? " +
				"AND A.CDMUNICIP=B.CDMUNICIP " +
				"ORDER BY A.NMABRUNID ";
		} else {
			sqlQuery =
				"SELECT A.NMGERAL, A.CDBAIRRO, A.CDCEP, A.CDDDD, A.CDLOGRADR, A.DSCOMPLEM, A.NRENDEREC, A.NRTELEF, A.CDDDDFAX, " +
				"A.NRTELEFAX, A.DSRAMAL, A.HRINICIO, A.HRFIM, A.HRINIINTV, A.HRFIMINTV, A.CDUNIDCOM, A.CDMUNICIP, A.NMABRUNID, B.CDATCOM, A.TPUNIDCOM, B.CDMUNICIP, A.STACATAM " +
				"FROM CTB18 A, CTB23 B " +
				"WHERE B.CDATCOM=A.CDUNIDCOM " +
				"AND A.STACATAM='S' " +
				"AND A.TPUNIDCOM=5 " +
				"AND A.CDUNIDCOM=? " +
				"ORDER BY A.NMABRUNID ";
		}

		try{
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, cdMunicipio);
			ResultSet rs = pstmt.executeQuery();

			while( rs.next() ){
				System.out.print("NMGERAL " + rs.getString("NMGERAL"));
				System.out.print(" CDBAIRRO " + rs.getString("CDBAIRRO"));
				System.out.print(" CDCEP " + rs.getString("CDCEP"));
				System.out.print(" CDDDD " + rs.getString("CDDDD"));
				System.out.print(" CDLOGRADR " + rs.getString("CDLOGRADR"));
				System.out.print(" DSCOMPLEM " + rs.getString("DSCOMPLEM"));
				System.out.print(" NRENDEREC " + rs.getString("NRENDEREC"));
				System.out.print(" NRTELEF " + rs.getString("NRTELEF"));
				System.out.print(" CDDDDFAX " + rs.getString("CDDDDFAX"));
				System.out.print(" NRTELEFAX " + rs.getString("NRTELEFAX"));
				System.out.print(" DSRAMAL " + rs.getString("DSRAMAL"));
				System.out.print(" HRINICIO " + formatarHoraMinuto(rs.getString("HRINICIO")));
				System.out.print(" HRFIM " + formatarHoraMinuto(rs.getString("HRFIM")));
				System.out.print(" HRINIINTV " + rs.getString("HRINIINTV"));
				System.out.print(" HRFIMINTV " + rs.getString("HRFIMINTV"));
				System.out.print(" CDUNIDCOM " + rs.getString("CDUNIDCOM"));
				System.out.print(" CDMUNICIP " + rs.getString("CDMUNICIP"));
				System.out.print(" NMABRUNID " + rs.getString("NMABRUNID"));
				System.out.print(" STACATAM " + rs.getString("STACATAM"));
				System.out.println(" TPUNIDCOM " + rs.getString("TPUNIDCOM"));
			}
			
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}
	
	
	private static void getEnderecoQuery(){
		
		/* QUERY BASE DO ENDEREÇO */
		String sqlQueryBaseEndereco =
			"SELECT "+
			"C.DSENDEREC, " +
			"A.NRENDEREC, " +
			"A.DSCOMPLEM, " +
			"C.TPLOGRAD, " +
			"C.CDSGPREP, " + 
			"C.CDTITLOG, " +
			"A.CDBAIRRO, " +
			"A.CDLOGRADR, " +
			"A.CDMUNICIP, " +
			"A.CDCEP " +
			"FROM " + 
			"CCG01 A,   CCG05 C,   PCCG0302 G " +  
			"WHERE " +
			"G.CDMUNICIP=? AND G.NRRGILIG=? AND " + 
			"A.CDMUNICIP=G.CDMUNICIP AND A.CDIMOVEL=G.CDIMOVEL AND " + 
			"C.CDMUNICIP=A.CDMUNICIP AND C.CDLOGRADR=A.CDLOGRADR";
		
		Endereco endereco = new Endereco();		
		
		String dsTipoLogradouro = "";
		String dsHonorifico = "";
		String dsPreposicao = "";
		String dsEndereco = "";		
		String nmEndreco = "";
		String nmBairro = "";
		String nmMunicipio = "";
		
		
		String stmtTipoLogradouro = "";
		String stmtTituloHonorifico = "";
		String stmtPreposicaoLogradouro = "";
		String stmtCdBairro = "";
		String stmtCdMunicipio = "";
		String stmtCdTipoLogradouro = "";
		

		try{
			/* query base */
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQueryBaseEndereco);
//			pstmt.setString(1, "100");
//			pstmt.setString(2, "1597");
			
			pstmt.setString(1, "100");
			pstmt.setString(2, "52888789");
			ResultSet rs = pstmt.executeQuery();

			System.out.println("**************** Base Endereço ******************");
			
			while( rs.next() ){				
				
				dsEndereco = rs.getString("DSENDEREC"); 
				nmEndreco = rs.getString("NRENDEREC");	
				stmtTipoLogradouro = rs.getString("TPLOGRAD");
				stmtTituloHonorifico = rs.getString("CDTITLOG");
				stmtPreposicaoLogradouro = rs.getString("CDSGPREP");
				stmtCdTipoLogradouro = rs.getString("CDLOGRADR");
				stmtCdMunicipio = rs.getString("CDMUNICIP");
				stmtCdBairro = rs.getString("CDBAIRRO");
				System.out.println(rs.getString("CDCEP"));
				
				

			}
			
			rs.close();			
			pstmt.clearBatch();
			pstmt = null;
			
			System.out.println("**************** Logradouro ******************");
			String queryTipoLogradouro = 
				"SELECT " +
				"D.DSTPLOGAB " +
				"FROM CTB25 D " +
				"WHERE D.TPLOGRAD=?";
			
			pstmt = sf.openSession().connection().prepareStatement(queryTipoLogradouro);
			pstmt.setString(1, stmtTipoLogradouro);	
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dsTipoLogradouro = rs.getString(1);
			}
			
			rs.close();
			pstmt.clearBatch();
			pstmt = null;
			
			System.out.println("**************** Honorifico ******************");
			
			String queryTipoHonorifico =
				"SELECT " +
				"F.SGTITLOG " +
				"FROM CTB28 F " +
				"WHERE F.CDTITLOG=?";
			
			pstmt = sf.openSession().connection().prepareStatement(queryTipoHonorifico);
			pstmt.setString(1, stmtTituloHonorifico);	
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dsHonorifico = rs.getString(1);
			}
			
			rs.close();
			pstmt.clearBatch();
			pstmt = null;
			
			System.out.println("**************** Preposição ******************");			

			String queryPreposicaoLogradouro =
				"SELECT " +
				"E.SGPREPLOG FROM CTB27 E " +
				"WHERE E.CDSGPREP=?";
			
			pstmt = sf.openSession().connection().prepareStatement(queryPreposicaoLogradouro);
			pstmt.setString(1, stmtPreposicaoLogradouro);
			rs = pstmt.executeQuery();			
			
			while(rs.next()){
				dsPreposicao = rs.getString(1);
			}
			
			rs.close();
			pstmt.clearBatch();
			pstmt = null;			
			
			System.out.println("**************** Bairro ******************");
			
			String queryBairroCTB66 =
				"SELECT " +
				"I.CDBAIRRO " +
				"FROM CTB66 I " +
				"WHERE " +
				"I.CDMUNICIP=? " +
				"AND " +
				"I.CDLOGRADR=? " +
				"AND " +
				"I.STBAIRRO=1";	

			
			if( (stmtCdBairro.trim()).equals("0") ){

				pstmt = sf.openSession().connection().prepareStatement(queryBairroCTB66);
				pstmt.setString(1, stmtCdMunicipio);
				pstmt.setString(2, stmtCdTipoLogradouro);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					
					stmtCdBairro = rs.getString(1);
					
				}
				
				rs.close();
				pstmt.clearBatch();
				pstmt = null;

			}
			
			String queryBairroCTB30 =
				"SELECT " +
				"B.NMBAIRRO " +
				"FROM CTB30 B " +
				"WHERE " +
				"B.CDMUNICIP=? " +
				"AND " +
				"B.CDBAIRRO=?";	
			
			pstmt = sf.openSession().connection().prepareStatement(queryBairroCTB30);
			pstmt.setString(1, stmtCdMunicipio);
			pstmt.setString(2, stmtCdBairro);					
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				nmBairro = rs.getString(1);
				
			}
			
			rs.close();
			pstmt.clearBatch();
			pstmt = null;
			
			System.out.println("**************** Municipio ******************");
			
			String queryMunicipio =
				"SELECT " +
				"H.NMMUNICIP " +
				"FROM CTB17 H " +
				"WHERE H.CDMUNICIP=?";
			
			pstmt = sf.openSession().connection().prepareStatement(queryMunicipio);
			pstmt.setString(1, stmtCdMunicipio);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				nmMunicipio = rs.getString(1);
			}
			
			rs.close();
			pstmt.clearBatch();
			pstmt.close();
			
			System.out.println("\n");
			System.out.println("************ Endereço Final ************");
			
			StringBuilder sb = new StringBuilder();
			sb.append(dsTipoLogradouro);
			sb.append(" ");
			sb.append(dsHonorifico);
			sb.append(" ");
			sb.append(dsPreposicao);
			sb.append(" ");
			sb.append(dsEndereco);
			sb.append(" ");
			sb.append(nmEndreco);
			sb.append(" ");
			sb.append(nmBairro);
			sb.append(" ");
			sb.append(nmMunicipio);
			
			System.out.println(sb.toString());
			
			String queryClienteCCG50 =
				"SELECT " +
				"B. " +
				"FROM CCG50 B " +
				"WHERE " +
				"B.CDMUNICIP=? " +
				"AND " +
				"B.CDCLIENTE=?";	
			
			pstmt = sf.openSession().connection().prepareStatement(queryBairroCTB30);
			pstmt.setString(1, stmtCdMunicipio);
			pstmt.setString(2, stmtCdBairro);					
			rs = pstmt.executeQuery();
			
			
			
			
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}		
		
	}
	
	public static void getEnderecoByNameMunicipio(){
		/* QUERY BASE DO ENDEREÇO */
		String sqlQueryBaseEndereco =
			"SELECT "+
			"C.DSENDEREC," +
			"C.CDMUNICIP," +
			"C.CDLOGRADR, " +
			"A.CDBAIRRO " +
			"FROM " + 
			"CCG05 C, CCG01 A  " +  
			"WHERE " +
			"C.CDMUNICIP =? " +
			"AND " +
			"C.DSENDEREC=? " +
			"AND C.CDMUNICIP = A.CDMUNICIP " +
			"AND C.CDLOGRADR=A.CDLOGRADR";
			
		
		try {
			
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQueryBaseEndereco);
			pstmt.setString(1, "492");
			pstmt.setString(2, "ROSA");
			
			String stmtCdBairro = null;
			String stmtCdLogradr = null;
			String stmtCdMunicip = null;
			
			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("********");
			while( rs.next() ){
				
				
				System.out.println("C.DSENDEREC "+ rs.getString(1) );
				System.out.println("CDMUNICIP "+ rs.getString(2) );
				System.out.println("CDLOGRADR "+ rs.getString(3) );
				System.out.println("CDBAIRRO "+ rs.getString(4) );
				
				System.out.println("********");
				
				stmtCdMunicip = rs.getString(2);
				stmtCdLogradr = rs.getString(3);
				stmtCdBairro = rs.getString(4);
			}
			
			rs.close();
			pstmt.clearBatch();
			pstmt.close();
			
			System.out.println("**************** Bairro ******************");
			
			String queryBairroCTB66 =
				"SELECT " +
				"I.CDMUNICIP, " +
				"I.CDBAIRRO " +
				"FROM CTB66 I " +
				"WHERE " +
				"I.CDMUNICIP=? " +
				"AND " +
				"I.CDLOGRADR=? " +
				"AND " +
				"I.STBAIRRO=1";	

			
			

				pstmt = sf.openSession().connection().prepareStatement(queryBairroCTB66);
				pstmt.setString(1, "100");
				pstmt.setString(2, stmtCdLogradr);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					
					System.out.println( rs.getString(1) );
					stmtCdBairro = rs.getString(2);
					
					
				}
				
				rs.close();
				pstmt.clearBatch();
				pstmt = null;

			
			
			String queryBairroCTB30 =
				"SELECT " +
				"B.NMBAIRRO " +
				"FROM CTB30 B " +
				"WHERE " +
				"B.CDMUNICIP=? " +
				"AND " +
				"B.CDBAIRRO=?";	
			
			pstmt = sf.openSession().connection().prepareStatement(queryBairroCTB30);
			pstmt.setString(1, stmtCdMunicip);
			pstmt.setString(2, stmtCdBairro);					
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				System.out.println( rs.getString(1) );
				
			}
			
			rs.close();
			pstmt.clearBatch();
			pstmt = null;
			
			

			
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}			
		
	}
	
	private static void fehcarRecurso(PreparedStatement pstmt, Statement stmt, ResultSet rs) throws SQLException {
		
		if( pstmt != null ) {
			pstmt.clearBatch();
			pstmt.close();
		}
		
		if( stmt != null ){
			stmt.clearBatch();
			stmt.close();
		}
		
		if( rs != null ){
			rs.close();
		}
		
	}
	
	private static void getCategoriaByMunicipio(){
		String sqlQuery =
			"SELECT * FROM CTB14";

		try{
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				System.out.println(rs.getString("CDCATCON"));
				System.out.println(rs.getString("DSCATCON"));
			}
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}
	
	private static void getTarifaByTipoTarifaCategoriaMunicipio(){
		String sqlQuery =
			"SELECT A.CDTARIFA, A.CDCATCON, A.CDTCOBR, A.QTFAIXATE, A.DJVIGENC, A.VLAGUA, A.VLESGOTO, A.CDMUNICIP, A.STREGIST " +
			"FROM CTF01 A " +
			"WHERE A.CDMUNICIP=? " +
			"AND A.CDCATCON=? " +
			"AND A.STREGIST=0 " +
			"ORDER BY A.CDCATCON, A.CDTARIFA, A.CDTCOBR, A.DJVIGENC DESC, A.QTFAIXATE ASC";
		
		try{
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, "100");
			pstmt.setString(2, "4");
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				System.out.print("CDCATCON=" + rs.getString("CDCATCON") + " - ");
				System.out.print(" CDTARIFA=" + rs.getString("CDTARIFA") + " - ");
				System.out.print(" CDTCOBR=" + rs.getString("CDTCOBR") + " - ");
				System.out.print(" DJVIGENC=" + rs.getString("DJVIGENC") + "/");
				System.out.print(WrapperUtils.addDays(Integer.parseInt(rs.getString("DJVIGENC"))) + " - ");
				System.out.print(" QTFAIXATE=" + rs.getString("QTFAIXATE") + " - ");
				System.out.print(" VLAGUA=" + rs.getString("VLAGUA") + " - ");
				System.out.println(" VLESGOTO=" + rs.getString("VLESGOTO"));
			}
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}
	
	private static void getTarifaByCategoriaMunicipio(){
		String sqlQuery =
			"SELECT A.CDTARIFA, A.CDCATCON, A.CDTCOBR, A.DJVIGENC, A.VLAGUA, A.VLESGOTO, A.CDMUNICIP, A.STREGIST " +
			"FROM CTF01 A " +
			"WHERE A.CDMUNICIP=? " +
			"AND A.STREGIST=0 " +
			"ORDER BY A.CDCATCON, A.CDTARIFA, A.CDTCOBR, A.DJVIGENC DESC";
		
		try{
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, "100");
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				System.out.print("CDCATCON=" + rs.getString("CDCATCON") + " - ");
				System.out.print(" CDTARIFA=" + rs.getString("CDTARIFA") + " - ");
				System.out.print(" CDTCOBR=" + rs.getString("CDTCOBR") + " - ");
				System.out.print(" DJVIGENC=" + rs.getString("DJVIGENC") + "/");
				System.out.print(WrapperUtils.addDays(Integer.parseInt(rs.getString("DJVIGENC"))) + " - ");
				System.out.print(" VLAGUA=" + rs.getString("VLAGUA") + " - ");
				System.out.println(" VLESGOTO=" + rs.getString("VLESGOTO"));
			}
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}
	
	private static void getCronogramaLeitura() {
		
//Exibir o cronograma de leitura de um imóvel:
//  - Pegar o grupo de faturamento, municipio e unidade de negócio na CCG03
//    SQL SELECT NRGRUPFAT, CDMUNICIP, CDUNIDCOM FROM CCG03 WHERE NRRGILIG=9999999999
//  - Verificar o cronograma válido, onde o primeiro registro lido indicará o cronograma válido
//	  a partir do ano/mes e ciclo, além de informar o codigo do cronograma CDCRONPROJ 
//	  SQL SELECT CDCRONPROJ FROM PCTF3801 WHERE CDUNIDCOM=CCG03.CDUNIDCOM ORDER BY AMJREFER DESC
//  - Verificar a data de leitura na CTB06 através do codigo do cronograma CDCRONPROJ da CTF38,
//	  no mes de referencia AMJREFER desejado, no grupo de faturamento NRGRUPFAT da CCG03 e tipo
//	  de evento TPEVENTO = 1 que é leitura. 
//	  SQL SELECT * FROM PCTB0611 WHERE CDCRONPROJ=CTB38.CDCRONPROJ AND AMJREFER=999 AND NRGRUPFAT =CCG03.NRGRUPFAT  AND TPEVENTO = 1
//	  A data de leitura está no campo DJCRONPREV no formato juliana.
		
		String NRGRUPFAT = null;
		String CDMUNICIP = null;
		String CDUNIDCOM = null;
		String CDCRONPROJ = null;
		String CDCLIENTE = null;
		String CDCOBR = null; // CDCOBR = 6 ou 7 indica ser RGI  do rol especial
		String sqlQuery =
			"SELECT A.NRGRUPFAT, A.CDMUNICIP, A.CDUNIDCOM, A.CDCOBR, A.CDCLIENTE FROM CCG03 A WHERE A.NRRGILIG=?";
		
		try{
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, "527211400");
			
			ResultSet rs = pstmt.executeQuery();

			while( rs.next() ){
				NRGRUPFAT = rs.getString("NRGRUPFAT");
				CDMUNICIP = rs.getString("CDMUNICIP");
				CDUNIDCOM = rs.getString("CDUNIDCOM");
				CDCOBR = rs.getString("CDCOBR");
				CDCLIENTE = rs.getString("CDCLIENTE");
				System.out.println("NRGRUPFAT = " + rs.getString("NRGRUPFAT"));
				System.out.println("CDMUNICIP = " + rs.getString("CDMUNICIP"));
				System.out.println("CDUNIDCOM = " + rs.getString("CDUNIDCOM"));
				System.out.println("CDCOBR = " + rs.getString("CDCOBR"));
				System.out.println("CDCLIENTE = " + rs.getString("CDCLIENTE"));
			}
			
			String sqlQuery2 = "SELECT G.CDCRONPROJ, G.AMJREFER FROM CTF38 G WHERE G.CDUNIDCOM=? ORDER BY G.AMJREFER DESC";
			PreparedStatement pstmt2 = sf.openSession().connection().prepareStatement(sqlQuery2);
			pstmt2.setString(1, CDUNIDCOM);
			
			ResultSet rs2 = pstmt2.executeQuery();
			while( rs2.next() ){
				CDCRONPROJ = rs2.getString("CDCRONPROJ");
				System.out.println("CDCRONPROJ = " + rs2.getString("CDCRONPROJ"));
				break;
			}
			
			//Verifica se CDCOBR é igual a 6 ou 7, caso seja o cliente é Rol Especial, caso não Rol Comum
			if ("6".equalsIgnoreCase(CDCOBR) || "7".equalsIgnoreCase(CDCOBR)) {
				
			} else {
//Felipe, ROL COMUMna CTB06 o evento é 01 para data de leitura e 02 para 
//data de vencimento, ambos pegam a DTCRONPREV
				String sqlQuery3 =
					"SELECT * FROM CTB06 G WHERE G.CDCRONPROJ=? AND G.NRGRUPFAT=? AND G.TPEVENTO=1 ORDER BY G.AMJREFER ASC";
				PreparedStatement pstmt3 = sf.openSession().connection().prepareStatement(sqlQuery3);
				pstmt3.setString(1, CDCRONPROJ);
				pstmt3.setString(2, NRGRUPFAT);
				
				ResultSet rs3 = pstmt3.executeQuery();
				System.out.println("------- TPEVENTO=1 -------");
				while( rs3.next() ){
					Date dataJuliana = WrapperUtils.addDays(rs3.getInt("DJCRONPREV"));
					SimpleDateFormat teste = new SimpleDateFormat("MMMM/yyyy");
//					System.out.println(teste.format(dataJuliana));
					if(dataJuliana.after(getDataDoDia())){
						System.out.print("Data Furuta");
						System.out.print(" DJCRONPREV " + WrapperUtils.addDays(rs3.getInt("DJCRONPREV")));
						System.out.println(" AMJREFER " + teste.format(WrapperUtils.addMonths(rs3.getInt("AMJREFER"))));
					}
					
				}
			}

			String sqlQuery3 = "SELECT * FROM CTB06 G WHERE G.CDCRONPROJ=? AND G.NRGRUPFAT=? AND G.TPEVENTO=1 ORDER BY G.AMJREFER ASC";
			
			PreparedStatement pstmt3 = sf.openSession().connection().prepareStatement(sqlQuery3);
			pstmt3.setString(1, CDCRONPROJ);
			pstmt3.setString(2, NRGRUPFAT);
			
			ResultSet rs3 = pstmt3.executeQuery();
			System.out.println("------- TPEVENTO=1 -------");
			while( rs3.next() ){
				Date dataJuliana = WrapperUtils.addDays(rs3.getInt("DJCRONPREV"));
				SimpleDateFormat teste = new SimpleDateFormat("MMMM/yyyy");
//				System.out.println(teste.format(dataJuliana));
				if(dataJuliana.after(getDataDoDia())){
					System.out.print("Data Furuta");
					System.out.print(" DJCRONPREV " + WrapperUtils.addDays(rs3.getInt("DJCRONPREV")));
					System.out.println(" AMJREFER " + teste.format(WrapperUtils.addMonths(rs3.getInt("AMJREFER"))));
				}
				
			}
			
			String sqlQuery4 = "SELECT * FROM CTB06 G WHERE G.CDCRONPROJ=? AND G.NRGRUPFAT=? AND G.TPEVENTO=2 ORDER BY G.AMJREFER ASC";
			
			PreparedStatement pstmt4 = sf.openSession().connection().prepareStatement(sqlQuery4);
			pstmt4.setString(1, CDCRONPROJ);
			pstmt4.setString(2, NRGRUPFAT);
			
			ResultSet rs4 = pstmt4.executeQuery();
			System.out.println("------- TPEVENTO=2 -------");
			while( rs4.next() ){
				Date dataJuliana = WrapperUtils.addDays(rs4.getInt("DJCRONPREV"));
				SimpleDateFormat teste = new SimpleDateFormat("MMMM/yyyy");
//				System.out.println(teste.format(dataJuliana));
				if(dataJuliana.after(getDataDoDia())){
					System.out.print("Data Furuta");
					System.out.print(" DJCRONPREV " + WrapperUtils.addDays(rs4.getInt("DJCRONPREV")));
					System.out.println(" AMJREFER " + teste.format(WrapperUtils.addMonths(rs4.getInt("AMJREFER"))));
				}
				
			}
		}catch (Exception e) {
			System.out.println(e);
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}
	
	private static void getEnderecoAgencia() {
	
		String sqlQuery =
			"SELECT " + 
			"A.CDBAIRRO, " +				
			"A.CDMUNICIP, " +
			"C.CDLOGRADR, " +
			"C.DSENDEREC, " + 
			"A.NRENDEREC "+
			"FROM " +
			"CCG01 A, " +
			"CCG05 C " +
			"WHERE " +
			"A.CDMUNICIP =" + "100" +" " +
			"AND " +
			"A.CDMUNICIP=C.CDMUNICIP " + 
			"AND " +
			"A.CDLOGRADR=C.CDLOGRADR " +
			"AND " +
			"A.CDLOGRADR =49200089";
		
		try {
			PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
	
			pstmt.setString(1, "2720");
			
			ResultSet rs = pstmt.executeQuery();
	
			while( rs.next() ){
				System.out.println("CDBAIRRO = " + rs.getString("CDBAIRRO"));
				System.out.println("CDMUNICIP = " + rs.getString("CDMUNICIP"));
				System.out.println("CDLOGRADR = " + rs.getString("CDLOGRADR"));
				System.out.println("DSENDEREC = " + rs.getString("DSENDEREC"));
				System.out.println("NRENDEREC = " + rs.getString("NRENDEREC"));
			}
		}catch (Exception e) {
			System.out.println(e);
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}
	
	private static Date getDataDoDia() {
    	Calendar dataAtual = Calendar.getInstance();
    	dataAtual.setTime(new Date());
    	dataAtual.set(Calendar.HOUR_OF_DAY, 0);
    	dataAtual.set(Calendar.MINUTE, 0);
    	dataAtual.set(Calendar.SECOND, 0);
    	dataAtual.set(Calendar.MILLISECOND, 0);
    	return dataAtual.getTime();
    }
	
	private static String formatarHoraMinuto(String horaBruta) {
		String horaMinutoFormatada = "";
		char posicao;
		for (int j= 0, i = horaBruta.length(); i > 0; i--, j++) {
			posicao = horaBruta.charAt(i-1);
			if (j == 2) {
				if (!"00".equalsIgnoreCase(horaMinutoFormatada)) {
					horaMinutoFormatada = "h" + horaMinutoFormatada + "min";
				} else {
					horaMinutoFormatada = "h";
				}
				
			}
			horaMinutoFormatada =  posicao + horaMinutoFormatada;
		}
    	return horaMinutoFormatada;
    }
	
	
	private static void pesquisaEndereco(){
		
		try{
			
			
			
			String sql = "SELECT A.NMGERAL, A.CDBAIRRO, A.CDCEP, A.CDDDD, A.CDLOGRADR, A.DSCOMPLEM, A.NRENDEREC, A.NRTELEF, A.CDDDDFAX, " +
			"A.NRTELEFAX, A.DSRAMAL, A.HRINICIO, A.HRFIM, A.HRINIINTV, A.HRFIMINTV, A.CDUNIDCOM, A.CDMUNICIP, A.NMABRUNID, A.TPUNIDCOM, A.STACATAM " +
			"FROM CTB18 A " +
			"WHERE A.TPUNIDCOM=5 " +
			"AND A.CDMUNICIP=100 " +
			"ORDER BY A.NMABRUNID ";
			
			PreparedStatement pstmt2 = sf.openSession().connection().prepareStatement(sql);
			
			ResultSet rs2 = pstmt2.executeQuery();
			
			while( rs2.next() ){
				
				System.out.println("AGENCIA===>"+rs2.getString("NMABRUNID") +"| LOGRADOURO===>"+ rs2.getString("CDLOGRADR"));
				String sqlQuery =
//					"SELECT A.DSENDEREC, C.NRENDEREC, B.DSCOMPLEM, A.TPLOGRAD, A.CDSGPREP, A.CDTITLOG, C.CDBAIRRO, A.CDLOGRADR, A.CDMUNICIP " +
//					"FROM CCG05 A, PCCG0102 B, CCG01 C WHERE TPUNIDCOM=5 AND A.CDMUNICIP=100 AND A.CDMUNICIP=B.CDMUNICIP " +
//					"AND A.CDLOGRADR=B.CDLOGRADR AND A.CDMUNICIP=C.CDMUNICIP AND B.CDIMOVEL=C.CDIMOVEL";
				
					"SELECT X.CDMUNICIP, X.CDUNIDCOM,X.CDLOGRADR, A.DSENDEREC, C.NRENDEREC, B.DSCOMPLEM, A.TPLOGRAD, A.CDSGPREP," +
					" A.CDTITLOG, C.CDBAIRRO, A.CDLOGRADR, A.CDMUNICIP " +
					"FROM CTB18 X, CCG05 A,PCCG0102 B, CCG01 C WHERE X.TPUNIDCOM=5 " +
					"AND X.CDMUNICIP=100 AND X.CDLOGRADR="+rs2.getString("CDLOGRADR")+" AND X.CDMUNICIP=A.CDMUNICIP AND A.CDLOGRADR=X.CDLOGRADR AND A.CDMUNICIP=B.CDMUNICIP " +
					"AND A.CDLOGRADR=B.CDLOGRADR AND A.CDMUNICIP =C.CDMUNICIP AND B.CDIMOVEL=C.CDIMOVEL AND C.NRENDEREC=X.NRENDEREC";
				
				System.err.println(sqlQuery);
				PreparedStatement pstmt = sf.openSession().connection().prepareStatement(sqlQuery);
				
				ResultSet rs = pstmt.executeQuery();
				
				while( rs.next() ){
					System.out.println(rs.getString(1) +"|"+ rs.getString(2) +"|"+ rs.getString(3)+"|"+ rs.getString(4) +"|"+ rs.getString(5));
				}
				
//				if(.equals("5428")){
//					System.out.println(rs2.getString(1) +"|"+ rs2.getString(2) +"|"+ rs2.getString(3)+"|"+  +"|"+ rs2.getString(5));
//				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}
}
