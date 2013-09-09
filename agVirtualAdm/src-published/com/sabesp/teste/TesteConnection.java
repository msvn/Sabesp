package com.sabesp.teste;

import org.apache.commons.pool.impl.GenericObjectPool;

import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.IspecModelRef;
import com.unisys.jellybeans.LINCEnvironment;


public class TesteConnection {
	
	private static String host = "x-ratl:ratldm1.ti.sabesp.com.br:1030";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		int simultaneos = 10;
		
		int conexoes = 49;
		
		int total = 100;

		int threads = 20;
		
		int counter = 0;

		GenericObjectPool connectionPool = new GenericObjectPool(new com.sabesp.pool.ConnectionFactory(host, "csit", "TAGVIRTUAL", "AGVIRT01"), conexoes);
		
		ThreadGroup threadGroup = new ThreadGroup("usuarios");
		
		//long before = System.currentTimeMillis();

		while (counter < total) {

			for (int i = 0; i < threads; i++, counter++) {
				new Ping3(threadGroup, counter, connectionPool).start();
			}
	
			print("Total = " + counter);
	
			System.out.println("threadGroup.activeCount() = "+threadGroup.activeCount());
			//while(threadGroup.activeCount() > (simultaneos));
			//while(threadGroup.activeCount() > (simultaneos-conexoes));

		}
	}
	
	private static void print(String msg) {

		System.out.println("*** ------------------------------------- ***");
		System.out.println(msg);
		System.out.println("*** ------------------------------------- ***");
	}

}

class Ping3 extends Thread {
	
	private GenericObjectPool connectionPool;
	private LINCEnvironment objLINC;
	
	Ping3(ThreadGroup tg, int i, GenericObjectPool connectionPool) {
		super(tg, String.valueOf(i));
		
		this.connectionPool = connectionPool;
	}

	public void run() {
		
		try {
			objLINC = (LINCEnvironment) connectionPool.borrowObject();

			System.out.println("Numero de conexoes = " + connectionPool.getNumActive());			

			loadIspec("CMEZX");
	        
	        cmezx();
	        
	        cap98();
	        
	        connectionPool.returnObject(objLINC);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadIspec(String ispec) {
		
		IspecModel objTempIspec = objLINC.getIspec(ispec);
		objLINC.loadIspec(objTempIspec);
		
		System.out.println("Loading Ispec " + objLINC.getCurrentIspec() + "...");
	}
	
	private int cmezx() {
		
		IspecModel objCurrentIspec = objLINC.getCurrentIspec();
	
		int iResult = objCurrentIspec.setFieldValue("DSPROXTEL", "CAPAF");
		
		System.out.println("model name = " + objLINC.getCurrentIspec().getIspecModelName());
	
	    IspecModelRef objNewIspecRef = objLINC.makeIspecModelRef();
		iResult = objLINC.transaction(objCurrentIspec, objNewIspecRef);
		System.out.println("model name = " + objLINC.getCurrentIspec().getIspecModelName());
	
		return iResult;

	}
	
	private int cap98() {
		
           IspecModel objCurrentIspec = objLINC.getCurrentIspec();

    		int iResult = objCurrentIspec.setFieldValue("NRRGILIG", "3107");
    		objCurrentIspec.setFieldValue("NRTELA5", "188");
    		objCurrentIspec.setFieldValue("DSCONFIRM", "S");
    		
            IspecModelRef objNewIspecRef = objLINC.makeIspecModelRef();
    		iResult = objLINC.transaction(objCurrentIspec, objNewIspecRef);
    		
//    		String[] fields = objNewIspecRef.getIspec().getFieldList();
//    		for (int i = 0; i < fields.length; i++) {
//				System.out.println(fields[i] + " = " + objCurrentIspec.getFieldValue(fields[i]) + " Type= " + objCurrentIspec.getFieldType(fields[i]));
//			}
    		
    		String[] cols = objNewIspecRef.getIspec().getArrayColumnNames();
    		
    		int rows = objNewIspecRef.getIspec().getArrayRows();
    		
    		System.out.println("rows = " + rows);
    		
    		for (int i = 0; i < rows; i++) {
        		System.out.println("Row = " + (i+1));
        		for (String columnName : cols) {
        			System.out.print(columnName + " = ");
    				System.out.println(objNewIspecRef.getIspec().getFieldValue(columnName, i));
    			}
        		System.out.println("--------------------------------------");
			}
    		
    		System.out.println("model name = " + objLINC.getCurrentIspec().getIspecModelName());
    		

    		return iResult;

	}

}