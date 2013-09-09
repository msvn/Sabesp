package com.prime.app.agvirtual.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.CsiDao;
import com.prime.app.agvirtual.to.BancoConveniadoTO;
import com.prime.app.agvirtual.to.TarifaTO;
import com.prime.infra.dao.jpa.GenericRDMSJpaDao;
import com.prime.infra.util.WrapperUtils;

/**
 * Dao responsavel por todas chamadas ao CSI
 * 
 * @author gustavons
 * 
 */
@Repository
public class CsiDaoImpl extends GenericRDMSJpaDao<BancoConveniadoTO, Long> implements CsiDao {

	private static final Logger agvlogger = LoggerFactory.getLogger(CsiDaoImpl.class);

	public ArrayList<BancoConveniadoTO> consultarBancoConveniado() {

		ArrayList lista  = new ArrayList<BancoConveniadoTO>();
		
		String sqlQuery = "select cdbanco,nmbanco,nmrazsoc,tpcobranc , dsurl from ctb51 where stdebban='A' order by 2";
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BancoConveniadoTO banco = new BancoConveniadoTO();
				// Se tpCobranc  != 1 mostra asterisco no frontend
				if(! rs.getString("TPCOBRANC").equals("1")){
					banco.setDebitoautomatico(true);
				}
				banco.setCdBancoConveniado(rs.getInt("CDBANCO"));
				banco.setNmBancoConveniado(rs.getString("NMBANCO"));
				banco.setLinkPaginaBanco(rs.getString("DSURL")); //TODO TESTAR 
				lista.add(banco);
			}
			
		}catch (Exception e) {
			agvlogger.error(e.getMessage());
		}		
		return lista;
	}

	public TarifaTO findTarifaByParams(String... params) {
		TarifaTO tarifaTO = new TarifaTO();
		if(WrapperUtils.isNull(params)){
			return null;
		}
		switch (params.length) {
		// MUNICIPIO;CDGRPSERV;CDSERVCOM;CDSERVEXE
		case 4:
			tarifaTO = getTarifaByMunicipioGRPServServComServExe(params);			
			break;
		default:
			break;
		}
		return tarifaTO;
	}

	private TarifaTO getTarifaByMunicipioGRPServServComServExe(String... params) {		
		TarifaTO retorno = new TarifaTO();
		
		// 4 params no minimo: MUNICIPIO;CDGRPSERV;CDSERVCOM;CDSERVEXE
		if (params.length<4) {
			return retorno;
		}else 	if (WrapperUtils.isNull(params[0]) || WrapperUtils.isNull(params[1]) || WrapperUtils.isNull(params[2])
				|| WrapperUtils.isNull(params[3])) {
			return retorno;
		}
		
		// primeira sub lista com parametros para proxima query
		String sql =  " Select B.cdunidco2, B.cdunidco3, B.CDMUNICIP " +
					  " from PCTB2304 A, ctb23 B " +
					  " where A.CDMUNICIP=? AND A.CDMUNICIP=B.CDMUNICIP AND A.CDATCOM=B.CDATCOM";	
		ArrayList<String> lista = findQBEOneTuple(sql, params[0]);

		sql = " Select CDUNIDCO3, CDUNIDCO2, CDGRPSERV, CDSERVCOM, CDSERVEXE, CDPRIORID,QTPARCEL1, SNPRECOFIX, STGERAL1 " +
		  " FROM CTB01 WHERE CDUNIDCO2=? AND CDUNIDCO3=? AND CDGRPSERV=? and CDSERVEXE=?";
	
		String[] listParams = new String[5];
		
		if (WrapperUtils.isNotNull(lista)) {
			
			listParams[0] = lista.get(0);
			listParams[1] = lista.get(1);
			listParams[2] = (params[1]);
			listParams[3] = (params[2]);
			listParams[4] = (params[3]);		

			lista = findQBEOneTuple(sql, listParams);
			
			String precoFix = lista.get(7) == null ? "" : lista.get(7) ;
			
			if (WrapperUtils.isNotNull(precoFix) && 
					 ( precoFix.trim().equalsIgnoreCase(OrcamentoDaoImpl.SERVICO_GRATUITO)  ||
							precoFix.trim().equalsIgnoreCase(OrcamentoDaoImpl.SERVICO_VARIADO) ) ){
				
				if(precoFix.trim().equalsIgnoreCase(OrcamentoDaoImpl.SERVICO_GRATUITO)){
					retorno.setTarifaAgua(OrcamentoDaoImpl.LBL_SERVICO_GRATUITO);
					retorno.setTarifaEsgoto(OrcamentoDaoImpl.LBL_SERVICO_GRATUITO);
				}else {
					retorno.setTarifaAgua(OrcamentoDaoImpl.LBL_SERVICO_VARIADO);
					retorno.setTarifaEsgoto(OrcamentoDaoImpl.LBL_SERVICO_VARIADO);
				}
				return retorno;
			}
			
			sql = " Select CDUNIDCO3, CDUNIDCO2, CDGRPSERV, CDSERVCOM, CDSERVEXE, DTVIGENC, QTPARCEL1, VLSERVCOM " +
			  " FROM CTB81 WHERE CDUNIDCO2=? AND CDUNIDCO3=? AND " +
			  " CDGRPSERV=? AND CDSERVCOM=?  AND CDSERVEXE=? ORDER BY CDSERVEXE,DTVIGENC DESC FETCH FIRST 1 ROW ONLY";
			
			lista = findQBEOneTuple(sql, listParams);
			
			if(WrapperUtils.isNotNull(lista) ){
				retorno.setTarifaAgua(WrapperUtils.parseMoney(lista.get(7)));
				retorno.setTarifaEsgoto(WrapperUtils.parseMoney(lista.get(7)));
			}			
		}

		return retorno;
	}
	
	

}
