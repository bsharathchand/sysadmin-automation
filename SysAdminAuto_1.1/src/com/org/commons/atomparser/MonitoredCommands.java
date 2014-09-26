/**
 * 
 */
package com.org.commons.atomparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.org.commons.atomparser.exceptions.CheckedException;
import com.org.commons.atomparser.helpers.MonitoredCommand;

/**
 * @author Sharath
 *
 */

public class MonitoredCommands {
	
	/**
	 * File containing the details of the Monotirable Commands
	 */
	public static String FileName = ".//System//MonitoredCommands";
	/**
	 * Is the count of tokens as per the template
	 */
	public static int TokenCount = 7;
	/**
	 * Consistency Variable
	 */
	public static String InvalidFile = "FILE_CORRUPTED_OR_COMMAND_INVALID";
	private File commandFile; 
	private ArrayList<MonitoredCommand> commandList;
	
	/**
	 * @throws CheckedException 
	 * 
	 */
	public MonitoredCommands() throws CheckedException{
		commandFile = new File(FileName);
		populateCommands();
	}
	
	private void populateCommands() throws CheckedException{
		Scanner _fileScanner;
		StringTokenizer _tokens;
		String _commandLine;
		
		int _countTokens;
		MonitoredCommand _command;
		ArrayList<MonitoredCommand> _tempList;
		_tempList = new ArrayList<MonitoredCommand>();
		try {
			_fileScanner = new Scanner(this.commandFile);
			
			for(boolean _hasNextLine = _fileScanner.hasNextLine();_hasNextLine;_hasNextLine = _fileScanner.hasNextLine()){
				_commandLine = _fileScanner.nextLine();
				_tokens = new StringTokenizer(_commandLine,";");
				_countTokens = _tokens.countTokens();
				_command = new MonitoredCommand();
				if(_countTokens != TokenCount){
					_command.setCommandPattern(InvalidFile);
					_tempList.add(_command);
					continue;
				}
				_command.setCommandDescription(_tokens.nextToken());
				_command.setCommandPattern(_tokens.nextToken());
				_command.setHasHeader(_tokens.nextToken());
				_command.setRowId(_tokens.nextToken());
				_command.setColumnId(_tokens.nextToken());
				_command.setThresholdLimit(_tokens.nextToken());
				_command.setMonitoredElement(_tokens.nextToken());
				_tempList.add(_command);
			}
			this.setCommandList(_tempList);
		} catch (FileNotFoundException e) {
			CheckedException ce = new CheckedException(e);
			ce.setCustomMessage("Error while parsing the Monitored commands file");
			throw ce;
		}
	}

	/**
	 * @param commandList the commandList to set
	 */
	public void setCommandList(ArrayList<MonitoredCommand> commandList) {
		this.commandList = commandList;
	}

	/**
	 * @return the commandList
	 */
	public ArrayList<MonitoredCommand> getCommandList() {
		return commandList;
	}
	
	/**
	 * @param aa
	 */
	public static void main(String aa[]){
		try {
			MonitoredCommands _mc = new MonitoredCommands();
			ArrayList<MonitoredCommand> _list = _mc.getCommandList();
			
			System.out.println(_list.size());
			System.out.println(_list.get(0).getCommandPattern());
		} catch (CheckedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
