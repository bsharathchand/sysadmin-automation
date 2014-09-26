/**
 * 
 */
package com.org.commons.atomparser.parser;

import java.util.ArrayList;

import com.org.commons.atomparser.exceptions.CheckedException;
import com.org.commons.atomparser.helpers.MonitorObject;

/**
 * @author Sharath
 *
 */
public interface AtomUtilizations {
	
	/**
	 * @param utilizationGraph
	 * @throws CheckedException 
	 */
	public void fileUtilization(StringBuffer utilizationGraph) throws CheckedException;
	
	/**
	 * @param _monitors
	 * @throws CheckedException 
	 */
	public void parseMonitorObjects(ArrayList<MonitorObject> _monitors) throws CheckedException;

}
