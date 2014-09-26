/**
 * 
 */
package com.org.commons.atomparser.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.org.commons.atomparser.exceptions.CheckedException;

/**
 * @author Sharath
 *
 */
public abstract class AtomUtilizationAdapter implements AtomUtilizations {
	
	protected String serverIp;
	protected String serverDescription;
	/**
	 * 
	 */
	public static String UtilizationFilePath = ".//AtomLogs//Utilization Results//";
	/**
	 * 
	 */
	public static String FileExtension = ".html";
	
	/**
	 * @param serverIp
	 * @param serverDescription
	 */
	public AtomUtilizationAdapter(String serverIp,String serverDescription){
		this.serverIp = serverIp;
		this.serverDescription = serverDescription;
	}

	/* (non-Javadoc)
	 * @see com.org.commons.atomparser.parser.AtomUtilizations#fileUtilization(java.lang.StringBuffer)
	 */
	@Override
	public void fileUtilization(StringBuffer utilizationGraph) throws CheckedException {
		File _myFile;
		FileWriter _fileWriter;
		String _fileName;
		
		_fileName = UtilizationFilePath+this.serverIp+FileExtension;
		_myFile = new File(_fileName);
		_fileWriter = null;
		if(!_myFile.exists()){
			try {
				_myFile.createNewFile();
			} catch (IOException e) {
				CheckedException ce = new CheckedException(e);
				ce.setCustomMessage("Unbale to create a File : "+_fileName.replace(UtilizationFilePath,""));
				throw ce;
			}
		}
		try {
			_fileWriter = new FileWriter(_myFile);
			_fileWriter.flush();
			_fileWriter.write(utilizationGraph.toString());
		} catch (IOException e) {
			CheckedException ce = new CheckedException(e);
			ce.setCustomMessage("Unbale to write to Utilization File"+ _fileName.replace(UtilizationFilePath, ""));
			throw ce;
		} finally {
			if (_fileWriter != null) {
				try {
					_fileWriter.close();
				} catch (IOException e) {
					CheckedException ce = new CheckedException(e);
					ce.setCustomMessage("Unbale to close file Writer of : "+ _fileName.replace(UtilizationFilePath, ""));
					throw ce;
				}
			}
		}

	}
	
}
