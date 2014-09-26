package com.org.commons.atomcore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.org.commons.atomcore.exceptions.CheckedException;
import com.org.commons.atomcore.helpers.AtomCommand;


/**
 * @author Sharath
 * 
 * This will read the file AtomCmdDetails and list all the commands in it.
 * 
 * Calling the Constructor will populate the Command List also.
 * 
 * After the call to Constructor we can directly get the commands using the 
 * getCommandList(); method on the newly created object.
 * 
 */

public class AtomCommands {
	private File commandFile;
	private Scanner commandScanner;
	private Hashtable<String, AtomCommand> commandList;
	/**
	 * Final String for the String to maintain Consistency checks.
	 */
	public static final String InvalidCommand = "INVALID COMMAND";
	
	/**
	 * This file contails the list of commands and details
	 */
	public static final String CommandFileName = ".\\System\\AtomCmdDetails";
	
	/**
	 * @throws CheckedException
	 */
	public AtomCommands() throws CheckedException
	{
		commandFile = new File(CommandFileName);
		if(commandFile.exists())
			try {
				commandScanner = new Scanner(commandFile);
			} catch (FileNotFoundException e) {
				CheckedException _exception = new CheckedException(e);
				_exception.setCustomMessage("Unable to open the Command File");
			}
		else {
			throw new CheckedException("Command File Not Found");
		}
		commandList = new Hashtable<String,AtomCommand>();
		this.pupulateCommands();
	}
	
	/**
	 * Used for populating the Commands in to a Collection
	 */
	public void pupulateCommands()
	{
		
		AtomCommand _acommand = null;
		String _commandId=null;
		
		while(commandScanner.hasNextLine()){
			String _commandLine;
			StringTokenizer _tokens;
			int _tokenCount;
			_acommand = new AtomCommand();
			_commandLine = commandScanner.nextLine();
			_acommand.setCommandLine(_commandLine);
			_tokens = new StringTokenizer(_commandLine,";");
			_tokenCount = _tokens.countTokens();
			if (_tokenCount!=3){
				_acommand.setCommand(InvalidCommand);
				continue;
			}
			else
			{
				_acommand.setCommandDescription(_tokens.nextToken());
				_acommand.setCommand(_tokens.nextToken());
				_acommand.setCommandID(_tokens.nextToken());
			}
			
			_commandId = _acommand.getCommandID();
			this.commandList.put(_commandId,_acommand);		
		}		
	}
	

	/**
	 * @return Hash table<String, AtomCommand>
	 */
	public Hashtable<String, AtomCommand> getCommandList() {
		return commandList;
	}
	
	
	
	/**
	 * @param aa
	 *
	 */
 	public static void main(String aa[]){
		AtomCommands ac = null;
		try {
			 ac = new AtomCommands();
		} catch (CheckedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.println("*******************************************");
		System.out.println("Printing the tokes of each line") ;
		System.out.println("*******************************************");
		Enumeration<String> _commandIds;
		_commandIds = ac.getCommandList().keys();
		System.out.println(ac.getCommandList().get("0005-0001")); 
		boolean _hasmoreElements = _commandIds.hasMoreElements();
		AtomCommand _command;
		while(_hasmoreElements){
			_command = ac.getCommandList().get(_commandIds.nextElement());
			System.out.println("*******************************************");
			System.out.println("Command :" + _command.getCommandLine());
			System.out.println("Command :" + _command.getCommand());
			System.out.println("Command :" + _command.getCommandID());
			System.out.println("Command :" + _command.getServerID());
			System.out.println("Command :" + _command.getCommandDescription());
			System.out.println("*******************************************");
			_hasmoreElements = _commandIds.hasMoreElements();
		}
	}

}
