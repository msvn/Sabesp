package com.prime.app.agvirtual.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.ClienteDao;
import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.app.agvirtual.util.Utils;
import com.prime.infra.BusinessException;
import com.prime.infra.dao.jpa.GenericRDMSJpaDao;
import com.prime.infra.util.WrapperUtils;

@Repository
public class ClienteDaoImpl extends GenericRDMSJpaDao<Imovel, Long> implements ClienteDao {
	
	private static final Logger agvlogger = LoggerFactory.getLogger(ClienteDaoImpl.class);

	public Cliente findByCliente(Cliente cliente) {
		
		Cliente clienteCarregado = null;
		
		String sqlQuery = 
			"SELECT " +
			"cc.cdmunicip, " +
			"cc.cdcliente, " +
			"cc.nmcliente, " +
			"cc.dsemail " +
			"FROM " +
			"ccg02 cc "+
			" WHERE cc.cdmunicip = ?" +
			" AND cc.cdcliente = ?";
		
		
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, cliente.getCdMunicipio());
			pstmt.setString(2, cliente.getCdCliente());
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				clienteCarregado = new Cliente();
				
				clienteCarregado.setCdMunicipio(rs.getString("cdmunicip"));
				
				clienteCarregado.setCdCliente(rs.getString("cdcliente"));
				
				clienteCarregado.setNmCLiente(rs.getString("nmcliente"));
				
				clienteCarregado.setDsEmail(rs.getString("dsemail"));
			}
			
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		
		return clienteCarregado;
		
	}
	
	/**
	 * recupera o Numero de Inscrição Estadua do RGI
	 * @param dados
	 * @return
	 */
	public String pesquisaNumeroInscricaoEstadual(DadosImoveisTO dados) {
		
		agvlogger.info("#ContaDaoImpl# -> Método  #pesquisaNumeroInscricaoEstadual# \n");
		String sqlQuery = "SELECT NRINSCEST FROM CCG50 WHERE CDMUNICIP = ? AND "+
	    "CDCLIENTE = ? ";
		
		agvlogger.info("Query ==> \n" + sqlQuery);
		String numeroInscricao = null;
		
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, dados.getImovel().getCdMunicipio());
			pstmt.setString(2, dados.getImovel().getCdCliente());
			agvlogger.info("Query param 1 ==> "+ dados.getImovel().getCdMunicipio());
			agvlogger.info("Query param 2 ==> "+ dados.getImovel().getCdCliente());
			ResultSet rs = pstmt.executeQuery();
			
			int i = 0;
			while( rs.next() ){
				numeroInscricao = rs.getString(1); 
			}
		}catch (Exception e) {
			e.printStackTrace();
			agvlogger.error("Erro consulta pesquisaNumeroInscricaoEstadual" + e.getMessage());
		}
		return numeroInscricao;
	}
	
	/**
	 * recupera o Numero do CPF/CNPJ
	 * @param dados
	 * @return
	 */
	public void pesquisaNumeroCpfCnpjCliente(DadosImoveisTO dados) {
		
		agvlogger.info("#ContaDaoImpl# -> Método  #pesquisaNumeroCpfCnpjCliente# \n");
		
		String sqlQuery = "SELECT A.NMRAZSOC50,A.NRCICLO,A.DSENDERE2,A.CDMUNIC1,A.CDCEP," +
        "B.NMCLIENTE,B.NRDOCTO,B.TPDOCTO, A.NRRGILIG FROM CFE01 A, CCG02 B " +
        "WHERE A.STREGIST=0 AND A.CDMUNICIP = ? AND A.CDCLIENTE = ? " +
        "AND A.CDCONTFAT = ? AND A.CDMUNICIP = B.CDMUNICIP AND A.CDCLIENTE=B.CDCLIENTE";
		
		agvlogger.info("Query ==> \n" + sqlQuery);
		
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, dados.getImovel().getCdMunicipio());
			pstmt.setString(2, dados.getImovel().getCdCliente());
			pstmt.setString(3, dados.getImovel().getCodigoControleFaturamento());
			
			agvlogger.info("Query param 1 ==> "+ dados.getImovel().getCdMunicipio());
			agvlogger.info("Query param 2 ==> "+ dados.getImovel().getCdCliente());
			agvlogger.info("Query param 3 ==> "+ dados.getImovel().getCodigoControleFaturamento());
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				/**
				 *  3 = CPF
					7 = CNPJ
					 
					Segue a lista completa da CTB77
					CTO- -----dsdoctoab------
					   1 SEM DOCUMENTO       
					   2 RG                  
					   3 CPF                 
					   4 COMUNICACAO INTERNA 
					   5 CART. PROFISSIONAL  
					   6 OFICIO              
					   7 CNPJ                
					   8 SOLICITACAO SERVICO 
					   9 DOC                 
					  10 CEI-CAD.ESPECIF.INSS
					  11 JURIDICO            
					  12 PROCON              
					  13 EXPRESSINHO         
					  14 FALE CONOSCO        
					  15 OUVIDORIA           
					  16 PRESIDENCIA         
					  17 PRODECON            
					  18 SSAC - ANTERIOR     
					  20 CFIAA               
					  21 PRODECON-PROC.MUNIC.
					  22 SSAC-PARECER   
				 */
				String tpDoc = rs.getString("TPDOCTO");
				if(tpDoc.equals("3")){
					dados.getCliente().setCpf(rs.getString("NRDOCTO"));
				}else if(tpDoc.equals("7")){
					dados.getCliente().setCnpj(rs.getString("NRDOCTO"));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			agvlogger.error("Erro consulta pesquisaNumeroCpfCnpjCliente" + e.getMessage());
		}
	}	

	// historico - query 2
	/**
	 * Encontra RGI master
	 * 
	 */
	public String pesquisaRgiMaster(List rgis){
		String dsRgiMaster = "";
		
		// unico RGI, so pode ser o master
//		if(rgis.size() == 1) return rgis.get(0);
		
		String rgisStr = Utils.spritList(rgis, ",");
		String dataReferenciaAnoJuliano = Long.toString(WrapperUtils.getMonthsFromDate( WrapperUtils.removeMonthsFromNow(14)));
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			// encontra qual rgi e o master
			// regra -> nrgilib = nrrgifat
			String sqlQuery = "SELECT nrrgilig , nrrgifat  FROM cfe03 " +
							  "WHERE nrrgilig in (" + rgisStr + ") and amjrefer = ? ";
			
			agvlogger.info("Query ==> \n" + sqlQuery);
			
			ps = getHibernateSession().connection().prepareStatement(sqlQuery);
			ps.setString(1, dataReferenciaAnoJuliano);	
			agvlogger.info("Query param 2 ==> " + dataReferenciaAnoJuliano);	
			
			String numRgi = "";
			String rgiFat = "";
			
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("--> nrrgilig="+rs.getString(1)+" --> nrrgifat="+rs.getString(2));
				
				numRgi = rs.getString(1);
				rgiFat = rs.getString(2);
				
				if(numRgi.equals(rgiFat)){
					dsRgiMaster = numRgi;
					agvlogger.info("Encontrado RGI Master : " + dsRgiMaster);
					break;
				}
			}

			if("".equals(dsRgiMaster)){
				throw new BusinessException("RGI master nao encontrado !");
			}
			
			return dsRgiMaster;
		}catch(Exception e){
			// TODO - throw system exception
			e.printStackTrace();
			agvlogger.error("Erro ao executar pesquisaRgisPorCodCliente", e);
		}finally{
			fecharRecurso(ps, null, rs);
		}
		
		return dsRgiMaster;
	}	
	
	
	

}
