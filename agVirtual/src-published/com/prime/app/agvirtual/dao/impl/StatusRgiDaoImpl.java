package com.prime.app.agvirtual.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.StatusRgiDao;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.infra.dao.jpa.GenericRDMSJpaDao;

@Repository
public class StatusRgiDaoImpl extends GenericRDMSJpaDao<Imovel, Long> implements StatusRgiDao {
	
	private static final Logger logger = LoggerFactory.getLogger(StatusRgiDaoImpl.class);
	private static final int CODIGO_GRUPO_SERVICO_SUPRESSAO = 11;
	
	public boolean isLigacaoAtiva(Long rgi) {
		
		boolean isLigacaoAtiva = false;
		
		try {
	        StringBuilder query = new StringBuilder();
	        query.append("SELECT B.STLIGACAO FROM PCCG0305 A, CCG03 B ");
	        query.append("WHERE A.NRRGILIG = ? ");
	        query.append("AND A.NRGRUPFAT=B.NRGRUPFAT ");
	        query.append("AND A.NRRGILIG=B.NRRGILIG ");
	        query.append("AND B.STLIGACAO NOT IN ('C', 'E', 'D', 'M', 'S', 'H')");
			
			PreparedStatement pstm = getHibernateSession().connection().prepareStatement(query.toString());
			
			pstm.setLong(1, rgi);
			
			ResultSet rs = pstm.executeQuery();
			
			isLigacaoAtiva = rs.next();

			fecharRecurso(pstm, null, rs);
		}catch (Exception e) {
			logger.error("Erro ao acessar o DB.");
		}
		
		return isLigacaoAtiva;
	}
	
	public boolean rgiPossuiSupressao(Long rgi) {
		
		boolean rgiPossuiSupressao = false;
		
		try {
	        StringBuilder query = new StringBuilder();
	        query.append("SELECT B.CDMUNICIP, B.CDIMOVEL, B.STLIGACAO, D.STRC, D.DTGERAL ");
	        query.append("FROM PCCG0305 A, CCG03 B, PCCG0601 C, CCG06 D ");
	        query.append("WHERE A.NRRGILIG = ? ");
	        query.append("AND A.NRGRUPFAT=B.NRGRUPFAT ");
	        query.append("AND A.NRRGILIG=B.NRRGILIG ");
	        query.append("AND B.STLIGACAO  NOT IN ('C', 'E', 'D', 'M', 'S', 'H') ");
	        query.append("AND B.CDMUNICIP=C.CDMUNICIP ");
	        query.append("AND B.CDIMOVEL=C.CDIMOVEL ");
	        query.append("AND C.CDGRPSERV = " + CODIGO_GRUPO_SERVICO_SUPRESSAO + " ");
	        query.append("AND C.CDUNIDCO2=D.CDUNIDCO2 ");
	        query.append("AND C.AARCACAT=D.AARCACAT ");
	        query.append("AND C.NRRC=D.NRRC ");
	        query.append("AND C.CDERROSIG=D.CDERROSIG ");
	        query.append("AND D.STRC NOT IN ('K','X','Y')");
			
			PreparedStatement pstm = getHibernateSession().connection().prepareStatement(query.toString());
			
			pstm.setLong(1, rgi);
			
			ResultSet rs = pstm.executeQuery();
			
			rgiPossuiSupressao = rs.next();

			fecharRecurso(pstm, null, rs);
		}catch (Exception e) {
			logger.error("Erro ao acessar o DB.");
		}
		
		return rgiPossuiSupressao;
	}

	public boolean isRgiCortado(Long rgi) {
		
		boolean isRgiCortado = false;
		
		try {
	        StringBuilder query = new StringBuilder();
	        query.append("SELECT B.AMJREFER,B.DJVENC, B.DJCARENGC ");
	        query.append("FROM PCCG0305 A, CCO01 B ");
	        query.append("WHERE A.NRRGILIG=B.NRRGILIG ");
	        query.append("AND B.CDACAOGC='C' ");
	        query.append("AND A.NRRGILIG = ?");
			
			PreparedStatement pstm = getHibernateSession().connection().prepareStatement(query.toString());
			
			pstm.setLong(1, rgi);
			
			ResultSet rs = pstm.executeQuery();
			
			isRgiCortado = rs.next();

			fecharRecurso(pstm, null, rs);
		}catch (Exception e) {
			logger.error("Erro ao acessar o DB.");
		}
		
		return isRgiCortado;
	}

}
