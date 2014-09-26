/**
 * 
 */
package com.org.commons.atomparser.test;

/**
 * @author Sharath
 *
 */
public class TestMisc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			String value = "56%";
			value = value.replace("%","");
			if(50<Integer.parseInt(value))
				System.out.println(Integer.parseInt(value));
	}

}
