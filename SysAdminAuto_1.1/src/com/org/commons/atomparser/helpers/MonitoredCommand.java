package com.org.commons.atomparser.helpers;

/**
 * @author Sharath
 *
 */
public class MonitoredCommand {
	private String commandDescription;
	private String commandPattern;
	private String rowId;
	private String columnId;
	private String hasHeader;
	private String thresholdLimit;
	private String monitoredElement;
	/**
	 * @return the commandDescription
	 */
	public String getCommandDescription() {
		return commandDescription;
	}
	/**
	 * @param commandDescription the commandDescription to set
	 */
	public void setCommandDescription(String commandDescription) {
		this.commandDescription = commandDescription;
	}
	/**
	 * @return the commandPattern
	 */
	public String getCommandPattern() {
		return commandPattern;
	}
	/**
	 * @param commandPattern the commandPattern to set
	 */
	public void setCommandPattern(String commandPattern) {
		this.commandPattern = commandPattern;
	}
	/**
	 * @return the rowId
	 */
	public String getRowId() {
		return rowId;
	}
	/**
	 * @param rowId the rowId to set
	 */
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	/**
	 * @return the columnId
	 */
	public String getColumnId() {
		return columnId;
	}
	/**
	 * @param columnId the columnId to set
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	/**
	 * @return the hasHeader
	 */
	public String getHasHeader() {
		return hasHeader;
	}
	/**
	 * @param hasHeader the hasHeader to set
	 */
	public void setHasHeader(String hasHeader) {
		this.hasHeader = hasHeader;
	}
	/**
	 * @return the thresholdLimit
	 */
	public String getThresholdLimit() {
		return thresholdLimit;
	}
	/**
	 * @param thresholdLimit the thresholdLimit to set
	 */
	public void setThresholdLimit(String thresholdLimit) {
		this.thresholdLimit = thresholdLimit;
	}
	/**
	 * @param monitoredElement the monitoredElement to set
	 */
	public void setMonitoredElement(String monitoredElement) {
		this.monitoredElement = monitoredElement;
	}
	/**
	 * @return the monitoredElement
	 */
	public String getMonitoredElement() {
		return monitoredElement;
	}
}
