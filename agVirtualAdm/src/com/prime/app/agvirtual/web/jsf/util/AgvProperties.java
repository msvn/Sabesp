package com.prime.app.agvirtual.web.jsf.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import com.prime.infra.config.Version;

public class AgvProperties {
	
	private static Logger logger = LoggerFactory.getLogger(Version.class);
	
	static Resource res = new DefaultResourceLoader().getResource("classpath:META-INF/config/agvirtual.properties");
	
	public static String readProperties(String name) {
		
		String properties = "";
	        try
	        {
	        	InputStream inputStream = res.getInputStream();
				if (inputStream != null) {
					Properties p = new Properties();
					p.load(inputStream);
					inputStream.close();
					properties = p.getProperty(name);
	
				}
			} catch (IOException e) {
				logger.error("Erro ao ler properties");
			}
		return properties;
	}
}
