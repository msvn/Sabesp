package com.prime.app.test.util.batch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prime.app.agvirtual.entity.batch.ServicosAcatados;
import com.prime.app.test.util.BatchTools;

public class ServicosAcatadosBatch extends BatchTools<ServicosAcatados> {
	private static String path = "c:/temp/";	
	private static String sql2 = "";
	
	private static String sql =  " Select distinct " +
								 "  	autoat.CD_AUTOATENDIMENTO," +
								 " 		acaoautoat.CD_SERVCOM_CSI," +
								 "		to_char(autoat.DT_FIM,'DMMYYYYHHMISS'), " +
								 "		autoat.CD_UN, " +
								 "		autoat.Cd_Atend_Comercial" +
								 " from agv_tab_autoat_acessado autoat, AGV_TAB_AUTOAT_ACESS_PERGUNTA autoatpergunta, " +
								 "	 	AGV_TAB_CONJ_RESPOSTA conjresp, AGV_TAB_ACAO_AUTOATENDIMENTO acaoautoat, agv_tab_autoat_acao_autoat acao" +
								 " where autoat.CD_AUTOAT_ACESSADO = autoatpergunta.CD_AUTOAT_ACESS_PERGUNTA" +
								 "      and autoatpergunta.CD_CONJUNTO_RESPOSTA = conjresp.CD_CONJUNTO_RESPOSTA" +
								 "      and conjresp.CD_ACAO = acaoautoat.CD_ACAO" +
								 "      and acaoautoat.CD_ACAO = acao.CD_ACAO" +
								 "      and nvl(acao.CD_BANCO,'') = ''";
	
	public static void main(String[] args) throws Exception{
		List<ServicosAcatados> collectionValues = new ArrayList<ServicosAcatados>();

		ServicosAcatadosBatch batch = new ServicosAcatadosBatch();
		batch.setConnectionType(ORACLE);
		ResultSet rs = batch.executeSQL(sql);

		batch.setConnectionType(RDMS);
//		ResultSet rs2 = batch.executeSQL(sql2);

		while(rs.next()){
			ServicosAcatados at = new ServicosAcatados();
			at.setIdAtendimento(rs.getString(1));
			at.setStatusServicoCSI(rs.getString(2));
			at.setDataServicoAcatado(rs.getString(3));
			at.setIdUnidadeNegocio(rs.getString(4));			
			at.setIdAtendimentoComercial(rs.getString(5));


			// CSI
//			at.setDsUnidadeNegocio(rs.getString(1));
//			at.setDsAtendimentoComercial(rs.getString(1));
//			at.setIdServicoAcatado(rs.getString(1));
			
			collectionValues.add(at);
		}
		
//		while(rs2.next()){}			
		
		rs.close();
//		rs2.close();
		batch.setCollectionValues(collectionValues);
		batch.run(path);
	}
}
