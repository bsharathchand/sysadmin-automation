package com.org.commons.atomcore;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import com.org.commons.atomcore.helpers.AtomExecutableCommand;
import com.org.commons.atomcore.exceptions.CheckedException;
import com.org.commons.atomcore.helpers.AtomCommand;
import com.org.commons.atomcore.helpers.AtomRunner;
import com.org.commons.atomcore.helpers.AtomServerDetails;
import com.org.commons.atomcore.sequel.AtomSqlInterface;
import com.org.commons.atomcore.shell.AtomShellInterface;

/**
 * @author Sharath
 * Main Class which executes the thread for getting the results..
 * 
 */
public class AtomCoreExecute {
	Hashtable<String,AtomServerDetails> servers;
	ArrayList<AtomRunner> runnerList;
	/**
	 * The path of the File where suppressed errors are logged.
	 */
	public static final String ErrorLog = ".\\AtomErrors\\Error.log";
	/**
	 * Fetches the Executable Commands, Command List, Server List etc.,
	 * @throws CheckedException 
	 */
	public AtomCoreExecute() throws CheckedException{
		AtomCommands _commands = new AtomCommands();
		AtomExecutables _executables = new AtomExecutables();
		AtomServers _servers = new AtomServers(_commands,_executables);
		this.servers = _servers.getServerMap();
		this.runnerList = new ArrayList<AtomRunner>();
	}
	
	/**
	 * Prepare the Runners List...
	 */
	public void prepareRunnerList(){
		AtomServerDetails _server;
		Enumeration<String> _serverIds;
		ArrayList<AtomCommand> _runnerCommandList;
		AtomRunner _runner;
		String _serverId;
		boolean _hasMoreElements;
		
		_serverIds = this.servers.keys();
		_hasMoreElements = _serverIds.hasMoreElements();
		while(_hasMoreElements){
			_serverId = _serverIds.nextElement();
			_server = this.servers.get(_serverId);
			
			_runnerCommandList = prepareRunnerCommandList(_server);
			if(_runnerCommandList.size()==0){
				_hasMoreElements = _serverIds.hasMoreElements();
				continue;
			}
			_runner = new AtomRunner(_runnerCommandList,_server);
			this.runnerList.add(_runner);
			_hasMoreElements = _serverIds.hasMoreElements();
		}
	}
	
	private ArrayList<AtomCommand> prepareRunnerCommandList(
			AtomServerDetails server) {
		ArrayList<AtomCommand> _finalCommadList;
		ArrayList<AtomExecutableCommand> _executables;
		Hashtable<String,AtomCommand> _commands;
		AtomCommand _command;
		AtomExecutableCommand _executable;
		
		int _index=0;
		int _countExecutables;
		_finalCommadList = new ArrayList<AtomCommand>();
		_commands = server.getCommandList();
		_executables = server.getExecutableCommandList();
		_countExecutables = _executables.size();
		while(_countExecutables>_index){
			_executable = _executables.get(_index);
			_command = _commands.get(_executable.getExecutableId());
			_finalCommadList.add(_command);
			_index++;
		}
		return _finalCommadList;
	}

	/**
	 * Starts the process 
	 * Should be called only after preparing the Runners. 
	 * This method will start executing the runners one by one in separate threads. 
	 * Hope everything goes good... :)
	 * @throws CheckedException 
	 * 
	 */
	public void startRunners() throws CheckedException{
		AtomRunner _runner;
		int _runnerCount;
		_runnerCount = this.runnerList.size();
		if(_runnerCount==0){
			throw new CheckedException("Runner List Not Prepared");
		}
		for(int _index=0;_runnerCount > _index;_index++){
			_runner = runnerList.get(_index);
			threadShellRunner(_runner);
			threadSqlRunner(_runner);
		}
	}
	/*
	 * This method will execute the commands of each runner in a separate thread.
	 * The results for the executeSqlRunner can be caught and used for logging.
	 * This method will log all the exceptions raised in Error.log
	 */	
	private void threadShellRunner(AtomRunner runner){
		final AtomRunner _runner;
		_runner = runner;
		Thread _thread = new Thread(){
			public void run(){
				try {
					AtomCoreExecute.executeShellRunner(_runner);
				} catch(CheckedException ce){
					FileResults _logger=null;
					StringBuffer _message;
					_message = new StringBuffer("\n******************************************");
					_message.append("\n");
					_message.append((new Date()).toString());
					_message.append("\n******************************************");
					_message.append("\nError Raised Details given below:");
					_message.append("\nError Message :"+ce.getCustomMessage());
					_message.append("\nServer : "+_runner.getServer().getServerIP());
					_message.append("\n******************************************");
					try {
						_logger = new FileResults(AtomCoreExecute.ErrorLog);
						_logger.logResults(_message);
						_logger.closeLogging();
					} catch (CheckedException e) {}
				}
			}
		};
		_thread.start();
	}
		
	private static StringBuffer executeShellRunner(AtomRunner runner) throws CheckedException{
		
		/*
		 * 
		 * Code to be added to call the Shell Interface with Runner as parameter.
		 * 
		 */
		StringBuffer _bufferResult;
		AtomShellInterface _shellInterface;
		_shellInterface = new AtomShellInterface(runner);
		_bufferResult = _shellInterface.executeRunner();
		return _bufferResult;
	}
	/*
	 * This method will execute the commands of each runner in a separate thread.
	 * The results for the executeSqlRunner can be caught and used for logging.
	 * This method will log all the exceptions raised in Error.log
	 */	
	private void threadSqlRunner(AtomRunner runner) throws CheckedException{
		final AtomRunner _runner;
		_runner = runner;
		Thread _thread = new Thread(){
			public void run(){
				try {
					AtomCoreExecute.executeSqlRunner(_runner);
				} catch(CheckedException ce){
					FileResults _logger=null;
					StringBuffer _message;
					_message = new StringBuffer("\n******************************************");
					_message.append("\n");
					_message.append((new Date()).toString());
					_message.append("\n******************************************");
					_message.append("\nError Raised Details given below:");
					_message.append("\nError Message :"+ce.getCustomMessage());
					_message.append("\nServer : "+_runner.getServer().getServerIP());
					_message.append("\n******************************************");
					try {
						_logger = new FileResults(AtomCoreExecute.ErrorLog);
						_logger.logResults(_message);
						_logger.closeLogging();
					} catch (CheckedException e) {}
				}
			}
		};
		_thread.start();
	}
	
	private static StringBuffer executeSqlRunner(AtomRunner runner) throws CheckedException{
		
		/*
		 * 
		 * Code to be added to call the Shell Interface with Runner as parameter.
		 * 
		 */
		StringBuffer _bufferResult;
		AtomSqlInterface _sqlInterface;
		_sqlInterface = new AtomSqlInterface(runner);
		_bufferResult = _sqlInterface.executeRunner();
		return _bufferResult;
	}

}
