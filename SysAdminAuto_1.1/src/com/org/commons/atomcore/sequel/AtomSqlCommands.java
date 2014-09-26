/**
 * 
 */
package com.org.commons.atomcore.sequel;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import com.org.commons.atomcore.AtomCommands;
import com.org.commons.atomcore.helpers.AtomCommand;
import com.org.commons.atomcore.sequel.helpers.AtomSqlCommand;

/**
 * @author Sharath
 *
 */
public class AtomSqlCommands {
	private Hashtable<String,AtomCommand> commandList;
	/**
	 * @param _commands
	 */
	public AtomSqlCommands(AtomCommands _commands){
		this.commandList = _commands.getCommandList();
		prepareSqlCommands();
	}
	
	private void prepareSqlCommands(){
		Enumeration<String> _commandIds;
		AtomCommand _command;
		String _commandId;
		int _countCommands;
		int _index = 0;
		_commandIds = this.commandList.keys();
		
		_countCommands = this.commandList.size();
		while(_countCommands>_index){
			_commandId = _commandIds.nextElement();
			if(_commandId.startsWith("Q")){
				_command = this.commandList.get(_commandId);
				try{
				createSequelFromCommand(_command);
				}catch(NumberFormatException nfe){
					System.out.println("Invalid SQL Command "+_command.getCommand());
				}
			}
			_index++;
		}
	}
	
	private void createSequelFromCommand(AtomCommand _command){
		String _commandExtracted;
		String _sqlStatement;
		ArrayList<Integer> _getColumns;
		StringTokenizer _tokens;
		AtomSqlCommand _sqlCommand;
		int _index = 0;
		int _countTokens;
		_sqlStatement = null;
		_getColumns = new ArrayList<Integer>();
		_commandExtracted = _command.getCommand();
		_tokens = new StringTokenizer(_commandExtracted,",");
		_countTokens = _tokens.countTokens();
		while(_countTokens > _index){
			if(_index==0){
				_sqlStatement = _tokens.nextToken();
			}
			else{
				_getColumns.add(Integer.parseInt(_tokens.nextToken()));
			}
			_index++;
		}
		_sqlCommand = new AtomSqlCommand();
		_sqlCommand.setColumnNos(_getColumns);
		_sqlCommand.setSqlStatement(_sqlStatement);
	}
		
	

}
