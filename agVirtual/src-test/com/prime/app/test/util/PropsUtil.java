package com.prime.app.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropsUtil {
	public PropsUtil() {  }
    /**
     * Load a properties file from the classpath
     * @param propsName
     * @return Properties
     * @throws Exception
     */
    public Properties load(String propsName) {
    	Properties props = new Properties();
    	try{        
           String path = getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
           FileInputStream fis = new FileInputStream(new File( path + propsName));
           props.load(fis);
           fis.close();
    	}catch (Exception e) {
			e.printStackTrace();
		}
        return props;
    }
    
    /**
     * Load a Properties File
     * @param propsFile
     * @return Properties
     * @throws IOException
     */
    public static Properties load(File propsFile) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(propsFile);
        props.load(fis);    
        fis.close();
        return props;
    }
}
