package com.org.commons.atomcore.helpers;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author Sharath
 *
 */
public class AtomServerDetails {

	private String serverName;
	private String serverDescription;
	private String serverIP;
	private String loginUserName;
	private String loginPassword;
	private String sqlLoginUserName;
	private String sqlLoginPassword;
	private String serverId;
	private Hashtable<String, AtomCommand> commandList;
	private ArrayList<AtomExecutableCommand> executableCommandList;
	
	/**
	 * @return serverName
	 */
	public String getServerName() {
		return serverName;
	}
	/**
	 * @param serverName
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	/**
	 * @return serverDescription
	 */
	public String getServerDescription() {
		return serverDescription;
	}
	/**
	 * @param serverDescription
	 */
	public void setServerDescription(String serverDescription) {
		this.serverDescription = serverDescription;
	}
	/**
	 * @return serverIP
	 */
	public String getServerIP() {
		return serverIP;
	}
	/**
	 * @param serverIP
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	/**
	 * @return loginUserName
	 */
	public String getLoginUserName() {
		return loginUserName;
	}
	/**
	 * @param loginUserName
	 */
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	/**
	 * @return loginPassword
	 */
	public String getLoginPassword() {
		return loginPassword;
	}
	/**
	 * @param loginPassword
	 */
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	/**
	 * @return the sqlLoginUserName
	 */
	public String getSqlLoginUserName() {
		return sqlLoginUserName;
	}
	/**
	 * @param sqlLoginUserName the sqlLoginUserName to set
	 */
	public void setSqlLoginUserName(String sqlLoginUserName) {
		this.sqlLoginUserName = sqlLoginUserName;
	}
	/**
	 * @return the sqlLoginPassword
	 */
	public String getSqlLoginPassword() {
		return sqlLoginPassword;
	}
	/**
	 * @param sqlLoginPassword the sqlLoginPassword to set
	 */
	public void setSqlLoginPassword(String sqlLoginPassword) {
		this.sqlLoginPassword = sqlLoginPassword;
	}
	/**
	 * @param serverId
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	/**
	 * @return serverId
	 */
	public String getServerId() {
		return serverId;
	}

	/**
	 * @param commandList the commandList to set
	 */
	public void setCommandList(Hashtable<String, AtomCommand> commandList) {
		this.commandList = commandList;
	} 
	/**
	 * @return the commandList
	 */
	public Hashtable<String, AtomCommand> getCommandList() {
		return commandList;
	}
	/**
	 * @return the executableCommandList
	 */
	public ArrayList<AtomExecutableCommand> getExecutableCommandList() {
		return executableCommandList;
	}
	/**
	 * @param executableCommandList the executableCommandList to set
	 */
	public void setExecutableCommandList(
			ArrayList<AtomExecutableCommand> executableCommandList) {
		this.executableCommandList = executableCommandList;
	}
}
