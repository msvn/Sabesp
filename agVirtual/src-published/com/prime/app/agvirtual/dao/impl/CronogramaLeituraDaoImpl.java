package com.prime.app.agvirtual.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibm.icu.util.Calendar;
import com.prime.app.agvirtual.dao.CronogramaLeituraDao;
import com.prime.app.agvirtual.to.CronogramaLeituraTO;
import com.prime.infra.dao.jpa.GenericRDMSJpaDao;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

@Repository
public class CronogramaLeituraDaoImpl
	extends GenericRDMSJpaDao<CronogramaLeituraTO, Long>
		implements CronogramaLeituraDao {

	private static final Logger logger = LoggerFactory.getLogger(CronogramaLeituraDaoImpl.class);
	
	/**
	 * {@inheritDoc}
	 */
	public List<CronogramaLeituraTO> findByRgi(String cdRgi) throws Exception {
		logger.debug("Inicio metodo CronogramaLeituraDaoImpl -> findByRgi");
		long startTime = System.currentTimeMillis();
		
		List<CronogramaLeituraTO> listaCronograma = new ArrayList<CronogramaLeituraTO>();
		List<CronogramaLeituraTO> listaDataLeituraIntermediaria = new ArrayList<CronogramaLeituraTO>();
		List<CronogramaLeituraTO> listaDataLeituraMensal = new ArrayList<CronogramaLeituraTO>();
		List<CronogramaLeituraTO> listaDataVencimento = new ArrayList<CronogramaLeituraTO>();
		String NRGRUPFAT = null;
		String CDMUNICIP = null;
		String CDUNIDCOM = null;
		String CDCRONPROJ = null;
		String CDCLIENTE = null;
		String CDUNIDCO2 = null;
		String NRCICLO = null;
		String CDCOBR = null; // CDCOBR = 6 ou 7 indica ser RGI  do rol especial

		try {
			String sqlQueryCCG03 =
				"SELECT A.NRGRUPFAT, A.CDMUNICIP, A.CDUNIDCOM, A.CDCOBR, A.CDCLIENTE " +
				"FROM CCG03 A " +
				"WHERE A.NRRGILIG=?";

			logger.info("SQL -> " + sqlQueryCCG03);
			
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQueryCCG03);
			pstmt.setString(1, cdRgi);
			
			ResultSet rs = pstmt.executeQuery();

			if(logger.isDebugEnabled()){logger.debug("Numero de tuplas retornadas: " + countResultSet(rs));}  // log numero tuplas
			
			if(rs.next()){
				NRGRUPFAT = rs.getString("NRGRUPFAT");
				CDMUNICIP = rs.getString("CDMUNICIP");
				CDUNIDCOM = rs.getString("CDUNIDCOM");
				CDCOBR = rs.getString("CDCOBR");
				CDCLIENTE = rs.getString("CDCLIENTE");				
			}
			
//			while( rs.next() ){
//				NRGRUPFAT = rs.getString("NRGRUPFAT");
//				CDMUNICIP = rs.getString("CDMUNICIP");
//				CDUNIDCOM = rs.getString("CDUNIDCOM");
//				CDCOBR = rs.getString("CDCOBR");
//				CDCLIENTE = rs.getString("CDCLIENTE");
//				break;
//			}

			//Verifica se CDCOBR é igual a 6 ou 7, caso seja o cliente é Rol Especial, caso não Rol Comum
			if ("6".equalsIgnoreCase(CDCOBR) || "7".equalsIgnoreCase(CDCOBR)) {
				logger.debug("Executando query rol Especial");
				
				String CFE01 =
					"SELECT A.CDUNIDCO2, A.NRCICLO " +
					"FROM CFE01 A " +
					"WHERE A.STREGIST=0 " +
					"AND A.CDMUNICIP=? " +
					"AND A.CDCLIENTE=? " +
					"AND A.NRRGILIG=? ";
				
				logger.info("SQL -> " + CFE01);
				
				PreparedStatement pstmt3 = getHibernateSession().connection().prepareStatement(CFE01);
				pstmt3.setString(1, CDMUNICIP);
				pstmt3.setString(2, CDCLIENTE);
				pstmt3.setString(3, cdRgi);
				
				ResultSet rs3 = pstmt3.executeQuery();
				
				if(logger.isDebugEnabled()){logger.debug("Numero de tuplas retornadas: " + countResultSet(rs3));}  // log numero tuplas
				
				while( rs3.next() ){
					CDUNIDCO2 = rs3.getString("CDUNIDCO2");
					NRCICLO = rs3.getString("NRCICLO");
					break;
				}
				
				if ("21".equalsIgnoreCase(NRCICLO)) {
					throw new IllegalArgumentException(
							FacesBundleUtil.getInstance().getString("cronogramaleitura.erro.ciclo21"));
				} else if ("22".equalsIgnoreCase(NRCICLO)) {
					throw new IllegalArgumentException(
							FacesBundleUtil.getInstance().getString("cronogramaleitura.erro.ciclo22"));
				} else {
					//Evento 8 para Data de Leitura Mensal
					listaDataLeituraMensal = selectTabelaCTF11(CDUNIDCO2, NRCICLO, "8");
					
					//Evento 1 para Data de Leitura Intermediária
					listaDataLeituraIntermediaria = selectTabelaCTF11(CDUNIDCO2, NRCICLO, "1");
					
					//Evento 16 para Data de Vencimento
					listaDataVencimento = selectTabelaCTF11(CDUNIDCO2, NRCICLO, "16");
					
					//Uni as datas pesquisadas na Tabela CTF11
					listaCronograma =
						unirRolEspecial(listaDataLeituraMensal, listaDataLeituraIntermediaria, listaDataVencimento);
				}
			} else {
				logger.debug("Executando query rol Comum");
				
				String sqlQuery2 =
					"SELECT A.CDCRONPROJ, A.AMJREFER " +
					"FROM CTF38 A " +
					"WHERE A.CDUNIDCOM=? " +
					"ORDER BY A.AMJREFER DESC";

				logger.info(sqlQuery2);
				
				PreparedStatement pstmt2 = getHibernateSession().connection().prepareStatement(sqlQuery2);
				pstmt2.setString(1, CDUNIDCOM);
				
				ResultSet rs2 = pstmt2.executeQuery();
				
				if(rs2.next()){
					CDCRONPROJ = rs2.getString("CDCRONPROJ");
				}
//				while( rs2.next() ){
//					CDCRONPROJ = rs2.getString("CDCRONPROJ");
//					break;
//				}

				//Evento 1 para Data de Leitura
				listaDataLeituraMensal = selectTabelaCTB06(CDCRONPROJ, NRGRUPFAT, "1");
				
				//Evento 2 para Data de Vencimento
				listaDataVencimento = selectTabelaCTB06(CDCRONPROJ, NRGRUPFAT, "2");
				
				//Uni as datas pesquisadas na Tabela CTB06
				listaCronograma = unirRolComum(listaDataLeituraMensal, listaDataVencimento);
			}
			
		}catch (Exception e) {
			if (e.getMessage() == null) {
				throw new IllegalArgumentException("Erro ao acessar o DB.");
			} else {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
		
		long endTime = System.currentTimeMillis();
		if(logger.isDebugEnabled()){
			logger.debug("Tempo total gasto: " + (endTime-startTime));
		}
		
		return listaCronograma;
	}
	
	/**
	 * Realiza a pesquisa na tabela CTB06.
	 * @param CDCRONPROJ String
	 * @param NRGRUPFAT String
	 * @param TPEVENTO String
	 * @return List<CronogramaLeituraTO>
	 */
	private List<CronogramaLeituraTO> selectTabelaCTB06(String CDCRONPROJ, String NRGRUPFAT, String TPEVENTO) {
		
		List<CronogramaLeituraTO> listaCronograma = new ArrayList<CronogramaLeituraTO>();
		CronogramaLeituraTO cronogramaLeitura = new CronogramaLeituraTO();
		
		try{
			String sqlQuery =
				"SELECT A.DJCRONPREV, A.AMJREFER " +
				"FROM CTB06 A " +
				"WHERE A.CDCRONPROJ=? " +
				"AND A.NRGRUPFAT=? " +
				"AND A.TPEVENTO=? " +
				"ORDER BY A.AMJREFER ASC";
			PreparedStatement pstmt =  getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, CDCRONPROJ);
			pstmt.setString(2, NRGRUPFAT);
			pstmt.setString(3, TPEVENTO);
			
			ResultSet rs = pstmt.executeQuery();
			while( rs.next() ){
				Date dataJuliana = WrapperUtils.addDays(rs.getInt("DJCRONPREV"));
				if(dataJuliana.after(getDataDoDia())){
					cronogramaLeitura = new CronogramaLeituraTO();
					cronogramaLeitura.setDataLeituraMensal(dataJuliana);
					cronogramaLeitura.setMesReferencia(WrapperUtils.addMonths(rs.getInt("AMJREFER")));
					listaCronograma.add(cronogramaLeitura);
				}
			}
		} catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		return listaCronograma;
	}

	/**
	 * Realiza a pesquisa na tabela CTF11.
	 * @param CDUNIDCO2 String
	 * @param NRCICLO String
	 * @param TPEVENTO String
	 * @return List<CronogramaLeituraTO>
	 */
	private List<CronogramaLeituraTO> selectTabelaCTF11(String CDUNIDCO2, String NRCICLO, String TPEVENTO) {
		logger.debug("Inicio metodo CronogramaLeituraDaoImpl -> selectTabelaCTF11");
		
		List<CronogramaLeituraTO> listaCronograma = new ArrayList<CronogramaLeituraTO>();
		CronogramaLeituraTO cronogramaLeitura = new CronogramaLeituraTO();
		
		try {
			String sqlQuery =
				"SELECT A.DJLEIT, A.AMJREFER " +
				"FROM CTF11 A " +
				"WHERE A.CDUNIDCOM=? " +
				"AND A.NRCICLO=? " +
				"AND A.TPEVENTO=?";
			
			logger.info("SQL -> " + sqlQuery);
			
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, CDUNIDCO2);
			pstmt.setString(2, NRCICLO);
			pstmt.setString(3, TPEVENTO);
			
			logger.debug("Parametros: CDUNIDCO2=" + CDUNIDCO2 + ", NRCICLO=" + NRCICLO + ", TPEVENTO=" + TPEVENTO);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(logger.isDebugEnabled()){logger.debug("Numero de tuplas retornadas: " + countResultSet(rs));}  // log numero tuplas
			
			while( rs.next() ){
				Date dataJuliana = WrapperUtils.addDays(rs.getInt("DJLEIT"));
				if(dataJuliana.after(getDataDoDia())){
					cronogramaLeitura = new CronogramaLeituraTO();
					cronogramaLeitura.setDataLeituraMensal(dataJuliana);
					cronogramaLeitura.setMesReferencia(WrapperUtils.addMonths(rs.getInt("AMJREFER")));
					listaCronograma.add(cronogramaLeitura);
				}
			}
		} catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		return listaCronograma;
	}
	
	/**
	 * Realiza a junção das datas para da pesquisa de Rol Comum.
	 * @param listaDataLeituraMensal List<CronogramaLeituraTO>
	 * @param listaDataVencimento List<CronogramaLeituraTO>
	 * @return List<CronogramaLeituraTO>
	 */
	private List<CronogramaLeituraTO> unirRolComum(
			List<CronogramaLeituraTO> listaDataLeituraMensal,
			List<CronogramaLeituraTO> listaDataVencimento) {

		for (CronogramaLeituraTO cronogramaMensal : listaDataLeituraMensal) {
			for (CronogramaLeituraTO cronogramaVencimento : listaDataVencimento) {
				if (cronogramaMensal.getMesReferencia().equals(cronogramaVencimento.getMesReferencia())) {
					cronogramaMensal.setDataVencimento(cronogramaVencimento.getDataLeituraMensal());
					break;
				}
			}
		}
		return listaDataLeituraMensal;
	}
	
	/**
	 * Realiza a junção das datas para da pesquisa de Rol Especial.
	 * @param listaDataLeituraMensal List<CronogramaLeituraTO>
	 * @param listaDataLeituraIntermediaria List<CronogramaLeituraTO>
	 * @param listaDataVencimento List<CronogramaLeituraTO>
	 * @return List<CronogramaLeituraTO>
	 */
	private List<CronogramaLeituraTO> unirRolEspecial(
			List<CronogramaLeituraTO> listaDataLeituraMensal,
			List<CronogramaLeituraTO> listaDataLeituraIntermediaria,
			List<CronogramaLeituraTO> listaDataVencimento) {

		for (CronogramaLeituraTO cronogramaMensal : listaDataLeituraMensal) {
			for (CronogramaLeituraTO cronogramaIntermediaria : listaDataLeituraIntermediaria) {
				if (cronogramaMensal.getMesReferencia().equals(cronogramaIntermediaria.getMesReferencia())) {
					cronogramaMensal.setDataLeituraIntermediaria(cronogramaIntermediaria.getDataLeituraMensal());
					break;
				}
			}
			for (CronogramaLeituraTO cronogramaVencimento: listaDataVencimento) {
				if (cronogramaMensal.getMesReferencia().equals(cronogramaVencimento.getMesReferencia())) {
					cronogramaMensal.setDataVencimento(cronogramaVencimento.getDataLeituraMensal());
					break;
				}
			}
		}
		return listaDataLeituraMensal;
	}

	/**
	 * Obtém a data do dia.
	 * @return Date
	 */
	private Date getDataDoDia() {
    	Calendar dataAtual = Calendar.getInstance();
    	dataAtual.setTime(new Date());
    	dataAtual.set(Calendar.HOUR_OF_DAY, 0);
    	dataAtual.set(Calendar.MINUTE, 0);
    	dataAtual.set(Calendar.SECOND, 0);
    	dataAtual.set(Calendar.MILLISECOND, 0);
    	return dataAtual.getTime();
    }
	
	private int countResultSet(ResultSet res){
		int i = 0;
		try {
			while(res.next()){
				i++;
			}
		} catch (SQLException e) {
			return -1;
		}
		
		return i;
	}
}
