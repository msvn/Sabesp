package com.prime.app.test.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.prime.infra.record.adapters.ObjectUtils;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtField;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtFieldFile;
import com.prime.infra.util.WrapperUtils;

public abstract class BatchTools<T> {
	private List<T> collectionValues;
	private String connectionType;
	public static final String RDMS="RDMS";
	public static final String ORACLE="ORACLE";
	
	public void run(String path){
		long t0 = System.currentTimeMillis();
		
		FixedLenghtFieldFile annFixedLenghtFieldFile = null; 
		FileWriter fstream = null;
		BufferedWriter out = null;
		if(WrapperUtils.isNotNull(collectionValues)){
			Class clazz = collectionValues.get(0).getClass();		 
			annFixedLenghtFieldFile = ((FixedLenghtFieldFile)clazz.getAnnotation(FixedLenghtFieldFile.class));
		}
		
		try{
			if(WrapperUtils.isNotNull(annFixedLenghtFieldFile) && WrapperUtils.isNotNull(annFixedLenghtFieldFile.nameFile())){
				fstream = new FileWriter(path+annFixedLenghtFieldFile.nameFile());
			}else {
				fstream = new FileWriter(path+"GENERIC_NAME");
			}
			out = new BufferedWriter(fstream);
			
			int i = 0;
			for(Object obj : collectionValues){			
				writeObject(obj,out);	
				out.newLine();
				
				if(i%1000==0){
					out.flush();	
				}
			}			
			out.close();
		}catch (Exception e) {
			e.printStackTrace();			
		}
		
		long t1 = System.currentTimeMillis();
		long t = t1 - t0;
		System.out.println("* Total time: {} sec " + Long.valueOf(t));
//		System.exit(0);		

	}

	private void writeObject(Object object,BufferedWriter out){
			Field[] fields = object.getClass().getDeclaredFields();
		    for (Field field : fields) {
		    	 char paddind=' ';
		    	 FixedLenghtField annAEFixedLenghtField = (FixedLenghtField)field.getAnnotation(FixedLenghtField.class);;
		    	 try {	    		 
		    		 String value = (String)ObjectUtils.invokeGetterMethod(field, object);
//		    		 System.out.println(field.getType().toString()+"  "+	    				            
//		    				            annAEFixedLenghtField.position()+"  "+
//		    				            annAEFixedLenghtField.lenght()+"  "+
//		    				            annAEFixedLenghtField.paddingAlign()+"  "+
//		    				            Character.getType(annAEFixedLenghtField.paddingChar())+"  "+
//		    				            value);
		    		 
		    		 if(WrapperUtils.isNull(value)){
		    			 value="";
		    		 }	 
	    			 // completa espaços com ...
	    			 if(Character.getType(annAEFixedLenghtField.paddingChar())==15){
	    				 paddind='0';
	    			 }
	    			 
	    			 if(value.length()>annAEFixedLenghtField.lenght()){
	    				 value = value.substring(0,annAEFixedLenghtField.lenght());
	    			 }
	    			 
	    			 // alinha a direita ou esqueda com...
	    			 if(annAEFixedLenghtField.paddingAlign()>0){
	    				 out.write(StringUtils.rightPad(value,annAEFixedLenghtField.lenght() , paddind));
	    			 }else{
	    				 out.write(StringUtils.leftPad(value,annAEFixedLenghtField.lenght() , paddind));
	    			 }
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
	}
	
	public Collection<T> getCollectionValues() {
		return collectionValues;
	}

	public void setCollectionValues(List<T> collectionValues) {
		this.collectionValues = collectionValues;
	}
	
	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public Connection getConnection(){		
		Connection conn=null;
		try {
			Properties props = load(new File(System.getProperty("user.dir")+"/jdbc.properties" ));
			if(this.connectionType.equals(ORACLE)){
				Class.forName(props.getProperty("jdbc.driverClassName"));
				conn = DriverManager.getConnection(props.getProperty("jdbc.url"), props.getProperty("jdbc.username"), props.getProperty("jdbc.password"));				
			}else {
				Class.forName(props.getProperty("jdbc2.driverClassName"));
				conn = DriverManager.getConnection(props.getProperty("jdbc2.url"), props.getProperty("jdbc2.username"), props.getProperty("jdbc2.password"));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
    public static Properties load(File propsFile) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(propsFile);
        props.load(fis);    
        fis.close();
        return props;
    }
    
    public ResultSet executeSQL(String sql, Object... params ) {
		PreparedStatement pstmt=null;
		try {
			pstmt = getConnection().prepareStatement(sql);
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					if(params[i] instanceof String){
						pstmt.setString(i+1,(String) params[i]);
					}else if(params[i] instanceof Date){
						pstmt.setDate(i+1,(java.sql.Date)params[i]);	
					}
				}
			}
			return pstmt.executeQuery();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
