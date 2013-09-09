package com.prime.app.jpa.test;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.ibm.icu.text.SimpleDateFormat;
import com.prime.app.agvirtual.dao.CategoriaDao;
import com.prime.app.agvirtual.dao.impl.CategoriaDaoImpl;
import com.prime.app.agvirtual.entity.Categoria;
import com.prime.app.agvirtual.entity.Conta;
import com.prime.app.agvirtual.entity.Endereco;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.service.impl.ContaServiceImpl;

public class RunJPAEndereco {

	static SessionFactory sf = new AnnotationConfiguration().configure(
			"/hibernate.cfg.xml").buildSessionFactory();

	public static void main(String[] args) throws HibernateException, SQLException {
//		getEndereco();
//		pesquisaCdMunicipio();
		//pesquisaTarifa();
		//Imovel imovel  = new Imovel();
		//imovel.setDsRgi("81094");
		//carregarCategoria(imovel);
		getEnderecoAgenciaAtendimento();
	}
	
	static void gerarCodigoBarras(){
		
		ContaServiceImpl contaService = new ContaServiceImpl();
		
		Conta conta = new Conta();
		Imovel imovel  = new Imovel();
		imovel.setDsRgi("00000031");
		
		conta.setTpConta("1");
		conta.setDataReferenciaAnoJuliano("366");
		conta.setImovel( imovel );
		
		conta.setNrSequenciaConta2("1");
		double vl = 40.92;
		conta.setVlTotal(vl);
		
		/*System.out.println( contaService.carregarOCR( conta ) );*/
	}

	public static void getEndereco() {		
		
		List<Endereco> listaEndereco = new ArrayList<Endereco>();

		try {			
			
			/* RECUPERA O LOGRADOURO */
			String sSQL =				
		     "select " +
		     "a.cdlogradr," +
		     "d.nrdiglog," +
		     "d.stlograd," +
		     "a.dsenderec," +
		     "c.nmbairro," +
		     "d.tplograd," +
		     "d.cdtitlog," +
		     "d.cdsgprep," +
		     "b.stbairro," +
		     "C.CDBAIRRO" +
		     " from pccg0502 a, ctb66 b, pctb3002 c, ccg05 d" +
		     " where a.cdmunicip = 100" +
		     " and   a.dsenderec >= 'JOSE'" + 
		     " and   b.stbairro = 1 " +
		     " and   a.cdmunicip  =b.cdmunicip" +
		     " and   a.cdlogradr  =b.cdlogradr" +
		     " and   b.cdmunicip  =c.cdmunicip" +
		     " and   b.cdbairro   =c.cdbairro" +
		     " and   a.cdmunicip  =d.cdmunicip" +
		     " and   a.cdlogradr  =d.cdlogradr";	
				
			
			Statement stmt = sf.openSession().connection().createStatement();
			
			ResultSet rs = stmt.executeQuery(sSQL);
		
			
			while( rs.next() ) {
				
				Endereco endereco = new Endereco();
		
				System.out.println(" cdlogradr : " + rs.getString(1));
				endereco.setDsEndereco( rs.getString(1) );
				
				System.out.println(" dsendereco : " + rs.getString(4));
				endereco.setDsEndereco( rs.getString(4) );
				
				System.out.println(" nmBairro : " + rs.getString(5));	
				endereco.setNrEndereco( rs.getString(5) );

				
				listaEndereco.add(endereco);
				
				
				System.out.println("\n");
				

			
			}
			
						
			rs.close();
			stmt.clearBatch();
			stmt.close();
			
			
			/* RECUPERA O RGI DO LOGRADOURO SELECIONADO */
			String sqlRgi = 
				"select " +
				"a.nrenderec, " +
				"b.NRRGILIG " +
				"from pccg0102 a, " +
				"PCCG0302 c, " +
				"ccg03 b " +
				"where a.cdmunicip=? " +
				"and a.cdlogradr=? " +
				"and a.cdimovel = c.cdimovel " +
				"and a.cdmunicip = c.cdmunicip " +
				"and c.nrgrupfat = b.nrgrupfat ";
			
			PreparedStatement pstm = sf.openSession().connection().prepareStatement(sqlRgi);
			
			pstm.setString(1 , "100" );
			pstm.setString(2 , "16920" );
			
			
			rs = pstm.executeQuery();
			
			while(rs.next()){
				
				System.out.println("nrEndereco " + rs.getString(1));		
				System.out.println("nrRGI " + rs.getString(2));							

				
			}
			
			rs.close();
			stmt.clearBatch();
			stmt.close();


		} catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}
	
	private static void pesquisaCdMunicipio() throws HibernateException, SQLException{
		Session session = sf.openSession();
		PreparedStatement ps = session.connection().prepareStatement("SELECT A.CDMUNICIP , A.cdmunic3, A.NMMUNICIP, A.TPCODIFIC, A.STGERAL " +
			"FROM CTB17 A " +
			"WHERE A.CDCLASLOC='1' " +
			"AND A.CDMUNICIP= 100 ");
			
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1));
			System.out.println(rs.getString(2));
			System.out.println(rs.getString(3));
			System.out.println(rs.getString(4));
		}
		session.close();
	}
	
	
	private static void pesquisaTarifa() throws HibernateException, SQLException{
		Session session = sf.openSession();
		PreparedStatement ps = session.connection().prepareStatement( 
		"select vlservcom,dtvigenc from ctb81 where cdunidco3= ? and cdunidco2= ? " + 
	    " and cdgrpserv= ? and cdservcom=?  and cdservexe= ? "+
	    " and dtvigenc <= ?  order by dtvigenc desc" );
		
		ps.setString(1, "1");
		ps.setString(2, "51");
		ps.setString(3, "31");
		ps.setString(4, "1");
		ps.setString(5, "1");
		Date  data =  new Date(com.prime.infra.util.WrapperUtils.toDate("1-1-57", "dd-MM-yy").getTime());
		
		SimpleDateFormat d =  new SimpleDateFormat("ddMMyyyy");
		//ps.setString(6, d.format(new Date(com.prime.infra.util.WrapperUtils.addDaysToDate(data, +19606).getTime())));
//		ps.setString(6, d.format(new Date(com.prime.infra.util.WrapperUtils.addDaysToDate(data, +19606).getTime())));
		
		ps.setString(6, d.format(new java.util.Date()));
			
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1));
			System.out.println(rs.getString(2));
			break;
		}
		session.close();
	}
	
	public static List<Categoria> carregarCategoria(Imovel imovel){
		
		CategoriaDao dao = new CategoriaDaoImpl();
		List<Categoria> categoriaList = dao.carregarCategoria(imovel);
		
		for (Categoria categoria : categoriaList) {
			System.out.println( categoria.getCdCategoriaConsumo() );
		}
		
		return categoriaList;
		
	}
	
	static void getEnderecoAgenciaAtendimento(){
		
		ResultSet rs = null;
		PreparedStatement ps = null;

		String sqlBaseEndereco =
			"SELECT " +
			"b.cdunidcom, " +
			"b.cdmunicip, " +
			"c.cdlogradr, " +
			"d.tplograd, " +
			"d.cdtitlog, " +
			"d.cdsgprep, " +
			"d.dsenderec, " +
			"c.nrenderec, " +
			"c.cdbairro" +
			" FROM pccg0305 a, ccg03 b, ctb18 c, ccg05 d " +
			" WHERE a.nrrgilig = ?" +
			" and a.nrgrupfat = b.nrgrupfat" + 
			" and a.nrrgilig=b.nrrgilig" +
			" and c.tpunidcom=5 and c.cdunidcom=b.cdunidcom" + 
			" and d.cdmunicip = b.cdmunicip" +
			" and d.cdlogradr = c.cdlogradr";
		
		try {
		
			String tipoLogradouro = null;
			String codigoTituloLogradouro = null;
			String codigoPreposicaoLogradouro = null;
			String codigoBairro = null;
			String codigoMunicipio = null;
			String codigoLogradouro = null;
			String dsEndereco = null;
			
			Session session = sf.openSession();
			ps = session.connection().prepareStatement( sqlBaseEndereco );
			ps.setString(1, "2720");
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				codigoMunicipio = rs.getString(2);
				System.out.println(rs.getString(3));
				codigoLogradouro = rs.getString(3);
				System.out.println(rs.getString(4));
				tipoLogradouro = rs.getString(4);
				System.out.println(rs.getString(5));
				codigoTituloLogradouro = rs.getString(5);
				System.out.println(rs.getString(6));
				codigoPreposicaoLogradouro = rs.getString(6);
				System.out.println(rs.getString(7));
				dsEndereco = rs.getString(7);
				System.out.println(rs.getString(8));
				System.out.println(rs.getString(9));
				codigoBairro = rs.getString(9);
				
			}
			ps.close();
			rs.close();
			
			String sqlTipoLogradouro = 
				"SELECT " +
				"DSTPLOGAB " +
				"FROM CTB25 " +
				"WHERE TPLOGRAD = ?"; 
			
			ps = session.connection().prepareStatement( sqlTipoLogradouro );
			
			ps.setString(1, tipoLogradouro);

			
			rs = ps.executeQuery();
			
			while(rs.next()){
				System.out.println(rs.getString(1));
			}
			
			ps.close();
			rs.close();
			
			/* 
			 * se o codigo do logradouro for diferente de zero
			 * então existe um titulo
			 */
			if( !codigoTituloLogradouro.equals("0") ) {

				String sqlTituloLogradouro = 
					"select " +
					"sgtitlog " +
					"from CTB28 " +
					"where cdtitlog = ?";
				
				ps = session.connection().prepareStatement( sqlTituloLogradouro );
				ps.setString(1, codigoTituloLogradouro);
				
				rs = ps.executeQuery();
				
				while(rs.next()){
					System.out.println(rs.getString(1));
				}
				
				rs.close();
				ps.close();
				
			}
			
			String sqlPreposicaoLogradouro = 
				"select" +
				" SGPREPLOG " +
				" from CTB27 " +
				" where cdSGPREP = ?";
			
			ps = session.connection().prepareStatement( sqlPreposicaoLogradouro );
			ps.setString(1, codigoPreposicaoLogradouro);
			
			rs = ps.executeQuery();
			
			while( rs.next() ){
				System.out.println(rs.getString(1));
			}
			
			rs.close();
			ps.close();
			
			/*
			 * obter o bairro se o codigo do bairro for 0
			 * */
			
			if( codigoBairro.equals("0") ) {
				
				String sqlBairro =
					"select CDBAIRRO" +
					" from CTB66 " +
					" where CDMUNICIP=?  AND CDLOGRADR = ?";
				
				
				ps = session.connection().prepareStatement( sqlBairro );
				ps.setString(1, codigoMunicipio);
				ps.setString(2, codigoLogradouro);
				
				rs = ps.executeQuery();
				
				while( rs.next() ){
					System.out.println(rs.getString(1));
				}
				
				rs.close();
				ps.close();
				
			}else {
				String sqlBairro =
					"select " +
					"NMBAIRRO " +
					"from CTB30 " +
					"where " +
					"CDBAIRRO = ? " +
					"AND CDMUNICIP=?";
				
				ps = session.connection().prepareStatement( sqlBairro );
				ps.setString(1, codigoBairro);
				ps.setString(2, codigoMunicipio);
				
				rs = ps.executeQuery();
				
				while( rs.next() ){
					System.out.println(rs.getString(1));
				}
				
				rs.close();
				ps.close();
				
				
			}
			
			String queryMunicipio =
				"SELECT " +
				"H.NMMUNICIP " +
				"FROM CTB17 H " +
				"WHERE H.CDMUNICIP=?";
			
			ps = session.connection().prepareStatement( queryMunicipio );
			ps.setString(1, codigoMunicipio);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString(1));
			}
			
			rs.close();
			ps.close();

			
			session.close();
		
		}catch (Exception e) {
			// TODO: handle exception
		}

		
		
		
	}

}
