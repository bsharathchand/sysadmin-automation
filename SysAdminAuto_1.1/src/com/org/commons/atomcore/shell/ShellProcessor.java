package com.org.commons.atomcore.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.org.commons.atomcore.shell.exceptions.AtomShellException;
import com.org.commons.atomcore.shell.helpers.AtomShellCommand;

/**
 * @author Sharath
 *
 */
public class ShellProcessor {
	
	private Connection connectServer;
	private String hostName;
	private String loginUserName;
	private String loginPassword;
	private StringBuffer result;
	private ArrayList<String> commands;
	/**
	 * Final String for the String to maintain Consistency checks.
	 */
	public static final String CurruptedData = "CURRUPTED DATA";
	
	/**
	 * @param commands 
	 * @throws AtomShellException
	 */
	public ShellProcessor(AtomShellCommand commands) throws AtomShellException{
		this.hostName = commands.getServerIp();
		this.loginUserName = commands.getLoginUserName();
		this.loginPassword = commands.getLoginPassword();
		this.result = new StringBuffer();
		this.commands = commands.getCommand();
		connectToRemoteShell();
	}
	
	private void connectToRemoteShell() throws AtomShellException{
		boolean _isAuthenticated = false;
		this.connectServer = new Connection(this.hostName);
		/*
		 * Prepare Connection Connection
		 */
		try {
			connectServer.connect();
		} catch (IOException e) {
			AtomShellException _shellException = new AtomShellException(e);
			_shellException.setCustomMessage("Unable to connect to Server IP : "+this.hostName);
		}
		/*
		 * Complete Connection to the server with Authentication
		 */
		try {
			_isAuthenticated = connectServer.authenticateWithPassword(loginUserName, loginPassword);
			if (!_isAuthenticated)
				throw new AtomShellException("Unable to authenticate username: "+this.loginUserName);
		} catch (IOException e) {
			AtomShellException _shellException = new AtomShellException(e);
			_shellException.setCustomMessage("User Authentication Failure");
			throw _shellException;
		} catch (IllegalStateException e) {
			AtomShellException _shellException = new AtomShellException(e);
			_shellException.setCustomMessage("Connection is not Esablished");
			throw _shellException;
		}
		
	}
	
	/**
	 * This is to be called to execute the runner after the initialization of ShellProcessor
	 * It will create new session on the server to execute the command in the runner.
	 * 
	 * 
	 */
	public void execute(){
		Session session = null;
		String _commandString;
		String _output;
		int _countCommands = 0;
		int _index = 0;
		
		/*
		 * Executing the commands sequentially for a server
		 */
		this.result.append("*******************************************************\n");
		this.result.append("Started processing Unix Commands On Server :");
		this.result.append(this.hostName);
		this.result.append("\n");
		this.result.append("*******************************************************\n");
		_countCommands = this.commands.size();
		while(_countCommands>_index){
			_commandString = this.commands.get(_index);
			this.result.append("Executing :");
			this.result.append(_commandString);
			this.result.append("\n");
			this.result.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
			
			try {
				session = connectServer.openSession();
				_output = executeCommand(session,_commandString);
				this.result.append(_output);
			} catch (IOException e) {
				AtomShellException _shellException = new AtomShellException(e);
				_shellException.setCustomMessage("Unable to Open a Session on Server IP :"+this.hostName);
			} catch (AtomShellException e) {
				this.result.append(e.getCustomMessage());
				/*
				 * Add the code to write errors in a file.... 
				 */
			}
			this.result.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
			_index++;
		}
		session.close();
		connectServer.close();
	}

	private String executeCommand(Session session,String commandString) throws AtomShellException{
		InputStream _resultOut ;
		BufferedReader _buffer;
		StringBuffer _storageBuffer;
		String _outputLine;
		try {
			session.execCommand(commandString);
		} catch (IOException e) {
			e.printStackTrace();
			AtomShellException _shellException = new AtomShellException(e);
			_shellException.setCustomMessage("Failed while executing the command in Session on server "+this.hostName+"\n");
			throw _shellException;
		}
		_resultOut = new StreamGobbler(session.getStdout());
		_buffer = new BufferedReader(new InputStreamReader(_resultOut));
		_storageBuffer = new StringBuffer();
		while (true)
		{
			try {
				_outputLine = _buffer.readLine();
			} catch (IOException e) {
				_outputLine = CurruptedData;
			}
			if (_outputLine == null)
				break;
			_storageBuffer.append(_outputLine);
			_storageBuffer.append("\n");
		}
		return _storageBuffer.toString();
	}
	
	/**
	 * @return the result
	 */
	public StringBuffer getResult() {
		return result;
	}

}
