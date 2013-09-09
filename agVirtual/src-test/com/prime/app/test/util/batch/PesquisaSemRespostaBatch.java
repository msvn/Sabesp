package com.prime.app.test.util.batch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prime.app.agvirtual.entity.batch.PesquisaSemResposta;
import com.prime.app.test.util.BatchTools;

public class PesquisaSemRespostaBatch extends BatchTools<PesquisaSemResposta> {
	private static String path = "c:/temp/";	
	private static String sql = " Select  " +
								"	pesqexibida.cd_atendimento," +
								" 	pesqexibida.cd_pesquisa, " +
								"	pesq.cd_autoatendimento, " +
								"	to_char(pesqexibida.dt_resposta,'DMMYYYYHHMISS'), " +
								"	autoatend.cd_un" +
								" From    " +
								"	AGV_TAB_PESQ_EXIBIDA pesqexibida, AGV_TAB_PESQUISA pesq, agv_tab_autoat_acessado autoatend " +
								" Where pesqexibida.cd_pesquisa = pesq.cd_pesquisa" +
								"   and pesqexibida.cd_atendimento = autoatend.cd_atendimento" +
								"   and pesq.cd_autoatendimento = autoatend.cd_autoatendimento" +
								"   and pesqexibida.cd_pesq_exibida not in (select cd_pesq_exibida from AGV_TAB_RESP_PESQ_EXIBIDA) ";
	
	private static String sql2 = "";
	
	public static void main(String[] args) throws Exception{
		List<PesquisaSemResposta> collectionValues = new ArrayList<PesquisaSemResposta>();

		PesquisaSemRespostaBatch batch = new PesquisaSemRespostaBatch();
		batch.setConnectionType(ORACLE);
		ResultSet rs = batch.executeSQL(sql);

		batch.setConnectionType(RDMS);
//		ResultSet rs2 = batch.executeSQL(sql2);

		while(rs.next()){
			PesquisaSemResposta at = new PesquisaSemResposta();
			at.setIdAtendimento(rs.getString(1));
			at.setIdPesquisa(rs.getString(2));
			at.setIdAutoatendimento(rs.getString(3));			
			at.setDataPesquisa(rs.getString(4));
			at.setIdUnidadeNegocio(rs.getString(5));
			
			// CSI
//			at.setDsUnidadeNegocio(rs.getString(1));
			collectionValues.add(at);
		}
		
//		while(rs2.next()){}			
		
		rs.close();
//		rs2.close();
		batch.setCollectionValues(collectionValues);
		batch.run(path);
	}
}
