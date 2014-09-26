/**
 * 
 */
package com.org.commons.atomcore.sequel;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.org.commons.atomcore.FileResults;
import com.org.commons.atomcore.exceptions.CheckedException;
import com.org.commons.atomcore.helpers.AtomCommand;
import com.org.commons.atomcore.helpers.AtomRunner;
import com.org.commons.atomcore.helpers.AtomServerDetails;
import com.org.commons.atomcore.sequel.exceptions.AtomSqlException;
import com.org.commons.atomcore.sequel.helpers.AtomSequelCommands;
import com.org.commons.atomcore.sequel.helpers.AtomSqlCommand;


/**
 * @author Sharath
 *
 */
public class AtomSqlInterface {
	AtomRunner runner;
	AtomSequelCommands sqlCommand;
	/**
	 * This variable stores the path where the results of each server is stored.
	 */
	public static final String SqlResultsPath = ".\\Atom Results\\";
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
	 */
	public AtomSqlInterface(AtomRunner runner){
		this.runner = runner;
		this.prepareSqlCommand();
	}

	private void prepareSqlCommand() {
		AtomSequelCommands _commandSet = null;
		ArrayList<AtomSqlCommand> _commandList;
		AtomServerDetails _server;
		
		_server = this.runner.getServer();
		_commandList = splitRunnerCommandList();
		if(_commandList.size() > 0){
		_commandSet = new AtomSequelCommands();
		_commandSet.setLoginPassword(_server.getSqlLoginPassword());
		_commandSet.setLoginUserName(_server.getSqlLoginUserName());
		_commandSet.setServerIp(_server.getServerIP());
		_commandSet.setCommandList(_commandList);
		}
		this.sqlCommand = _commandSet;
	}
	
	private ArrayList<AtomSqlCommand> splitRunnerCommandList() {
		ArrayList<AtomCommand> _runnerCommandList;
		ArrayList<AtomSqlCommand> _commandList;
		AtomSqlCommand _atomSqlCommand;
		AtomCommand _atomCommand;
		String _commandId;
		String _command;
		int _index = 0;
		int _countRunnerCommand;
		_commandList = new ArrayList<AtomSqlCommand>();
		_runnerCommandList = this.runner.getFinalCommandList(); 
		_countRunnerCommand = _runnerCommandList.size();
		while(_countRunnerCommand>_index){
			_atomSqlCommand = new AtomSqlCommand();
			_atomCommand = _runnerCommandList.get(_index);
			_command = _atomCommand.getCommand();
			_commandId = _atomCommand.getCommandID();
			
			// Filters the Shell Commands
			if(_commandId.startsWith("S")){
				_index++;
				continue;
			}
			else{
				_atomSqlCommand = getSqlCommand(_command);
				_commandList.add(_atomSqlCommand);
			}
			_index++;
		}
		return _commandList;
	}
	
	private AtomSqlCommand getSqlCommand(String _command){
		AtomSqlCommand _atomSqlCommand;
		String _sqlStatement;
		StringTokenizer _tokens;
		ArrayList<Integer> _columnNos;
		int _countTokens;
		int _index=0;
		_sqlStatement = null;
		_columnNos = new ArrayList<Integer>();
		_atomSqlCommand = new AtomSqlCommand();
		_tokens = new StringTokenizer(_command,",");
		_countTokens = _tokens.countTokens();
		while(_countTokens>_index){
			if(_index==0)
				_sqlStatement = _tokens.nextToken();
			else{
				try{
				_columnNos.add(Integer.parseInt(_tokens.nextToken()));
				}catch(NumberFormatException nfe){
					_sqlStatement = AtomSqlCommand.InvalidCommand;
				}
			}
			_index++;
		}
		_atomSqlCommand.setSqlStatement(_sqlStatement);
		_atomSqlCommand.setColumnNos(_columnNos);
		return _atomSqlCommand;
	}
	
	/**
	 * @return _result Command Result to File..
	 * @throws CheckedException 
	 * 
	 */
	public StringBuffer executeRunner() throws CheckedException{
		StringBuffer _result;
		SqlProcessor _sqlProcessor = null;
		String _ServerFileName;
		_result = new StringBuffer("\nDate & Time "+(new java.util.Date()).toString());
		_result.append("\n");
		if(this.sqlCommand != null){
			try {
			_sqlProcessor = new SqlProcessor(this.sqlCommand);
			_sqlProcessor.execute();
			_result.append(_sqlProcessor.getResults());
			} catch (AtomSqlException e) {
				CheckedException ce = new CheckedException(e);
				ce.setCustomMessage(e.getCustomMessage());
				throw ce;
			}
		}
		else{
		_result.append("\n*******************************************************\n");
		_result.append("No Sequel Commands for the Server :");
		_result.append("\n*******************************************************\n");
		return _result;
		}
		_ServerFileName = SqlResultsPath+this.sqlCommand.getServerIp().toString();
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
		_fileResults = new FileResults(AtomSqlInterface.LogFile,AtomSqlInterface.LogHistory);
		_fileResults.logResults(results);
		_fileResults.closeLogging();
	}
}
