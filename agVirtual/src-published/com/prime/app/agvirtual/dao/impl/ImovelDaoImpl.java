package com.prime.app.agvirtual.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.ImovelDao;
import com.prime.app.agvirtual.entity.Categoria;
import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.infra.BusinessException;
import com.prime.infra.dao.jpa.GenericRDMSJpaDao;
import com.prime.infra.util.WrapperUtils;

@Repository
public class ImovelDaoImpl extends GenericRDMSJpaDao<Imovel, Long>  implements ImovelDao {

	private static final Logger logger = LoggerFactory.getLogger(ClienteDaoImpl.class);
	
	public Imovel findByRgi(String dsRgi) throws Exception {
		Imovel imovel = new Imovel();
		imovel.setDsRgi(dsRgi);
		return findByImovel(imovel);
	}
	
	public Imovel findByImovel(Imovel imovel) throws Exception {
		
		Imovel imovelCarregado = new Imovel();
		
		String sqlQuery = 
			"SELECT " +
			"B.CDMUNICIP, " +
			"B.CDIMOVEL, " +
			"B.cdcliente, " +
			"B.NRRGILIG ," +
			"B.CDDIAMETR ," +
			"B.CDCPH ," +
			"B.NRGRUPFAT "+
			"FROM PCCG0305 A, CCG03 B "+
			"WHERE A.NRGRUPFAT=B.NRGRUPFAT " +
			"AND A.NRRGILIG=B.NRRGILIG " +
			"AND A.NRRGILIG= ? fetch first 1 row only";
		
		
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, imovel.getDsRgi());
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				imovelCarregado.setCdMunicipio(rs.getString("CDMUNICIP"));
				imovelCarregado.setCdImovel(rs.getString("CDIMOVEL"));
				imovelCarregado.setCdCliente(rs.getString("cdcliente"));
				imovelCarregado.setDsRgi(rs.getString("NRRGILIG"));
				imovelCarregado.setNrGrupoFaturamento(rs.getString("NRGRUPFAT"));
				imovelCarregado.setCdCapacidadeHidrometro(rs.getInt("CDCPH"));
				imovelCarregado.setCdDiametroHidrometro(rs.getInt("CDDIAMETR"));
				
				//pesquisa CodProcessamentoRGI
				pesquisaCodProcessamentoRgi(imovelCarregado,imovel); //FIXME GUSTAVO colocar log
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		   throw new BusinessException("Erro ao acessar o DB.");
		}
		
		return imovelCarregado;
	}

	/**
	 * Pesquisa Codigo Processamento / Chamado na busca padrao de Imovel
	 * @param imovelCarregado
	 * @param imovel 
	 */
	public void pesquisaCodProcessamentoRgi(Imovel imovelCarregado, Imovel imovel) {
		
		String temp = "SELECT " +
		"C.CDCONTFAT " +
		"FROM CCG03 B , CFE01 C " +
		"WHERE "+ 
		"C.CDMUNICIP=B.CDMUNICIP " +
		"AND C.CDCLIENTE=B.CDCLIENTE " +
		"AND B.NRRGILIG= ? " +
		"AND B.CDMUNICIP= ? " +
		"AND B.CDCLIENTE= ? " +
		"AND B.NRGRUPFAT= ? " +
		"AND B.STREGIST=0 ";
		
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(temp);
			
			pstmt.setString(1, imovel.getDsRgi());
			pstmt.setString(2, imovelCarregado.getCdMunicipio());
			pstmt.setString(3, imovelCarregado.getCdCliente());
			pstmt.setString(4, imovelCarregado.getNrGrupoFaturamento());
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				imovelCarregado.setCodigoControleFaturamento(rs.getString("CDCONTFAT"));
			}
		}catch (Exception e) {
		   throw new IllegalArgumentException("Erro ao acessar o DB.");
		}
		
	}

	public List<Imovel> findByName(Imovel imovel) {

		List<Imovel> imovelList = new ArrayList<Imovel>();
		
		Imovel imovelLocal_1 = new Imovel();
		
		imovelLocal_1.setDsEndereco("Rua Ramon Vieira Matos");
		imovelLocal_1.setDsBairro("Vila Maria");
		imovelLocal_1.setDsRgi("912190");
		
		Imovel imovelLocal_2 = new Imovel();
		
		imovelLocal_2.setDsEndereco("Rua Flora");
		imovelLocal_2.setDsBairro("Vila Olimpia");
		imovelLocal_2.setDsRgi("1597");
		
		Imovel imovelLocal_3 = new Imovel();
		
		imovelLocal_3.setDsEndereco("Rua dos Pinheiros");
		imovelLocal_3.setDsBairro("Pinheiros");
		imovelLocal_3.setDsRgi("1597");
		
		imovelList.add(imovelLocal_1);
		imovelList.add(imovelLocal_2);
		imovelList.add(imovelLocal_3);	
		
		return imovelList;
		
	}
	
	
	public List<Categoria> listaCategoriaByRGI(Imovel imovel) {
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		String sqlQuery =
			"SELECT " +
			"CDCATCON " +
			"FROM CCG04 " +
			"WHERE " +
			"NRRGILIG =? " + 
			" AND STREGIST = 1";
		
		try {
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, imovel.getDsRgi());
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				
				Categoria categoria = new Categoria();
				categoria.setCdCategoriaConsumo( rs.getString(1) );
				
				categorias.add(categoria);
				
			}
			
			rs.close();
			pstmt.close();
			
			
		}catch (Exception e) {
		   throw new IllegalArgumentException("Erro ao acessar o DB.");
		}			
		
		return categorias;
	}
	
	// historico - query 1
	/**
	 * Pesquisa todos os imoveis (RGIs) pertencentes ao cliente
	 * 
	 *  return Cliente - Codigos de RGIs
	 */
	public List<String> findAllRGIs(Imovel imovel){
		List<String> listaRgisImoveis = new ArrayList<String>();
		
		String codMunicipio = imovel.getCdMunicipio();
		String codCliente = imovel.getCdCliente();
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			
			/**
			 *  busca todos imoveis (Rgis) para o cliente
			 */
			
			// TABELAS
			// cfe01 -> faturamento
			String sqlQuery = " SELECT NRRGILIG,STREGIST,CDMUNICIP,CDCLIENTE,CDCONTFAT " +
					   		  " FROM cfe01 " +  
					   		  " WHERE cdmunicip=? and cdcliente=? "+ //and cdcontfat=?" +
					   		  " AND stregist=0 ";  // PQ E VALIDO
			
					   		// query para encontrar todos os rgis filhos, do estado de SP  
							// + " WHERE cdmunicip=100 and cdcontfat > 1 and stregist=0 ";
			
			logger.info("Query ==> \n" + sqlQuery);
			
			ps = getHibernateSession().connection().prepareStatement(sqlQuery);
			
			ps.setString(1, codMunicipio); 
			ps.setString(2, codCliente);   
			
			logger.info("Query param 1 ==> " + codMunicipio); 
			logger.info("Query param 2 ==> " + codCliente);   

			String dsRgi = "";

			rs = ps.executeQuery();
			while (rs.next()) {
				if(WrapperUtils.isNull(rs.getString(1))) continue;
				dsRgi = rs.getString(1);
				listaRgisImoveis.add(dsRgi);
			}

			logger.info("Numbero de imoveis encontrados: " +listaRgisImoveis.size());
		}catch(Exception e){
			// TODO - throw system exception
			e.printStackTrace();
			logger.error("Erro ao executar findAllRGIs", e);
		}finally{
			fecharRecurso(ps, null, rs);
		}
		
		return listaRgisImoveis;
	}

	/**
	 * Verifica se o Rgi em questão é do rol especial 
	 */
	public boolean verificaRgiRolEspecial(Imovel imovel) {
		PreparedStatement ps = null;
		ResultSet rs = null;		
		
		String sql = "SELECT C.CDUNIDCO3,C.CDCONTFAT ,B.CDCOBR FROM PCCG0305 A,CCG03 B,CFE01 C WHERE  A.NRRGILIG = ? AND B.NRGRUPFAT=A.NRGRUPFAT " +
				" AND B.NRRGILIG=A.NRRGILIG AND C.CDMUNICIP=B.CDMUNICIP AND C.CDCLIENTE=B.CDCLIENTE AND C.STREGIST = '0' AND B.CDCOBR = '6' ";
		
		logger.info("Query verificaRgiRolEspecial ");
		logger.info("Query --->"+ sql );
		try{
			ps = getHibernateSession().connection().prepareStatement(sql);
			ps.setString(1, imovel.getDsRgi());
			
			logger.info("Query param 1 ==> " + imovel.getDsRgi());

			rs = ps.executeQuery();
			if (rs.next()) {
				imovel.setUnidadeComercialRolEspecial(rs.getString(1)); // Atendimento Comercial Rol especial pra pesquisa na CTB18 campo CDUNIDCOM 
				imovel.setRolEspecial(true); 
				return true;
			}else{
				imovel.setRolEspecial(false);
			}
		}catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}	
	
	/**
	 * 
	 * @return 
	 * @return
	 */
	public String pesquisaUnidadeNegocio(String rgi) {
		String sqlQuery = "select cdunidcom from ctb23 c where c.nrgilig = ? ";
		
		logger.info("#ContaDaoImpl# -> Método pesquisaUnidadeNegocio  # \n");
		logger.info("Query ==> \n" + sqlQuery);
		ArrayList<String> listaResult =  new ArrayList<String>();
		
		String nrUn = "";
		try{
			
			PreparedStatement pstmt =  getHibernateSession().connection().prepareStatement(sqlQuery);
			getHibernateSession().reconnect();
			pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			
			pstmt.setString(1, rgi);
			ResultSet rs = pstmt.executeQuery();
			while( rs.next() ){
				nrUn = rs.getString(1);
				break;
			}
			logger.info("Query Finalizada com Sucesso\n");
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			logger.error("Erro consulta RGI:"+ e.getMessage());
		}
		return nrUn;
	}

}
