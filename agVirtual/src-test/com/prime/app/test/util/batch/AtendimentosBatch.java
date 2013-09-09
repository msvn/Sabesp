package com.prime.app.test.util.batch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prime.app.agvirtual.entity.batch.Atendimentos;
import com.prime.app.test.util.BatchTools;

public class AtendimentosBatch extends BatchTools<Atendimentos> {
	private static String path = "c:/temp/";	
	private static String sql = " Select distinct atend.cd_atendimento, to_char(atend.DT_INICIO,'DMMYYYYHHMISS') DT_INICIO, " +
			" to_char(atend.DT_FINAL,'DMMYYYYHHMISS') DT_FINAL, count(CD_RGI) QT_RGI " +
			" From agv_tab_atendimento atend, agv_tab_autoat_acessado autoatend " +
			" Where atend.cd_atendimento = autoatend.cd_atendimento " +
			" Group By atend.cd_atendimento, CD_RGI, atend.DT_INICIO, atend.DT_FINAL";
	
	public static void main(String[] args) throws Exception{
		List<Atendimentos> collectionValues = new ArrayList<Atendimentos>();

		AtendimentosBatch batch = new AtendimentosBatch();
		batch.setConnectionType(ORACLE);
		ResultSet rs = batch.executeSQL(sql);

//		batch.setConnectionType(RDMS);
//		ResultSet rs2 = batch.executeSQL("");

		while(rs.next()){
			Atendimentos at = new Atendimentos();	
			at.setIdAtendimento(rs.getString(1));
			at.setDataAbertura(rs.getString(2));
			at.setDataEncerramento(rs.getString(3));
			at.setQtdRGIs(rs.getString(4));
			collectionValues.add(at);
		}
		
//		while(rs2.next()){			
//		}
		
		rs.close();
//		rs2.close();
		batch.setCollectionValues(collectionValues);
		batch.run(path);
	}
}
