package com.org.commons.atomcore.sequel.helpers;

import java.util.ArrayList;

/**
 * @author Sharath
 *
 */
public class AtomSequelCommands {
	private String loginUserName;
	private String loginPassword;
	private String serverIp;
	private StringBuffer result;
	private ArrayList<AtomSqlCommand> commandList;
	/**
	 * @return the commandList
	 */
	public ArrayList<AtomSqlCommand> getCommandList() {
		return commandList;
	}
	/**
	 * @param commandList the commandList to set
	 */
	public void setCommandList(ArrayList<AtomSqlCommand> commandList) {
		this.commandList = commandList;
	}
	/**
	 * @return the loginUserName
	 */
	public String getLoginUserName() {
		return loginUserName;
	}
	/**
	 * @param loginUserName the loginUserName to set
	 */
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	/**
	 * @return the loginPassword
	 */
	public String getLoginPassword() {
		return loginPassword;
	}
	/**
	 * @param loginPassword the loginPassword to set
	 */
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	/**
	 * @return the result
	 */
	public StringBuffer getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(StringBuffer result) {
		this.result = result;
	}
	/**
	 * @param serverIp the serverIp to set
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	/**
	 * @return the serverIp
	 */
	public String getServerIp() {
		return serverIp;
	}

	
}
