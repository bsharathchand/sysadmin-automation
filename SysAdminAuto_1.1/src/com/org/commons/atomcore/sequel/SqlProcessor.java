/**
 * 
 */
package com.org.commons.atomcore.sequel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.org.commons.atomcore.sequel.exceptions.AtomSqlException;
import com.org.commons.atomcore.sequel.helpers.AtomSequelCommands;
import com.org.commons.atomcore.sequel.helpers.AtomSqlCommand;

/**
 * @author Sharath
 *
 */
public class SqlProcessor {
	private AtomSequelCommands commandset;
	private String serverIp;
	private String loginUserName;
	private String loginPassword;
	private StringBuffer results;
	private final String port = "2325";
	private Connection connection;
	
	/**
	 * @param _commands
	 * @throws AtomSqlException 
	 */
	public SqlProcessor(AtomSequelCommands _commands) throws AtomSqlException{
		this.serverIp = _commands.getServerIp();
		this.loginPassword = _commands.getLoginPassword();
		this.loginUserName = _commands.getLoginUserName();
		this.commandset = _commands; 
		this.results = new StringBuffer();
		connectToRemoteServer();
	}
	
	private void connectToRemoteServer() throws AtomSqlException{
		Connection _connect = null;
		StringBuffer _connectionString = new StringBuffer(); 
		_connectionString.append("jdbc:sqlserver://");
		_connectionString.append(this.serverIp);
		_connectionString.append(":");
		_connectionString.append(this.port);
		_connectionString.append(";");
		_connectionString.append("databaseName=master;integratedSecurity=false;");
		
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			_connect = DriverManager.getConnection(_connectionString.toString(),this.loginUserName,this.loginPassword);
		}
		catch(ClassNotFoundException cnfe){
			AtomSqlException _sqlException = new AtomSqlException(cnfe);
			_sqlException.setCustomMessage("JDBC Driver does not exist");
			throw _sqlException;
		} catch (SQLException e) {
			AtomSqlException _sqlException = new AtomSqlException(e);
			_sqlException.setCustomMessage("Unknown SQL Exception while connecting");
			throw _sqlException;
		}
		this.connection = _connect;
	}
	
	/**
	 * @throws AtomSqlException 
	 * 
	 */
	public void execute() throws AtomSqlException{
		AtomSequelCommands _commandset;
		String _sqlStatement;
		ArrayList<Integer> _columnList;
		ArrayList<AtomSqlCommand> _commands;
		AtomSqlCommand _command;
		StringBuffer _result;
		int _index = 0;
		int _countCommands;
		_commandset = this.commandset;
		
		_commands = _commandset.getCommandList();
		_countCommands = _commands.size();
		this.results.append("*******************************************************\n");
		this.results.append("Started processing SQL Statements On Server :");
		this.results.append(this.serverIp);
		this.results.append("\n");
		this.results.append("*******************************************************\n");
		
		while(_countCommands>_index){
			_command = _commands.get(_index);
			_sqlStatement = _command.getSqltStatement();
			_columnList = _command.getColumnNos();
			this.results.append("Executing :");
			this.results.append(_sqlStatement);
			this.results.append("\n");
			this.results.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
			
			try {
				_result = executeQuery(_sqlStatement,_columnList);
				this.results.append(_result);
			} catch (AtomSqlException e) {
				this.results.append(e.getCustomMessage());
			}
			this.results.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
			_index++;
		}
		if(this.connection!=null){
			try {
				this.connection.close();
			} catch (SQLException e) {
				AtomSqlException _sqlException = new AtomSqlException(e);
				_sqlException.setCustomMessage("Failed While Closing Connection");
				throw _sqlException;
			}
		}
	}
	
	private StringBuffer executeQuery(String queryStatement,ArrayList<Integer> _columnList) throws AtomSqlException{
		Statement _query = null;
		ResultSet _resultSet = null;
		StringBuffer _result ;
		int _columnsCount;
		_columnsCount = _columnList.size();
		_result = new StringBuffer();
		try {
			_query = this.connection.createStatement();
			_resultSet = _query.executeQuery(queryStatement);
			while(_resultSet.next()){
				for(int _index=0;_columnsCount>_index;_index++){
					_result.append(_resultSet.getString(_columnList.get(_index)));	
				}
				_result.append("\n");
			}
		} catch (SQLException e) {
			AtomSqlException _sqlException = new AtomSqlException(e);
			_sqlException.setCustomMessage("Failed While Creating/Executing Statement");
			throw _sqlException;
		}
		finally{
			try {
				_query.close();
				_resultSet.close();
			} catch (SQLException e) {
				AtomSqlException _sqlException = new AtomSqlException(e);
				_sqlException.setCustomMessage("Failed While Closing Statement");
				throw _sqlException;
			}
		}
		return _result;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(StringBuffer results) {
		this.results = results;
	}

	/**
	 * @return the results
	 */
	public StringBuffer getResults() {
		return results;
	}
}
