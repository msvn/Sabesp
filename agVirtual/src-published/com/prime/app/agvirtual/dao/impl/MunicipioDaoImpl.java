package com.prime.app.agvirtual.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibm.icu.text.SimpleDateFormat;
import com.prime.app.agvirtual.dao.MunicipioDao;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.infra.dao.jpa.GenericRDMSJpaDao;

@Repository
public class MunicipioDaoImpl extends GenericRDMSJpaDao<Imovel, Long>  implements MunicipioDao {

	private static final Logger logger = LoggerFactory.getLogger(AgenciaDaoImpl.class);
	
	public List<MunicipioTO> findAll() {
		
		MunicipioTO municipioTO;
		List<MunicipioTO> listaMunicipio = new ArrayList<MunicipioTO>();
		
		String sqlQuery = 
			"SELECT A.CDMUNICIP, A.NMMUNICIP, A.TPCODIFIC, A.STGERAL, B.DJINICIO, B.DJFIM " +
			"FROM CTB17 A LEFT OUTER JOIN CEB91 B " +
			"ON A.CDMUNICIP=B.CDMUNICIP " +
			"AND B.STREGIST='1' " +
			"WHERE A.CDCLASLOC='1' " +
			"AND A.CDMUNICIP < 60000 " +
			"ORDER BY A.NMMUNICIP ";

		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				municipioTO = new MunicipioTO();
				municipioTO.setIdMun(rs.getLong("CDMUNICIP"));
				municipioTO.setNome(rs.getString("NMMUNICIP").trim());
				listaMunicipio.add(municipioTO);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return listaMunicipio;
	}
	
	/**
	 * retorna o codigo municipio tarifário
	 */
	public int pesquisaCodMunicipio(String cdMunicipio) {
		String sqlQuery = "select cdmunic3 from ctb17 where cdmunicip= ?";
		
		logger.info("#ContaDaoImpl# -> Método pesquisaCodMunicipio  # \n");
		logger.info("Query ==> \n" + sqlQuery);
		
		int resultado = 0;
		try{
			
			PreparedStatement pstmt =  getHibernateSession().connection().prepareStatement(sqlQuery);
			getHibernateSession().reconnect();
			pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			
			pstmt.setString(1, cdMunicipio);
			ResultSet rs = pstmt.executeQuery();
			while( rs.next() ){
				resultado = rs.getInt(1);
				break;
			}
			logger.info("Query Finalizada com Sucesso\n");
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			logger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro pesquisar municipio , pesquisa de contas em aberto");
		}
		
		return resultado ;
	}
	
	
	/**
	 * Pesquisa preço da tarifa para envio da segunda via pelo correio 
	 * @param element
	 * @param pstmt
	 * @return
	 */
	public double pesquisaTarifaSegundaViaConta(String nrVP , String uNu) {
		
		String sqlQuery = "select vlservcom,dtvigenc from ctb81 where cdunidco3= ? and cdunidco2= ? " + 
	    " and cdgrpserv= ? and cdservcom=?  and cdservexe= ? "+
	    " and dtvigenc <= ?  order by dtvigenc desc";
		
		logger.info("#ContaDaoImpl# -> Método pesquisaTarifaSegundaViaConta  # \n");
		logger.info("Query ==> \n" + sqlQuery);
		
		double resultado = 0;
		try{
			
			PreparedStatement pstmt =  getHibernateSession().connection().prepareStatement(sqlQuery);
			
			getHibernateSession().reconnect();
			pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			
			pstmt.setString(1, nrVP);
			pstmt.setString(2, uNu);
			pstmt.setString(3, "31"); //FIXO
			pstmt.setString(4, "1");  //FIXO
			pstmt.setString(5, "1"); // FIXO
			
			SimpleDateFormat d =  new SimpleDateFormat("ddMMyyyy");
			pstmt.setString(6, d.format(new java.util.Date()));
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				resultado = rs.getDouble(1);
				break;
			}
			
			logger.info("Query Finalizada com Sucesso\n");
			
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			logger.error("Erro consulta municipio tarifa:"+ e.getMessage());
			new IllegalArgumentException("Erro pesquisar situação da conta , pesquisa de contas em aberto");
		}
		
		return resultado;
	}

	/**
	 * 
	 * @param cdMunicipio
	 * @return 
	 * @return
	 */
	public ArrayList<String> pesquisaNrVpUn(String cdAtendimento ,String nrVp ,String nUn) {
		String sqlQuery = "select cdunidco3,cdunidco2 from ctb23 where cdatcom = ?";
		
		logger.info("#ContaDaoImpl# -> Método pesquisaNrVpUn  # \n");
		logger.info("Query ==> \n" + sqlQuery);
		ArrayList<String> listaResult =  new ArrayList<String>();
		
		try{
			
			PreparedStatement pstmt =  getHibernateSession().connection().prepareStatement(sqlQuery);
			getHibernateSession().reconnect();
			pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			
			pstmt.setString(1, cdAtendimento);
			ResultSet rs = pstmt.executeQuery();
			while( rs.next() ){
				nrVp = rs.getString(1);
				listaResult.add(nrVp);
				nUn = rs.getString(2);
				listaResult.add(nUn);
				break;
			}
			logger.info("Query Finalizada com Sucesso\n");
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			logger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro pesquisar municipio nrVP");
		}
		return listaResult;
	}
}
