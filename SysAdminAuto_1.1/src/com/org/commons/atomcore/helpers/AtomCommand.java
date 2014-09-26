package com.org.commons.atomcore.helpers;

import java.util.StringTokenizer;

/**
 * @author SharathBhaskara
 * @category AtomCore
 * This is an helper class pointing to a single command instance for execution.
 * 
 */
public class AtomCommand {
	private String commandID;
	private String command;
	private String commandDescription;
	private String commandLine;
	
	/**
	 * @return commandLine
	 */
	public String getCommandLine() {
		return commandLine;
	}
	/**
	 * @param commandLine
	 */
	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}
	/**
	 * @return commandID
	 */
	public String getCommandID() {
		return commandID;
	}
	/**
	 * @param commandID
	 */
	public void setCommandID(String commandID) {
		this.commandID = commandID;
	}
	/**
	 * @return command
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * @param command
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	/**
	 * @return serverID
	 */
	public String getServerID() {
		String _commandId = this.commandID;
		String _serverId;
		StringTokenizer _tokens = new StringTokenizer(_commandId,"-");
		_tokens.nextToken();
		_serverId =_tokens.nextToken();
		return _serverId;
	}

	/**
	 * @return commandDescription
	 */
	public String getCommandDescription() {
		return commandDescription;
	}
	/**
	 * @param commandDescription
	 */
	public void setCommandDescription(String commandDescription) {
		this.commandDescription = commandDescription;
	}
	
}
