/**
 * 
 */
package com.org.commons.atomparser.parser;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.org.commons.atomparser.exceptions.CheckedException;
import com.org.commons.atomparser.helpers.MonitorObject;

/**
 * @author Sharath
 *
 */
public class AtomDiskUtilizationProcessor extends AtomUtilizationAdapter {

	private String threshold;
	/**
	 * 
	 */
	public static String MonitoredElement = "DISK"; 
	
	/**
	 * @param serverIp
	 * @param serverDescription
	 */
	public AtomDiskUtilizationProcessor(String serverIp,
			String serverDescription) {
		super(serverIp, serverDescription);
	}
	
	/**
	 * @param serverIp
	 * @param serverDescription
	 * @param threshold
	 */
	public AtomDiskUtilizationProcessor(String serverIp,String serverDescription,String threshold){
		this(serverIp,serverDescription);
		this.threshold = threshold;
	}

	/* (non-Javadoc)
	 * @see com.org.commons.atomparser.parser.AtomUtilizations#parseMonitorObjects(java.util.ArrayList, java.lang.String)
	 * Sorry folks, this is custom for our project ... :)
	 * this is completely customized.. we need to implement this method and customize as per the desired output and command;
	 */
	@Override
	public void parseMonitorObjects(ArrayList<MonitorObject> monitors) throws CheckedException {
		// TODO Auto-generated method stub
		ArrayList<String> _directoryList;
		StringBuffer _utilizationGraph;
		
		_directoryList = getDirectoresFromMonitors(monitors);
		_utilizationGraph = populateGraph(_directoryList,monitors);
		fileUtilization(_utilizationGraph);

	}

	private ArrayList<String> getDirectoresFromMonitors(
			ArrayList<MonitorObject> monitors) throws CheckedException {
		ArrayList<String> _directoryList;
		Hashtable<String,String> _utilizationTable;
		Enumeration<String> _directories;
		MonitorObject _monitor;
		String _directoryName;
		int _countMonitors;
		
		
		_directoryList = new ArrayList<String>();
		_countMonitors = monitors.size();
		for(int _index=0;_index<_countMonitors;_index++){
			_monitor = monitors.get(_index);
			if(!MonitoredElement.equalsIgnoreCase(_monitor.getMonitoredElement())){
				CheckedException ce = new CheckedException("ArrayList contains an Invalid Monitor Object");
				throw ce;
			}
			_utilizationTable = _monitor.getUtilizationTable();
			_directories = _utilizationTable.keys();
			for(boolean _hasMoreElement = _directories.hasMoreElements();_hasMoreElement;_hasMoreElement = _directories.hasMoreElements()){
				_directoryName = _directories.nextElement();
				if(!_directoryList.contains(_directoryName)){
					_directoryList.add(_directoryName);
				}
			}
		}
		
		return _directoryList;
	}

	
	
	private StringBuffer populateGraph(ArrayList<String> directoryList,
		ArrayList<MonitorObject> monitors) {
		StringBuffer _utilizationGraph;
		_utilizationGraph = new StringBuffer();
		_utilizationGraph.append(graphTemplate(directoryList));
		_utilizationGraph.append(populateGraphWithValues(directoryList,monitors));
		_utilizationGraph.append(closeGraphTemplate());
		return _utilizationGraph;
	}
	

	private String graphTemplate(ArrayList<String> directoryList){
		StringBuffer _template;
		_template = new StringBuffer(HtmlHelper.openBodyOfHTML(this.serverIp));
		_template.append("<H3 align=center>");
		_template.append("Filing Utilization Graph for ");
		_template.append(this.serverIp);
		_template.append("</H3><BR/>");
		_template.append(HtmlHelper.openTable("Date & Time", directoryList, "80%"));
		return _template.toString();
	}
	
	private Object closeGraphTemplate() {
		StringBuffer _template;
		_template = new StringBuffer(HtmlHelper.closeTable());
		_template.append(HtmlHelper.closeBodyOfHTML());
		return _template.toString();
	}


	private Object populateGraphWithValues(ArrayList<String> directoryList,
			ArrayList<MonitorObject> monitors) {
		
		int _directoryListSize;
		int _monitorsSize;
		ArrayList<String> _columnValues;
		MonitorObject _monitor;
		Enumeration<String> _directories;
		String _directoryName;
		String _columnValue;
		StringBuffer _temp;
		String _serialColumnValue;
		
		_temp = new StringBuffer();
		_directoryListSize = directoryList.size();
		_monitorsSize = monitors.size();
		for(int _index=0;_index<_monitorsSize;_index++){
			_monitor = monitors.get(_index);
			_serialColumnValue = _monitor.getDateNTime();
			_directories = _monitor.getUtilizationTable().keys();
			_columnValues = new ArrayList<String>();
			for(boolean _hasMoreElements = _directories.hasMoreElements();_hasMoreElements;_hasMoreElements = _directories.hasMoreElements()){
				_directoryName = _directories.nextElement();
				if(directoryList.contains(_directoryName)){
					_columnValue = _monitor.getUtilizationTable().get(_directoryName);
				} else{
					_columnValue = null;
				}
				_columnValues.add(_columnValue);
			}
			if(_directoryListSize == _columnValues.size()){
				_temp.append(HtmlHelper.writeToTable(_serialColumnValue, _columnValues));
			}
			else{
				System.out.println("Something went wrong");
			}
		}
		
		return _temp.toString();
	}


	/**
	 * @return the threshold
	 */
	public String getThreshold() {
		return threshold;
	}

}
