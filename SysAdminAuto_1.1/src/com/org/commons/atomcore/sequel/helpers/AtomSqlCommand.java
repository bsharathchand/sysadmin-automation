package com.org.commons.atomcore.sequel.helpers;

import java.util.ArrayList;


/**
 * @author Sharath
 *
 */
public class AtomSqlCommand {
	
	private String sqlStatement;
	private ArrayList<Integer> columnNos;
	/**
	 * TO maintain the consistency.
	 */
	public static final String InvalidCommand = "Invalid SQL Statement";
	/**
	 * @return the sqlStatement
	 */
	public String getSqltStatement() {
		return sqlStatement;
	}
	/**
	 * @param sqlStatement the sqlSatement to set
	 */
	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}
	/**
	 * @return the columnNos
	 */
	public ArrayList<Integer> getColumnNos() {
		return columnNos;
	}
	/**
	 * @param columnNos the columnNos to set
	 */
	public void setColumnNos(ArrayList<Integer> columnNos) {
		this.columnNos = columnNos;
	}
}
