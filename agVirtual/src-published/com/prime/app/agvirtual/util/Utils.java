package com.prime.app.agvirtual.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import collections.Collection;

public class Utils {
	public static String spritArray(Object[] array, String separator){
		if(array==null) return null;
		
		if(array.length == 0) return "";
		
		StringBuilder sb = new StringBuilder(2 + array.length * 5);
		
		for(int i=0; i<array.length; i++){
			if(i>0) sb.append(separator);
			sb.append(array[i]);
		}
		return sb.toString();
	}
	
	public static String spritList(List<Object> list, String separator){
		String result = "";
		
		if(list==null) return null;
		if(list.size() == 0) return result;
		
		StringBuilder sb = new StringBuilder(2 + list.size() * 5);
		
		int i=0;
		for(Object item : list){
			if(i++>0) sb.append(separator);  // TODO - get from iterator.hasNext();
			sb.append(item);
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
//		String[] array = new String[]{"a"};
//		List<String> list = new ArrayList<String>();
		
//		list.add("xpto"); //list.add("xpto2"); list.add("xpto3");
		
//		System.out.println(Arrays.toString(arr));
		
//		System.out.println(Utils.spritArray(array, ","));
		
//		System.out.println(Utils.spritList(list, ","));
		
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(new Integer(1));
		arr.add(new Integer(2));
		
		String res = spritArray(arr.toArray(), ",");
		System.out.println(res);
	}
}
