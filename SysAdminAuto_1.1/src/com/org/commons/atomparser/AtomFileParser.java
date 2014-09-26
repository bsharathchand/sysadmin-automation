/**
 * 
 */
package com.org.commons.atomparser;

import java.io.File;
import java.util.ArrayList;


import com.org.commons.atomparser.exceptions.CheckedException;
import com.org.commons.atomparser.helpers.MonitorObject;
import com.org.commons.atomparser.helpers.MonitoredCommand;
import com.org.commons.atomparser.parser.AtomDiskUtilizationProcessor;
import com.org.commons.atomparser.parser.FileParser;

/**
 * @author Sharath
 *
 */
public class AtomFileParser {
	
	/**
	 * 
	 */
	public static String ParseDirectory = ".//Atom Results";
	
	private MonitoredCommands monitoredCommands;
	private ArrayList<MonitoredCommand> commandList;
	
	AtomFileParser() throws CheckedException{
		this.monitoredCommands = new MonitoredCommands(); 
		this.commandList = monitoredCommands.getCommandList();
	}
	
	/**
	 * @throws CheckedException
	 */
	public void parseCommands() throws CheckedException{
		int _commandCount;
		MonitoredCommand _command;
	
		_commandCount = this.commandList.size();
		for(int _index=0;_index<_commandCount;_index++){
			_command = this.commandList.get(_index);
			parseCommandInFiles(_command);
		}
	}
	
	private void parseCommandInFiles(MonitoredCommand command) throws CheckedException{
		
		File _parseDirectory;
		File [] _files;
		_parseDirectory = new File(ParseDirectory);
		if(_parseDirectory.isDirectory()){
			_files = _parseDirectory.listFiles();
		}
		else{
			throw new CheckedException("ParseDirectory is not a directory for parsing");
		}
		for(int _index=0;_index<_files.length;_index++){
			System.out.println("*******************************************");
			System.out.println("Parsing for file :"+_files[_index].getName());
			System.out.println("*******************************************");
			parseCommandInFile(command,_files[_index]);
		}
		
	}
	
	private void parseCommandInFile(MonitoredCommand command,File file) throws CheckedException{
		String _commandPattern;
		String _rowId;
		String _columnId;
		String _monitoredElement;
		String _threshold;
		FileParser _parser;
		String _hasHeader;
		String _fileName;
		ArrayList<MonitorObject> _monitorsList;
		
		_fileName = file.getName();
		_commandPattern = command.getCommandPattern();
		_rowId = command.getRowId();
		_columnId = command.getColumnId();
		_monitoredElement = command.getMonitoredElement();
		_threshold = command.getThresholdLimit();
		_hasHeader = command.getHasHeader();
		
		_parser = new FileParser(file,_commandPattern,_rowId,_columnId,_hasHeader,_monitoredElement);
		_monitorsList = _parser.getMonitorObjectDetails();
		fileResults(_fileName,_threshold,_monitorsList);
	}
	
	private void fileResults(String fileName,String threshold,ArrayList<MonitorObject> monitors) throws CheckedException{
		/*
		 * Code for filing the results will start here...
		 */


		/*
		 * Code for filing the results will start here...
		 */
		
		AtomDiskUtilizationProcessor _fileDiskGraph;
		_fileDiskGraph = new AtomDiskUtilizationProcessor(fileName,"",threshold);
		_fileDiskGraph.parseMonitorObjects(monitors);
	}
	
	/**
	 * @param aa
	 */
	public static void main(String aa[]){
		AtomFileParser _parser;
		try {
			_parser = new AtomFileParser();
			_parser.parseCommands();
		} catch (CheckedException e) {
			System.out.println(e.getCustomMessage());
			e.printStackTrace();
		}
	}

}
