package com.org.commons.atomcore.helpers;

import java.util.ArrayList;

/**
 * @author Sharath
 *
 */
public class AtomRunner {
	private ArrayList<AtomCommand> finalCommandList;
	private AtomServerDetails server;
	private StringBuffer result;
	
	/**
	 * @param commandList
	 * @param server
	 */
	public AtomRunner(ArrayList<AtomCommand> commandList,AtomServerDetails server){
		this.finalCommandList = commandList;
		this.server = server;
		result = new StringBuffer();
	}

	/**
	 * @return the finalCommandList
	 */
	public ArrayList<AtomCommand> getFinalCommandList() {
		return finalCommandList;
	}

	/**
	 * @param finalCommandList the finalCommandList to set
	 */
	public void setFinalCommandList(ArrayList<AtomCommand> finalCommandList) {
		this.finalCommandList = finalCommandList;
	}

	/**
	 * @return the server
	 */
	public AtomServerDetails getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(AtomServerDetails server) {
		this.server = server;
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
