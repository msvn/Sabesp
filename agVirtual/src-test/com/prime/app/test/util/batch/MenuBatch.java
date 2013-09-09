package com.prime.app.test.util.batch;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prime.app.agvirtual.entity.batch.Menu;
import com.prime.app.test.util.BatchTools;

public class MenuBatch extends BatchTools<Menu> {
	private static String path = "c:/temp/";	
	private static String sql = " Select  logac.CD_ATENDIMENTO, logac.CD_ITEM_MENU, to_char(logac.DT_ACESSO,'DMMYYYYHHMISS') " +
								" from AGV_LOG_ACESSO logac, AGV_TAB_ITEM_MENU item " +
								" where logac.cd_item_menu = item.cd_item_menu ";
	private static String sql2 = "";
	
	public static void main(String[] args) throws Exception{
		List<Menu> collectionValues = new ArrayList<Menu>();

		MenuBatch batch = new MenuBatch();
		batch.setConnectionType(ORACLE);
		ResultSet rs = batch.executeSQL(sql);

		while(rs.next()){
			Menu at = new Menu();	
			at.setDataAcesso(rs.getString(1));
			at.setIdAtendimento(rs.getString(2));
			at.setIdOpcaoMenu(rs.getString(3));
			collectionValues.add(at);
		}
		
		rs.close();
		batch.setCollectionValues(collectionValues);
		batch.run(path);
	}
}
