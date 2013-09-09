package com.prime.app.agvirtual.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.EnderecoDao;
import com.prime.app.agvirtual.entity.Endereco;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.to.AgenciaTO;
import com.prime.infra.dao.jpa.GenericRDMSJpaDao;

@Repository
public class EnderecoDaoImpl extends GenericRDMSJpaDao<Imovel, Long> implements EnderecoDao {
	
	private static final Logger logger = LoggerFactory.getLogger(EnderecoDaoImpl.class);
	
	public List<Endereco> findByName(Endereco endereco) {
		
		
		return null;
	}

	public Endereco findByEnderecoAgenciaAtendimento(Endereco enderecoPesquisa){

		ResultSet rs = null;
		PreparedStatement ps = null;
		
		Endereco endereco = new Endereco();

		String sqlBaseEndereco =
			"SELECT " +
			"b.cdmunicip, " +
			"c.cdlogradr, " +
			"d.tplograd, " + 
			"d.cdtitlog, " + 
			"d.cdsgprep, " + 
			"d.dsenderec, " + 
			"c.nrenderec, " + 
			"c.cdbairro ," +
			"c.NRTELEF " +
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
			String dsTipoLogradouro = null;
			String dsPreposicaoLogradouro = null;
			String nrEndereco  = null;
			String dsMunicipio = null;
			String dsBairro = null;
			String nrTelefone = null;
			
			
			
			
			ps = getHibernateSession().connection().prepareStatement( sqlBaseEndereco );
			ps.setString(1, enderecoPesquisa.getImovel().getDsRgi());
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				codigoMunicipio = rs.getString(1);
				codigoLogradouro = rs.getString(2);
				tipoLogradouro = rs.getString(3);
				codigoTituloLogradouro = rs.getString(4);
				codigoPreposicaoLogradouro = rs.getString(5);
				dsEndereco = rs.getString(6);
				nrEndereco = rs.getString(7);
				codigoBairro = rs.getString(8);
				nrTelefone = rs.getString(9);
			}
			ps.close();
			rs.close();
			
			String sqlTipoLogradouro = 
				"SELECT " +
				"DSTPLOGAB " +
				"FROM CTB25 " +
				"WHERE TPLOGRAD = ?"; 
			
			ps = getHibernateSession().connection().prepareStatement( sqlTipoLogradouro );
			
			ps.setString(1, tipoLogradouro);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				System.out.println(rs.getString(1));
				dsTipoLogradouro = rs.getString(1);
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
				
				ps = getHibernateSession().connection().prepareStatement( sqlTituloLogradouro );
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
			
			ps = getHibernateSession().connection().prepareStatement( sqlPreposicaoLogradouro );
			ps.setString(1, codigoPreposicaoLogradouro);
			
			rs = ps.executeQuery();
			
			while( rs.next() ){
				System.out.println(rs.getString(1));
				dsPreposicaoLogradouro = rs.getString(1);
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
				
				
				ps = getHibernateSession().connection().prepareStatement( sqlBairro );
				ps.setString(1, codigoMunicipio);
				ps.setString(2, codigoLogradouro);
				
				rs = ps.executeQuery();
				
				while( rs.next() ){
					System.out.println(rs.getString(1));
					codigoBairro = rs.getString(1);
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
				
				ps = getHibernateSession().connection().prepareStatement( sqlBairro );
				ps.setString(1, codigoBairro);
				ps.setString(2, codigoMunicipio);
				
				rs = ps.executeQuery();
				
				while( rs.next() ){
					System.out.println(rs.getString(1));
					dsBairro = rs.getString(1);
				}
				
				rs.close();
				ps.close();
				
				
			}
			
			String queryMunicipio =
				"SELECT " +
				"H.NMMUNICIP " +
				"FROM CTB17 H " +
				"WHERE H.CDMUNICIP=?";
			
			ps = getHibernateSession().connection().prepareStatement( queryMunicipio );
			ps.setString(1, codigoMunicipio);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString(1));
				dsMunicipio = rs.getString(1);
			}			
			
			rs.close();
			ps.close();
			
			StringBuilder sb = new StringBuilder();
			sb.append(dsMunicipio.trim());
			sb.append(" - ");
			sb.append(dsTipoLogradouro.trim());
			sb.append(" ");
			sb.append(dsEndereco.trim());
			sb.append(" ");
			sb.append(nrEndereco.trim());
			
			endereco.setEnderecoCompletoAtendimento( sb.toString() );
			endereco.setNrTelefone(nrTelefone);
		
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		
		return endereco;

	}
	
	public Endereco findByEndereco(Endereco endereco) {
		logger.info("Inicio EnderecoDaoImpl -> findByEndereco()");  long start = (new Date()).getTime();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Endereco enderecoCarregado = new Endereco();
		
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
			"A.CDMUNICIP " +
			"FROM " + 
			"CCG01 A,   CCG05 C,   PCCG0302 G " +  
			"WHERE " +
			"G.CDMUNICIP=? AND G.NRRGILIG=? AND " + 
			"A.CDMUNICIP=G.CDMUNICIP AND A.CDIMOVEL=G.CDIMOVEL AND " + 
			"C.CDMUNICIP=A.CDMUNICIP AND C.CDLOGRADR=A.CDLOGRADR";
		
		String dsTipoLogradouro = "";
		String dsHonorifico = "";
		String dsPreposicao = "";
		String dsEndereco = "";		
		String nmEndereco = "";
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
			pstmt = getHibernateSession().connection().prepareStatement(sqlQueryBaseEndereco);
		
			
			pstmt.setString(1, endereco.getImovel().getCdMunicipio());
			pstmt.setString(2, endereco.getImovel().getDsRgi());
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ){				
				
				dsEndereco = rs.getString("DSENDEREC"); 
				nmEndereco = rs.getString("NRENDEREC");	
				stmtTipoLogradouro = rs.getString("TPLOGRAD");
				stmtTituloHonorifico = rs.getString("CDTITLOG");
				stmtPreposicaoLogradouro = rs.getString("CDSGPREP");
				stmtCdTipoLogradouro = rs.getString("CDLOGRADR");
				stmtCdMunicipio = rs.getString("CDMUNICIP");
				stmtCdBairro = rs.getString("CDBAIRRO");

			}
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);			
			
			String queryTipoLogradouro = 
				"SELECT " +
				"D.DSTPLOGAB " +
				"FROM CTB25 D " +
				"WHERE D.TPLOGRAD=?";
			
			pstmt = getHibernateSession().connection().prepareStatement(queryTipoLogradouro);
			pstmt.setString(1, stmtTipoLogradouro);	
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dsTipoLogradouro = rs.getString(1);
			}
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);
			
			String queryTipoHonorifico =
				"SELECT " +
				"F.SGTITLOG " +
				"FROM CTB28 F " +
				"WHERE F.CDTITLOG=?";
			
			pstmt = getHibernateSession().connection().prepareStatement(queryTipoHonorifico);
			pstmt.setString(1, stmtTituloHonorifico);	
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dsHonorifico = rs.getString(1);
			}
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);			

			String queryPreposicaoLogradouro =
				"SELECT " +
				"E.SGPREPLOG FROM CTB27 E " +
				"WHERE E.CDSGPREP=?";
			
			pstmt = getHibernateSession().connection().prepareStatement(queryPreposicaoLogradouro);
			pstmt.setString(1, stmtPreposicaoLogradouro);
			rs = pstmt.executeQuery();			
			
			while(rs.next()){
				dsPreposicao = rs.getString(1);
			}
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);			
			
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

				pstmt = getHibernateSession().connection().prepareStatement(queryBairroCTB66);
				pstmt.setString(1, stmtCdMunicipio);
				pstmt.setString(2, stmtCdTipoLogradouro);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					
					stmtCdBairro = rs.getString(1);
					
				}
				
				pstmt = null;
				fecharRecurso(pstmt, null, rs);	

			}
			
			String queryBairroCTB30 =
				"SELECT B.NMBAIRRO FROM CTB30 B WHERE B.CDMUNICIP=? AND B.CDBAIRRO=?";	
			
			pstmt = getHibernateSession().connection().prepareStatement(queryBairroCTB30);
			pstmt.setString(1, stmtCdMunicipio);
			pstmt.setString(2, stmtCdBairro);					
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				nmBairro = rs.getString(1);
				
			}
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);	
			
			String queryMunicipio =
				"SELECT " +
				"H.NMMUNICIP " +
				"FROM CTB17 H " +
				"WHERE H.CDMUNICIP=?";
			
			pstmt = getHibernateSession().connection().prepareStatement(queryMunicipio);
			pstmt.setString(1, stmtCdMunicipio);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				nmMunicipio = rs.getString(1);
			}
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);				
			
			StringBuilder sb = new StringBuilder();
			sb.append(dsTipoLogradouro.trim());
			sb.append(" ");
			sb.append(dsHonorifico.trim());
			sb.append(" ");
			sb.append(dsPreposicao.trim());
			sb.append(" ");
			sb.append(dsEndereco.trim());
			sb.append(", ");
			sb.append(nmEndereco.trim());
			sb.append(" ");
			sb.append(nmBairro.trim());

			
			enderecoCarregado.setEnderecoCompleto(sb.toString());
			
			enderecoCarregado.setDsTipoLogradouro(dsTipoLogradouro.trim());
			
			enderecoCarregado.setDsHonorifico(dsHonorifico.trim());
			
			enderecoCarregado.setDsPreposicao(dsPreposicao.trim());
			
			enderecoCarregado.setDsEndereco(dsEndereco.trim());
			
			enderecoCarregado.setNrEndereco(nmEndereco.trim());
			
			enderecoCarregado.setNmBairro(nmBairro.trim());
			
			enderecoCarregado.setNmMunicipio(nmMunicipio.trim());
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);				
			
	
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		
		logger.info("Tempo gasto: " + ((new Date()).getTime() - start));
		
		logger.info("Fim EnderecoDaoImpl -> findByEndereco()");
		
		return enderecoCarregado;
		
	}
	
	public Endereco getEnderecoByRGI(String dsRGI) {
		Endereco endereco = new Endereco();
		try {
			
			String sSQL = 
			" SELECT D.DSENDEREC, B.CDMUNICIP, B.CDIMOVEL, B.cdcliente, B.NRRGILIG, C.CDLOGRADR " +
			" FROM   PCCG0305 A, CCG03 B , PCCG0102 C, CCG05 D " +
			" WHERE  A.NRGRUPFAT=B.NRGRUPFAT AND A.NRRGILIG=B.NRRGILIG AND C.CDMUNICIP=B.CDMUNICIP AND C.CDIMOVEL=B.CDIMOVEL " +
			" AND    C.CDMUNICIP=D.CDMUNICIP AND C.CDLOGRADR=D.CDLOGRADR AND A.NRRGILIG= ? ";
			
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sSQL);
			pstmt.setString(1, dsRGI);
			
			ResultSet rs = pstmt.executeQuery();
								
			while( rs.next() ) {
				

				endereco.setDsEndereco(rs.getString("DSENDEREC"));

//				endereco.setNmEndereco(rs.getString("NMENDERECO"));

				endereco.setCdMunicipio(rs.getString("CDMUNICIP"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		return endereco;
	}
	
	public List<Endereco> getEnderecosByNameAndMunicipio(Endereco enderecoPesquisa){
		
		List<Endereco> listaEndereco = new ArrayList<Endereco>();
		
		try{		

			String sqlLogradrouro =
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
			     "C.CDBAIRRO, " +
			     "a.cdmunicip " +
			     " from pccg0502 a, ctb66 b, pctb3002 c, ccg05 d" +
			     " where a.cdmunicip = " + enderecoPesquisa.getImovel().getCdMunicipio()+
			     " and   a.dsenderec >= '"+ enderecoPesquisa.getNrEndereco().trim() +"'"+ 
			     " and   b.stbairro = 1 " +
			     " and   a.cdmunicip  =b.cdmunicip" +
			     " and   a.cdlogradr  =b.cdlogradr" +
			     " and   b.cdmunicip  =c.cdmunicip" +
			     " and   b.cdbairro   =c.cdbairro" +
			     " and   a.cdmunicip  =d.cdmunicip" +
			     " and   a.cdlogradr  =d.cdlogradr";
			
			Statement stmt = getHibernateSession().connection().createStatement();
			
			ResultSet rs = stmt.executeQuery(sqlLogradrouro);	
			
			while( rs.next() ) {
				
				Endereco endereco = new Endereco();
		
				System.out.println(" cdlogradr : " + rs.getString(1));
				endereco.setCdLogradr( rs.getString(1) );
				
				System.out.println(" dsendereco : " + rs.getString(4));
				endereco.setDsEndereco( rs.getString(4) );
				
				System.out.println(" nmBairro : " + rs.getString(5));	
				endereco.setNmBairro( rs.getString(5) );
				
				System.out.println(" cdmunicip : " + rs.getString(11));	
				endereco.setCdMunicipio( rs.getString(11) );
	
				
				listaEndereco.add(endereco);				
			
			}
			
			fecharRecurso(null, stmt, rs);			
		
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		
		
		
		
		
		return listaEndereco;
	}
	
	public List<Endereco> getEnderecosByMunicipio(Endereco enderecoPesquisa){
		
		List<Endereco> listaEndereco = new ArrayList<Endereco>();

		try {			
			
			String sSQL =
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
				"A.CDMUNICIP =" + enderecoPesquisa.getImovel().getCdMunicipio() +" " +
				"AND " +
				"A.CDMUNICIP=C.CDMUNICIP " + 
				"AND " +
				"A.CDLOGRADR=C.CDLOGRADR"; 				

			
			Statement stmt = getHibernateSession().connection().createStatement();
			
			ResultSet rs = stmt.executeQuery(sSQL);
		
			
			while( rs.next() ) {
				
				Endereco endereco = new Endereco();
				
				endereco.setCdBairro( rs.getString(1) ); 

				endereco.setCdMunicipio( rs.getString(2) );				
				
				endereco.setCdLogradr( rs.getString(3) );
			
				endereco.setDsEndereco( rs.getString(4) );
				
				endereco.setNrEndereco( rs.getString(5) );
				
				listaEndereco.add(endereco);
			
			}			
			
			fecharRecurso(null, stmt, rs);
			
/*			rs.close();
			stmt.clearBatch();
			stmt.close();*/
			
			PreparedStatement pstm = null;			
						
				
			for(Endereco enderecoLocal : listaEndereco) {
			
				if( enderecoLocal.getCdBairro().equals("0") ){
					
					String sqlBairro =
						"SELECT " +
						"I.CDBAIRRO " +
						"FROM " +
						"CTB66 I " +
						"WHERE " +
						"I.CDMUNICIP=? " +
						"AND " +
						"I.CDLOGRADR=? " +
						"AND " +
						"I.STBAIRRO=1";
				
					if( pstm == null)
						pstm = getHibernateSession().connection().prepareStatement(sqlBairro);
					
						
					pstm.setString(1 , enderecoLocal.getCdMunicipio() );
					pstm.setString(2 , enderecoLocal.getCdLogradr() );
					
					
					rs = pstm.executeQuery();
					
					while(rs.next()){						
						
						enderecoLocal.setCdBairro( rs.getString(1) );
						
					}
					
				}
				
			}
			
			fecharRecurso(pstm, stmt, rs);
			
/*			rs.close();
			pstm.clearBatch();
			pstm.close();*/
			
			String sqlBairro =
				"SELECT " +
				"B.NMBAIRRO " +
				"FROM " +
				"CTB30 B " +
				"WHERE " +
				"B.CDMUNICIP=? " +
				"AND " +
				"B.CDBAIRRO=?" ;
				
			pstm = getHibernateSession().connection().prepareStatement(sqlBairro);
			
			
			for(Endereco enderecoLocal : listaEndereco) {
			
				pstm.setString(1 , enderecoLocal.getCdMunicipio() );
				pstm.setString(2 , enderecoLocal.getCdBairro() );
				
				rs = pstm.executeQuery();
				
				while(rs.next()){					
							
					enderecoLocal.setNmBairro( rs.getString(1) );
					
				}
				


			}
			
			fecharRecurso(pstm, stmt, rs);
			
/*			rs.close();
			pstm.clearBatch();
			pstm.close();*/
			
			
			String queryRgi =
				"SELECT " +
				"G.NRRGILIG " +
				"FROM PCCG0302 G " +
				"WHERE G.CDMUNICIP =?";
			
			pstm = getHibernateSession().connection().prepareStatement(queryRgi);
			
			for(Endereco enderecoLocal : listaEndereco) {
				
				pstm.setString(1 , enderecoLocal.getCdMunicipio() );
				
				rs = pstm.executeQuery();
				
				while(rs.next()){					
					Imovel imovel = new Imovel();
					imovel.setDsRgi( rs.getString(1) );
					enderecoLocal.setImovel( imovel );
					
				}
			}
			
			fecharRecurso(pstm, stmt, rs);
			
/*			rs.close();
			pstm.clearBatch();
			pstm.close();*/
			
			


		} catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		
		return listaEndereco;
	}	
	
	public List<Endereco> carregarNumeroEnderecoRGI(Endereco enderecoPesquisa) {
		
		List<Endereco> listaNumeroEnderecoRGI = new ArrayList<Endereco>();
		
		try {
			
			/* RECUPERA O RGI E O NUMERO DO ENDERECO DO LOGRADOURO SELECIONADO */
			String sqlRgi = 
				"select " +
				"a.nrenderec, " +
				"b.NRRGILIG, " +
				"a.DSCOMPLEM " +
				"from pccg0102 a, " +
				"PCCG0302 c, " +
				"ccg03 b " +
				"where a.cdmunicip=? " +
				"and a.cdlogradr=? " +
				"and a.cdimovel = c.cdimovel " +
				"and a.cdmunicip = c.cdmunicip " +
				"and c.nrgrupfat = b.nrgrupfat ";
			
			PreparedStatement pstm = getHibernateSession().connection().prepareStatement(sqlRgi);
			
			pstm.setString(1 , enderecoPesquisa.getCdMunicipio());
			pstm.setString(2 , enderecoPesquisa.getCdLogradr() );
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				
				Endereco endereco = new Endereco();
				Imovel imovel = new Imovel();
				
				System.out.println("nrEndereco " + rs.getString(1));
				endereco.setNrEndereco( rs.getString(1) );
				
				System.out.println("nrRGI " + rs.getString(2));
				imovel.setDsRgi( rs.getString(2) );				
				
				System.out.println("dsComplemento " + rs.getString(3));
				endereco.setDsComplemento( rs.getString(3) );
				
				endereco.setImovel(imovel);				
				
				listaNumeroEnderecoRGI.add( endereco );			
			}
			
			fecharRecurso(pstm, null, rs);
			
			
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		
		return listaNumeroEnderecoRGI;
		
	}

	public Endereco findEnderecoAgencia(AgenciaTO agencia , boolean pesquisaTodasAgencias) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Endereco enderecoCarregado = new Endereco();
		
		/* QUERY BASE DO ENDEREÇO */
		String sqlQueryBaseEndereco = "";
		if(pesquisaTodasAgencias){
			sqlQueryBaseEndereco =
			"SELECT X.CDMUNICIP, X.CDUNIDCOM,X.CDLOGRADR, A.DSENDEREC, C.NRENDEREC, B.DSCOMPLEM, A.TPLOGRAD, A.CDSGPREP," +
			" A.CDTITLOG, C.CDBAIRRO, A.CDLOGRADR, A.CDMUNICIP " +
			"FROM CTB18 X, CCG05 A,PCCG0102 B, CCG01 C WHERE X.TPUNIDCOM=5 " +
			"AND X.CDMUNICIP=? AND X.CDMUNICIP=A.CDMUNICIP AND A.CDLOGRADR=X.CDLOGRADR AND A.CDMUNICIP=B.CDMUNICIP " +
			"AND A.CDLOGRADR=B.CDLOGRADR AND A.CDMUNICIP =C.CDMUNICIP AND B.CDIMOVEL=C.CDIMOVEL AND C.NRENDEREC=X.NRENDEREC";
			
		}else{
			sqlQueryBaseEndereco =
			"SELECT X.CDMUNICIP, X.CDUNIDCOM,X.CDLOGRADR, A.DSENDEREC, C.NRENDEREC, B.DSCOMPLEM, A.TPLOGRAD, A.CDSGPREP," +
			" A.CDTITLOG, C.CDBAIRRO, A.CDLOGRADR, A.CDMUNICIP " +
			"FROM CTB18 X, CCG05 A,PCCG0102 B, CCG01 C WHERE X.TPUNIDCOM=5 " +
			"AND X.CDMUNICIP=? AND X.CDLOGRADR=? AND X.CDMUNICIP=A.CDMUNICIP AND A.CDLOGRADR=X.CDLOGRADR AND A.CDMUNICIP=B.CDMUNICIP " +
			"AND A.CDLOGRADR=B.CDLOGRADR AND A.CDMUNICIP =C.CDMUNICIP AND B.CDIMOVEL=C.CDIMOVEL AND C.NRENDEREC=X.NRENDEREC";
		}

		String dsTipoLogradouro = "";
		String dsHonorifico = "";
		String dsPreposicao = "";
		String dsEndereco = "";		
		String nrEndereco = "";
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
			pstmt = getHibernateSession().connection().prepareStatement(sqlQueryBaseEndereco);

			pstmt.setString(1, agencia.getEndereco().getImovel().getCdMunicipio());
			if(!pesquisaTodasAgencias){
				pstmt.setString(2, agencia.getEndereco().getCdLogradr());
			}
			
			rs = pstmt.executeQuery();
			
		
			if(rs.next()){
				dsEndereco = rs.getString("DSENDEREC");
				nrEndereco = agencia.getNrEndereco();	
				stmtTipoLogradouro = rs.getString("TPLOGRAD");
				stmtTituloHonorifico = rs.getString("CDTITLOG");
				stmtPreposicaoLogradouro = rs.getString("CDSGPREP");
				stmtCdTipoLogradouro = agencia.getEndereco().getCdLogradr();
				stmtCdMunicipio = agencia.getEndereco().getImovel().getCdMunicipio();
				stmtCdBairro = agencia.getCdBairro();
				stmtCdBairro = rs.getString("CDBAIRRO");
			}else{
				throw new Exception();
			}
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);			
			
			String queryTipoLogradouro = 
				"SELECT " +
				"D.DSTPLOGAB " +
				"FROM CTB25 D " +
				"WHERE D.TPLOGRAD=?";
			
			pstmt = getHibernateSession().connection().prepareStatement(queryTipoLogradouro);
			pstmt.setString(1, stmtTipoLogradouro);	
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dsTipoLogradouro = rs.getString(1);
			}
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);
			
			String queryTipoHonorifico =
				"SELECT " +
				"F.SGTITLOG " +
				"FROM CTB28 F " +
				"WHERE F.CDTITLOG=?";
			
			pstmt = getHibernateSession().connection().prepareStatement(queryTipoHonorifico);
			pstmt.setString(1, stmtTituloHonorifico);	
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dsHonorifico = rs.getString(1);
			}
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);			

			String queryPreposicaoLogradouro =
				"SELECT " +
				"E.SGPREPLOG FROM CTB27 E " +
				"WHERE E.CDSGPREP=?";
			
			pstmt = getHibernateSession().connection().prepareStatement(queryPreposicaoLogradouro);
			pstmt.setString(1, stmtPreposicaoLogradouro);
			rs = pstmt.executeQuery();			
			
			while(rs.next()){
				dsPreposicao = rs.getString(1);
			}
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);			
			
			/*String queryBairroCTB66 =
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

				pstmt = getHibernateSession().connection().prepareStatement(queryBairroCTB66);
				pstmt.setString(1, stmtCdMunicipio);
				pstmt.setString(2, stmtCdTipoLogradouro);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					stmtCdBairro = rs.getString(1);
				}

				pstmt = null;
				fecharRecurso(pstmt, null, rs);	
			}*/
			
			String queryBairroCTB30 =
				"SELECT B.NMBAIRRO FROM CTB30 B WHERE B.CDMUNICIP=? AND B.CDBAIRRO=?";	
			
			pstmt = getHibernateSession().connection().prepareStatement(queryBairroCTB30);
			pstmt.setString(1, stmtCdMunicipio);
			pstmt.setString(2, stmtCdBairro);					
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				nmBairro = rs.getString(1);
			}
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);	
			
			String queryMunicipio =
				"SELECT " +
				"H.NMMUNICIP " +
				"FROM CTB17 H " +
				"WHERE H.CDMUNICIP=?";
			
			pstmt = getHibernateSession().connection().prepareStatement(queryMunicipio);
			pstmt.setString(1, stmtCdMunicipio);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				nmMunicipio = rs.getString(1);
			}
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);
			
			//TODO Melhoria 
			
			if (!"".equalsIgnoreCase(dsTipoLogradouro.trim())) {
				enderecoCarregado.setDsTipoLogradouro(dsTipoLogradouro.trim()+ " ");
			}
			if (!"".equalsIgnoreCase(dsHonorifico.trim())) {
				enderecoCarregado.setDsHonorifico(dsHonorifico.trim() + " ");
			}
			if (!"".equalsIgnoreCase(dsPreposicao.trim())) {
				enderecoCarregado.setDsPreposicao(dsPreposicao.trim() + " ");
			}
			if (!"".equalsIgnoreCase(dsEndereco.trim())) {
				enderecoCarregado.setDsEndereco(dsEndereco.trim() + ", ");
			}
			if (!"".equalsIgnoreCase(nrEndereco == null ? "" :nrEndereco.trim())) {
				enderecoCarregado.setNrEndereco(nrEndereco.trim() + " - ");
			}
			if (!"".equalsIgnoreCase(nmBairro.trim())) {
				enderecoCarregado.setNmBairro(nmBairro.trim() + " - ");
			}
			if (!"".equalsIgnoreCase(nmMunicipio.trim())) {
				enderecoCarregado.setNmMunicipio(nmMunicipio.trim()+ ", ");
			}
			enderecoCarregado.setDsUF("SP");
			
			pstmt = null;
			fecharRecurso(pstmt, null, rs);
			return enderecoCarregado;
		}catch (Exception e) {
			Endereco enderecoNaoEncontrado = new Endereco();
			enderecoNaoEncontrado.setDsTipoLogradouro("Endereço não encontrado.");
			return enderecoNaoEncontrado;
		}
	}

	public Endereco findDadosBasicosByEndereco(Endereco endereco) {
		
		return null;
	}


}
