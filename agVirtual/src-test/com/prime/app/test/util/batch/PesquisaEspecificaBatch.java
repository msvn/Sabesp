package com.prime.app.test.util.batch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prime.app.agvirtual.entity.batch.PesquisaEspecifica;
import com.prime.app.test.util.BatchTools;

public class PesquisaEspecificaBatch extends BatchTools<PesquisaEspecifica> {
	private static String path = "c:/temp/";	
	private static String sql = " select  " +
								"	pesqexibida.cd_atendimento," +
								"	pesqexibida.cd_pesquisa,        " +
								"	pesq.cd_autoatendimento,        " +
								"	resp.cd_pergunta,        " +
								"	resp.cd_resposta,        " +
								"	respexibida.ds_observacao,        " +
//								"	to_char(SYSDATE,'DMMYYYYHHMISS'),        " +
								"	to_char(pesqexibida.dt_resposta,'DMMYYYYHHMISS'),        " +
								"	autoatend.cd_un" +
								" from    " +
								"	AGV_TAB_PESQ_EXIBIDA pesqexibida, " +
								"	AGV_TAB_PESQUISA pesq, 	" +
								"	AGV_TAB_RESP_PESQ_EXIBIDA respexibida,        " +
								"	AGV_TAB_RESP_PESQUISA resp,        " +
								"	agv_tab_autoat_acessado autoatend" +
								" Where " +
								"	pesqexibida.cd_pesquisa = pesq.cd_pesquisa      " +
								"	and pesqexibida.cd_pesq_exibida = respexibida.cd_resp_pesq_exibida      " +
								"	and respexibida.cd_resposta = resp.cd_resposta      " +
								"	and pesqexibida.cd_atendimento = autoatend.cd_atendimento      " +
								"	and pesq.cd_autoatendimento = autoatend.cd_autoatendimento " +
								"   and pesq.tp_pesquisa = 2";
	
	private static String sql2 = "";
	
	public static void main(String[] args) throws Exception{
		List<PesquisaEspecifica> collectionValues = new ArrayList<PesquisaEspecifica>();

		PesquisaEspecificaBatch batch = new PesquisaEspecificaBatch();
		batch.setConnectionType(ORACLE);
		ResultSet rs = batch.executeSQL(sql);

		batch.setConnectionType(RDMS);
//		ResultSet rs2 = batch.executeSQL(sql2);

		while(rs.next()){
			PesquisaEspecifica at = new PesquisaEspecifica();
			at.setIdAtendimento(rs.getString(1));
			at.setIdPesquisa(rs.getString(2));
			at.setIdAutoatendimento(rs.getString(3));
			at.setIdPergunta(rs.getString(4));
			at.setIdResposta(rs.getString(5));
			at.setObsResposta(rs.getString(6));			
			at.setDataPesquisa(rs.getString(7));
			at.setIdUnidadeNegocio(rs.getString(8));
			
			//CSI
//			at.setDsUnidadeNegocio(rs.getString(1));
			collectionValues.add(at);
		}
		
//		while(rs2.next()){			}
		
		rs.close();
//		rs2.close();
		batch.setCollectionValues(collectionValues);
		batch.run(path);
	}
}
