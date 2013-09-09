package com.sabesp.teste;

import java.util.logging.Logger;

import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.IspecModelRef;
import com.unisys.jellybeans.LINCEnvironment;
import com.unisys.jellybeans.LINCStatus;
import com.unisys.jellybeans.TransactionManager;
import com.unisys.util.Log;

public class TesteAVWEB {
	
	Logger logger = Logger.getLogger("TesteAVWEB");
	

	LINCEnvironment objLINC = null;
	LINCStatus objStatusLine = null;
	IspecModel objCurrentIspec = null;
	String appName = "APPNAME";
	String error_page = "errorpage";

    public TesteAVWEB()
    {
    }

    /**
     * @param args
     */
    /**
     * @param args
     */
    public static void main(String args[])
    {
        TesteAVWEB t = new TesteAVWEB();
        t.envia();
        
        t.loadIspec("AVWEB");
        
        t.avweb();
    }

    private void envia()
    {
        objLINC = new LINCEnvironment();
        objLINC.setName("csit");
        objLINC.setPackagePrefix("com.sabesp");
        objLINC.setApplicationName("csit");
        objLINC.setBundleName("full");
        LINCStatus objStatusLine = objLINC.makeLINCStatus();
        com.unisys.util.ObjectRef objLoginAttrsRef = objLINC.makeObjectRef();
        Log log = objLINC.getLogObject();
        if(Log.def.getLogLevel() != 1)
        {
            log.setOutputFile("e:\\temp\\log1.txt");
            Log.def.setLogLevel(5);
            Log.def.setFlags(62);
        }

		TransactionManager tm = objLINC.getTransManager();
		System.out.println("Logado = " + tm.isLoggedIn());

		System.out.println("Conecting...");
        int iResult = objLINC.connect("x-ratl:ratldm1.ti.sabesp.com.br:1030", "csit", objLoginAttrsRef, objStatusLine, 0);
        //int iResult = objLINC.connect("x-ratl:ratlpr1.ti.sabesp.com.br:1030", "csia", objLoginAttrsRef, objStatusLine, 0);
        if(iResult == 100)
            System.out.println((new StringBuilder("Connected!!! - Result = ")).append(iResult).toString());
        else
            System.out.println((new StringBuilder("Not Connected ... - Result = ")).append(iResult).toString());
		System.out.println("Logado = " + tm.isLoggedIn());
        IspecModelRef objHelloIspecRef = objLINC.makeIspecModelRef();
        iResult = objLINC.hello(objHelloIspecRef, objStatusLine);
        Object objHelloIspec = objHelloIspecRef.getObject();
        IspecModel objCurrentIspec = (IspecModel)objHelloIspec;
        objCurrentIspec.setFieldValue("NMUSERID", "TAGVIRTUAL");
        objCurrentIspec.setFieldValue("CDPASSWOR", "AGVIRT02");
        IspecModelRef objNewIspecRef = objLINC.makeIspecModelRef();
        System.out.println((new StringBuilder("objLINC Version = ")).append(objLINC.getVersion()).toString());
        System.out.println((new StringBuilder("objCurrentIspec Version = ")).append(objCurrentIspec.getGenVersion()).toString());
        System.out.println("Sending Login...");
        
        iResult = objLINC.simpleTransaction(objCurrentIspec, objStatusLine);
        //iResult = objLINC.transaction(objCurrentIspec, objNewIspecRef, objStatusLine);
        
		if (tm.isLoggedIn()) {
			System.out.println("Logado!");
		}
        
        if(iResult == 100)
        {
            System.out.println((new StringBuilder("Login OK!!! - Result = ")).append(iResult).toString());
        } else
        {
            System.out.println((new StringBuilder("Not Logged ... - Result = ")).append(iResult).toString());
            System.out.println((new StringBuilder("Status line = ")).append(objStatusLine.getError(0)).toString());
            System.out.println((new StringBuilder("Error 2 =")).append(objStatusLine.getStatus()).toString());
        }
    }
	
	private void loadIspec(String ispec) {
		
		IspecModel objTempIspec = objLINC.getIspec(ispec);
		objLINC.loadIspec(objTempIspec);
	}

	private void avweb() {
		
           IspecModel objCurrentIspec = objLINC.getCurrentIspec();

    		//int iResult = objCurrentIspec.setFieldValueFromInt("NRRGILIG", 52791947);
           //int iResult = objCurrentIspec.setFieldValueFromInt("NRRGILIG", 3107);
           	int iResult = objCurrentIspec.setFieldValueFromInt("NRRGILIG", 267060408);
    		objCurrentIspec.setFieldValueFromInt("CDGRPSERV", 20);
    		//objCurrentIspec.setFieldValueFromInt("CDSERVCOM", 1);
    		objCurrentIspec.setFieldValue("DSTELA50", "teste@teste.com");
    		//objCurrentIspec.setFieldValue("DSPROXTEL", "AVWEB");
    		//objCurrentIspec.setFieldValue("STOK", "S");
    		
            LINCStatus objStatusLine = objLINC.makeLINCStatus();
    		IspecModelRef objNewIspecRef = objLINC.makeIspecModelRef();
    		iResult = objLINC.transaction(objCurrentIspec, objNewIspecRef, objStatusLine);
    		//objLINC.simpleTransaction(objCurrentIspec, objStatusLine);
    		
    		if (objStatusLine != null) {
    			System.out.println("Status = " + objStatusLine.getStatus());
    		}
    		
    		objCurrentIspec = objNewIspecRef.getIspec();
    		String[] fields = objCurrentIspec.getFieldList();
    		for (int i = 0; i < fields.length; i++) {
				System.out.println(fields[i] + " = " + objCurrentIspec.getFieldValue(fields[i]) + " Type= " + objCurrentIspec.getFieldType(fields[i]));
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
    		

			//------------------------------------------------------------------------------------------
    		// Seleciona na CAPAF
			//------------------------------------------------------------------------------------------
    		
//    		IspecModel capafIspec = objCurrentIspec;
//
//    		capafIspec.setFieldValue("STSELEC", 4, "X");
//    		capafIspec.setFieldValueFromInt("CDMOTVIA", 4, 3);
//    		capafIspec.setFieldValue("DSTELA8", 4, "23/04/10");
//    		capafIspec.setFieldValue("STOK", "S");
//
//    		objNewIspecRef = objLINC.makeIspecModelRef();
//    		iResult = objLINC.transaction(capafIspec, objNewIspecRef);
//    		
//
//			//------------------------------------------------------------------------------------------
//    		// Insere na CAPAB
//			//------------------------------------------------------------------------------------------
//    		
//    		IspecModel capabIspec = objCurrentIspec;
//
//    		capabIspec.setFieldValue("NMGERAL","Ze da Silva");
//    		capabIspec.setFieldValue("CDDDD","11");
//    		capabIspec.setFieldValue("NRTELEF","99998888");
//    		capabIspec.setFieldValue("STGERAL","N");
//    		capabIspec.setFieldValue("STGERAL1","S");
//    		capabIspec.setFieldValue("STGERAL2","S");
//    		capabIspec.setFieldValue("STIMPRESS","S");
//
//    		iResult = objLINC.transaction(capabIspec, objNewIspecRef);
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		//return iResult;

	}

}
