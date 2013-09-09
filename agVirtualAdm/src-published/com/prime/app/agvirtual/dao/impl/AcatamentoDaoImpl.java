package com.prime.app.agvirtual.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AcatamentoDao;
import com.prime.app.agvirtual.to.AcatamentoTO;
import com.prime.app.agvirtual.to.TabelaTO;
import com.sabesp.pool.ConnectionFactory;
import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.IspecModelRef;
import com.unisys.jellybeans.LINCEnvironment;
import com.unisys.jellybeans.LINCStatus;

@Repository
public class AcatamentoDaoImpl implements AcatamentoDao{
	
	private static final Logger LOGGER = Logger.getLogger("AcatamentoDaoImpl");
	
	private static String host = "x-ratl:ratldm1.ti.sabesp.com.br:1030";
	
	int conexoes = 10;
	
	LINCEnvironment objLINC = null;
	LINCStatus objStatusLine = null;
	IspecModel objCurrentIspec = null;
	
	
	public List consulta2(AcatamentoTO acatamentoTO) {
		List listaResultado = new ArrayList();
		listaResultado.add(new TabelaTO("Linha1","valor 10"));
		listaResultado.add(new TabelaTO("Linha1","valor 11"));
		listaResultado.add(new TabelaTO("Linha1","valor 12"));
		listaResultado.add(new TabelaTO("Linha1","valor 13"));
		listaResultado.add(new TabelaTO("Linha1","valor 14"));
		return listaResultado;
	}
	
	/**
	 * Carrega pool de conexão , passa parametros de entrada e recebe resultado do processamento do mainframe
	 */
	public List consulta(AcatamentoTO acatamentoTO) {
		
		List listaResultado = new ArrayList();

		try{
			
			GenericObjectPool connectionPool = new GenericObjectPool(new ConnectionFactory(host, "csit", "TAGVIRTUAL", "AGVIRT02"), conexoes);
			LOGGER.info("Inicializacao do pool de conexão MainFrame EAE feita com sucesso");
			
			objLINC = (LINCEnvironment) connectionPool.borrowObject(); 
			
			loadIspec("AVWEB");

			IspecModel objCurrentIspec = objLINC.getCurrentIspec();
			
			int iResult = objCurrentIspec.setFieldValueFromInt("NRRGILIG", Integer.valueOf(acatamentoTO.getNrRgiLig()).intValue());
			objCurrentIspec.setFieldValueFromInt("CDGRPSERV", Integer.valueOf(acatamentoTO.getCdGrpServ()).intValue());
			if(acatamentoTO.getCdServCom().length() > 0){
				objCurrentIspec.setFieldValueFromInt("CDSERVCOM", Integer.valueOf(acatamentoTO.getCdServCom()).intValue());
			}	
			objCurrentIspec.setFieldValue("DSTELA50", (acatamentoTO.getDsTela50()));
			
			LINCStatus objStatusLine = objLINC.makeLINCStatus();
			IspecModelRef objNewIspecRef = objLINC.makeIspecModelRef();
			iResult = objLINC.transaction(objCurrentIspec, objNewIspecRef, objStatusLine);
			//objLINC.simpleTransaction(objCurrentIspec, objStatusLine); //Transação apenas de entrada
			
			if (objStatusLine != null) {
				System.out.println("Status = " + objStatusLine.getStatus());
			}
			
			objCurrentIspec = objNewIspecRef.getIspec();
			String[] fields = objCurrentIspec.getFieldList();
			for (int i = 0; i < fields.length; i++) {
				TabelaTO t = new TabelaTO(fields[i],objCurrentIspec.getFieldValue(fields[i]));
				listaResultado.add(t);
//				listaResultado.add(new String(fields[i] + " = " + objCurrentIspec.getFieldValue(fields[i]) + " Type= " + objCurrentIspec.getFieldType(fields[i])));
			}
			
			String[] cols = objCurrentIspec.getArrayColumnNames();
			
			int rows = objCurrentIspec.getArrayRows();
			
			System.out.println("rows = " + rows);
			
			for (int i = 0; i < rows; i++) {
	    		System.out.println("Row = " + (i+1));
	    		for (String columnName : cols) {
	    			System.out.print(columnName + " = ");
					System.out.println(objCurrentIspec.getFieldValue(columnName, i));
				}
	    		System.out.println("--------------------------------------");
			}
			
			System.out.println("model name = " + objLINC.getCurrentIspec().getIspecModelName());
			
		}catch (Exception e) {
			//LOGGER.info("Erro ao criar pool de conexão");
			e.printStackTrace();
		}
		
		return listaResultado; 
	}
	
	private void loadIspec(String ispec) {
		IspecModel objTempIspec = objLINC.getIspec(ispec);
		objLINC.loadIspec(objTempIspec);
	}
	
	public static void main(String[] args) {
		AcatamentoDaoImpl d =  new AcatamentoDaoImpl();
		AcatamentoTO acatamentoTO =  new AcatamentoTO();
		acatamentoTO.setCdGrpServ("20");
		acatamentoTO.setNrRgiLig("52791947");
		acatamentoTO.setDsTela50("teste@teste.com");
		d.consulta2(acatamentoTO);
	}
}
