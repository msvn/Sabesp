package com.prime.app.agvirtual.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgenciaDao;
import com.prime.app.agvirtual.entity.Endereco;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.to.AgenciaTO;
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.infra.dao.jpa.GenericRDMSJpaDao;

@Repository
public class AgenciaDaoImpl extends GenericRDMSJpaDao<AgenciaTO, Long>  implements AgenciaDao {
	
	private static final Logger logger = LoggerFactory.getLogger(AgenciaDaoImpl.class);
	
	public List<AgenciaTO> findByMunicipio(String cdMunicipio) {

		AgenciaTO agencia;
		List<AgenciaTO> listaAgencia = new ArrayList<AgenciaTO>();
		String sqlQuery = null;
		
		if ("100".equalsIgnoreCase(cdMunicipio)) {
			sqlQuery =
				"SELECT A.NMGERAL, A.CDBAIRRO, A.CDCEP, A.CDDDD, A.CDLOGRADR, A.DSCOMPLEM, A.NRENDEREC, A.NRTELEF, A.CDDDDFAX, " +
				"A.NRTELEFAX, A.DSRAMAL, A.HRINICIO, A.HRFIM, A.HRINIINTV, A.HRFIMINTV, A.CDUNIDCOM, A.CDMUNICIP, A.NMABRUNID, A.TPUNIDCOM, A.STACATAM " +
				" FROM CTB18 A, CCG05 B  "+
				" WHERE TPUNIDCOM=5 AND CDMUNICIP=100 AND A.CDMUNICIP = B.CDMUNICIP AND A.CDLOGRADR = B.CDLOGRADR ORDER BY A.CDUNIDCOM ";
			
		} else {
			sqlQuery =
				" SELECT A.NMGERAL, A.CDBAIRRO, A.CDCEP, A.CDDDD, A.CDLOGRADR, A.DSCOMPLEM, A.NRENDEREC, A.NRTELEF, A.CDDDDFAX, " +
				" A.NRTELEFAX, A.DSRAMAL, A.HRINICIO, A.HRFIM, A.HRINIINTV, A.HRFIMINTV, A.CDUNIDCOM, A.CDMUNICIP, A.NMABRUNID, A.TPUNIDCOM, A.STACATAM " +
				" B.CDSGPREP, B.DSENDEREC, A.NRENDEREC, A.CDBAIRRO  " +
				" FROM CTB18 A, CCG05 B  " +
				" WHERE TPUNIDCOM=5 AND CDUNIDCOM=? AND A.CDMUNICIP = B.CDMUNICIP  " +
				" AND A.CDLOGRADR = B.CDLOGRADR ORDER BY A.CDUNIDCOM ";
		}
		
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, cdMunicipio);
			
			ResultSet rs = pstmt.executeQuery();

			while( rs.next() ){
				agencia = new AgenciaTO();
				Endereco endereco = new Endereco();
				Imovel imovel = new Imovel();

				agencia.setNmGeral(rs.getString("NMGERAL"));
				agencia.setCdCep(rs.getString("CDCEP"));
				agencia.setCdDDD(rs.getString("CDDDD"));
				agencia.setDsComplemento(rs.getString("DSCOMPLEM"));
				agencia.setNrTelefone(rs.getString("NRTELEF"));
				agencia.setCdDDDFax(rs.getString("CDDDDFAX"));
				agencia.setNrTelFax(rs.getString("NRTELEFAX"));
				agencia.setDsRamal(rs.getString("DSRAMAL"));
				agencia.setHoraInicio(formatarHoraMinuto(rs.getString("HRINICIO")));
				agencia.setHoraFim(formatarHoraMinuto(rs.getString("HRFIM")));
				agencia.setHoraInicioIntervalo(rs.getString("HRINIINTV"));
				agencia.setHoraFimIntervalo(rs.getString("HRFIMINTV"));
				agencia.setCdUnidCom(rs.getString("CDUNIDCOM"));
				agencia.setNmAbrUnid(rs.getString("NMABRUNID"));
				endereco.setCdBairro(rs.getString("CDBAIRRO"));
				endereco.setCdLogradr(rs.getString("CDLOGRADR"));
				endereco.setNrEndereco(rs.getString("NRENDEREC"));
				imovel.setCdMunicipio(rs.getString("CDMUNICIP"));
				endereco.setImovel(imovel);
				agencia.setEndereco(endereco);
				listaAgencia.add(agencia);
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}

		return listaAgencia;
	}
	
	private String formatarHoraMinuto(String horaBruta) {
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

	
	public AgenciaTO findAgenciaMaisProximaByRgiRolEspecial(DadosImoveisTO dadosImoveisTO) {
		
		String sql = 
			"SELECT A.NMGERAL, A.CDBAIRRO, D.NMBAIRRO, A.CDCEP, A.CDDDD, A.CDLOGRADR,b.DSENDEREC, A.DSCOMPLEM, A.NRENDEREC, " +
			"A.NRTELEF, A.CDDDDFAX, A.NRTELEFAX, A.DSRAMAL, A.HRINICIO, A.HRFIM, A.HRINIINTV, A.HRFIMINTV, A.CDUNIDCOM, A.CDMUNICIP, " +
			"C.NMGERAL, A.NMABRUNID, A.TPUNIDCOM, A.STACATAM FROM CTB18 A , CCG05 B, CTB17 C, PCTB3002 D WHERE A.TPUNIDCOM=5 and A.CDUNIDCOM = ?  " +
			"and a.cdmunicip = b.cdmunicip and a.cdlogradr = b.cdlogradr AND C.CDMUNICIP=A.CDMUNICIP AND D.CDMUNICIP=A.CDMUNICIP " +
			"AND D.CDBAIRRO=A.CDBAIRRO";
		
		
		logger.info("Query findAgenciaMaisProximaByRgi ==> \n" + sql);
		AgenciaTO agencia = null;
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sql);
			pstmt.setString(1, dadosImoveisTO.getImovel().getUnidadeComercialRolEspecial());
			
			logger.info("Query param 1==> \n" + dadosImoveisTO.getImovel().getUnidadeComercialRolEspecial());
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				agencia =  new AgenciaTO();
				Endereco endereco = new Endereco();
				
				agencia.setNmGeral(rs.getString("NMGERAL"));
				agencia.setCdCep(rs.getString("CDCEP"));
				agencia.setCdDDD(rs.getString("CDDDD"));
				agencia.setDsComplemento(rs.getString("DSCOMPLEM"));
				agencia.setNrTelefone(rs.getString("NRTELEF"));
				agencia.setCdDDDFax(rs.getString("CDDDDFAX"));
				agencia.setNrTelFax(rs.getString("NRTELEFAX"));
				agencia.setDsRamal(rs.getString("DSRAMAL"));
				agencia.setHoraInicio(formatarHoraMinuto(rs.getString("HRINICIO")));
				agencia.setHoraFim(formatarHoraMinuto(rs.getString("HRFIM")));
				agencia.setHoraInicioIntervalo(rs.getString("HRINIINTV"));
				agencia.setHoraFimIntervalo(rs.getString("HRFIMINTV"));
				agencia.setCdUnidCom(rs.getString("CDUNIDCOM"));
				agencia.setNmAbrUnid(rs.getString("NMABRUNID"));
				endereco.setCdBairro(rs.getString("CDBAIRRO"));
				endereco.setCdLogradr(rs.getString("CDLOGRADR"));
				endereco.setNrEndereco(rs.getString("NRENDEREC"));
				endereco.setDsEndereco("DSENDEREC");
				endereco.setNmBairro(rs.getString("NMBAIRRO"));
				agencia.setEndereco(endereco);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Erro busca de findAgenciaMaisProximaByRgi ==>"+e.getMessage());
		}
		return agencia;
	}

	/**
	 * Pesquisa Agencia responsavel dado um RGI rol comum
	 */
	public AgenciaTO findAgenciaMaisProximaByRgiRolComum(DadosImoveisTO dadosImoveisTO) {
		
		String sqlUnidCom = "SELECT B.CDMUNICIP, B.CDUNIDCOM FROM PCCG0305 A,CCG03 B WHERE A.NRRGILIG=? " +
				"AND B.NRGRUPFAT=A.NRGRUPFAT AND B.NRRGILIG=A.NRRGILIG";
		
		String cdunidcom = "";
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlUnidCom);
			pstmt.setString(1, dadosImoveisTO.getImovel().getDsRgi());
			
			logger.info("Query param 1==> \n" + dadosImoveisTO.getImovel().getDsRgi());
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				cdunidcom = rs.getString(2);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		String agencia =
			"SELECT A.NMGERAL, A.CDBAIRRO, D.NMBAIRRO, A.CDCEP, A.CDDDD, A.CDLOGRADR,b.DSENDEREC, A.DSCOMPLEM, A.NRENDEREC, " +
			"A.NRTELEF, A.CDDDDFAX, A.NRTELEFAX, A.DSRAMAL, A.HRINICIO, A.HRFIM, A.HRINIINTV, A.HRFIMINTV, A.CDUNIDCOM, A.CDMUNICIP, " +
			"C.NMGERAL, A.NMABRUNID, A.TPUNIDCOM, A.STACATAM FROM CTB18 A , CCG05 B, CTB17 C, PCTB3002 D WHERE A.TPUNIDCOM=5 and A.CDUNIDCOM = ?  " +
			"and a.cdmunicip = b.cdmunicip and a.cdlogradr = b.cdlogradr AND C.CDMUNICIP=A.CDMUNICIP AND D.CDMUNICIP=A.CDMUNICIP " +
			"AND D.CDBAIRRO=A.CDBAIRRO";
			
		logger.info("Query findAgenciaMaisProximaByRgi ==> \n" + agencia);
			AgenciaTO agencia1 = null;
			try{
				PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(agencia);
				pstmt.setString(1, cdunidcom);
				
				logger.info("Query param 2==> \n" + cdunidcom);
				
				ResultSet rs = pstmt.executeQuery();
				
				while( rs.next() ){
					agencia1 =  new AgenciaTO();
					Endereco endereco = new Endereco();
					
					agencia1.setNmGeral(rs.getString("NMGERAL"));
					agencia1.setCdCep(rs.getString("CDCEP"));
					agencia1.setCdDDD(rs.getString("CDDDD"));
					agencia1.setDsComplemento(rs.getString("DSCOMPLEM"));
					agencia1.setNrTelefone(rs.getString("NRTELEF"));
					agencia1.setCdDDDFax(rs.getString("CDDDDFAX"));
					agencia1.setNrTelFax(rs.getString("NRTELEFAX"));
					agencia1.setDsRamal(rs.getString("DSRAMAL"));
					agencia1.setHoraInicio(formatarHoraMinuto(rs.getString("HRINICIO")));
					agencia1.setHoraFim(formatarHoraMinuto(rs.getString("HRFIM")));
					agencia1.setHoraInicioIntervalo(rs.getString("HRINIINTV"));
					agencia1.setHoraFimIntervalo(rs.getString("HRFIMINTV"));
					agencia1.setCdUnidCom(rs.getString("CDUNIDCOM"));
					agencia1.setNmAbrUnid(rs.getString("NMABRUNID"));
					endereco.setCdBairro(rs.getString("CDBAIRRO"));
					endereco.setCdLogradr(rs.getString("CDLOGRADR"));
					endereco.setNrEndereco(rs.getString("NRENDEREC"));
					endereco.setDsEndereco(rs.getString("DSENDEREC"));
					endereco.setNmBairro(rs.getString("NMBAIRRO"));
					agencia1.setEndereco(endereco);
					
				}
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("Erro busca de findAgenciaMaisProximaByRgiRolComum ==>"+e.getMessage());
			}
			return agencia1;
	}
}
