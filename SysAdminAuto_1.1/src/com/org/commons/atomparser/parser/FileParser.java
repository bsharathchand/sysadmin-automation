/**
 * 
 */
package com.org.commons.atomparser.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.org.commons.atomparser.exceptions.CheckedException;
import com.org.commons.atomparser.helpers.MonitorObject;

/**
 * @author Sharath
 *
 */
public class FileParser {
	
	/**
	 * 
	 */
	public static String TempFile = ".//AtomLogs//Temp";
	/**
	 * 
	 */
	public static String LineSeparator = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
	/**
	 * 
	 */
	public static String DatePrefix = "Date & Time";
	
	/**
	 * Used for custom fix code for Linux servers Disk utilization 
	 */
	public static int LinuxDiskTokenCount = 1;
	
	private File file;
	private String pattern;
	private String rowId;
	private String columnId;
	private String hasHeader;
	private String monitoredElement;
	private File tempFile;
	
	
	/**
	 * @param fileName
	 * @param pattern
	 * @param rowId
	 * @param columnId
	 * @param hasHeader
	 * @param monitoredElement 
	 * @throws CheckedException
	 */
	public FileParser(String fileName,String pattern,String rowId, String columnId, String hasHeader, String monitoredElement) throws CheckedException{
		this.tempFile = new File(TempFile);
		this.file = new File(fileName);
		this.pattern = pattern;
		this.rowId = rowId;
		this.columnId = columnId;
		this.hasHeader = hasHeader;
		this.monitoredElement = monitoredElement;
		parseFile();
	}
	/**
	 * @param file
	 * @param pattern
	 * @param rowId
	 * @param columnId
	 * @param hasHeader
	 * @param monitoredElement
	 * @throws CheckedException
	 */
	public FileParser(File file,String pattern,String rowId, String columnId, String hasHeader, String monitoredElement) throws CheckedException{
		this.tempFile = new File(TempFile);
		this.file = file;
		this.pattern = pattern;
		this.rowId = rowId;
		this.columnId = columnId;
		this.hasHeader = hasHeader;
		this.monitoredElement = monitoredElement;
		parseFile();
	}
	
	private void parseFile() throws CheckedException{
		Scanner _fileScanner;
        String _parseLine;
        StringBuffer _tempBuffer;
        boolean _hasNextLine;
        boolean _temp;
        int _patternIndex;
        String _skipLineSeparator;
        String _nextLine;
        try{
        _fileScanner = new Scanner(this.file);
        _hasNextLine = _fileScanner.hasNextLine();
        _tempBuffer = new StringBuffer();
        while(_hasNextLine){
            _parseLine = _fileScanner.nextLine();
            _temp = _parseLine.startsWith(DatePrefix);
            if(_temp){
                            _tempBuffer.append(_parseLine);
                            _tempBuffer.append("\n");
            }
            _patternIndex = _parseLine.indexOf(pattern);
            if(_patternIndex >= 0){
                _skipLineSeparator = "true";
                _hasNextLine = _fileScanner.hasNextLine();
                while(_hasNextLine && (!(_nextLine = _fileScanner.nextLine()).equals(LineSeparator)||(_skipLineSeparator.equals("true")))){
                    if(_skipLineSeparator.equals("true"))
                                    _skipLineSeparator = "false";
                    _tempBuffer.append(_nextLine);
                    _tempBuffer.append("\n");
                }
            }
            _hasNextLine = _fileScanner.hasNextLine();
        }
        storeToTempFile(_tempBuffer);
        }catch (FileNotFoundException e) {
			CheckedException ce = new CheckedException(e);
			ce.setCustomMessage("Unable to find the file to be parsed : "+this.file);
			throw ce;
		}
	}
	
	private void storeToTempFile(StringBuffer buffer) throws CheckedException{
		FileWriter _fileWriter;
		try{
			if(this.tempFile.exists()){
				_fileWriter = new FileWriter(this.tempFile);
			}
			else{
				this.tempFile.createNewFile();
				_fileWriter = new FileWriter(this.tempFile);
			}
			_fileWriter.flush();
			_fileWriter.write(buffer.toString());
			_fileWriter.close();
		}catch(IOException e){
			CheckedException ce = new CheckedException(e);
			ce.setCustomMessage("Error while writing to the temp File : "+TempFile);
			throw ce;
		}
	}
	
	/**
	 * @return
	 * 
	 * this method will read the details from the tempFile to create the Monitor Object.
	 * the created the MonitorObject will be returned back.
	 * @throws CheckedException 
	 * 
	 */
	public ArrayList<MonitorObject> getMonitorObjectDetails() throws CheckedException{
		ArrayList<MonitorObject> _monitors;
		_monitors = getMonitorsListFromFile(this.tempFile);
		return _monitors;
	}
	
	private ArrayList<MonitorObject> getMonitorsListFromFile(File _file) throws CheckedException{
		ArrayList<MonitorObject> _monitorsList;
		Scanner _fileScanner;
		String _tempLine;
		MonitorObject _monitorObject;
		StringTokenizer _tempTokens;
		String _rowString;
		String _columnString;
		String _token;
		int _countTokens;
		_monitorsList = new ArrayList<MonitorObject>();
		boolean _isFirstLine = true;;
		/**
		 * Start the code to populate the Array list with Monitor Objects
		 */
		try {
			_fileScanner = new Scanner(_file);
			_monitorObject = null;
			for(boolean _hasNextLine = _fileScanner.hasNextLine();_hasNextLine;_hasNextLine = _fileScanner.hasNextLine()){
				_rowString = null;
				_columnString = null;
				_tempLine = _fileScanner.nextLine();
				if(_tempLine.startsWith(DatePrefix)){
					if(!_isFirstLine){
						_monitorsList.add(_monitorObject);
					}
					_monitorObject = new MonitorObject(_tempLine,this.monitoredElement);
					_isFirstLine = false;
					continue;
				}
				if(_tempLine.startsWith(LineSeparator)){
					if(this.hasHeader.equals("true")){
						_fileScanner.nextLine();
					}
					continue;
				}
				_tempTokens = new StringTokenizer(_tempLine);
				_countTokens = _tempTokens.countTokens();
				/*
				 * Custom Fix for the FAST Linux servers might create problems in future
				 */
				if(_countTokens == LinuxDiskTokenCount && this.monitoredElement.equals("DISK")){
					setUtilizationValuesForLinuxServers(_fileScanner,_monitorObject);
					continue;
				} 
				/*
				 * End of Custom Fix for the FAST Linux servers might create problems in future
				 */
				for(int _index=0;_index<_countTokens;_index++){
					_token = _tempTokens.nextToken();
					if(_index == (Integer.parseInt(this.rowId))){
						_rowString = _token;
						continue;
					}
					if(_index == (Integer.parseInt(this.columnId))){
						_columnString = _token;
						continue;
					}
				}
				if( (_monitorObject != null) && (_rowString != null) && (_columnString != null) ){
					_monitorObject.setUtilizationValues(_rowString,_columnString);	
				}
			}
		} catch (FileNotFoundException e) {
			CheckedException ce = new CheckedException(e);
			ce.setCustomMessage("Error while creating the MonitorObjects ");
			throw ce;
		}
		/**
		 * End of the code to populate the ArrayList
		 */
		return _monitorsList;
	}
	
	private void setUtilizationValuesForLinuxServers(Scanner fileScanner,MonitorObject monitorObject){
		String _tempLine;
		StringTokenizer _tempTokens;
		int _countTokens;
		String _rowString;
		String _columnString;
		String _token;
		
		_rowString = null;
		_columnString = null;
		_tempLine = fileScanner.nextLine();
		_tempTokens = new StringTokenizer(_tempLine);
		_countTokens = _tempTokens.countTokens();
		for(int _index=0;_index<_countTokens;_index++){
			_token = _tempTokens.nextToken();
			if(_index == (Integer.parseInt(this.rowId)-1)){
				_rowString = _token;
				continue;
			}
			if(_index == (Integer.parseInt(this.columnId)-1)){
				_columnString = _token;
				continue;
			}
		}
		if( (monitorObject != null) && (_rowString != null) && (_columnString != null) ){
			monitorObject.setUtilizationValues(_rowString,_columnString);	
		}
	}
}
