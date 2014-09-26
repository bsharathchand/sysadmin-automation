package com.org.commons.atomcore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.org.commons.atomcore.exceptions.CheckedException;

/**
 * @author Sharath
 *
 */
public class FileResults {
	private FileWriter fileWriter;
	
	/**
	 * @throws CheckedException 
	 * 
	 * 
	 */
	public FileResults() throws CheckedException{
		this(".\\Temp\\TempFile");
	}
	
	/**
	 * @param fileName
	 * @throws CheckedException
	 */
	public FileResults(String fileName) throws CheckedException {
		this(fileName,true);
	}
	/**
	 * @param fileName
	 * @param append
	 * @throws CheckedException
	 */
	public FileResults(String fileName,boolean append) throws CheckedException {
		File _outputFile;
		String _fileName;
		_fileName = fileName;
		_outputFile = new File(_fileName);
		try {
			_outputFile.createNewFile();
			fileWriter = new FileWriter(_outputFile,append);
			fileWriter.flush();
		} catch (IOException e) {
			CheckedException _exception = new CheckedException(e);
			_exception.setCustomMessage("Couldn't create a new file");
			throw _exception;
		}
	}

	/**
	 * @param _bufferContents
	 * @throws CheckedException
	 */
	public void logResults (StringBuffer _bufferContents) throws CheckedException{
		try {
			fileWriter.append(_bufferContents.toString());
		} catch (IOException e) {
			CheckedException _exception = new CheckedException(e);
			_exception.setCustomMessage("Couldn't append to file");
			throw _exception;
		}
	}
	
	/**
	 * @throws CheckedException
	 */
	public void closeLogging() throws CheckedException{
		try {
			this.fileWriter.close();
		} catch (IOException e) {
			CheckedException _exception = new CheckedException(e);
			_exception.setCustomMessage("Couldn't append to file");
			throw _exception;
		}
	}
	/**
	 * @param aa
	 */
	public static void main(String aa[]){
		StringBuffer _contents ;
		
		_contents = new StringBuffer();
		
		_contents.append("HI this is sharath \n");
		_contents.append("Testing the file writeer \n");
		_contents.append("This is the end of the file");
		
		try {
			FileResults fr = new FileResults();
			fr.logResults(_contents);
			fr.logResults(_contents);
			fr.logResults(_contents);
			fr.closeLogging();
			System.out.println("Done");
		} catch (CheckedException e) {
			System.out.println("Error : "+e.getCustomMessage());
			e.printStackTrace();
		}
	}

}
