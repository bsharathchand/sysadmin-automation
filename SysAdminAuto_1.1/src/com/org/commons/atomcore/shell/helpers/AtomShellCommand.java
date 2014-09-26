package com.org.commons.atomcore.shell.helpers;

import java.util.ArrayList;

/**
 * @author Sharath
 *
 */
public class AtomShellCommand {
	private ArrayList<String> command;
	private String serverIp;
	private String loginUserName;
	private String loginPassword;
	private StringBuffer result;
	/**
	 * @return the command
	 */
	public ArrayList<String> getCommand() {
		return command;
	}
	/**
	 * @param command the command to set
	 */
	public void setCommand(ArrayList<String> command) {
		this.command = command;
	}
	/**
	 * @return the serverIp
	 */
	public String getServerIp() {
		return serverIp;
	}
	/**
	 * @param serverIp the serverIp to set
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
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
	
}
