package com.prime.app.jpa.test;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.prime.app.test.util.PropsUtil;

public class RunGenericJPA {

	public Properties props = new PropsUtil().load("/testcase.properties");
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");	
		
	public static void main(String[] args) {
		emf.createEntityManager();
	}
	
}
