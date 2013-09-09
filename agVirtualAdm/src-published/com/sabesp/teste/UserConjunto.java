package com.sabesp.teste;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.pool.impl.GenericObjectPool;

import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.LINCEnvironment;
import com.unisys.jellybeans.LINCStatus;

public class UserConjunto extends Thread {
	
	private GenericObjectPool connectionPool;
	private LINCEnvironment objLINC;
	private long rgi;
	private DataSource datasource;
	
	public UserConjunto(ThreadGroup tg, long i, GenericObjectPool connectionPool, DataSource datasource) {
		super(tg, String.valueOf(i));
		
		this.connectionPool = connectionPool;
		this.rgi = i;
		this.datasource = datasource;
	}

	public void run() {
		
		try {
			insere();
			
			consulta();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void insere() throws Exception {
		objLINC = (LINCEnvironment) connectionPool.borrowObject();

		loadIspec("W2VIN");
		
		w2vin();
		 
		connectionPool.returnObject(objLINC);

		System.out.println("RGI " + rgi + " inserido");
	}
	
	private void loadIspec(String ispec) {
		
		IspecModel objTempIspec = objLINC.getIspec(ispec);
		objLINC.loadIspec(objTempIspec);
	}
	
	private void w2vin() {
        LINCStatus objStatusLine = objLINC.makeLINCStatus();
		
		IspecModel objCurrentIspec = objLINC.getCurrentIspec();
	
		objCurrentIspec.setFieldValue("TPLEITURA", "C");
		objCurrentIspec.setFieldValueFromInt("CDMUNICIP", 180);
		objCurrentIspec.setFieldValueFromLong("NRRGILIG", rgi);
		
		objLINC.simpleTransaction(objCurrentIspec, objStatusLine);

		System.out.println("Status = " + objStatusLine.getStatus());
	}
	
	private void consulta() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;

        try {
            conn = datasource.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select NRRGILIG from cea01 where cdmunicip=180 and NRRGILIG=" + rgi);

            if (rset.next()) {
    	        System.out.println("RGI " + rset.getString("NRRGILIG") + " consultado");
            } else {
    	        System.out.println("RGI NAO ENCONTRADO" + rgi);
//    			TesteConjunto.invalidadosRDMS++;
            }
        } catch(SQLException e) {
//			TesteConjunto.invalidadosRDMS++;
            e.printStackTrace();
        } finally {
            try { if (rset != null) rset.close(); } catch(Exception e) { }
            try { if (stmt != null) stmt.close(); } catch(Exception e) { }
            try { if (conn != null) conn.close(); } catch(Exception e) { }
        }
		
	}
}