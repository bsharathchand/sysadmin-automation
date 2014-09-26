package com.org.commons.atomcore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.org.commons.atomcore.exceptions.CheckedException;
import com.org.commons.atomcore.helpers.AtomCommand;
import com.org.commons.atomcore.helpers.AtomExecutableCommand;
import com.org.commons.atomcore.helpers.AtomServerDetails;


/**
 * @author Sharath
 *
 */
public class AtomServers {
	private File serverDetails;
	private Scanner serverFileScanner;
	private Hashtable<String, AtomServerDetails> serverMap;
	/**
	 * This file contains the list of commands and details
	 */
	public static final String ServersFileName = ".\\System\\AtomServerDetails";
	/**
	 * Final String for the String to maintain Consistency checks.
	 */
	public static final String InvalidServer = "INVALID SERVER"; 
	private static final String serverLoginUserName = "loadruser";
	private static final String serverLoginPassword = "PerfTest4QA";

	/**
	 * @throws CheckedException
	 * If this Constructor is used while creating the AtomServers object, then we need to call the 
	 * methods populateCommandList and populateExecutableCommandList explicitly.
	 * 
	 */
	public AtomServers() throws CheckedException{
		serverDetails = new File(ServersFileName);
		if(serverDetails.exists()){
			try {
				serverFileScanner = new Scanner(serverDetails);
			} catch (FileNotFoundException e) {
				CheckedException _exception = new CheckedException(e);
				_exception.setCustomMessage("Unable to open the Command File");
			}
		}
		else
		{
			throw new CheckedException("Server Details File is corrupted or missing");
		}
		
		this.populateServers();
	}
	
	
	/**
	 * @param commands
	 * @param executables
	 * @throws CheckedException
	 * 
	 * If this Constructor is used while creating the AtomServers object, then <b>no</b> need to call the 
	 * methods populateCommandList and populateExecutableCommandList explicitly.
	 * 
	 */
	public AtomServers(AtomCommands commands, AtomExecutables executables) throws CheckedException {
		this();
		this.populateServerCommandList(commands.getCommandList());
		this.populateServerExecutableCommandList(executables.getExecutables());
	}


	/**
	 * @param commandList
	 * This needs to be called for setting the CommandList for a server.
	 */
	public void populateServerCommandList(Hashtable<String,AtomCommand> commandList){
		Hashtable<String, AtomServerDetails> _serverMap;
		Hashtable<String,AtomCommand> _serverCommandList;
		Enumeration<String> _serverIds;
		boolean _hasMoreElements;
		String _serverId;
		_serverMap = this.serverMap;
		
		_serverIds = _serverMap.keys();
		_hasMoreElements=_serverIds.hasMoreElements();
		while(_hasMoreElements){
			_serverId = _serverIds.nextElement();
			_serverCommandList = populateCommandList(_serverId,commandList);
			_serverMap.get(_serverId).setCommandList(_serverCommandList);
			_hasMoreElements=_serverIds.hasMoreElements();
		}
	}
	
	private Hashtable<String,AtomCommand> populateCommandList(String serverId,Hashtable<String,AtomCommand> commandList){
		AtomCommand _commandDet;
		Enumeration<String> _commandIds;
		String _commandId;
		boolean _hasMoreElements;
		Hashtable<String,AtomCommand> _commandList;
		_commandList = new Hashtable<String,AtomCommand>();
		_commandIds = commandList.keys();
		
		_hasMoreElements = _commandIds.hasMoreElements();
		while(_hasMoreElements){
			_commandId = _commandIds.nextElement();
			_commandDet = commandList.get(_commandId);
			if (serverId.equals(_commandDet.getServerID())){
				_commandList.put(_commandId,_commandDet);
			}
			_hasMoreElements=_commandIds.hasMoreElements();
		}
		return _commandList;
	}
	
	/**
	 * @param commandList
	 * This needs to be called to set the Executable Commands for a Server.
	 * 
	 */
	public void populateServerExecutableCommandList(ArrayList<AtomExecutableCommand> commandList){
		Hashtable<String, AtomServerDetails> _serverMap;
		ArrayList<AtomExecutableCommand> _serverCommandList;
		Enumeration<String> _serverIds;
		boolean _hasMoreElements;
		String _serverId;
		_serverMap = this.serverMap;
		
		_serverIds = _serverMap.keys();
		_hasMoreElements=_serverIds.hasMoreElements();
		while(_hasMoreElements){
			_serverId = _serverIds.nextElement();
			_serverCommandList = populateExecutableCommandList(_serverId,commandList);
			_serverMap.get(_serverId).setExecutableCommandList(_serverCommandList);
			_hasMoreElements=_serverIds.hasMoreElements();
		}
	}
	
	private ArrayList<AtomExecutableCommand> populateExecutableCommandList(String serverId,ArrayList<AtomExecutableCommand> commandList){
		AtomExecutableCommand _commandDet;
		ArrayList<AtomExecutableCommand> _commandList;
		int index = 0;
		int _countExecutables;
		_commandList = new ArrayList<AtomExecutableCommand>();
		_countExecutables = commandList.size();
		
		while(_countExecutables>index){
			_commandDet = commandList.get(index);
			if (serverId.equals(_commandDet.getServerID())){
				_commandList.add(_commandDet);
			}
			index++;
		}
		return _commandList;
	}
	/**
	 * For populating the Server details in the Hash table
	 */
	private void populateServers(){
		serverMap = new Hashtable<String, AtomServerDetails>();
		AtomServerDetails _atomServer = null;
		String _serverId;
		int _tokenCount;
		
		while(serverFileScanner.hasNextLine()){
			String _serverDetails = serverFileScanner.nextLine();
			StringTokenizer _tokens = new StringTokenizer(_serverDetails);
			_atomServer = new AtomServerDetails();
			_tokenCount = _tokens.countTokens();
			switch (_tokenCount)
			{
			case 3:
				_atomServer.setServerId(_tokens.nextToken());
				_atomServer.setServerName(_tokens.nextToken());
				_atomServer.setServerIP(_tokens.nextToken());
				_atomServer.setLoginUserName(serverLoginUserName);
				_atomServer.setLoginPassword(serverLoginPassword);
				break;
			case 5:
				_atomServer.setServerId(_tokens.nextToken());
				_atomServer.setServerName(_tokens.nextToken());
				_atomServer.setServerIP(_tokens.nextToken());
				_atomServer.setLoginUserName(_tokens.nextToken());
				_atomServer.setLoginPassword(_tokens.nextToken());
				break;
			case 7:
				_atomServer.setServerId(_tokens.nextToken());
				_atomServer.setServerName(_tokens.nextToken());
				_atomServer.setServerIP(_tokens.nextToken());
				_atomServer.setLoginUserName(_tokens.nextToken());
				_atomServer.setLoginPassword(_tokens.nextToken());
				_atomServer.setSqlLoginUserName(_tokens.nextToken());
				_atomServer.setSqlLoginPassword(_tokens.nextToken());
				break;
			default:
				_atomServer.setServerId(_tokens.nextToken());
				_atomServer.setServerName(InvalidServer);
			}
			
			_serverId = _atomServer.getServerId();
			serverMap.put(_serverId, _atomServer);
		}
		
	}
	
	
	/**
	 * @return the serverMap
	 */
	public Hashtable<String, AtomServerDetails> getServerMap() {
		return serverMap;
	}

	
	
	
	
	
	
	/**
	 * @param aa
	 *
	 */
 	public static void main(String aa[]){
		AtomServers as = null;
		AtomCommands ac = null;
		AtomExecutables ae = null;
		try {
			ae = new AtomExecutables();
			ac = new AtomCommands();
			as = new AtomServers(ac,ae);
		} catch (CheckedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.println("*******************************************");
		System.out.println("Printing the Server Details of each line") ;
		System.out.println("*******************************************");
		
		Hashtable<String,AtomServerDetails> servers = as.serverMap;
		AtomServerDetails _serverDetails = servers.get("0004");
		System.out.println("Server ID "+_serverDetails.getServerId());
		System.out.println("Server name "+_serverDetails.getServerName());
		System.out.println("Server IP "+_serverDetails.getServerIP());
		
		Hashtable<String,AtomCommand> _commands = _serverDetails.getCommandList();
		ArrayList<AtomExecutableCommand> _executables = _serverDetails.getExecutableCommandList();
		System.out.println(_executables.size());
		int index=0;
		while(_executables.size()>index){
			System.out.println("Command Id "+_commands.get(_executables.get(index).getExecutableId()).getCommandID());
			System.out.println("Command Id "+_commands.get(_executables.get(index).getExecutableId()).getCommandLine());
			System.out.println("Command Id "+_commands.get(_executables.get(index).getExecutableId()).getCommand());
			System.out.println("Command Id "+_commands.get(_executables.get(index).getExecutableId()).getServerID());
			index++;
		}
		
		
	}
	
	
} // End of the Class
