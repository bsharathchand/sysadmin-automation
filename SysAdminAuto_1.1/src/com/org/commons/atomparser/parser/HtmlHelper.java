/**
 * 
 */
package com.org.commons.atomparser.parser;

import java.util.ArrayList;

/**
 * @author Sharath
 *
 */
public class HtmlHelper {
	
	
	/**
	 * @param serverDetails
	 * @return the HTML format in String with an Open Body
	 */
	public static String openBodyOfHTML(String serverDetails){
		StringBuffer _temp;
		_temp = new StringBuffer("<HTML><HEAD>");
		_temp.append("<TITLE>");
		_temp.append(serverDetails);
		_temp.append("</TITLE>");
		_temp.append("</HEAD> <BODY>");
		return _temp.toString();
	}
	
	/**
	 * @return a String with HTML format for closing BODy and HTML elements
	 */
	public static String closeBodyOfHTML(){
		StringBuffer _temp;
		_temp = new StringBuffer("</BODY></HTML>");
		return _temp.toString();
	}
	
	/**
	 * @param serialColumnName
	 * @param columns
	 * @param width 
	 * @return a String with HTML with the table and header row.
	 */
	public static String openTable(String serialColumnName,ArrayList<String> columns, String width){
		StringBuffer _temp;
		int _columnSize;
		_columnSize = columns.size();
		_temp = new StringBuffer(createTable(width));
		_temp.append("<TR><TH>");
		_temp.append(serialColumnName);
		_temp.append("</TH>");
		for(int _index=0;_index<_columnSize;_index++){
			_temp.append("<TH>");
			_temp.append(columns.get(_index));
			_temp.append("</TH>");
		}
		_temp.append("</TR>");
		return _temp.toString();
	}
	
	/**
	 * @param width 
	 * @return a HTML string with only a table created for specified width
	 */
	public static String createTable(String width){
		StringBuffer _temp;
		_temp = new StringBuffer("<TABLE border='1' align='center' width = ");
		_temp.append(width);
		_temp.append(">");
		return _temp.toString();
	}
	
	/**
	 * @return a HTML string for closing the table
	 */
	public static String closeTable(){
		StringBuffer _temp;
		_temp = new StringBuffer("</TABLE>");
		return _temp.toString();
	}
	
	/**
	 * @param serialColumnValue
	 * @param columnValues
	 * @return a HTML String with a row populated.
	 * 
	 * Before calling this please verify that your header row and column row has same no of cells.
	 * Otherwise the output will be distracted/unreadable/unreliable
	 * 
	 */
	public static String writeToTable(String serialColumnValue,ArrayList<String> columnValues){
		StringBuffer _temp;
		int _columnSize;
		_columnSize = columnValues.size();
		_temp = new StringBuffer("<TR><TD>");
		_temp.append(serialColumnValue);
		_temp.append("</TD>");
		for(int _index=0;_index<_columnSize;_index++){
			_temp.append("<TD>");
			_temp.append(columnValues.get(_index));
			_temp.append("</TD>");
		}
		_temp.append("</TR>");
		return _temp.toString();
	}


}
