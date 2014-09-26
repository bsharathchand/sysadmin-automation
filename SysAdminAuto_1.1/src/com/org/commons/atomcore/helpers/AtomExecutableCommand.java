package com.org.commons.atomcore.helpers;

import java.util.StringTokenizer;

/**
 * @author Sharath
 *
 */
public class AtomExecutableCommand {
	private String executableId;
	/**
	 * @return executableId
	 */
	public String getExecutableId(){
		return executableId;
	}
	/**
	 * @param executableId
	 */
	public void setExecutableID(String executableId){
		this.executableId = executableId;
	}
	/**
	 * @return serverID
	 */
	public String getServerID() {
		String _commandId = this.executableId;
		String _serverId;
		StringTokenizer _tokens = new StringTokenizer(_commandId,"-");
		_tokens.nextToken();
		_serverId =_tokens.nextToken();
		return _serverId;
	}
}
