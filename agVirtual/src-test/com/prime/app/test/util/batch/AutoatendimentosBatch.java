package com.prime.app.test.util.batch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import static com.prime.infra.util.WrapperUtils.*;

import com.prime.app.agvirtual.entity.batch.Autoatendimentos;
import com.prime.app.test.util.BatchTools;

public class AutoatendimentosBatch extends BatchTools<Autoatendimentos> {
	public static String TPUNIDCOM_AT_COM = "5";
	public static String TPUNIDCOM_DS_UN = "1";
	private static HashMap<String, String> arrayUnidCom = new HashMap<String, String>();
	private static HashMap<String, String> arrayAtCom = new HashMap<String, String>(); 
	private static HashMap<String, String> arrayMuni = new HashMap<String, String>();
	
	private static String path = "c:/temp/";	
	private static String sql = " Select  autoatend.cd_atendimento," +
										" autoatend.cd_autoat_acessado," +
										" autoatend.CD_ATEND_COMERCIAL, " +
										" to_char(autoatend.DT_INICIO,'DMMYYYYHHMISS')," +
										" to_char(autoatend.DT_FIM,'DMMYYYYHHMISS')," +
										" autoatend.cd_sta_atendimento,  " +
										" motivo.DS_MOTIVO_INSUCESSO, " +
										" autoatend.CD_UN, " +										
										" autoatend.cd_municipio" +
							    " from agv_tab_autoat_acessado autoatend, AGV_TAB_MOTIVO_INSUCESSO motivo" +
							    " Where autoatend.CD_MOTIVO_INSUCESSO = motivo.CD_MOTIVO_INSUCESSO ";
	
	private static String SQL_CSI_MUNI = " SELECT NMMUNICIP FROM CTB17 WHERE CDMUNICIP=? ";
	
	private static String SQL_CSI_TPUNIDCOM = " SELECT NMGERAL FROM CTB18 WHERE TPUNIDCOM=? AND CDUNIDCOM=? ";
	
	public static void main(String[] args) throws Exception{
		List<Autoatendimentos> collectionValues = new ArrayList<Autoatendimentos>();

		AutoatendimentosBatch batch = new AutoatendimentosBatch();
		batch.setConnectionType(ORACLE);
		ResultSet rs = batch.executeSQL(sql);
		ResultSet rs2 = null;
		int i=0;
		
		while(rs.next()){
			i++; if(i>8) break;			
			Autoatendimentos at = new Autoatendimentos();	
			System.out.println("--->"+rs.getString(2));
			// setting fields bellow:
			at.setIdAtendimento(rs.getString(1));
			at.setIdAutoatendimento(rs.getString(2));
			at.setIDAtendimentoComercial(rs.getString(3));			
			at.setDataIniAutoatendimento(rs.getString(4));			
			at.setDataFimAutoatendimento(rs.getString(5));
			at.setStatusAutoatendimentoAGV(rs.getString(6));
			at.setMotivoInsucesso(rs.getString(7));
			at.setIdUnidadeNegocio(rs.getString(8));			
			at.setIdMunicipio(rs.getString(9));
			
			// CSI
			if(isNotNull(at.getIdUnidadeNegocio())){
				at.setDsUnidadeNegocio(findOne(rs2,at.getIdUnidadeNegocio(),batch,TPUNIDCOM_DS_UN));	
			}
				 
			if(isNotNull(at.getIdAtendimento())){
				at.setDsAtendimentocomercial(findOne(rs2,at.getIdAtendimento(),batch,TPUNIDCOM_AT_COM));	
			}
			
			if(isNotNull(at.getIdMunicipio())){
				at.setDsMunicipio(findOne(rs2,at.getIdMunicipio(),batch,""));
			}

			collectionValues.add(at);
		}
		
		rs.close();
//		rs2.close();
		
		batch.setCollectionValues(collectionValues);
		
		batch.run(path);
	}
	
	private static String findOne(ResultSet rs2, String id, AutoatendimentosBatch batch, String tpQuery){
		String ret = "";
		if(tpQuery.equalsIgnoreCase(TPUNIDCOM_DS_UN)){
			ret = arrayUnidCom.get(id);	
		}else if(tpQuery.equalsIgnoreCase(TPUNIDCOM_AT_COM)){
			ret = arrayAtCom.get(id);
		}else{
			ret = arrayMuni.get(id);
		}
		
		if(isNull(ret)){
			try{
				batch.setConnectionType(RDMS);
				
				if(tpQuery.equalsIgnoreCase(TPUNIDCOM_DS_UN)){
					rs2 = batch.executeSQL(SQL_CSI_TPUNIDCOM,TPUNIDCOM_DS_UN,id);
				}else if(tpQuery.equalsIgnoreCase(TPUNIDCOM_AT_COM)){
					rs2 = batch.executeSQL(SQL_CSI_TPUNIDCOM,TPUNIDCOM_AT_COM,id);
				}else{
					rs2 = batch.executeSQL(SQL_CSI_MUNI,id);
				}					
				
				while(rs2.next()){
					if(tpQuery.equalsIgnoreCase(TPUNIDCOM_DS_UN)){
						arrayUnidCom.put(id, rs2.getString(1));	
					}else if(tpQuery.equalsIgnoreCase(TPUNIDCOM_AT_COM)){
						arrayAtCom.put(id, rs2.getString(1));
					}else{
						arrayMuni.put(id, rs2.getString(1));
					}
					ret = rs2.getString(1);
				}
//				rs.close();
				
			}catch (Exception e) {
				e.printStackTrace();
			}

		}
		return ret;
	}
	
}
