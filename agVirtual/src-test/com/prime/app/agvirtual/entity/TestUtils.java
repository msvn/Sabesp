package com.prime.app.agvirtual.entity;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TestUtils {
	public static void print(List<Object> items){
		System.out.println("####### Items ########");
		int i = 1;
		for(Object o : items){
			System.out.println("Item: " + i + " - Content: " + ToStringBuilder.reflectionToString(o));
		}
		System.out.println("####### fim items ########");
	}
}
