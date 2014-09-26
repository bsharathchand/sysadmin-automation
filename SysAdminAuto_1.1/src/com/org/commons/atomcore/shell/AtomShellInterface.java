/**
 * 
 */
package com.org.commons.atomcore.shell;

import java.util.ArrayList;

import com.org.commons.atomcore.FileResults;
import com.org.commons.atomcore.exceptions.CheckedException;
import com.org.commons.atomcore.helpers.AtomCommand;
import com.org.commons.atomcore.helpers.AtomRunner;
import com.org.commons.atomcore.helpers.AtomServerDetails;
import com.org.commons.atomcore.shell.exceptions.AtomShellException;
import com.org.commons.atomcore.shell.helpers.AtomShellCommand;

/**
 * @author Sharath
 *
 */
public class AtomShellInterface {
	AtomRunner runner;
	AtomShellCommand shellCommand;
	/**
	 * This variable stores the path where the results of each server is stored.
	 */
	public static final String ShellResultsPath = ".\\Atom Results\\";
	/**
	 * This variable stores the path where the Complete Log of all Runners is Stored.
	 */
	public static final String LogFile = ".\\AtomLogs\\Complete.log";
	/**
	 * This variable is to be set if we need to maintain the Complete Log History.
	 * Not Advisable.. Suggested Only for debugging purposes. NOT WORKING AS SUPPOSED
	 */
	public static final boolean LogHistory = true;
	/**
	 * @param runner
	 * This procedures execute the runner Shell Commands only.
	 */
	public AtomShellInterface(AtomRunner runner){
		this.runner = runner;
		this.prepareShellCommand();
	}
	
	private void prepareShellCommand(){
		AtomShellCommand _commandSet = null;
		ArrayList<String> _commandList;
		AtomServerDetails _server;
		
		_server = this.runner.getServer();
		_commandList = splitRunnerCommandList();
		if(_commandList.size()> 0){
			_commandSet = new AtomShellCommand();
			_commandSet.setLoginPassword(_server.getLoginPassword());
			_commandSet.setLoginUserName(_server.getLoginUserName());
			_commandSet.setServerIp(_server.getServerIP());
			_commandSet.setCommand(_commandList);
		}
		this.shellCommand = _commandSet;
	}

	private ArrayList<String> splitRunnerCommandList() {
		ArrayList<AtomCommand> _runnerCommandList;
		ArrayList<String> _commandList;
		AtomCommand _atomCommand;
		String _command;
		String _commandId;
		int _index = 0;
		int _countRunnerCommand;
		_commandList = new ArrayList<String>();
		_runnerCommandList = this.runner.getFinalCommandList(); 
		_countRunnerCommand = _runnerCommandList.size();
		while(_countRunnerCommand>_index){
			_atomCommand = _runnerCommandList.get(_index);
			_command = _atomCommand.getCommand();
			_commandId = _atomCommand.getCommandID();
			// Filters the SQL Commands
			if(_commandId.startsWith("Q")){
				_index++;
				continue;
			}
			_commandList.add(_command);
			_index++;
		}
		return _commandList;
	}
	
	/**
	 * @return _result Command Result to File..
	 * @throws CheckedException 
	 * 
	 */
	public StringBuffer executeRunner() throws CheckedException{
		StringBuffer _result;
		ShellProcessor _shellProcessor = null;
		String _ServerFileName;
		_result = new StringBuffer("\n\nDate & Time "+(new java.util.Date()).toString());
		_result.append("\n");
		if(this.shellCommand!=null){
			try {
				_shellProcessor = new ShellProcessor(this.shellCommand);
				_shellProcessor.execute();
				_result.append(_shellProcessor.getResult());
			} catch (AtomShellException e) {
				CheckedException ce = new CheckedException(e);
				ce.setCustomMessage(e.getCustomMessage());
				throw ce;
			}
		}else {
			_result.append("\n*******************************************************\n");
			_result.append("No Shell Commands for the Server :");
			_result.append("\n*******************************************************\n");
			return _result;
		}
		_ServerFileName = ShellResultsPath+this.shellCommand.getServerIp().toString();
		logResults(_ServerFileName,_result);
		return _result;
	}
	
	private void logResults(String fileName,StringBuffer results) throws CheckedException{
		FileResults _fileResults;
		// Logging for Individual Runners
		_fileResults = new FileResults(fileName);
		_fileResults.logResults(results);
		_fileResults.closeLogging();
		// Logging for Complete Log
		_fileResults = new FileResults(AtomShellInterface.LogFile,AtomShellInterface.LogHistory);
		_fileResults.logResults(results);
		_fileResults.closeLogging();
	}

}
