/**
 * 
 */
package com.org.commons.atomparser.test;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.org.commons.atomparser.exceptions.CheckedException;
import com.org.commons.atomparser.helpers.MonitorObject;
import com.org.commons.atomparser.parser.FileParser;

/**
 * @author Sharath
 *
 */
public class TestFileParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = ".//Atom Results//10.200.252.105";
		String hasHeader = "true";
		String rowId = "5";
		String columnId = "4";
		String pattern = "df -k";
		ArrayList<MonitorObject> _monitors;
		Hashtable<String,String> _utilizationTable;
		MonitorObject _values;
		int rows;
		String _columnValue;
		Enumeration<String> _rowStrings;
		String _rowValue;
		try {
			FileParser _parser = new FileParser(fileName,pattern,rowId,columnId,hasHeader,"DISK");
			_monitors = _parser.getMonitorObjectDetails();
			rows = _monitors.size();
			for(int _index =0;_index<rows;_index++){
				_values = _monitors.get(_index);
				System.out.println(_values.getMonitoredElement());
				System.out.println(_values.getDateNTime());
				_utilizationTable = _values.getUtilizationTable();
				_rowStrings = _utilizationTable.keys();
				System.out.println(_utilizationTable.size());
				while(_rowStrings.hasMoreElements()){
					_rowValue = _rowStrings.nextElement();
					//System.out.println(_rowValue);
					_columnValue = _utilizationTable.get(_rowValue);
					System.out.println(_rowValue +"\t\t\t\t\t\t\t" + _columnValue);
				}
			}
		} catch (CheckedException e) {
			System.out.println(e.getCustomMessage());
			e.printStackTrace();
		}

	}

}
