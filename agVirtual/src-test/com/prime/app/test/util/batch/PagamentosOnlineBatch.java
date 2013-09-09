package com.prime.app.test.util.batch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prime.app.agvirtual.entity.batch.PagamentosOnline;
import com.prime.app.test.util.BatchTools;

public class PagamentosOnlineBatch extends BatchTools<PagamentosOnline> {
	private static String path = "c:/temp/";	
	private static String sql = " Select " +
								" autoat.CD_ATENDIMENTO, " +
								" to_char(autoat.DT_INICIO,'DMMYYYYHHMISS'), " +
								" to_char(autoat.DT_FIM,'DMMYYYYHHMISS'), " +
								" acao.CD_BANCO" +
								" From agv_tab_autoat_acessado autoat, AGV_TAB_AUTOAT_ACESS_ACAO autoatacao, agv_tab_autoat_acao_autoat acao" +
								" Where autoat.cd_autoat_acessado = autoatacao.CD_AUTOAT_ACESS_ACAO " +
								" and autoatacao.CD_AUTOAT_ACESS_ACAO = acao.CD_AUTOAT_ACAO and nvl(acao.CD_BANCO,'') <> ''";
	
	public static void main(String[] args) throws Exception{
		List<PagamentosOnline> collectionValues = new ArrayList<PagamentosOnline>();

		PagamentosOnlineBatch batch = new PagamentosOnlineBatch();
		batch.setConnectionType(ORACLE);
		ResultSet rs = batch.executeSQL(sql);

		while(rs.next()){
			PagamentosOnline at = new PagamentosOnline();	
			at.setIdAtendimento(rs.getString(1));
			at.setDataIniServico(rs.getString(2));
			at.setDataFimServico(rs.getString(3));			
			at.setIdBanco(rs.getString(4));
			collectionValues.add(at);
		}
		
		rs.close();
		batch.setCollectionValues(collectionValues);
		batch.run(path);
	}
}
