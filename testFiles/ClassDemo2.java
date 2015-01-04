package com.ast;

import static org.junit.Assert.assertEquals;

import java.util.*;



/**
 * javadoc
 * @Description demo for test 
 * 	eclipse ast
 * @version 1.0
 * @author shendong
 *
 */
public class ClassDemo2 {

	//field comments
	private String text ;

	/*
	 * Inner class
	 * @author shendong
	 *
	 */
	private class InnerClass{
		
	}
	
	static {
		String hi = "Hello World!";
	}
	/*
	 * function comments
	 * @param li
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List input(List li) {
		List list = new ArrayList();
		list.addAll(li);
		return list;
	}
}